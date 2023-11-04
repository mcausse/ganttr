package io.homs.ganttr.ga;

import io.homs.ganttr.GanttCombinationsIterator;
import io.homs.ganttr.GanttSolution;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;

import java.util.*;

public class GeneticGanttr {

    private final Random rnd = new Random(1L);

    private final List<User> users;
    private final List<Task> tasks;

    public GeneticGanttr(List<User> users, List<Task> tasks) {
        this.users = users;
        this.tasks = tasks;
    }

    public GanttSolution run(int numGenerations, int poblationSize) {

        int[] adam = new int[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            adam[i] = i % (users.size());
        }

        List<Individual> poblation = new ArrayList<>();
        for (int i = 0; i < poblationSize; i++) {
            poblation.add(new Individual(adam));
        }

        GanttSolution bestGenerationSolution = null;
        for (int gen = 0; gen < numGenerations; gen++) {
            List<ScoredIndividual> scoreds = new ArrayList<>();
            for (var individual : poblation) {
                scoreds.add(new ScoredIndividual(individual, calculateFitnessOf(individual)));
            }
            scoreds.sort(Comparator.comparingInt(a -> a.fitness));

            // ===
            // printa el millor individu de la generació
            Individual bestGenerationIndividual = scoreds.get(0).individual;
            bestGenerationSolution = calculateCombination(bestGenerationIndividual.getCombination());
            System.out.println(bestGenerationSolution.toString());
            // ===

            for (int i = 0; i < poblationSize; i++) {
                if (i < poblationSize / 2) {
                    poblation.set(i, scoreds.get(i).individual);
                } else {
                    poblation.set(i, mutate(scoreds.get(poblationSize / 2).individual));
                }
            }
        }
        return bestGenerationSolution;
    }

    protected Individual mutate(Individual individual) {
        int[] combination = Arrays.copyOf(individual.getCombination(), individual.getCombination().length);

        do {
            int indexToMutate = rnd.nextInt(tasks.size());
            int mutatedValue = rnd.nextInt(users.size());
            combination[indexToMutate] = mutatedValue;
        } while (rnd.nextInt(100) < 25);

        return new Individual(combination);
    }

    protected int calculateFitnessOf(Individual individuaL) {
        GanttSolution solution = calculateCombination(individuaL.getCombination());
        int daysToFinish = solution.calculateDaysToFinish();
        int workingUsers = solution.calculateWorkingUsers();
        return calculateScore(daysToFinish, workingUsers);
    }

    protected static int calculateScore(int daysToFinish, int workingUsers) {
        return daysToFinish * 10 + workingUsers;
    }

    protected GanttSolution calculateCombination(int[] combination) {
        var solution = new GanttSolution(users);
        var t = new GanttCombinationsIterator(tasks);

        var task = t.getNextTaskToProcess();
        while (task != null) {

            int taskStartingDay = 0;
            for (var dep : task.dependencies) {
                int depEndingDay = t.getProcessedTaskEndingDay(dep);
                if (taskStartingDay < depEndingDay) {
                    taskStartingDay = depEndingDay;
                }
            }

            int taskCombinationIndex = tasks.indexOf(task);
            int userIndex = combination[taskCombinationIndex];
            User userToExecuteTheTask = users.get(userIndex);
            int taskEndingDay = solution.addTaskTo(userToExecuteTheTask, task, taskStartingDay);
            t.markTaskAsProcessedEndingAtDay(task, taskEndingDay);

            task = t.getNextTaskToProcess();
        }
        return solution;
    }
}

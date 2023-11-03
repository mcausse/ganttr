package io.homs.ganttr.ga;

import io.homs.ganttr.GanttSolution;
import io.homs.ganttr.GanttTasksIterator;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;

import java.util.*;

public class G {

    final Random rnd = new Random(1L);

    static class Individual {

        private final int[] combination;

        public Individual(int[] combination) {
            this.combination = Arrays.copyOf(combination, combination.length);
        }
    }

    static class ScoredIndividual {
        public final Individual individual;
        public final int fitness;

        public ScoredIndividual(Individual individual, int fitness) {
            this.individual = individual;
            this.fitness = fitness;
        }
    }

    private final List<User> users;
    private final List<Task> tasks;

    public G(List<User> users, List<Task> tasks) {
        this.users = users;
        this.tasks = tasks;
    }

    public void run(int numGenerations, int poblationSize) {

        int[] adam = new int[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            adam[i] = i % (users.size());
        }

        List<Individual> poblation = new ArrayList<>();
        for (int i = 0; i < poblationSize; i++) {
            poblation.add(new Individual(adam));
        }

        for (int gen = 0; gen < numGenerations; gen++) {
            List<ScoredIndividual> scoreds = new ArrayList<>();
            for (var individual : poblation) {
                scoreds.add(new ScoredIndividual(individual, calculateFitnessOf(individual)));
            }
            scoreds.sort(Comparator.comparingInt(a -> a.fitness));

            // ===
            Individual bestGenerationIndividual = scoreds.get(0).individual;
            GanttSolution solution = calculateCombination(bestGenerationIndividual.combination);
            System.out.println(solution.toString());
            // ===

            for (int i = 0; i < poblationSize; i++) {
                if (i < poblationSize / 2) {
                    poblation.set(i, scoreds.get(i).individual);
                } else {
                    poblation.set(i, mutate(scoreds.get(poblationSize / 2).individual));
                }
            }
        }
    }

    protected Individual mutate(Individual individual) {
        int[] combination = Arrays.copyOf(individual.combination, individual.combination.length);

        while (rnd.nextInt(100) < 25) {
            int indexToMutate = rnd.nextInt(tasks.size());
            int mutatedValue = rnd.nextInt(users.size());
            combination[indexToMutate] = mutatedValue;
        }
        return new Individual(combination);
    }

    protected int calculateFitnessOf(Individual individuaL) {
        GanttSolution solution = calculateCombination(individuaL.combination);
        int daysToFinish = solution.calculateDaysToFinish();
        int workingUsers = solution.calculateWorkingUsers();
        int score = calculateScore(daysToFinish, workingUsers);
        return score;
    }

    protected static int calculateScore(int daysToFinish, int workingUsers) {
//        return (1_000 - daysToFinish) * 10 + (100 - workingUsers);
        return daysToFinish * 10 + workingUsers;
    }

    protected GanttSolution calculateCombination(int[] combination) {
        var solution = new GanttSolution(users);
        var t = new GanttTasksIterator(tasks);

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

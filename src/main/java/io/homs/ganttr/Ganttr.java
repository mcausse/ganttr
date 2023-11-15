package io.homs.ganttr;

import java.util.List;

public class Ganttr {

    private final SolutionsProcessor solutionsProcessor;
    private final List<User> users;
    private final List<Task> tasks;

    public Ganttr(SolutionsProcessor solutionsProcessor, List<User> users, List<Task> tasks) {
        this.solutionsProcessor = solutionsProcessor;
        this.users = users;
        this.tasks = tasks;
    }

    public void gantterize() {

        long totalCombinationsToExplore = (long) Math.pow(users.size(), tasks.size());

        // combination[#task] => #user
        var combinationsGen = CombinationsIterator.of(tasks.size(), 0, users.size());
        long numCombinationsExplored = 0;
        while (combinationsGen.hasNext()) {
            var combination = combinationsGen.getNext();
            GanttSolution solution = calculateCombination(combination);

            solutionsProcessor.process(numCombinationsExplored, combination, solution);

            numCombinationsExplored++;
            if (numCombinationsExplored % 1_000_000 == 0) {
                System.out.println((100L * numCombinationsExplored) / totalCombinationsToExplore + "% of " + totalCombinationsToExplore + " combinations");
            }
        }
        solutionsProcessor.finish(users, tasks, numCombinationsExplored);
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

        t.verifyDeadTasksAtFinish();
        return solution;
    }
}

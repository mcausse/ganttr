package io.homs.ganttr;

import java.util.List;

public class Ganttr {

    private final SolutionsProcessor solutionsProcessor;
    private final List<User> users;
    private final GanttTasksList tasksList;

    public Ganttr(SolutionsProcessor solutionsProcessor, List<User> users, List<Task> tasks) {
        this.solutionsProcessor = solutionsProcessor;
        this.users = users;
        this.tasksList = new GanttTasksList(tasks);
    }

    public void gantterize() {

        // combination[#task] => #user
        var combinationsGen = CombinationsIterator.of(tasksList.getNumTasks(), 0, users.size());
        int numCombinationsExplored = 0;
        while (combinationsGen.hasNext()) {
            var combination = combinationsGen.getNext();
            GanttSolution solution = calculateCombination(combination);

            solutionsProcessor.process(numCombinationsExplored, combination, solution);

            numCombinationsExplored++;
        }
        System.out.println();
        System.out.println("total combinations explored: " + numCombinationsExplored);
        solutionsProcessor.finish();
    }

    protected GanttSolution calculateCombination(int[] combination) {

        var solution = new GanttSolution(users);
        List<Task> initialTasks = tasksList.getInitialTasks();
        int daysOffset = 0;
        r(combination, solution, initialTasks, daysOffset);

        return solution;
    }

    protected void r(int[] combination, GanttSolution solution, List<Task> tasks, int daysOffset) {
        for (var task : tasks) {
            int taskCombinationIndex = tasksList.getIndexOfTask(task);
            int userIndex = combination[taskCombinationIndex];
            User userToExecuteTheTask = users.get(userIndex);
            int nextDaysOffset = solution.addTaskTo(userToExecuteTheTask, task, daysOffset);

            // recursion
            r(combination, solution, tasksList.getNextTasksOf(task), nextDaysOffset);
        }
    }
}

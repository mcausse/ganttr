package io.homs.ganttr;

import java.util.List;

public class Ganttr {

    private final SolutionsProcessor solutionsProcessor;
    private final List<User> users;
    private final List<Task> tasksList;

    public Ganttr(SolutionsProcessor solutionsProcessor, List<User> users, List<Task> taskList) {
        this.solutionsProcessor = solutionsProcessor;
        this.users = users;
        this.tasksList = taskList;
    }

    public void gantterize() {

        // combination[#task] => #user
        var combinationsGen = CombinationsIterator.of(tasksList.size(), 0, users.size());
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
        var t = new GanttTasksIterator(tasksList);

        var task = t.getNextTaskToProcess();
        while (task != null) {

            int taskStartingDay = 0;
            for (var dep : task.dependencies) {
                int depEndingDay = t.getProcessedTaskEndingDay(dep);
                if (taskStartingDay < depEndingDay) {
                    taskStartingDay = depEndingDay;
                }
            }

            int taskCombinationIndex = tasksList.indexOf(task);
            int userIndex = combination[taskCombinationIndex];
            User userToExecuteTheTask = users.get(userIndex);
            int taskEndingDay = solution.addTaskTo(userToExecuteTheTask, task, taskStartingDay);
            t.markTaskAsProcessedEndingAtDay(task, taskEndingDay);

            task = t.getNextTaskToProcess();
        }
        return solution;
    }
}

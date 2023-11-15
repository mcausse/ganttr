package io.homs.ganttr;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GanttCombinationsIterator {

    static class EndingAtDay {

        public final int day;

        public EndingAtDay(int day) {
            this.day = day;
        }
    }

    private final List<Task> tasks;
    private final Map<Task, EndingAtDay> processedTasks;

    public GanttCombinationsIterator(List<Task> tasks) {
        this.tasks = tasks;
        this.processedTasks = new LinkedHashMap<>();
    }

    public Task getNextTaskToProcess() {
        for (Task task : this.tasks) {
            if (processedTasks.containsKey(task)) {
                continue;
            }
            if (processedTasks.keySet().containsAll(task.dependencies)) {
                return task;
            }
        }
        return null;
    }

    public void markTaskAsProcessedEndingAtDay(Task task, int day) {
        this.processedTasks.put(task, new EndingAtDay(day));
    }

    public int getProcessedTaskEndingDay(Task task) {
        return this.processedTasks.get(task).day;
    }

    public void verifyDeadTasksAtFinish() {
        if (!processedTasks.keySet().containsAll(tasks)) {
            throw new RuntimeException("there are some task dead at the end of process");
        }
    }
}

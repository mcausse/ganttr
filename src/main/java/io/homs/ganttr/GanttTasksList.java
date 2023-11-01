package io.homs.ganttr;

import java.util.List;
import java.util.stream.Collectors;

public class GanttTasksList {

    private final List<Task> tasks;

    public GanttTasksList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getInitialTasks() {
        return this.tasks.stream()
                .filter(t -> t.dependencies.isEmpty())
                .collect(Collectors.toList());
    }

    public List<Task> getNextTasksOf(Task task) {
        return this.tasks.stream()
                .filter(t -> t.dependencies.contains(task))
                .collect(Collectors.toList());
    }

    public int getNumTasks() {
        return tasks.size();
    }

    public int getIndexOfTask(Task task) {
        return tasks.indexOf(task);
    }
}

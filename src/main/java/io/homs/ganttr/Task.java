package io.homs.ganttr;

import java.util.Arrays;
import java.util.List;

public class Task {

    public final char code;
    public final String label;
    public final String description;
    public final int durationInDays;
    public final List<Task> dependencies;

    public Task(char code, String description, int durationInDays, Task... dependencies) {
        this.code = code;
        this.label = String.valueOf(code) + code;
        this.description = description;
        this.durationInDays = durationInDays;
        this.dependencies = Arrays.asList(dependencies);
    }

    public Task(char code, String label, String description, int durationInDays, Task... dependencies) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.durationInDays = durationInDays;
        this.dependencies = Arrays.asList(dependencies);
    }

    @Override
    public String toString() {
        return "Task{" +
                "code=" + code +
                '}';
    }
}

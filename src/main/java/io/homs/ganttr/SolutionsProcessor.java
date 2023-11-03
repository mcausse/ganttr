package io.homs.ganttr;

import java.util.List;

public interface SolutionsProcessor {

    void process(long numCombinationsExplored, int[] combination, GanttSolution solution);

    void finish(List<User> users, List<Task> taskList, long numCombinationsExplored);
}

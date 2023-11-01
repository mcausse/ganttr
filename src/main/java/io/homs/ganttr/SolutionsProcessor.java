package io.homs.ganttr;

public interface SolutionsProcessor {

    void process(int numCombinationsExplored, int[] combination, GanttSolution solution);

    void finish();
}

package io.homs.ganttr;

import java.util.Arrays;
import java.util.Map;

public class FinishAsapSolutionsProcessor implements SolutionsProcessor {

    private int bestScore = -1;
    private GanttSolution bestScoredSolution = null;

    private static int calculateScore(int daysToFinish, int workingUsers) {
        return (1_000 - daysToFinish) * 10 + (100 - workingUsers);
    }

    @Override
    public void process(int numCombinationsExplored, int[] combination, GanttSolution solution) {

        System.out.println("#" + numCombinationsExplored);
        System.out.println(Arrays.toString(combination));
        System.out.println(solution);

        int daysToFinish = calculateDaysToFinish(solution);
        int workingUsers = calculateWorkingUsers(solution);

        int score = calculateScore(daysToFinish, workingUsers);

        System.out.println("days to finish: " + daysToFinish);
        System.out.println("working users:  " + workingUsers);
        System.out.println("-----------------");
        System.out.println("score:          " + score);
        System.out.println();

        if (this.bestScore < score) {
            this.bestScore = score;
            bestScoredSolution = solution;
        }
    }

    @Override
    public void finish() {

        System.out.println();
        System.out.println("===============================================");
        System.out.println("===============================================");
        System.out.println("===============================================");
        System.out.println();

        System.out.println(bestScoredSolution);

        int daysToFinish = calculateDaysToFinish(bestScoredSolution);
        int workingUsers = calculateWorkingUsers(bestScoredSolution);

        int score = calculateScore(daysToFinish, workingUsers);

        System.out.println("days to finish: " + daysToFinish);
        System.out.println("working users:  " + workingUsers);
        System.out.println("-----------------");
        System.out.println("score:          " + score);
        System.out.println();
    }

    protected int calculateDaysToFinish(GanttSolution solution) {
        int r = 0;

        for (StringBuilder pattern : solution.getUserSolution().values()) {
            for (int day = 0; day < pattern.length(); day++) {
                if (r < day && dayFilledWithTask(pattern, day)) {
                    r = day;
                }
            }
        }

        return r + 1;
    }

    protected int calculateWorkingUsers(GanttSolution solution) {
        int users = 0;

        for (Map.Entry<User, StringBuilder> entry : solution.getUserSolution().entrySet()) {
            StringBuilder pattern = entry.getValue();
            for (int day = 0; day < pattern.length(); day++) {
                if (dayFilledWithTask(pattern, day)) {
                    users++;
                    break;
                }
            }
        }

        return users;
    }

    protected boolean dayFilledWithTask(StringBuilder pattern, int nthDay) {
        char c = pattern.charAt(nthDay);
        return c != User.EMPTY_WORKING_DAY_CHAR && c != User.DAY_OFF_CHAR;
    }

    public GanttSolution getBestScoredSolution() {
        return bestScoredSolution;
    }
}

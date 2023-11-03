package io.homs.ganttr;

import java.util.Arrays;
import java.util.List;

public class FinishAsapSolutionsProcessor implements SolutionsProcessor {

    private int bestScore = -1;
    private GanttSolution bestScoredSolution = null;

    private final boolean showToSout;

    public FinishAsapSolutionsProcessor(boolean showToSout) {
        this.showToSout = showToSout;
    }

    public FinishAsapSolutionsProcessor() {
        this(true);
    }

    private static int calculateScore(int daysToFinish, int workingUsers) {
        return (1_000 - daysToFinish) * 10 + (100 - workingUsers);
    }

    @Override
    public void process(long numCombinationsExplored, int[] combination, GanttSolution solution) {

        int daysToFinish = solution.calculateDaysToFinish();
        int workingUsers = solution.calculateWorkingUsers();
        int score = calculateScore(daysToFinish, workingUsers);

        if (this.showToSout) {
            int totalUsers = solution.getUserSolution().keySet().size();
            System.out.println(Arrays.toString(combination));
            System.out.println(solution);
            System.out.println("days to finish: " + daysToFinish);
            System.out.println("working users:  " + workingUsers + "/" + totalUsers);
            System.out.println("-----------------");
            System.out.println("score:          " + score);
            System.out.println();
        }

        if (this.bestScore < score) {
            this.bestScore = score;
            bestScoredSolution = solution;
            System.out.println("=> score: " + score + "; days: " + daysToFinish + "; devs=" + workingUsers);
        }
    }

    @Override
    public void finish(List<User> users, List<Task> tasks, long numCombinationsExplored) {

        System.out.println();
        System.out.println("===============================================");
        for (Task task : tasks) {
            System.out.println(task.code + ": " + task.description + " (" + task.durationInDays + " days)");
        }
        System.out.println("===============================================");

        int daysToFinish = bestScoredSolution.calculateDaysToFinish();
        int workingUsers = bestScoredSolution.calculateWorkingUsers();
        int totalUsers = bestScoredSolution.getUserSolution().keySet().size();
        int score = calculateScore(daysToFinish, workingUsers);

        System.out.println(bestScoredSolution);
        System.out.println("days to finish: " + daysToFinish);
        System.out.println("working users:  " + workingUsers + "/" + totalUsers);
        System.out.println("-----------------");
        System.out.println("score:          " + score);
        System.out.println("combinations:   " + numCombinationsExplored);
        System.out.println("===============================================");
        System.out.println();
    }

//    protected int calculateDaysToFinish(GanttSolution solution) {
//        int r = 0;
//
//        for (StringBuilder pattern : solution.getUserSolution().values()) {
//            for (int day = 0; day < pattern.length(); day++) {
//                if (r < day && dayFilledWithTask(pattern, day)) {
//                    r = day;
//                }
//            }
//        }
//
//        return r + 1;
//    }
//
//    protected int calculateWorkingUsers(GanttSolution solution) {
//        int users = 0;
//
//        for (Map.Entry<User, StringBuilder> entry : solution.getUserSolution().entrySet()) {
//            StringBuilder pattern = entry.getValue();
//            for (int day = 0; day < pattern.length(); day++) {
//                if (dayFilledWithTask(pattern, day)) {
//                    users++;
//                    break;
//                }
//            }
//        }
//
//        return users;
//    }
//
//    protected boolean dayFilledWithTask(StringBuilder pattern, int nthDay) {
//        char c = pattern.charAt(nthDay);
//        return c != User.EMPTY_WORKING_DAY_CHAR && c != User.DAY_OFF_CHAR;
//    }

    public GanttSolution getBestScoredSolution() {
        return bestScoredSolution;
    }
}

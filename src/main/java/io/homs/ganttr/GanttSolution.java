package io.homs.ganttr;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GanttSolution {

    private final Map<User, StringBuilder> userSolution;

    public GanttSolution(List<User> users) {
        this.userSolution = new LinkedHashMap<>();
        users.forEach(u -> {
            userSolution.put(u, new StringBuilder(u.daysPattern));
        });
    }

    protected boolean isUnassignedDay(User user, int nthDay) {
        var daysPattern = userSolution.get(user);
        if (nthDay >= daysPattern.length()) {
            return true;
        }
        return User.EMPTY_WORKING_DAY_CHAR == daysPattern.charAt(nthDay);
    }

    /**
     * @return the nth day in witch this task ends, taking into consideration the starting day,
     * the duration of the task itself, and the days off.
     */
    public int addTaskTo(User user, Task task, int startingAtNthDay) {
        int r = startingAtNthDay;

        var strb = userSolution.get(user);
        int taskDurationInDays = task.durationInDays;

        for (int day = startingAtNthDay; taskDurationInDays > 0; day++) {

            if (day < strb.length()) {
                if (isUnassignedDay(user, day)) {
                    strb.setCharAt(day, task.code);
                    taskDurationInDays--;
                }
            } else {
                strb.append(task.code);
                taskDurationInDays--;
            }

            r++;
        }

        return r;
    }

    public String getSolutionForUser(User user) {
        return userSolution.get(user).toString();
    }

    public Map<User, StringBuilder> getUserSolution() {
        return userSolution;
    }

    @Override
    public String toString() {
        var r = new StringBuilder();
        for (var user : userSolution.keySet()) {
            r.append(user.name);
            r.append(": ");
            r.append(userSolution.get(user).toString());
            r.append('\n');
        }
        return r.toString();
    }
}
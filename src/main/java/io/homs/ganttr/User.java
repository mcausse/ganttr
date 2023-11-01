package io.homs.ganttr;

public class User {

    public static final char EMPTY_WORKING_DAY_CHAR = '.';
    public static final char DAY_OFF_CHAR = '|';

    public final String name;
    public final String daysPattern;

    public User(String name, String daysPattern) {
        this.name = name;
        this.daysPattern = daysPattern;
    }
}

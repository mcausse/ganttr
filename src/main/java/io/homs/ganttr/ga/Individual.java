package io.homs.ganttr.ga;

import java.util.Arrays;

public class Individual {

    private final int[] combination;

    public Individual(int[] combination) {
        this.combination = Arrays.copyOf(combination, combination.length);
    }

    public int[] getCombination() {
        return combination;
    }
}

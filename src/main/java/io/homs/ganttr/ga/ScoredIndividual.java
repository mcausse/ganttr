package io.homs.ganttr.ga;

public class ScoredIndividual {
    public final Individual individual;
    public final int fitness;

    public ScoredIndividual(Individual individual, int fitness) {
        this.individual = individual;
        this.fitness = fitness;
    }
}

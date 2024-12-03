package io.homs.ganttr.ga;

import io.homs.ganttr.GanttSolution;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneticGanttr2Test {

    @Test
    void name() {

        var mh = new User("mh", ".....||.....||.....||");
        var jh = new User("jh", ".....||.....||.....||");
        var jc = new User("ah", ".....||.....||.....||");

        var t1 = new Task('1', "datamodel", 3);
        var t2 = new Task('2', "upgrade", 2, t1);
        var t3 = new Task('3', "logic", 4, t1);
        var t4 = new Task('4', "controller", 4, t1);
        var t5 = new Task('5', "ats", 3, t3, t4);

        GeneticGanttr geneticGanttr = new GeneticGanttr(
                List.of(mh, jh, jc),
                List.of(t1, t2, t3, t4, t5)
        );

        // Act
        GanttSolution best = geneticGanttr.run(100, 1000);

        System.out.println(best.toString());
        assertThat(best).hasToString(
                """
                        mh: 11144||44...||.....||
                        jh: ...22||..555||.....||
                        ah: ...33||33...||.....||
                        """
        );
    }
}
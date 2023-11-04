package io.homs.ganttr.ga;

import io.homs.ganttr.GanttSolution;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneticGanttrTest {

    @Test
    void name() {

        var mh = new User("mh", ".....||.....||||...||");
        var jh = new User("jh", "||...||.....||.....||");
        var jc = new User("jc", ".....||||...||.....||");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 2, t1);
        var t3 = new Task('3', "part (A)", 4, t1);
        var t4 = new Task('4', "part (B)", 3, t2, t3);

        GeneticGanttr geneticGanttr = new GeneticGanttr(
                List.of(mh, jh, jc),
                List.of(t1, t2, t3, t4/*, t5, t6, t7, t8, t9*/)
        );

        // Act
        GanttSolution best = geneticGanttr.run(100, 1000);

        System.out.println(best);
        assertThat(best).hasToString(
                "mh: 11133||33444||||...||\n" +
                        "jh: ||.22||.....||.....||\n" +
                        "jc: .....||||...||.....||\n"
        );
    }
}
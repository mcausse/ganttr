package io.homs.ganttr;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GanttrTest {

    @Test
    void calculateCombination() {

        var mh = new User("mh", ".....||.....||||...||");
        var jh = new User("jh", "||...||.....||.....||");
        var jc = new User("jc", ".....||||...||.....||");
        var pb = new User("pb", ".....|||||||||.....||");
        var rc = new User("rc", ".....||||...||...|.||");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 2, t1);
        var t3 = new Task('3', "part (A)", 4, t2);
        var t4 = new Task('4', "part (B)", 5, t2);
        var t5 = new Task('5', "SAD", 1);

        var gantt = new Ganttr(
                null,
                List.of(mh, jh, jc, pb, rc),
                List.of(t1, t2, t3, t4, t5)
        );

        //        [4, 1, 0, 2, 3]
        //        mh: .....||3333.||||...||
        //        jh: ||.22||.....||.....||
        //        jc: .....||||444||44...||
        //        pb: 5....|||||||||.....||
        //        rc: 111..||||...||...|.||

        // Act
        GanttSolution solution = gantt.calculateCombination(new int[]{4, 1, 0, 2, 3});

        assertThat(solution).hasToString(
                "mh: .....||3333.||||...||\n" +
                        "jh: ||.22||.....||.....||\n" +
                        "jc: .....||||444||44...||\n" +
                        "pb: 5....|||||||||.....||\n" +
                        "rc: 111..||||...||...|.||\n"
        );
    }

    @Test
    void realTest() {

        var mh = new User("mh", ".....||.....||||...||");
        var jh = new User("jh", "||...||.....||.....||");
        var jc = new User("jc", ".....||||...||.....||");
        var pb = new User("pb", ".....|||||||||.....||");
        var rc = new User("rc", ".....||||...||...|.||");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 2, t1);
        var t3 = new Task('3', "part (A)", 4, t2);
        var t4 = new Task('4', "part (B)", 5, t2);
        var t5 = new Task('5', "SAD", 1);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor();
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh, jc, pb, rc),
                List.of(t1, t2, t3, t4, t5)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: 1115.||44444||||...||\n" +
                        "jh: ||.22||3333.||.....||\n" +
                        "jc: .....||||...||.....||\n" +
                        "pb: .....|||||||||.....||\n" +
                        "rc: .....||||...||...|.||\n"
        );
    }

    @Test
    void prefer_finish_asap() {

        var mh = new User("mh", "||...||.||..||.....||");
        var jh = new User("jh", ".....||.|...||.....||");

        var t1 = new Task('1', "", 10);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor();
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh),
                List.of(t1)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: ||...||.||..||.....||\n" +
                        "jh: 11111||1|111||1....||\n"
        );
    }

    @Test
    void prefer_parallelizing_to_finish_asap() {

        var mh = new User("mh", "||...||.||..||.....||");
        var jh = new User("jh", ".....||.|...||.....||");

        var t1 = new Task('1', "", 11);
        var t2 = new Task('2', "", 8);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor();
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh),
                List.of(t1, t2)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: ||222||2||22||22...||\n" +
                        "jh: 11111||1|111||11...||\n"
        );
    }

    @Test
    void handle_tasks_tree_joins_as_graph() {

        var mh = new User("mh", ".....||.....||.....||");
        var jh = new User("jh", ".....|||....||.....||");

        var t1 = new Task('1', "", 3);
        var t2 = new Task('2', "", 4);
        var t3 = new Task('3', "", 2, t1, t2);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor();
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh),
                List.of(t1, t2, t3)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: 22223||3....||.....||\n" +
                        "jh: 111..|||....||.....||\n"
        );
    }

    @Test
    void more_complex_example() {

        var mh = new User("mh", ".....||.....||.....||");
        var jh = new User("jh", ".....||.....||.....||");
        var jc = new User("jc", ".....||.....||.....||");
        var pb = new User("pb", ".....||.....||.....||");
        var rc = new User("rc", ".....||.....||.....||");

        var t1 = new Task('1', "", 3);
        var t2 = new Task('2', "", 3, t1);
        var t3 = new Task('3', "", 3, t1);
        var t4 = new Task('4', "", 3, t1);
        var t5 = new Task('5', "", 3, t3);
        var t6 = new Task('6', "", 3, t4);
        var t7 = new Task('7', "", 3, t5, t6);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor(false);
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh, jc, pb, rc),
                List.of(t1, t2, t3, t4, t5, t6, t7)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: 11144||46667||77...||\n" +
                        "jh: ...33||3555.||.....||\n" +
                        "jc: ...22||2....||.....||\n" +
                        "pb: .....||.....||.....||\n" +
                        "rc: .....||.....||.....||\n"
        );
    }

    @Test
    void more_complex_example_with_less_developers() {

        var mh = new User("mh", ".....||.....||.....||");
        var jh = new User("jh", ".....||.....||.....||");

        var t1 = new Task('1', "", 3);
        var t2 = new Task('2', "", 3, t1);
        var t3 = new Task('3', "", 3, t1);
        var t4 = new Task('4', "", 3, t1);
        var t5 = new Task('5', "", 3, t3);
        var t6 = new Task('6', "", 3, t4);
        var t7 = new Task('7', "", 3, t5, t6);

        FinishAsapSolutionsProcessor solutionsProcessor = new FinishAsapSolutionsProcessor(false);
        var gantt = new Ganttr(
                solutionsProcessor,
                List.of(mh, jh),
                List.of(t1, t2, t3, t4, t5, t6, t7)
        );

        // Act
        gantt.gantterize();

        assertThat(solutionsProcessor.getBestScoredSolution()).hasToString(
                "mh: 11144||46665||55777||\n" +
                        "jh: ...22||2333.||.....||\n"
        );
    }
}
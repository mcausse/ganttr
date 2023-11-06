package io.homs.ganttr.realcases;

import io.homs.ganttr.FinishAsapSolutionsProcessor;
import io.homs.ganttr.Ganttr;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;
import io.homs.ganttr.ga.GeneticGanttr;

import java.util.List;

public class CorrelationAcksGantter {

    public static void main(String[] args) {

        var mh = new User("mh", ".....||.....||||...||");
        var jh = new User("jh", "||...||.....||.....||");
        var jc = new User("jc", ".....||||...||.....||");
        var pb = new User("pb", ".....|||||||||.....||");
        var rc = new User("rc", ".....||||...||...|.||");
        var qh = new User("qh", ".|.|.|.||.|.||.|.|.||");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 2, t1);
        var t3 = new Task('3', "part (A)", 4, t2);
        var t4 = new Task('4', "part (B)", 5, t2);
        var t5 = new Task('5', "CleanerDaemon", 3, t2);
        var t6 = new Task('6', "ManualSend", 3, t2);
        var t7 = new Task('7', "LoaderTool", 1, t2);
        var t8 = new Task('8', "CoreFunctionalReview", 2, t3, t4);
        var t9 = new Task('9', "FinalFunctionalReview", 4, t3, t4, t5, t6, t7, t8);

        var t0 = new Task('0', "SAD", 1);

        var gantt = new Ganttr(
                new FinishAsapSolutionsProcessor(false),
                List.of(mh, jh, jc, pb, rc, qh),
                List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t0)
        );
//        gantt.gantterize();

        //        total combinations explored: 9765625
        //
        //        mh: 0..22||33337||||999||9
        //        jh: ||...||44444||88...||
        //        jc: 111..||||666||.....||
        //        pb: .....|||||||||.....||
        //        rc: .....||||555||...|.||
        //
        //        days to finish: 22
        //        working users:  4
        //        -----------------
        //        score:          9876

        GeneticGanttr geneticGanttr = new GeneticGanttr(
                List.of(mh, jh, jc, pb, rc, qh),
                List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t0)
        );
        geneticGanttr.run(200, 10000);

        //        mh: 111..||33337||||999||9
        //        jh: ||.22||44444||88...||
        //        jc: 0....||||666||.....||
        //        pb: .....|||||||||.....||
        //        rc: .....||||555||...|.||
        //        qh: .|.|.|.||.|.||.|.|.||

        //        mh: 0....||33337||||...||
        //        jh: ||.22||44444||88...||
        //        jc: .....||||555||..999||9
        //        pb: .....|||||||||.....||
        //        rc: 111..||||666||...|.||
        //        qh: .|.|.|.||.|.||.|.|.||
    }
}

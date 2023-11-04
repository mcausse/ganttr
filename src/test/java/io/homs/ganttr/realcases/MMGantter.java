package io.homs.ganttr.realcases;

import io.homs.ganttr.Task;
import io.homs.ganttr.User;
import io.homs.ganttr.ga.GeneticGanttr;

import java.util.List;

public class MMGantter {

    public static void main(String[] args) {

        var rc = new User("rc", "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||....||.....||");
        var pb = new User("pb", ".....||||||||||||||||.....||.....||.....||.....||.....||.....||.....||.....||");
        var mh = new User("mh", ".....||||...|||....||.....||.....||.....||.....||.....||.....||.....||.....||");
        var jh = new User("jh", "...||||||...|||....||.....||.....||.....||.....||.....||.....||.....||.....||");
        var jc = new User("jc", "|||||||||||||||||||||.....||.....||.....||.....||.....||.....||.....||.....||");
        var qh = new User("qh", ".....||||||||||....||.....||.....||.....||.....||.....||.....||.....||.....||");


        //        ============================================================
        //        PHASE 2: Skeleton & deployment
        //        ============================================================

        var ta = new Task('a', "[DevOps] new repo or add to OpenCONNECT? Jenkins, reports, coverage, etc.", 1);
        var tb = new Task('b', "[MM] new MM project skeleton", 4);
        var tc = new Task('c', "[Installer] new MM project: Installer", 3);

        //        ============================================================
        //        PHASE 1: Infrastructure
        //        ============================================================

        var t1 = new Task('1', "[Infra] Java11=>17", 6);
        var t2 = new Task('2', "[Infra] new MM Schema creation & User.MessagesTable", 4);
        var t3 = new Task('3', "[Infra] New Iris MM user", 3, t2);
        var t4 = new Task('4', "[COS] [BET][Monitoring]Display & highlight field caused the error and its location on error description  v2", 7);
        var t5 = new Task('5', "[COS] unify/rename rest.RestTemplate and TLS Templates: DriverEngine => more javish, general name", 4);
        var t6 = new Task('6', "[UI] React pending testing, coverage and quality.", 5);
        var t7 = new Task('d', "[MM+Jasypt] new MM project: Jasypt with 2 Datasources", 5, t1, t2, t3, tb, tc);

        var mmPrototypeCheckPoint = new Task('*', "MM proptotype and infraestructure ready", 1,
                ta, tb, tc,
                t1, t2, t3, t4, t5, t6, t7
        );

        //        ============================================================
        //        PHASE 3: MM
        //        ============================================================

        var tA = new Task('A', "[COS] MessageMigration from CONN to MM schemas (rutina Upgrade)", 4, t2);
        var tB = new Task('B', "[UI] que el menú porti a nova MM, i mantenir l'antiga disponible mitjançant URL directa (persiaca en el futur).", 1);
        var tC = new Task('C', "[COS] MessageIngress/Egress & MM EventsDaemon", 8, t4, t5);
        var tD = new Task('D', "[MM] Message Domain Model", 1, mmPrototypeCheckPoint);
        var tE = new Task('E', "[MM] PUT - /messages/resend - Resend messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tF = new Task('F', "[MM] PUT - /messages/reopen - Reopen messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tG = new Task('G', "[MM] GET - /messages - Get messages list, paged and sorted, by search criteria", 5, mmPrototypeCheckPoint, tD);
        var tH = new Task('H', "[MM] GET - /messages/{uuid} - Get message detail by UUID", 5, mmPrototypeCheckPoint, tD);
        var tI = new Task('I', "[MM] DELETE - /messages/delete - Delete messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tJ = new Task('J', "[MM] POST - /events - Posts a batch of events", 8, mmPrototypeCheckPoint, tD);
        var tK = new Task('K', "[MM] MessageCleaner", 5, mmPrototypeCheckPoint, t2);
        var tL = new Task('L', "[MM] Timezones", 2, mmPrototypeCheckPoint, tG, tH);
        var tM = new Task('M', "[MM] Final integration, Functional review, final SonarQ, final UTs, final Coverage. Metrics report.", 5,
                tb,
                tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL);
        var tN = new Task('N', "[DOC] SAD Rationale", 2);

//        var gantt = new Ganttr(
//                new FinishAsapSolutionsProcessor(false),
//                List.of(/*mh,*/ jh, jc, pb, rc, qh),
//                List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, tb, tc, tf, tg, tj, tk, tl, tm, tn, to, tp, tq)
//        );
//        gantt.gantterize();

        GeneticGanttr geneticGanttr = new GeneticGanttr(
                List.of(/*mh,*/ jh, jc, pb, rc, qh),
                List.of(
                        t1, t2, t3, t4, t5, t6, t7,
                        ta, tb, tc,
                        mmPrototypeCheckPoint,
                        tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL, tM, tN
                )
        );
        geneticGanttr.run(10000, 100000);
    }
}

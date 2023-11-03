package io.homs.ganttr;

import io.homs.ganttr.ga.G;

import java.util.List;

public class MMGantter {

    public static void main(String[] args) {

        var rc = new User("rc", "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||....||....||");
        var pb = new User("pb", ".....||||||||||||||||....||....||....||....||....||....||....||....||");
//        var mh = new User("mh", ".....||||...|||....||....||....||....||....||....||....||....||....||");
        var jh = new User("jh", "...||||||...|||....||....||....||....||....||....||....||....||....||");
        var jc = new User("jc", "|||||||||||||||||||||....||....||....||....||....||....||....||....||");
        var qh = new User("qh", ".....||||||||||....||....||....||....||....||....||....||....||....||");

        var t1 = new Task('1', "Java11=>17:", 7);
        var t2 = new Task('2', "new MM Schema creation", 4);
        var t3 = new Task('3', "New Iris MM user", 4);
        var t4 = new Task('4', "[BET][Monitoring]Display & highlight field caused the error and its location on error description  v2", 5);
        var t5 = new Task('5', "new MM project skeleton", 4);
        var t6 = new Task('6', "new MM project: Installer", 3, t5);
        var t7 = new Task('7', "new MM project: Jasypt with 2 Datasources", 4, t5, t6);
        var t8 = new Task('8', "MessageMigration from CONN to MM schemas (rutina Upgrade)", 4, t2, t3);
        var t9 = new Task('9', "[DevOps] new repo or add to OpenCONNECT? Jenkins, reports, coverage, etc.", 1);
        var tb = new Task('b', "[UI] que el menú porti a nove MM, i mantenir l'antiga disponible mitjançant URL directa (persiaca en el futur).", 1);
        var tc = new Task('c', "unify/rename 'rest.RestTemplate' and TLS Templates: DriverEngine => more javish, general name", 3);
        var tf = new Task('f', "MessageIngress/Egress & MM EventsDaemon", 10);
        var tg = new Task('g', "Message Domain Model", 1, t5);
        var tj = new Task('j', "PUT - /messages/resend - Resend messages by UUID list + filters criteria", 4, tg);
        var tk = new Task('k', "PUT - /messages/reopen - Reopen messages by UUID list + filters criteria", 4, tg);
        var tl = new Task('l', "GET - /messages - Get messages list, paged and sorted, by search criteria", 4, tg);
        var tm = new Task('m', "GET - /messages/{uuid} - Get message detail by UUID", 3, tg);
        var tn = new Task('n', "DELETE - /messages/delete - Delete messages by UUID list + filters criteria", 4, tg);
        var to = new Task('o', "POST - /events - Posts a batch of events", 8, tg);
        var tp = new Task('p', "MessageCleaner", 5, tg);
        var tq = new Task('q', "MM Timezones", 3, tg, tl);


        var t0 = new Task('0', "SAD", 1);

//        var gantt = new Ganttr(
//                new FinishAsapSolutionsProcessor(false),
//                List.of(/*mh,*/ jh, jc, pb, rc, qh),
//                List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, tb, tc, tf, tg, tj, tk, tl, tm, tn, to, tp, tq)
//        );
//        gantt.gantterize();
        G g = new G(
                List.of(/*mh,*/ jh, jc, pb, rc, qh),
                List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, tb, tc, tf, tg, tj, tk, tl, tm, tn, to, tp, tq)
        );
        g.run(10000, 100000);
    }
}

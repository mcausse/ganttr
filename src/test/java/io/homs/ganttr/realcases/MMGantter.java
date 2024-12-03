package io.homs.ganttr.realcases;

import io.homs.ganttr.GanttCombinationsIterator;
import io.homs.ganttr.Task;
import io.homs.ganttr.User;
import io.homs.ganttr.ga.GeneticGanttr;

import java.util.List;

public class MMGantter {

    public static void main(String[] args) {

        var rc = new User("rc", "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||....||.....||");
        var pb = new User("pb", ".....||||||||||||||||.....||.....||....|||.....||.....||....|||.....||.....||");
        var mh = new User("mh", ".....||||...|||...|||.....||.....||....|||.....||.....||....|||.....||.....||");
        var jh = new User("jh", "...||||||...|||...|||.....||.....||....|||.....||.....||....|||.....||.....||");
        var jc = new User("jc", "|||||||||||||||||||||.....||.....||....|||.....||.....||....|||.....||.....||");
        var qh = new User("qh", ".....||||||||||...|||.....||.....||....|||.....||.....||....|||.....||.....||");
        //                                                           ^                    ^                    ^

        // ============================================================
        // PHASE 1: Skeleton & Deployment Infrastructure
        // ============================================================
        //
        // business/testing value:
        //  - deliver a configured and ready-to-use sample application, using JDK-17,
        //    and working on multiples schemas (datasources, DB users).
        //  - improved error messages. Proactivity regression.
        //  - Test clean install, upgrade; with security and not.
        //  - testing regression of OpenConnect (and Installer?)
        //

        var t0 = new Task('0', "[DevOps] new repo or add to OpenCONNECT? Jenkins, reports, coverage, etc.", 1);
        var t1 = new Task('1', "[MM] new MM project skeleton", 4);
        //        clean
        //        rename all packages, classes, properties, YAML, etc: kmdcs/kernel => mm/memon/messamon
        //        prepare the multi-DataSource infra: CONN & MM
        //        outbox table is needed? can be deactivated to don't create table in MM schema?
        //        prepare simple REST entrypoints that reads something from CONN & MM Datasources to test that all is working well.

        var t2 = new Task('2', "[Installer] new MM project: Installer", 4);
        //        create new Artifact to install the new FatJar, Windows Service, ...

        var t3 = new Task('3', "[Infra] Java11=>17", 6);
        //        - we assume that the OpenJDK17 is yet published in Kiosk? buildgenerator
        //                - Adapt Installer to Java17 (pom.xml properties)
        //                - Installer should update the JVM (use UpgradeableByTargetVersionArtifact?)
        //        - Installer should update the OpenCONNECT FatJar (ja es fa)
        //                - Adapt OpenConnect (pom.xml properties)
        //                - Libraries upgrade plan: change Spring major version as possible.

        var t4 = new Task('4', "[Infra] new MM Schema creation & User.MessagesTable", 4);
        //        - The new MM module will use its own schema (the official name is pending, but by the moment we can name it as "MM").
        //        - How a new Schema is created, and the affectation to our product (CachéPlugin, new .DAT file? , Installer, LISConfig, Update.xml...)
        //        - How the persistent classes package is configured (like CONN's User package)

        var t5 = new Task('5', "[Infra] New Iris MM user", 3, t4);
        //        Refine/investigate the creation of a new IRIS user (like the INVENTORYUSER). How to set highly restrictive permissions to a new MM schema
        //        Jasypt encryption of the password (same mechanism as OpenCONNECT, or we can improve something?). DDDToolkit has something to facilitate the pass encryption, or integrates with Jasypt...?
        //        Other implications: caché plugin, installer, LISConfig, Update.xml (Explore to include the upgrade in lab-hub-installer artifact), ...
        //        Gather information and document.

        var t6 = new Task('6', "[COS] [BET][Monitoring]Display & highlight field caused the error and its location on error description  v2", 7);
        //        explore how we can enrich the error message
        //        explore how we can highlight the field causing the error within the message body for HL7 and WS hosts (including 3rd party)
        //        check if there is any impact on the existing proactivity module
        //        standardized the error message like  "error description itself, reason/context, mitigation (check entity, field, value)".
        //                List of errors: https://docs.google.com/spreadsheets/d/1ehUdRLPog40tWjN_PxenWYpwdJQ667YVJZlDeoJDgbk/edit#gid=473892287

        var t7 = new Task('7', "[COS] unify/rename rest.RestTemplate and TLS Templates: DriverEngine => more javish, general name", 3);
        var t8 = new Task('8', "[UI] React pending testing, coverage and quality.", 20);
        var t9 = new Task('9', "[MM+Jasypt] new MM project: Jasypt with 2 Datasources", 6, t3, t4, t5, t1, t2);
        //        LISConfig, installer-commons, YAML, ...

        var t_ = new Task('_', "[MM] testing 2 REST entry points to check the 2 Datasources", 2, t1, t9);

        var mmPrototypeCheckPoint = new Task('*', "[Integration] MM prototype and infraestructure ready", 4,
                t0, t1, t2,
                t3, t4, t5, t6, t7, t8, t9, t_
        );

        // ============================================================
        // PHASE 2: MM
        // ============================================================
        //
        // business/testing value:
        //  - deliver the MM, UI & Backend
        //

        var tA = new Task('A', "[COS] MessageMigration from CONN to MM schemas (rutina Upgrade)", 4, t4);
        var tC = new Task('C', "[COS] MessageIngress/Egress & MM EventsDaemon", 10, t6, t7);
        var tD = new Task('D', "[MM] Message Domain Model", 1, mmPrototypeCheckPoint);
        var tE = new Task('E', "[MM] PUT - /messages/resend - Resend messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tF = new Task('F', "[MM] PUT - /messages/reopen - Reopen messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tG = new Task('G', "[MM] GET - /messages - Get messages list, paged and sorted, by search criteria", 5, mmPrototypeCheckPoint, tD);
        var tH = new Task('H', "[MM] GET - /messages/{uuid} - Get message detail by UUID", 5, mmPrototypeCheckPoint, tD);
        var tI = new Task('I', "[MM] DELETE - /messages/delete - Delete messages by UUID list + filters criteria", 5, mmPrototypeCheckPoint);
        var tJ = new Task('J', "[MM] POST - /events - Posts a batch of events", 8, mmPrototypeCheckPoint, tD);
        var tK = new Task('K', "[MM] MessageCleaner", 5, mmPrototypeCheckPoint, t4);
        var tL = new Task('L', "[MM] Timezones", 2, mmPrototypeCheckPoint, tG, tH);
        //        - General parameter to format date & time mapping
        //        - DB UTC-0 => local IRIS timezone

        var tB = new Task('B', "[UI] que el menú porti a nova MM, i mantenir l'antiga disponible mitjançant URL directa (persiaca en el futur).", 1, tG, tH);
        var tM = new Task('M', "[MM] Events integration: [COS] daemon+outbox pattern => [MM] events entrypoint", 3, tC, tD, tJ);
        var tN = new Task('N', "[Integration] Functional review, final SonarQ, final UTs, final Coverage. Metrics report.", 5,
                t1,
                tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL, tM);
        var tZ = new Task('Z', "[DOC] SAD Rationale", 3);

//        var gantt = new Ganttr(
//                new FinishAsapSolutionsProcessor(false),
//                List.of(/*mh,*/ jh, jc, pb, rc, qh),
//                List.of(
//                        t1, t2, t3, t4, t5, t6, t7,
//                        ta, tb, tc,
//                        mmPrototypeCheckPoint,
//                        tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL, tM, tN
//                )
//        );
//        gantt.gantterize();

        GeneticGanttr geneticGanttr = new GeneticGanttr(
                List.of(/*mh,*/ jh, jc, pb, rc, qh),
                List.of(
                        t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t_,
                        mmPrototypeCheckPoint,
                        tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL, tM, tN,
                        tZ
                )
        );

        //        generation #99
        //        jh: 022||||||224|||444|||55577||7ZZZ.||__..|||..IGG||GGGHH||HHHI|||IIINN||NNN..||
        //        jc: |||||||||||||||||||||11116||66666||6C**|||**CCC||CCCCC||CEEE|||EE...||.....||
        //        pb: 33333||||||||||||||||3AAA9||99999||A...|||..FFF||FFKKK||KK.B|||LL...||.....||
        //        rc: ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||....||.....||
        //        qh: 88888||||||||||888|||88888||88888||88..|||..DJJ||JJJJJ||JMMM|||.....||.....||
        //
        //        days to finish: 73
        //        score:          734

        //        generation #474
        //        jh: 088||||||888|||888|||88888||88888||88..|||...DE||EEEEI||IIII|||LLNNN||NN...||
        //        jc: |||||||||||||||||||||66666||66ZZZ||.__*|||***KJ||JJJJJ||JJKK|||KK...||.....||
        //        pb: 11112||||||||||||||||22277||7AAAA||....|||...FF||FFFHH||HHH.|||.....||.....||
        //        rc: ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||B...||.....||
        //        qh: 33333||||||||||344|||44555||99999||9CCC|||CCCCC||CCGGG||GGMM|||M....||.....||
        //
        //        days to finish: 72
        //        score:          725
        geneticGanttr.run(500, 100000);

        new MMGantter().drawGantt(
                List.of(
                        t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t_,
                        mmPrototypeCheckPoint,
                        tA, tB, tC, tD, tE, tF, tG, tH, tI, tJ, tK, tL, tM, tN,
                        tZ
                )
        );

    }

    protected void drawGantt(List<Task> tasks) {
//        var solution = new GanttSolution(users);
        var t = new GanttCombinationsIterator(tasks);

        var task = t.getNextTaskToProcess();
        while (task != null) {

            int taskStartingDay = 0;
            for (var dep : task.dependencies) {
                int depEndingDay = t.getProcessedTaskEndingDay(dep);
                if (taskStartingDay < depEndingDay) {
                    taskStartingDay = depEndingDay;
                }
            }

            int taskCombinationIndex = tasks.indexOf(task);
//            int userIndex = combination[taskCombinationIndex];
//            User userToExecuteTheTask = users.get(userIndex);
//            int taskEndingDay = solution.addTaskTo(userToExecuteTheTask, task, taskStartingDay);
//            t.markTaskAsProcessedEndingAtDay(task, taskEndingDay);
            t.markTaskAsProcessedEndingAtDay(task, taskStartingDay + task.durationInDays);

            for (int i = 0; i < taskStartingDay; i++) {
                System.out.print('.');
            }
            for (int i = 0; i < task.durationInDays; i++) {
                System.out.print(task.code);
            }
            System.out.println();

            task = t.getNextTaskToProcess();
        }
    }
}

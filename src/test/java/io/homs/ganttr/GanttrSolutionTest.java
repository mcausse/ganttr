package io.homs.ganttr;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GanttrSolutionTest {

    @Test
    void exploratory_test() {

        var mh = new User("mh", "..X..XX.X...XXXX...XX");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 2, t1);
        var t3 = new Task('3', "part (A)", 4, t2);
        var t4 = new Task('4', "part (B)", 5, t2);
        var t5 = new Task('5', "SAD", 1);

        var sut = new GanttSolution(List.of(mh));

        int day = 0;

        // Act
        day = sut.addTaskTo(mh, t1, day);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("11X1.XX.X...XXXX...XX");

        // Act: isUnassignedDay
        assertThat(sut.isUnassignedDay(mh, 0)).isFalse();
        assertThat(sut.isUnassignedDay(mh, 1)).isFalse();
        assertThat(sut.isUnassignedDay(mh, 2)).isFalse();
        assertThat(sut.isUnassignedDay(mh, 3)).isFalse();
        assertThat(sut.isUnassignedDay(mh, 4)).isTrue();
        assertThat(sut.isUnassignedDay(mh, 5)).isFalse();
        assertThat(sut.isUnassignedDay(mh, 100)).isTrue();

        // Act
        day = sut.addTaskTo(mh, t3, day);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("11X13XX3X33.XXXX...XX");

        // Act
        day = sut.addTaskTo(mh, t2, day);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("11X13XX3X332XXXX2..XX");

        // Act
        day = sut.addTaskTo(mh, t4, day);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("11X13XX3X332XXXX244XX444");

        assertThat(sut).hasToString("mh: 11X13XX3X332XXXX244XX444\n");
    }

    @Test
    void split_tasks_for_optimal_gap_filling() {

        var mh = new User("mh", ".....XX.....XX.....XX");

        var t1 = new Task('1', "entities & repositories", 3);
        var t2 = new Task('2', "service", 3, t1);
        var t3 = new Task('3', "part (A)", 7, t2);

        var sut = new GanttSolution(List.of(mh));

        // Act
        sut.addTaskTo(mh, t1, 0);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("111..XX.....XX.....XX");

        sut.addTaskTo(mh, t2, 10);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("111..XX...22XX2....XX");

        sut.addTaskTo(mh, t3, 0);
        System.out.println(sut.getSolutionForUser(mh));
        assertThat(sut.getSolutionForUser(mh)).isEqualTo("11133XX33322XX233..XX");
    }
}
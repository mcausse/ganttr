package io.homs.ganttr;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GanttTasksListTest {

    final Task t1 = new Task('1', "entities & repositories", 3);
    final Task t2 = new Task('2', "service", 2, t1);
    final Task t3 = new Task('3', "part (A)", 4, t2);
    final Task t4 = new Task('4', "part (B)", 5, t2);
    final Task t5 = new Task('5', "SAD", 1);

    private final GanttTasksList sut = new GanttTasksList(List.of(t1, t2, t3, t4, t5));

    @Test
    void getInitialTasks() {

        assertThat(sut.getInitialTasks()).hasToString("[Task{code=1}, Task{code=5}]");
    }

    @Test
    void getNextTasksOf() {

        assertThat(sut.getNextTasksOf(t1)).hasToString("[Task{code=2}]");
        assertThat(sut.getNextTasksOf(t2)).hasToString("[Task{code=3}, Task{code=4}]");
        assertThat(sut.getNextTasksOf(t3)).hasToString("[]");
        assertThat(sut.getNextTasksOf(t4)).hasToString("[]");
    }

    @Test
    void getNumTasks() {
        assertThat(sut.getNumTasks()).isEqualTo(5);
    }

    @Test
    void getIndexOfTask() {
        assertThat(sut.getIndexOfTask(t1)).isEqualTo(0);
        assertThat(sut.getIndexOfTask(t2)).isEqualTo(1);
    }
}
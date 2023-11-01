package io.homs.ganttr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GanttTasksIteratorTest {

    @Test
    void simple() {

        var t1 = new Task('1', "", 3);
        var t2 = new Task('2', "", 4);
        var t3 = new Task('3', "", 2, t1, t2);

        var sut = new GanttTasksIterator(List.of(t1, t2, t3));

        List<Task> processedTasks = new ArrayList<>();
        while (true) {
            var ta = sut.getNextTaskToProcess();
            if (ta == null) {
                break;
            }
            processedTasks.add(ta);
            sut.markTaskAsProcessedEndingAtDay(ta, 5);
        }

        assertThat(processedTasks).hasToString(
                "[Task{code=1}, Task{code=2}, Task{code=3}]"
        );
    }
}
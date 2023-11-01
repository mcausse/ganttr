package io.homs.ganttr;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CombinationsIteratorTest {

    @Test
    void CombinationsIterator_should() {

        //var sut = new CombinationsIterator(3, List.of(1, 2));
        var sut = CombinationsIterator.of(3, 1, 3);

        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[1, 1, 1]");
        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[2, 1, 1]");

        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[1, 2, 1]");
        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[2, 2, 1]");

        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[1, 1, 2]");
        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[2, 1, 2]");

        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[1, 2, 2]");
        assertThat(sut.hasNext()).isTrue();
        assertThat(Arrays.toString(sut.getNext())).isEqualTo("[2, 2, 2]");

        assertThat(sut.hasNext()).isFalse();
        assertThrows(ArrayIndexOutOfBoundsException.class, sut::getNext);
    }
}
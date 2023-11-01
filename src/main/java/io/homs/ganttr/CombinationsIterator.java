package io.homs.ganttr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationsIterator {

    private final int wordLength;
    private final List<Integer> symbols;

    private int[] state;

    public CombinationsIterator(int wordLength, List<Integer> symbols) {
        this.wordLength = wordLength;
        this.symbols = symbols;
    }

    public static CombinationsIterator of(int wordLength, int from, int to) {
        var symbols = new ArrayList<Integer>();
        for (int i = from; i < to; i++) {
            symbols.add(i);
        }
        return new CombinationsIterator(wordLength, symbols);
    }

    public int[] getNext() {
        if (state == null) {
            this.state = new int[wordLength];
            for (int i = 0; i < wordLength; i++) {
                this.state[i] = symbols.get(0);
            }
            return Arrays.copyOf(state, wordLength);
        } else {
            increment(0);
            return Arrays.copyOf(state, wordLength);
        }
    }

    public boolean hasNext() {
        if (this.state == null) {
            return true;
        }
        int lastSymbol = symbols.get(symbols.size() - 1);
        for (int i = 0; i < wordLength; i++) {
            if (state[i] != lastSymbol) {
                return true;
            }
        }
        return false;
    }

    protected void increment(int pos) {
        int digit = state[pos];
        int symbolPos = symbols.indexOf(digit);
        if (symbolPos == symbols.size() - 1) {
            // carry
            state[pos] = symbols.get(0);
            increment(pos + 1);
        } else {
            state[pos] = symbols.get(symbolPos + 1);
        }
    }
}
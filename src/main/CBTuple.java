package main;


import java.util.Arrays;

/**
 * Counting boolean tuple is a class that implements a fixed length int array.
 * Each entry represents the number of occurrences of a word in the n-th document,
 * but these can also be used as boolean values where truth is a non-zero value.
 * User must be careful to only mix tuples with equal size.
 */
public class CBTuple {
    private int array[];

    public int getSize() {
        return array.length;
    }
    public CBTuple(int nr) {
        array = new int[nr];
        Arrays.fill(array, 0);
    }

    // Create new tuple by negating a tuple
    public static CBTuple not(CBTuple tuple) {
        CBTuple negatedTuple = new CBTuple(tuple.getSize());

        for (int iter = 0; iter < tuple.getSize(); iter++) {
            negatedTuple.array[iter] = tuple.array[iter] != 0 ? 0 : 1;
        }

        return negatedTuple;
    }

    // Create new tuple by combining two tuples with the and operator
    public static CBTuple and(CBTuple tuple1, CBTuple tuple2) {
        CBTuple andTuple = new CBTuple(tuple1.getSize());

        for (int iter = 0; iter < tuple1.getSize(); iter++) {
            andTuple.array[iter] = Math.min(tuple1.array[iter], tuple2.array[iter]);
        }

        return andTuple;
    }

    // Create new tuple by combining two tuples with the or operator
    public static CBTuple or(CBTuple tuple1, CBTuple tuple2) {
        CBTuple orTuple = new CBTuple(tuple1.getSize());

        for (int iter = 0; iter < tuple1.getSize(); iter++) {
            orTuple.array[iter] = Math.max(tuple1.array[iter], tuple2.array[iter]);
        }

        return orTuple;
    }

    // Get the total number of items in the counting tuple
    public int getTotal() {
        int total = 0;

        for (int occurrenceNr : array) {
            total += occurrenceNr;
        }

        return total;
    }

    // Get number of non-zero entries in tuple
    public int getNonZero() {
        int nrNonZero = 0;
        for (int occurrenceNr : array) {
            if (occurrenceNr != 0) {
                nrNonZero += 1;
            }
        }

        return nrNonZero;
    }

    public void addOccurrence(int index) {
        array[index] += 1;
    }

    public int getAt(int index) {
        return  array[index];
    }
}

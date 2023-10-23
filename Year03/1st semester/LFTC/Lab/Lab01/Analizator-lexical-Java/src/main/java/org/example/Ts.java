package org.example;

import java.util.Arrays;

public class Ts {
    private final int SIZE = 1000;
    private final String[] hashTable = new String[SIZE];

    public Integer addAtom(String element) {
        int hashed = hashCode(element);

        while (hashTable[hashed] != null && !hashTable[hashed].isEmpty() && hashed < SIZE) {
            ++hashed;
        }

        if (hashed == SIZE) {
            return null;
        }

        hashTable[hashed] = element;

        return hashed;
    }

    public Integer findAtom(String element) {
        int hashed = hashCode(element);

        while (hashTable[hashed] != null && !hashTable[hashed].equals(element)) {
            ++hashed;
        }

        if (hashTable[hashed] == null) {
            return null;
        }

        return hashed;
    }

    private int hashCode(String value) {
        return value.charAt(0) % 11;
    }

    @Override
    public String toString() {
        return Arrays.toString(hashTable);
    }

    public String printMaxNotNull() {
        int lastPos = 0;
        for (int index = 0; index < hashTable.length; ++index) {
            if (hashTable[index] != null) {
                lastPos = index;
            }
        }

        StringBuilder result = new StringBuilder();
        String pattern = "cod TS: %d; atom: %s\n";
        for (int index = 0; index <= lastPos; ++index) {
            result.append(String.format(pattern, index, hashTable[index]));
        }

        return result.toString();
    }
}

package org.example;

import java.util.*;

class Pair {
    Character ch;
    String value;

    public Pair(Character ch, String value) {
        this.ch = ch;
        this.value = value;
    }
}

public class Gramatica {
    List<Character> terminale = new ArrayList<>();
    List<Character> neterminale = new ArrayList<>();
    List<Pair> rules = new ArrayList<>();

    public Gramatica() {
        for (char i = 'a'; i <= 'z'; ++i) {
            terminale.add(i);
        }

        for (char i = 'A'; i <= 'Z'; ++i) {
            neterminale.add(i);
        }
    }
}

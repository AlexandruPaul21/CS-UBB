package org.example;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Grammar grammar = new Grammar("S");
        grammar.readSimpleGrammarFromFile("src/main/resources/grammar.txt");

        if (grammar.isLeftRecursive()) {
            System.out.println("Invalid grammar! It should not be left-recursive");
            System.exit(1);
        }

        Analyzer analyzer = new Analyzer(grammar);

        if (grammar.isLeftRecursive()) {
            System.out.println("You can't use this method on this type of grammar");
            System.exit(1);
        }

        String expression = "ababeeer";
        String[] arrExpression = new String[expression.length()];
        for (int i = 0; i < expression.length(); ++i) {
            arrExpression[i] = String.valueOf(expression.charAt(i));
        }

        if (analyzer.matchExpression(arrExpression)) {
            System.out.println("The expression: " + expression + " is accepted by the grammar");
            analyzer.printProductionList();
        } else {
            System.out.println("The expression is not accepted by the grammar");
        }
    }
}

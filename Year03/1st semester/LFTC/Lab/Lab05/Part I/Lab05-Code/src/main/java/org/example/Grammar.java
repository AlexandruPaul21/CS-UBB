package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Grammar {
    private List<String> terminals = new ArrayList<>();
    private List<String> nonTerminals = new ArrayList<>();
    private final List<Rule> rules = new ArrayList<>();
    private final String startSymbol;

    public boolean isNonTerminal(String element) {
        return nonTerminals.contains(element);
    }

    public boolean isTerminal(String element) {
        return terminals.contains(element);
    }

    public Grammar(List<String> terminals, List<String> nonTerminals, String startSymbol) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
    }

    public void readSimpleGrammarFromFile(String path) throws FileNotFoundException {
        File obj = new File(path);
        Scanner reader = new Scanner(obj);

        while (reader.hasNextLine()) {
            String[] entities = reader.nextLine().split(":");

            String[] productions = new String[entities[1].length()];

            for (int i = 0; i < entities[1].length(); ++i) {
                productions[i] = String.valueOf(entities[1].charAt(i));
            }

            rules.add(new Rule(entities[0], productions));
        }
    }

    public Grammar(String startSymbol) {
        for (char i = 'A'; i <= 'Z'; ++i) {
            nonTerminals.add(String.valueOf(i));
            terminals.add(String.valueOf(i).toLowerCase());
        }
        this.startSymbol = startSymbol;
    }

    public void addRule(String key, String[] production) {
        rules.add(new Rule(key, production));
    }

    public boolean isLeftRecursive() {
        for (Rule rule : rules) {
            if (rule.productions[0].equals(rule.key)) {
                return true;
            }
        }

        return false;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(List<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public String getStartSymbol() {
        return startSymbol;
    }
}

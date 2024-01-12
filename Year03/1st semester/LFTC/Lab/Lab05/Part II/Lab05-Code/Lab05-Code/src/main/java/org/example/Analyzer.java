package org.example;

import java.util.*;

public class Analyzer {
    private final Grammar grammar;
    private char state;
    private int step;
    private final Stack<String> alpha = new Stack<>();
    private final Stack<String> beta = new Stack<>();
    private final Map<String, Rule> numberedRules = new HashMap<>();

    public Analyzer(Grammar grammar) {
        this.grammar = grammar;
        List<Rule> rules = grammar.getRules();
        Map<String, Integer> ruleCount = new HashMap<>();
        for (Rule rule : rules) {
            Integer actCount = ruleCount.get(rule.key);

            if (actCount == null) {
                ruleCount.put(rule.key, 1);
                actCount = 1;
            }

            numberedRules.put(rule.key + "_" + actCount, rule);

            ruleCount.put(rule.key, actCount + 1);
        }

        ruleCount.clear();
    }

    public boolean matchExpression(String[] expression) {
        initAnalyzer();

        while (state != 't' && state != 'e') {
            if (state == 'q') {
                if (beta.empty()) {
                    if (step == expression.length + 1) state = 't';
                    else state = 'r';
                } else {
                    if (grammar.isNonTerminal(beta.peek())) {
                        String element = beta.pop();
                        Rule rule = numberedRules.get(element + "_1");

                        alpha.push(rule.key + "_1");

                        addProductionsToStack(rule);
                    } else {
                        if (grammar.isTerminal(beta.peek()) && (expression.length >= step && beta.peek().equals(expression[step - 1]))) {
                            step += 1;
                            alpha.push(beta.pop());
                        } else {
                            state = 'r';
                        }
                    }
                }
            } else if (state == 'r') {
                if (alpha.isEmpty()) {
                    state = 'e';
                    continue;
                }
                if (grammar.isTerminal(alpha.peek())) {
                    step -= 1;
                    beta.push(alpha.pop());
                } else {
                    String lastSymbol = alpha.peek();
                    String key = lastSymbol.split("_")[0];
                    int ruleNo = Integer.parseInt(lastSymbol.split("_")[1]) + 1;

                    Rule rule = numberedRules.get(key + "_" + ruleNo);

                    if (rule != null) {
                        state = 'q';
                        alpha.pop();
                        alpha.push(key + "_" + ruleNo);
                        removeFromInStack(lastSymbol);
                        addProductionsToStack(rule);
                    } else {
                        if (step == 1 && beta.peek().equals(grammar.getStartSymbol())) {
                            state = 'e';
                        } else {
                            String ruleIdentifier = alpha.pop();
                            removeFromInStack(ruleIdentifier);
                            beta.push(ruleIdentifier.split("_")[0]);
                        }
                    }
                }
            }
        }

        return state != 'e';
    }

    public void printProductionList() {
        System.out.println("Internal convention on rules: ");
        for (String key : numberedRules.keySet()) {
            System.out.println(prettyShow(numberedRules.get(key)) + "-> (" + key + ")");
        }

        for (String s : alpha) {
            if (!grammar.isTerminal(s)) {
                System.out.print(s + " ");
            }
        }
    }

    private String prettyShow(Rule rule) {
        return rule.key + ":" + Arrays.toString(rule.productions);
    }

    private void removeFromInStack(String lastSymbol) {
        Rule rule = numberedRules.get(lastSymbol);

        int repeat = rule.productions.length;

        while (repeat != 0) {
            beta.pop();
            --repeat;
        }
    }

    private void addProductionsToStack(Rule rule) {
        String[] productions = rule.productions;
        for (int i = productions.length - 1; i >= 0; --i) {
            beta.push(productions[i]);
        }
    }

    private void initAnalyzer() {
        state = 'q';
        step = 1;
        alpha.clear();
        beta.push(grammar.getStartSymbol());
    }
}

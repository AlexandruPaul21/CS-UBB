package org.example;

import java.util.*;

public class FiniteStateMachine {
    private Set<String> states;
    private Set<String> alphabet;
    private List<Transition> transitions;
    private String initialState;
    private Set<String> finalStates;

    private Set<String> letters = new HashSet<>();
    private Set<String> digits = new HashSet<>();

    public FiniteStateMachine(Set<String> states, Set<String> alphabet, List<Transition> transitions, String initialState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;

        Collections.addAll(letters, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        Collections.addAll(digits, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    }

    private String matchExpression(String sequence) {
        StringBuilder result = new StringBuilder();
        String actualState = initialState;

        for (int index = 0; index < sequence.length(); ++index) {
            String finalActualState = actualState;
            List<Transition> validTransitions = transitions.stream().filter(transition -> transition.getInitialState().equals(finalActualState)).toList();

            int finalIndex = index;
            List<Transition> validTransition = validTransitions.stream().filter(transition -> Objects.equals(transition.getValue(), String.valueOf(sequence.charAt(finalIndex)))).toList();

            if (validTransition.size() != 1) {
                return result.toString();
            }
            result.append(sequence.charAt(index));

            actualState = validTransition.get(0).getFinalState();
        }

        String finalActualState1 = actualState;
        if (finalStates.stream().anyMatch(state -> state.equals(finalActualState1))) {
            return result.toString();
        } else {
            return null;
        }
    }

    public boolean checkCompliance(String sequence) {
        return Objects.equals(matchExpression(sequence), sequence);
    }

    public String checkOffset(String sequence) {
        String result = "";
        StringBuilder temporary = new StringBuilder();

        String actualState = initialState;
        for (int index = 0; index < sequence.length(); ++index) {
            String finalActualState = actualState;
            List<Transition> validTransitions = transitions.stream().filter(transition -> transition.getInitialState().equals(finalActualState)).toList();

            int finalIndex = index;
            List<Transition> validTransition = validTransitions.stream().filter(transition -> Objects.equals(transition.getValue(), String.valueOf(sequence.charAt(finalIndex)))).toList();

            if (validTransition.size() != 1) {
                return result;
            }
            temporary.append(sequence.charAt(index));

            actualState = validTransition.get(0).getFinalState();

            String finalActualState1 = actualState;
            if (finalStates.stream().anyMatch(state -> state.equals(finalActualState1))) {
                result = temporary.toString();
            }
        }

        return result;
    }

    public boolean isNotDeterminist() {
        return transitions.stream().anyMatch(transition -> transitions.stream().anyMatch(transition1 -> transition1.getInitialState().equals(transition.getInitialState()) &&
               transition1.getValue().equals(transition.getValue()) &&
               !transition1.getFinalState().equals(transition.getFinalState())));
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    @Override
    public String toString() {
        return "FiniteStateMachine{" +
                "states=" + states +
                ", alphabet=" + alphabet +
                ", transitions=" + transitions +
                ", initialState='" + initialState + '\'' +
                ", finalStates=" + finalStates +
                ", letters=" + letters +
                ", digits=" + digits +
                '}';
    }
}

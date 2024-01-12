package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;


import java.io.FileReader;
import java.util.*;

// assumption: the automata is valid and deterministic
public class FiniteAutomata {
    private final String filePath;
    private String initialState;
    private String alphabet;
    private final List<String> states, finalStates;

    private final Map<String, Map<Character, String>> transitions;

    public FiniteAutomata(String filePath) {
        this.filePath = filePath;
        this.states = new ArrayList<>();
        this.finalStates =  new ArrayList<>();
        this.transitions = new HashMap<>();
        this.load();
    }

    private void load() {
        try {
            // Create a FileReader object
            FileReader fileReader = new FileReader(filePath);

            // Create a JSONParser object
            JSONParser jsonParser = new JSONParser();

            // Parse the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            // Access different parts of the JSON structure
            JSONArray statesArray = (JSONArray) jsonObject.get("states");
            for (Object state : statesArray) {
                states.add(String.valueOf(state));
            }

            initialState = (String) jsonObject.get("initial_state");

            JSONArray finalStatesArray = (JSONArray) jsonObject.get("final_states");
            for (Object state : finalStatesArray) {
                finalStates.add(String.valueOf(state));
            }

            alphabet = (String) jsonObject.get("alphabet");

            JSONArray transitionsArray = (JSONArray) jsonObject.get("transitions");
            for (Object transition : transitionsArray) {
                JSONObject t = (JSONObject) transition;

                String from = (String) t.get("from");
                String to = (String) t.get("to");
                String letters = (String) t.get("letters");

                // Process the transition data
                if (!transitions.containsKey(from)) {
                    transitions.put(from, new HashMap<>());
                }

                for (char letter : letters.toCharArray()) {
                    transitions.get(from).put(letter, to);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getInitialState() {
        return initialState;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public Map<String, Map<Character, String>> getTransitions() {
        return transitions;
    }

    public String longestAcceptedPrefix(String seq) {
        String state = getInitialState();
        String current = "";
        String lastAccepted = null;
        if (finalStates.contains(state)) {
            lastAccepted = "";
        }
        for (char ch : seq.toCharArray()) {
            if (transitions.containsKey(state) && transitions.get(state).containsKey(ch)) {
                current += ch;
                state = transitions.get(state).get(ch);
                if (finalStates.contains(state)) {
                    lastAccepted = current;
                }
            } else {
                return lastAccepted;
            }
        }
        return lastAccepted;
    }

    @Test
    public void test() {
        FiniteAutomata fa = new FiniteAutomata("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/symbols.json");

        assert (fa.longestAcceptedPrefix("01") == null);
        assert (Objects.equals(fa.longestAcceptedPrefix("int"), "int"));
        assert (Objects.equals(fa.longestAcceptedPrefix("main"), "main"));
        assert (Objects.equals(fa.longestAcceptedPrefix("return"), "return"));
        assert (Objects.equals(fa.longestAcceptedPrefix("float"), "float"));
        assert (Objects.equals(fa.longestAcceptedPrefix("{"), "{"));
        assert (Objects.equals(fa.longestAcceptedPrefix("}"), "}"));
        assert (Objects.equals(fa.longestAcceptedPrefix("("), "("));
        assert (Objects.equals(fa.longestAcceptedPrefix(")"), ")"));
        assert (Objects.equals(fa.longestAcceptedPrefix("while"), "while"));
        assert (Objects.equals(fa.longestAcceptedPrefix("if"), "if"));
        assert (Objects.equals(fa.longestAcceptedPrefix("else"), "else"));
        assert (Objects.equals(fa.longestAcceptedPrefix(">"), ">"));
        assert (Objects.equals(fa.longestAcceptedPrefix("if(a<-b)"), "if"));
        assert (Objects.equals(fa.longestAcceptedPrefix("+"), "+"));

        FiniteAutomata fa1 = new FiniteAutomata("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/const.json");
        assert (Objects.equals(fa1.longestAcceptedPrefix("01"), "0"));
        assert (Objects.equals(fa1.longestAcceptedPrefix("3.14"), "3.14"));

        System.out.println("Tests passed");
    }
}



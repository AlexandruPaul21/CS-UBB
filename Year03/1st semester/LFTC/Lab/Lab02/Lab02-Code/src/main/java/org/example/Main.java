package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
/*
* Structura in EBNF
*
* automat_finit = lista_stari, stare_initiala, stari_finale, tranzitii.
* lista_stari & stari_finale = stare {" " , stare}.
* stare_initiala = stare
* tranzitii = tranzitie { "\n", tranzitie }
* stare = q,CONST
* tranzitie = stare CONST stare
* CONST = digit{digit}
* digit = [0-9]
*
* */
public class Main {

    private static FiniteStateMachine af;

    private static void readFile(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Set<String> states = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());
            String initialState = bufferedReader.readLine();
            Set<String> finalStates = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());
            Set<String> alphabet = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());

            List<Transition> transitions = new ArrayList<>();
            String transition = bufferedReader.readLine();
            while (transition != null) {
                String[] elements = transition.split(" ");
                transitions.add(new Transition(elements[0], elements[2], elements[1]));

                transition = bufferedReader.readLine();
            }

            af = new FiniteStateMachine(states, alphabet, transitions, initialState, finalStates);

            System.out.println("Finite state machine successfully read from file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readConsole() {
        Scanner scanner = new Scanner(System.in);

        Set<String> states = new HashSet<>();
        System.out.println("Give the possible states: ");
        Collections.addAll(states, scanner.nextLine().split(" "));

        System.out.println("Give the initial state");
        String initialState = scanner.nextLine();

        Set<String> finalStates = new HashSet<>();
        System.out.println("Give the final states: ");
        Collections.addAll(finalStates, scanner.nextLine().split(" "));

        Set<String> alphabet = new HashSet<>();
        System.out.println("Give the alphabet: ");
        Collections.addAll(alphabet, scanner.nextLine().split(" "));

        System.out.println("Introduce the transition and than enter");
        List<Transition> transitions = new ArrayList<>();
        String transition = scanner.nextLine();
        while (transition != null && !transition.isEmpty()) {
            String[] elements = transition.split(" ");
            transitions.add(new Transition(elements[0], elements[2], elements[1]));

            transition = scanner.nextLine();
        }

        af = new FiniteStateMachine(states, alphabet, transitions, initialState, finalStates);

        System.out.println("Finite state machine successfully read from console");
    }

    public static void main(String[] args) {
        System.out.println("--------------------Welcome-------------------");
        System.out.println("How do you want to input the state machine?");
        System.out.println("\t1. File");
        System.out.println("\t2. Console");
        System.out.println("\t3. Exit");

        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        switch (command) {
            case "1" -> readFile("src/main/resources/input.txt");
            case "2" -> readConsole();
            case "3" -> {
                System.out.println("Goodbye!");
                return;
            }
            default -> {
                System.out.println("Error! Exiting......");
                return;
            }
        }

        boolean done = false;

        while (!done) {
            System.out.println("----------Functionalities------------------");
            System.out.println("\t1. States");
            System.out.println("\t2. Input alphabet");
            System.out.println("\t3. Transitions");
            System.out.println("\t4. Final states");
            System.out.println("\t5. Check the compliance of a sequence");
            System.out.println("\t6. Determine the longest compliant offset");
            System.out.println("\t0. Exit");

            command = scanner.nextLine();
            switch (command) {
                case "1" -> System.out.println(af.getStates());
                case "2" -> System.out.println(af.getAlphabet());
                case "3" -> af.getTransitions().forEach(System.out::println);
                case "4" -> af.getFinalStates().forEach(System.out::println);
                case "5" -> {
                    if (af.isNotDeterminist()) {
                        System.out.println("The FSM is not determinist. This option is not available!");
                        break;
                    }

                    System.out.println("Introduce the sequence: ");
                    String sequence = scanner.nextLine();
                    if (af.checkCompliance(sequence)) {
                        System.out.println("The sequence is compliant");
                    } else {
                        System.out.println("The sequence is not compliant");
                    }
                }
                case "6" -> {
                    if (af.isNotDeterminist()) {
                        System.out.println("The FSM is not determinist. This option is not available!");
                        break;
                    }

                    System.out.println("Introduce the sequence: ");
                    String sequence = scanner.nextLine();
                    String result = af.checkOffset(sequence);
                    System.out.println(result == null ? sequence : result);
                }
                case "0" -> {
                    System.out.println("Goodbye!");
                    done = true;
                }
                default -> {
                    System.out.println("Error! Exiting......");
                    return;
                }
            }
        }
    }
}

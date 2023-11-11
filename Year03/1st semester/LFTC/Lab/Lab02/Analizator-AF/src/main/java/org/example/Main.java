package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Integer> atoms;
    private static final List<FipElement> fip = new ArrayList<>();
    private static final Ts ts = new Ts();

    private static FiniteStateMachine readFromFile(String file) {
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

            return new FiniteStateMachine(states, alphabet, transitions, initialState, finalStates);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        FiniteStateMachine afId = readFromFile("src/main/resources/machines/identifiers.txt");
        FiniteStateMachine afInt = readFromFile("src/main/resources/machines/integers.txt");
        FiniteStateMachine afReal = readFromFile("src/main/resources/machines/real-numbers.txt");

        File program = new File("src/main/resources/program.txt");
        Scanner reader = new Scanner(program);

        initAtomsMap();

        for (int lineNumber = 1; reader.hasNextLine(); ++lineNumber) {
            String line = reader.nextLine();
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            boolean error = false;
            boolean finished = false;
            while (!finished) {
                String element = afReal.getOffset(line);

                FipElement fipElement;

                if (!element.isEmpty()) {
                    Integer pos = ts.findAtom(element);
                    fipElement = new FipElement(element, atoms.get("CONST"), (pos == null) ? ts.addAtom(element) : pos);
                    fip.add(fipElement);
                } else {
                    element = afInt.getOffset(line);
                    if (!element.isEmpty()) {
                        Integer pos = ts.findAtom(element);
                        fipElement = new FipElement(element, atoms.get("CONST"), (pos == null) ? ts.addAtom(element) : pos);
                        fip.add(fipElement);
                    } else {
                        element = afId.getOffset(line);
                        if (!element.isEmpty()) {
                            if (atoms.get(element) != null) {
                                fipElement = new FipElement(element, atoms.get(element), -1);
                                fip.add(fipElement);
                            } else {
                                Integer pos = ts.findAtom(element);
                                fipElement = new FipElement(element, atoms.get("ID"), (pos == null) ? ts.addAtom(element) : pos);
                                fip.add(fipElement);
                            }
                        } else {
                            System.out.println("An error occurred on line: " + lineNumber);
                            error = true;
                        }
                    }
                }

                if (error) {
                    break;
                }

                line = line.substring(element.length());
                line = line.trim();

                finished = line.isEmpty();
            }

            if (error) {
                break;
            }
        }

        System.out.println("--------------- Forma interna a progamului ------------------");
        AtomicInteger index = new AtomicInteger();
        String pattern = "Index: %d; cod atom: %d; cod in TS: %d\n";
        fip.forEach((fipElement -> {
            System.out.printf(pattern, index.get(), fipElement.getCodAtom(), fipElement.getCodInTs());
            index.addAndGet(1);
        }));

        System.out.println("--------------- Tabela de simboluri -------------------------");
        System.out.println(ts.printMaxNotNull());

        reader.close();
    }

    private static void initAtomsMap() {
        atoms = new HashMap<>();
        atoms.put("ID", 0);
        atoms.put("CONST", 1);
        atoms.put("#include<iostream>", 2);
        atoms.put("struct", 3);
        atoms.put("main", 4);
        atoms.put("using", 5);
        atoms.put("namespace", 6);
        atoms.put("std", 7);
        atoms.put("return", 8);
        atoms.put("cin", 9);
        atoms.put("cout", 10);
        atoms.put("if", 11);
        atoms.put("int", 12);
        atoms.put("char", 13);
        atoms.put("float", 14);
        atoms.put(";", 15);
        atoms.put(",", 16);
        atoms.put("[", 17);
        atoms.put("]", 18);
        atoms.put("{", 19);
        atoms.put("}", 20);
        atoms.put("-", 21);
        atoms.put("+", 22);
        atoms.put("/", 23);
        atoms.put("*", 24);
        atoms.put("=", 25);
        atoms.put(">>", 26);
        atoms.put("<<", 27);
        atoms.put(">", 28);
        atoms.put("<", 29);
        atoms.put(">=", 30);
        atoms.put("<=", 31);
        atoms.put("==", 32);
        atoms.put("!=", 33);
        atoms.put("#", 34);
        atoms.put("(", 35);
        atoms.put(")", 36);
        atoms.put("include", 37);
        atoms.put("iostream", 38);
    }

    private static boolean classify(String line, int lineNumber) {
        line = line.trim();
        if (Objects.equals(line, "")) {
            return true;
        }
        String[] elements = line.split(" ");

        for (String element : elements) {
            Atom atom = new Atom(element);

            if (atom.isKeyword()) {
                elementPrint(atom + " - KEYWORD");
            } else if (atom.isBoolOperator()) {
                elementPrint(atom + " - BOOLEAN OPERATOR");
            } else if (atom.isOperator()) {
                elementPrint(atom + " - OPERATOR");
            } else if (atom.isSeparator()) {
                elementPrint(atom + " - SEPARATOR");
            } else if (atom.isId()) {
                elementPrint(atom + " - ID");
            } else if (atom.isConst()) {
                elementPrint(atom + " - CONST");
            } else {
                System.out.println("Error on line " + lineNumber + ". Aborting!");
                return false;
            }
        }

        return true;
    }

    private static void elementPrint(String s) {
        System.out.println(s);
    }

    private static boolean isId(String atom) {
        return atom.matches("^[a-z][a-z0-9]{0,7}");
    }

    private static boolean isConst(String atom) {
        return atom.matches("^[0-9]\\.*[0-9]*");
    }
}

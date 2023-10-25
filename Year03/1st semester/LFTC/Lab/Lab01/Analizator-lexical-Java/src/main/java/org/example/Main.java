package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Map<String, Integer> atoms;
    private static final List<FipElement> fip = new ArrayList<>();
    private static final Ts ts = new Ts();

    public static void main(String[] args) throws FileNotFoundException {
        File program = new File("src/main/resources/program.txt");
        Scanner reader = new Scanner(program);

        initAtomsMap();

        for (int lineNumber = 1; reader.hasNextLine(); ++lineNumber) {
            String line = reader.nextLine();
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] elements = line.split(" ");

            boolean done = false;

            for (String element : elements) {
                FipElement fipElement;
                if (isConst(element)) {
                    Integer pos = ts.findAtom(element);
                    fipElement = new FipElement(element, atoms.get("CONST"), (pos == null) ? ts.addAtom(element) : pos);
                } else if (atoms.get(element) != null) {
                    fipElement = new FipElement(element, atoms.get(element), -1);
                } else if (isId(element)) {
                    Integer pos = ts.findAtom(element);
                    fipElement = new FipElement(element, atoms.get("ID"), (pos == null) ? ts.addAtom(element) : pos);
                } else {
                    System.out.println("An error occurred on line " + lineNumber);
                    done = true;
                    break;
                }
                fip.add(fipElement);
            }

            if (done) {
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
        atoms.put("main()", 4);
        atoms.put("using", 5);
        atoms.put("namespace", 6);
        atoms.put("std", 7);
        atoms.put("return", 8);
        atoms.put("cin>>", 9);
        atoms.put("cout<<", 10);
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

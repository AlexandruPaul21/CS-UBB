package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File program = new File("src/main/resources/program.txt");
        Scanner reader = new Scanner(program);

        for (int lineNumber = 1; reader.hasNextLine(); ++lineNumber) {
            String line = reader.nextLine();
            if (!process(line, lineNumber)) {
                return;
            }
        }

        reader.close();
    }

    private static boolean process(String line, int lineNumber) {
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
}

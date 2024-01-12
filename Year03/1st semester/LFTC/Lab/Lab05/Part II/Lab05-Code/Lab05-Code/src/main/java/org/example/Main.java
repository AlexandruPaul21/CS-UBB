package org.example;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        //String filePath = "/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/main.cpp";
        String filePath = "/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/perim_area_circle.cpp";


        Parser parser = new Parser(filePath);
        parser.run();
        Grammar grammar = new Grammar("PROGRAM");
        grammar.readComplexGrammarFromFile("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/grammar.txt");


        System.out.println(grammar.getTerminals());

        System.out.println(grammar.getNonTerminals());


        Analyzer analyzer = new Analyzer(grammar);

        if (grammar.isLeftRecursive()) {
            System.out.println("You can't use this method on this type of grammar");
            System.exit(1);
        }


        // Read the content of the file as a single string
        String filename = filePath.substring(0, filePath.length() - 4);
        String filePathFIP = filename + "_fip.txt";

        String[] content = Files.readString(Paths.get(filePathFIP)).split(" ");

        analyzer.printProductionList();

        if (analyzer.matchExpression(content)) {
            System.out.println("The expression: is accepted by the grammar");
            analyzer.printProductionList();
        } else {
            System.out.println("The expression is not accepted by the grammar");
        }
    }
}

package org.example;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Parser {
    private final String filePath;
    private final String filePathFIP;

    private final String filePathIdTable;

    private final String filePathConstTable;

    private String outputFileString = "";

    private final Hashtable<String, Integer> idTable;
    private final Hashtable<String, Integer> constTable;
    private final Hashtable<String, String> symbTable;

    private final FiniteAutomata symbFa, idFa, constFa;
    private int count = 0;


    public Parser(String filePath) {
        this.filePath = filePath;

        String filename = filePath.substring(0, filePath.length() - 4);

        this.filePathFIP = filename + "_fip.txt";
        this.filePathIdTable = filename + "_id.txt";
        this.filePathConstTable = filename + "_const.txt";

        this.idTable = new Hashtable<>();
        this.constTable = new Hashtable<>();

        this.symbTable = new Hashtable<>();
        populateSymbTable();

        symbFa = new FiniteAutomata("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/symbols.json");
        idFa = new FiniteAutomata("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/id.json");
        constFa = new FiniteAutomata("/Users/dianastancu/Desktop/lftc/lab5/Lab05-Code/src/main/resources/const.json");
    }

    private void populateSymbTable() {
        // hardcoded at the moment
        // "alphabet": "intfloatmain(){}return;+-*/%<>=!ifelsewhilecincout",
        symbTable.put("int", "int");
        symbTable.put("id", "id");
        symbTable.put("const", "const");
        symbTable.put("float", "float");
        symbTable.put("main", "main");
        symbTable.put("if", "if");
        symbTable.put("else", "else");
        symbTable.put("while", "while");
        symbTable.put("cin", "cin");
        symbTable.put("return", "return");
        symbTable.put("cout", "cout");
        symbTable.put("+", "add");
        symbTable.put("-", "subtract");
        symbTable.put("/", "divide");
        symbTable.put("%", "modulo");
        symbTable.put("*", "multiply");
        symbTable.put("<", "lt");
        symbTable.put(">", "gt");
        symbTable.put("<=", "le");
        symbTable.put(">=", "ge");
        symbTable.put("=", "assign");
        symbTable.put("==", "equal");
        symbTable.put("!", "not");
        symbTable.put("(", "lparen");
        symbTable.put(")", "rparen");
        symbTable.put("{", "lbrace");
        symbTable.put("}", "rbrace");
        symbTable.put(";", "semicolon");
    }

    private void addId(String id) {
        if (!idTable.containsKey(id)) {
            idTable.put(id, count);
            count++;
        }
        outputFileString +=  symbTable.get("id") + " ";// + (count - 1) + "\n";
    }

    private void addConst(String cst) {
        if (!constTable.containsKey(cst)) {
            constTable.put(cst, count);
            count++;
        }
        outputFileString += symbTable.get("const") + " ";// + (count - 1) + "\n";
    }

    private void addSymb(String symb) {
        if (symbTable.containsKey(symb)) {
            outputFileString += symbTable.get(symb) + " ";// + "\n";
        }
    }

    private void parse() throws FileNotFoundException {
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        int noLine = 1;
        while (input.hasNextLine()){
            String line = input.nextLine();
            while (line != "") {
                String symb = symbFa.longestAcceptedPrefix(line);
                String id = idFa.longestAcceptedPrefix(line);
                String cnst = constFa.longestAcceptedPrefix(line);

                if (Objects.equals(symb, id)) {
                    id = null;
                }
                if (id != null && !id.isEmpty()) {
                    addId(id);
                    line = line.substring(id.length());
                } else if (symb != null && !symb.isEmpty()) {
                    addSymb(symb);
                    line = line.substring(symb.length());
                } else if (cnst != null && !cnst.isEmpty()) {
                    addConst(cnst);
                    line = line.substring(cnst.length());
                } else if (line.charAt(0) == ' ') {
                    line = line.substring(1);
                } else {
                    throw new Error("Error on line " + noLine + " here: " + "'" + line + "'");
                }
            }
            ++noLine;
        }
        input.close();
    }

    private void writeTables() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathFIP))) {
            // Write content to the file
            writer.write(outputFileString);
            System.out.println(outputFileString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathConstTable))) {
            // Write content to the file
            for (Map.Entry<String, Integer> entry : constTable.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                writer.write(key + " " + value + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathIdTable))) {
            // Write content to the file
            for (Map.Entry<String, Integer> entry : idTable.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                writer.write(key + " " + value + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            parse();
            writeTables();
        } catch (FileNotFoundException e) {
            System.out.println("atata s-a putut");
        }

    }
}

package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static final Gramatica gramatica = new Gramatica();

    public static void main(String[] args) throws FileNotFoundException {
        File obj = new File("src/main/resources/rules.txt" );
        Scanner scanner = new Scanner(obj);

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(":");

            gramatica.rules.add(new Pair(data[0].charAt(0), data[1]));
        }

        for (Pair key : gramatica.rules) {
            if (key.value.contains(key.ch.toString())) {
                System.out.println(key.ch + ":" + key.value);
            }
        }
    }
}

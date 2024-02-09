package org.example.utils;

import org.example.Music;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileGenerator {
    static String format = "File_%d";
    static List<Music> music = new ArrayList<>();
    static final String[] departments = new String[]{"clasic", "rock", "pop", "populara"};
    static final String[] authors = new String[]{"Mozart", "Bethoween", "Guta", "Minune"};
    static Random random = new Random();
    
    public static void generateData() throws IOException {
        int files = 8;
        for (int i = 0; i < files; ++i) {
            generateEntities();
            String file = String.format(format, i);

            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + file));
            for (Music elem : music) {
                bw.write(elem.toString());
                bw.newLine();
            }
            bw.flush();
        }
    }

    private static String generateName() {
        int length = random.nextInt() % 3 + 5;
        
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            res.append((char) ((Math.abs(random.nextInt()) % 24) + 97));
        }
        
        return res.toString();
    }
    
    private static void generateEntities() {
        music.clear();
        int number = 100;
        
        for (int i = 0; i < number; ++i) {
            music.add(new Music(
                    generateName(),
                    authors[Math.abs(random.nextInt()) % 4],
                    departments[Math.abs(random.nextInt()) % 4]
            ));
        }
    }
}

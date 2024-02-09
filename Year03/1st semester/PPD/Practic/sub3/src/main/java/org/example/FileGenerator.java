package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
    static String format = "File_%d";
    static List<Integer> lst = new ArrayList<>();
    
    public static void generateData() throws IOException {
        int files = 8;
        for (int i = 0; i < files; ++i) {
            generateEntities();
            String file = String.format(format, i);
            
            // TODO write according to format
            for (Integer elem : lst) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                
                bw.write(elem);
            }
        }
    }

    private static void generateEntities() {
        lst.clear();
        int number = 100;
        
        for (int i = 0; i < number; ++i) {
            // TODO generate right entities
            lst.add(2);
        }
    }
}

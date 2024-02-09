package org.example.utils;

import org.example.Consult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
    static String format = "File_%d";
    static List<Consult> lst = new ArrayList<>();
    
    public static void generateData() throws IOException {
        int files = 8;
        for (int i = 0; i < files; ++i) {
            generateEntities();
            String file = String.format(format, i);

            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + file));
            for (Consult elem : lst) {
                bw.write(prettyFormat(elem));
                bw.newLine();
            }
            
            bw.flush();
            bw.close();
        }
    }
    
    private static String prettyFormat(Consult elem) {
        // TODO make a beauty out of this
        return "";
    }

    private static void generateEntities() {
        lst.clear();
        int number = 100;
        
        for (int i = 0; i < number; ++i) {
            // TODO generate right entities
        }
    }
}

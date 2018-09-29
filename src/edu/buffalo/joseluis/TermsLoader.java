package edu.buffalo.joseluis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TermsLoader {

    public static List<String[]> load(String file) {
        List<String[]> terms = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while (( line = br.readLine() ) != null ){
                terms.add(line.split("\\s+"));
            }

        } catch (IOException e) {
            System.err.println("Error accessing input file!");
        }


        return terms;
    }

}

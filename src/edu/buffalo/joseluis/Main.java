package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";
        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);

        String outputFile = "output.txt";
        String inputFile = "input.txt";

        List<String[]> terms = TermsLoader.load(inputFile);

        QueryRunner runner = new PostinListQuery(invertedIndex);
        List<String> queryTerm = new ArrayList<>();
        for(String[] termArray : terms) {
            for(String term : termArray){
                queryTerm.add(term);
                runner.Query(queryTerm);
                queryTerm.clear();
            }

        }







    }
}

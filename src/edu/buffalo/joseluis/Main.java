package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";
        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);

        String outputFile = "output.txt";
        String inputFile = "input.txt";

        List<LocalTerms> terms = TermsLoader.load(inputFile);

        PostingList runner = new PostingList(invertedIndex);

        for(LocalTerms term : terms){
            Map<String, LinkedList<Integer>> postingList = runner.get(term);
            for(String t : postingList.keySet())
                System.out.println(postingList.get(t).size());
            System.out.println("------------");
            //LogPrinter.printPostingList(postingList);


            TermAtTimeAndQuery.query(postingList);
            //TermAtTimeOrQuery.query(postingList);
            //DocumentAtTimeAndQuery.query(postingList);
            //DocumentAtTimeOrQuery.query(postingList);

        }


    }
}


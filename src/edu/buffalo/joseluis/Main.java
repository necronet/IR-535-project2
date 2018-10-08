package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";
        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);


        /*for(IndexedTerm invertedIndexSet : invertedIndex.getIndex().keySet()) {
            boolean sorted = invertedIndex.getIndex().get(invertedIndexSet)
                    .stream()
                    .sorted()
                    .collect(Collectors.toList()).equals(invertedIndex.getIndex().get(invertedIndexSet));
            if(!sorted) {
                System.err.println("Not sorted properly");
                System.exit(1);
            }
        }*/

        String outputFile = "output.txt";
        String inputFile = "input.txt";

        List<LocalTerms> terms = TermsLoader.load(inputFile);

        PostingList runner = new PostingList(invertedIndex);


        for(LocalTerms term : terms){

            Map<String, LinkedList<Integer>> postingList = runner.get(term);
            LogPrinter.printPostingList(postingList);
        }


        /*
        List<String> queryTerm = new ArrayList<>();
        for(String[] termArray : terms) {
            for(String term : termArray){
                queryTerm.add(term);
                runner.Query(queryTerm);
                queryTerm.clear();
            }

        }*/


    }
}


package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
            //LogPrinter.printPostingList(postingList);
            startQuerying(postingList,term.getTerms());


            //TermAtTimeOrQuery.query(postingList);
            //DocumentAtTimeAndQuery.query(postingList);
            //DocumentAtTimeOrQuery.query(postingList);

        }
/*
        Map<String, LinkedList<Integer>> pl = new LinkedHashMap<>();
        LinkedList<Integer> list1 = new LinkedList<>();

        list1.add(5236);
        list1.add(5873);
        list1.add(6396);
        list1.add(6668);
        list1.add(6768);
        list1.add(6790);
        list1.add(7481);
        list1.add(7688);
        list1.add(8031);
        list1.add(8339);
        list1.add(8581);
        list1.add(8679);
        list1.add(8768);
        list1.add(9844);
        list1.add(11647);
        list1.add(11675);
        list1.add(11904);


        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(5027);
        list2.add(5067);
        list2.add(5156);
        list2.add(5351);
        list2.add(5463);
        list2.add(5543);
        list2.add(5592);
        list2.add(5762);
        list2.add(5772);
        list2.add(6162);
        list2.add(6325);

        pl.put("term0", list1);
        pl.put("term1", list2);
//        pl.put("term3", l3);

        startQuerying(pl);
*/
    }

    private static void startQuerying(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeAndQuery.reset();

        Iterator<String> terms = postingList.keySet().iterator();
        String firstTerm = terms.next();
        LinkedList<Integer> result = postingList.get(firstTerm);

        while(terms.hasNext() && result.size() > 0) {
            String term = terms.next();

            if(postingList.get(term).size() == 0) continue;

            result = TermAtTimeAndQuery.query(result, postingList.get(term));

        }

        //System.out.println("Final Answer "+result);

        LogPrinter.printTAATAnd(result, orderedTerms, TermAtTimeAndQuery.comparisonCount);


    }
}


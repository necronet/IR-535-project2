package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {


        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";
        String outputFile = "output.txt";
        String inputFile = "input.txt";

        if(args.length ==3) {
            path = args[0];
            outputFile = args[1];
            inputFile= args[2];
        }

        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);
        List<LocalTerms> terms = TermsLoader.load(inputFile);
        PostingList runner = new PostingList(invertedIndex);

        for(LocalTerms term : terms) {
            Map<String, LinkedList<Integer>> postingList = runner.get(term);
            //LogPrinter.printPostingList(postingList);
            //startQueryingTaatAnd(postingList,term.getTerms());
            //startQueryingTaatOr(postingList,term.getTerms());
            //TermAtTimeOrQuery.query(postingList);
            //DocumentAtTimeAndQuery.query(postingList);
            //DocumentAtTimeOrQuery.query(postingList);
        }
/*
        Map<String, LinkedList<Integer>> pl = new LinkedHashMap<>();
        LinkedList<Integer> list1 = new LinkedList<>();

        list1.add(5);
        list1.add(13);
        list1.add(21);
        list1.add(25);
        list1.add(31);

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(25);
        list2.add(27);

        pl.put("term0", list1);
        pl.put("term1", list2);

        startQueryingTaatOr(pl, null);*/
        //System.out.println(TermAtTimeOrQuery.comparisonCount);

    }

    private static void startQueryingTaatAnd(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeAndQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        String firstTerm = terms.next();
        LinkedList<Integer> result = postingList.get(firstTerm);

        while(terms.hasNext() && result.size() > 0) {
            String term = terms.next();
            if(postingList.get(term).size() == 0) continue;
            result = TermAtTimeAndQuery.query(result, postingList.get(term));
        }
        LogPrinter.printTAATAnd(result, orderedTerms, TermAtTimeAndQuery.comparisonCount);
    }

    private static void startQueryingTaatOr(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeOrQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        String firstTerm = terms.next();
        LinkedList<Integer> result = postingList.get(firstTerm);

        while(terms.hasNext() && result.size() > 0) {
            String term = terms.next();
            if(postingList.get(term).size() == 0) continue;
            result = TermAtTimeOrQuery.query(result, postingList.get(term));
        }

        LogPrinter.printTAATOr(result, orderedTerms, TermAtTimeOrQuery.comparisonCount);
    }
}


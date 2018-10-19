package edu.buffalo.joseluis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogPrinter {

    static PrintStream o = System.out;

    public static void setOutputFile(String output){
        try {
            o = new PrintStream(new File(output));
        } catch (FileNotFoundException e) {
            o = System.out;
        }
    }

    public static void printPostingList(Map<String, LinkedList<Integer>> postingList,String[] orderedTerms) {

        for(String term : orderedTerms) {
            o.println("GetPostings");
            o.println(term);
            String postingDocs = postingList.get(term).stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining(" "));

            o.println(String.format("Postings list: %s",postingDocs));
        }
    }

    public static void printTAATAnd(LinkedList<Integer> result, String[] orderedTerms, int comparisonCount) {
        o.println("TaatAnd");
        printTaat(result,orderedTerms,comparisonCount);
    }


    public static void printTAATOr(LinkedList<Integer> result, String[] orderedTerms, int comparisonCount) {
        o.println("TaatOr");
        printTaat(result,orderedTerms,comparisonCount);
    }

    public static void printTaat(LinkedList<Integer> result, String[] orderedTerms, int comparisonCount) {
        o.println(Stream.of(orderedTerms).collect(Collectors.joining(" ")));


        if(result.size() == 0)
            o.println("Results: empty");
        else
            o.println("Results: "+result.stream().map(r->r.toString()).collect(Collectors.joining(" ")));

        o.println("Number of documents in results: "+result.size());
        o.println("Number of comparisons: "+comparisonCount);
    }

    public static void printDAATOr(LinkedList<Integer> result, String[] orderedTerms, int comparisonCount) {
        o.println("DaatOr");
        printTaat(result,orderedTerms,comparisonCount);
    }

    public static void printDAATAnd(LinkedList<Integer> results, String[] orderedTerms, int comparisonCount) {
        o.println("DaatAnd");
        printTaat(results,orderedTerms,comparisonCount);
    }
}

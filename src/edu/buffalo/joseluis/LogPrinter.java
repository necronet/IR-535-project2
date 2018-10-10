package edu.buffalo.joseluis;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogPrinter {

    static PrintStream o = System.out;

    public static void printPostingList(Map<String, LinkedList<Integer>> postingList) {

        for(String term : postingList.keySet()) {
            o.println("GetPostings");
            o.println(term);
            String postingDocs = postingList.get(term).stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining(" "));

            o.println(String.format("Postings list: %s",postingDocs));
        }
    }

    public static void printTAATAnd(LinkedList<Integer> result, String[] orderedTerms, int comparisonCount) {
        o.println("DaatAnd");
        Stream.of(orderedTerms).collect(Collectors.joining(" "));
        o.println("Results: "+result.stream().map(r->r.toString()).collect(Collectors.joining(" ")));

        if(result.size() == 0)
            o.println("Numbers of documents in results: empty");
        else
            o.println("Numbers of documents in results: "+result);
        o.println("Numbers of comparisons: "+result.size());
    }
}

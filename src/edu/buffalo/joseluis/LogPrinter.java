package edu.buffalo.joseluis;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

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
}

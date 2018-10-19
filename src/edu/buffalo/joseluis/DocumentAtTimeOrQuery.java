package edu.buffalo.joseluis;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocumentAtTimeOrQuery {

    public static int comparisonCount = 0;

    public static void init() {
        comparisonCount = 0;
    }

    public static LinkedList<Integer> query(Map<String, LinkedList<Integer>> postingList, Map<String, Integer> pointers) {

        LinkedList<Integer> result = new LinkedList<>();
        String minTerm = null;


        while (pointers.keySet().stream().anyMatch(s -> pointers.get(s) < postingList.get(s).size())) {

            int min = Integer.MAX_VALUE;

            for (String term : postingList.keySet()) {
                if (pointers.get(term) >= postingList.get(term).size()) continue;

                if (postingList.get(term).get(pointers.get(term)) < min) {

                    min = postingList.get(term).get(pointers.get(term));
                    minTerm = term;
                    comparisonCount++;

                } else if (postingList.get(term).get(pointers.get(term)) == min) {
                    pointers.put(term, pointers.get(term) + 1);
                    comparisonCount++;
                }


            }
            result.add(min);
            pointers.put(minTerm, pointers.get(minTerm) + 1);


        }

        return result;

    }
}

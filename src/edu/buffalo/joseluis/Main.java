package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {


        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";
        String outputFile = "output.txt";
        String inputFile = "input.txt";

        if (args.length == 3) {
            path = args[0];
            outputFile = args[1];
            inputFile = args[2];
        }

        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);
        List<LocalTerms> terms = TermsLoader.load(inputFile);
        PostingList runner = new PostingList(invertedIndex);

        for (LocalTerms term : terms) {
            Map<String, LinkedList<Integer>> postingList = runner.get(term);
            //LogPrinter.printPostingList(postingList);
            //startQueryingTaatAnd(postingList,term.getTerms());
            //startQueryingTaatOr(postingList,term.getTerms());
            //startQueryingDaatAnd(postingList, term.getTerms());
            //TermAtTimeOrQuery.query(postingList);
            //DocumentAtTimeAndQuery.query(postingList);
            //DocumentAtTimeOrQuery.query(postingList);
        }

        Map<String, LinkedList<Integer>> pl = new LinkedHashMap<>();
        LinkedList<Integer> list1 = new LinkedList<>();

        list1.add(5);
        list1.add(13);
        list1.add(21);
        list1.add(25);
        list1.add(31);
        list1.add(32);
        list1.add(33);
        list1.add(34);

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(25);
        list2.add(27);

        pl.put("term0", list1);
        pl.put("term1", list2);
        //pl.put("term2", list2);

        //startQueryingDaatAnd(pl, null);
        startQueryingDaatOr(pl, null);
        //System.out.println(TermAtTimeOrQuery.comparisonCount);

    }

    private static void startQueryingDaatOr(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        LinkedList<Integer> result = new LinkedList();

        Iterator<String> terms = postingList.keySet().iterator();
        Map<String, Integer> pointers = new HashMap<>();
        while (terms.hasNext())
            pointers.put(terms.next(), 0);


        String min_term = null;
        int comparison = 0;

        while (pointers.keySet().stream().anyMatch(s -> pointers.get(s) < postingList.get(s).size())) {

            int min = Integer.MAX_VALUE;

            for (String term : postingList.keySet()) {
                if (pointers.get(term) >= postingList.get(term).size()) continue;

                if (postingList.get(term).get(pointers.get(term)) < min) {

                    min = postingList.get(term).get(pointers.get(term));
                    min_term = term;
                    comparison++;

                } else if (postingList.get(term).get(pointers.get(term)) == min) {
                    pointers.put(term, pointers.get(term) + 1);
                    comparison++;
                }


            }
            result.add(min);
            pointers.put(min_term, pointers.get(min_term) + 1);


        }

        System.out.println(result);

    }

    private static void startQueryingDaatAnd(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        LinkedList<Integer> result = new LinkedList<>();
        int comparisonCount = 1;
        System.out.println("DaatAnd\n------------------------");

        System.out.println(postingList);

        Iterator<String> terms = postingList.keySet().iterator();


        Map<String, Integer> pointers = new HashMap<>();
        while (terms.hasNext()) {
            pointers.put(terms.next(), 0);
        }


        String minTerm = null;
        boolean pointerLimit;

        while (!(pointerLimit = pointers.keySet().stream().anyMatch(s -> pointers.get(s) >= postingList.get(s).size()))) {
            terms = postingList.keySet().iterator();
            int currentDocId;
            int minDoc = Integer.MAX_VALUE;
            while (terms.hasNext()) {

                String term = terms.next();

                currentDocId = postingList.get(term).get(pointers.get(term));
                if (minDoc > currentDocId) {
                    minDoc = currentDocId;
                    minTerm = term;
                }



                /*int ix = pointers.get(term);

                if (minDoc > postingList.get(term).get(ix)) {
                    minDoc = postingList.get(term).get(ix);
                    minTerm = term;
                    pointers.put(term, pointers.get(term));
                }*/
            }
            System.out.println(minTerm + ":" + minDoc);


            boolean allEquals = (pointers.keySet().stream()
                    .mapToInt(p -> {
                        int index = pointers.get(p);
                        return postingList.get(p).get(index);
                    }).distinct().count() == 1);

            comparisonCount = comparisonCount + 1;
            //if(allEquals) {
            //   result.add(minDoc);
            //}


            pointers.put(minTerm, pointers.get(minTerm) + 1);

        }

        //System.out.println("minDoc: " + minDoc + " term: " + minTerm);
        //System.out.println("pointer map: " + pointers);
        System.out.println("result: " + result);


        //5,25 13,25 21,25 25,25 25,27 31,27


        //System.out.println("------------------------\n\n");

    }

    private static void startQueryingTaatAnd(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeAndQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        String firstTerm = terms.next();
        LinkedList<Integer> result = postingList.get(firstTerm);

        while (terms.hasNext() && result.size() > 0) {
            String term = terms.next();
            if (postingList.get(term).size() == 0) continue;
            result = TermAtTimeAndQuery.query(result, postingList.get(term));
        }
        LogPrinter.printTAATAnd(result, orderedTerms, TermAtTimeAndQuery.comparisonCount);
    }

    private static void startQueryingTaatOr(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeOrQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        String firstTerm = terms.next();
        LinkedList<Integer> result = postingList.get(firstTerm);

        while (terms.hasNext() && result.size() > 0) {
            String term = terms.next();
            if (postingList.get(term).size() == 0) continue;
            result = TermAtTimeOrQuery.query(result, postingList.get(term));
        }

        LogPrinter.printTAATOr(result, orderedTerms, TermAtTimeOrQuery.comparisonCount);
    }

}


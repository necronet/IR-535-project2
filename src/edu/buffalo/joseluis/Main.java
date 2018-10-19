package edu.buffalo.joseluis;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {


        /*
        * Only for testing purposes
        */
        String path = "../../IR-535/project2/index";
        String outputFile = "output.txt";
        String inputFile = "input2.txt";

        if (args.length == 3) {
            path = args[0];
            outputFile = args[1];
            inputFile = args[2];
        }

        //LogPrinter.setOutputFile(outputFile);
        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);
        List<LocalTerms> terms = TermsLoader.load(inputFile);
        PostingList runner = new PostingList(invertedIndex);

        System.out.println(terms.iterator().next());
        for (LocalTerms term : terms) {

            Map<String, LinkedList<Integer>> postingList = runner.get(term);

            LogPrinter.printPostingList(postingList);
            startQueryingTaatAnd(postingList,term.getTerms());
            startQueryingTaatOr(postingList,term.getTerms());
            startQueryingDaatAnd(postingList, term.getTerms());
            startQueryingDaatOr(postingList, term.getTerms());
        }
    }

    private static void startQueryingDaatOr(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        DocumentAtTimeOrQuery.init();
        Iterator<String> terms = postingList.keySet().iterator();
        Map<String, Integer> pointers = new HashMap<>();
        while (terms.hasNext())
            pointers.put(terms.next(), 0);

        LinkedList<Integer> result = DocumentAtTimeOrQuery.query(postingList, pointers);

        LogPrinter.printDAATOr(result, orderedTerms, DocumentAtTimeOrQuery.comparisonCount);

    }

    private static void startQueryingDaatAnd(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        if(postingList.size() == 0) {
            LogPrinter.printDAATAnd(new LinkedList<>(), orderedTerms, 0);
            return;
        }

        LinkedList<Integer> results = new LinkedList<>();
        Iterator<String> terms = postingList.keySet().iterator();

        Map<String, Integer> pointers = new HashMap<>();
        while (terms.hasNext())
            pointers.put(terms.next(), 0);

        int comparisons = 1;

        while (!postingList.keySet().stream().anyMatch(s-> postingList.get(s).size() <= pointers.get(s))) {

            int minimumValue = postingList.keySet().stream()
                    .mapToInt(s->postingList.get(s).get(pointers.get(s)))
                    .min().getAsInt();

            int counter = 0;

            terms = postingList.keySet().iterator();



            while(terms.hasNext()) {
                String term = terms.next();
                if (postingList.get(term).get(pointers.get(term)) == minimumValue) {
                    counter++;
                    pointers.put(term,pointers.get(term)+1);
                    comparisons++;
                }

            }

            if (counter == postingList.size()) results.add(minimumValue);


        }

        LogPrinter.printDAATAnd(results, orderedTerms, comparisons);



    }

    private static void startQueryingTaatAnd(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeAndQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        LinkedList<Integer> result = new LinkedList<>();
        if(terms.hasNext()){
            String firstTerm = terms.next();
            result = postingList.get(firstTerm);

            while (terms.hasNext() && result.size() > 0) {
                String term = terms.next();
                if (postingList.get(term).size() == 0) continue;
                result = TermAtTimeAndQuery.query(result, postingList.get(term));
            }
        }
        LogPrinter.printTAATAnd(result, orderedTerms, TermAtTimeAndQuery.comparisonCount);
    }

    private static void startQueryingTaatOr(Map<String, LinkedList<Integer>> postingList, String[] orderedTerms) {

        TermAtTimeOrQuery.reset();
        Iterator<String> terms = postingList.keySet().iterator();
        LinkedList<Integer> result = new LinkedList<>();

        if(terms.hasNext()) {
            String firstTerm = terms.next();
            result = postingList.get(firstTerm);

            while (terms.hasNext() && result.size() > 0) {
                String term = terms.next();
                if (postingList.get(term).size() == 0) continue;
                result = TermAtTimeOrQuery.query(result, postingList.get(term));
            }
        }
        LogPrinter.printTAATOr(result, orderedTerms, TermAtTimeOrQuery.comparisonCount);
    }

}


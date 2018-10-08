package edu.buffalo.joseluis;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostingList {

    InvertedIndex index;
    public PostingList(InvertedIndex index) {
        this.index = index;
    }


    public Map<String, LinkedList<Integer>> get(LocalTerms localTerms) {

        Map<IndexedTerm, LinkedList<Integer>> termLinkedListMap = index.getIndex().entrySet().stream()
                .filter(e ->
                        Stream.of(localTerms.getTerms())
                                .anyMatch(lt -> lt.equals(e.getKey().getTerm())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final Map<String, LinkedList<Integer>> postingList = new HashMap<>();

        String languages[] = {"text_en","text_es","text_fr"};
        for(IndexedTerm term : termLinkedListMap.keySet()) {

            if(postingList.containsKey(term.getTerm())) continue;

            LinkedList<Integer> docs = Stream.of(languages)
                    .map(field -> new IndexedTerm(field, term.getTerm()))
                    .map(indexedTerm -> termLinkedListMap.get(indexedTerm))
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .sorted()
                    .collect(Collectors.toCollection(LinkedList::new));


            postingList.put(term.getTerm(), docs);

        }

        return postingList;


    }
}

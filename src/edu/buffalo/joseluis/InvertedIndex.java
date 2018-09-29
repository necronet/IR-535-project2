package edu.buffalo.joseluis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class InvertedIndex {


    private Map<IndexedTerm, LinkedList<Integer>> index;

    public InvertedIndex() {
        this(new HashMap<>());
    }

    public InvertedIndex(Map<IndexedTerm, LinkedList<Integer>> index){
        this.index = index;
    }

    public Map<IndexedTerm, LinkedList<Integer>> getIndex() {
        return index;
    }

    public void update(String term, String field) {
        index.putIfAbsent(new IndexedTerm(term,field), new LinkedList<>());
    }

    public void update(String term, String field, int docid) {
        IndexedTerm key = new IndexedTerm(term,field);
        index.computeIfPresent(key, (K, V) -> { V.add(docid); return V; });
    }
}

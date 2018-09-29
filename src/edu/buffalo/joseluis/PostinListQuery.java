package edu.buffalo.joseluis;

import org.apache.lucene.index.Term;

import java.util.*;

public class PostinListQuery extends QueryRunner {

    public PostinListQuery(InvertedIndex index) {
        super(index);
    }

    @Override
    public void Query(List<String> terms) {
        String[] fields_language = {"text_es","text_fr","text_en"};

        List<Integer> sortingList = new ArrayList<>();

        for(String field_language : fields_language) {

            for(String term : terms) {
                IndexedTerm indexedTerm = new IndexedTerm(field_language, term);
                if(!index.getIndex().containsKey(indexedTerm)) continue;

                sortingList.addAll(index.getIndex().get(indexedTerm));
            }
        }

        Collections.sort(sortingList);
    }
}

package edu.buffalo.joseluis;

import java.util.List;

public class DocumentAtTimeOrQuery extends QueryRunner {
    public DocumentAtTimeOrQuery(InvertedIndex index) {
        super(index);
    }

    @Override
    public void Query(List<String> terms) {

    }
}

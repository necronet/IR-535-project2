package edu.buffalo.joseluis;

import java.util.List;

public class TermAtTimeOrQuery extends QueryRunner {
    public TermAtTimeOrQuery(InvertedIndex index) {
        super(index);
    }

    @Override
    public void Query(List<String> terms) {

    }
}

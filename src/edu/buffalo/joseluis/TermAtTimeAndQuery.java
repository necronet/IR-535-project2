package edu.buffalo.joseluis;

import java.util.List;

public class TermAtTimeAndQuery extends QueryRunner {
    public TermAtTimeAndQuery(InvertedIndex index) {
        super(index);
    }

    @Override
    public void Query(List<String> terms) {

    }
}

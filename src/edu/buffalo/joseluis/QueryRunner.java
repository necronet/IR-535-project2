package edu.buffalo.joseluis;

import java.util.List;

public abstract class QueryRunner {

    protected InvertedIndex index;

    public QueryRunner(InvertedIndex index) {
        this.index = index;
    }

    public abstract void Query(List<String> terms);

}

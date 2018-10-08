package edu.buffalo.joseluis;

import java.util.Arrays;

public class LocalTerms {

    public String[] getTerms() {
        return terms;
    }

    private String[] terms;

    public LocalTerms(String [] terms) {
        this.terms = terms;
    }

    @Override
    public String toString() {
        return "LocalTerms{" +
                "terms=" + Arrays.toString(terms) +
                '}';
    }
}

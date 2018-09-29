package edu.buffalo.joseluis;

import java.util.Objects;

public class IndexedTerm {

    private String term;
    private String field;

    public IndexedTerm(String field, String term) {
        this.term = term;
        this.field = field;
    }

    public String getTerm() {
        return term;
    }

    public String getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexedTerm)) return false;
        IndexedTerm that = (IndexedTerm) o;
        return Objects.equals(getTerm(), that.getTerm()) &&
                Objects.equals(getField(), that.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTerm(), getField());
    }

    @Override
    public String toString() {
        return "{" +
                "term='" + term + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}

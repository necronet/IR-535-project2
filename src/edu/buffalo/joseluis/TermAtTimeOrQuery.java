package edu.buffalo.joseluis;

import java.util.LinkedList;

public class TermAtTimeOrQuery {
    public static int comparisonCount;

    public static void reset() {
        comparisonCount = 0;
    }

    public static LinkedList<Integer> query(LinkedList<Integer> list1, LinkedList<Integer> list2) {
        LinkedList<Integer> answers = new LinkedList<>();
        int i = 0, j = 0;
        while ( i < list1.size() && j <list2.size() ) {

            int doc1Id = list1.get(i);
            int doc2Id = list2.get(j);

            //System.out.println(String.format("Comparison %d=%d", doc1Id, doc2Id));

            comparisonCount++;

            if (doc1Id == doc2Id) {
                answers.add(doc1Id);
                j++;
                i++;
            } else if (doc1Id < doc2Id) {
                answers.add(doc1Id);
                i++;
            } else {
                answers.add(doc2Id);
                j++;
            }

        }

        while(i < list1.size())
        {
            answers.add(list1.get(i));
            i++;
        }

        while( j < list2.size())
        {
            answers.add(list2.get(j));
            j++;
        }


        //System.out.println("Final answer " + answers);
        return answers;
    }
}

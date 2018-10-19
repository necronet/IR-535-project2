package edu.buffalo.joseluis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TermAtTimeAndQuery  {

    public static int comparisonCount = 0;

    static void reset() {
        comparisonCount = 0;
    }

    public static LinkedList<Integer> query(LinkedList<Integer> list1, LinkedList<Integer> list2) {
        LinkedList<Integer> answer = new LinkedList<>();

        Iterator<Integer> it1 = list1.iterator();
        Iterator<Integer> it2 = list2.iterator();

        int doc1Id = it1.next();
        int doc2Id = it2.next();

        int skipPointer1 = (int) Math.round(Math.sqrt(list1.size()));
        int skipPointer2 = (int) Math.round(Math.sqrt(list2.size()));

        //System.out.println(String.format("SKList > %d = %d",skipPointer1,skipPointer2));

        int list1Index = 0;
        int list2Index = 0;


        while(it1.hasNext() || it2.hasNext()) {

            //System.out.println(String.format("%d = %d",doc1Id,doc2Id));
            try {
                if (doc1Id == doc2Id) {
                    comparisonCount++;
                    answer.add(doc1Id);
                    doc1Id = it1.next();
                    list1Index++;
                    doc2Id = it2.next();
                    list2Index++;
                } else if (doc1Id < doc2Id) {
                    comparisonCount++;


                    if(hasSkip(list1Index, skipPointer1, list1.size()) &&
                            (list1.get(list1Index + skipPointer1) <= doc2Id)) {
                        while (hasSkip(list1Index, skipPointer1, list1.size()) &&
                                (list1.get(list1Index + skipPointer1) <= doc2Id)) {
                            skip(it1, skipPointer1);
                            doc1Id = list1.get(list1Index + skipPointer1);
                            list1Index = list1Index + skipPointer1;
                        }
                    } else {
                        doc1Id = it1.next();
                        list1Index++;
                    }

                } else {
                    comparisonCount++;

                    if(hasSkip(list2Index, skipPointer2, list2.size()) &&
                            (list2.get(list2Index + skipPointer2) <= doc1Id)) {

                        //System.out.println(String.format("%d = %d",doc1Id,doc2Id));
                        while (hasSkip(list2Index, skipPointer2, list2.size()) &&
                                (list2.get(list2Index + skipPointer2) <= doc1Id)) {
                            //System.out.println("Try to skip");
                            skip(it2, skipPointer2);
                            list2Index = list2Index + skipPointer2;
                            doc2Id = list2.get(list2Index);
                        }
                    } else {
                        doc2Id = it2.next();
                        list2Index++;
                    }
                }
            }catch(NoSuchElementException e){
                //System.out.println("Ran out of elements");
                break;
            }

        }

        //System.out.println("Comparison count "+comparisonCount++);
        //System.out.println(String.format("Last index for l1: %d, l2:%d",list1Index,list2Index));

        return answer;
    }

    public static boolean hasSkip(int ix, int sk, int limit) {

        return ix % sk == 0 && !(ix + sk >= limit);
    }

    public static void skip(Iterator<Integer> it, int skip) {
        for(int i=0; i< skip;i++) {
            it.next();
        }
    }


}

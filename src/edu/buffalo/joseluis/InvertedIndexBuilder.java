package edu.buffalo.joseluis;

import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class InvertedIndexBuilder {

    public static InvertedIndex build(String pathStr) throws IOException {
        Path path = FileSystems.getDefault().getPath(pathStr);
        Directory indexDirectory = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(indexDirectory);

        InvertedIndex invertedIndex = build(reader);
        /*InvertedIndex newInvertedIndex = new InvertedIndex();

        String universal_lang = "universal_lang";



        for(IndexedTerm indexedTerm : invertedIndex.getIndex().keySet()) {
            LinkedList<Integer> currentDocs = invertedIndex.getIndex().get(indexedTerm);

            IndexedTerm newTerm = new IndexedTerm(universal_lang, indexedTerm.getTerm());

            newInvertedIndex.getIndex().put(
                    newTerm,
                    newInvertedIndex.getIndex().merge(newTerm, currentDocs,
                            (l1, l2) -> Stream.of(l1, l2)
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.toCollection(LinkedList::new)))
            );


        }*/


        return invertedIndex;
    }

    private static InvertedIndex build(IndexReader reader) throws IOException {
        InvertedIndex invertedIndex = new InvertedIndex();

        Iterator<String> it = MultiFields.getFields(reader).iterator();

        while(it.hasNext()) {
            String field = it.next();
            if(field.equals("id")) continue;

            Terms terms = MultiFields.getTerms(reader, field);

            TermsEnum termsEnum = terms.iterator();

            BytesRef term;
            while ( (term = termsEnum.next()) != null) {
                collectDocs(invertedIndex, termsEnum, term.utf8ToString(), field);
            }
        }
        return invertedIndex;
    }

    private static void collectDocs(InvertedIndex invertedIndex, TermsEnum termsEnum, String term, String field) throws IOException {
        invertedIndex.update(term, field);
        PostingsEnum postingsEnum = termsEnum.postings(null);
        int docid;
        while ((docid = postingsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
            invertedIndex.update(term, field, docid);
        }
    }
}

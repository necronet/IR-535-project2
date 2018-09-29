package edu.buffalo.joseluis;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "file:///Users/necronet/Documents/repos/IR-535/project2/index";

        InvertedIndex invertedIndex = InvertedIndexBuilder.build(path);
        System.out.println(invertedIndex.getIndex().size());

    }
}

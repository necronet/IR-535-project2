# Information retrieval CSE 535 University at buffalo Project 2. Fall 2018

## Overview 

In this project, you will be given Lucene index generated from the PubMed corpus[1]. Based on this provided index, you task is to build your own inverted index usingthe information extracted from the given data. Your index should be stored as LinkedList in memory as the examples shown in textbook (Refer Chapter 1 – BooleanRetrieval). Having built this index, now you are required to implement two strategiesto return boolean query results: Term-at-a-time (TAAT) and Document-at-a-time(DAAT). Your implementation should be based on Java.

## Step 1: Interact with Lucene Index and Build Your Own Inverted Index

As the examples in referred textbook, postings lists should be stored as LinkedLists.You will need to construct your own index in which postings of each term should beordered by increasing document IDs. 

In order to use the APIs to talk to the given index and get the information you need,import the [Lucene module](https://lucene.apache.org/core/) to your java project.

## Step 2: Boolean Query Processing
You are required to implement the following methods, and provide the results for aset of boolean queries (AND/OR) on your own index. Results should be output asa .txt file in the required format.
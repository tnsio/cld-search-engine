package main;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * And inverted index table on a given set of documents.
 */
public class InvertedIndexTable {

    // All characters except alphanumeric ones and ' are considered delimiters
    private final static Pattern delimiters = Pattern.compile("[^A-Za-z0-9']");

    // Array containing all the names of the documents which will be queried
    private String[] documentArray;

    private int[] documentWordCount;

    /*
        Hashmap that contains word and boolean tuple entries where each entry in the
        boolean tuple represents the existence of the word in the n-th document.
     */
    private HashMap<String, CBTuple> table = new HashMap<>();


    // Create a table from a document name array
    public InvertedIndexTable(String[] args) throws FileNotFoundException {
       documentWordCount = new int[args.length];
       Arrays.fill(documentWordCount, 0);
       documentArray = args;

       for (int documentIndex = 0; documentIndex < documentArray.length; documentIndex ++) {

           File document = new File(documentArray[documentIndex]);
           Scanner scanner = new Scanner(document);
           scanner.useDelimiter(delimiters);

           while (scanner.hasNext()) {
               String word = scanner.next();

               if (word.isBlank()) {
                   continue;
               }

               addWordAtIndex(word, documentIndex);
               documentWordCount[documentIndex]++;
           }
       }
    }

    private void addWordAtIndex(String word, int index) {
        word = word.toLowerCase();

        if (!table.containsKey(word)) {
            table.put(word, new CBTuple(documentArray.length));
        }

        table.get(word).addOccurrence(index);
    }

    /**
     * Calculate the tf-idf of a word in a document of documentIndex.
     * @param word word that is searched
     * @param documentIndex index of document for which tf is calculated
     * @return tfidf value
     */
    public double calculateTfIdf(String word, int documentIndex) {
        CBTuple wordTuple = table.get(word);

        double tf = ((double) wordTuple.getAt(documentIndex))
                / ((double) documentWordCount[documentIndex]);
        double idf = Math.log(((double) documentArray.length) / ((double) wordTuple.getNonZero()));
        return tf * idf;
    }



    public CBTuple get(String word) {
        return table.getOrDefault(word, new CBTuple(documentArray.length));
    }

    /**
     * Get the document names that satisfy pseudo-boolean value tuple.
     * @param tuple tuple with truth values for each document
     * @return documentList
     */
    public ArrayList<String> getMatchingDocumentNames(CBTuple tuple) {
        ArrayList<String> documentList = new ArrayList<>();

        for (int documentIndex = 0; documentIndex < tuple.getSize(); documentIndex++) {
            if (tuple.getAt(documentIndex) != 0) {
                documentList.add(documentArray[documentIndex]);
            }
        }

        return documentList;
    }

    /**
     * Print all the the td-idf values for words appearing in expressions
     * in their matching document.
     * @param expression expression from which the words are extracted
     */
    public void printTdIdfTable(String expression, CBTuple result) {
        ArrayList<String> wordList = new ArrayList<>();
        Scanner expressionScanner = new Scanner(expression);
        expressionScanner.useDelimiter(delimiters);
        while (expressionScanner.hasNext()) {
            String word = expressionScanner.next();

            if (word.isBlank()) {
                continue;
            }
            wordList.add(word);
        }
        System.out.println("~~~~~ Td-idf table ~~~~~");
        for (int documentIndex = 0; documentIndex < documentArray.length; documentIndex++) {
            if (result.getAt(documentIndex) != 0) {
                System.out.print("Document: ");
                System.out.println(documentArray[documentIndex]);
                for (String word : wordList) {
                    System.out.println(word + " " + calculateTfIdf(word, documentIndex));
                }

                System.out.println();
            }
        }
    }
}

package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InvertedIndexTable table;

        try {
            table = new InvertedIndexTable(args);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Could not read files");
            return;
        }

        ExpressionEvaluator evaluator = new ExpressionEvaluator(table);
        Scanner inputScanner = new Scanner(System.in);

        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine().toLowerCase();

            if (line.equals("")) {
                break;
            }

            CBTuple result = evaluator.evaluate(line);
            ArrayList<String> documentList = table.getMatchingDocumentNames(result);
            if (documentList.isEmpty()) {
                System.out.println("No matches found!");
            } else {
                System.out.println("Following matches found:");
                for (String documentName : documentList) {
                    System.out.println(documentName);
                }

                System.out.println();

                table.printTdIdfTable(line, result);
            }

        }
    }
}

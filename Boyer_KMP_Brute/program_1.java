/*
 * Christian Carranza
 * Program 1: Find It Fast
 * Dr. Lori
 * 10/05/2023
 * Sources: Calvin Grant helped me debugg and understand certain concepts
 * Cites used: https://chat.openai.com/, https://www.geeksforgeeks.org/java-program-for-kmp-algorithm-for-pattern-searching-2/,
 * https://www.w3schools.com/java/java_arraylist.asp, https://www.geeksforgeeks.org/boyer-moore-algorithm-for-pattern-searching/
 * 
 */



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class program_1 {
    public static void main(String[] args) {
        // Input file names
        String inputFileName1 = "input1.txt";
        String inputFileName2 = "input2.txt";

        // Read and store strings from the files in ArrayLists
        ArrayList<String> arrayList1 = fileToStringArrayList(inputFileName1);
        ArrayList<String> arrayList2 = fileToStringArrayList(inputFileName2);

        // Printing the ArrayLists for testing purposes
        System.out.println("ArrayList 1: " + arrayList1);
        System.out.println("ArrayList 2: " + arrayList2);

        // Accept substrings from the user for the four scenarios
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a substring to search for in Input 1 (find 'Ickle'): ");
        String substring1_1 = scanner.nextLine();
        System.out.print("Enter a substring to search for in Input 1 (find 'toot'): ");
        String substring1_2 = scanner.nextLine();
        System.out.print("Enter a substring to search for in Input 2 (find 'mmmmm'): ");
        String substring2_1 = scanner.nextLine();
        System.out.print("Enter a substring to search for in Input 2 (find 'mmmom'): ");
        String substring2_2 = scanner.nextLine();

        // Add space
        System.out.println();

        // Perform searches using different algorithms and measure time in nanoseconds
        searchAndMeasureTime("Brute Force", arrayList1, "Input 1", "Ickle", substring1_1);
        searchAndMeasureTime("Brute Force", arrayList1, "Input 1", "toot", substring1_2);
        searchAndMeasureTime("Brute Force", arrayList2, "Input 2", "mmmmm", substring2_1);
        searchAndMeasureTime("Brute Force", arrayList2, "Input 2", "mmmom", substring2_2);

        // Add space
        System.out.println();

        // Add calls for Boyer-Moore and KMP searches
        searchAndMeasureTime("Boyer-Moore", arrayList1, "Input 1", "Ickle", substring1_1);
        searchAndMeasureTime("Boyer-Moore", arrayList1, "Input 1", "toot", substring1_2);
        searchAndMeasureTime("Boyer-Moore", arrayList2, "Input 2", "mmmmm", substring2_1);
        searchAndMeasureTime("Boyer-Moore", arrayList2, "Input 2", "mmmom", substring2_2);

        // Add space
        System.out.println();

        searchAndMeasureTime("KMP", arrayList1, "Input 1", "Ickle", substring1_1);
        searchAndMeasureTime("KMP", arrayList1, "Input 1", "toot", substring1_2);
        searchAndMeasureTime("KMP", arrayList2, "Input 2", "mmmmm", substring2_1);
        searchAndMeasureTime("KMP", arrayList2, "Input 2", "mmmom", substring2_2);
    }

    // Function to read a file and store each character in an ArrayList of strings
    private static ArrayList<String> fileToStringArrayList(String fileName) {
        ArrayList<String> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into individual characters and add them to the ArrayList
                for (int i = 0; i < line.length(); i++) {
                    arrayList.add(String.valueOf(line.charAt(i)));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return arrayList;
    }

    // Function to search for a substring within an ArrayList of strings and measure execution time
    private static void searchAndMeasureTime(String algorithmName, ArrayList<String> arrayList, String inputName, String scenarioName, String substringToSearch) {
        long startTime = System.nanoTime();

        // Choose the search algorithm based on the algorithmName
        switch (algorithmName) {
            case "Brute Force":
                bruteForceSearch(arrayList, inputName, scenarioName, substringToSearch);
                break;
            case "Boyer-Moore":
                boyerMooreSearch(arrayList, inputName, scenarioName, substringToSearch);
                break;
            case "KMP":
                kmpSearch(arrayList, inputName, scenarioName, substringToSearch);
                break;
            default:
                System.err.println("Invalid algorithm name: " + algorithmName);
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        
        // Display execution time in scientific notation
        String executionTimeScientific = String.format("%5.2e", (double)executionTime);
        System.out.println("Execution time for " + algorithmName + ": " + executionTimeScientific + " nanoseconds");
    }

    // Function to search for a substring within an ArrayList of strings using brute force
    private static void bruteForceSearch(ArrayList<String> arrayList, String inputName, String scenarioName, String substringToSearch) {
        boolean found = false;
        for (int i = 0; i <= arrayList.size() - substringToSearch.length(); i++) {
            boolean match = true;
            for (int j = 0; j < substringToSearch.length(); j++) {
                if (!arrayList.get(i + j).equals(String.valueOf(substringToSearch.charAt(j)))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                found = true;
                break;
            }
        }

        // Print or store the result indicating whether a match was found
        if (found) {
            System.out.println("Brute Force: Match found for " + scenarioName + " in " + inputName);
        } else {
            System.out.println("Brute Force: No match found for " + scenarioName + " in " + inputName);
        }
    }

    // Function to search for a substring within an ArrayList of strings using Boyer-Moore
    private static void boyerMooreSearch(ArrayList<String> arrayList, String inputName, String scenarioName, String substringToSearch) {
        // Convert ArrayList of strings to a single string for Boyer-Moore
        StringBuilder text = new StringBuilder();
        for (String s : arrayList) {
            text.append(s);
        }

        // Convert substrings to search for to strings
        String pattern = substringToSearch;

        // Boyer-Moore algorithm
        int[] badChar = badCharHeuristic(pattern);
        int s = 0;
        boolean found = false;
        while (s <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            // Print or store the result indicating whether a match was found
            if (j < 0) {
                found = true;
                break;
            }

            // Move the pattern to the next possible position
            s += (s + pattern.length() < text.length()) ? pattern.length() - badChar[text.charAt(s + pattern.length())] : 1;
        }

        if (found) {
            System.out.println("Boyer-Moore: Match found for " + scenarioName + " in " + inputName);
        } else {
            System.out.println("Boyer-Moore: No match found for " + scenarioName + " in " + inputName);
        }
    }

    // Function to compute the Bad Character Heuristic table for Boyer-Moore
    private static int[] badCharHeuristic(String pattern) {
        int[] badChar = new int[256];
        for (int i = 0; i < badChar.length; i++) {
            badChar[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
        return badChar;
    }

    // Function to search for a substring within an ArrayList of strings using Knuth-Morris-Pratt
    private static void kmpSearch(ArrayList<String> arrayList, String inputName, String scenarioName, String substringToSearch) {
        // Convert ArrayList of strings to a single string for KMP
        StringBuilder text = new StringBuilder();
        for (String s : arrayList) {
            text.append(s);
        }

        // Convert substrings to search for to strings
        String pattern = substringToSearch;

        // KMP algorithm
        int[] prefix = computePrefix(pattern);
        int j = 0;
        boolean found = false;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                found = true;
                break;
            }
        }

        // Print or store the result indicating whether a match was found
        if (found) {
            System.out.println("KMP: Match found for " + scenarioName + " in " + inputName);
        } else {
            System.out.println("KMP: No match found for " + scenarioName + " in " + inputName);
        }
    }

    // Function to compute the prefix array for the KMP algorithm
    private static int[] computePrefix(String pattern) {
        int[] prefix = new int[pattern.length()];
        int j = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }
}

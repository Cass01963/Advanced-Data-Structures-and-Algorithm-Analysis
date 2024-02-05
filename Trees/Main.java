/**
 * An ordered binary search tree class.
 * TO DO:
 * 1) Complete the methods indicated.
 * 2) Add a remove method that eliminates the node of a given value
 * (parameter = data to find and kill; return = void)
 *
 * @author (Christian Carranza)
 * @version (2023)
 */


import java.io.*;
import java.util.Scanner;


public class Main {
    private BinaryTree library;
    private AVLTree avlTree;

    private RedBlackTree redBlackTree;

    public Main() {
        library = new BinaryTree();
        avlTree = new AVLTree();
        redBlackTree = new RedBlackTree();
    }

    public static void main(String[] args) {
        Main main = new Main();

        // Populate the BST and AVL tree with titles from "SciFiLiSorted.txt"
        try {
            File file1 = new File("SciFiLiSorted.txt");
            Scanner scanner1 = new Scanner(file1);
            
            scanner1.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading SciFiLiSorted.txt.");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose tree type:");
            System.out.println("1. Binary Search Tree");
            System.out.println("2. AVL Tree");
            System.out.println("3. Red Black Tree");
            System.out.println("4. Exit");

            int treeOption = scanner.nextInt();

            if (treeOption == 1) {
                main.binarySearchTreeOptions(scanner);
            } else if (treeOption == 2) {
                main.AVLTreeOptions(scanner);
            } else if (treeOption == 3) {
                main.RedBlackTreeOptions(scanner);
            } else if (treeOption == 4) {
                System.out.println("Exiting. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void binarySearchTreeOptions(Scanner scanner) {
        while (true) {
            System.out.println("Binary Search Tree Options:");
            System.out.println("1. Create Tree");
            System.out.println("2. Search for a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    createTree();
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void AVLTreeOptions(Scanner scanner) {
        while (true) {
            System.out.println("AVL Tree Options:");
            System.out.println("1. Create Tree");
            System.out.println("2. Search for a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    createAVLTree();
                    break;
                case 2:
                    searchBookAVL(scanner);
                    break;
                case 3:
                    removeBookAVL(scanner);
                    break;
                case 4:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createTree() {
        String fileName = "SciFiLiSorted.txt"; // Change this to your actual file name

        loadLibrary(fileName);

        System.out.println("Binary Search Tree created from the file: " + fileName);
    }


    private void loadLibrary(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                library.insert(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void searchBook(Scanner scanner) {
        System.out.println("Enter the title of the book to search:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        boolean found = library.getRoot() != null && library.search(title);
        if (found) {
            System.out.println("Book found in the library.");
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    private void removeBook(Scanner scanner) {
        System.out.println("Enter the title of the book to remove:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        if (library.getRoot() != null) {
            boolean removed = library.search(title);
            if (removed) {
                library.remove(title);
                System.out.println("Book removed from the library.");
            } else {
                System.out.println("Book not found in the library. Cannot remove.");
            }
        } else {
            System.out.println("Library is empty. Cannot remove book.");
        }
    }


    private void createAVLTree() {
        String fileName = "SciFiLiSorted.txt"; // Change this to your actual file name

        loadLibraryAVL(fileName);

        System.out.println("AVL Tree created from the file: " + fileName);
    }

    private void loadLibraryAVL(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                avlTree.insert(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void searchBookAVL(Scanner scanner) {
        System.out.println("Enter the title of the book to search:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        boolean found = avlTree.getRoot() != null && avlTree.search(title);
        if (found) {
            System.out.println("Book found in the library.");
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    private void removeBookAVL(Scanner scanner) {
        System.out.println("Enter the title of the book to remove:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        if (avlTree.getRoot() != null) {
            boolean removed = avlTree.search(title);
            if (removed) {
                avlTree.remove(title);
                System.out.println("Book removed from the library.");
            } else {
                System.out.println("Book not found in the library. Cannot remove.");
            }
        } else {
            System.out.println("Library is empty. Cannot remove book.");
        }
    }

    private void RedBlackTreeOptions(Scanner scanner) {
        while (true) {
            System.out.println("Red Black Tree Options:");
            System.out.println("1. Create Tree");
            System.out.println("2. Search for a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    createRBTree();
                    break;
                case 2:
                    searchRBBook(scanner);
                    break;
                case 3:
                    removeRBBook(scanner);
                    break;
                case 4:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createRBTree() {
        String fileName = "SciFiLiSorted.txt";
        loadRBLibrary(fileName);
        System.out.println("Red-Black Tree created from the file: " + fileName);
    }

    private void loadRBLibrary(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                redBlackTree.insert(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void searchRBBook(Scanner scanner) {
        System.out.println("Enter the title of the book to search:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        boolean found = redBlackTree.getRoot() != null && redBlackTree.search(title);
        if (found) {
            System.out.println("Book found in the library.");
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    private void removeRBBook(Scanner scanner) {
        System.out.println("Enter the title of the book to remove:");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();

        if (redBlackTree.getRoot() != null) {
            boolean removed = redBlackTree.search(title);
            if (removed) {
                redBlackTree.remove(title);
                System.out.println("Book removed from the library.");
            } else {
                System.out.println("Book not found in the library. Cannot remove.");
            }
        } else {
            System.out.println("Library is empty. Cannot remove book.");
        }
    }


}








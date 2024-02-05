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



class BinaryTree {
    private BinNode root;

    /**
     * default constructor
     */
    public BinaryTree() {
        root = null;
    }

    public BinNode getRoot() {
        return this.root;
    }

    public void setRoot(BinNode root) {
        this.root = root;
    }

    protected BinNode protectedInsert(BinNode node, String data) {
        return insert(node, data);
    }

    protected BinNode protectedRemove(BinNode node, String data) {
        return remove(node, data);
    }
    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return this.root == null;
    }

    /* Functions to insert data */
    public void insert(String data) {
        this.root = insert(this.root, data);
    }

    /* Function to insert data recursively */
    private BinNode insert(BinNode node, String data) {
        if (node == null)
            node = new BinNode(data);
        else {
            if (data.compareTo(node.getData()) < 0) {
                // Insert into the left subtree
                node.setLeft(insert(node.getLeft(), data));
            } else if (data.compareTo(node.getData()) > 0) {
                // Insert into the right subtree
                node.setRight(insert(node.getRight(), data));
            }
            // If data is equal, do nothing (no duplicates allowed)
        }
        return node;
    }

    /* Function to count number of nodes */
    public int countNodes() {
        return countNodes(this.root);
    }

    /* Function to count number of nodes recursively */
    private int countNodes(BinNode r) {
        if (r == null)
            return 0;
        else
            return 1 + countNodes(r.getLeft()) + countNodes(r.getRight());
    }

    /* Function to search for an element */
    public boolean search(String val) {
        return search(this.root, val);
    }

    /* Function to search for an element recursively */
    private boolean search(BinNode r, String val) {
        if (r == null)
            return false;
        else if (val.equals(r.getData()))
            return true;
        else if (val.compareTo(r.getData()) < 0)
            return search(r.getLeft(), val);
        else
            return search(r.getRight(), val);
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(this.root);
        System.out.println(); // Add a new line for better readability
    }

    private void inorder(BinNode r) {
        if (r != null) {
            inorder(r.getLeft());
            System.out.print(r.getData() + " ");
            inorder(r.getRight());
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
        System.out.println(); // Add a new line for better readability
    }

    private void preorder(BinNode r) {
        if (r != null) {
            System.out.print(r.getData() + " ");
            preorder(r.getLeft());
            preorder(r.getRight());
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
        System.out.println(); // Add a new line for better readability
    }

    private void postorder(BinNode r) {
        if (r != null) {
            postorder(r.getLeft());
            postorder(r.getRight());
            System.out.print(r.getData() + " ");
        }
    }

    /* Function to remove a node with a given value */
    public void remove(String data) {
        root = remove(root, data);
    }

    private BinNode remove(BinNode node, String data) {
        if (node == null)
            return null;

        // Traverse to the node to be removed
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            // Node with only one child or no child
            if (node.getLeft() == null)
                return node.getRight();
            else if (node.getRight() == null)
                return node.getLeft();

            // Node with two children, get the inorder successor
            node.setData(findMinValue(node.getRight()));

            // Remove the inorder successor
            node.setRight(remove(node.getRight(), node.getData()));
        }

        return node;
    }

    // Helper method to find the minimum value in a BST
    private String findMinValue(BinNode node) {
        String minValue = node.getData();
        while (node.getLeft() != null) {
            minValue = node.getLeft().getData();
            node = node.getLeft();
        }
        return minValue;
    }


}

class AVLTree extends BinaryTree {

    // Override the insert method to balance the tree
    @Override
    public void insert(String data) {
        setRoot(insert(getRoot(), data));
    }

    // Override the remove method to balance the tree
    @Override
    public void remove(String data) {
        setRoot(remove(getRoot(), data));
    }

    // Additional AVL tree balancing methods can be added here

    // Height of a node
    private int height(BinNode node) {
        if (node == null)
            return 0;
        return node.getHeight();
    }

    // Update the height of a node
    private void updateHeight(BinNode node) {
        if (node != null) {
            node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        }
    }

    // Get the balance factor of a node
    private int getBalance(BinNode node) {
        if (node == null)
            return 0;
        return height(node.getLeft()) - height(node.getRight());
    }

    // Perform a right rotation
    private BinNode rightRotate(BinNode y) {
        BinNode x = y.getLeft();
        BinNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Perform a left rotation
    private BinNode leftRotate(BinNode x) {
        BinNode y = x.getRight();
        BinNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Insert a node into the AVL tree
    private BinNode insert(BinNode node, String data) {
        // Perform standard BST insert
        if (node == null)
            return new BinNode(data);

        if (data.compareTo(node.getData()) < 0)
            node.setLeft(insert(node.getLeft(), data));
        else if (data.compareTo(node.getData()) > 0)
            node.setRight(insert(node.getRight(), data));
        else
            return node; // Duplicate data not allowed

        // Update height of the current node
        updateHeight(node);

        // Perform balancing
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    // Remove a node from the AVL tree
    private BinNode remove(BinNode node, String data) {
        // Implement the remove logic here
        if (node == null)
            return null;

        // Perform standard BST delete
        if (data.compareTo(node.getData()) < 0)
            node.setLeft(remove(node.getLeft(), data));
        else if (data.compareTo(node.getData()) > 0)
            node.setRight(remove(node.getRight(), data));
        else {
            // Node with only one child or no child
            if (node.getLeft() == null)
                return node.getRight();
            else if (node.getRight() == null)
                return node.getLeft();

            // Node with two children, get the inorder successor
            node.setData(findMinValue(node.getRight()));

            // Remove the inorder successor
            node.setRight(remove(node.getRight(), node.getData()));
        }

        // Update height of the current node
        updateHeight(node);

        // Perform balancing
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && getBalance(node.getLeft()) >= 0)
            return rightRotate(node);

        // Left Right Case
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.getRight()) <= 0)
            return leftRotate(node);

        // Right Left Case
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    // Helper method to find the minimum value in a BST
    private String findMinValue(BinNode node) {
        String minValue = node.getData();
        while (node.getLeft() != null) {
            minValue = node.getLeft().getData();
            node = node.getLeft();
        }
        return minValue;
    }
}



class RedBlackTree extends BinaryTree {

    // Define color constants
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // Override the insert method to maintain Red-Black Tree properties
    @Override
    public void insert(String data) {
        setRoot(insert(getRoot(), data));
    }

    // Override the remove method to balance the Red-Black Tree
    @Override
    public void remove(String data) {
        setRoot(remove(getRoot(), data));
    }

    // Insert a node into the Red-Black Tree
    // Override the insert method of BinaryTree to perform additional Red-Black Tree balancing steps
    protected BinNode insert(BinNode node, String data) {
        // Perform standard BST insert
        setRoot(protectedInsert(getRoot(), data));

        // Additional Red-Black Tree balancing steps
        fixInsert(getRoot());

        return getRoot();
    }


    protected BinNode remove(BinNode node, String data) {
        // Perform standard BST delete
        setRoot(protectedRemove(getRoot(), data));

        // Additional Red-Black Tree balancing steps
        if (getRoot() != null)
            fixDelete(getRoot());

        return getRoot();
    }

    // Additional Red-Black Tree balancing methods

    // Fix the Red-Black Tree properties after insertion
    private void fixInsert(BinNode node) {
        while (node != null && node.getParent() != null && node.getParent().getColor() == RED) {
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                BinNode uncle = node.getParent().getParent().getRight();
                if (uncle != null && uncle.getColor() == RED) {
                    // Case 1: Uncle is red, recolor
                    node.getParent().setColor(BLACK);
                    uncle.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) {
                        // Case 2: Node is a right child, left rotate
                        node = node.getParent();
                        leftRotate(node);
                    }
                    // Case 3: Node is a left child, right rotate and recolor
                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    rightRotate(node.getParent().getParent());
                }
            } else {
                BinNode uncle = node.getParent().getParent().getLeft();
                if (uncle != null && uncle.getColor() == RED) {
                    // Case 1: Uncle is red, recolor
                    node.getParent().setColor(BLACK);
                    uncle.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        // Case 2: Node is a left child, right rotate
                        node = node.getParent();
                        rightRotate(node);
                    }
                    // Case 3: Node is a right child, left rotate and recolor
                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    leftRotate(node.getParent().getParent());
                }
            }
        }
        // Ensure the root is black
        getRoot().setColor(BLACK);
    }

    // Fix the Red-Black Tree properties after deletion
    private void fixDelete(BinNode node) {
        while (node != null && node != getRoot() && node.getColor() == BLACK) {
            if (node == node.getParent().getLeft()) {
                BinNode sibling = node.getParent().getRight();
                if (sibling != null) {
                    if (sibling.getColor() == RED) {
                        // Case 1: Sibling is red
                        sibling.setColor(BLACK);
                        node.getParent().setColor(RED);
                        leftRotate(node.getParent());
                        sibling = node.getParent().getRight();
                    }
                    if ((sibling.getLeft() == null || sibling.getLeft().getColor() == BLACK) &&
                            (sibling.getRight() == null || sibling.getRight().getColor() == BLACK)) {
                        // Case 2: Both children of sibling are black
                        sibling.setColor(RED);
                        node = node.getParent();
                    } else {
                        if (sibling.getRight() == null || sibling.getRight().getColor() == BLACK) {
                            // Case 3: Sibling's right child is black
                            sibling.getLeft().setColor(BLACK);
                            sibling.setColor(RED);
                            rightRotate(sibling);
                            sibling = node.getParent().getRight();
                        }
                        // Case 4: Sibling's right child is red
                        sibling.setColor(node.getParent().getColor());
                        node.getParent().setColor(BLACK);
                        sibling.getRight().setColor(BLACK);
                        leftRotate(node.getParent());
                        node = getRoot(); // Exit loop
                    }
                }
            } else {
                BinNode sibling = node.getParent().getLeft();
                if (sibling != null) {
                    if (sibling.getColor() == RED) {
                        // Case 1: Sibling is red
                        sibling.setColor(BLACK);
                        node.getParent().setColor(RED);
                        rightRotate(node.getParent());
                        sibling = node.getParent().getLeft();
                    }
                    if ((sibling.getRight() == null || sibling.getRight().getColor() == BLACK) &&
                            (sibling.getLeft() == null || sibling.getLeft().getColor() == BLACK)) {
                        // Case 2: Both children of sibling are black
                        sibling.setColor(RED);
                        node = node.getParent();
                    } else {
                        if (sibling.getLeft() == null || sibling.getLeft().getColor() == BLACK) {
                            // Case 3: Sibling's left child is black
                            sibling.getRight().setColor(BLACK);
                            sibling.setColor(RED);
                            leftRotate(sibling);
                            sibling = node.getParent().getLeft();
                        }
                        // Case 4: Sibling's left child is red
                        sibling.setColor(node.getParent().getColor());
                        node.getParent().setColor(BLACK);
                        sibling.getLeft().setColor(BLACK);
                        rightRotate(node.getParent());
                        node = getRoot(); // Exit loop
                    }
                }
            }
        }
        if (node != null)
            node.setColor(BLACK);
    }

    // Left rotation
    private void leftRotate(BinNode x) {
        if (x != null) {
            BinNode y = x.getRight();
            if (y != null) {
                x.setRight(y.getLeft());
                if (y.getLeft() != null)
                    y.getLeft().setParent(x);
                y.setParent(x.getParent());
                if (x.getParent() == null)
                    setRoot(y);
                else if (x == x.getParent().getLeft())
                    x.getParent().setLeft(y);
                else
                    x.getParent().setRight(y);
                y.setLeft(x);
                x.setParent(y);
            }
        }
    }

    // Right rotation
    private void rightRotate(BinNode y) {
        if (y != null) {
            BinNode x = y.getLeft();
            if (x != null) {
                y.setLeft(x.getRight());
                if (x.getRight() != null)
                    x.getRight().setParent(y);
                x.setParent(y.getParent());
                if (y.getParent() == null)
                    setRoot(x);
                else if (y == y.getParent().getLeft())
                    y.getParent().setLeft(x);
                else
                    y.getParent().setRight(x);
                x.setRight(y);
                y.setParent(x);
            }
        }
    }
}
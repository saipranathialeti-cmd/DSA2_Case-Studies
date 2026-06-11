import java.util.Scanner;

class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        height = 1;
    }
}

public class WasteSenseAVL {

    Node root;

    int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int key) {

        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // RR Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // LR Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    Node delete(Node root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = delete(root.left, key);

        else if (key > root.key)
            root.right = delete(root.right, key);

        else {

            if ((root.left == null) || (root.right == null)) {

                Node temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;

            } else {

                Node temp = minValueNode(root.right);

                root.key = temp.key;

                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left),
                height(root.right)) + 1;

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    boolean search(Node node, int key) {

        if (node == null)
            return false;

        if (node.key == key)
            return true;

        if (key < node.key)
            return search(node.left, key);

        return search(node.right, key);
    }

    void inorder(Node node) {

        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        WasteSenseAVL tree = new WasteSenseAVL();
        Scanner sc = new Scanner(System.in);

        int choice, value;

        do {
            System.out.println("\n=== WasteSense AVL Tree System ===");
            System.out.println("1. Insert Bin ID");
            System.out.println("2. Search Bin ID");
            System.out.println("3. Delete Bin ID");
            System.out.println("4. Display (Inorder Traversal)");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Bin ID: ");
                    value = sc.nextInt();

                    tree.root = tree.insert(tree.root, value);

                    System.out.println("Bin inserted successfully.");
                    break;

                case 2:
                    System.out.print("Enter Bin ID to Search: ");
                    value = sc.nextInt();

                    if (tree.search(tree.root, value))
                        System.out.println("Bin ID " + value + " Found.");
                    else
                        System.out.println("Bin ID Not Found.");

                    break;

                case 3:
                    System.out.print("Enter Bin ID to Delete: ");
                    value = sc.nextInt();

                    tree.root = tree.delete(tree.root, value);

                    System.out.println("Bin deleted successfully.");
                    break;

                case 4:
                    System.out.println("Inorder Traversal (Bin IDs in sorted order):");
                    tree.inorder(tree.root);
                    System.out.println();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}
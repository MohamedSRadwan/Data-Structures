import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackBST implements ITree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node {
        int key;
        Object value;
        Node left;
        Node right;
        boolean color;
        int N;

        Node(int key, Object value, boolean color, int N) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.N = N;
        }
    }

    private Node root;

    public RedBlackBST() {
        root = null;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        if (h == null || h.right == null || h.left == null) return;
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private void flipColorsDuringDelete(Node h) {
        if (h == null || h.left == null || h.right == null) return;
        h.color = BLACK;
        h.left.color = RED;
        h.right.color = RED;
    }

    private Node balance(Node h) {
        if (h == null) return null;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // h.left is checked for null in isRed()
        if (isRed(h.left) && isRed(h.right)) flipColorsDuringDelete(h);

        // Recompute size.
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    @Override
    public Object get(int key) {
        return get(root, key);
    }

    private Object get(Node x, int key) {
        /* Return value associated with key in the subtree rooted at x;
         * return null if key not present in subtree rooted at x.
         */

        if (x == null) return null;
        int cmp = key - (x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
    }

    public int select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) { // Return Node containing key of rank k.
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    public int rank(int key) {
        return rank(key, root);
    }

    private int rank(int key, Node x) { // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key - (x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public int min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public int max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    @Override
    public int floor(int key) {
        Node x = floor(root, key);
        if (x == null) return -1;
        return x.key;
    }

    private Node floor(Node x, int key) {
        if (x == null) return null;
        int cmp = key - (x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public int ceiling(int key) {
        Node x = ceiling(root, key);
        if (x == null) return -1;
        return x.key;
    }

    private Node ceiling(Node x, int key) {
        if (x == null) return null;
        int cmp = key - (x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public void put(int key, Object val) {
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, int key, Object val) {
        if (h == null) // Do standard insert, with red link to parent.
            return new Node(key, val, RED, 1);
        int cmp = key - (h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.value = val; // modify value of pre existing key

        // Fix up any right-leaning links.
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        // Recompute size.
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node moveRedLeft(Node h) {
        /* Assuming that h is red and both h.left and h.left.left
         * are black, make h.left or one of its children red.
         */

        flipColorsDuringDelete(h);
        if (h.right != null && isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) return;
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);

        return balance(h);
    }

    private Node moveRedRight(Node h) {
        /* Assuming that h is red and both h.right and h.right.left
         * are black, make h.right or one of its children red.
         */

        flipColorsDuringDelete(h);
        if (h.left != null && !isRed(h.left.left)) {
            h = rotateRight(h);
        }
        return h;
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) return;
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);
        if (h.right == null)
            return null;
        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);

        return balance(h);
    }

    @Override
    public void delete(int key) {
        if (isEmpty()) return;
        if (!contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, int key) {
        if (h == null) return null;
        if (key - (h.key) < 0) {
            if (!isRed(h.left) && h.left != null && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key - (h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && h.right != null && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key - (h.key) == 0) {
                h.value = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }

        return balance(h);
    }

    @Override
    public boolean contains(Object value) {
        if (value == null) return false;
        if (root == null) return false;
        Node x = root;
        return contains(value, x);
    }

    private boolean contains(Object value, Node x) {
        if (x == null) return false;
        int cmp = value.toString().compareTo(x.value.toString());
        if (cmp < 0) return contains(value, x.left);
        else if (cmp > 0) return contains(value, x.right);
        else return true;
    }

    @Override
    public Iterable<Integer> keys() {
        return keys(min(), max());
    }

    private Iterable<Integer> keys(int lo, int hi) {
        Queue<Integer> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Integer> queue, int lo, int hi) {
        if (x == null) return;
        int cmplo = lo - (x.key);
        int cmphi = hi - (x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int height() {
        if (root == null) return -1;
        Node x = root;
        return height(x);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    @Override
    public void clear() {
        root = null;
    }

    private void printSpaces(int n){
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }
    public void printTree() {
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        // Calculate tree height to determine initial spacing
        int height = height();
        int maxLevel = height + 1;

        // Use a queue for level-order traversal
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        int level = 1;
        int nodesInLevel = 1;
        boolean moreLevels = true;

        while (moreLevels) {
            moreLevels = false;

            // Print leading spaces for this level
            int leadingSpaces = (int) (Math.pow(2, maxLevel - level) - 1);
            printSpaces(leadingSpaces);

            // Print nodes and between-spaces
            int betweenSpaces = (int) (Math.pow(2, maxLevel - level + 1) - 1);

            Queue<Node> currentLevel = new LinkedList<>();
            for (int i = 0; i < nodesInLevel; i++) {
                Node current = queue.poll();
                currentLevel.offer(current);

                if (current != null) {
                    System.out.print(current.value.toString());
                    queue.offer(current.left);
                    queue.offer(current.right);
                    if (current.left != null || current.right != null) {
                        moreLevels = true;
                    }
                } else {
                    System.out.print(" ");
                    queue.offer(null);
                    queue.offer(null);
                }

                if (i < nodesInLevel - 1) {
                    printSpaces(betweenSpaces);
                }
            }

            System.out.println();

            // Print slashes for the next level
            if (moreLevels) {
                printSpaces(leadingSpaces - 1);

                for (int i = 0; i < nodesInLevel; i++) {
                    Node current = currentLevel.poll();
                    if (current != null && current.left != null) {
                        System.out.print("/ ");
                    } else {
                        System.out.print(" ");
                    }

                    if (current != null && current.right != null) {
                        System.out.print("\\");
                    } else {
                        System.out.print(" ");
                    }

                    if (i < nodesInLevel - 1) {
                        printSpaces(betweenSpaces - 1);
                    }
                }
                System.out.println();
            }

            level++;
            nodesInLevel *= 2;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(this.root, sb);
        return sb.toString();
    }

    private void toString(Node x, StringBuilder sb) {
        if (x == null) return;
        toString(x.left, sb);
        sb.append("Key: ").append(x.key).append(", Value: ");
        if (x.value.getClass().isArray()) {
            sb.append(Arrays.toString((Object[]) x.value));
        } else {
            sb.append(x.value.toString());
        }
        sb.append("\n");
        toString(x.right, sb);
    }
}

package com.MohamedSRadwan.github.datastructures.redblackbst;

public interface ITree {

    /**
     * Interface for a Red-Black Tree implementation.
     *
     * A Red-Black Tree is a self-balancing binary search tree where each node has an
     * extra attribute: color, which can be either RED or BLACK. The tree maintains
     * balance during insertions and deletions by enforcing a set of properties:
     *
     * 1. Every node is either RED or BLACK
     * 2. The root is always BLACK
     * 3. No RED node has a RED child (RED nodes have only BLACK children)
     * 4. Every path from a node to its descendant NULL nodes has the same number of BLACK nodes
     * 5. NULL nodes are considered BLACK

     * /**
     * Inserts a new element into the tree, Update value if found; grow table if new, delete element if value is null
     *
     * @param value the element to be inserted into the tree
     * @param key the key of the element tp be inserted
     */
    void put(int key, Object value);

    /**
     * Retrieves the value associated with the specified key in the tree.
     *
     * @param key the key of the element to retrieve from the tree
     * @return the value associated with the given key, or null if the key is not found in the tree
     */
    Object get(int key);

    /**
     * Deletes the element with the minimum key
     */
    void deleteMin();

    /**
     * Deletes the element with the maximum key
     */
    void deleteMax();

    /**
     * Deletes an element from the tree while maintaining Red-Black properties.
     *
     * @param key the key of the element to be removed from the tree
     *
     */
    void delete(int key);

    /**
     * finds the largest element not larger than the given key
     * @param key key of a node
     * @return the key corresponding to the found element
     */
    int floor(int key);

    /**
     * finds the smallest element not smaller than the given key
     * @param key key of a node
     * @return the key corresponding to the found element
     */
    int  ceiling(int key);

    boolean contains(Object data);

    /**
     * Returns the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    int size();

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree contains no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the height of the tree. The height is defined as the number of edges
     * on the longest path from the root node to a leaf node.
     *
     * @return the height of the tree
     */
    int height();

    /**
     * Returns an iterable containing all the keys of the elements currently stored in the tree.
     * The keys are returned in ascending order.
     *
     * @return an iterable collection of all keys in the tree
     */
    Iterable<Integer> keys();

    /**
     * Clears all elements from the tree.
     */
    void clear();

    /**
     * Returns a string representation of the tree structure.
     *
     * @return a string representation of the tree
     */
    @Override
    String toString();
}

public interface IHashMap {

    /**
     * Computes the hash code for the given key by determining its position
     * in the internal storage array of the map.
     *
     * @param key the object whose hash code is to be computed
     * @return the hash value corresponding to the given key
     */
    int hash(int key);
    /**
     * Add a key-value pair to the map.
     * @param key the key to be added
     * @param value the value to be added
     */
    void put(int key, Object value);

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist in the map
     */

    Object get(int key);

    /**
     * Removes the entry for the specified key from the map if it exists.
     *
     * @param key the key whose entry is to be removed from the map
     */
    void remove(int key);

    /**
     * Checks if the map contains a mapping for the specified key.
     *
     * @param key the key whose presence in the map is to be tested
     * @return true if this map contains a mapping for the specified key, false otherwise
     */
    boolean containsKey(int key);

    /**
     * Returns the number of key-value mappings in the map.
     *
     * @return the number of key-value pairs contained in the map
     */
    int size();

    /**
     * Checks if the map contains no key-value pairs.
     *
     * @return true if the map is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Removes all key-value mappings from the map, leaving it empty.
     */
    void clear();

    /**
     * Returns a string representation of the map.
     * The string format and content may vary depending on the implementation.
     *
     * @return a string describing the entries in the map
     */
    String toString();
}

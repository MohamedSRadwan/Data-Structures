public class MyHashMap implements IHashMap {
    private final int capacity;
    private int size;
    private final RedBlackBST[] buckets;

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap(5);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "!");
        map.put(5, "f");
        map.put(6, "f");
        map.put(7, "f");

        System.out.println(map.toString());
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(3));
        System.out.println(map.get(4));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        map.remove(1);
        map.remove(2);
        map.remove(3);
        map.remove(4);
        System.out.println(map.toString());
    }

    public MyHashMap() {
        this(101);
    }
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        buckets = new RedBlackBST[capacity];
        for (int i = 0; i < capacity; i++){
            buckets[i] = new RedBlackBST();
        }
    }

    @Override
    public int hash(int key){
        return (key & 0x7fffffff) % capacity;
    }

    @Override
    public void put(int key, Object value) {
        this.buckets[hash(key)].put(key, value);
        this.size++;
    }

    @Override
    public Object get(int key) {
        if (this.isEmpty()){
            return null;
        }
        return this.buckets[hash(key)].get(key);
    }

    @Override
    public void remove(int key) {
        if (this.isEmpty()){
            return;
        }
        this.buckets[hash(key)].delete(key);
        this.size--;
    }

    @Override
    public boolean containsKey(int key) {
        if (this.isEmpty()){
            return false;
        }
        return this.buckets[hash(key)].contains(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++){
            buckets[i].clear();
        }
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (!buckets[i].isEmpty()) {
                sb.append(buckets[i].toString());
            }
        }
        return sb.toString();
    }
}
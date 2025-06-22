import java.util.Scanner;

public class DoubleLinkedList implements ILinkedList{
    private DNode head;
    private DNode tail;
    private int size;

    // Constructor
    public DoubleLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public DNode getHead(){
        return this.head;
    }
    public DNode getTail(){
        return this.tail;
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {

        DoubleLinkedList list = new DoubleLinkedList();

        try (Scanner sc = new Scanner(System.in)) {

            String input = sc.nextLine().trim();
            if (!input.equals("[]")) {
                String trimmed = input.substring(1, input.length() - 1).trim();
                String[] numberStrings = trimmed.split(",");
                for (String numStr : numberStrings) {
                    int number = Integer.parseInt(numStr.trim());
                    list.add(number);
                }
            }

            String command = sc.next();

            switch (command) {
                case "add":
                    list.add(sc.nextInt());
                    break;
                case "remove":
                    list.remove(sc.nextInt());
                    break;
                case "size":
                    System.out.println(list.size());
                    return;
                case "get":
                    Object result = list.get(sc.nextInt());
                    if (result != null) System.out.println(result);
                    return;
                case "set":
                    list.set(sc.nextInt(), sc.nextInt());
                    break;
                case "sublist":
                    list.sublist(sc.nextInt(), sc.nextInt());
                    break;
                case "isEmpty":
                    System.out.println(list.isEmpty() ? "True" : "False");
                    return;
                case "addToIndex":
                    list.add(sc.nextInt(), sc.nextInt());
                    break;
                case "contains":
                    System.out.println(list.contains(sc.nextInt()) ? "True" : "False");
                    return;
                case "clear":
                    list.clear();
                    break;
            }
        }
        catch (Exception e) {
            System.out.println("Error");

            return;
        }
        System.out.println(list);

    }

    private DNode nodeFromIndex(int index) throws RuntimeException{
        if (index < 0 || index >= size || head == null) {
            throw new RuntimeException("Error");
        }
        DNode curr = head;
        for (int i = 0; i < index && curr != null; i++) {
            curr = curr.getNext();
        }
        return curr;
    }
    private void addFirst(DNode node) {
        node.setNext(head);
        node.setPrev(null);
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }
    private void addLast(DNode node) {
        DNode curr = head;
        if (curr == null) {
            addFirst(node);
            return;
        }
        node.setPrev(tail);
        tail.setNext(node);
        tail = node;
        size++;
    }
    public void set(int index, Object element) {
        nodeFromIndex(index).setData(element);
    }
    public Object get(int index) {
        try {
            return nodeFromIndex(index).getData();
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public ILinkedList sublist(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= size || toIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new RuntimeException("Error");
        }
        head = nodeFromIndex(fromIndex);

        DNode curr = head;
        for (int i = fromIndex; i < toIndex && curr != null; i++) {
            curr = curr.getNext();
        }
        if (curr == null) {
            throw new RuntimeException("Error");
        }
        curr.setNext(null);
        return this;
    }
    public boolean contains(Object o) {
        DNode curr = head;
        while (curr != null) {
            if (curr.getData().equals(o)) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }
    public void add(int index, Object element) {
        DNode newNode = new DNode(element);
        if (index == 0) {
            addFirst(newNode);
            return;
        }
        if (index == size - 1) {
            addLast(newNode);
        }

        DNode prev = nodeFromIndex(index - 1);
        newNode.setNext(prev.getNext());
        prev.setNext(newNode);
        newNode.setPrev(prev);
        size++;
    }
    public void add(Object element) {
        addLast(new DNode(element));
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void removeFirst(){
        if (isEmpty()) {
            throw new RuntimeException("Error");
        }
        head = head.getNext();
        size--;
        if (head == null) {
            tail = null;
        }
    }
    public void removeLast(){
        if (isEmpty()) {
            throw new RuntimeException("Error");
        }
        if (size == 1) {
            clear();
        }
        DNode beforeLast = tail.getPrev();
        tail.setPrev(null);
        beforeLast.setNext(null);
        tail = beforeLast;
        size--;
    }

    public void remove(int index) {
        
        if (index == 0 && head != null){ // Remove head
            removeFirst();
            return;
        }
        if (index == size - 1) {
            removeLast();
            return;
        }
        DNode prev = nodeFromIndex(index - 1);
        if (prev.getNext() == null) {
            throw new RuntimeException("Error");
        }
        prev.setNext(prev.getNext().getNext()); // Works for middle/last nodes
        if (prev.getNext() != null){
            prev.getNext().setPrev(prev);
        }
        size--;
    }

    @Override
    public String toString() {
        DNode curr = head;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (curr != null) {
            sb.append(curr.getData());
            if (curr.hasNext()) {
                sb.append(", ");
            }
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}

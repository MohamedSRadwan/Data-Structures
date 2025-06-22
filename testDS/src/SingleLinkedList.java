import java.util.Arrays;
import java.util.Scanner;

public class SingleLinkedList implements ILinkedList {

    private SNode head;
    private SNode tail;
    private int size;

    public SingleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public SNode getHead(){
        return this.head;
    }
    public SNode getTail(){
        return this.tail;
    }
    public void incrementSize(){
        this.size++;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SingleLinkedList list = new SingleLinkedList();

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
        try {
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
        sc.close();
    }

    private SNode nodeFromIndex(int index) throws RuntimeException{
        if (index < 0 || index >= size || head == null) {
            throw new RuntimeException("Error");
        }
        SNode curr = head;
        for (int i = 0; i < index && curr != null; i++) {
            curr = curr.getNext();
        }
        return curr;
    }

    private void addFirst(SNode node) {
        node.setNext(head);
        head = node;
        if (tail == null) {
            tail = head;
        }
        size++;
    }
    private void addLast(SNode node) {
        if (isEmpty()){
            addFirst(node);
            return;
        }
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

        SNode curr = head;
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
        SNode curr = head;
        while (curr != null) {
            if (curr.getData().equals(o)) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void add(int index, Object element) {
        SNode newNode = new SNode(element);
        if (index == 0) {
            addFirst(newNode);
            return;
        }
        if (index == size - 1) {
            addLast(newNode);
            return;
        }

        SNode prev = nodeFromIndex(index - 1);
        newNode.setNext(prev.getNext());
        prev.setNext(newNode);
        size++;
    }

    public void add(Object element) {
        addLast(new SNode(element));
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void removeFirst() {
        if (isEmpty()) throw new RuntimeException("Error");
        head = head.getNext();
        if (head == null){
            tail = null;
        }
        size--;
    }

    public void remove(int index) {
        if (index == 0 && head != null){ // Remove head
            removeFirst();
            return;
        }
        SNode prev = nodeFromIndex(index - 1);
        if (prev.getNext() == null) {
            throw new RuntimeException("Error");
        }
        if (prev.getNext() == this.tail){ // delete last element
            prev.setNext(null);
            tail = prev;
            size--;
            return;
        }
        prev.setNext(prev.getNext().getNext()); // delete middle node
        size--;
    }


    @Override
public String toString() {
    SNode curr = head;
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    while (curr != null) {
        Object data = curr.getData();
        if (data instanceof int[]) {
            sb.append(Arrays.toString((int[]) data));
        } else {
            sb.append(data.toString());
        }

        if (curr.hasNext()) {
            sb.append(", ");
        }
        curr = curr.getNext();
    }
    sb.append("]");
    return sb.toString();
}
}
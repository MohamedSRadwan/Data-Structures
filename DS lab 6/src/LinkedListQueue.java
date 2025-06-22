import java.util.Scanner;

public class LinkedListQueue implements IQueue, ILinkedBased {
    private SingleLinkedList queue;

    public static void main(String[] args) {
        LinkedListQueue q = new LinkedListQueue();
        try(Scanner sc = new Scanner(System.in)){
            String input = sc.nextLine().trim();
            if (!input.equals("[]")) {
                String trimmed = input.substring(1, input.length() - 1);
                String[] numStrings = trimmed.split(",");

                for (int i = numStrings.length - 1; i >= 0; i--) {
                    Integer number = Integer.parseInt(numStrings[i].trim());
                    q.enqueue(number);
                }
            }

            String command = sc.nextLine().trim();
            switch (command) {
                case "enqueue": {
                    Integer number = Integer.parseInt(sc.nextLine().trim());
                    q.enqueue(number);
                    System.out.println(q);
                    break;
                }
                case "dequeue":
                    q.dequeue();
                    System.out.println(q);
                    break;
                case "size":
                    System.out.println(q.size());
                    break;
                case "isEmpty":
                    System.out.println((q.isEmpty())? "True" : "False");
                    break;
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public LinkedListQueue() {
        queue = new SingleLinkedList();
    }
    public Object front(){
        return this.queue.getHead().getData();
    }

    @Override
    public void enqueue(Object item) {
        this.queue.add(item);
    }

    @Override
    public Object dequeue() {
        if (this.isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        Object item = this.queue.get(0);
        this.queue.removeFirst();
        return item;
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = this.queue.size() - 1; i >= 0; i--) {
            if (i != this.queue.size() - 1){
                sb.append(", ");
            }
            sb.append(this.queue.get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}

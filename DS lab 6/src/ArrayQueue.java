import java.util.Scanner;

public class ArrayQueue implements IQueue, IArrayBased {
    private Object[] queue;
    private int MAXSIZE;
    private int front;
    private int rear;
    private int size;

    public static void main(String[] args) {
        ArrayQueue q = null;
        try(Scanner sc = new Scanner(System.in)){
            String input = sc.nextLine().trim();
            if (!input.equals("[]")) {
                String trimmed = input.substring(1, input.length() - 1);
                String[] numStrings = trimmed.split(",");
                
                q = new ArrayQueue(numStrings.length);
                for (int i = numStrings.length - 1; i >= 0; i--) {
                    Integer number = Integer.parseInt(numStrings[i].trim());
                    q.enqueue(number);
                }
            }
            else {
                q = new ArrayQueue(1);
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

    public ArrayQueue(int n) {
        if (n < 0) {
            throw new RuntimeException("invalid negative size");
        }
        this.queue = new Object[n + 1];
        this.MAXSIZE = n + 1;
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public Object front(){
        if(this.isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        return this.queue[front];
    }

    @Override
    public void enqueue(Object item) {
        if (this.size() == MAXSIZE) {
            throw new RuntimeException("Queue is full");
        }
        this.queue[rear] = item;
        rear = (rear + 1) % MAXSIZE;
        this.size++;
    }

    @Override
    public Object dequeue() {
        if (this.isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        Object item = this.queue[front];
        this.queue[front] = null;
        front = (front + 1) % MAXSIZE;
        this.size--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int i = (rear - 1 + MAXSIZE) % MAXSIZE;
        sb.append("[");
        while (count < size) {
            if (count != 0){
                sb.append(", ");
            }
            sb.append(this.queue[i]);
            i = (i - 1 + MAXSIZE) % MAXSIZE;
            count++;
        }
        sb.append("]");
        return sb.toString();
    }
}

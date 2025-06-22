import java.util.Scanner;

public class MyStack implements IStack {

    SingleLinkedList stack;

    MyStack() {
        stack = new SingleLinkedList();
    }

    public static void main(String[] args) {

        MyStack stack = new MyStack();

        try (Scanner sc = new Scanner(System.in)) {
            String input = sc.nextLine().trim();
            if (!input.equals("[]")) {
                String trimmed = input.substring(1, input.length() - 1);
                String[] numStrings = trimmed.split(",");
                for (int i = numStrings.length - 1; i >= 0; i--) {
                    int number = Integer.parseInt(numStrings[i].trim());
                    stack.push(number);
                }
            }
            String command = sc.nextLine().trim();

            switch (command) {
                case "push":
                    stack.push(Integer.parseInt(sc.nextLine().trim()));
                    System.out.println(stack);
                    break;
                case "pop":
                    stack.pop();
                    System.out.println(stack);
                    break;
                case "peek":
                    System.out.println(stack.peek());
                    break;
                case "size":
                    System.out.println(stack.size());
                    break;
                case "isEmpty":
                    System.out.println(stack.isEmpty() ? "True" : "False");
                    break;
                default:
                    System.out.println("Error");
                    return;
            }
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Override
    public Object pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Error");
        }
        Object temp = this.stack.getHead().getData();
        this.stack.removeFirst();
        return temp;
    }

    @Override
    public Object peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Error");
        }
        return this.stack.getHead().getData();
    }

    @Override
    public void push(Object element) {
        this.stack.add(0, element);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    public void clear(){
        this.stack.clear();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }
}
import java.util.Scanner;

public class Evaluator implements IExpressionEvaluator {

    MyStack stack = new MyStack();
    int a, b, c;

    public static void main(String[] args) {
        Evaluator eval = new Evaluator();

        try (Scanner sc = new Scanner(System.in)) {

            String infix = sc.nextLine().trim();
            for (int i = 0; i < 3; i++) {
                String line = sc.nextLine().trim();
                switch (line.charAt(0)) {
                    case 'a':
                        eval.a = Integer.parseInt(line.substring(2));
                        break;
                    case 'b':
                        eval.b = Integer.parseInt(line.substring(2));
                        break;
                    case 'c':
                        eval.c = Integer.parseInt(line.substring(2));
                        break;
                }
            }

            try {
                String postfix = eval.infixToPostfix(infix);
                int result = eval.evaluate(postfix);
                System.out.println(eval.restorePostfix(postfix));
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    /** generate precedence to help compare operators
     *
     * @param operator the operator to generate its precedence
     * @return a number describing the precedence
     */
    private int precedence(char operator) {
        switch (operator) {
            case '(': return 0;
            case '+':
            case '-':
            case '~': return 1;
            case '*':
            case '/' : return 2;
            case '^': return 3;
            default: return -1;
        }
    }

    /** remove the special character used in calculations for unary minus
     *
     * @param postfix the postfix expression
     * @return the postfix expression after turning the unary minus into a normal minus
     */
    public String restorePostfix(String postfix) {
        StringBuilder result = new StringBuilder(postfix);

        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '~') {
                result.setCharAt(i, '-');
            }
        }
        return result.toString();
    }

    @Override
    public String infixToPostfix(String expression) {
        stack.clear();
        if (expression == null || expression.isEmpty()) {
            throw new RuntimeException("Empty expression");
        }

        StringBuilder postfix = new StringBuilder();
        boolean unary = true;
        int parenthesesCount = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            switch (c) {
                case 'a':
                case 'b':
                case 'c':
                    postfix.append(c);
                    unary = false;
                    break;

                case '+':
                case '*':
                case '/':
                    // Process operators on stack with higher or equal precedence
                    while (!stack.isEmpty() && precedence(c) <= precedence((char) stack.peek())) {
                        postfix.append(stack.pop());
                    }
                    stack.push(c);
                    unary = true;
                    break;

                case '^': // Right-associative operator
                    // Process operators with HIGHER precedence only
                    while (!stack.isEmpty() && precedence(c) < precedence((char) stack.peek())) {
                        postfix.append(stack.pop());
                    }
                    stack.push(c);
                    unary = true;
                    break;

                case '-':
                    if (unary) {
                        // Check for double minus
                        if (i + 1 < expression.length() && expression.charAt(i + 1) == '-') {
                            // Double unary minus cancels out
                            i++; // Skip next minus
                            // Continue to next character, keeping unary as true
                        } else {
                            // Single unary minus
                            stack.push('~');
                        }
                    } else {
                        // Check for double minus after an operand
                        if (i + 1 < expression.length() && expression.charAt(i + 1) == '-') {
                            // "--" after an operand becomes "+"
                            i++; // Skip next minus

                            // Treat as a plus operator
                            while (!stack.isEmpty() && precedence('+') <= precedence((char) stack.peek())) {
                                postfix.append(stack.pop());
                            }
                            stack.push('+');
                        } else {
                            // Regular binary minus
                            while (!stack.isEmpty() && precedence('-') <= precedence((char) stack.peek())) {
                                postfix.append(stack.pop());
                            }
                            stack.push('-');
                        }
                        unary = true;
                    }
                    break;

                case '(':
                    stack.push(c);
                    unary = true;
                    parenthesesCount++;
                    break;

                case ')':
                    if (parenthesesCount <= 0) {
                        throw new RuntimeException("Empty parentheses");
                    }
                    boolean foundMatchingParenthesis = false;
                    while (!stack.isEmpty()) {
                        char top = (char) stack.peek();
                        if (top == '(') {
                            stack.pop(); // Remove opening parenthesis
                            foundMatchingParenthesis = true;
                            break;
                        }
                        postfix.append(stack.pop());
                    }

                    if (!foundMatchingParenthesis) {
                        throw new RuntimeException("Unmatched right parenthesis");
                    }
                    parenthesesCount--;
                    unary = false;
                    break;

                case ' ': // Skip spaces
                    break;

                default:
                    throw new RuntimeException("Invalid character: " + c);
            }
        }

        if (parenthesesCount > 0) {
            throw new RuntimeException("Unmatched left parentheses");
        }
        // Pop remaining operators
        while (!stack.isEmpty()) {
            char top = (char) stack.peek();
            if (top == '(') {
                throw new RuntimeException("Unmatched left parenthesis");
            }
            postfix.append(stack.pop());
        }

        if (postfix.length() == 0){
            throw new RuntimeException("Empty postfix");
        }

        return postfix.toString();
    }

    @Override
    public int evaluate(String expression) {

        if (expression == null || expression.isEmpty()) {
            throw new RuntimeException("Empty expression");
        }
        stack.clear();

        for (char c : expression.toCharArray()) {
            switch (c) {
                case 'a': stack.push(this.a); break;
                case 'b': stack.push(this.b); break;
                case 'c': stack.push(this.c); break;

                case '+': {
                    if (stack.size() < 2) throw new RuntimeException("Invalid expression");
                    int last = (int) stack.pop();
                    int first = (int) stack.pop();
                    stack.push(first + last);
                    break;
                }
                case '-': {
                    if (stack.size() < 2) throw new RuntimeException("Invalid expression");
                    int last = (int) stack.pop();
                    int first = (int) stack.pop();
                    stack.push(first - last);
                    break;
                }
                case '~': { // Unary minus
                    if (stack.isEmpty()) throw new RuntimeException("Invalid expression");
                    int operand = (int) stack.pop();
                    stack.push(-operand);
                    break;
                }
                case '*': {
                    if (stack.size() < 2) throw new RuntimeException("Invalid expression");
                    int last = (int) stack.pop();
                    int first = (int) stack.pop();
                    stack.push(first * last);
                    break;
                }
                case '^': {
                    if (stack.size() < 2) throw new RuntimeException("Invalid expression");
                    int exponent = (int) stack.pop();
                    int base = (int) stack.pop();
                    stack.push((int)Math.pow(base, exponent));
                    break;
                }
                case '/': {
                    if (stack.size() < 2) throw new RuntimeException("Invalid expression");
                    int divisor = (int) stack.pop();
                    int dividend = (int) stack.pop();
                    if (divisor == 0) {
                        throw new RuntimeException("Division by zero");
                    }
                    stack.push(dividend / divisor);
                    break;
                }
                default:
                    throw new RuntimeException("Invalid character in postfix: " + c);
            }
        }

        // There should be exactly one value in stack at the end
        if (stack.size() != 1) {
            throw new RuntimeException("Invalid expression");
        }

        return (int)stack.pop();
    }
}
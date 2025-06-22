import java.util.Scanner;

public class PolynomialSolver implements IPolynomialSolver {

    SingleLinkedList A = new SingleLinkedList();
    SingleLinkedList B = new SingleLinkedList();
    SingleLinkedList C = new SingleLinkedList();
    SingleLinkedList R = new SingleLinkedList();

    public static void main(String[] args) {
        PolynomialSolver ps = new PolynomialSolver();

        try(Scanner sc = new Scanner(System.in)) {

            while (sc.hasNextLine()) { // input not empty

                String command = sc.nextLine().trim();
                switch (command) {
                    case "set": {
                        char poly = sc.nextLine().charAt(0); // polynomial name
                        String input = sc.nextLine().trim(); // list of coefficients

                        String trimmed = input.substring(1, input.length() - 1).trim();
                        String[] numberStrings = trimmed.split(",");
                        int[][] terms = new int[numberStrings.length][2];
                        for (int i = 0; i < numberStrings.length; i++) {
                            int numberInt = Integer.parseInt(numberStrings[i].trim());
                            terms[i][0] = numberInt;
                            terms[i][1] = numberStrings.length - i - 1;
                        }
                        ps.setPolynomial(poly, terms);
                        break;
                    }
                    case "print": {
                        char poly = sc.nextLine().charAt(0);
                        System.out.println(ps.print(poly));
                        break;
                    }
                    case "add": {
                        char poly1 = sc.nextLine().charAt(0);
                        char poly2 = sc.nextLine().charAt(0);
                        int[][] additionResult = ps.add(poly1, poly2);

                        ps.clearPolynomial('R');
                        ps.setPolynomial('R', additionResult);
                        System.out.println(ps.print('R'));
                        break;
                    }
                    case "sub": {
                        char poly1 = sc.nextLine().charAt(0);
                        char poly2 = sc.nextLine().charAt(0);
                        int[][] subtractionResult = ps.subtract(poly1, poly2);

                        ps.clearPolynomial('R');
                        ps.setPolynomial('R', subtractionResult);
                        System.out.println(ps.print('R'));
                        break;
                    }
                    case "mult": {
                        char poly1 = sc.nextLine().charAt(0);
                        char poly2 = sc.nextLine().charAt(0);
                        int[][] multResult = ps.multiply(poly1, poly2);

                        ps.clearPolynomial('R');
                        ps.setPolynomial('R', multResult);
                        System.out.println(ps.print('R'));
                        break;
                    }
                    case "clear": {
                        char poly = sc.nextLine().charAt(0);
                        ps.clearPolynomial(poly);
                        System.out.println("[]");
                        break;
                    }
                    case "eval": {
                        if (!sc.hasNextLine()) break;
                        char poly = sc.nextLine().trim().charAt(0);
                        if (!sc.hasNextLine()) break;
                        int value = Integer.parseInt(sc.nextLine().trim());
                        float result = ps.evaluatePolynomial(poly, value);
                        System.out.println(Math.round(result));
                        break;

                    }
                    default: {
                        System.out.println("Error");
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Override
    public void setPolynomial(char poly, int[][] terms) {
        switch (poly) {
            case 'A':
                A.clear();
                for (int[] term : terms) {
                    A.add(term);
                }
                break;
            case 'B':
                B.clear();
                for (int[] term : terms) {
                    B.add(term);
                }
                break;
            case 'C':
                C.clear();
                for (int[] term : terms) {
                    C.add(term);
                }
                break;
            case 'R':
                R.clear();
                for (int[] term : terms) {
                    R.add(term);
                }
                break;
        }
    }


    private int[][] getTerms(char poly) {
        switch (poly){
            case 'A': {
                if (A.isEmpty()) throw new RuntimeException("Error");
                int[][] terms = new int[A.size()][2];
                for (int i = 0; i < A.size(); i++) {
                    terms[i] = (int[]) A.get(i); // Casting object to int[]
                }
                return terms;
            }
            case 'B': {
                if (B.isEmpty()) throw new RuntimeException("Error");
                int[][] terms = new int[B.size()][2];
                for (int i = 0; i < B.size(); i++) {
                    terms[i] = (int[]) B.get(i); // Casting object to int[]
                }
                return terms;
            }
            case 'C': {
                if (C.isEmpty()) throw new RuntimeException("Error");
                int[][] terms = new int[C.size()][2];
                for (int i = 0; i < C.size(); i++) {
                    terms[i] = (int[]) C.get(i); // Casting object to int[]
                }
                return terms;
            }
            case 'R': {
                if (R.isEmpty()) throw new RuntimeException("Error");
                int[][] terms = new int[R.size()][2];
                for (int i = 0; i < R.size(); i++) {
                    terms[i] = (int[]) R.get(i);
                }
                return terms;
            }
            default: {
                return null;
            }
        }
    }


    @Override
    public String print(char poly) {
        StringBuilder sb = new StringBuilder();
        int[][] terms = getTerms(poly);
        if (terms == null) {
            //throw new RuntimeException()("Error");
            return "[]";
        }
        int nonZeroCount = 0;
        boolean first = true;
        for (int[] term : terms) {
            if (!first && term[0] > 0) sb.append('+'); // insert + symbol between positive terms

            if (term[0] == 0) continue;

            nonZeroCount++;
            // if coefficient is not zero, and not one in front of a variable
            if (!(term[0] == 1 && term[1] != 0))sb.append(term[0]);
            
            if (term[1] != 0) sb.append('x'); // if power is not zero
            if (term[1] != 0 && term[1] != 1) sb.append('^').append(term[1]);

            first = false;
        }
        if (nonZeroCount == 0) return "0";
        return sb.toString();
    }

    @Override
    public void clearPolynomial(char poly) {
        switch (poly) {
            case 'A':
                A.clear();
                break;
            case 'B':
                B.clear();
                break;
            case 'C':
                C.clear();
                break;
            case 'R':
                R.clear();
                break;
        }
    }


    @Override
    public float evaluatePolynomial(char poly, float value) {
        float result = 0;
        int[][] terms = getTerms(poly);
        for (int i = 0; i < terms.length; i++) {
            int coefficient = terms[i][0];
            int exp = terms[i][1];
            result += coefficient * Math.pow(value, exp);
        }
        return result;
    }

    @Override
    public int[][] add(char poly1, char poly2) {
        int[][] terms1 = getTerms(poly1);
        int[][] terms2 = getTerms(poly2);
        if (terms1 == null || terms2 == null) {
            throw new RuntimeException("Error");
        }
        int numOfTerms = Math.max(terms1.length,terms2.length);
        int[][] result = new int[numOfTerms][2];
        for (int i = 0; i < numOfTerms;i++){
            int num1 = (i < numOfTerms - terms1.length) ? 0 : terms1[i - (numOfTerms-terms1.length)][0];
            int num2 = (i < numOfTerms - terms2.length) ? 0 : terms2[i - (numOfTerms-terms2.length)][0];
            result[i][0] = num1 + num2;
            if (numOfTerms == terms1.length){
                result[i][1] = terms1[i][1];
            }
            else{
                result[i][1] = terms2[i][1];
            }
        }
        return result;
    }

    @Override
    public int[][] subtract(char poly1, char poly2) {
        int[][] terms1 = getTerms(poly1);
        int[][] terms2 = getTerms(poly2);
        if (terms1 == null || terms2 == null) {
            throw new RuntimeException("Error");
        }
        int numOfTerms = Math.max(terms1.length,terms2.length);
        int[][] result = new int[numOfTerms][2];
        for (int i = 0; i < numOfTerms;i++){
            int num1 = (i < numOfTerms - terms1.length) ? 0 : terms1[i - (numOfTerms-terms1.length)][0];
            int num2 = (i < numOfTerms - terms2.length) ? 0 : terms2[i - (numOfTerms-terms2.length)][0];
            result[i][0] = num1 - num2;
            if (numOfTerms == terms1.length){
                result[i][1] = terms1[i][1];
            }
            else{
                result[i][1] = terms2[i][1];
            }
        }
        return result;
    }

    private SingleLinkedList getPolynomial(char poly){

        switch (poly) {
            case 'A' :
                return A;
            case 'B':
                return B;
            case 'C':
                return C;
            case 'R':
                return R;
            default:
                return null;
        }
    }
    
    private void sortedAddNumbers(SingleLinkedList list, Object element) {
        SNode newNode = new SNode(element);
        int newVal = ((int[]) newNode.getData())[1];

        // Case 1: Insert at head if list is empty or new element is larger than head
        if (list.getHead() == null || ((int[]) list.getHead().getData())[1] < newVal) {
            list.add(0, newNode);
            return;
        }
    
        // Case 2: Insert in the correct sorted position
        SNode curr = list.getHead();
        while (curr.getNext() != null && ((int[]) curr.getNext().getData())[1] >= newVal) {
            curr = curr.getNext();
        }
    
        newNode.setNext(curr.getNext());
        curr.setNext(newNode);
        list.incrementSize();
    }


    @Override
    public int[][] multiply(char poly1, char poly2) {
        // I utilize the accumulator list R in this method as I don't know the final number of terms ?
        R.clear();
        SingleLinkedList firstList = getPolynomial(poly1);
        SingleLinkedList secondList = getPolynomial(poly2);
        if (firstList == null || secondList == null) {
            throw new RuntimeException("Error");
        }
        SNode ptr1 = firstList.getHead();
        SNode ptr2 = secondList.getHead();
        while(ptr1 != null){
            while(ptr2 != null){
                int coeff = ((int[]) ptr1.getData())[0] * ((int[]) ptr2.getData())[0];
                int exp = ((int[]) ptr1.getData())[1] + ((int[]) ptr2.getData())[1];
                int[] x = {coeff,exp};
                sortedAddNumbers(R,x);
                ptr2 = ptr2.getNext();
            }
            ptr2 = secondList.getHead();
            ptr1 = ptr1.getNext();
        }

        // Now we want to add like terms
        SNode ptr3 = R.getHead();
        int finalNumberOfTerms = ((int[]) ptr3.getData())[1];
        int[][] finalResult = new int[finalNumberOfTerms+1][2];
        int sumLikeTerms = 0;
        int currentPower = finalNumberOfTerms;
        int exp = ((int[]) ptr3.getData())[1];
        while(ptr3!=null){
            while(ptr3!=null && currentPower == exp){
                sumLikeTerms+= ((int[]) ptr3.getData())[0];
                ptr3 = ptr3.getNext();
                if (ptr3 != null){
                    exp = ((int[]) ptr3.getData())[1];
                }
            }
            finalResult[finalNumberOfTerms - currentPower][0] = sumLikeTerms;
            finalResult[finalNumberOfTerms - currentPower][1] = currentPower;
            sumLikeTerms = 0;
            currentPower = exp;
        }
        return finalResult;
    }
}

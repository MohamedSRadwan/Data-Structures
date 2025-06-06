public class Main {
    public static void main(String[] args) {
        int[] reversable = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        reversable = reverse(reversable);
        for (int x : reversable)
            System.out.print(x + " ");
        System.out.println("*****************");
        int[] sums = sumEvenOdd(reversable);
        for (int x : sums)
            System.out.print(x + " ");
        System.out.println("*****************");
        System.out.println(average(reversable));
        System.out.println("*****************");
        reversable = moveValue(reversable, 3);
        for (int x : reversable)
            System.out.print(x + " ");
        System.out.println("*****************");

        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        matrix = transpose(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("*****************");

        int n = 5;
        System.out.println(fibonacci(n));
    }
    public static int[] reverse(int[] arr){
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }
    public static int[] sumEvenOdd(int[] arr){
        int[] sums = new int[2]; // automatically initialized to 0
        for (int i = 0; i < arr.length; i++){
            if (arr[i] % 2 == 0){
                sums[0] += arr[i];
            }
            else{
                sums[1] += arr[i];
            }
        }
        return sums;
    }
    public static double average(int[] arr){
        if (arr.length == 0) return 0;
        double sum = 0;
        for (int x : arr)
            sum += x;
        return sum / (double)arr.length;
    }

    public static int[] moveValue(int[] arr, int val){
        int k = 0;
        int[] result = new int[arr.length];
        for (int x : arr){
            if (x != val){
                result[k++] = x;
            }
        }
        for (int i = k; i < arr.length; i++){
            result[i] = val;
        }
        return result;
    }

    public static int[][] transpose(int[][] arr){
        int[][] result = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[0].length; j++){
                result[j][i] = arr[i][j];
            }
        }
        return result;
    }

    public static int fibonacci(int n){
        if (n == 1) return 0;
        int[] arr = new int[n];
        arr[0] = 0;
        arr[1] = 1;
        for(int i = 2; i < n; i++){
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n - 1];
    }
}
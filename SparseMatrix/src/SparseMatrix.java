public class SparseMatrix {
    private final int[] xCoord = new int[100];
    private final int[] yCoord = new int[100];
    private final int[] data = new int[100];
    private int size;
    private final int[] matrixSize = new int[2];

    public static void main(String[] args) {
        int[][] matrix = new int [][] {
                {1, 2, 3, 0, 5, 6},
                {4, 0, 0, 5, 6, 0},
                {3, 0, 0, 0, 8, 0}
        };
        SparseMatrix sm = new SparseMatrix(matrix);
        System.out.println(sm.getValue(0, 0));
        sm.setValue(0, 3, 4);
        System.out.println(sm);

    }
    public SparseMatrix(int[][] matrix) {
        matrixSize[0] = matrix.length;
        matrixSize[1] = matrix[0].length;
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    xCoord[k] = i;
                    yCoord[k] = j;
                    data[k] = matrix[i][j];
                    k++;
                }
            }
        }
        size = k;
    }

    private int getIndex(int x, int y) {
        int start = 0, end = size - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (xCoord[mid] > x || yCoord[mid] > y) {
                end = mid - 1;
            }
            else if (xCoord[mid] < x || yCoord[mid] < y) {
                start = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
    public int getSize() {
        return size;
    }

    public int getValue(int x, int y) {
        if (x < 0 || x > matrixSize[0] || y < 0 || y > matrixSize[1]) {
            return -1;
        }
        int index = getIndex(x, y);
        if (index == -1) {
            return 0;
        }
        return data[index];
    }

    public void setValue(int x, int y, int value) {
        if (x < 0 || x > matrixSize[0] || y < 0 || y > matrixSize[1]) {
            return;
        }
        int index = getIndex(x, y);
        if (index == -1) {
            // DONE: insert
            int start = 0, end = size - 1, ans = size - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (xCoord[mid] > x || yCoord[mid] > y) { // upper bound
                    ans = mid;
                    end = mid - 1;
                }
                else {
                    start = mid + 1;
                }
            }
            // Array shift
            for (int i = size; i > ans; i--){
                xCoord[i] = xCoord[i - 1];
                yCoord[i] = yCoord[i - 1];
                data[i] = data[i - 1];
            }
            // inserting
            xCoord[ans] = x;
            yCoord[ans] = y;
            data[ans] = value;

            size++;
            return;
        }

        if (value == 0){
            // DONE: erase
            // Array shift
            for (int i = index; i < size; i++) {
                xCoord[i] = xCoord[i + 1];
                yCoord[i] = yCoord[i + 1];
                data[i] = data[i + 1];
            }
            size--;
            return;
        }
        else {
            // DONE: modify
            data[index] = value;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            s.append(xCoord[i]).append(", ").append(yCoord[i]).append(" : ").append(data[i]).append("\n");
        }
        return s.toString();
    }
}

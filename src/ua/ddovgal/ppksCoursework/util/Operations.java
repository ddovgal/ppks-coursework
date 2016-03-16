package ua.ddovgal.ppksCoursework.util;

public class Operations {

    private static int N;
    private static int P;
    private static int H;

    public Operations(int N, int P) {
        Operations.N = N;
        Operations.P = P;
        H = N / P;
    }

    public static int getN() {
        return N;
    }

    public static int[][] createMatrix(int num) {
        int[][] newMatrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newMatrix[i][j] = num;
            }
        }
        return newMatrix;
    }

    public static int[] createVector(int num) {
        int[] newVector = new int[N];
        for (int i = 0; i < N; i++) {
            newVector[i] = num;
        }
        return newVector;
    }

    public static void printMatrix(int[][] matrix) {
        if (N > 6) return;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void copyMatrix(int[][] matrixSource, int[][] destMatrix) {
        for (int i = 0; i < N; i++) {
            System.arraycopy(matrixSource[i], 0, destMatrix[i], 0, N);
        }
    }

    public static int[][] multiMatrix(int processNum, int[][] matrix1, int[][] matrix2) {
        int[][] resMatrix = new int[N][N];

        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == P) {
            end = N;
        }

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    resMatrix[i][j] = resMatrix[i][j] + matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return resMatrix;
    }

    public static int minOfMatrix(int processNum, int[][] matrix) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == P) {
            end = N;
        }

        int rez = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] < rez) rez = matrix[i][j];
            }
        }
        return rez;
    }

    public static void multiMatrixConst(int processNum, int[][] matrix, int constant) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == P) {
            end = N;
        }

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] *= constant;
            }
        }
    }

    public static void addMatrix(int processNum, int[][] rezMatrix, int[][] left, int[][] right) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == P) {
            end = N;
        }

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                rezMatrix[i][j] = left[i][j] + right[i][j];
            }
        }
    }

}

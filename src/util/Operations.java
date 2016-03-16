package util;

public class Operations {

    private int N;
    private int H;

    public Operations(int N, int P) {
        this.N = N;
        H = N / P;
    }

    public int[][] createMatrix(int num) {
        int[][] newMatrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newMatrix[i][j] = num;
            }
        }
        return newMatrix;
    }

    public int[] createVector(int num) {
        int[] newVector = new int[N];
        for (int i = 0; i < N; i++) {
            newVector[i] = num;
        }
        return newVector;
    }

    public void printMatrix(int[][] matrix) {
        if (N > 6) return;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void copyMatrix(int[][] matrixSource, int[][] destMatrix) {
        for (int i = 0; i < N; i++) {
            System.arraycopy(matrixSource[i], 0, destMatrix[i], 0, N);
        }
    }

    public int[][] multiMatrix(int processNum, int[][] matrix1, int[][] matrix2) {
        int[][] resMatrix = new int[N][N];

        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == 4) {
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

    public int minOfVector(int processNum, int[] vector) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == 4) {
            end = N;
        }

        int rez = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            if (vector[i] < rez) rez = vector[i];
        }
        return rez;
    }

    public int maxOfVector(int processNum, int[] vector) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == 4) {
            end = N;
        }

        int rez = 0;
        for (int i = start; i < end; i++) {
            if (vector[i] > rez) rez = vector[i];
        }
        return rez;
    }

    public void multiMatrixConst(int processNum, int[][] matrix, int constant) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == 4) {
            end = N;
        }

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] *= constant;
            }
        }
    }

    public void addMatrix(int processNum, int[][] rezMatrix, int[][] matrix2) {
        int start = (processNum - 1) * H;
        int end = processNum * H;
        if (processNum == 4) {
            end = N;
        }

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                rezMatrix[i][j] += matrix2[i][j];
            }
        }
    }

}

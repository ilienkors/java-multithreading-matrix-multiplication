import java.util.concurrent.ExecutionException;

public class SyncProgram {
    static int n = 2560;

    static int[][] matrixA = new int[n][n];
    static int[][] matrixB = new int[n][n];
    static int[][] matrixRes = new int[n][n];

    public static void main(final String[] args) throws InterruptedException, ExecutionException {
        matrixGen();

        long startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            rowMultiplication(i, matrixA, matrixB, matrixRes);
        }
        System.out.println((System.nanoTime() - startTime)/1000);
    }

    private static void matrixGen() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = 3;
                matrixB[i][j] = 3;
                matrixRes[i][j] = 0;
            }
        }
    }

    private static void rowMultiplication(int rowNumber, int[][] matrixA, int[][] matrixB, int[][] matrixRes) {
        int sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrixA[rowNumber][j] * matrixB[j][i];
            }
            matrixRes[rowNumber][i] = sum;
        }
    }
}

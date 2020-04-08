import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class Program {
    static int n = 2560;

    static int[][] matrixA = new int[n][n];
    static int[][] matrixB = new int[n][n];
    static int[][] matrixRes = new int[n][n];

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        matrixGen();
        long startTime = System.nanoTime();
        rowMultiplication();
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

    private static void rowMultiplication() throws InterruptedException {

        ExecutorService s = Executors.newFixedThreadPool(n);

        List<Multiplication> multis = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Multiplication task = new Multiplication(i, matrixA, matrixB, matrixRes);
            multis.add(task);
        }

        List<Future<Object>> futureMulti = s.invokeAll(multis);

        s.shutdown();
        while (!s.isTerminated()) {
        }

    }
}

class Multiplication implements Callable<Object> {

    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixRes;

    private int ARow;

    public Multiplication(int rowNumber, int[][] matrixA, int[][] matrixB, int[][] matrixRes) {
        this.ARow = rowNumber;
        this.matrixB = matrixB;
        this.matrixA = matrixA;
        this.matrixRes = matrixRes;
    }

    @Override
    public Object call() throws Exception {
        int sum;
        for (int i = 0; i < matrixA.length; i++) {
            sum = 0;
            for (int j = 0; j < matrixA.length; j++) {
                sum += matrixA[ARow][j] * matrixB[j][i];
            }
            matrixRes[ARow][i] = sum;
        }
        return null;
    }
}

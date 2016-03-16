package ua.ddovgal.ppksCoursework;

import ua.ddovgal.ppksCoursework.util.Operations;
import ua.ddovgal.ppksCoursework.util.ThreadType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static int[][] MA;
    public static int[][] MC;
    public static int[][] MO;
    public static int[][] MT;
    public static AtomicInteger alpha = new AtomicInteger();
    public static AtomicInteger minMO = new AtomicInteger(Integer.MAX_VALUE);
    public static int matrixNumber = 2;
    public static int alphaNumber = 1;
    private static int[][] MB;
    private static int N = 6;
    private static int P = 2;

    public static synchronized int[][] getMB() {
        return MB;
    }

    public static void setMB(int[][] MB) {
        Main.MB = MB;
    }


    public static void main(String[] args) throws InterruptedException {

        try {
            for (int i = 0; i < args.length; i++) {
                String s = args[i];
                switch (s) {
                    case "-p": {
                        P = Integer.parseInt(args[++i]);
                        break;
                    }
                    case "-n": {
                        N = Integer.parseInt(args[++i]);
                        break;
                    }
                    case "-m": {
                        matrixNumber = Integer.parseInt(args[++i]);
                        break;
                    }
                    case "-a": {
                        alphaNumber = Integer.parseInt(args[++i]);
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Enter some parameters after '-n', '-p', '-m' or '-a'");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parameters after '-n', '-p', '-m' or '-a' not integers");
        }

        if (N % P != 0) throw new IllegalArgumentException("Yor N and P are incorrect. N mod P must be zero");

        System.out.println("All params are correct. Calculation is starting...");

        MA = new int[N][N];
        MC = new int[N][N];
        MO = new int[N][N];
        MT = new int[N][N];
        MB = new int[N][N];

        final CountDownLatch inputEventCounter = new CountDownLatch(2);
        final CountDownLatch minMaxEventCounter = new CountDownLatch(P);
        final CountDownLatch resultEventCounter = new CountDownLatch(P - 1);

        Operations justInitOperations = new Operations(N, P);

        Thread[] threads = new Thread[P];
        threads[0] = new Thread(new ThreadPerformer(0, ThreadType.IN_OUT,
                inputEventCounter, minMaxEventCounter, resultEventCounter));
        threads[0].start();
        for (int i = 1; i < P - 1; i++) {
            threads[i] = new Thread(new ThreadPerformer(i, ThreadType.NONE,
                    inputEventCounter, minMaxEventCounter, resultEventCounter));
            threads[i].start();
        }
        threads[P - 1] = new Thread(new ThreadPerformer(P - 1, ThreadType.IN,
                inputEventCounter, minMaxEventCounter, resultEventCounter));
        threads[P - 1].start();

        for (Thread t : threads) t.join();

        System.out.println("End of all calculations");
    }
}

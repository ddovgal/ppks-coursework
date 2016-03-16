import util.Operations;
import util.ThreadType;

import java.util.concurrent.CountDownLatch;

public class ThreadPerformer implements Runnable {

    private int id;
    private ThreadType type;
    /*private int N;
    private int H;*/

    private CountDownLatch in;
    private CountDownLatch minMax;
    private CountDownLatch result;

    private Operations operations;

    private int[][] localMB;
    private int localAlpha;
    private int localMinMO;
    private int MATRIX_NUMBER = 1;

    public ThreadPerformer(int N, int P, int id, ThreadType type,
                           CountDownLatch in, CountDownLatch minMax, CountDownLatch result) {
        /*this.N = N;
        H = N/P;*/
        this.type = type;
        this.id = id;
        this.in = in;
        this.minMax = minMax;
        this.result = result;
        operations = new Operations(N, P);
        localMB = new int[N][N];
    }

    @Override
    public void run() {
        System.out.printf("Thread %d started", id);

        //...
        switch (type) {
            case IN_OUT: {
                Main.setMB(operations.createMatrix(MATRIX_NUMBER));
                Main.MO = operations.createMatrix(MATRIX_NUMBER);
                in.countDown();
                break;
            }
            case IN: {
                Main.MC = operations.createMatrix(MATRIX_NUMBER);
                Main.alpha.set(Main.initAlpha);
                in.countDown();
                break;
            }
            case NONE: {
                try {
                    in.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //...

        //...
        minMax.countDown();
        try {
            minMax.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //...

        //...
        if (type != ThreadType.IN_OUT) {
            result.countDown();
        } else try {
            result.await();
            operations.printMatrix(Main.MA);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //...

        System.out.printf("Thread %d finished", id);
    }
}

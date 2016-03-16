package ua.ddovgal.ppksCoursework;

import ua.ddovgal.ppksCoursework.util.Operations;
import ua.ddovgal.ppksCoursework.util.ThreadType;

import java.util.concurrent.CountDownLatch;

public class ThreadPerformer implements Runnable {

    private final CountDownLatch in;
    private final CountDownLatch minMax;
    private final CountDownLatch result;
    private int id;
    private ThreadType type;
    private int[][] localMB;
    private int localAlpha;
    private int localMinMO;

    public ThreadPerformer(int id, ThreadType type,
                           CountDownLatch in, CountDownLatch minMax, CountDownLatch result) {
        this.type = type;
        this.id = id + 1;
        this.in = in;
        this.minMax = minMax;
        this.result = result;
        localMB = new int[Operations.getN()][Operations.getN()];
    }

    @Override
    public void run() {
        System.out.printf("Thread %d started\n", id);

        switch (type) {
            case IN_OUT: {
                Main.MA = Operations.createMatrix(Main.matrixNumber);
                Main.setMB(Operations.createMatrix(Main.matrixNumber));
                Main.MO = Operations.createMatrix(Main.matrixNumber);
                in.countDown();
                break;
            }
            case IN: {
                Main.MC = Operations.createMatrix(Main.matrixNumber);
                Main.alpha.set(Main.alphaNumber);
                Main.MT = Operations.createMatrix(Main.matrixNumber);
                in.countDown();
                break;
            }
        }
        try {
            in.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        localMinMO = Operations.minOfMatrix(id, Main.MO);

        synchronized (Main.minMO) {
            if (Main.minMO.get() > localMinMO) Main.minMO.set(localMinMO);
        }

        minMax.countDown();
        try {
            minMax.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        localMinMO = Main.minMO.get();
        localAlpha = Main.alpha.get();
        Operations.copyMatrix(Main.getMB(), localMB);

        Operations.multiMatrixConst(id, Main.MT, localMinMO);
        int[][] MBMC = Operations.multiMatrix(id, Main.MC, localMB);
        Operations.multiMatrixConst(id, MBMC, localAlpha);
        Operations.addMatrix(id, Main.MA, MBMC, Main.MT);

        if (type != ThreadType.IN_OUT) {
            result.countDown();
        } else try {
            result.await();
            Operations.printMatrix(Main.MA);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread %d finished\n", id);
    }
}

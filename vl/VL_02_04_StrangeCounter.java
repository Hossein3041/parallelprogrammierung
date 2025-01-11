package vl;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class VL_02_04_StrangeCounter {
    private final static int INCREMENTERS = 15;
    private final static int RUNS = 50;

    private static long counter = 0;

    protected static class Incrementer implements Runnable {
        private final CountDownLatch start, end;

        public Incrementer(CountDownLatch start, CountDownLatch end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            try {
                start.await();
                for (int i = 0; i < RUNS; ++i) {
                    ++counter;
                }
                end.countDown();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);

        try {
            System.out.println("Starting with counter = " + counter);

            startLatch.countDown();

            IntStream.range(0, INCREMENTERS).parallel().forEach(i -> {
                for (int j = 0; j < RUNS; ++j)
                    ++counter;
                endLatch.countDown();
            });

            endLatch.await();
            long totalInc = RUNS * INCREMENTERS;
            System.out.println("Finished after " + totalInc + " increments width counter = " + counter);
        } catch (Exception e) {
        }
    }
}










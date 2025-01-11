package vl;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class VL_02_04_1_StrangeCounter {
    private final static int INCREMENTERS = 2;
    private final static int RUNS = 5;
    private static long counter = 0;

    protected static class Incrementer implements Runnable {
        private final CountDownLatch start, end;

        public Incrementer(CountDownLatch start, CountDownLatch end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                start.await();  // Wartet, bis der Start freigegeben wird
                for (int i = 0; i < RUNS; i++) {
                    counter++;  // Zählt den Counter hoch
                }
                end.countDown();  // Signalisiert, dass dieser Thread fertig ist
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);

        Thread.Builder tb = Thread.ofPlatform();
        // Alternative: tb = Thread.ofVirtual(); für virtuelle Threads

        for (int i = 0; i < INCREMENTERS; ++i) {
            tb.start(new Incrementer(startLatch, endLatch));
        }

        try {
            System.out.println("Starting with counter = " + counter);
            startLatch.countDown(); // Startet alle Threads gleichzeitig
            endLatch.await(); // Wartet, bis alle Threads fertig sind

            long totalInc = RUNS * INCREMENTERS;
            System.out.println("Finished after " + totalInc + " increments with counter = " + counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

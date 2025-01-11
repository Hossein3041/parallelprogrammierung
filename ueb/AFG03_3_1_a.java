package ueb;

import vl.VL_02_04_1_StrangeCounter;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;


interface CounterInterface {
    long get();

    long incrementAndGet();

    default void check(long desired) {
        if (get() != desired) {
            System.out.println("Counter mismatch! Expected: " + desired + ", but got: " + get());
        }
    }
}

class MyLong implements CounterInterface {
    private long counter;

    @Override
    public long get() {
        return counter;
    }

    @Override
    public long incrementAndGet() {
        return ++counter;
    }

}

public class AFG03_3_1_a {
    private final static int INCREMENTERS = 2;
    private final static int RUNS = 5;

    protected static class Incrementer implements Runnable {
        private final CountDownLatch start, end;
        private final CounterInterface counter;

        public Incrementer(CountDownLatch start, CountDownLatch end, CounterInterface counter) {
            this.start = start;
            this.end = end;
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                start.await();  // Wartet, bis der Start freigegeben wird
                for (int i = 0; i < RUNS; i++) {
                    counter.incrementAndGet();
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

        CounterInterface counter = new MyLong();

        Thread.Builder tb = Thread.ofPlatform();
        // Alternative: tb = Thread.ofVirtual(); für virtuelle Threads

        for (int i = 0; i < INCREMENTERS; ++i) {
            tb.start(new AFG03_3_1_a.Incrementer(startLatch, endLatch, counter));
        }

        try {
            System.out.println("Starting with counter = " + counter.get());
            startLatch.countDown(); // Startet alle Threads gleichzeitig
            endLatch.await(); // Wartet, bis alle Threads fertig sind

            long totalInc = RUNS * INCREMENTERS;
            System.out.println("Finished after " + totalInc + " increments with counter = " + counter.get());
            counter.check(totalInc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

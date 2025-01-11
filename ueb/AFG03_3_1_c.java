package ueb;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

interface AFG03_3_1_c_CounterInterface {
    long get();

    long incrementAndGet();

    default void check(long desired) {
        if (get() != desired)
            System.out.println("Counter mismatch! Expected: " + desired + ", but got: " + get());
    }
}

class AFG03_3_1_MyLongAtomic implements AFG03_3_1_c_CounterInterface {
    private final AtomicLong counter = new AtomicLong();

    @Override
    public long get() {
        return counter.get();
    }

    @Override
    public long incrementAndGet() {
        return counter.incrementAndGet();
    }
}

public class AFG03_3_1_c {
    private final static int INCREMENTERS = 1000;
    private final static int RUNS = 100;

    // Zähler für die Anzahl der gestarteten Threads
    private static AtomicInteger threadCount = new AtomicInteger(0);

    protected static class Incrementer implements Runnable {
        private final CountDownLatch start, end;
        private final AFG03_3_1_c_CounterInterface counter;

        public Incrementer(CountDownLatch start, CountDownLatch end, AFG03_3_1_c_CounterInterface counter) {
            this.start = start;
            this.end = end;
            this.counter = counter;
        }

        @Override
        public void run() {
            threadCount.incrementAndGet();  // Thread-Start zählen
            try {
                start.await();
                for (int i = 0; i < RUNS; ++i)
                    counter.incrementAndGet();
                end.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        testExecutorService(Executors.newCachedThreadPool(), "CachedThreadPool");
        testExecutorService(Executors.newFixedThreadPool(INCREMENTERS), "FixedThreadPool");
        testExecutorService(Executors.newSingleThreadExecutor(), "SingleThreadExecutor");
        testExecutorService(Executors.newVirtualThreadPerTaskExecutor(), "VirtualThreadPerTaskExecutor");
    }

    private static void testExecutorService(ExecutorService executor, String executorType) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
        AFG03_3_1_c_CounterInterface counter = new AFG03_3_1_MyLongAtomic();

        System.out.println("Testing with " + executorType);
        threadCount.set(0);

        long startTime = System.nanoTime(); // Startzeit messen
        try {
            for (int i = 0; i < INCREMENTERS; ++i)
                executor.submit(new Incrementer(startLatch, endLatch, counter));

            System.out.println("Starting with counter = " + counter.get());
            startLatch.countDown(); // Startet alle Threads gleichzeitig
            endLatch.await(); // Wartet, bis alle Threads fertig sind

            long totalInc = RUNS * INCREMENTERS;
            System.out.println("Finished after " + totalInc + " increments with counter = " + counter.get());
            counter.check(totalInc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        long endTime = System.nanoTime();   // Endzeit messen
        long duration = (endTime - startTime) / 1_000_000; // Dauer in Millisekunden
        System.out.println("Execution time with " + executorType + ": " + duration + " ms");
        System.out.println("Total threads started with " + executorType + ": " + threadCount.get());
        System.out.println();
    }
}

// Frage: Arbeitet Ihr Programm immer korrekt? Warum?
// Ja, das Programm arbeitet korrekt. Durch Verwendung von AtomicLong, ist die Zählung thread-sicher,
// dadurch werden ebenso Race Conditions verhindert. Das wird bestätigt durch check()
// Alle ExecuterService-Typs liefern dasselbe Ergebnis => Zählerstand von 10
// => Inkrementierug ist zuverlässig und unabhängig vom Thread-Typ.

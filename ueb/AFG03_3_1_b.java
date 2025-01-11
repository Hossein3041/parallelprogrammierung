package ueb;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

interface AFG03_3_1_b_CounterInterface {
    long get();

    long incrementAndGet();

    default void check(long desired) {
        if (get() != desired)
            System.out.println("Counter mismatch! Expected: " + desired + ", but got: " + get());
    }
}

class AFG03_3_1_b_MyLong implements AFG03_3_1_b_CounterInterface {
    private long counter;

    @Override
    public synchronized long get() {
        return counter;
    }

    @Override
    public synchronized long incrementAndGet() {
        return ++counter;
    }
}

public class AFG03_3_1_b {
    private final static int INCREMENTERS = 100;
    private final static int RUNS = 100;

    // Zähler für die Anzahl der gestarteten Threads
    private static AtomicInteger threadCount = new AtomicInteger(0);

    protected static class Incrementer implements Runnable {
        private final CountDownLatch start, end;
        private final AFG03_3_1_b_CounterInterface counter;

        public Incrementer(CountDownLatch start, CountDownLatch end, AFG03_3_1_b_CounterInterface counter) {
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
        // Funktion:
        // Erstellt nach Bedarf neue Threads und wiederverwendet vorhandene, wenn sie frei sind.

        // Erwartung:
        // Sollte bei vielen Aufgaben schnellere Ausführung zeigen, da neue Threads erstellt werden,
        // wenn keine freien Threads im Pool vorhanden sind.

        testExecutorService(Executors.newFixedThreadPool(INCREMENTERS), "FixedThreadPool");
        // Funktion:
        // Erstellt einen Pool mit einer festen Anzahl von Threads.

        // Erwartung:
        // Begrenzt die Anzahl der gleichzeitig laufenden Threads. Falls die Anzahl der INCREMENTERS-Threads
        // sehr groß ist, könnte das die Ausführungszeit verlängern.

        testExecutorService(Executors.newSingleThreadExecutor(), "SingleThreadExecutor");
        // Funktion:
        // Erstellt einen Executor mit nur einem Thread, der Aufgaben nacheinander ausführt.

        // Erwartung:
        // Führt alle Aufgaben nacheinander aus, daher erwartet man eine langsame Ausführung

        testExecutorService(Executors.newVirtualThreadPerTaskExecutor(), "VirtualThreadPerTaskExecutor");
        // Funktion:
        // Erstellt für jede Aufgabe einen virtuellen Thread,
        // was eine effizientere Ausführung vieler leichter Aufgaben ermöglicht

        // Erwartung:
        // Ideal für viele leichte Aufgaben, da virtuelle Threads weniger Speicher benötigen
        // und effizienter verwaltet werden.
    }

    private static void testExecutorService(ExecutorService executor, String executorType) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
        AFG03_3_1_b_CounterInterface counter = new AFG03_3_1_b_MyLong();

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

// Um die Unterschiede der Ergebnisse analysieren zu können, werden Indikatoren implementiert:

// Messung von Ausführungszeit: Für jeden ExecutorService, um Zeiteffizienz jedes Typs zu messen,
// um sagen zu können, wie effizient jeder Typ die Aufgaben parallelisiert und verarbeitet


// Überprüfung des Zählerwerts: Finalen Zählerwert nach Inkrementieren prüfen, um sicherzustellen,
// dass alle Aufgaben korrekt synchronisiert wurden und es keine Race Conditions gibt.

// Anzahl der gestarteten Threads: Feste Anzahl protokollieren, um Unterschiede in der
// Ressourcennutzung zu erkennen.

// Frage: Arbeitet Ihr Programm unter Verwendung der Wrapper-Klasse MyLong im Unterschied zu der Version
// auf den Folien immer korrekt? Warum?

// Ja, das Programm arbeitet korrekt: Es ist möglich, in den Methoden get() und incrementAndGet()
// einen thread-sicheren Zugriff auf den zähler counter zu gewährleisten. Dadurch vermeidet man Race Conditions.
// Im Vergleich zu der Version ohne Synchronisierung, stellt MyLong Klasse, Synchronisierung sicher.
// Das kann man vergleichen mit:
// System.out.println("Finished after " + totalInc + " increments with counter = " + counter.get());
// man prüft es mit: counter.check(totalInc);

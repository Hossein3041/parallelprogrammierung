package ueb;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.concurrent.ExecutionException;

class SimulateWorkload {
    public static void initiateSimulation() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class FileReaderTask implements Callable<String> {
    private final File file;

    public FileReaderTask(File file) {
        this.file = file;
    }

    @Override
    public String call() {
        System.out.println("Reading file: " + file + " on thread: " + Thread.currentThread().getName());
        SimulateWorkload.initiateSimulation();
        return "Content of " + file;
    }
}

class DataProcessorTask implements Callable<String> {
    private final String fileContent;

    public DataProcessorTask(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String call() {
        System.out.println("Processing content: " + fileContent + " on thread: " + Thread.currentThread().getName());
        SimulateWorkload.initiateSimulation();
        return fileContent.toUpperCase();
    }
}

class FileWriterTask implements Runnable {
    private final String processedContent;

    public FileWriterTask(String processedContent) {
        this.processedContent = processedContent;
    }

    @Override
    public void run() {
        System.out.println("Writing content: " + processedContent + " on thread: " + Thread.currentThread().getName());
        SimulateWorkload.initiateSimulation();
    }
}

class ExecutorTest {
    private final List<File> files = Arrays.asList(File.values());

    public void testCachedThreadPool() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting CachedThreadPool: ");
        ExecutorService executor = Executors.newCachedThreadPool();
        executeTasks(executor);
        executor.shutdown();
        // Erzeugt Threads nach Bedarf, wiederverwendet inaktive Threads für neue Aufgaben
        // Nützlich für kurzlebige, stark schwankende Aufgabenzahlen
    }

    public void testFixedThreadPool() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting FixedThreadPool: ");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executeTasks(executor);
        executor.shutdown();
        // Pool mit fester Anzahl von Threads, werden wiederverwendet
        // Ideal, wenn Anzahl von Aufgaben (für parallelität) konstant ist
    }

    public void testSingleThreadExecutor() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting SingleThreadExecutor: ");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executeTasks(executor);
        executor.shutdown();
        // Ein einziger Thread für alle Aufgaben, die nacheinander ausgeführt werden
        // Geeignet für Aufgaben, die in sequentieller Reihenfolge bearbeitet müssen
    }

    public void testScheduledThreadPool() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting ScheduledThreadPool: ");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        for (int count = 0; count < files.size(); ++count) {
            executor.schedule(new FileReaderTask(files.get(count)), 1, TimeUnit.SECONDS);
        }
        executor.shutdown();
        // Führt Aufgaben nach bestimmten Zeitplan oder festgelegten Abständen aus
        // Geeignet für zeitbasierte oder wiederkehrende Aufgaben, z.B. regelmäßige Datensicherung
    }

    public void testWorkStealingPool() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting WorkStealingPool:");
        ExecutorService executor = Executors.newWorkStealingPool(4);
        executeTasks(executor);
        executor.shutdown();
        // Nutzt mehrere Threads zur Lastverteilung und ermöglicht ihnen, Aufgaben aus anderen
        // Warteschlangen zu stehlen
        // Effektiv bei rechenintensiven Aufgaben & zur Maximierung der Ressourcenverteilung
    }

    public void testVirtualThreadExecutor() throws InterruptedException, ExecutionException {
        System.out.println("\nTesting VirtualThreadExecutor: ");
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        executeTasks(executor);
        executor.shutdown();
        // Führt Aufgaben in virtuellen Threads aus, die in großer Zahl effizient
        // gestartet werden können
        // Bietet Vorteile bei großen, blockierenden Aufgaben, da diese Threads wenig
        // Ressourcen benötigen
    }

    private void executeTasks(ExecutorService executor) throws InterruptedException, ExecutionException {
        for (int count = 0; count < files.size(); ++count) {
            File file = files.get(count);
            Future<String> fileContent = executor.submit(new FileReaderTask(file));
            Future<String> processedContent = executor.submit(new DataProcessorTask(fileContent.get()));
            executor.submit(new FileWriterTask(processedContent.get()));
        }
    }
}

public class AFG02_2_2_07_a {
    private static ExecutorTest test;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        initiateTesting();

    }

    public static void initiateTesting() throws InterruptedException, ExecutionException {
        initializeExecutorTest();
        initiateTestCachedThreadPool();
        initiateTestFixedThreadPool();
        initiateTestSingleThreadExecutor();
        initiateTestScheduledThreadPool();
        initiateTestWorkStealingPool();
        initiateTestVirtualThreadExecutor();
    }

    public static void initializeExecutorTest() {
        test = new ExecutorTest();
    }

    public static void initiateTestCachedThreadPool() throws InterruptedException, ExecutionException {
        test.testCachedThreadPool();
    }

    public static void initiateTestFixedThreadPool() throws InterruptedException, ExecutionException {
        test.testFixedThreadPool();
    }

    public static void initiateTestSingleThreadExecutor() throws InterruptedException, ExecutionException {
        test.testSingleThreadExecutor();
    }

    public static void initiateTestScheduledThreadPool() throws InterruptedException, ExecutionException {
        test.testScheduledThreadPool();
    }

    public static void initiateTestWorkStealingPool() throws InterruptedException, ExecutionException {
        test.testWorkStealingPool();
    }

    public static void initiateTestVirtualThreadExecutor() throws InterruptedException, ExecutionException {
        test.testVirtualThreadExecutor();
    }
}

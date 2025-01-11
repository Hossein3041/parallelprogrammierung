// Es werden hierbei aus einem festen Thread-Pool "FixedThreadPool(3)",
// Threads wiederverwendet
// Vorteil: Man verwendet dieselben Threads für mehrere Aufgaben
package ueb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AFG02_2_1_03_ExecutorService {
    private ExecutorService executor;

    public static void main(String[] args) throws Exception {
        AFG02_2_1_03_ExecutorService app = new AFG02_2_1_03_ExecutorService();
        app.runFileProcessing();
    }

    public void runFileProcessing() throws Exception {
        initializeExecutor();   // ExecutorService wird erzeugt

        // Datenverarbeitung in Schritten
        String fileData = executeFileReader();
        String processedData = executeDataProcessor(fileData);
        executeFileWriter(processedData);

        shutdownExecutor();
    }

    private void initializeExecutor() {
        executor = Executors.newFixedThreadPool(3);
    }

    private String executeFileReader() throws Exception {
        System.out.println("Executing DataProcessor on thread: " + Thread.currentThread().getName());
        Future<String> fileReaderFuture = executor.submit(new FileReaderCallable());
        return fileReaderFuture.get();
    }

    private String executeDataProcessor(String fileData) throws Exception {
        Future<String> dataProcessorFuture = executor.submit(new DataProcessorCallable(fileData));
        return dataProcessorFuture.get();
    }

    private void executeFileWriter(String processedData) throws Exception {
        Future<String> fileWriterFuture = executor.submit(new FileWriterCallable(processedData));
        fileWriterFuture.get();
    }

    private void shutdownExecutor() {
        executor.shutdown();
    }
}

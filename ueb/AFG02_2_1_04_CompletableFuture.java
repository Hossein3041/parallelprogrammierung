package ueb;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AFG02_2_1_04_CompletableFuture {
    private ExecutorService executor;

    public static void main(String[] args) throws Exception {
        AFG02_2_1_04_CompletableFuture app = new AFG02_2_1_04_CompletableFuture();
        app.runFileProcessing();
    }

    public void runFileProcessing() {

        executor = Executors.newFixedThreadPool(3);
        CompletableFuture<String> fileReaderFuture = readFileAsync();
        CompletableFuture<String> processedDataFuture = processFileAsync(fileReaderFuture);
        writeFileAsync(processedDataFuture).join();

        shutdownExecutor();
    }

    private CompletableFuture<String> readFileAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("FileReader: Start reading file on thread: " + Thread.currentThread().getName());
                return new FileReaderCallable().call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    private CompletableFuture<String> processFileAsync(CompletableFuture<String> fileReaderFuture) {
        return fileReaderFuture.thenApplyAsync(data -> {
            try {
                System.out.println("DataProcessor: Processing on thread: " + Thread.currentThread().getName());
                return new DataProcessorCallable(data).call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    private CompletableFuture<Void> writeFileAsync(CompletableFuture<String> processedDataFuture) {
        return processedDataFuture.thenAcceptAsync(processedData -> {
            try {
                System.out.println("DataProcessor: Processing on thread: " + Thread.currentThread().getName());
                new FileWriterCallable(processedData).call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    private void shutdownExecutor() {
        executor.shutdown();
    }
}

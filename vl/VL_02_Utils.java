package vl;

import java.util.concurrent.ExecutorService;

public class VL_02_Utils {
    public static void main(String[] args) {
        doit(10);
    }

    public static void printThreadData(Integer n) {
        Thread t = Thread.currentThread();
        System.out.printf("instance of %s(%s): %s\n", t.getClass().getSimpleName(), n, t.toString());
    }

    public static void doit(int n) {
        printThreadData(n);
        try {
            Thread.sleep(1000); // *who* is sleeping?
        } catch (Exception e) {
        }
    }

    public static void shutdown(ExecutorService executorService) {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

package vl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VL_02_CachedPool {
    public static void runTest(ExecutorService executorService, int timeOut){
        // submit some runnables to my executorService
        for(int i = 0; i < 10; i += 1){
            executorService.submit(new VL_02_MyRunnable(i));
        }

        // wait some time
        try{Thread.sleep(timeOut);} catch(Exception e) {}

        System.out.println("submitting more Runnables...");
        // again execute some runnables in my executorService
        for(int i = 0; i < 10; i += 1){
            executorService.execute(new VL_02_MyRunnable(100 + i));
        }
    }

    public static void main(String... args){
        ExecutorService executorService = Executors.newCachedThreadPool();

        // print data of main thread
        VL_02_Utils.printThreadData(null);

        runTest(executorService, 1000);
        // runTest(executorService, 0);

        VL_02_Utils.shutdown(executorService);

    }
}

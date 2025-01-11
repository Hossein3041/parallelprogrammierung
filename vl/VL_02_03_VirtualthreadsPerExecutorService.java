package vl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VL_02_03_VirtualthreadsPerExecutorService {
    public static void main(String[] args) {
        final int noOfTasks = 10_000;
        final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        IntStream.range(0, noOfTasks).forEach(i ->
                executorService.submit(() -> VL_02_Utils.doit(i))
        );
        executorService.close();
    }

}

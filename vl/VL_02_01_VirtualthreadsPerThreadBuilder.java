package vl;

import java.util.stream.IntStream;

public class VL_02_01_VirtualthreadsPerThreadBuilder {
    public static void main(String[] args) {
        final int noOfTasks = 10_000;

        Thread[] allThreads = new Thread[noOfTasks];
        IntStream.range(0, noOfTasks).forEach(i ->
                allThreads[i] = Thread.startVirtualThread(() -> VL_02_Utils.doit(i)));

        for (int i = 0; i < allThreads.length; ++i) {
            try {
                allThreads[i].join();
            } catch (Exception e) {
            }
        }
    }
}

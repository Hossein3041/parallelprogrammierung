package vl;

import java.util.stream.IntStream;

public class VL_02_02_VirtualthreadsPerThreadBuilder {
    public static void main(String[] args) {
        final int noOfTasks = 10_000;

        Thread.Builder threadBuilder = Thread.ofVirtual();
        // Thread.Builder threadBuilder = Thread.ofPlatform();

        Thread[] allThreads = new Thread[noOfTasks];
        IntStream.range(0, noOfTasks).forEach(i -> {
            String name = String.format("doit(%s)", i);
            allThreads[i] = threadBuilder.name(name).start(() -> VL_02_Utils.doit(i));
        });

        for (int i = 0; i < allThreads.length; ++i) {
            try {
                allThreads[i].join();
            } catch (Exception e) {
            }
        }
    }
}

package ueb;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AFG06_6_4_a_1_ProdProcess extends Thread {
    private final String name;
    private final CyclicBarrier barrier;
    private final int productionCount = 20;

    public AFG06_6_4_a_1_ProdProcess(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < productionCount; ++i) {
                System.out.println(name + " produziert Teil " + i);
                barrier.await();
                System.out.println(name + " wartet auf Montage...");
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            System.err.println(name + " wurde unterbrochen.");
            Thread.currentThread().interrupt();
        }
    }
}

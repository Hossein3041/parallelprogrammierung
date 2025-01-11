package ueb;

import java.util.concurrent.CyclicBarrier;

public class AFG06_6_4_a_2_MontageSystem {
    public static void main(String... args) {
        initializeMontageSystem();
    }

    private static void initializeMontageSystem() {
        CyclicBarrier barrier = new CyclicBarrier(3, new AFG06_6_4_a_0_MontageAktion());
        String[] namen = initializeProdProcessNames();
        AFG06_6_4_a_1_ProdProcess[] prodArray = initializeProdProcessArray(namen);
        for (int i = 0; i < prodArray.length; ++i)
            prodArray[i] = new AFG06_6_4_a_1_ProdProcess(namen[i], barrier);
        startProdProcesses(prodArray);
    }

    private static String[] initializeProdProcessNames() {
        return new String[]{"ProdA", "ProdB", "ProdC"};
    }

    private static AFG06_6_4_a_1_ProdProcess[] initializeProdProcessArray(String[] namen) {
        return new AFG06_6_4_a_1_ProdProcess[3];
    }

    private static void startProdProcesses(AFG06_6_4_a_1_ProdProcess[] prodArray) {
        for (int i = 0; i < prodArray.length; ++i)
            prodArray[i].start();
    }
}

package ueb;

import java.util.Random;

public class AFG06_6_3_1_Kannibale extends Thread {
    private final String name;
    private final AFG06_6_3_0_Buffet buffet;
    private final int SLEEPTIME = 1000;

    public AFG06_6_3_1_Kannibale(String name, AFG06_6_3_0_Buffet buffet) {
        this.name = name;
        this.buffet = buffet;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                buffet.essen(name);
                Thread.sleep(rnd.nextInt(SLEEPTIME));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(name + " wurde unterbrochen.");
            }
        }
    }
}

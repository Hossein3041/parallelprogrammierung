package ueb;

public class AFG06_6_3_2_Koch extends Thread {
    private final AFG06_6_3_0_Buffet buffet;
    private final int SLEEPTIME = 2000;

    public AFG06_6_3_2_Koch(AFG06_6_3_0_Buffet buffet) {
        this.buffet = buffet;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffet.auffuellen();
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Der Koch wurde unterbrochen.");
            }
        }
    }
}

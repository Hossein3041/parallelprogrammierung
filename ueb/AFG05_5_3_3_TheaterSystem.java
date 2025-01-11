package ueb;

public class AFG05_5_3_3_TheaterSystem {
    public static void main(String... args) {
        AFG05_5_3_1_Zentralrechner rechner = new AFG05_5_3_1_Zentralrechner(5);

        AFG05_5_3_2_TerminalThread terminal1 = new AFG05_5_3_2_TerminalThread(rechner, 1);
        AFG05_5_3_2_TerminalThread terminal2 = new AFG05_5_3_2_TerminalThread(rechner, 2);

        terminal1.start();
        terminal2.start();

        try {
            terminal1.join();
            terminal2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

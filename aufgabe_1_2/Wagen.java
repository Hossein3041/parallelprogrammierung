package aufgabe_1_2;

public class Wagen implements Runnable {
    private final int n;
    private Steuerung steuerung;
    private int SLEEPTIME = 1000;

    public Wagen(int n, Steuerung obj) {
        this.n = n;
        this.steuerung = obj;
    }

    @Override
    public void run() {
        Achterbahn.printThreadData(n);
        while (true) {
            try {
                steuerung.abfahrt();
                System.out.println("unterwegs");
                Thread.sleep(SLEEPTIME);
                steuerung.aussteigen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

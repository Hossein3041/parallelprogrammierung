package aufgabe_1_2;

public class Drehkreuz implements Runnable {
    private final int n;
    private Steuerung steuerung;
    private int SLEEPTIME = 500;

    public Drehkreuz(int n, Steuerung obj) {
        this.n = n;
        this.steuerung = obj;
    }

    @Override
    public void run() {
        Achterbahn.printThreadData(n);
        while (true) {
            try {
                steuerung.passagier();
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

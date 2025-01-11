package aufgabe_1_3;

public class Drehkreuz implements Runnable {
    //n merkt sich die wievielte Thread-Instanz
    private final int n;
    private Steuerung steuerung;
    private int SLEEPTIME = 500;

    public Drehkreuz(int n, Steuerung obj) {
        this.n = n;
        this.steuerung = obj;
    }

    //Implementation der auszuführenden run-Methode
    @Override
    public void run() {
        Achterbahn.printThreadData(n);
        //Endlosschleife: sende Passagiere
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

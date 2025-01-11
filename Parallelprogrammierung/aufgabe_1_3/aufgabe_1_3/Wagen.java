package aufgabe_1_3;

public class Wagen implements Runnable {
    //n merkt sich die wievielte Thread-Instanz
    private final int n;
    private Steuerung steuerung;
    private int SLEEPTIME = 500;

    public Wagen(int n, Steuerung obj) {
        this.n = n;
        this.steuerung = obj;
    }

    //Implementation der auszuführenden run-Methode
    @Override
    public void run() {
        Achterbahn.printThreadData(n);
        //Endlosschleife: abfahren -> unterwegs sein -> Passagiere aussteigen
        while (true) {
            try {
                steuerung.abfahrt(n);
                System.out.println("unterwegs");
                Thread.sleep(SLEEPTIME);
                steuerung.aussteigen(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

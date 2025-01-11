package aufgabe_1_2;

public class Steuerung {
    private int N;
    private int resetPlaetze = 0;
    private int plaetze;
    private int SLEEPTIME = 1000;

    public Steuerung(int n) {
        this.N = n;
        this.plaetze = 0;
    }

    public synchronized void passagier() throws InterruptedException {
        while (!(plaetze < N)) wait();
        ++plaetze;
        System.out.printf("Passagier: %s\n", plaetze);
        notifyAll();
    }

    public synchronized void abfahrt() throws InterruptedException {
        while (!(plaetze == N)) wait();
        System.out.println("Abfahrt");
        Thread.sleep(SLEEPTIME);
        notifyAll();
    }

    public synchronized void aussteigen() throws InterruptedException {
        System.out.println("aussteigen");
        Thread.sleep(SLEEPTIME);
        plaetze = resetPlaetze;
        notifyAll();
    }
}

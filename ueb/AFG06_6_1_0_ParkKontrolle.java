package ueb;

public class AFG06_6_1_0_ParkKontrolle {
    private final int maxPlaetze;
    private int freiePlaetze;

    public AFG06_6_1_0_ParkKontrolle(int maxPlaetze) {
        this.maxPlaetze = maxPlaetze;
        this.freiePlaetze = maxPlaetze;
    }

    public synchronized void parken() throws InterruptedException {
        while (!(freiePlaetze > 0)) wait();
        --freiePlaetze;
        System.out.println("Fahrzeug geparkt. Freie Plätze: " + freiePlaetze);
        notifyAll();
    }

    public synchronized void wegfahren() throws InterruptedException {
        while (!(freiePlaetze < maxPlaetze)) wait();
        ++freiePlaetze;
        System.out.println("Fahrzeug ausgefahren. Freie Plätze: " + freiePlaetze);
        notifyAll();
    }
}

package vl;

public class VL_04_04_ParkKontrolle {
    public int N = 3;
    private int freiePlaetze = N;

    public VL_04_04_ParkKontrolle() {
    }

    public synchronized void parken() throws InterruptedException {
        while (!(freiePlaetze > 0)) {
            wait();
        }
        --freiePlaetze;
        notifyAll();
    }

    public synchronized void wegfahren() throws InterruptedException {
        while (!(freiePlaetze < N)) {
            wait();
        }
        ++freiePlaetze;
        notifyAll();
    }

    public synchronized boolean erfolgreichGeparkt() {
        if (freiePlaetze == 0)
            return false;
        else {
            --freiePlaetze;
            return true;
        }
    }
}
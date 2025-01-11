package vl;

public class VL_05_01_Gabel {
    protected boolean istBelegt;
    protected final int nummer;

    public VL_05_01_Gabel(int number) {
        this.nummer = number;
    }

    // ... Konstruktor
    public synchronized void aufnehmen() throws InterruptedException {
        while (istBelegt) wait();
        istBelegt = true;
        notifyAll();    // weglassen?
    }

    public synchronized void ablegen() throws InterruptedException {
        while (!istBelegt) wait();   // weglassen?
        istBelegt = false;
        notifyAll();
    }

    public int getNumber() {
        return nummer;
    }
}

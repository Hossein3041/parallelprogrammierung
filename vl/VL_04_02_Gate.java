package vl;

public class VL_04_02_Gate extends Thread {
    private VL_04_03_GoodCounter globalCounter;
    private VL_04_03_GoodCounter localCounter;
    private int MAX_VISITORS = 20;

    public VL_04_02_Gate(String name, VL_04_03_GoodCounter globalCounter) {
        // Konstruktor der Oberklasse Thread aufrufen
        super("Tor \"" + name + "\"");
        // Zaehler zuweisen
        this.globalCounter = globalCounter;
        this.localCounter = new VL_04_03_GoodCounter();
    }

    public int getLocalValue() {
        return localCounter.getValue();
    }
    @Override
    public void run() {
        System.out.println(getName() + ": oeffne...");
        for (int i = 0; i < MAX_VISITORS; ++i) {
            localCounter.incrementValue();
            globalCounter.incrementValue();
        }
        System.out.println(getName() + ": schliesse.");
    }

    public static void main(String[] args) {
        VL_04_03_GoodCounter firstCounter = new VL_04_03_GoodCounter();
        VL_04_02_Gate east = new VL_04_02_Gate("Osten", firstCounter);
        VL_04_02_Gate west = new VL_04_02_Gate("Westen", firstCounter);
        east.start();
        west.start(); // jetzt laufen drei Threads!

        // auf Beendigung der Gate-Threads warten
        try {
            east.join();
            west.join();
        } catch (Exception e) {
            System.out.println(">>> Exception: " + e);
        }

        // Zaehlerstaende ausgeben
        System.out.println();
        System.out.println(east.getName() + ": lokal " + east.getLocalValue() + " Besucher gezaehlt.");
        System.out.println(west.getName() + ": lokal " + west.getLocalValue() + " Besucher gezaehlt.");
        System.out.println("Zaehler: global " + firstCounter.getValue() + " Besucher gezaehlt.");
    }
}

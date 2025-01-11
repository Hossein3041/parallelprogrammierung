package vl;

public class VL_04_08_Counter { // ist kein Monitor mehr!
    private int value;  // aktueller Zaehlerstand
    // nur ein Prozess darf den Zähler manipulieren
    private VL_04_07_Semaphor semaphor = new VL_04_07_Semaphor(1);

    // Instanziieren findet nur von einem Thread statt, ist also unkritisch
    public VL_04_08_Counter() {
        value = 0;
    }

    // Gegensetiger Ausschluss für das Hochzählen
    public void incrementValue() throws InterruptedException {   // nicht synchronized!
        semaphor.down();
        // kritischer Bereich
        int temp = value + 1;
        value = temp;
        semaphor.up();
    }

    // Gegenseitiger Ausschluss auch für das Lesen
    public int getValue() throws InterruptedException { // nicht synchronized!
        semaphor.down();
        // kritischer Bereich
        int temp = value;
        semaphor.up();
        return temp;
    }
}

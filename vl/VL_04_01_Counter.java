package vl;

public class VL_04_01_Counter {
    private int value;  // aktueller Zaehlerstand

    public VL_04_01_Counter() {
        value = 0;
    }

    public synchronized void increment() {
        value += 1;
    }

    public synchronized void increment(int n) {
        if (n > 0) {
            increment();    // einmal hochzaehlen
            increment(n - 1);   // rekursiv noch (n - 1)-mal
        }
    }

    public void incrementValue() {
        int temp = value + 1;
        {   // eventuell Prozesswechsel vornehmen
            try {
                if (Math.random() < 0.5)
                    Thread.yield(); // Thread.sleep(1);
            } catch (Exception e) {
            }
        }
        value = temp;
        // Kurzform "value++" wird intern auch so aufgelöst
    }

    public int getValue() {
        return value;
    }
}

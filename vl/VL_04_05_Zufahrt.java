package vl;

public class VL_04_05_Zufahrt extends Thread {
    private VL_04_04_ParkKontrolle parkKontrolle;   // Anzahl einfahrende Fahrzeuge
    private int n;

    @Override
    public void run() {
        for (int i = 0; i < n; ++i) {
            // i-tes Auto parken
            boolean geparkt = false;
            // solange versuchen, bis es geklappt hat
            while (!geparkt) {
                geparkt = parkKontrolle.erfolgreichGeparkt();
            }
        }
    }
}

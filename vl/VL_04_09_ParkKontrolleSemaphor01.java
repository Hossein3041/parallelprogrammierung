package vl;

public class VL_04_09_ParkKontrolleSemaphor01 { // ist kein Monitor mehr!
    // Zähler für die freien Plätze
    int N;
    private int plaetze = N;

    // nur ein Prozess darf auf die Verwaltung zugreifen
    private VL_04_07_Semaphor semaphor = new VL_04_07_Semaphor(1);

    public void parken() throws InterruptedException {   // nicht synchronized!
        boolean keinPlatzBekommen = true;
        while (keinPlatzBekommen) {
            // kritischen Bereich betreten
            semaphor.down();
            if (plaetze <= 0)
                // kritischen Bereich temporär verlassen...
                semaphor.up();
            else keinPlatzBekommen = false;
        }
        --plaetze;
        // kritischen Bereich wieder verlassen
        semaphor.up();
    }
}

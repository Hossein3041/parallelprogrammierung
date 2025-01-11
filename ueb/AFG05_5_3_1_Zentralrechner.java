package ueb;

public class AFG05_5_3_1_Zentralrechner {
    protected final AFG05_5_3_0_Platz[] plaetze;

    public AFG05_5_3_1_Zentralrechner(int anzahlPlaetze) {
        this.plaetze = new AFG05_5_3_0_Platz[anzahlPlaetze];
        for (int i = 0; i < anzahlPlaetze; ++i)
            plaetze[i] = new AFG05_5_3_0_Platz();
    }
    /* 5.3 a) Erstellen Sie zunächst eine korrekte Implementierung, in der keine Doppelbelegungen vorkommen können.
    public Boolean reservierePlatz(int platzNummer) {
        if (platzNummer >= 0 && platzNummer < plaetze.length)
            synchronized (plaetze[platzNummer]) {
                return plaetze[platzNummer].reservierePlatz();
            }
        return null;
    }
     */

    // 5.3 b) Verändern Sie Ihre Implementierung dann so, dass auch Race Hazards - d.h. Doppelbelegungen
    // möglich sind.
    // Hier einfach synchronized auf die jeweilige Platz-Ebene wegmachen.
    public Boolean reservierePlatz(int platzNummer) {
        if (platzNummer >= 0 && platzNummer < plaetze.length) {
            simuliereVerzoegerung();
            Boolean status = plaetze[platzNummer].reservierePlatz();
            pruefeRaceHazard(status, platzNummer);
            return status;
        }
        return null;
    }

    public void simuliereVerzoegerung() {
        try {
            Thread.sleep((int) (Math.random() * 50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pruefeRaceHazard(Boolean status, int platzNummer) {
        if (!status)
            System.out.println(">>> RACE HAZARD: " + platzNummer + " wurde mehrfach reserviert!");
    }

    public synchronized void freigebenPlatz(int platzNummer) {
        if (platzNummer >= 0 && platzNummer < plaetze.length)
            plaetze[platzNummer].freigebenPlatz();
    }

    public Boolean istPlatzFrei(int platzNummer) {
        if (platzNummer >= 0 && platzNummer < plaetze.length)
            return plaetze[platzNummer].istFrei();
        return null;
    }

}

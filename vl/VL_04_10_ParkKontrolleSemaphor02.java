package vl;

public class VL_04_10_ParkKontrolleSemaphor02 { // ist kein Monitor mehr!
    private final VL_04_07_Semaphor freiePlaetze;
    private final VL_04_07_Semaphor belegtePlaetze;
    private final int N = 1;

    public VL_04_10_ParkKontrolleSemaphor02() {
        freiePlaetze = new VL_04_07_Semaphor(N);
        belegtePlaetze = new VL_04_07_Semaphor(0);
    }

    public void parken() throws InterruptedException {   // nicht mehr synchronized!
        freiePlaetze.down();
        belegtePlaetze.up();
    }

    public void wegfahren() throws InterruptedException {   // nicht mehr synchronized!
        belegtePlaetze.down();
        freiePlaetze.up();
    }
}

package ueb;

public class AFG05_5_3_0_Platz {
    private boolean frei = true;

    public Boolean reservierePlatz() {
        return frei ? !(frei = false) : false;
    }

    public void freigebenPlatz() {
        frei = true;
    }

    public synchronized Boolean istFrei() {
        return frei;
    }
}

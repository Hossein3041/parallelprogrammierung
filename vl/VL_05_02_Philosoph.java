package vl;

public class VL_05_02_Philosoph extends Thread {
    final VL_05_01_Gabel linkeGabel;
    final VL_05_01_Gabel rechteGabel;
    protected final int nummer;

    public VL_05_02_Philosoph(int nummer, VL_05_01_Gabel linkeGabel, VL_05_01_Gabel rechteGabel) {
        this.nummer = nummer;
        this.linkeGabel = linkeGabel;
        this.rechteGabel = rechteGabel;
    }

    void denken() throws InterruptedException {
        System.out.println("Philosoph " + getName() + " mit Nummer: " + nummer + " denkt.");
        sleep((int) (Math.random() * 1000));
    }

    void essen() throws InterruptedException {
        System.out.println("Philosoph " + getName() + " mit Nummer: " + nummer + "isst.");
        sleep((int) (Math.random() * 1000));
    }

    private void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void aktionAusfuehren() {
        try {
            while (true) {
                essen();
                denken();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                denken();
                synchronized (linkeGabel) {
                    linkeGabel.aufnehmen();
                    synchronized (rechteGabel) {
                        rechteGabel.aufnehmen();
                        essen();
                        rechteGabel.ablegen();
                    }
                    linkeGabel.ablegen();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

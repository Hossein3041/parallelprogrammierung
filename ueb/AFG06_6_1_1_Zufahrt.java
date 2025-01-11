package ueb;

public class AFG06_6_1_1_Zufahrt extends Thread {
    private final AFG06_6_1_0_ParkKontrolle parkKontrolle;
    private final int fahrzeuge;

    public AFG06_6_1_1_Zufahrt(AFG06_6_1_0_ParkKontrolle parkKontrolle, int fahrzeuge) {
        this.parkKontrolle = parkKontrolle;
        this.fahrzeuge = fahrzeuge;
    }

    @Override
    public void run() {
        for (int i = 0; i < fahrzeuge; i++) {
            try {
                parkKontrolle.parken();
                Thread.sleep((int) (Math.random() * 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Zufahrt unterbrochen.");
            }
        }
    }
}

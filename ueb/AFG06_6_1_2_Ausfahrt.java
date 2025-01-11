package ueb;

public class AFG06_6_1_2_Ausfahrt extends Thread {
    private final AFG06_6_1_0_ParkKontrolle parkKontrolle;
    private final int fahrzeuge;

    public AFG06_6_1_2_Ausfahrt(AFG06_6_1_0_ParkKontrolle parkKontrolle, int fahrzeuge) {
        this.parkKontrolle = parkKontrolle;
        this.fahrzeuge = fahrzeuge;
    }

    @Override
    public void run() {
        for (int i = 0; i < fahrzeuge; ++i) {
            try {
                parkKontrolle.wegfahren();
                Thread.sleep((int) (Math.random() * 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Ausfahrt unterbrochen.");
            }
        }
    }
}

package ueb;

public class AFG06_6_1_3_ParkplatzSimulation {
    public static void main(String... args) {
        int parkplatzGroesse = 5;
        int fahrzeuge = 25;
        int fahrzeuge2 = fahrzeuge + 10;

        AFG06_6_1_0_ParkKontrolle parkKontrolle = new AFG06_6_1_0_ParkKontrolle(parkplatzGroesse);

        AFG06_6_1_1_Zufahrt zufahrt = new AFG06_6_1_1_Zufahrt(parkKontrolle, fahrzeuge2);
        AFG06_6_1_2_Ausfahrt ausfahrt = new AFG06_6_1_2_Ausfahrt(parkKontrolle, fahrzeuge);

        zufahrt.start();
        ausfahrt.start();

        try {
            zufahrt.join();
            ausfahrt.join();
        } catch (InterruptedException e) {
            System.err.println("Simulation unterbrochen.");
        }

        System.out.println("Simulation beendet.");
    }
}

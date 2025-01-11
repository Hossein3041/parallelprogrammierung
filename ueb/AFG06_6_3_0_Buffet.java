package ueb;

public class AFG06_6_3_0_Buffet {
    private final int M = 5;
    private int speicher = M;

    public synchronized void essen(String name) throws InterruptedException {
        while (!(speicher > 0)) {
            System.out.println(name + " wartet auf das Auffüllen des Buffets.");
            wait();
        }
        --speicher;
        System.out.println(name + " isst. Verbleibende Missionare: " + speicher);
        notifyAll();
    }

    public synchronized void auffuellen() throws InterruptedException {
        while (speicher > 0) wait();
        speicher = M;
        System.out.println("Der Koch hat das Buffet aufgefüllt.");
        notifyAll();
    }
}

package aufgabe_1_3;
import java.util.ArrayList;
import java.util.Vector;
public class Steuerung {
    //Anzahl Passagiere M pro Wagen
    private final int M;
    //Anzahl Wagen T (default: 3)
    private final int T;
    private int resetPlaetze = 0;
    private int SLEEPTIME = 1000;
    //ein Array merkt sich für jeden Wagen(n) an Index n-1 die aktuelle Anzahl Passagiere und den Stellplatz
    private int[][] wagonAndPassengers;
    private final int passengers, position;
    //die einzige zulässige Position für das Abfahren eines Wagens
    private final int startPos;

    public Steuerung(int M, int T) {
        this.M = M;
        this.T = T;
        this.passengers = 0;
        this.position = 1;
        this.startPos = T - 1;
        //Wagen(n): wagonAndPassengers[n-1][passengers] = Anzahl Passagiere; wagonAndPassengers[n-1][position] = Stellplatz des Wagens
        this.wagonAndPassengers = new int[T][2];
        //Initiale Reihenfolge der Wagen 1 bis n, mit Position n-1 bis 0: bei drei Wagen ist die Position von Wagen 1 also 2 ≙ startPos (T-1)
        for (int i = 0, j = startPos; i < T; ++i, --j) {
            wagonAndPassengers[i][position] = j;
        }
    }

    public synchronized void passagier() throws InterruptedException {
        //solange Plätze in mindestens einem der Wagen frei sind, darf das Drehkreuz einzelne Passagiere nicht-deterministisch verteilen

        boolean flag = false;
        for (int i = 0; i < T; ++i) {
            if ((wagonAndPassengers[i][passengers] < M)) flag = true;
        }
        if (flag == false) wait();

        int pos;
        Vector<Integer> vec = new Vector<>(1, 1);
        for (int i = 0; i < wagonAndPassengers.length - 1; ++i) {
            if ((wagonAndPassengers[i][passengers] < M)) vec.add(i);
        }
        pos = vec.get((int) (Math.random() * (vec.size())));
        wagonAndPassengers[pos][passengers]++;
        notifyAll();
    }

    public synchronized void abfahrt(int n) throws InterruptedException {
        //ist der Wagen an der Startposition voll belegt, darf dieser Abfahren
        while (!(wagonAndPassengers[n - 1][position] == startPos && wagonAndPassengers[n - 1][passengers] == M)) wait();
        System.out.printf("Abfahrt Wagen: %s\n", n);
        Thread.sleep(SLEEPTIME);
        notifyAll();
    }

    public synchronized void aussteigen(int wagonnumber) throws InterruptedException {
        System.out.println("aussteigen");
        Thread.sleep(SLEEPTIME);
        //erst nachdem der Wagen aussteigen() aufruft, darf der aktuelle Passagier-Wert auf Null gesetzt werden
        wagonAndPassengers[wagonnumber - 1][passengers] = resetPlaetze;
        //alle Wagen inkrementieren ihre aktuelle Position des Stellplatzes um Eins, Modulo der Gesamtanzahl an Wagen
        //dadurch landet der Erste Wagen, nach seiner Fahrt, an dem Ende der Warteschlange
        for (int i = 0; i < T; ++i) {
            wagonAndPassengers[i][position] = (wagonAndPassengers[i][position] + 1) % T;
        }
        notifyAll();
    }
}

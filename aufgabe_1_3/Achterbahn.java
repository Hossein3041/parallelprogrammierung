package aufgabe_1_3;

import java.util.Scanner;

public class Achterbahn {
    //Anzahl Passagiere M pro Wagen 
    private static int M;
    //Anzahl Wagen T (default: 3)
    private static int T;

    public static void setEingaben() {
        //Scanner-Objekt sc initialisiert M und T per Tastatureingabe
        boolean inputAccepted = false;
        Scanner sc = new Scanner(System.in);

        //gültige Werte für M sind positive Ganzahlen (ohne Null)
        while (!inputAccepted) {
            try {
                System.out.print("Anzahl Passagiere M pro Wagen festlegen: ");
                M = Integer.valueOf(sc.nextLine());
                if (M >= 1) inputAccepted = !inputAccepted;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe!");
            }
        }
        //gültige Werte für T sind positive Ganzahlen (ohne Null) bis maximal 10
        //falsche/keine Eingaben führen zu dem Standard-Wert: 3
        try {
            System.out.print("Anzahl Wagen T festlegen: ");
            T = Integer.valueOf(sc.nextLine());
            if (!(T >= 1 && T <= 10)) {
                T = 3;
                System.out.println("Default Wert: 3");
            }
        } catch (NumberFormatException e) {
            T = 3;
            System.out.println("Default Wert: 3");
        }

        sc.close();
    }

    public static void printThreadData(Integer n) {
        //Ausgabe der Thread-Daten
        System.out.printf("Instanz(%s): %s\n", n, Thread.currentThread().toString());
    }

    public static void main(String... args) {
        setEingaben();
        printThreadData(0);

        //erstellt Steuerungs-Objekt + Runnables und startet die Threads
        Steuerung steuerung = new Steuerung(M, T);
        for (int i = 1; i <= T; ++i) {
            (new Thread(new Wagen(i, steuerung), "Wagen")).start();
        }
        (new Thread(new Drehkreuz(T + 1, steuerung), "Drehkreuz")).start();
    }
}

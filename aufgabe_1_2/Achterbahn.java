package aufgabe_1_2;

import java.util.Scanner;

public class Achterbahn {
    private static int M;
    private static int SLEEPTIME = 100;
    public static void setEingabe() {
        boolean inputAccepted = false;
        Scanner sc = new Scanner(System.in);

        while (!inputAccepted) {
            try {
                System.out.print("Anzahl Passagiere M pro Wagen festlegen: ");
                M = Integer.valueOf(sc.nextLine());
                if (M >= 1) inputAccepted = !inputAccepted;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe!");
            }
        }
        sc.close();
    }

    public static void printThreadData(Integer n) {
        System.out.printf("Instanz(%s): %s\n", n, Thread.currentThread().toString());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        setEingabe();
        printThreadData(0);
        Steuerung steuerung = new Steuerung(M);
        Thread myFirst = new Thread(new Wagen(1, steuerung), "Wagen");
        Thread mySecond = new Thread(new Drehkreuz(2, steuerung), "Drehkreuz");
        myFirst.start();
        mySecond.start();
    }
}

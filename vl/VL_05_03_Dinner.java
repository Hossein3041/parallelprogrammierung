package vl;

import java.util.concurrent.Semaphore;

public class VL_05_03_Dinner {

    public static void starteMitButlerStrategie() {
        int anzahlPhilosophen = 5;
        VL_05_01_Gabel[] gabeln = new VL_05_01_Gabel[anzahlPhilosophen];
        VL_05_02_Philosoph[] philosophen = new VL_05_02_Philosoph[anzahlPhilosophen];
        Semaphore butler = new Semaphore(anzahlPhilosophen - 1);

        // Initialisierung der Gabeln
        for (int i = 0; i < anzahlPhilosophen; ++i)
            gabeln[i] = new VL_05_01_Gabel(i);

        // Initialisierung der Philosophen mit Butler-Strategie
        for (int i = 0; i < anzahlPhilosophen; ++i) {
            philosophen[i] = new VL_05_02_Philosoph(i, gabeln[i], gabeln[(i + 1) % anzahlPhilosophen]) {
                @Override
                public void run() {
                    try {
                        while (true) {
                            denken();
                            butler.acquire();
                            synchronized (linkeGabel) {
                                linkeGabel.aufnehmen();
                                synchronized (rechteGabel) {
                                    rechteGabel.aufnehmen();
                                    essen();
                                    rechteGabel.ablegen();
                                }
                                linkeGabel.ablegen();
                            }
                            butler.release();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        // Start der Philosophen-Threads
        for (int i = 0; i < philosophen.length; ++i)
            philosophen[i].start();
    }

    public static void starteMitParitaetReihenfolge() {
        int anzahlPhilosophen = 5;
        VL_05_01_Gabel[] gabeln = new VL_05_01_Gabel[anzahlPhilosophen];
        VL_05_02_Philosoph[] philosophen = new VL_05_02_Philosoph[anzahlPhilosophen];

        // Initialisierung der Gabeln
        for (int i = 0; i < anzahlPhilosophen; ++i)
            gabeln[i] = new VL_05_01_Gabel(i);

        // Initialisierung der Philosophen mit Paritätsreihenfolge
        for (int i = 0; i < anzahlPhilosophen; ++i) {
            VL_05_01_Gabel linkeGabel = gabeln[i];
            VL_05_01_Gabel rechteGabel = gabeln[(i + 1) % anzahlPhilosophen];

            if (i % 2 == 0)
                philosophen[i] = new VL_05_02_Philosoph(i, linkeGabel, rechteGabel);
            else
                philosophen[i] = new VL_05_02_Philosoph(i, rechteGabel, linkeGabel);
        }

        for (int i = 0; i < philosophen.length; ++i)
            philosophen[i].start();
    }

    public static void starteMitFesterReihenfolge() {
        int anzahlPhilosophen = 5;
        VL_05_01_Gabel[] gabeln = new VL_05_01_Gabel[anzahlPhilosophen];
        VL_05_02_Philosoph[] philosophen = new VL_05_02_Philosoph[anzahlPhilosophen];

        // Initialisierung der Gabeln
        for (int i = 0; i < anzahlPhilosophen; ++i)
            gabeln[i] = new VL_05_01_Gabel(i);

        // Initialisierung der Philosophen mit fester Reihenfolge der Gabelaufnahme
        for (int i = 0; i < anzahlPhilosophen; ++i) {
            VL_05_01_Gabel linkeGabel = gabeln[i];
            VL_05_01_Gabel rechteGabel = gabeln[(i + 1) % anzahlPhilosophen];
            if (linkeGabel.getNumber() < rechteGabel.getNumber())
                philosophen[i] = new VL_05_02_Philosoph(i, linkeGabel, rechteGabel);
            else
                philosophen[i] = new VL_05_02_Philosoph(i, rechteGabel, linkeGabel);
        }

        // Start der Philosophen-Threads
        for (int i = 0; i < philosophen.length; ++i)
            philosophen[i].start();
    }

    public static void main(String... args) {
        //starteMitFesterReihenfolge();
        //starteMitParitaetReihenfolge();
        starteMitButlerStrategie();
    }
}

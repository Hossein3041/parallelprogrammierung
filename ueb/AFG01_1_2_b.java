/*  Aufgabenblatt 1
    1.2 b) Wie wurden Sie den Algorithmus nebenläufig implementieren?
    Für den geraden, sowie ungeraden Schritt wurden separate Klassen OddStepThread,
    sowie EvenStepThread implementiert, jeweils abgeleitet von Klasse Thread

    In Klasse oddEvenSort, wird in oddEvenSort(), bei jeder while-Iteration,
    ein Thread gestartet, für jeden Schritt.

    Was müssten Sie dabei beachten? Wo könnten Probleme (welche?) auftreten?
    Die Threads sollen korrekt gestartet, und synchronisiert werden,
    um mögliche Zugriffskonflikte zu vermeiden.
    Zugriffskonflikte tauchen hierbei auf, wenn zwei Threads gleichzeitig auf dieselbe Ressource
    zugreifen, hierbei auf denselben Array, bzw. aufdenselben Index/Wert/Prozessor.
    Warum? Siehe Funktionsweise von OddEvenSort

    Es könnten auch bei swap() Zugriffskonflikte auftreten.

    Potenzielles Problem bei Zugriffskonflikten:
    Es könnten Deadlocks-Situation entstehen, wenn Synchronisation zwischen Threads nicht richtig
    läuft. Führt dazu, dass Threads sich gegenseitig blockieren

    Ein weiteres Problem: Algorithmus kann ineffizient werden, wenn Threads häufig gestartet werden,
    siehe while-Schleife: Bei jeder Iteration wird ein neuer Thread gestartet.
    Bei vielen Iterationen erzeugt es einen hohen Overhead:
    Verwendung von zu vielen Ressourcen, die mehr sind, als die Aufgabe nötig hat.

    Effizientere Lösung: Verwendung von Thread-Pools, um damit Threads wied:q
    ::erzuverwenden.

    Wenn

 */

package ueb;

class AFG01_1_2_b_OddEvenSort {
    private Integer[] array;

    public AFG01_1_2_b_OddEvenSort(Integer[] array) {
        this.array = array;
    }

    public AFG01_1_2_b_OddEvenSort() {
    }

    public void oddEvenSort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;

            OddStepThread oddStepThread = new OddStepThread(array, this);
            oddStepThread.start();
            try {
                oddStepThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (oddStepThread.isSwapped())
                sorted = false;

            EvenStepThread evenStepThread = new EvenStepThread(array, this);
            evenStepThread.start();
            try {
                evenStepThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (evenStepThread.isSwapped())
                sorted = false;
        }
    }

    public void swap(Integer[] array, Integer i, Integer j) {
        Integer tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}

class OddStepThread extends Thread {
    private Integer[] array;
    private AFG01_1_2_b_OddEvenSort sorter;
    private boolean swapped = false;

    public OddStepThread(Integer[] array, AFG01_1_2_b_OddEvenSort sorter) {
        this.array = array;
        this.sorter = sorter;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length - 1; i += 2) {
            if (array[i] > array[i + 1]) {
                sorter.swap(array, i, i + 1);
                swapped = true;
            }
        }
    }

    public boolean isSwapped() {
        return swapped;
    }
}

class EvenStepThread extends Thread {
    private Integer[] array;
    private AFG01_1_2_b_OddEvenSort sorter;
    private boolean swapped = false;

    public EvenStepThread(Integer[] array, AFG01_1_2_b_OddEvenSort sorter) {
        this.array = array;
        this.sorter = sorter;
    }

    @Override
    public void run() {
        for (int i = 1; i < array.length - 1; i += 2) {
            if (array[i] > array[i + 1]) {
                sorter.swap(array, i, i + 1);
                swapped = true;
            }
        }
    }

    public boolean isSwapped() {
        return swapped;
    }
}

public class AFG01_1_2_b {
    public static void main(String[] args) {
        sortAndPrintArray();
    }

    public static void sortAndPrintArray() {
        Integer[] randomArray = AFG01_00_ArrayUtils.generateRandomArray(15);

        System.out.println("Ursprüngliches Array: ");
        AFG01_00_ArrayUtils.printArray(randomArray);

        AFG01_1_2_b_OddEvenSort sorter = new AFG01_1_2_b_OddEvenSort(randomArray);
        sorter.oddEvenSort();

        System.out.println("Sortiertes Array: ");
        AFG01_00_ArrayUtils.printArray(randomArray);

        AFG01_00_ArrayUtils.isSorted(randomArray);
    }
}

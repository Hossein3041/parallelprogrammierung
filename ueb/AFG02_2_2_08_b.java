// Rekursiven Sortieralgorithmus Quicksort entweder mit RecursiveTask oder RecursiveAction
// implementieren. Das Programm soll Standard- als auch benutzerdefinierten ForkJoinPool verwenden.
// Ziel: Unterschied bei Ausführung der Sortierung zu sehen, das Potenzial der parallelen Verarbeitung zu nutzen.
package ueb;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;

class Quicksort extends RecursiveAction {
    // RecursiveAction, speziell für rekursive Aufgaben entwickelt.
    // Ist Teil der ForkJoin-Bibliothek, dient dazu parallele Aufgaben zu definieren,
    // die sich selbst in kleinere Teilaufgaben aufteilen und parallel ausführen.

    // Durch Erben von RecursiveAction erhält Quicksort die Fähigkeit, parallel aufgeteilt
    // und ausgeführt zu werden, indem invokeAll() verwendet wird, um rekursive Teilaufgaben
    // zu starten.
    private final Integer[] array;
    private final int iLeft;
    private final int iRight;

    public Quicksort(Integer[] array, int iLeft, int iRight) {
        this.array = array;
        this.iLeft = iLeft;
        this.iRight = iRight;
    }

    @Override
    protected void compute() {
        // compute() enthält eigentliche Logik für rekursive Berechnung.
        // Wenn Index links kleiner Index rechts, wird Array durch partition() aufgeteilt.
        // Es werden zwei neue Quicksort-Aufgaben für beide Teilbereiche des Arrays erstellt
        // und parallel ausgeführt.
        System.out.println("Computing with thread: " + Thread.currentThread().getName());
        if (iLeft < iRight) {
            int pivotIndex = partition(array, iLeft, iRight);
            Quicksort leftTask = new Quicksort(array, iLeft, pivotIndex - 1);
            Quicksort rightTask = new Quicksort(array, pivotIndex + 1, iRight);
            invokeAll(leftTask, rightTask);
        }
    }

    public int partition(Integer[] array, int left, int right) {
        final int mid = array[(left + right) / 2];
        int l = left;
        int r = right;
        while (l <= r) {
            while (array[l] < mid)
                ++l;
            while (array[r] > mid)
                --r;
            if (l <= r) {
                swap(array, l, r);
                ++l;
                --r;
            }
        }
        return l;
    }

    private void swap(Integer[] array, int iPos1, int iPos2) {
        int temp = array[iPos1];
        array[iPos1] = array[iPos2];
        array[iPos2] = temp;
    }
}

public class AFG02_2_2_08_b {

    private static void runCustomForkJoinSort(Integer[] array) {
        ForkJoinPool customPool = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism() * 10);
        // Wir erstellen benutzerdefinierten ForkJoinPool, mit einer zehnfacher Parallelität des Standard-Pool.
        // D.h.: Es werden mehr Threads parallel verwendet, was Sortiergeschwindigkeit für große Array verbessern kann
        customPool.invoke(new Quicksort(array, 0, array.length - 1));
        customPool.shutdown();
        System.out.println("Array after sorting with Custom ForkJoinPool: ");
        //AFG01_00_ArrayUtils.printArray(array);
    }

    private static void runStandardForkJoinSort(Integer[] array) {
        ForkJoinPool.commonPool().invoke(new Quicksort(array, 0, array.length - 1));
        // Wir verwenden Standard-ForkJoinPool, um Quicksort auszuführen.
        // Standardpool nutzt voreingestellte Anzahl an paralleln Threads,
        // basierend auf Anzahl der verfügbaren CPU-Kerne
        System.out.println("Array after sorting with Standard ForkJoinPool: ");
        //AFG01_00_ArrayUtils.printArray(array);
    }

    public static void main(String[] args) {
        int size = 100_000;
        Integer[] array = AFG01_00_ArrayUtils.generateRandomArray(size);

        System.out.println("Array before sorting with Standard ForkJoinPool: ");
        //AFG01_00_ArrayUtils.printArray(array);

        long startTime = System.currentTimeMillis();
        runStandardForkJoinSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("Standard ForkJoinPool took: " + (endTime - startTime) + " milliseconds.");

        Integer[] newArray = AFG01_00_ArrayUtils.generateRandomArray(size);
        System.out.println("Array before sorting with Custom ForkJoinPool: ");
        //AFG01_00_ArrayUtils.printArray(newArray);

        startTime = System.currentTimeMillis();
        runCustomForkJoinSort(newArray);
        endTime = System.currentTimeMillis();
        System.out.println("Custom ForkJoinPool took: " + (endTime - startTime) + " milliseconds.");
    }
}

// c) Lässt sich im Aufgabenteil b "Work Stealing" beobachten?
// Workstealing: Eine Technik implementiert im ForkJoin-Framework, um Arbeit gleichmäßig auf Threads zu verteilen.
// Jeder Thread hat seine eigene Aufgaben-Warteschlage. Wenn ein Thread seine eigene Warteschlange abgearbeitet hat,
// versucht er Aufgaben (z.B. rekursive Quicksort-Aufgaben) aus Warteschlangen anderer Threads zu stehlen und abzuarbeiten.
// Ziel: Optimierung der Auslastung, sowie Vermeidung von Leerlaufzeit.

// Hinweise:
//
// Thread-Nutzung: Man sieht bei der Ausgabe, unterschiedliche Threads bei Ausführung von compute(),
// für verschiedene Quicksort-Aufgaben. Ein Zeichen dafür, dass die Aufgaben dynamisch verteilt
// und möglicherweise gestohlen wurden.
//
// Geschwindigkeitsunterschied: Custom ForkJoinPool (mit erhöhten Parallelität) zeigt eine
// verkürzte Sortierzeit. Zeichen dafür, dass zusätzliche Thread-Anzahl, zusätzliche Aufgaben
// übernommen hat (möglicherweise durch "Work Stealing"). Dadurch wurde Bearbeitungszeit weniger.
//
// Zusammenfassung: Wenn im Laufe des Programms viele unterschiedliche Threads bei Bearbeitung
// verschiedener Partitionen zu sehen sind und die Performance des Custom ForkJoinPool
// besser ist als die des Standardpools, kann man schlussfolgern, dass "Work Stealing"
// effektiv genutzt wurde.


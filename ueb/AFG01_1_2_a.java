/*  Aufgabenblatt 1
    1.2 a) Implementieren Sie in Java eine Version des Odd Even Sort-Algorithmus ohne Verwendung von
    Nebenlaufigkeit. Testen Sie Ihre Implementierung mit zuf ¨ allig generierten Zahlenfolgen. Pr ¨ ufen ¨
    Sie das Resultat auf Korrektheit!

    // Anzahl Schritte implementieren
 */
package ueb;

class OddEvenSort {
    private Integer[] array;

    public OddEvenSort(Integer[] array) {
        this.array = array;
    }

    public OddEvenSort() {
    }

    public void oddEvenSort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            sorted = oddStep() && sorted;
            sorted = evenStep() && sorted;
        }
    }

    private boolean oddStep() {
        boolean swapped = false;
        for (int i = 0; i < array.length - 1; i += 2) {
            if (array[i] > array[i + 1]) {
                swap(array, i, i + 1);
                swapped = true;
            }
        }
        return !swapped;
    }

    private boolean evenStep() {
        boolean swapped = false;
        for (int i = 1; i < array.length - 1; i += 2) {
            if (array[i] > array[i + 1]) {
                swap(array, i, i + 1);
                swapped = true;
            }
        }
        return !swapped;
    }

    private void swap(Integer[] array, Integer i, Integer j) {
        Integer tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}

public class AFG01_1_2_a {
    public static void main(String[] args) {
        sortAndPrintArray();
    }

    public static void sortAndPrintArray() {
        Integer[] randomArray = AFG01_00_ArrayUtils.generateRandomArray(15);

        System.out.println("Ursprüngliches Array: ");
        AFG01_00_ArrayUtils.printArray(randomArray);

        OddEvenSort sorter = new OddEvenSort(randomArray);
        sorter.oddEvenSort();

        System.out.println();
        System.out.println("Sortiertes Array: ");
        AFG01_00_ArrayUtils.printArray(randomArray);

        System.out.println();
        AFG01_00_ArrayUtils.isSorted(randomArray);
    }

}

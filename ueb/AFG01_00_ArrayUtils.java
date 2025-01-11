package ueb;

import java.util.Random;

public class AFG01_00_ArrayUtils {
    public static Integer[] generateRandomArray(int size) {
        Random rand = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; ++i) {
            array[i] = rand.nextInt(100);
        }
        return array;
    }

    public static void printArray(Integer[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + "\t");
        }
        System.out.print("\n");
    }

    public static void isSorted(Integer[] array) {
        boolean result = true;
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i] > array[i + 1]) {
                result = false;
            }
        }
        printSortResult(result);
    }

    public static void printSortResult(boolean result) {
        if (result) {
            System.out.println("Das Array ist korrekt sortiert.");
        } else {
            System.out.println("Das Array ist NICHT korrekt sortiert.");
        }
    }
}

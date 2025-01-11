package vl;

public class VL_04_07_Semaphor {
    private int wert;

    public VL_04_07_Semaphor(int initial) {
        this.wert = initial;
        // Initialisierung: Ein Semaphor wird mit einem Startwert initialisiert. Dieser Wert bestimmt,
        // wie viele Threads den kritischen Bereich betreten dürfen.
    }

    // Synchronisation: Beide Methoden up() und down() sind synchronisiert, wodurch sichergestellt wird, dass nur
    // ein Thread zur gleichen Zeit auf die Semaphore-Variable zugreifen kann.
    public synchronized void up() {
        // FSP: up -> Sema[i + 1]
        // kein Wächter, daher hier auch kein wait
        ++wert;
        notifyAll();
    }

    public synchronized void down() throws InterruptedException {
        // FSP: when (i > 0) down -> Sema[i - 1]
        while (!(wert > 0)) {
            wait();
        }
        --wert;
        notifyAll();
    }

    // Verwendung von wait() und notifyAll():
    // wait() wird verwendet, um Threads anzuhalten, wenn der Zähler <= 0 ist.
    // notifyAll() wird aufgerufen, um alle wartenden Threads zu benachrichtigen, dass sich der Zustand der Semaphore
    // geändert hat.

    // Diese Implementierung: Entspricht FSP-Beschreibung von Semaphoren und ermöglicht
    // gegenseitigen Ausschluss (Mutex) für kritische Bereiche
}

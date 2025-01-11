package vl;

public class VL_04_06_BoundedBuffer<E> {
    private final E[] speicher;
    private int rein = 0,   // nächster Schreibindex
            raus = 0,   // nächster Leseindex
            count = 0;  // Belegungszähler
    private final int size;

    public VL_04_06_BoundedBuffer(int size) {
        this.size = size;
        speicher = (E[]) new Object[size];
    }


    public synchronized void put(E object) throws InterruptedException {
        while (!(count < size)) wait();    // count == size
        speicher[rein] = object;
        ++count;
        rein = (rein + 1) % size;
        notifyAll();
    }

    public synchronized E get() throws InterruptedException {
        while (!(count > 0)) wait();     // count == 0
        final E object = speicher[raus];
        speicher[raus] = null;
        --count;
        raus = (raus + 1) % size;
        notifyAll();
        return object;
    }

}

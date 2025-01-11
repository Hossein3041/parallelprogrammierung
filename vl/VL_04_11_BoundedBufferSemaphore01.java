package vl;

public class VL_04_11_BoundedBufferSemaphore01<E> {
    private final E[] speicher;
    private int vorne = 0, hinten = 0, count = 0;
    private final int size;
    private final VL_04_07_Semaphor freie, belegte;

    public VL_04_11_BoundedBufferSemaphore01(int size) {
        this.size = size;
        speicher = (E[]) new Object[size];
        freie = new VL_04_07_Semaphor(size);
        belegte = new VL_04_07_Semaphor(0);
    }

    public synchronized void put(E object) throws InterruptedException {
        freie.down();   // freie.acquire();
        speicher[vorne] = object;
        ++count;
        vorne = (vorne + 1) % size;
        belegte.up();   // belegte.release();
    }

    public synchronized E get() throws InterruptedException {
        belegte.down(); // belegte.acquire;
        E object = speicher[hinten];
        --count;
        hinten = (hinten + 1) % size;
        freie.up(); // freie.release();
        return object;
    }
}

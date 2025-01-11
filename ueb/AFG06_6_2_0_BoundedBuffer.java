package ueb;

public class AFG06_6_2_0_BoundedBuffer<E> {
    private final E[] speicher;
    private int rein = 0, raus = 0, count = 0;
    private final int size;

    public AFG06_6_2_0_BoundedBuffer(int size) {
        this.size = size;
        speicher = (E[]) new Object[size];
    }

    public synchronized void put(E object) throws InterruptedException {
        while (!(count < size)) wait();
        speicher[rein] = object;
        ++count;
        rein = (rein + 1) % size;
        notifyAll();
    }

    public synchronized E get() throws InterruptedException {
        while (!(count > 0)) wait();
        final E object = speicher[raus];
        speicher[raus] = null;
        --count;
        raus = (raus + 1) % size;
        notifyAll();
        return object;
    }
}

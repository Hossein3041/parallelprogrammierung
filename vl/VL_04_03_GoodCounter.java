package vl;

public class VL_04_03_GoodCounter extends VL_04_01_Counter {
    public synchronized void incrementValue() {
        super.incrementValue();
    }

    public synchronized int getValue() {
        return super.getValue();
    }
}

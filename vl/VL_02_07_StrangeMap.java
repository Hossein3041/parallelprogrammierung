package vl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class VL_02_07_StrangeMap {
    private final Map map;
    private final int noOfReaders;
    private final int noOfPagesPerReader;
    private final CountDownLatch start;
    private final CountDownLatch end;

    public VL_02_07_StrangeMap(Map<String, Date> map, int noOfReaders, int noOfPagesPerReader) {
        this.map = map;
        this.noOfReaders = noOfReaders;
        this.noOfPagesPerReader = noOfPagesPerReader;
        this.start = new CountDownLatch(1);
        this.end = new CountDownLatch(noOfReaders);
        start();
    }

    void start() {
        try {
            VL_02_05_WebStat webStat = new VL_02_05_WebStat(map);

            // create readers
            Thread.Builder tb = Thread.ofPlatform();
            for (int i = 0; i < noOfReaders; ++i) {
                tb.start(new VL_02_06_WebClientSimulator(webStat, noOfPagesPerReader, i, start, end));
            }

            // start readers
            start.countDown();

            // wait for termination of readers
            end.await();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Map<String, Date> map;
        // map = new Hashtable<String, Date>(1);
        map = new HashMap<String, Date>(1);
        // map = Collections.synchronizedMap(new HashMap<String, Date>(1));
        new VL_02_07_StrangeMap(map, 25, 1500);

        // check content of map now!
    }
}













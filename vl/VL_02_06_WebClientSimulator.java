package vl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class VL_02_06_WebClientSimulator implements Runnable {
    private final VL_02_05_WebStat webStat;
    private final int noOfPages;
    private final int name;
    private final CountDownLatch start, end;

    public VL_02_06_WebClientSimulator(VL_02_05_WebStat webStat, int noOfPages, int no, CountDownLatch start, CountDownLatch end) {
        this.webStat = webStat;
        this.noOfPages = noOfPages;
        this.name = no;
        this.start = start;
        this.end = end;
    }

    public void run() {
        try {
            start.await();
            // visit all pages
            for (int i = 0; i < noOfPages; ++i) {
                // create location name from thread name and current loop index
                String location = "/directory_accessed_by_" + name + "/page" + i + ".html";
                webStat.rememberLastAccess(location);
            }
            end.countDown();
        } catch (Exception e) {
        }
    }
}

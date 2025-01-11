package vl;

import java.util.Date;
import java.util.Map;

public class VL_02_05_WebStat {
    // map used to store the (key, value) pairs
    private Map<String, Date> map;

    public VL_02_05_WebStat(Map<String, Date> map) {
        // initialize local variable from constructor
        this.map = map;
        this.map.clear();
    }

    public void rememberLastAccess(String location) {
        map.put(location, new Date());
    }
}

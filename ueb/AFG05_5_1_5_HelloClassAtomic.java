package ueb;

public class AFG05_5_1_5_HelloClassAtomic extends AFG05_5_1_0_HelloClass {
    public AFG05_5_1_5_HelloClassAtomic(String name) {
        super(name);
    }

    @Override
    protected synchronized void printIt(String str) {
        for (int i = 0; i < str.length(); ++i) {
            try {
                Thread.sleep(Math.round(Math.random() * 100));
            } catch (Exception e) {
            }
            System.out.print(str.substring(i, i + 1));
        }
        System.out.println();
    }
}

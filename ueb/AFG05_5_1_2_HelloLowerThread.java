package ueb;

public class AFG05_5_1_2_HelloLowerThread extends Thread {
    private AFG05_5_1_0_HelloClass helloClass;

    public AFG05_5_1_2_HelloLowerThread(AFG05_5_1_0_HelloClass helloClass) {
        this.helloClass = helloClass;
    }

    @Override
    public void run() {
        helloClass.printLower();
    }
}

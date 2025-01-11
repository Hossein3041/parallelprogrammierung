package ueb;

public class AFG05_5_1_3_HelloUpperThread extends Thread {
    private AFG05_5_1_0_HelloClass helloClass;

    public AFG05_5_1_3_HelloUpperThread(AFG05_5_1_0_HelloClass helloClass) {
        this.helloClass = helloClass;
    }

    @Override
    public void run() {
        helloClass.printUpper();
    }
}

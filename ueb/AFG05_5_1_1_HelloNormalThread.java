package ueb;

public class AFG05_5_1_1_HelloNormalThread extends Thread {
    private AFG05_5_1_0_HelloClass helloClass;

    public AFG05_5_1_1_HelloNormalThread(AFG05_5_1_0_HelloClass helloClass) {
        this.helloClass = helloClass;
    }

    @Override
    public void run() {
        helloClass.printNormal();
    }
}

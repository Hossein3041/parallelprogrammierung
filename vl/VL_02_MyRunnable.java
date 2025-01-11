package vl;

public class VL_02_MyRunnable implements Runnable{
    /*
    public static void main(String... args){
        VL_02_Utils.printThreadData(null);

        // create named threads from myRunnable
        Thread myFirst = new Thread(new VL_02_MyRunnable(-1));
        Thread mySecond = new Thread(new VL_02_MyRunnable(-2), "foo");
        myFirst.start(); mySecond.start();

        // await termination of both threads
        try{ myFirst.join(); mySecond.join();

        } catch(Exception e){}

        // start some anonymous threads from MyRunnable
        for(int n = 1; n <= 10; n+= 1){
            (new Thread(new VL_02_MyRunnable(n))).start();
        }
        System.out.println("* * *");
    }

     */

    public static void main(String... args){
        VL_02_Utils.printThreadData(null);

        // create named threads via Thread.Builder and start them
        Thread myFirst = Thread.ofPlatform().start(new VL_02_MyRunnable(-1));
        Thread mySecond = Thread.ofPlatform().name("foo").start(new VL_02_MyRunnable(-2));

        // await termination of both threads
        try{
            myFirst.join(); mySecond.join();
        } catch(Exception e){}

        // start some anonymous threads from MyRunnable
        for(int n = 1; n <= 10; n += 1){
            Thread.ofPlatform().start(new VL_02_MyRunnable(n));
        }
        System.out.println("* * *");
    }
    private final int n;
    public VL_02_MyRunnable(int n){
        this.n = n;
    }

    @Override
    public void run(){
        VL_02_Utils.doit(n);
    }
}

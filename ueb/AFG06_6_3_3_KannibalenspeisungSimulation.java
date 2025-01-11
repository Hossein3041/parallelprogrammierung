package ueb;

public class AFG06_6_3_3_KannibalenspeisungSimulation {
    public static void main(String... args) {
        initializeBuffetMembers();
    }

    public static void initializeBuffetMembers() {
        AFG06_6_3_0_Buffet buffet = new AFG06_6_3_0_Buffet();
        String[] namen = initializeKannibalArray();
        Thread[] buffetMembers = initializeThreadArray(namen);
        buffetMembers = initializeBuffetMembers(buffet, namen, buffetMembers);
        startBuffetMembers(buffetMembers);
    }

    public static String[] initializeKannibalArray() {
        return new String[]{"Kannibale A", "Kannibale B", "Kannibale C"};
    }

    public static Thread[] initializeThreadArray(String[] namen) {
        return new Thread[namen.length + 1];
    }

    public static Thread[] initializeBuffetMembers(AFG06_6_3_0_Buffet buffet, String[] namen, Thread[] buffetMembers) {
        for (int i = 0; i < namen.length; ++i)
            buffetMembers[i] = new AFG06_6_3_1_Kannibale(namen[i], buffet);
        buffetMembers[namen.length] = new AFG06_6_3_2_Koch(buffet);
        return buffetMembers;
    }

    private static void startBuffetMembers(Thread[] buffetMembers) {
        for (int i = 0; i < buffetMembers.length; ++i)
            buffetMembers[i].start();
    }
}



















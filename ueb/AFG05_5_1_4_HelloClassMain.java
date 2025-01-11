package ueb;

public class AFG05_5_1_4_HelloClassMain {
    public static void main(String... args) {
        AFG05_5_1_0_HelloClass helloClass = new AFG05_5_1_5_HelloClassAtomic("World");
        AFG05_5_1_1_HelloNormalThread normalThread = new AFG05_5_1_1_HelloNormalThread(helloClass);
        AFG05_5_1_2_HelloLowerThread lowerThread = new AFG05_5_1_2_HelloLowerThread(helloClass);
        AFG05_5_1_3_HelloUpperThread upperThread = new AFG05_5_1_3_HelloUpperThread(helloClass);

        normalThread.start();
        lowerThread.start();
        upperThread.start();

        try {
            normalThread.join();
            lowerThread.join();
            upperThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// a) Führen Sie HelloClassMain aus. Was wird auf der Konsole ausgegeben?
// >>>>>> H>>e >lHlEL helo Worlold!LO   <WORL<Dw!o<
// r l<d<!<
// <<<

// b) Durch welche Maßnahmen können Sie erreichen, dass die Ausgabe jedes Threads in einer eigenen
// Zeile ausgegeben wird? Erweitern Sie die Klasse HelloClass durch HelloClassAtomic extends HelloClass
// und setzen Sie dort ihre Maßnahmen um. Ersetzen Sie jetzt in HelloClassMain die Instanz von HelloClass
// durch HelloClassAtomic. Wie sieht die Ausgabe jetzt aus? Haben Ihre Maßnahmen gegriffen?
//
// Um sicherzustellen, dass die Ausgabe jedes Threads in einer Zeile erscheint, kann Synchronisation umgesetzt werden,
// um zu verhindern, dass mehrere Threads gleichzeitig auf Methode printIt zugreifen. Es wird sichergestellt,
// dass ein Thread die komplette ausgabe abschließt, bevor ein Thread mit seiner Ausgabe beginnt.
// public class AFG05_5_1_0_HelloClassAtomic extends AFG05_5_1_0_HelloClass ...
// protected synchronized void printIt(String str) { ...
// Ergebnis:
// >>> Hello World! <<<
// >>> HELLO WORLD! <<<
// >>> hello world! <<<
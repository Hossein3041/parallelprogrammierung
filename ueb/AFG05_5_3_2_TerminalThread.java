package ueb;

enum ReservierungsStatus {
    UNGUELTIG, ERFOLGREICH, BELEGT
}

public class AFG05_5_3_2_TerminalThread extends Thread {
    private final int anzahlReservierungsAnfrage = 10;
    private final AFG05_5_3_1_Zentralrechner rechner;
    private final int terminalNummer;

    public AFG05_5_3_2_TerminalThread(AFG05_5_3_1_Zentralrechner rechner, int terminalNummer) {
        this.rechner = rechner;
        this.terminalNummer = terminalNummer;
    }

    @Override
    public void run() {
        for (int i = 0; i < anzahlReservierungsAnfrage; ++i) {
            int platzNummer = (int) (Math.random() * rechner.plaetze.length);
            Boolean result = rechner.reservierePlatz(platzNummer);
            ReservierungsStatus status = (result == null) ? ReservierungsStatus.UNGUELTIG
                    : (result ? ReservierungsStatus.ERFOLGREICH : ReservierungsStatus.BELEGT);
            switch (status) {
                case UNGUELTIG:
                    System.out.println("Terminal " + terminalNummer + ": Ungültige Platznummer " + platzNummer);
                    break;
                case ERFOLGREICH:
                    System.out.println("Terminal " + terminalNummer + ": Platz " + platzNummer + " erfolgreich reserviert.");
                    break;
                case BELEGT:
                    System.out.println("Terminal " + terminalNummer + ": Platz " + platzNummer + " ist bereits reserviert.");
                    break;
            }
        }
    }
}

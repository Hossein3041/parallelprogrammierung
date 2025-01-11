// 2.1: Vollziehen Sie die Beispiele aus den Vorlesungsfolien zum Erzeugen bzw. Starten
// von Threads nach
//  Runnable
// Callable
// ExecutorService
// CompletableFuture
// Parallel Streams
// Virtual Threads
//
// Notiz: Bei Runnable wird für jede Aufgabe/Klasse ein separates Thread verwendet
package ueb;

class FileReaderRunnable implements Runnable {
    private String data;

    @Override
    public void run() {
        System.out.println("FileReader: Start reading file on thread: " + Thread.currentThread().getName());
        try {
            // Simuliere Einlesen von Daten durch sleep
            Thread.sleep(2000);
            data = "Initial file data";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FileReader: Finished reading file on thread: " + Thread.currentThread().getName());
    }

    public String getData() {
        return data;
    }
}

class DataProcessorRunnable implements Runnable {
    private String data;

    public DataProcessorRunnable(String data) {
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println("DataProcessor: Start processing data on thread: " + Thread.currentThread().getName());
        try {
            // Simuliere Datenverarbeitung durch sleep
            Thread.sleep(2000);
            data = data.toUpperCase();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DataProcessor: Finished processing data on thread: " + Thread.currentThread().getName());
    }

    public String getProcessedData() {
        return data;
    }
}

class FileWriterRunnable implements Runnable {
    private String processedData;

    public FileWriterRunnable(String processedData) {
        this.processedData = processedData;
    }

    @Override
    public void run() {
        System.out.println("FileWriter: Start writing data to file on thread: " + Thread.currentThread().getName());
        try {
            // Simuliere Datenspeicherung durch sleep
            Thread.sleep(2000);
            processedData += " FINISHED!";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FileWriter: Finished writing data: " + processedData + " on thread: " + Thread.currentThread().getName());
    }
}

public class AFG02_2_1_01_Runnable {
    private FileReaderRunnable fileReader;
    private DataProcessorRunnable dataProcessor;
    private FileWriterRunnable fileWriter;

    public static void main(String[] args) {
        AFG02_2_1_01_Runnable app = new AFG02_2_1_01_Runnable();
        app.runFileProcessing();
    }

    public void runFileProcessing() {
        runFileReader();
        runDataProcessor();
        runFileWriter();
    }

    private void runFileReader() {
        fileReader = new FileReaderRunnable();
        Thread readerThread = new Thread(fileReader);
        readerThread.start();

        try {
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runDataProcessor() {
        dataProcessor = new DataProcessorRunnable(fileReader.getData());
        Thread processorThread = new Thread(dataProcessor);
        processorThread.start();

        try {
            processorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runFileWriter() {
        fileWriter = new FileWriterRunnable(dataProcessor.getProcessedData());
        Thread writerThread = new Thread(fileWriter);
        writerThread.start();
    }
}

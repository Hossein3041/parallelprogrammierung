// Es wird für jede Aufgabe ein separates Thread verwendet
package ueb;

import java.util.concurrent.*;

class FileReaderCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("FileReader: Start reading file on thread:" + Thread.currentThread().getName());
        Thread.sleep(2000);
        String data = "Initial file data";
        System.out.println("FileReader: Finished reading file on thread" + Thread.currentThread().getName());
        return data;
    }
}

class DataProcessorCallable implements Callable<String> {
    private String data;

    public DataProcessorCallable(String data) {
        this.data = data;
    }

    @Override
    public String call() throws Exception {
        System.out.println("DataProcessor: Start processing data on thread" + Thread.currentThread().getName());
        Thread.sleep(2000);
        String processedData = data.toUpperCase();
        System.out.println("DataProcessor: Finished processing data on thread" + Thread.currentThread().getName());
        return processedData;
    }
}

class FileWriterCallable implements Callable<String> {
    private String processedData;

    public FileWriterCallable(String processedData) {
        this.processedData = processedData;
    }

    @Override
    public String call() throws Exception {
        System.out.println("FileWriter: Start writing data to file on thread" + Thread.currentThread().getName());
        Thread.sleep(2000);
        processedData += " FINISHED";
        System.out.println("FileWriter: Finished writing data: " + processedData + " on thread " + Thread.currentThread().getName());
        return processedData;
    }
}

public class AFG02_2_1_02_Callable {
    public static void main(String[] args) throws Exception {
        AFG02_2_1_02_Callable app = new AFG02_2_1_02_Callable();
        app.runFileProcessing();
    }

    public void runFileProcessing() throws Exception {
        String fileData = executeFileReader();
        String processedData = executeDataProcessor(fileData);
        executeFileWriter(processedData);
    }

    private String executeFileReader() throws Exception {
        FutureTask<String> fileReaderTask = new FutureTask<>(new FileReaderCallable());
        Thread readerThread = new Thread(fileReaderTask);
        readerThread.start();
        return fileReaderTask.get();
    }

    private String executeDataProcessor(String fileData) throws Exception {
        FutureTask<String> dataProcessorTask = new FutureTask<>(new DataProcessorCallable(fileData));
        Thread processorThread = new Thread(dataProcessorTask);
        processorThread.start();
        return dataProcessorTask.get();
    }

    private void executeFileWriter(String processedData) throws Exception {
        FutureTask<String> fileWriterTask = new FutureTask<>(new FileWriterCallable(processedData));
        Thread writerThread = new Thread(fileWriterTask);
        writerThread.start();
        fileWriterTask.get();
    }
}

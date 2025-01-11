package ueb;

import java.util.Arrays;
import java.util.List;

enum FileType {
    TEXT, CSV, XML
}

enum File {
    FILE_1(FileType.TEXT), FILE_2(FileType.CSV), FILE_3(FileType.XML),
    FILE_4(FileType.TEXT), FILE_5(FileType.CSV), FILE_6(FileType.XML);

    private final FileType fileType;

    File(FileType fileType) {
        this.fileType = fileType;
    }

    public FileType getFileType() {
        return fileType;
    }
}

class SleepStep {
    public static void getSleep() {
        try {
            Thread.sleep(1000); // Simuliere Verzögerung
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class FileReader {
    public String readFile(File file) {
        System.out.println("FileReader: Reading " + file + " (Type: " + file.getFileType() + ") on thread: " + Thread.currentThread().getName());
        SleepStep.getSleep();
        return "\"This is Content of " + file + "\" ";
    }
}

class DataProcessor {
    public String processFile(String fileContent) {
        System.out.println("DataProcessor: Processing " + fileContent + " on thread" + Thread.currentThread().getName());
        SleepStep.getSleep();
        return fileContent.toUpperCase();
    }
}

class FileWriter {
    public void writeFile(String processedContent) {
        System.out.println("FileWriter: Writing " + processedContent + " on thread" + Thread.currentThread().getName());
        SleepStep.getSleep();
        System.out.println(processedContent + " written successfully on thread" + Thread.currentThread().getName());
    }
}

public class AFG02_2_1_05_ParallelStreams {
    private List<File> files;
    private FileReader fileReader;
    private DataProcessor dataProcessor;
    private FileWriter fileWriter;

    public static void main(String[] args) {
        AFG02_2_1_05_ParallelStreams app = new AFG02_2_1_05_ParallelStreams();
        app.initialize();
        app.runFileProcessing();
    }

    private void initialize() {
        files = Arrays.asList(File.values());
        fileReader = new FileReader();
        dataProcessor = new DataProcessor();
        fileWriter = new FileWriter();
    }

    private void runFileProcessing() {
        files.parallelStream().map(this::readFile).map(this::processFile).forEach(this::writeFile);
    }

    private String readFile(File file) {
        return fileReader.readFile(file);
    }

    private String processFile(String fileData) {
        return dataProcessor.processFile(fileData);
    }

    private void writeFile(String processedData) {
        fileWriter.writeFile(processedData);
    }
}

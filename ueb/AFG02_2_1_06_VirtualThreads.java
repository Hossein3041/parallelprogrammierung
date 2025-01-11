package ueb;

import java.util.Arrays;
import java.util.List;

public class AFG02_2_1_06_VirtualThreads {
    private List<File> files;
    private FileReader fileReader;
    private DataProcessor dataProcessor;
    private FileWriter fileWriter;

    public static void main(String[] args) {
        AFG02_2_1_06_VirtualThreads app = new AFG02_2_1_06_VirtualThreads();
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
        List<Thread> threads = files.stream()
                .map(file -> Thread.ofVirtual().unstarted(() -> {
                    String fileData = readFile(file);
                    String processedData = processFile(fileData);
                    writeFile(processedData);
                })).toList();

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
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

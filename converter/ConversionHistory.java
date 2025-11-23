package converter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionHistory {
    private final File file;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConversionHistory(String fileName) {
        this.file = new File(fileName);
    }

    public synchronized void save(String record) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(record);
        } catch (IOException e) {
            System.err.println("Failed to write history: " + e.getMessage());
        }
    }

    public void showAll() {
        if (!file.exists()) {
            System.out.println("No history yet.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.err.println("Failed to read history: " + e.getMessage());
        }
    }

    public String formatRecord(String category, String from, String to, double input, double output) {
        return String.format("%s | %s: %.6f %s -> %.6f %s",
                sdf.format(new Date()), category, input, from, output, to);
    }
}

package converter;

import java.io.*;

public class ConversionHistory {

    String filename;

    public ConversionHistory(String filename) {
        this.filename = filename;
    }

    public void save(String record) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(record + "\n");
        } catch (IOException e) {
            System.out.println("Error saving history: " + e.getMessage());
        }
    }

    public String formatRecord(String category, String from, String to, double value, double result) {
        String timestamp = java.time.LocalDateTime.now().toString().replace('T', ' ').substring(0,19);
        return String.format("%s | [%s] %.6f %s → %.6f %s",
                timestamp, category, value, from, result, to);
    }


    // >>> ADD THIS METHOD <<<
    public void showHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean empty = true;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                empty = false;
            }

            if (empty) {
                System.out.println("(No history yet)");
            }

        } catch (IOException e) {
            System.out.println("Failed to read history file: " + e.getMessage());
        }
    }
}

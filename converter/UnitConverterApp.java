package converter;

import java.util.Scanner;

public class UnitConverterApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConversionHistory history = new ConversionHistory("history.txt");
        System.out.println("=== Universal Unit Conversion System (Console) ===");

        while (true) {
            System.out.println("\nChoose category:");
            System.out.println("1) Length (m, km, cm, mm, in, ft, yd, mi)");
            System.out.println("2) Weight (kg, g, mg, lb, oz, t)");
            System.out.println("3) Temperature (C, F, K)");
            System.out.println("4) Time (ms, s, m, h, d, wk)");
            System.out.println("5) Speed (m/s, km/h, mph, ft/s)");
            System.out.println("6) Volume (L, mL, m3, cm3, gal, pt)");
            System.out.println("7) Show history");
            System.out.println("8) Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("8")) {
                System.out.println("Goodbye!");
                break;
            } else if (choice.equals("7")) {
                history.showAll();
                continue;
            }

            Converter converter;
            String category;

            switch (choice) {
                case "1":
                    converter = new LengthConverter();
                    category = "Length";
                    break;
                case "2":
                    converter = new WeightConverter();
                    category = "Weight";
                    break;
                case "3":
                    converter = new TemperatureConverter();
                    category = "Temperature";
                    break;
                case "4":
                    converter = new TimeConverter();
                    category = "Time";
                    break;
                case "5":
                    converter = new SpeedConverter();
                    category = "Speed";
                    break;
                case "6":
                    converter = new VolumeConverter();
                    category = "Volume";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            try {
                System.out.print("From unit: ");
                String from = sc.nextLine().trim();
                System.out.print("To unit: ");
                String to = sc.nextLine().trim();
                System.out.print("Value: ");
                double value = Double.parseDouble(sc.nextLine().trim());

                Thread t = new Thread(new ConversionTask(category, converter, from, to, value, history));
                t.start();

                try {
                    t.join(); // Wait until the background conversion finishes
                } catch (InterruptedException e) {
                    System.err.println("Main thread interrupted while waiting for conversion to finish.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric value.");
            }
        }
        sc.close();
    }
}

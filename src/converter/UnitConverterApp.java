// File: converter/UnitConverterApp.java
package converter;

import java.util.Scanner;

public class UnitConverterApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConversionHistory history = new ConversionHistory("history.txt");

        System.out.println("=== Universal Unit Conversion System (Console) ===");

        while (true) {
            System.out.println("\nChoose category:");
            System.out.println(" 1) Length      (m, km, cm, mm, in, ft, yd, mi)");
            System.out.println(" 2) Weight      (kg, g, mg, lb, oz, t)");
            System.out.println(" 3) Temperature (C, F, K)");
            System.out.println(" 4) Time        (ms, s, m, h, d, wk)");
            System.out.println(" 5) Speed       (m/s, km/h, mph, ft/s)");
            System.out.println(" 6) Volume      (L, mL, m3, cm3, gal, pt)");
            System.out.println(" 7) Area        (m2, km2, cm2, mm2, in2, ft2, yd2, acre, hectare)");
            System.out.println(" 8) Energy      (J, kJ, MJ, Wh, kWh, cal, kcal, BTU)");
            System.out.println(" 9) Pressure    (Pa, kPa, MPa, bar, psi, atm, mmHg, inHg)");
            System.out.println("10) Power       (W, kW, MW, HP, BTU/h)");
            System.out.println("11) Data        (bit, byte, KB, MB, GB, TB)");
            System.out.println("12) Currency    (USD, EUR, GBP, INR, JPY, CAD, AUD)");
            System.out.println("13) Show full history");
            System.out.println("14) Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            // Exit
            if (choice.equals("14")) {
                System.out.println("Goodbye!");
                break;
            }

            // Global history
            if (choice.equals("13")) {
                System.out.println("\n=== Conversion History (All Categories) ===");
                history.showHistory();
                continue;
            }

            Converter converter;
            String category;

            switch (choice) {
                case "1"  -> { converter = new LengthConverter();      category = "Length"; }
                case "2"  -> { converter = new WeightConverter();      category = "Weight"; }
                case "3"  -> { converter = new TemperatureConverter(); category = "Temperature"; }
                case "4"  -> { converter = new TimeConverter();        category = "Time"; }
                case "5"  -> { converter = new SpeedConverter();       category = "Speed"; }
                case "6"  -> { converter = new VolumeConverter();      category = "Volume"; }
                case "7"  -> { converter = new AreaConverter();        category = "Area"; }
                case "8"  -> { converter = new EnergyConverter();      category = "Energy"; }
                case "9"  -> { converter = new PressureConverter();    category = "Pressure"; }
                case "10" -> { converter = new PowerConverter();       category = "Power"; }
                case "11" -> { converter = new DataConverter();        category = "Data"; }
                case "12" -> { converter = new CurrencyConverter();    category = "Currency"; }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }

            try {
                System.out.print("From unit: ");
                String from = sc.nextLine().trim();

                System.out.print("To unit: ");
                String to = sc.nextLine().trim();

                System.out.print("Value: ");
                String valStr = sc.nextLine().trim();
                double value = Double.parseDouble(valStr);

                double result = converter.convert(from, to, value);

                System.out.printf("Result: %.6f %s%n", result, to);

                // Save the record
                String record = history.formatRecord(category, from, to, value, result);
                history.save(record);

            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric value.");
            } catch (InvalidUnitException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }
}

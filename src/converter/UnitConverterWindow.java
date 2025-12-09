package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UnitConverterWindow extends JFrame {

    private JComboBox<String> fromUnit;
    private JComboBox<String> toUnit;
    private JTextField valueField;
    private JTextField resultField;
    private JLabel statusLabel;

    private final String category;
    private final ConversionHistory history = new ConversionHistory("history.txt");

    public UnitConverterWindow(String category) {
        super(category + " Converter");
        this.category = category;

        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder();

        add(new JLabel("Category:"));
        add(new JLabel(category));

        add(new JLabel("From Unit:"));
        fromUnit = new JComboBox<>();
        add(fromUnit);

        add(new JLabel("To Unit:"));
        toUnit = new JComboBox<>();
        add(toUnit);

        add(new JLabel("Value:"));
        valueField = new JTextField();
        add(valueField);

        add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        JButton convertBtn = new JButton("Convert");
        convertBtn.addActionListener(e -> convert());
        add(convertBtn);


        JButton historyBtn = new JButton("History (" + category + ")");
        historyBtn.addActionListener(e -> showHistoryForCategory());
        add(historyBtn);

        statusLabel = new JLabel(" ");
        add(statusLabel);
        add(new JLabel("")); // filler

        loadUnits();

        setSize(450, 300);
        setLocationRelativeTo(null);
    }

    private void setBorder() {
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    private void loadUnits() {
        String[] units;

        switch (category) {
            case "Length" -> units = new String[]{"m", "km", "cm", "mm", "in", "ft", "yd", "mi"};
            case "Weight" -> units = new String[]{"kg", "g", "mg", "lb", "oz", "t"};
            case "Temperature" -> units = new String[]{"C", "F", "K"};
            case "Time" -> units = new String[]{"ms", "s", "m", "h", "d", "wk"};
            case "Speed" -> units = new String[]{"m/s", "km/h", "mph", "ft/s"};
            case "Volume" -> units = new String[]{"L", "ml", "m3", "cm3", "gal", "pt"};
            case "Area" -> units = new String[]{"m²", "km²", "cm²", "mm²", "in²", "ft²", "yd²", "acre", "hectare"};
            case "Energy" -> units = new String[]{"J", "kJ", "MJ", "Wh", "kWh", "cal", "kcal", "BTU"};
            case "Pressure" -> units = new String[]{"Pa", "kPa", "MPa", "bar", "psi", "atm", "mmHg", "inHg"};
            case "Power" -> units = new String[]{"W", "kW", "MW", "HP", "BTU/h"};
            case "Data" -> units = new String[]{"bit", "byte", "KB", "MB", "GB", "TB", "PB"};
            case "Currency" -> units = new String[]{"USD", "EUR", "GBP", "INR", "JPY", "CAD", "AUD"};
            default -> units = new String[0];
        }

        DefaultComboBoxModel<String> fromModel = new DefaultComboBoxModel<>(units);
        DefaultComboBoxModel<String> toModel   = new DefaultComboBoxModel<>(units);
        fromUnit.setModel(fromModel);
        toUnit.setModel(toModel);

        if (units.length > 1) {
            fromUnit.setSelectedIndex(0);
            toUnit.setSelectedIndex(1);
        }
    }

    private void convert() {
        String from = (String) fromUnit.getSelectedItem();
        String to = (String) toUnit.getSelectedItem();
        String valueText = valueField.getText().trim();

        if (from == null || to == null || valueText.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueText);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid numeric value.");
            return;
        }

        Converter converter;

        switch (category) {
            case "Length"      -> converter = new LengthConverter();
            case "Weight"      -> converter = new WeightConverter();
            case "Temperature" -> converter = new TemperatureConverter();
            case "Time"        -> converter = new TimeConverter();
            case "Speed"       -> converter = new SpeedConverter();
            case "Volume"      -> converter = new VolumeConverter();
            case "Area"        -> converter = new AreaConverter();
            case "Energy"      -> converter = new EnergyConverter();
            case "Pressure"    -> converter = new PressureConverter();
            case "Power"       -> converter = new PowerConverter();
            case "Data"        -> converter = new DataConverter();
            case "Currency"    -> converter = new CurrencyConverter();
            default -> { statusLabel.setText("Unknown category."); return; }
        }

        try {
            double result = converter.convert(from, to, value);
            resultField.setText(String.valueOf(result));
            statusLabel.setText("Conversion successful.");

            String record = history.formatRecord(category, from, to, value, result);
            history.save(record);

        } catch (InvalidUnitException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }

    private void showHistoryForCategory() {
        JDialog dlg = new JDialog(this, "History - " + category, true);
        dlg.setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(category)) {
                    found = true;
                    area.append(line + "\n");
                }
            }
            if (!found) area.setText("No history entries for '" + category + "'.");
        } catch (IOException e) {
            area.setText("No history available or failed to read history.txt");
        }

        dlg.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel btnP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton close = new JButton("Close");
        close.addActionListener(ev -> dlg.dispose());
        btnP.add(close);
        dlg.add(btnP, BorderLayout.SOUTH);

        dlg.setSize(500, 420);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
}

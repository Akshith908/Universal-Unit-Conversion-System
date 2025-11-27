package converter;

import java.awt.*;
import java.awt.event.*;

public class UnitConverterGUI extends Frame implements ActionListener, WindowListener {

    private Choice categoryChoice;
    private TextField fromUnitField;
    private TextField toUnitField;
    private TextField valueField;
    private TextField resultField;
    private Label statusLabel;
    private Button convertButton;

    public UnitConverterGUI() {
        super("Universal Unit Conversion System (AWT)");

        // Simple grid layout: 6 rows, 2 columns
        setLayout(new GridLayout(6, 2, 5, 5));

        // Category
        add(new Label("Category:"));
        categoryChoice = new Choice();
        categoryChoice.add("Length");
        categoryChoice.add("Weight");
        categoryChoice.add("Temperature");
        categoryChoice.add("Time");
        categoryChoice.add("Speed");
        categoryChoice.add("Volume");
        add(categoryChoice);

        // From unit
        add(new Label("From unit:"));
        fromUnitField = new TextField();
        add(fromUnitField);

        // To unit
        add(new Label("To unit:"));
        toUnitField = new TextField();
        add(toUnitField);

        // Value
        add(new Label("Value:"));
        valueField = new TextField();
        add(valueField);

        // Result
        add(new Label("Result:"));
        resultField = new TextField();
        resultField.setEditable(false);
        add(resultField);

        // Button
        convertButton = new Button("Convert");
        convertButton.addActionListener(this);
        add(convertButton);

        // Status label
        statusLabel = new Label(" ");
        add(statusLabel);

        // Window settings
        addWindowListener(this);
        setSize(420, 260);
        setLocationRelativeTo(null); // center window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String category = categoryChoice.getSelectedItem();
        String from = fromUnitField.getText().trim();
        String to = toUnitField.getText().trim();
        String valueText = valueField.getText().trim();

        if (from.isEmpty() || to.isEmpty() || valueText.isEmpty()) {
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

        if ("Length".equals(category)) {
            converter = new LengthConverter();
        } else if ("Weight".equals(category)) {
            converter = new WeightConverter();
        } else if ("Temperature".equals(category)) {
            converter = new TemperatureConverter();
        } else if ("Time".equals(category)) {
            converter = new TimeConverter();
        } else if ("Speed".equals(category)) {
            converter = new SpeedConverter();
        } else if ("Volume".equals(category)) {
            converter = new VolumeConverter();
        } else {
            statusLabel.setText("Unknown category.");
            return;
        }

        try {
            double result = converter.convert(from, to, value);
            resultField.setText(String.valueOf(result));
            statusLabel.setText("Conversion successful.");
        } catch (InvalidUnitException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }

    // WindowListener methods

    @Override
    public void windowOpened(WindowEvent e) {
        // not used
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // not used
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // not used
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // not used
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // not used
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // not used
    }

    public static void main(String[] args) {
        UnitConverterGUI gui = new UnitConverterGUI();
        gui.setVisible(true);
    }
}


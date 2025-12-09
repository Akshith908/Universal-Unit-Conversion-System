package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Swing version of your AWT UnitConverterGUI.
 * Uses a two-column GridLayout with tile-style panels (blue tiles).
 *
 * Replace your AWT class with this (or adapt names), the conversion logic
 * still depends on your Converter classes and ConversionHistory class.
 */
public class UnitConverterSwing extends JFrame {

    private static final Color TILE_BLUE = new Color(0x0A84FF);
    private static final Color TILE_RED  = new Color(0xD64550);
    private static final Color TILE_TEXT = Color.WHITE;

    private JComboBox<String> categoryCombo;
    private JComboBox<String> fromUnitCombo;
    private JComboBox<String> toUnitCombo;
    private JTextField valueField;
    private JTextField resultField;
    private JLabel statusLabel;
    private JButton convertButton;
    private JButton historyButton;
    private JButton exitButton;

    private ConversionHistory history = new ConversionHistory("history.txt");

    public UnitConverterSwing() {
        super("Universal Unit Conversion System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top header
        JLabel header = new JLabel("Universal Unit Conversion System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 20f));
        header.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Outer white background with padding
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Grid panel (5 rows x 2 cols) like your screenshot (tile appearance)
        JPanel grid = new JPanel(new GridLayout(5, 2, 16, 16));
        grid.setOpaque(false); // let outer's white show between tiles

        // Create tile panels (left & right pairs)
        grid.add(makeTilePanel(createCategoryPanel()));   // Row 1 left
        grid.add(makeTilePanel(createFromPanel()));       // Row 1 right

        grid.add(makeTilePanel(createToPanel()));         // Row 2 left
        grid.add(makeTilePanel(createValuePanel()));      // Row 2 right

        grid.add(makeTilePanel(createResultPanel()));     // Row 3 left
        grid.add(makeTilePanel(createButtonsPanel()));    // Row 3 right

        grid.add(makeTilePanel(createStatusPanel()));     // Row 4 left
        grid.add(makeTilePanel(createEmptyTile()));       // Row 4 right (placeholder)

        // Last row for History/Admin/Exit etc.
        grid.add(makeTilePanel(createHistoryPanel()));    // Row 5 left
        grid.add(makeTilePanel(createExitPanel()));       // Row 5 right (red exit tile)

        outer.add(grid, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(outer, BorderLayout.CENTER);

        // Default window size and centering
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize units for the default category
        updateUnitChoices();
    }

    // ==========================
    // Tile & sub-panel builders
    // ==========================
    private JPanel makeTilePanel(JComponent inner) {
        JPanel tile = new JPanel(new GridBagLayout()); // centers the inner component
        tile.setBackground(new Color(0xFFFFFF)); // parent's white will show between tiles
        tile.setBorder(new EmptyBorder(4, 4, 4, 4)); // spacing inside the white border

        // colored tile body
        JPanel body = new JPanel(new BorderLayout());
        body.setBorder(new EmptyBorder(18, 18, 18, 18)); // inside padding
        body.add(inner, BorderLayout.CENTER);
        body.setBackground(TILE_BLUE);
        body.setOpaque(true);

        tile.add(body);
        return tile;
    }

    private JPanel createCategoryPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel lbl = createTileLabel("Category");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(lbl);
        p.add(Box.createRigidArea(new Dimension(0,10)));

        categoryCombo = new JComboBox<>(new String[] {"Length", "Weight", "Temperature", "Time", "Speed", "Volume"});
        categoryCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        categoryCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) updateUnitChoices();
        });
        styleCombo(categoryCombo);
        p.add(categoryCombo);
        return p;
    }

    private JPanel createFromPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createTileLabel("From Unit"));
        p.add(Box.createRigidArea(new Dimension(0,10)));
        fromUnitCombo = new JComboBox<>();
        styleCombo(fromUnitCombo);
        fromUnitCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.add(fromUnitCombo);
        return p;
    }

    private JPanel createToPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createTileLabel("To Unit"));
        p.add(Box.createRigidArea(new Dimension(0,10)));
        toUnitCombo = new JComboBox<>();
        styleCombo(toUnitCombo);
        toUnitCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.add(toUnitCombo);
        return p;
    }

    private JPanel createValuePanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createTileLabel("Value"));
        p.add(Box.createRigidArea(new Dimension(0,10)));
        valueField = new JTextField();
        valueField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.add(valueField);
        return p;
    }

    private JPanel createResultPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createTileLabel("Result"));
        p.add(Box.createRigidArea(new Dimension(0,10)));
        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.add(resultField);
        return p;
    }

    private JPanel createButtonsPanel() {
        JPanel p = new JPanel(new GridLayout(2, 1, 8, 8));
        p.setOpaque(false);

        convertButton = new JButton("Convert");
        convertButton.setPreferredSize(new Dimension(160, 48));
        convertButton.addActionListener(e -> doConvert());
        styleButton(convertButton);

        historyButton = new JButton("Show History");
        historyButton.addActionListener(e -> showHistoryWindow());
        styleButton(historyButton);

        p.add(convertButton);
        p.add(historyButton);
        return p;
    }

    private JPanel createStatusPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createTileLabel("Status"));
        p.add(Box.createRigidArea(new Dimension(0,10)));
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(TILE_TEXT);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(statusLabel);
        return p;
    }

    private JPanel createHistoryPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());
        JLabel l = createTileLabel("History");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(l, BorderLayout.NORTH);

        JButton show = new JButton("<html><center>Open<br>History</center></html>");
        styleButton(show);
        show.addActionListener(e -> showHistoryWindow());
        p.add(show, BorderLayout.CENTER);
        return p;
    }

    private JPanel createExitPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());
        JLabel l = createTileLabel("Exit");
        l.setHorizontalAlignment(SwingConstants.CENTER);

        // build a red tile for exit
        JPanel redTile = new JPanel(new GridBagLayout());
        redTile.setBackground(TILE_RED);
        redTile.setBorder(new EmptyBorder(18,18,18,18));

        exitButton = new JButton("Exit");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(TILE_RED);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setFont(exitButton.getFont().deriveFont(Font.BOLD, 16f));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(160, 48));

        redTile.add(exitButton);
        p.add(redTile, BorderLayout.CENTER);
        return p;
    }

    private JPanel createEmptyTile() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridBagLayout());
        JLabel lbl = createTileLabel(""); // blank
        p.add(lbl);
        return p;
    }

    // ==========================
    // Styling helpers
    // ==========================
    private JLabel createTileLabel(String text) {
        JLabel l = new JLabel("<html><center>" + text + "</center></html>", SwingConstants.CENTER);
        l.setForeground(TILE_TEXT);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private void styleCombo(JComboBox<?> combo) {
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setFont(combo.getFont().deriveFont(14f));
    }

    private void styleButton(JButton btn) {
        btn.setBackground(TILE_BLUE);
        btn.setForeground(TILE_TEXT);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
    }

    // ==========================
    // Conversion logic (wired to your converter classes)
    // ==========================
    private void doConvert() {
        String category = (String) categoryCombo.getSelectedItem();
        String from = (String) fromUnitCombo.getSelectedItem();
        String to = (String) toUnitCombo.getSelectedItem();
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

            String record = history.formatRecord(category, from, to, value, result);
            history.save(record);

        } catch (InvalidUnitException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }

    // ==========================
    // Unit choices based on category
    // ==========================
    private void updateUnitChoices() {
        String category = (String) categoryCombo.getSelectedItem();
        String[] units;

        if ("Length".equals(category)) {
            units = new String[] { "m", "km", "cm", "mm", "in", "ft", "yd", "mi" };
        } else if ("Weight".equals(category)) {
            units = new String[] { "kg", "g", "mg", "lb", "oz", "t" };
        } else if ("Temperature".equals(category)) {
            units = new String[] { "C", "F", "K" };
        } else if ("Time".equals(category)) {
            units = new String[] { "ms", "s", "m", "h", "d", "wk" };
        } else if ("Speed".equals(category)) {
            units = new String[] { "m/s", "km/h", "mph", "ft/s" };
        } else if ("Volume".equals(category)) {
            units = new String[] { "L", "ml", "m3", "cm3", "gal", "pt" };
        } else {
            units = new String[0];
        }

        DefaultComboBoxModel<String> fromModel = new DefaultComboBoxModel<>(units);
        DefaultComboBoxModel<String> toModel   = new DefaultComboBoxModel<>(units);
        fromUnitCombo.setModel(fromModel);
        toUnitCombo.setModel(toModel);

        if (units.length > 1) {
            fromUnitCombo.setSelectedIndex(0);
            toUnitCombo.setSelectedIndex(1);
        }
    }

    // ==========================
    // History viewer
    // ==========================
    private void showHistoryWindow() {
        JDialog dlg = new JDialog(this, "Conversion History", true);
        dlg.setLayout(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                area.append(line + "\n");
            }
        } catch (IOException e) {
            area.setText("No history available or failed to read history.txt");
        }

        dlg.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(e -> dlg.dispose());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(close);
        dlg.add(bottom, BorderLayout.SOUTH);

        dlg.setSize(500, 400);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    // ==========================
    // Main
    // ==========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnitConverterSwing ui = new UnitConverterSwing();
            ui.setVisible(true);
        });
    }
}

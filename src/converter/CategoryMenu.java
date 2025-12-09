package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CategoryMenu extends JFrame {

    private static final Color TILE_BLUE = new Color(0x0A84FF);
    private static final Color TILE_TEXT = Color.WHITE;

    public CategoryMenu() {
        super("Universal Unit Conversion System");

        JLabel header = new JLabel("Universal Unit Converter", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 22f));
        header.setBorder(new EmptyBorder(15, 10, 15, 10));


        JPanel grid = new JPanel(new GridLayout(6, 2, 20, 20));
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));
        grid.setBackground(Color.WHITE);

        grid.add(makeTile("Length"));
        grid.add(makeTile("Weight"));
        grid.add(makeTile("Temperature"));
        grid.add(makeTile("Time"));
        grid.add(makeTile("Speed"));
        grid.add(makeTile("Volume"));


        grid.add(makeTile("Area"));
        grid.add(makeTile("Energy"));
        grid.add(makeTile("Pressure"));
        grid.add(makeTile("Power"));
        grid.add(makeTile("Data"));
        grid.add(makeTile("Currency"));


        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottom.setBackground(Color.WHITE);

        JButton historyAllBtn = new JButton("Show Full History");
        historyAllBtn.setBackground(TILE_BLUE);
        historyAllBtn.setForeground(TILE_TEXT);
        historyAllBtn.setFont(historyAllBtn.getFont().deriveFont(Font.BOLD, 14f));
        historyAllBtn.setOpaque(true);
        historyAllBtn.setFocusPainted(false);

        historyAllBtn.addActionListener(e -> showEntireHistory());


        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(Color.WHITE);
        right.add(historyAllBtn);
        bottom.add(right, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton makeTile(String category) {
        JButton btn = new JButton("<html><center>" + category + "</center></html>");
        btn.setBackground(TILE_BLUE);
        btn.setForeground(TILE_TEXT);
        btn.setFocusPainted(false);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 20f));
        btn.setOpaque(true);

        btn.addActionListener(e -> {
            // Open the converter window for this category
            new UnitConverterWindow(category).setVisible(true);
        });

        return btn;
    }


    private void showEntireHistory() {
        JDialog dlg = new JDialog(this, "Conversion History (All Categories)", true);
        dlg.setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            boolean has = false;
            while ((line = br.readLine()) != null) {
                has = true;
                area.append(line + "\n");
            }
            if (!has) area.setText("No history available.");
        } catch (IOException e) {
            area.setText("No history available or failed to read history.txt");
        }

        dlg.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel btnP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton close = new JButton("Close");
        close.addActionListener(ev -> dlg.dispose());
        btnP.add(close);
        dlg.add(btnP, BorderLayout.SOUTH);

        dlg.setSize(600, 500);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoryMenu().setVisible(true));
    }
}


package converter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class UnitConverterGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public UnitConverterGUI() {
        super("Universal Unit Conversion System - GUI (Placeholder)");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        System.out.println("GUI version is currently not implemented. Please use the console version (UnitConverterApp).");

        SwingUtilities.invokeLater(() -> {
            UnitConverterGUI frame = new UnitConverterGUI();
            frame.setVisible(true);
        });
    }
}

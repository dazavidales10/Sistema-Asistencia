package Vista;

import javax.swing.*;

public class PanelCoordinador extends JFrame {

    public PanelCoordinador() {

        setTitle("Panel Coordinador");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Coordinador");
        add(lbl);
    }
}
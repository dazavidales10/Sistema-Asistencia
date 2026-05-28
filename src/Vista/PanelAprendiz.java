package Vista;

import javax.swing.*;

public class PanelAprendiz extends JFrame {

    public PanelAprendiz() {

        

        setTitle("Panel Aprendiz");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Bienvenido Aprendiz");
        add(lbl);
    }
}

package Vista;

import javax.swing.*;

public class panelInstructor extends JFrame {

    public panelInstructor() {

        setTitle("Panel Instructor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Instructor");
        add(lbl);
    }
}
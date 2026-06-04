package Vista.aprendiz;

import javax.swing.*;
import java.awt.*; 

public class PanelAprendiz extends JFrame {

    // Constructor principal con parámetros
    public PanelAprendiz(String nombre, String ficha, int asistencia, int faltas, int tardanzas) {
        setTitle("Panel Aprendiz");              
        setSize(600, 400);                       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            
        setLayout(new BorderLayout());           

        
        JLabel header = new JLabel("Panel Aprendiz", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST;    
        gbc.gridx = 0; gbc.gridy = 0;

        centerPanel.add(new JLabel("Bienvenido: " + nombre), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Ficha: " + ficha), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Asistencia: " + asistencia + "%"), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Faltas: " + faltas), gbc);
        gbc.gridy++;
        centerPanel.add(new JLabel("Tardanzas: " + tardanzas), gbc);

        add(centerPanel, BorderLayout.CENTER);

        // 🔘 Panel inferior con botones (FlowLayout para distribución adaptable)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton btnHistorial = new JButton("Ver Historial");
        btnHistorial.setPreferredSize(new Dimension(250, 80)); 

        JButton btnExcusa = new JButton("Enviar Excusa");
        btnExcusa.setPreferredSize(new Dimension(250, 80)); 
        JButton btnConsultar = new JButton("Consultar Asistencia");
        btnConsultar.setPreferredSize(new Dimension(250, 80)); 
        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setPreferredSize(new Dimension(250, 80)); 

        
        JButton[] botones = {btnHistorial, btnExcusa, btnConsultar, btnCerrar};
        for (JButton b : botones) {
            b.setBackground(new Color(0, 180, 0));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            bottomPanel.add(b);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        
        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada");
            dispose();
                Vista.Login login = new Vista.Login();
            login.setVisible(true);
        });
    }

    public PanelAprendiz() {
        this("Prueba", "0000", 0, 0, 0);
    }

    public static void main(String[] args) {
        new PanelAprendiz("Pedro Gomez", "3364343", 92, 3, 2).setVisible(true);
    }
}
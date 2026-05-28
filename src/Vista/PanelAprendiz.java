package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelAprendiz extends JFrame {

    // Constructor principal con parámetros
    public PanelAprendiz(String nombre, String ficha, int asistencia, int faltas, int tardanzas) {
        setTitle("Panel Aprendiz");              // 🏷️ Título de la ventana
        setSize(600, 400);                       // 📐 Tamaño inicial
        setDefaultCloseOperation(EXIT_ON_CLOSE); // ❌ Cierra el programa al cerrar
        setLocationRelativeTo(null);             // 📍 Centra la ventana
        setLayout(new BorderLayout());           // 📐 Layout principal responsive

        // 🟩 Encabezado verde arriba
        JLabel header = new JLabel("Panel Aprendiz", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // 📋 Panel central con datos del aprendiz (GridBagLayout para alineación)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // márgenes
        gbc.anchor = GridBagConstraints.WEST;    // alineación a la izquierda
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
        btnHistorial.setPreferredSize(new Dimension(250, 80)); // ancho 250, alto 120

        JButton btnExcusa = new JButton("Enviar Excusa");
        btnExcusa.setPreferredSize(new Dimension(250, 80)); // ancho 250, alto 120
        JButton btnConsultar = new JButton("Consultar Asistencia");
        btnConsultar.setPreferredSize(new Dimension(250, 80)); // ancho 250, alto 120
        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setPreferredSize(new Dimension(250, 80)); // ancho 250, alto 120

        // 🎨 Estilo de botones
        JButton[] botones = {btnHistorial, btnExcusa, btnConsultar, btnCerrar};
        for (JButton b : botones) {
            b.setBackground(new Color(0, 180, 0));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            bottomPanel.add(b);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // 🛠️ Acción del botón cerrar sesión
        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada");
            dispose();
            Login login = new Login();
            login.setVisible(true);
        });
    }

    // 🧪 Constructor vacío para pruebas
    public PanelAprendiz() {
        this("Prueba", "0000", 0, 0, 0);
    }

    // 🚀 Método main para probarlo directamente
    public static void main(String[] args) {
        new PanelAprendiz("Pedro Gomez", "3364343", 92, 3, 2).setVisible(true);
    }
}

package Vista.instructor;

import javax.swing.*;
import java.awt.*;

public class panelInstructor extends JFrame {

    public panelInstructor(String nombre, String ficha, String especialidad) {

        setTitle("Panel Instructor");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= PANEL SUPERIOR =================
        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.setBackground(Color.WHITE);

        // Barra verde
        JLabel header = new JLabel("Panel Instructor", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setPreferredSize(new Dimension(0, 70));

        // Panel botón cerrar (debajo de la barra)
        JPanel panelCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelCerrar.setBackground(Color.WHITE);

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setPreferredSize(new Dimension(180, 50));
        btnCerrar.setBackground(new Color(0, 180, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);

        panelCerrar.add(btnCerrar);

        contenedorSuperior.add(header, BorderLayout.NORTH);
        contenedorSuperior.add(panelCerrar, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);

        // ================= PANEL CENTRAL =================
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Bienvenido: " + nombre), gbc);

        gbc.gridy++;
        centerPanel.add(new JLabel("Ficha: " + ficha), gbc);

        gbc.gridy++;
        centerPanel.add(new JLabel("Especialidad: " + especialidad), gbc);

        add(centerPanel, BorderLayout.CENTER);

        // ================= BOTONES INFERIORES =================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        bottomPanel.setBackground(Color.WHITE);

        JButton btnRegistrar = new JButton("Registrar Asistencia");
        JButton btnExcusa = new JButton("Gestionar Excusas");
        JButton btnVerAprendices = new JButton("Ver Aprendices");
        JButton btnReportes = new JButton("Generar Reportes");

        JButton[] botones = {
                btnRegistrar,
                btnExcusa,
                btnVerAprendices,
                btnReportes
        };

        for (JButton b : botones) {

            b.setPreferredSize(new Dimension(250, 80));
            b.setBackground(new Color(0, 180, 0));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setFocusPainted(false);

            bottomPanel.add(b);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // ================= EVENTO CERRAR SESIÓN =================
        btnCerrar.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Sesión cerrada");

            dispose();

            Vista.Login login = new Vista.Login();
            login.setVisible(true);

        });

    }

    public panelInstructor() {
        this("Prueba", "0000", "Prueba");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new panelInstructor(
                    "Pedro Gomez",
                    "3364343",
                    "Análisis y Desarrollo de Software"
            ).setVisible(true);

        });

    }
}
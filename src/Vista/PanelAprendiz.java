package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelAprendiz extends JFrame {

    // 🎯 Constructor principal: recibe los datos reales del aprendiz desde el Login
    public PanelAprendiz(String nombre, String ficha, int asistencia, int faltas, int tardanzas) {
        setTitle("Panel Aprendiz");
        setSize(900, 600);                       
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);             
        setLayout(new BorderLayout());           

        // 🟩 Encabezado verde superior
        JLabel header = new JLabel("Panel Aprendiz", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setPreferredSize(new Dimension(900, 70));
        add(header, BorderLayout.NORTH);

        // 📋 Panel central con datos del aprendiz
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblBienvenido = new JLabel("Bienvenido: " + nombre);
        lblBienvenido.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblBienvenido, gbc);

        gbc.gridy++;
        JLabel lblFicha = new JLabel("Ficha: " + ficha);
        lblFicha.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblFicha, gbc);

        gbc.gridy++;
        JLabel lblAsistencia = new JLabel("Asistencia: " + asistencia + "%");
        lblAsistencia.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblAsistencia, gbc);

        gbc.gridy++;
        JLabel lblFaltas = new JLabel("Faltas: " + faltas);
        lblFaltas.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblFaltas, gbc);

        gbc.gridy++;
        JLabel lblTardanzas = new JLabel("Tardanzas: " + tardanzas);
        lblTardanzas.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblTardanzas, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // 🔘 Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setBackground(Color.WHITE);

        JButton btnHistorial = new JButton("Ver Historial");
        JButton btnExcusa = new JButton("Enviar Excusa");
        JButton btnConsultar = new JButton("Consultar Asistencia");
        JButton btnCerrar = new JButton("Cerrar Sesión");

        JButton[] botones = {btnHistorial, btnExcusa, btnConsultar, btnCerrar};
        for (JButton b : botones) {
            b.setBackground(new Color(0, 180, 0));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Arial", Font.BOLD, 18));
            b.setPreferredSize(new Dimension(220, 55));
            bottomPanel.add(b);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // 🛠️ Acción del botón cerrar sesión
        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada");
            dispose();
        });
    }
}

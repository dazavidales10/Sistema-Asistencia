package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelCoordinador extends JFrame {

    // Componentes globales (para moverlos en resize)
    private JLabel arrow, lblCoordinador, panelCoordinador;
    private JLabel titulo, nombreCoordinador, program;

    private JButton btnFichas, btnInstr, btnAprendices, btnExit;

    public PanelCoordinador(String nombre, String area) {

        setTitle("Panel Coordinador");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setContentPane(panel);

        // ================= HEADER =================
        arrow = new JLabel("←");
        arrow.setFont(new Font("Arial", Font.BOLD, 40));
        arrow.setForeground(Color.WHITE);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(arrow);

        lblCoordinador = new JLabel("Panel Coordinador");
        lblCoordinador.setForeground(Color.WHITE);
        lblCoordinador.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblCoordinador);

        panelCoordinador = new JLabel();
        panelCoordinador.setOpaque(true);
        panelCoordinador.setBackground(new Color(0, 180, 0));
        panel.add(panelCoordinador);

        // ================= CONTENIDO =================
        titulo = new JLabel("Coordinador");
        titulo.setFont(new Font("Arial", Font.PLAIN, 50));
        panel.add(titulo);

        nombreCoordinador = new JLabel("Nombre: " + nombre);
        nombreCoordinador.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(nombreCoordinador);

        program = new JLabel("Área: " + area);
        program.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(program);

        // ================= BOTONES =================
        btnFichas = new JButton("Gestión Fichas");
        styleButton(btnFichas);
        panel.add(btnFichas);

        btnInstr = new JButton("Gestión Instructores");
        styleButton(btnInstr);
        panel.add(btnInstr);

        btnAprendices = new JButton("Gestión Aprendices");
        styleButton(btnAprendices);
        panel.add(btnAprendices);

        btnExit = new JButton("Cerrar Sesion");
        styleButton(btnExit);
        panel.add(btnExit);

        btnExit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada");
            dispose();
            new Login().setVisible(true);
        });

        // ================= RESPONSIVE ENGINE =================
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        resizeComponents();
    }

    // ================= RESPONSIVE POSITIONING =================
    private void resizeComponents() {

        int w = getWidth();
        int h = getHeight();

        // HEADER
        panelCoordinador.setBounds(0, 0, w, 70);
        arrow.setBounds(30, 5, 50, 50);
        lblCoordinador.setBounds(100, 0, w, 70);

        // CONTENIDO CENTRADO
        titulo.setBounds(w / 2 - 150, h / 2 - 200, 500, 70);
        nombreCoordinador.setBounds(w / 2 - 150, h / 2 - 100, 600, 40);
        program.setBounds(w / 2 - 150, h / 2 - 40, 600, 40);

        // BOTONES (alineados abajo responsive)
        int btnY = h - 180;
        int spacing = w / 4;

        btnFichas.setBounds(spacing - 120, btnY, 240, 70);
        btnInstr.setBounds(2 * spacing - 120, btnY, 240, 70);
        btnAprendices.setBounds(3 * spacing - 120, btnY, 240, 70);

        // EXIT (arriba derecha responsive)
        btnExit.setBounds(w - 260, 120, 240, 70);
    }

    // ================= BUTTON STYLE =================
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(0, 180, 0));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
    }

    public static void main(String[] args) {

        new PanelCoordinador(
                "Ana Torres",
                "Coordinación Académica"
        ).setVisible(true);
    }
}
package modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

    private boolean mostrar = false;
    private JPasswordField txtPass;   // 👈 ahora global

    public Login() {

        setTitle("Login");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setContentPane(panel);

        JLabel titulo = new JLabel("Inicio de Sesión");
        titulo.setBounds(150, 20, 200, 30);
        titulo.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(titulo);

        JLabel lblId = new JLabel("Número de Identificación");
        lblId.setOpaque(true);
        lblId.setBackground(new Color(0, 180, 0));
        lblId.setForeground(Color.WHITE);
        lblId.setBounds(40, 70, 180, 25);
        panel.add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(220, 70, 180, 25);
        panel.add(txtId);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setOpaque(true);
        lblTipo.setBackground(new Color(0, 180, 0));
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setBounds(40, 110, 80, 25);
        panel.add(lblTipo);

        String[] tipos = {"CC", "CE", "TI", "PPT"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);
        cbTipo.setBounds(130, 110, 100, 25);
        panel.add(cbTipo);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setOpaque(true);
        lblRol.setBackground(new Color(0, 180, 0));
        lblRol.setForeground(Color.WHITE);
        lblRol.setBounds(240, 110, 80, 25);
        panel.add(lblRol);

        String[] roles = {"Coordinador", "Instructor", "Aprendiz"};
        JComboBox<String> cbRol = new JComboBox<>(roles);
        cbRol.setBounds(320, 110, 100, 25);
        panel.add(cbRol);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setOpaque(true);
        lblPass.setBackground(new Color(0, 180, 0));
        lblPass.setForeground(Color.WHITE);
        lblPass.setBounds(40, 150, 120, 25);
        panel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(170, 150, 200, 25);
        panel.add(txtPass);

        // 👁 ICONO
        JLabel eye = new JLabel("👁");
        eye.setBounds(380, 150, 30, 25);
        eye.setCursor(new Cursor(Cursor.HAND_CURSOR)); // cursor mano
        panel.add(eye);

        // ================= FUNCIÓN MOSTRAR / OCULTAR =================
        eye.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (mostrar) {
                    txtPass.setEchoChar('•'); // ocultar
                    eye.setText("👁");
                    mostrar = false;
                } else {
                    txtPass.setEchoChar((char) 0); // mostrar texto
                    eye.setText("/");
                    mostrar = true;
                }
            }
        });

        JButton btn = new JButton("Ingresar");
        btn.setBounds(160, 210, 120, 35);
        btn.setBackground(new Color(0, 180, 0));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        panel.add(btn);

        btn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Ingresando...");
        });
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
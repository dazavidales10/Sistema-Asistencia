
// =======================
// Login.java
// =======================

package Vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import Modelo.Conexion;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

    private boolean mostrar = false;
    private JPasswordField txtPass;
    private JTextField txtId;
    private JComboBox<String> cbTipo;
    private JComboBox<String> cbRol;

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

        txtId = new JTextField();
        txtId.setBounds(220, 70, 180, 25);
        panel.add(txtId);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setOpaque(true);
        lblTipo.setBackground(new Color(0, 180, 0));
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setBounds(40, 110, 80, 25);
        panel.add(lblTipo);

        String[] tipos = {"CC", "CE", "TI", "PPT"};
        cbTipo = new JComboBox<>(tipos);
        cbTipo.setBounds(130, 110, 100, 25);
        panel.add(cbTipo);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setOpaque(true);
        lblRol.setBackground(new Color(0, 180, 0));
        lblRol.setForeground(Color.WHITE);
        lblRol.setBounds(240, 110, 80, 25);
        panel.add(lblRol);

        String[] roles = {"Coordinador", "Instructor", "Aprendiz"};
        cbRol = new JComboBox<>(roles);
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

        JLabel eye = new JLabel("👁");
        eye.setBounds(380, 150, 30, 25);
        eye.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(eye);

        eye.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (mostrar) {
                    txtPass.setEchoChar('•');
                    eye.setText("👁");
                    mostrar = false;
                } else {
                    txtPass.setEchoChar((char) 0);
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

            String identificacion = txtId.getText();
            String tipo = cbTipo.getSelectedItem().toString();
            String rol = cbRol.getSelectedItem().toString();
            String pass = String.valueOf(txtPass.getPassword());

            try {

                Connection con = Conexion.conectar();

                // =======================
                // SQL UNIFICADO CORRECTO
                // =======================
                String sql =
                        "SELECT u.*, " +
                        "a.numeroFicha, a.programa, " +
                        "c.area " +
                        "FROM usuarios u " +
                        "LEFT JOIN aprendiz a ON u.id = a.id " +
                        "LEFT JOIN coordinador c ON u.id = c.id " +
                        "WHERE u.identificacion = ? " +
                        "AND u.tipoDocumento = ? " +
                        "AND u.rol = ? " +
                        "AND u.password = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, identificacion);
                ps.setString(2, tipo);
                ps.setString(3, rol);
                ps.setString(4, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(this,
                            "Bienvenido " + rs.getString("nombre"));

                    // =======================
                    // ABRIR SEGUN ROL
                    // =======================

                    if (rol.equals("Aprendiz")) {

                        new Vista.aprendiz.PanelAprendiz(
                                rs.getString("nombre"),
                                rs.getString("numeroFicha"),
                                95,
                                2,
                                1
                        ).setVisible(true);

                    } else if (rol.equals("Instructor")) {


                        new Vista.instructor.panelInstructor( ).setVisible(true);

                    } else if (rol.equals("Coordinador")) {
                        System.out.println("Nombre: " + rs.getString("nombre"));
                        System.out.println("Area: " + rs.getString("area"));
                        System.out.println("Rol: " + rs.getString("rol"));
                        new Vista.coordinador.PanelCoordinador(

                                rs.getString("nombre"),
                                rs.getString("area")
                        ).setVisible(true);
                    }

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Credenciales incorrectas");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
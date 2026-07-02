package Vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import Conexion.Conexion;
import Vista.aprendiz.PanelAprendiz;
import Vista.coordinador.PanelCoordinador;
import Vista.instructor.PanelInstructor;

import java.awt.*;
//Recordar iniciar sesion 
import java.util.prefs.Preferences;

public class Login extends JFrame {

    private JPasswordField txtPass;
    private JTextField txtId;
    private JComboBox<String> cbTipo;
    private JComboBox<String> cbRol;
    private JButton btnMostrar;
    private boolean mostrarPassword = false;
    private char echoChar;

    private Preferences prefs = Preferences.userNodeForPackage(Login.class);

    public Login() {

        String usuarioGuardado =
        prefs.get("usuario", null);


        System.out.println("ID GUARDADO = " + usuarioGuardado);

        if(usuarioGuardado!=null){

            try {

                Connection con = Conexion.conectar();

                String sql = """
                        SELECT
                            u.id,
                            u.identificacion,
                            u.nombre,
                            u.rol,

                            a.idAprendiz,
                            a.numeroFicha AS fichaAprendiz,

                            c.area,

                            i.idInstructor,
                            i.especialidad,
                            i.numeroFicha AS fichaInstructor

                            FROM usuarios u

                        LEFT JOIN aprendiz a
                        ON u.identificacion=a.documento

                        LEFT JOIN coordinador c
                        ON u.id=c.id

                        LEFT JOIN instructor i
                        ON u.id=i.id

                        WHERE u.identificacion=?
                        """;

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,prefs.get("usuario", ""));

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    String rol = rs.getString("rol");

                    if (rol.equals("Aprendiz")) {

                        new PanelAprendiz(

                                rs.getInt("idAprendiz"),

                                rs.getString("nombre"),

                                rs.getString("fichaAprendiz")

                        ).setVisible(true);

                        dispose();

                    } else if (rol.equals("Instructor")) {

                        new PanelInstructor(
                            rs.getInt("idInstructor"),
                            rs.getString("nombre"),
                            rs.getString("especialidad"),
                            rs.getString("fichaInstructor")
                        ).setVisible(true);

                    } else if (rol.equals("Coordinador")) {

                        new PanelCoordinador(

                                rs.getString("nombre"),

                                rs.getString("area")

                        ).setVisible(true);

                        dispose();
                    }

                    rs.close();
                    ps.close();
                    con.close();

                    dispose();
                    return;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

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
            txtPass.setBounds(170, 150, 170, 25);

            echoChar = txtPass.getEchoChar();

            panel.add(txtPass);

            // Botón mostrar contraseña
            ImageIcon iconoCerrado = new ImageIcon(getClass().getResource("/imagenes/ojo_cerrado.png"));
            Image imagenCerrada = iconoCerrado.getImage().getScaledInstance(21,13, Image.SCALE_SMOOTH);
            ImageIcon ojoCerrado = new ImageIcon(imagenCerrada);

            ImageIcon iconoAbierto = new ImageIcon(getClass().getResource("/imagenes/ojo_abierto.png"));
            Image imagenAbierta = iconoAbierto.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon ojoAbierto = new ImageIcon(imagenAbierta);

            btnMostrar = new JButton(ojoCerrado);
            btnMostrar.setBounds(345, 150, 30, 25);
            btnMostrar.setBorderPainted(false);
            btnMostrar.setContentAreaFilled(false);
            btnMostrar.setFocusPainted(false);
            btnMostrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnMostrar.addActionListener(e -> {

                if (mostrarPassword) {

                    txtPass.setEchoChar(echoChar);
                    btnMostrar.setIcon(ojoCerrado);
                    mostrarPassword = false;

                } else {

                    txtPass.setEchoChar((char) 0);
                    btnMostrar.setIcon(ojoAbierto);
                    mostrarPassword = true;
                }

            });

            panel.add(btnMostrar);

        JCheckBox chkRecordar = new JCheckBox("Recordar sesión");
        chkRecordar.setBounds(170, 180, 150, 25);
        panel.add(chkRecordar);

        JButton btn = new JButton("Ingresar");
        btn.setBounds(160, 220, 120, 35);
        panel.add(btn);

        btn.addActionListener(e -> {

            String identificacion = txtId.getText().trim();
            String tipo = cbTipo.getSelectedItem().toString();
            String rol = cbRol.getSelectedItem().toString();
            String pass = String.valueOf(txtPass.getPassword());

            try {

                Connection con = Conexion.conectar();

                String sql = """
                        SELECT

                            u.id,
                            u.identificacion,
                            u.nombre,
                            u.rol,

                            a.idAprendiz,
                            a.numeroFicha AS fichaAprendiz,

                            c.area,

                            i.idInstructor,
                            i.especialidad,
                            i.numeroFicha AS fichaInstructor

                        FROM usuarios u

                        LEFT JOIN aprendiz a
                        ON u.identificacion = a.documento

                        LEFT JOIN coordinador c
                        ON u.id = c.id

                        LEFT JOIN instructor i
                        ON u.id = i.id

                        WHERE u.identificacion = ?
                        AND u.tipoDocumento = ?
                        AND u.rol = ?
                        AND u.password = ?
                        """;

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, identificacion);
                ps.setString(2, tipo);
                ps.setString(3, rol);
                ps.setString(4, pass);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){

                    if(chkRecordar.isSelected()){

                        prefs.put(
                                "usuario",
                                rs.getString("identificacion")
                        );

                    }else{

                        prefs.remove("usuario");

                    }

                    JOptionPane.showMessageDialog(
                            this,
                            "¡Bienvenido, " + rs.getString("nombre") + "!",
                            "Inicio de sesión",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    switch(rol){

                        case "Aprendiz":

                            new PanelAprendiz(

                                    rs.getInt("idAprendiz"),

                                    rs.getString("nombre"),

                                    rs.getString("fichaAprendiz")

                            ).setVisible(true);

                            break;

                        case "Instructor":

                            System.out.println("ID INSTRUCTOR = " + rs.getInt("idInstructor"));
                            System.out.println("NOMBRE INSTRUCTOR = " + rs.getString("nombre"));
                            System.out.println("ESPECIALIDAD = " + rs.getString("especialidad"));
                            System.out.println("FICHA INSTRUCTOR = " + rs.getString("fichaInstructor"));
                            new PanelInstructor(

                                    rs.getInt("idInstructor"),

                                    rs.getString("nombre"),

                                    rs.getString("especialidad"),

                                    rs.getString("fichaInstructor")

                            ).setVisible(true);

                            break;

                        case "Coordinador":

                        System.out.println("NOMBRE COORDINADOR = " + rs.getString("nombre"));
                        System.out.println("AREA COORDINADOR = " + rs.getString("area"));

                            new PanelCoordinador(

                                    rs.getString("nombre"),

                                    rs.getString("area")

                            ).setVisible(true);

                            break;

                    }

                    dispose();

                }else{

                    JOptionPane.showMessageDialog(
                            this,
                            "Credenciales incorrectas"
                    );

                }

                rs.close();
                ps.close();
                con.close();

            }catch(Exception ex){

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );

            }

        });
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
} 
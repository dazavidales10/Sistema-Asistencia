package Vista.coordinador.Dialogs;

import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

import Conexion.Conexion;

public class dialogFicha extends JDialog {

    private JTextField txtNumeroFicha;
    private JTextField txtPrograma;
    private JComboBox<String> cbJornada;

    private boolean modoEditar = false;
    private String numeroFichaOriginal;

    // ================= AGREGAR =================

    public dialogFicha(JFrame parent) {

        super(parent, "Agregar Ficha", true);

        construirFormulario();

        JButton btnGuardar = crearBotonGuardar();

        btnGuardar.addActionListener(e -> agregarFicha());

        add(btnGuardar);

        setVisible(true);
    }

        // Editar

    public dialogFicha(
            JFrame parent,
            String numeroFicha,
            String programa,
            String jornada) {

        super(parent, "Editar Ficha", true);

        this.modoEditar = true;
        this.numeroFichaOriginal = numeroFicha;

        construirFormulario();

        txtNumeroFicha.setText(numeroFicha);
        txtPrograma.setText(programa);
        cbJornada.setSelectedItem(jornada);

        JButton btnGuardar = crearBotonGuardar();

        btnGuardar.addActionListener(e -> editarFicha());

        add(btnGuardar);

        setVisible(true);
    }

    // ================= FORMULARIO =================

    private void construirFormulario() {

        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Gestión Ficha");

        titulo.setBounds(130, 20, 300, 40);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        add(titulo);

        // Número ficha

        JLabel lblNumeroFicha =
                new JLabel("Número Ficha");

        lblNumeroFicha.setBounds(50, 90, 150, 30);

        lblNumeroFicha.setFont(
                new Font("Arial", Font.BOLD, 18));

        add(lblNumeroFicha);

        txtNumeroFicha = new JTextField();

        txtNumeroFicha.setBounds(220, 90, 200, 35);

        add(txtNumeroFicha);

        // Programa

        JLabel lblPrograma = new JLabel("Programa");
        lblPrograma.setBounds(50, 150, 150, 30);
        lblPrograma.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblPrograma);

        txtPrograma = new JTextField();
        txtPrograma.setBounds(220, 150, 200, 35);

        add(txtPrograma);

        // Jornada

        JLabel lblJornada =
                new JLabel("Jornada");

        lblJornada.setBounds(50, 210, 150, 30);

        lblJornada.setFont(
                new Font("Arial", Font.BOLD, 18));

        add(lblJornada);

        String[] jornadas = {

                "Mañana",
                "Tarde",
                "Noche",
                "Mixta"
        };

        cbJornada = new JComboBox<>(jornadas);

        cbJornada.setBounds(220, 210, 200, 35);

        add(cbJornada);
    }

    // ================= BOTON =================

    private JButton crearBotonGuardar() {

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 290, 200, 50);
        btnGuardar.setBackground(new Color(0, 180, 0));
        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.setFont(
                new Font("Arial", Font.BOLD, 20));

        btnGuardar.setBorderPainted(false);

        return btnGuardar;
    }

    // ================= AGREGAR =================

    private void agregarFicha() {

        String numeroFicha = txtNumeroFicha.getText().trim();
        String programa = txtPrograma.getText().trim();
        String jornada = cbJornada.getSelectedItem().toString();

        if (numeroFicha.isEmpty()
                || programa.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos");

            return;
        }

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "INSERT INTO ficha " +
                    "(programa, numeroFicha, jornada) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, programa);
            ps.setString(2, numeroFicha);
            ps.setString(3, jornada);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Ficha agregada correctamente");

            ps.close();
            con.close();

            dispose();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al agregar ficha");
        }
    }

    // ================= EDITAR =================

    private void editarFicha() {

        String numeroFicha =
                txtNumeroFicha.getText().trim();

        String programa =
                txtPrograma.getText().trim();

        String jornada =
                cbJornada.getSelectedItem().toString();

        if (numeroFicha.isEmpty()
                || programa.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos");

            return;
        }

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "UPDATE ficha " +
                    "SET programa = ?, " +
                    "numeroFicha = ?, " +
                    "jornada = ? " +
                    "WHERE numeroFicha = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, programa);
            ps.setString(2, numeroFicha);
            ps.setString(3, jornada);
            ps.setString(4, numeroFichaOriginal);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Ficha editada correctamente");

            ps.close();
            con.close();

            dispose();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al editar ficha");
        }
    }
}
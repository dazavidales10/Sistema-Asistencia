package Vista.coordinador.Dialogs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import Conexion.Conexion;

public class dialogAprendiz extends JDialog {

    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JTextField txtPrograma;
    private JTextField txtTelefono;
    private JTextField txtNumeroFicha;

    private boolean modoEditar = false;

    // AGREGAR
    public dialogAprendiz(JFrame parent) {

        super(parent, "Agregar Aprendiz", true);

        inicializarComponentes();
    }

    // EDITAR
    public dialogAprendiz(
            JFrame parent,
            String documento,
            String nombre,
            String programa,
            String telefono,
            String numeroFicha) {

        super(parent, "Editar Aprendiz", true);

        modoEditar = true;

        inicializarComponentes();

        txtDocumento.setText(documento);
        txtNombre.setText(nombre);
        txtPrograma.setText(programa);
        txtTelefono.setText(telefono);
        txtNumeroFicha.setText(numeroFicha);

        txtDocumento.setEditable(false);
    }

    private void inicializarComponentes() {

        setSize(550, 500);
        setLocationRelativeTo(getParent());
        setLayout(null);

        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(40, 40, 120, 30);
        add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(180, 40, 300, 30);
        add(txtDocumento);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 90, 120, 30);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 90, 300, 30);
        add(txtNombre);

        JLabel lblPrograma = new JLabel("Programa:");
        lblPrograma.setBounds(40, 140, 120, 30);
        add(lblPrograma);

        txtPrograma = new JTextField();
        txtPrograma.setBounds(180, 140, 300, 30);
        add(txtPrograma);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(40, 190, 120, 30);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(180, 190, 300, 30);
        add(txtTelefono);

        JLabel lblFicha = new JLabel("Número Ficha:");
        lblFicha.setBounds(40, 240, 120, 30);
        add(lblFicha);

        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(180, 240, 300, 30);
        add(txtNumeroFicha);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 340, 120, 40);
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 340, 120, 40);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> guardarAprendiz());
    }

    private void guardarAprendiz() {

        try {

            Connection con = Conexion.conectar();

            if (!modoEditar) {

                // INSERTAR EN USUARIOS

                String sqlUsuario =
                        "INSERT INTO usuarios " +
                        "(identificacion, tipoDocumento, rol, password, nombre) " +
                        "VALUES (?, 'CC', 'Aprendiz', '123456', ?)";

                PreparedStatement psUsuario =
                        con.prepareStatement(
                                sqlUsuario,
                                PreparedStatement.RETURN_GENERATED_KEYS);

                psUsuario.setString(
                        1,
                        txtDocumento.getText());

                psUsuario.setString(
                        2,
                        txtNombre.getText());

                psUsuario.executeUpdate();

                ResultSet rs =
                        psUsuario.getGeneratedKeys();

                int idUsuario = 0;

                if (rs.next()) {
                    idUsuario = rs.getInt(1);
                }

                // INSERTAR EN APRENDIZ

                String sqlAprendiz =
                        "INSERT INTO aprendiz " +
                        "(documento, programa, telefono, id, numeroFicha) " +
                        "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement psAprendiz =
                        con.prepareStatement(sqlAprendiz);

                psAprendiz.setString(
                        1,
                        txtDocumento.getText());

                psAprendiz.setString(
                        2,
                        txtPrograma.getText());

                psAprendiz.setString(
                        3,
                        txtTelefono.getText());

                psAprendiz.setInt(
                        4,
                        idUsuario);

                psAprendiz.setString(
                        5,
                        txtNumeroFicha.getText());

                psAprendiz.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Aprendiz agregado correctamente");

            } else {

                // ACTUALIZAR USUARIO

                String sqlUsuario =
                        "UPDATE usuarios " +
                        "SET nombre=? " +
                        "WHERE identificacion=?";

                PreparedStatement psUsuario =
                        con.prepareStatement(sqlUsuario);

                psUsuario.setString(
                        1,
                        txtNombre.getText());

                psUsuario.setString(
                        2,
                        txtDocumento.getText());

                psUsuario.executeUpdate();

                // ACTUALIZAR APRENDIZ

                String sqlAprendiz =
                        "UPDATE aprendiz " +
                        "SET programa=?, telefono=?, numeroFicha=? " +
                        "WHERE documento=?";

                PreparedStatement psAprendiz =
                        con.prepareStatement(sqlAprendiz);

                psAprendiz.setString(
                        1,
                        txtPrograma.getText());

                psAprendiz.setString(
                        2,
                        txtTelefono.getText());

                psAprendiz.setString(
                        3,
                        txtNumeroFicha.getText());

                psAprendiz.setString(
                        4,
                        txtDocumento.getText());

                psAprendiz.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Aprendiz actualizado correctamente");
            }

            con.close();

            dispose();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }
 
    
}

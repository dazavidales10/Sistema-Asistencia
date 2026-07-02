package Vista.coordinador.Dialogs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import Conexion.Conexion;

public class DialogAprendiz extends JDialog {

    private JTextField txtDocumento;
    private JComboBox<String> cbTipoDocumento;
    private JTextField txtNombre;
    private JTextField txtPrograma;
    private JTextField txtTelefono;
    private JTextField txtNumeroFicha;

    private boolean modoEditar = false;

    // AGREGAR
    public DialogAprendiz(JFrame parent) {

        super(parent, "Agregar Aprendiz", true);

        inicializarComponentes();
    }

    // EDITAR
        public DialogAprendiz(
                JFrame parent,
                String documento,
                String nombre,
                String tipoDocumento,
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
        cbTipoDocumento.setSelectedItem(tipoDocumento);

        txtDocumento.setEditable(false);
    }

    private void inicializarComponentes() {

        setSize(550,580);
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
        lblPrograma.setBounds(40,190,120,30);
        add(lblPrograma);

        txtPrograma = new JTextField();
        txtPrograma.setBounds(180,190,300,30);
        add(txtPrograma);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(40,240,120,30);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(180,240,300,30);
        add(txtTelefono);

        JLabel lblFicha = new JLabel("Número Ficha:");
        lblFicha.setBounds(40,290,120,30);
        add(lblFicha);

        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(180,290,300,30);
        add(txtNumeroFicha);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120,390,120,40);
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280,390,120,40);
        add(btnCancelar);

        JLabel lblTipo = new JLabel("Tipo Documento:");
        lblTipo.setBounds(40, 140, 120, 30);
        add(lblTipo);

        cbTipoDocumento = new JComboBox<>(
                new String[]{
                        "CC",
                        "TI",
                        "CE",
                        "PPT"
                });

        cbTipoDocumento.setBounds(180, 140, 300, 30);
        add(cbTipoDocumento);

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> guardarAprendiz());
}

        private void guardarAprendiz() {

                try {

                        Connection con = Conexion.conectar();

                        if (!modoEditar) {

                        //USUARIO 

                        String sqlUsuario = """
                                INSERT INTO usuarios
                                (
                                identificacion,
                                nombre,
                                tipoDocumento,
                                rol,
                                password
                                )
                                VALUES
                                (
                                ?, ?, ?, 'Aprendiz', '123456'
                                )
                                """;

                        PreparedStatement psUsuario =
                                con.prepareStatement(sqlUsuario);

                        psUsuario.setString(1, txtDocumento.getText().trim());
                        psUsuario.setString(2, txtNombre.getText().trim());
                        psUsuario.setString(
                                3,
                                cbTipoDocumento.getSelectedItem().toString());

                        psUsuario.executeUpdate();

                        // APRENDIZ 

                        String sqlAprendiz = """
                                INSERT INTO aprendiz
                                (
                                documento,
                                programa,
                                telefono,
                                numeroFicha
                                )
                                VALUES
                                (
                                ?, ?, ?, ?
                                )
                                """;

                        PreparedStatement psAprendiz =
                                con.prepareStatement(sqlAprendiz);

                        psAprendiz.setString(
                                1,
                                txtDocumento.getText().trim());

                        psAprendiz.setString(
                                2,
                                txtPrograma.getText().trim());

                        psAprendiz.setString(
                                3,
                                txtTelefono.getText().trim());

                        psAprendiz.setString(
                                4,
                                txtNumeroFicha.getText().trim());

                        psAprendiz.executeUpdate();

                        psAprendiz.close();
                        psUsuario.close();

                        JOptionPane.showMessageDialog(
                                this,
                                "Aprendiz agregado correctamente");

                        } else {

                        //================ USUARIO =================

                        String sqlUsuario = """
                                UPDATE usuarios
                                SET
                                        nombre = ?,
                                        tipoDocumento = ?
                                WHERE identificacion = ?
                                """;

                                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);

                                psUsuario.setString(1, txtNombre.getText().trim());
                                psUsuario.setString(2, cbTipoDocumento.getSelectedItem().toString());
                                psUsuario.setString(3, txtDocumento.getText().trim());

                                psUsuario.executeUpdate();
                                psUsuario.close();

                        //================ APRENDIZ =================

                        String sqlAprendiz = """
                                UPDATE aprendiz
                                SET
                                programa = ?,
                                telefono = ?,
                                numeroFicha = ?
                                WHERE documento = ?
                                """;

                        PreparedStatement psAprendiz =
                                con.prepareStatement(sqlAprendiz);

                        psAprendiz.setString(
                                1,
                                txtPrograma.getText().trim());

                        psAprendiz.setString(
                                2,
                                txtTelefono.getText().trim());

                        psAprendiz.setString(
                                3,
                                txtNumeroFicha.getText().trim());

                        psAprendiz.setString(
                                4,
                                txtDocumento.getText().trim());

                        psAprendiz.executeUpdate();
                        psAprendiz.close();

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

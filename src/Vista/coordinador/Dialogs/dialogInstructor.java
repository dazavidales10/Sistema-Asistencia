package Vista.coordinador.Dialogs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import Modelo.Conexion;

public class dialogInstructor extends JDialog {

    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JComboBox<String> cbTipoDocumento;
    private JTextField txtnumeroFicha;
    private JTextField txtEspecialidad;
    private boolean modoEditar = false;

    // AGREGAR
    public dialogInstructor(JFrame parent) {

        super(parent, "Agregar Instructor", true);

        inicializarComponentes();
    }

    // EDITAR
    public dialogInstructor(
            JFrame parent,
            String identificacion,
            String nombre,
            String tipoDocumento,
            String numeroFicha,
            String especialidad) {

        super(parent, "Editar Instructor", true);

        modoEditar = true;

        inicializarComponentes();

        txtDocumento.setText(identificacion);
        txtNombre.setText(nombre);
        cbTipoDocumento.setSelectedItem(tipoDocumento);
        txtnumeroFicha.setText(numeroFicha);
        txtEspecialidad.setText(especialidad);

        txtDocumento.setEditable(false);
    }

    private void inicializarComponentes() {

        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setLayout(null);

        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(40, 40, 120, 30);
        add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(170, 40, 250, 30);
        add(txtDocumento);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 90, 120, 30);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(170, 90, 250, 30);
        add(txtNombre);

        JLabel lblTipo = new JLabel("Tipo Documento:");
        lblTipo.setBounds(40, 140, 120, 30);
        add(lblTipo);

        cbTipoDocumento = new JComboBox<>(
                new String[]{"CC", "TI", "CE", "PPT"});
        cbTipoDocumento.setBounds(170, 140, 250, 30);
        add(cbTipoDocumento);

        JLabel lblnumeroFicha = new JLabel("Numero Ficha:");
        lblnumeroFicha.setBounds(40, 190, 120, 30);
        add(lblnumeroFicha);

        txtnumeroFicha = new JTextField();
        txtnumeroFicha.setBounds(170, 190, 250, 30);
        add(txtnumeroFicha);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(40, 230, 120, 30);
        add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(170, 230, 250, 30);
        add(txtEspecialidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 280, 120, 40);
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 280, 120, 40);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> guardarInstructor());

        
    }

    private void guardarInstructor() {

        try {

            Connection con = Conexion.conectar();

            if (!modoEditar) {

                // Insertar usuario
                String sqlUsuario =
                        "INSERT INTO usuarios " +
                        "(identificacion,tipoDocumento,rol,password,nombre) " +
                        "VALUES(?,?, 'Instructor','123456', ?)";

                PreparedStatement psUsuario =
                        con.prepareStatement(
                                sqlUsuario,
                                PreparedStatement.RETURN_GENERATED_KEYS);

                psUsuario.setString(1, txtDocumento.getText());
                psUsuario.setString(2,
                        cbTipoDocumento.getSelectedItem().toString());
                psUsuario.setString(3, txtNombre.getText());

                psUsuario.executeUpdate();

                ResultSet rs = psUsuario.getGeneratedKeys();

                int idUsuario = 0;

                if (rs.next()) {
                    idUsuario = rs.getInt(1);
                }

                // Insertar instructor
                String sqlInstructor =
                        "INSERT INTO instructor(especialidad,id,numeroFicha) VALUES(?,?,?)";

                PreparedStatement psInstructor =
                        con.prepareStatement(sqlInstructor);

                psInstructor.setString(
                        1,
                        txtEspecialidad.getText());

                psInstructor.setInt(
                        2,
                        idUsuario);
                        
                psInstructor.setString(3, txtnumeroFicha.getText());

                psInstructor.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Instructor agregado correctamente");

            } else {

                // Actualizar usuario
                String sqlUsuario =
                        "UPDATE usuarios " +
                        "SET nombre=?, tipoDocumento=? " +
                        "WHERE identificacion=?";

                PreparedStatement psUsuario =
                        con.prepareStatement(sqlUsuario);

                psUsuario.setString(1, txtNombre.getText());

                psUsuario.setString(
                        2,
                        cbTipoDocumento.getSelectedItem().toString());

                psUsuario.setString(
                        3,
                        txtDocumento.getText());

                psUsuario.executeUpdate();

                // Actualizar instructor 
                String sqlInstructor =
                        "UPDATE instructor " +
                        "SET especialidad=? " +
                        "WHERE id = (" +
                        "SELECT id FROM usuarios " +
                        "WHERE identificacion=?)";

                PreparedStatement psInstructor =
                        con.prepareStatement(sqlInstructor);

                psInstructor.setString(
                        1,
                        txtEspecialidad.getText());

                psInstructor.setString(
                        2,
                        txtDocumento.getText());

                psInstructor.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Instructor actualizado correctamente");
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
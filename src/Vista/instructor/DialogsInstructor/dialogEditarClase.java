package Vista.instructor.DialogsInstructor;

import Conexion.Conexion;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dialogEditarClase extends JDialog {

    private int idClase;

    private JTextField txtTema;

    private JComboBox<String> cbEstado;

    private JDateChooser dcFecha;

    private JSpinner spHoraInicio;
    private JSpinner spHoraFin;

    private JButton btnGuardar;

    public dialogEditarClase(JFrame parent, int idClase) {

        super(parent, "Editar Clase", true);

        this.idClase = idClase;

        setSize(500,520);
        setLocationRelativeTo(parent);
        setLayout(null);

        inicializarComponentes();

        cargarDatos();

        bloquearEdicion();

    }



    private void inicializarComponentes() {

        JLabel titulo = new JLabel("Editar Clase");
        titulo.setFont(new Font("Arial",Font.BOLD,22));
        titulo.setBounds(150,20,250,35);
        add(titulo);

        JLabel lblTema = new JLabel("Tema");
        lblTema.setBounds(40,90,120,30);
        add(lblTema);

        txtTema = new JTextField();
        txtTema.setBounds(170,90,240,35);
        add(txtTema);

        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setBounds(40,145,120,30);
        add(lblFecha);

        dcFecha = new JDateChooser();
        dcFecha.setBounds(170,145,240,35);
        add(dcFecha);

        JLabel lblHoraInicio = new JLabel("Hora Inicio");
        lblHoraInicio.setBounds(40,200,120,30);
        add(lblHoraInicio);

        spHoraInicio = new JSpinner(
                new SpinnerDateModel());

        spHoraInicio.setEditor(
                new JSpinner.DateEditor(
                        spHoraInicio,
                        "HH:mm:ss"));

        spHoraInicio.setBounds(170,200,240,35);

        add(spHoraInicio);

        JLabel lblHoraFin = new JLabel("Hora Fin");
        lblHoraFin.setBounds(40,255,120,30);
        add(lblHoraFin);

        spHoraFin = new JSpinner(
                new SpinnerDateModel());

        spHoraFin.setEditor(
                new JSpinner.DateEditor(
                        spHoraFin,
                        "HH:mm:ss"));

        spHoraFin.setBounds(170,255,240,35);

        add(spHoraFin);

        JLabel lblEstado = new JLabel("Estado");
        lblEstado.setBounds(40,310,120,30);
        add(lblEstado);

        cbEstado = new JComboBox<>(new String[]{
                "PROGRAMADA",
                "EN_CURSO",
                "FINALIZADA",
                "CANCELADA"
        });

        cbEstado.setBounds(170,310,240,35);

        add(cbEstado);

        btnGuardar = new JButton("Actualizar");

        btnGuardar.setBounds(150,390,180,45);

        btnGuardar.setBackground(
                new Color(0,180,0));

        btnGuardar.setForeground(Color.WHITE);

        add(btnGuardar);

        btnGuardar.addActionListener(
                e -> actualizarClase());

    }

    private void cargarDatos() {

        String sql = """
                SELECT
                    tema,
                    fecha,
                    horaInicio,
                    horaFin,
                    estado
                FROM clase
                WHERE idClase = ?
                """;

        try (Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idClase);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                txtTema.setText(
                        rs.getString("tema")
                );

                dcFecha.setDate(
                        rs.getDate("fecha")
                );

                spHoraInicio.setValue(
                        rs.getTime("horaInicio")
                );

                spHoraFin.setValue(
                        rs.getTime("horaFin")
                );

                cbEstado.setSelectedItem(
                        rs.getString("estado")
                );

            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    private void actualizarClase() {

        String tema = txtTema.getText().trim();

        if (tema.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el tema."
            );

            return;
        }

        if (dcFecha.getDate() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione la fecha."
            );

            return;
        }

        SimpleDateFormat formatoFecha =
                new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat formatoHora =
                new SimpleDateFormat("HH:mm:ss");

        String fecha =
                formatoFecha.format(dcFecha.getDate());

        String horaInicio =
                formatoHora.format(
                        (Date) spHoraInicio.getValue()
                );

        String horaFin =
                formatoHora.format(
                        (Date) spHoraFin.getValue()
                );

        String estado =
                cbEstado.getSelectedItem().toString();

        String sql = """
                UPDATE clase
                SET
                    fecha=?,
                    horaInicio=?,
                    horaFin=?,
                    tema=?,
                    estado=?
                WHERE idClase=?
                """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setString(1, fecha);
            ps.setString(2, horaInicio);
            ps.setString(3, horaFin);
            ps.setString(4, tema);
            ps.setString(5, estado);
            ps.setInt(6, idClase);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Clase actualizada correctamente."
            );

            dispose();

        }catch(Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }


    private boolean tieneAsistencia() {

        String sql = """
                SELECT COUNT(*)
                FROM asistencia
                WHERE idClase = ?
                """;

        try (Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idClase);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return false;

    }

    private void bloquearEdicion() {

        if (!tieneAsistencia()) {
            return;
        }

        txtTema.setEditable(false);

        dcFecha.setEnabled(false);

        spHoraInicio.setEnabled(false);

        spHoraFin.setEnabled(false);

        JOptionPane.showMessageDialog(
                this,
                "Esta clase ya tiene asistencias registradas.\n\nSolo podrá modificar el estado."
        );

    }
}
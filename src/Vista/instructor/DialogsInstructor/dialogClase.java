package Vista.instructor.DialogsInstructor;

import Conexion.Conexion;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class dialogClase extends JDialog {

    private JTextField txtTema;

    private JDateChooser dcFecha;

    private JSpinner spHoraInicio;
    private JSpinner spHoraFin;

    private JComboBox<String> cbEstado;

    private int idInstructor;
    private String ficha;

    public dialogClase(
            JFrame parent,
            int idInstructor,
            String ficha) {

        super(parent, "Crear Clase", true);

        this.idInstructor = idInstructor;
        this.ficha = ficha;

        construirFormulario();

        JButton btnGuardar = crearBotonGuardar();

        btnGuardar.addActionListener(e -> guardarClase());

        add(btnGuardar);

        setVisible(true);
    }

    private void construirFormulario() {

        setSize(520,560);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Nueva Clase");

        titulo.setBounds(135,20,300,40);
        titulo.setFont(new Font("Arial",Font.BOLD,28));

        add(titulo);

        //================ TEMA =================

        JLabel lblTema = new JLabel("Tema");

        lblTema.setBounds(40,90,150,30);
        lblTema.setFont(new Font("Arial",Font.BOLD,18));

        add(lblTema);

        txtTema = new JTextField();

        txtTema.setBounds(210,90,240,35);

        add(txtTema);

        //================ FECHA =================

        JLabel lblFecha = new JLabel("Fecha");

        lblFecha.setBounds(40,150,150,30);
        lblFecha.setFont(new Font("Arial",Font.BOLD,18));

        add(lblFecha);

        dcFecha = new JDateChooser();

        dcFecha.setBounds(210,150,240,35);

        dcFecha.setDateFormatString("yyyy-MM-dd");

        add(dcFecha);

        //================ HORA INICIO =================

        JLabel lblHoraInicio = new JLabel("Hora Inicio");

        lblHoraInicio.setBounds(40,210,150,30);
        lblHoraInicio.setFont(new Font("Arial",Font.BOLD,18));

        add(lblHoraInicio);

        spHoraInicio = new JSpinner(new SpinnerDateModel());

        JSpinner.DateEditor editorInicio =
                new JSpinner.DateEditor(
                        spHoraInicio,
                        "HH:mm");

        spHoraInicio.setEditor(editorInicio);

        spHoraInicio.setBounds(210,210,240,35);

        add(spHoraInicio);

        //================ HORA FIN =================

        JLabel lblHoraFin = new JLabel("Hora Fin");

        lblHoraFin.setBounds(40,270,150,30);
        lblHoraFin.setFont(new Font("Arial",Font.BOLD,18));

        add(lblHoraFin);

        spHoraFin = new JSpinner(new SpinnerDateModel());

        JSpinner.DateEditor editorFin =
                new JSpinner.DateEditor(
                        spHoraFin,
                        "HH:mm");

        spHoraFin.setEditor(editorFin);

        spHoraFin.setBounds(210,270,240,35);

        add(spHoraFin);

        //================ ESTADO =================

        JLabel lblEstado = new JLabel("Estado");

        lblEstado.setBounds(40,330,150,30);
        lblEstado.setFont(new Font("Arial",Font.BOLD,18));

        add(lblEstado);

        cbEstado = new JComboBox<>(new String[]{

                "PROGRAMADA",
                "EN_CURSO",
                "FINALIZADA",
                "CANCELADA"

        });

        cbEstado.setBounds(210,330,240,35);

        add(cbEstado);

    }

    private JButton crearBotonGuardar() {

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.setBounds(150,430,200,50);

        btnGuardar.setBackground(new Color(0,180,0));
        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.setFont(new Font("Arial",Font.BOLD,20));

        btnGuardar.setBorderPainted(false);

        return btnGuardar;

    }

        private void guardarClase() {

                String tema = txtTema.getText().trim();

                if (tema.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el tema de la clase.");
                        return;
                }

                if (dcFecha.getDate() == null) {
                        JOptionPane.showMessageDialog(this, "Seleccione la fecha.");
                        return;
                }

                Date fechaSeleccionada = dcFecha.getDate();

                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

                String fecha = formatoFecha.format(fechaSeleccionada);
                String horaInicio = formatoHora.format((Date) spHoraInicio.getValue());
                String horaFin = formatoHora.format((Date) spHoraFin.getValue());

                String estado = cbEstado.getSelectedItem().toString();

                try {

                        Connection con = Conexion.conectar();

                        //================ INSERTAR CLASE =================

                        String sqlClase = """
                                INSERT INTO clase
                                (
                                idInstructor,
                                numeroFicha,
                                fecha,
                                horaInicio,
                                horaFin,
                                tema,
                                estado
                                )
                                VALUES (?,?,?,?,?,?,?)
                                """;

                        PreparedStatement psClase = con.prepareStatement(
                                sqlClase,
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );

                        psClase.setInt(1, idInstructor);
                        psClase.setString(2, ficha);
                        psClase.setString(3, fecha);
                        psClase.setString(4, horaInicio);
                        psClase.setString(5, horaFin);
                        psClase.setString(6, tema);
                        psClase.setString(7, estado);

                        psClase.executeUpdate();

                        //================ OBTENER ID DE LA CLASE =================

                        ResultSet rs = psClase.getGeneratedKeys();

                        int idClase = 0;

                        if (rs.next()) {
                        idClase = rs.getInt(1);
                        }

                        rs.close();
                        psClase.close();

                        //================ OBTENER APRENDICES DE LA FICHA =================

                        String sqlAprendices = """
                                SELECT idAprendiz
                                FROM aprendiz
                                WHERE numeroFicha = ?
                                """;

                        PreparedStatement psAprendices =
                                con.prepareStatement(sqlAprendices);

                        psAprendices.setString(1, ficha);
                        System.out.println("Ficha recibida: " + ficha);
                        ResultSet rsAprendices =
                                psAprendices.executeQuery();

                        //================ INSERTAR EN clase_aprendiz =================

                        String sqlRelacion = """
                                INSERT INTO clase_aprendiz
                                (idClase,idAprendiz)
                                VALUES (?,?)
                                """;

                        PreparedStatement psRelacion =
                                con.prepareStatement(sqlRelacion);

                        System.out.println("Ficha recibida: " + ficha);

                        while (rsAprendices.next()) {

                                int idAprendiz = rsAprendices.getInt("idAprendiz");
                                System.out.println("Encontrado aprendiz: " + idAprendiz);

                                psRelacion.setInt(1, idClase);
                                psRelacion.setInt(2, idAprendiz);

                                psRelacion.addBatch();
                        }

                        psRelacion.executeBatch();

                        rsAprendices.close();
                        psAprendices.close();
                        psRelacion.close();

                        con.close();

                        JOptionPane.showMessageDialog(
                                this,
                                "Clase creada correctamente."
                        );

                        dispose();

                } catch (Exception ex) {

                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );
                }
        }

}
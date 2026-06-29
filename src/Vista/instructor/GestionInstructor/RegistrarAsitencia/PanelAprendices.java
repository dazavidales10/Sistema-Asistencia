package Vista.instructor.GestionInstructor.RegistrarAsitencia;

import Conexion.Conexion;
import Vista.instructor.DialogsInstructor.dialogAsistencia;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PanelAprendices extends JFrame {

        private JTable tabla;
        private DefaultTableModel modelo;

        private JTextField txtBuscar;

        private JLabel lblDocumento;
        private JLabel lblNombre;
        private JLabel lblTelefono;
        private JLabel lblPrograma;

        private JButton btnAsistencia;
        private JButton btnEditar;

        private int idInstructor;
        private String nombreInstructor;
        private String especialidad;
        private String ficha;
        private int idClase;

        private int idAprendizSeleccionado = -1;
        private String documentoSeleccionado = "";

        public PanelAprendices(
        int idInstructor,
        String nombreInstructor,
        String especialidad,
        String ficha,
        int idClase) {

                System.out.println("Constructor PanelAprendices");
                System.out.println("Ficha recibida: " + ficha);
                System.out.println("IdClase recibido: " + idClase);

        this.idInstructor = idInstructor;
        this.nombreInstructor = nombreInstructor;
        this.especialidad = especialidad;
        this.ficha = ficha;
        this.idClase = idClase;



        setTitle("Aprendices");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //================ HEADER =================

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0,70));
        header.setBackground(new Color(0,180,0));

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelIzquierdo.setOpaque(false);

        JLabel arrow = new JLabel("←");
        arrow.setForeground(Color.WHITE);
        arrow.setFont(new Font("Arial", Font.BOLD, 40));
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        arrow.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        panelIzquierdo.add(arrow);
        header.add(panelIzquierdo, BorderLayout.WEST);

        arrow.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

                new PanelClases(
                        idInstructor,
                        nombreInstructor,
                        especialidad,
                        ficha
                ).setVisible(true);

                dispose();
        }
        });

        

        JLabel titulo = new JLabel(
                "FICHA " + ficha,
                SwingConstants.CENTER
        );

        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial",Font.BOLD,22));

        header.add(arrow,BorderLayout.WEST);
        header.add(titulo,BorderLayout.CENTER);

        add(header,BorderLayout.NORTH);

        // Centro del panel

        JPanel centro = new JPanel(new BorderLayout());

        JPanel buscador = new JPanel(new FlowLayout());

        txtBuscar = new JTextField(25);

        JButton btnBuscar = new JButton("Buscar");

        buscador.add(new JLabel("Aprendiz"));
        buscador.add(txtBuscar);
        buscador.add(btnBuscar);

        centro.add(buscador,BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{
                        "Documento",
                        "Nombre",
                        "Teléfono",
                        "Programa",
                        "Estado"
                },0);

        tabla = new JTable(modelo);

        tabla.setRowHeight(35);

        DefaultTableCellRenderer c =
                new DefaultTableCellRenderer();

        c.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<5;i++)
                tabla.getColumnModel().getColumn(i).setCellRenderer(c);

        centro.add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        add(centro,BorderLayout.CENTER);

        //================ PANEL DERECHO =================

        JPanel derecha = new JPanel();

        derecha.setPreferredSize(new Dimension(280,0));
        derecha.setLayout(new BoxLayout(
                derecha,
                BoxLayout.Y_AXIS));

        derecha.setBorder(
                BorderFactory.createTitledBorder(
                        "Aprendiz seleccionado"));

        derecha.add(Box.createVerticalStrut(20));

        derecha.add(new JLabel("Documento"));

        lblDocumento = new JLabel("-");
        lblDocumento.setFont(new Font("Arial",Font.BOLD,16));

        derecha.add(lblDocumento);

        derecha.add(Box.createVerticalStrut(20));

        derecha.add(new JLabel("Nombre"));

        lblNombre = new JLabel("-");

        derecha.add(lblNombre);

        derecha.add(Box.createVerticalStrut(20));

        derecha.add(new JLabel("Teléfono"));

        lblTelefono = new JLabel("-");

        derecha.add(lblTelefono);

        derecha.add(Box.createVerticalStrut(20));

        derecha.add(new JLabel("Programa"));

        lblPrograma = new JLabel("-");

        derecha.add(lblPrograma);

        derecha.add(Box.createVerticalStrut(35));

        btnAsistencia = new JButton("Registrar Asistencia");
        btnEditar = new JButton("Editar");


        Color verde = new Color(0, 153, 51);

        JButton[] botones = {
        btnBuscar,
        btnAsistencia,
        btnEditar
        };

        for (JButton boton : botones) {
        boton.setBackground(verde);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnAsistencia.setEnabled(false);
        btnEditar.setEnabled(false);

        derecha.add(btnAsistencia);
        derecha.add(Box.createVerticalStrut(15));
        derecha.add(btnEditar);
        derecha.add(Box.createVerticalStrut(15));
        add(derecha,BorderLayout.EAST);

        //================ EVENTOS =================
        btnBuscar.addActionListener(e -> buscar());

        tabla.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

        if (e.getValueIsAdjusting()) {
                return;
        }

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
                return;
        }

        documentoSeleccionado = modelo.getValueAt(fila, 0).toString();
        idAprendizSeleccionado = obtenerIdAprendiz(documentoSeleccionado);

        lblDocumento.setText(documentoSeleccionado);
        lblNombre.setText(modelo.getValueAt(fila, 1).toString());
        lblTelefono.setText(modelo.getValueAt(fila, 2).toString());
        lblPrograma.setText(modelo.getValueAt(fila, 3).toString());

        btnAsistencia.setEnabled(true);
        btnEditar.setEnabled(true);

        });

        cargarAprendices();

        // FUNCIONALIDAD DE LOS EVENTOS DE LOS BOTONES
        
        btnAsistencia.addActionListener(e -> {
                System.out.println("Documento: " + documentoSeleccionado);
                System.out.println("idAprendiz: " + idAprendizSeleccionado);

                dialogAsistencia dialog =
                        new dialogAsistencia(
                                this,
                                idClase,
                                idAprendizSeleccionado
                        );

                dialog.setVisible(true);

                cargarAprendices();

        });

                btnEditar.addActionListener(e -> editarAsistencia());

                
        }

        // METODOS DE LA CLASE


        private void cargarAprendices() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT
                        a.idAprendiz,
                        u.identificacion,
                        u.nombre,
                        a.telefono,
                        a.programa,
                        asi.estado
                        FROM clase_aprendiz ca

                        INNER JOIN aprendiz a
                        ON ca.idAprendiz=a.idAprendiz

                        INNER JOIN usuarios u
                        ON a.documento=u.identificacion

                        LEFT JOIN asistencia asi
                        ON asi.idClase=ca.idClase
                        AND asi.idAprendiz=ca.idAprendiz

                        WHERE ca.idClase=?

                        ORDER BY u.nombre
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1, idClase);

                        ResultSet rs = ps.executeQuery();

                        while(rs.next()){

                                String estado = rs.getString("estado");

                                if(estado==null){
                                        estado="SIN REGISTRAR";
                                }

                                modelo.addRow(new Object[]{

                                        rs.getString("identificacion"),
                                        rs.getString("nombre"),
                                        rs.getString("telefono"),
                                        rs.getString("programa"),
                                        estado

                                });

                        }

                        limpiarSeleccion();

                } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

                limpiarSeleccion();

        }
        
        private void buscar() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT
                                u.identificacion,
                                u.nombre,
                                a.telefono,
                                a.programa
                        FROM aprendiz a
                        INNER JOIN usuarios u
                                ON a.documento=u.identificacion
                        WHERE a.idInstructor=?
                        AND a.numeroFicha=?
                        AND (
                                u.nombre LIKE ?
                                OR u.identificacion LIKE ?
                                )
                        AND u.rol='Aprendiz'
                        ORDER BY u.nombre
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1, idInstructor);
                        ps.setString(2, ficha);

                        ps.setString(3, "%" + txtBuscar.getText().trim() + "%");
                        ps.setString(4, "%" + txtBuscar.getText().trim() + "%");

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                        modelo.addRow(new Object[]{
                                rs.getString("identificacion"),
                                rs.getString("nombre"),
                                rs.getString("telefono"),
                                rs.getString("programa")
                        });

                        }

                        limpiarSeleccion();

                } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

        }
        private void limpiarSeleccion() {

                documentoSeleccionado = "";

                lblDocumento.setText("-");
                lblNombre.setText("-");
                lblTelefono.setText("-");
                lblPrograma.setText("-");

                btnAsistencia.setEnabled(false);
                btnEditar.setEnabled(false);

        }

        private int obtenerIdAprendiz(String documento) {

                String sql = """
                        SELECT idAprendiz
                        FROM aprendiz
                        WHERE documento = ?
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setString(1, documento);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                        return rs.getInt("idAprendiz");
                        }

                } catch (Exception ex) {
                        ex.printStackTrace();
                }

                return -1;
        }

        private void editarAsistencia() {

                if(idAprendizSeleccionado==-1){
                        return;
                }

                dialogAsistencia dialog = new dialogAsistencia(
                        this,
                        idClase,
                        idAprendizSeleccionado
                );

                dialog.setVisible(true);

                cargarAprendices();
                }

}
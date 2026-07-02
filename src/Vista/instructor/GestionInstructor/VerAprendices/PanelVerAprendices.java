package Vista.instructor.GestionInstructor.VerAprendices;

import Conexion.Conexion;
import Vista.instructor.DialogsInstructor.dialogAsistencia;
import Vista.instructor.GestionInstructor.RegistrarAsitencia.PanelClases;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PanelVerAprendices extends JFrame {

        private JTable tabla;
        private DefaultTableModel modelo;

        private JTextField txtBuscar;

        private JLabel lblDocumento;
        private JLabel lblNombre;
        private JLabel lblTelefono;
        private JLabel lblPrograma;

        private JButton btnVerExcusa;
        private JButton btnEliminar;
        private JButton btnActualizar;

        private int idInstructor;
        private String nombreInstructor;
        private String especialidad;
        private String ficha;
        private int idClase;

        private int idAprendizSeleccionado = -1;
        private String documentoSeleccionado = "";

        public PanelVerAprendices(
            int idInstructor,
            String nombreInstructor,
            String especialidad,
            String ficha,
            int idClase)
        {

            System.out.println("Constructor PanelAprendices");
            System.out.println("Ficha recibida: " + ficha);
            System.out.println("IdClase recibido: " + idClase);

            this.idInstructor = idInstructor;
            this.nombreInstructor = nombreInstructor;
            this.especialidad = especialidad;
            this.ficha = ficha;
            this.idClase = idClase;



            setTitle("Ver Aprendices");
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

                    new PanelClasesVer(
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

            // PANEL DERECHO

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

            btnActualizar = new JButton("Actualizar Asistencia");
            btnVerExcusa = new JButton("Ver Excusa");
            btnEliminar = new JButton("Eliminar de la Clase");


            Color verde = new Color(0, 153, 51);

            JButton[] botones = {
            btnBuscar,
            btnActualizar,
            btnVerExcusa,
            btnEliminar
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

            btnActualizar.setEnabled(false);
            btnVerExcusa.setEnabled(false);
            btnEliminar.setEnabled(false);

            derecha.add(btnActualizar);
            derecha.add(Box.createVerticalStrut(15));
            derecha.add(btnVerExcusa);
            derecha.add(Box.createVerticalStrut(15));
            derecha.add(btnEliminar);
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

            btnActualizar.setEnabled(true);
            btnVerExcusa.setEnabled(true);
            btnEliminar.setEnabled(true);

            });

            cargarAprendices();

            // FUNCIONALIDAD DE LOS EVENTOS DE LOS BOTONES
        

                btnActualizar.addActionListener(e -> {

                dialogAsistencia dialog =
                        new dialogAsistencia(
                                this,
                                idClase,
                                idAprendizSeleccionado
                        );

                dialog.setVisible(true);

                cargarAprendices();

                });

                btnVerExcusa.addActionListener(e -> verExcusa());

                btnEliminar.addActionListener(e -> {

                        int opcion = JOptionPane.showConfirmDialog(
                                this,
                                "¿Desea eliminar este aprendiz de esta clase?",
                                "Confirmar",
                                JOptionPane.YES_NO_OPTION
                        );

                        if(opcion==JOptionPane.YES_OPTION){

                                eliminarAprendiz();

                        }

                });

        // METODOS DE LA CLASE
        }

        private void cargarAprendices() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT
                        a.idAprendiz,
                        u.identificacion,
                        u.nombre,
                        a.telefono,
                        a.programa,
                        COALESCE(asi.estado,'SIN REGISTRAR') AS estado
                        FROM clase_aprendiz ca

                        INNER JOIN aprendiz a
                        ON ca.idAprendiz = a.idAprendiz

                        INNER JOIN usuarios u
                        ON a.documento = u.identificacion

                        LEFT JOIN asistencia asi
                        ON asi.idClase = ca.idClase
                        AND asi.idAprendiz = ca.idAprendiz

                        WHERE ca.idClase = ?

                        ORDER BY u.nombre
                        """;

                try(Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1,idClase);

                        ResultSet rs = ps.executeQuery();

                        while(rs.next()){

                        modelo.addRow(new Object[]{

                                rs.getString("identificacion"),
                                rs.getString("nombre"),
                                rs.getString("telefono"),
                                rs.getString("programa"),
                                rs.getString("estado")

                        });

                        }

                        limpiarSeleccion();

                }catch(Exception ex){

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

                }
        
        private void buscar() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT
                        u.identificacion,
                        u.nombre,
                        a.telefono,
                        a.programa,
                        COALESCE(asi.estado,'SIN REGISTRAR') AS estado

                        FROM clase_aprendiz ca

                        INNER JOIN aprendiz a
                        ON ca.idAprendiz = a.idAprendiz

                        INNER JOIN usuarios u
                        ON a.documento = u.identificacion

                        LEFT JOIN asistencia asi
                        ON asi.idClase = ca.idClase
                        AND asi.idAprendiz = ca.idAprendiz

                        WHERE ca.idClase = ?
                        AND (
                        u.nombre LIKE ?
                        OR u.identificacion LIKE ?
                        )

                        ORDER BY u.nombre
                        """;

                try(Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1,idClase);

                        String texto = "%" + txtBuscar.getText().trim() + "%";

                        ps.setString(2,texto);
                        ps.setString(3,texto);

                        ResultSet rs = ps.executeQuery();

                        while(rs.next()){

                        modelo.addRow(new Object[]{

                                rs.getString("identificacion"),
                                rs.getString("nombre"),
                                rs.getString("telefono"),
                                rs.getString("programa"),
                                rs.getString("estado")

                        });

                        }

                        limpiarSeleccion();

                }catch(Exception ex){

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

                btnActualizar.setEnabled(false);
                btnVerExcusa.setEnabled(false);
                btnEliminar.setEnabled(false);

        }
        

        private void eliminarAprendiz(){

                String sql="""
                        DELETE FROM clase_aprendiz
                        WHERE idClase=?
                        AND idAprendiz=?
                        """;

                try(Connection cn=Conexion.conectar();
                        PreparedStatement ps=cn.prepareStatement(sql)){

                        ps.setInt(1,idClase);
                        ps.setInt(2,idAprendizSeleccionado);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(
                                this,
                                "Aprendiz retirado de la clase."
                        );

                        cargarAprendices();

                }catch(Exception ex){

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

        }

        private void verExcusa() {

                String sql = """
                        SELECT
                        fechaSolicitud,
                        motivo,
                        estado,
                        archivo
                        FROM excusa
                        WHERE idClase = ?
                        AND idAprendiz = ?
                        ORDER BY fechaSolicitud DESC
                        LIMIT 1
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1, idClase);
                        ps.setInt(2, idAprendizSeleccionado);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                        StringBuilder mensaje = new StringBuilder();

                        mensaje.append("Fecha de solicitud: ")
                                .append(rs.getDate("fechaSolicitud"))
                                .append("\n\n");

                        mensaje.append("Estado: ")
                                .append(rs.getString("estado"))
                                .append("\n\n");

                        mensaje.append("Motivo:\n")
                                .append(rs.getString("motivo"));

                        String archivo = rs.getString("archivo");

                        if (archivo != null && !archivo.isBlank()) {

                                mensaje.append("\n\nArchivo:\n")
                                .append(archivo);

                        }

                        JOptionPane.showMessageDialog(
                                this,
                                mensaje.toString(),
                                "Excusa del Aprendiz",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Este aprendiz no tiene una excusa registrada para esta clase."
                        );

                        }

                } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

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

}
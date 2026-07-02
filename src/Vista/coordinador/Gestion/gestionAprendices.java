package Vista.coordinador.Gestion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Dimension;
import Conexion.Conexion;
import Vista.coordinador.Dialogs.DialogAprendiz;
import Vista.coordinador.PanelCoordinador;
import Vista.ReportePDF;

public class gestionAprendices extends JFrame {

    private JTable tablaAprendices;
    private DefaultTableModel modeloTabla;

    private String nombreCoordinador;
    private String areaCoordinador;

    private JTextField txtNumeroFicha;
    private JPanel panel;

        private JLabel arrow;
        private JLabel titulo;
        private JLabel header;
        private JLabel lblBuscar;

        private JButton btnBuscar;
        private JButton btnAgregar;
        private JButton btnEditar;
        private JButton btnEliminar;
        private JButton btnExportar;

private JScrollPane scroll;

    public gestionAprendices(
            String nombreCoordinador,
            String areaCoordinador) {

        this.nombreCoordinador = nombreCoordinador;
        this.areaCoordinador = areaCoordinador;

        setTitle("Gestión Aprendices");

        setSize(1200,700);
        setMinimumSize(new Dimension(1000,650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        //================ HEADER =================

        arrow = new JLabel("←");
        arrow.setBounds(30,5,50,50);
        arrow.setFont(new Font("Arial",Font.BOLD,40));
        arrow.setForeground(Color.WHITE);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));

        arrow.addMouseListener(new java.awt.event.MouseAdapter(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){

                new PanelCoordinador(
                        nombreCoordinador,
                        areaCoordinador
                ).setVisible(true);

                dispose();

            }

        });

        panel.add(arrow);

        titulo = new JLabel("Gestión Aprendices");
        titulo.setBounds(100,0,500,70);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial",Font.BOLD,22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titulo);

        header = new JLabel();
        header.setOpaque(true);
        header.setBackground(new Color(0,180,0));
        header.setBounds(0,0,1920,70);

        panel.add(header);

        //================ BUSCADOR =================

        lblBuscar = new JLabel("Número de ficha");

        lblBuscar.setBounds(420,150,180,35);
        lblBuscar.setFont(new Font("Arial",Font.BOLD,20));

        panel.add(lblBuscar);

        txtNumeroFicha = new JTextField();

        txtNumeroFicha.setBounds(620,150,500,40);

        panel.add(txtNumeroFicha);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(1160,150,180,40);

        panel.add(btnBuscar);

        //================ BOTONES =================

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnExportar = new JButton("Exportar");

        btnAgregar.setBounds(260,260,240,70);
        btnEditar.setBounds(660,260,240,70);
        btnEliminar.setBounds(1060,260,240,70);
        btnExportar.setBounds(1460,260,240,70);

        JButton[] botones = {
                btnBuscar,
                btnAgregar,
                btnEditar,
                btnEliminar,
                btnExportar
        };

        for(JButton b : botones){

            b.setBackground(new Color(0,180,0));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial",Font.BOLD,20));
            b.setBorderPainted(false);

            panel.add(b);

        }

        //================ TABLA =================

        String[] columnas = {

                "Documento",
                "Nombre",
                "Tipo Documento",
                "Programa",
                "Teléfono",
                "Número Ficha",
                "Seleccionar"

        };

        modeloTabla = new DefaultTableModel(columnas,0){

                @Override
                        public Class<?> getColumnClass(int column){

                        if(column == 6)
                                return Boolean.class;

                        return String.class;

                }

                @Override
                        public boolean isCellEditable(int row,int column){

                        return column == 6;

                }

        };

        tablaAprendices = new JTable(modeloTabla);
        tablaAprendices.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaAprendices.setRowHeight(45);
        tablaAprendices.setFont(new Font("Arial",Font.PLAIN,18));

        DefaultTableCellRenderer centro =
                new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(
                SwingConstants.CENTER);

        for(int i=0;i<6;i++){

            tablaAprendices.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centro);

        }

        scroll =
                new JScrollPane(tablaAprendices);

        scroll.setBounds(35,350,1840,600);

        panel.add(scroll);

        //EVENTOS DE LOS BOTONES    !!IMPORTANTE NO MOVER     

        cargarAprendices();

        btnAgregar.addActionListener(e -> {

            DialogAprendiz dialog =
                    new DialogAprendiz(this);

            dialog.setVisible(true);

            cargarAprendices();

        });

        btnEditar.addActionListener(e -> editarAprendiz());

        btnEliminar.addActionListener(e -> eliminarAprendiz());

        btnBuscar.addActionListener(e -> buscarPorFicha());

        btnExportar.addActionListener(e -> {

                ReportePDF.generarReporte(

                        tablaAprendices,

                        "REPORTE DE APRENDICES",

                        "Reporte_Aprendices"

                );

        });

        //Responsive para el Desing

        resizeComponents();

        addComponentListener(new java.awt.event.ComponentAdapter() {

                @Override
                        public void componentResized(java.awt.event.ComponentEvent e) {

                        resizeComponents();

                }

                });

        setLocationRelativeTo(null);
        }

        private void cargarAprendices() {

                modeloTabla.setRowCount(0);

                String sql = """
                        SELECT
                                a.documento,
                                u.nombre,
                                u.tipoDocumento,
                                a.programa,
                                a.telefono,
                                a.numeroFicha
                        FROM aprendiz a
                        INNER JOIN usuarios u
                                ON a.documento = u.identificacion
                        ORDER BY u.nombre
                        """;

                try (Connection con = Conexion.conectar();
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery()) {

                        while (rs.next()) {

                        modeloTabla.addRow(new Object[]{

                                rs.getString("documento"),
                                rs.getString("nombre"),
                                rs.getString("tipoDocumento"),
                                rs.getString("programa"),
                                rs.getString("telefono"),
                                rs.getString("numeroFicha"),
                                false

                        });

                        }

                } catch (Exception ex) {

                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

        }

        private void buscarPorFicha() {

                modeloTabla.setRowCount(0);

                String sql = """
                        SELECT
                                a.documento,
                                u.nombre,
                                u.tipoDocumento,
                                a.programa,
                                a.telefono,
                                a.numeroFicha
                        FROM aprendiz a
                        INNER JOIN usuarios u
                                ON a.documento = u.identificacion
                        WHERE
                                a.numeroFicha LIKE ?
                                OR a.documento LIKE ?
                                OR u.nombre LIKE ?
                        ORDER BY u.nombre
                        """;

                try (Connection con = Conexion.conectar();
                        PreparedStatement ps = con.prepareStatement(sql)) {

                        String busqueda = "%" + txtNumeroFicha.getText().trim() + "%";

                        ps.setString(1, busqueda);
                        ps.setString(2, busqueda);
                        ps.setString(3, busqueda);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                        modeloTabla.addRow(new Object[]{

                                rs.getString("documento"),
                                rs.getString("nombre"),
                                rs.getString("tipoDocumento"),
                                rs.getString("programa"),
                                rs.getString("telefono"),
                                rs.getString("numeroFicha"),
                                false

                        });

                        }

                        rs.close();

                } catch (Exception ex) {

                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );

                }

        }
        private void editarAprendiz() {

                int fila = obtenerFilaSeleccionada();

                if (fila == -1) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Seleccione un aprendiz");

                        return;
                }

                String documento =
                        modeloTabla.getValueAt(fila, 0).toString();

                String nombre =
                        modeloTabla.getValueAt(fila, 1).toString();

                String tipoDocumento =
                        modeloTabla.getValueAt(fila, 2).toString();

                String programa =
                        modeloTabla.getValueAt(fila, 3).toString();

                String telefono =
                        modeloTabla.getValueAt(fila, 4).toString();

                String ficha =
                        modeloTabla.getValueAt(fila, 5).toString();

                DialogAprendiz dialog = new DialogAprendiz(
                        this,
                        documento,
                        nombre,
                        tipoDocumento,
                        programa,
                        telefono,
                        ficha
                );

                dialog.setVisible(true);

                cargarAprendices();
                }

    private void eliminarAprendiz() {

        int fila = obtenerFilaSeleccionada();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un aprendiz");

            return;
        }

        String documento =
                modeloTabla.getValueAt(fila, 0).toString();

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este aprendiz?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = Conexion.conectar()) {

            // Eliminar registros relacionados
            String sqlClaseAprendiz = """
                    DELETE ca
                    FROM clase_aprendiz ca
                    INNER JOIN aprendiz a
                        ON ca.idAprendiz = a.idAprendiz
                    WHERE a.documento = ?
                    """;

            PreparedStatement ps1 =
                    con.prepareStatement(sqlClaseAprendiz);

            ps1.setString(1, documento);
            ps1.executeUpdate();
            ps1.close();

            String sqlAsistencia = """
                    DELETE asi
                    FROM asistencia asi
                    INNER JOIN aprendiz a
                        ON asi.idAprendiz = a.idAprendiz
                    WHERE a.documento = ?
                    """;

            PreparedStatement ps2 =
                    con.prepareStatement(sqlAsistencia);

            ps2.setString(1, documento);
            ps2.executeUpdate();
            ps2.close();

            String sqlAprendiz = """
                    DELETE FROM aprendiz
                    WHERE documento = ?
                    """;

            PreparedStatement ps3 =
                    con.prepareStatement(sqlAprendiz);

            ps3.setString(1, documento);
            ps3.executeUpdate();
            ps3.close();

            String sqlUsuario = """
                    DELETE FROM usuarios
                    WHERE identificacion = ?
                    """;

            PreparedStatement ps4 =
                    con.prepareStatement(sqlUsuario);

            ps4.setString(1, documento);

            int filas = ps4.executeUpdate();

            ps4.close();

            if (filas > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Aprendiz eliminado correctamente."
                );

                cargarAprendices();
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void resizeComponents() {

    int w = getWidth();
    int h = getHeight();

    //================ HEADER =================

    header.setBounds(0, 0, w, 70);

    arrow.setBounds(20, 10, 50, 50);

    titulo.setBounds(
            (w - 300) / 2,
            0,
            300,
            70
    );

    //================ BUSCADOR =================

    int buscadorY = 120;

    lblBuscar.setBounds(
            w / 2 - 420,
            buscadorY,
            180,
            40
    );

    txtNumeroFicha.setBounds(
            w / 2 - 220,
            buscadorY,
            450,
            40
    );

    btnBuscar.setBounds(
            w / 2 + 260,
            buscadorY,
            160,
            40
    );

    //================ BOTONES =================

    int ancho = 220;
    int alto = 65;
    int espacio = 35;

    int total = (ancho * 4) + (espacio * 3);

    int inicio = (w - total) / 2;

    int yBotones = 210;

    btnAgregar.setBounds(
            inicio,
            yBotones,
            ancho,
            alto
    );

    btnEditar.setBounds(
            inicio + ancho + espacio,
            yBotones,
            ancho,
            alto
    );

    btnEliminar.setBounds(
            inicio + (ancho + espacio) * 2,
            yBotones,
            ancho,
            alto
    );

    btnExportar.setBounds(
            inicio + (ancho + espacio) * 3,
            yBotones,
            ancho,
            alto
    );

    //================ TABLA =================

    scroll.setBounds(
            30,
            320,
            w - 60,
            h - 400
    );

}
    

        private int obtenerFilaSeleccionada() {

                for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 6);

                if (seleccionado != null && seleccionado) {
                        return i;
                }
                }

                return -1;
        }


}

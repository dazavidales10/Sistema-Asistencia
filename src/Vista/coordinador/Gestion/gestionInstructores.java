package Vista.coordinador.Gestion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.awt.*;

import Vista.ReportePDF;
import Vista.coordinador.PanelCoordinador;
import Vista.coordinador.Dialogs.DialogInstructor;

public class gestionInstructores extends JFrame {

    private JTable tablaInstructores;
    private DefaultTableModel modeloTabla;
    private String nombreCoordinador;
    private String areaCoordinador;
    private JTextField txtNumeroFicha;
    private JPanel panel;

    private JLabel arrow;
    private JLabel lblCoordinador;
    private JLabel panelCoordinador;
    private JLabel lblBuscar;

    private JButton btnBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnExportar;

    private JScrollPane scrollTabla;

    public gestionInstructores(String nombreCoordinador,
        String areaCoordinador) {

        this.nombreCoordinador = nombreCoordinador;
        this.areaCoordinador = areaCoordinador;
        
        setTitle("Panel instructor");
        setSize(1200,700);
        setMinimumSize(new java.awt.Dimension(1000,650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Instructor");
        add(lbl);

        //Paneles  

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setContentPane(panel);

        arrow = new JLabel("←");
        arrow.setBounds(30, 5, 50, 50);
        arrow.setFont(new Font("Arial", Font.BOLD,40));
        
        arrow.setForeground(Color.white);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        panel.add(arrow);

        //Devolverte con el Arrow 

        arrow.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                new PanelCoordinador(
                        nombreCoordinador,
                        areaCoordinador
                ).setVisible(true);

                dispose();
            }
        });

        lblCoordinador = new JLabel("Gestion Instructores");
        lblCoordinador.setForeground(Color.white);
        lblCoordinador.setFont(new Font("Arial", Font.BOLD,20));
        lblCoordinador.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoordinador.setBounds(0,0,1920,70);
        panel.add(lblCoordinador);  

        panelCoordinador = new JLabel();
        panelCoordinador.setOpaque(true);
        panelCoordinador.setBackground(new Color(0, 180, 0));
        panelCoordinador.setForeground(Color.WHITE);
        panelCoordinador.setBounds(0, 0, 1920, 70);
        panel.add(panelCoordinador);


        lblBuscar = new JLabel("Buscar");
        lblBuscar.setForeground(Color.black);
        lblBuscar.setBounds(440, 155, 180, 25);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblBuscar);
        
        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(620, 150, 600, 40);
        panel.add(txtNumeroFicha);
        
        
        
        //Bonoes para el Coordinador
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(1260, 150, 180, 40);
        btnBuscar.setFont(new Font("Arial", Font.BOLD,20));
        btnBuscar.setBackground(new Color(0, 180, 0));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorderPainted(false);
        panel.add(btnBuscar);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(260, 260, 240, 70);
        btnAgregar.setFont(new Font("Arial", Font.BOLD,20));
        btnAgregar.setBackground(new Color(0, 180, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorderPainted(false);
        panel.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(660, 260, 240, 70);
        btnEditar.setFont(new Font("Arial", Font.BOLD,20));
        btnEditar.setBackground(new Color(0, 180, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBorderPainted(false);
        panel.add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(1060, 260, 240, 70);
        btnEliminar.setFont(new Font("Arial", Font.BOLD,20));
        btnEliminar.setBackground(new Color(0, 180, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBorderPainted(false);
        panel.add(btnEliminar);

        btnExportar = new JButton("Exportar");
        btnExportar.setBounds(1460, 260, 240, 70);
        btnExportar.setFont(new Font("Arial", Font.BOLD,20));
        btnExportar.setBackground(new Color(0, 180, 0));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setBorderPainted(false);
        panel.add(btnExportar);





        //Tabla buscador de los Instructores

        String[] columnas = {

            "Documento",
            "Nombre",
            "Tipo Documento",
            "Ficha",
            "Especialidad",
            "Seleccionar"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tablaInstructores = new JTable(modeloTabla);
        tablaInstructores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        tablaInstructores.setRowHeight(50);
        tablaInstructores.setFont(new Font("Arial", Font.BOLD, 18));

        tablaInstructores.setShowGrid(false);
        tablaInstructores.setIntercellSpacing(new Dimension(0, 0));

        tablaInstructores.setSelectionBackground(new Color(220, 255, 220));
        tablaInstructores.setSelectionForeground(Color.BLACK);

        
        tablaInstructores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        tablaInstructores.getTableHeader().setBackground(Color.WHITE);
        tablaInstructores.getTableHeader().setForeground(Color.BLACK);
        tablaInstructores.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < 5; i++) {
            tablaInstructores.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centro);
        }

        
        tablaInstructores.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaInstructores.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaInstructores.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablaInstructores.getColumnModel().getColumn(3).setPreferredWidth(180);
        tablaInstructores.getColumnModel().getColumn(4).setPreferredWidth(250);
        tablaInstructores.getColumnModel().getColumn(5).setPreferredWidth(250);

        scrollTabla = new JScrollPane(tablaInstructores);

        scrollTabla.setBounds(35, 350, 1840, 600);

        scrollTabla.setBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        panel.add(scrollTabla);

        cargarInstructores();


        //EVENTOS DE LOS BOTONES !! IMPORTANTE NO CAMBIAR

        btnAgregar.addActionListener(e -> {

            DialogInstructor dialog =
                    new DialogInstructor(this);

            dialog.setVisible(true);

            cargarInstructores();
        });

        btnExportar.addActionListener(e -> {

            ReportePDF.generarReporte(

                    tablaInstructores,

                    "REPORTE DE INSTRUCTORES",

                    "Reporte_Instructores"

            );

        });

        btnEliminar.addActionListener(e -> {

            int filaSeleccionada = -1;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 5);

                if (seleccionado != null && seleccionado) {
                    filaSeleccionada = i;
                    break;
                }
            }

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione un instructor");

                return;
            }

            String identificacion =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0).toString();

            try {

                Connection con = Conexion.conectar();

                String sqlBuscar =
                        "SELECT id FROM usuarios " +
                        "WHERE identificacion = ?";

                PreparedStatement psBuscar =
                        con.prepareStatement(sqlBuscar);

                psBuscar.setString(1, identificacion);

                ResultSet rs = psBuscar.executeQuery();

                if (rs.next()) {

                    int idUsuario = rs.getInt("id");

                    PreparedStatement psInstructor =
                            con.prepareStatement(
                                    "DELETE FROM instructor WHERE id = ?");

                    psInstructor.setInt(1, idUsuario);

                    psInstructor.executeUpdate();

                    PreparedStatement psUsuario =
                            con.prepareStatement(
                                    "DELETE FROM usuarios WHERE id = ?");

                    psUsuario.setInt(1, idUsuario);

                    int filas = psUsuario.executeUpdate();

                    if (filas > 0) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Instructor eliminado correctamente");

                        cargarInstructores();
                    }

                    psInstructor.close();
                    psUsuario.close();
                }

                rs.close();
                psBuscar.close();
                con.close();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage());
            }
        });

        btnEditar.addActionListener(e -> {

            int filaSeleccionada = -1;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 5);

                if (seleccionado != null && seleccionado) {

                    filaSeleccionada = i;
                    break;
                }
            }

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione un instructor");

                return;
            }

            String identificacion = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

            String nombre = modeloTabla.getValueAt(filaSeleccionada, 1).toString();

            String tipoDocumento = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
            
            String numeroFicha = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
            String especialidad = modeloTabla.getValueAt(filaSeleccionada, 4).toString();

            DialogInstructor dialog =
                    new DialogInstructor(
                            this,
                            identificacion,
                            nombre,
                            tipoDocumento,
                            numeroFicha,
                            especialidad);

            dialog.setVisible(true);

            cargarInstructores();
        });

        resizeComponents();

        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {

                resizeComponents();

            }

        });
        setLocationRelativeTo(null);
        
    }

    private void resizeComponents() {

        int w = getWidth();
        int h = getHeight();

        //================ HEADER =================

        panelCoordinador.setBounds(0, 0, w, 70);

        arrow.setBounds(20, 10, 50, 50);

        lblCoordinador.setBounds(
                (w - 350) / 2,
                0,
                350,
                70
        );

        //================ BUSCADOR =================

        int buscadorY = 120;

        lblBuscar.setBounds(
                w / 2 - 420,
                buscadorY,
                120,
                40
        );

        txtNumeroFicha.setBounds(
                w / 2 - 270,
                buscadorY,
                500,
                40
        );

        btnBuscar.setBounds(
                w / 2 + 260,
                buscadorY,
                170,
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

        btnBuscar.addActionListener(e -> buscarInstructor());

        //================ TABLA =================

        scrollTabla.setBounds(
                30,
                320,
                w - 60,
                h - 400
        );

    }

    private void cargarInstructores() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql = """
                    SELECT
                        u.identificacion,
                        u.nombre,
                        u.tipoDocumento,
                        i.numeroFicha,
                        i.especialidad
                    FROM instructor i
                    INNER JOIN usuarios u
                        ON u.id = i.id
                    WHERE u.rol = 'Instructor'
                    ORDER BY u.nombre
                    """;

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getString("identificacion") + " | " +
                    rs.getString("numeroFicha") + " | " +
                    rs.getString("especialidad")
                );

                modeloTabla.addRow(new Object[]{

                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getString("tipoDocumento"),
                        rs.getString("numeroFicha"),
                        rs.getString("especialidad"),
                        false
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());
        }
    }
    private void buscarInstructor() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql = """
                SELECT
                    u.identificacion,
                    u.nombre,
                    u.tipoDocumento,
                    i.numeroFicha,
                    i.especialidad
                FROM instructor i
                INNER JOIN usuarios u
                    ON u.id = i.id
                WHERE u.rol='Instructor'
                AND (
                    u.nombre LIKE ?
                    OR u.identificacion LIKE ?
                    OR i.numeroFicha LIKE ?
                )
                ORDER BY u.nombre
                """;

            PreparedStatement ps = con.prepareStatement(sql);

            String buscar = "%" + txtNumeroFicha.getText().trim() + "%";

            ps.setString(1, buscar);
            ps.setString(2, buscar);
            ps.setString(3, buscar);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{

                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getString("tipoDocumento"),
                        rs.getString("numeroFicha"),
                        rs.getString("especialidad"),
                        false

                });

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, ex.getMessage());

        }

    }

}
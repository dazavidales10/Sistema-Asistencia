package Vista.instructor.GestionInstructor.RegistrarAsitencia;

import Conexion.Conexion;
import Vista.instructor.panelInstructor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class seleccionarFicha extends JFrame {

        private JTable tablaFichas;
        private DefaultTableModel modelo;
        private JTextField txtBuscar;

        private int idInstructor;
        private String nombre;
        private String especialidad;

        // Ingreso a Panel Clases


        private JLabel lblFicha;
        private JLabel lblPrograma;
        private JButton btnIngresar;

        private String fichaSeleccionada = "";
        private String programaSeleccionado = "";

        public seleccionarFicha(
                int idInstructor,
                String nombre,
                String especialidad) {

                        

        this.idInstructor = idInstructor;
        this.nombre = nombre;
        this.especialidad = especialidad;

        setTitle("Mis Fichas");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        System.out.println("panelSleccionarFicha: " + idInstructor + ", " + nombre + ", " + especialidad);
        //================ HEADER ================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0,180,0));
        header.setPreferredSize(new Dimension(0,70));

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelIzquierdo.setOpaque(false);

        JLabel arrow = new JLabel("←");
        arrow.setForeground(Color.WHITE);
        arrow.setFont(new Font("Arial", Font.BOLD, 40));
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        arrow.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        panelIzquierdo.add(arrow);
        header.add(panelIzquierdo, BorderLayout.WEST);


        arrow.addMouseListener(new java.awt.event.MouseAdapter(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e){

                        new panelInstructor(
                                idInstructor,
                                nombre,
                                especialidad,
                                fichaSeleccionada
                        ).setVisible(true);
                        dispose();

                }

        });


        JLabel titulo = new JLabel(
                "MIS FICHAS",
                SwingConstants.CENTER
        );

        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial",Font.BOLD,22));

        header.add(arrow,BorderLayout.WEST);
        header.add(titulo,BorderLayout.CENTER);

        add(header,BorderLayout.NORTH);

        //================ PANEL CENTRAL ================

        JPanel centro = new JPanel(new BorderLayout());

        JPanel buscador = new JPanel(new FlowLayout());

        txtBuscar = new JTextField(25);

        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setBackground(new Color(0,180,0));
        btnBuscar.setForeground(Color.WHITE);

        buscador.add(new JLabel("Ficha"));
        buscador.add(txtBuscar);
        buscador.add(btnBuscar);

        centro.add(buscador,BorderLayout.NORTH);

        //================ TABLA ================

        modelo = new DefaultTableModel(
                new String[]{
                        "Ficha",
                        "Programa"
                },0);

        tablaFichas = new JTable(modelo);

        tablaFichas.setRowHeight(35);

        DefaultTableCellRenderer c =
                new DefaultTableCellRenderer();

        c.setHorizontalAlignment(SwingConstants.CENTER);

        tablaFichas.getColumnModel().getColumn(0).setCellRenderer(c);
        tablaFichas.getColumnModel().getColumn(1).setCellRenderer(c);

        centro.add(new JScrollPane(tablaFichas),
                BorderLayout.CENTER);

        add(centro,BorderLayout.CENTER);

        //================ PANEL DERECHO ================

        JPanel derecha = new JPanel();
        derecha.setPreferredSize(new Dimension(280,0));
        derecha.setLayout(new BoxLayout(derecha,
                BoxLayout.Y_AXIS));

        derecha.setBorder(
                BorderFactory.createTitledBorder(
                        "Ficha seleccionada"));

        derecha.add(Box.createVerticalStrut(20));

        JLabel t1 = new JLabel("Número");
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);

        derecha.add(t1);

        lblFicha = new JLabel("Seleccione una ficha");
        lblFicha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFicha.setFont(new Font("Arial",
                Font.BOLD,
                18));

        derecha.add(lblFicha);

        derecha.add(Box.createVerticalStrut(30));

        JLabel t2 = new JLabel("Programa");
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);

        derecha.add(t2);

        lblPrograma = new JLabel("-");
        lblPrograma.setAlignmentX(Component.CENTER_ALIGNMENT);

        derecha.add(lblPrograma);

        derecha.add(Box.createVerticalStrut(50));

        btnIngresar = new JButton("Ingresar");

        btnIngresar.setEnabled(false);

        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnIngresar.setBackground(new Color(0,180,0));
        btnIngresar.setForeground(Color.WHITE);

        derecha.add(btnIngresar);

        add(derecha,BorderLayout.EAST);

        //================ EVENTOS ================

        btnBuscar.addActionListener(e->buscar());

        tablaFichas.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                        @Override
                        public void valueChanged(ListSelectionEvent e) {

                        if(e.getValueIsAdjusting())
                                return;

                        int fila = tablaFichas.getSelectedRow();

                        if(fila==-1)
                                return;

                        fichaSeleccionada =
                                modelo.getValueAt(fila,0).toString();

                        programaSeleccionado =
                                modelo.getValueAt(fila,1).toString();

                        lblFicha.setText(fichaSeleccionada);
                        lblPrograma.setText(programaSeleccionado);

                        btnIngresar.setEnabled(true);

                        }

                });

        btnIngresar.addActionListener(e -> {

                new PanelClases(
                        idInstructor,
                        nombre,
                        especialidad,
                        fichaSeleccionada
                ).setVisible(true);

                dispose();

        });

        cargarFichas();
        }
        private void cargarFichas() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT DISTINCT
                                numeroFicha
                        FROM clase
                        WHERE idInstructor = ?
                        ORDER BY numeroFicha
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1, idInstructor);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                        modelo.addRow(new Object[]{
                                rs.getString("numeroFicha"),
                                obtenerPrograma(rs.getString("numeroFicha"))
                        });

                        }

                        fichaSeleccionada = "";
                        programaSeleccionado = "";
                        lblFicha.setText("Seleccione una ficha");
                        lblPrograma.setText("-");
                        btnIngresar.setEnabled(false);

                } catch (Exception ex) {

                        JOptionPane.showMessageDialog(this, ex.getMessage());

                }

                }

        // ================= BUSCAR =================
        private void buscar() {

                modelo.setRowCount(0);

                String sql = """
                        SELECT DISTINCT
                                numeroFicha
                        FROM clase
                        WHERE idInstructor = ?
                        AND numeroFicha LIKE ?
                        ORDER BY numeroFicha
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setInt(1, idInstructor);
                        ps.setString(2, "%" + txtBuscar.getText().trim() + "%");

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                        modelo.addRow(new Object[]{
                                rs.getString("numeroFicha"),
                                obtenerPrograma(rs.getString("numeroFicha"))
                        });

                        }

                        fichaSeleccionada = "";
                        programaSeleccionado = "";
                        lblFicha.setText("Seleccione una ficha");
                        lblPrograma.setText("-");
                        btnIngresar.setEnabled(false);

                } catch (Exception ex) {

                        JOptionPane.showMessageDialog(this, ex.getMessage());

                }

        }
        private String obtenerPrograma(String ficha) {

                String sql = """
                        SELECT programa
                        FROM aprendiz
                        WHERE numeroFicha = ?
                        LIMIT 1
                        """;

                try (Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement(sql)) {

                        ps.setString(1, ficha);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                        return rs.getString("programa");
                        }

                } catch (Exception ex) {
                        ex.printStackTrace();
                }

                return "";
        }
}
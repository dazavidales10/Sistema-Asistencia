package Vista.coordinador.Gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.awt.*;

import Vista.coordinador.PanelCoordinador;

public class gestionFichas extends JFrame {

    private JTable tablaFichas;
    private DefaultTableModel modeloTabla;

    private String nombreCoordinador;
    private String areaCoordinador;

    private JTextField txtBuscarFicha;

    public gestionFichas(
            String nombreCoordinador,
            String areaCoordinador) {

        this.nombreCoordinador = nombreCoordinador;
        this.areaCoordinador = areaCoordinador;

        setTitle("Gestión Fichas");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        setContentPane(panel);

        // ================= HEADER =================

        JLabel arrow = new JLabel("←");
        arrow.setBounds(30, 5, 50, 50);
        arrow.setFont(new Font("Arial", Font.BOLD, 40));
        arrow.setForeground(Color.WHITE);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(arrow);

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

        JLabel titulo = new JLabel("Gestión Fichas");

        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBounds(100, 0, 500, 70);

        panel.add(titulo);

        JLabel header = new JLabel();

        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setBounds(0, 0, 1920, 70);

        panel.add(header);

        // ================= BUSCADOR =================

        JLabel lblBuscar = new JLabel("Buscar");

        lblBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        lblBuscar.setBounds(450, 150, 150, 40);

        panel.add(lblBuscar);

        txtBuscarFicha = new JTextField();

        txtBuscarFicha.setBounds(620, 150, 600, 40);

        panel.add(txtBuscarFicha);

        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setBounds(1260, 150, 180, 40);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        btnBuscar.setBackground(new Color(0, 180, 0));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorderPainted(false);

        panel.add(btnBuscar);

        // ================= BOTONES =================

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(260, 260, 240, 70);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(660, 260, 240, 70);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(1060, 260, 240, 70);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(1460, 260, 240, 70);

        JButton[] botones = {
                btnAgregar,
                btnEditar,
                btnEliminar,
                btnExportar
        };

        for (JButton btn : botones) {

            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setBackground(new Color(0, 180, 0));
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(false);

            panel.add(btn);
        }

        // ================= TABLA =================

        String[] columnas = {

                "Número Ficha",
                "Programa",
                "Jornada",
                "Seleccionar"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public Class<?> getColumnClass(int column) {

                return column == 3
                        ? Boolean.class
                        : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 3;
            }
        };

        tablaFichas = new JTable(modeloTabla);

        tablaFichas.setRowHeight(50);
        tablaFichas.setFont(new Font("Arial", Font.BOLD, 18));

        tablaFichas.setShowGrid(false);
        tablaFichas.setIntercellSpacing(new Dimension(0, 0));

        tablaFichas.setSelectionBackground(new Color(220, 255, 220));
        tablaFichas.setSelectionForeground(Color.BLACK);

        tablaFichas.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 20));

        tablaFichas.getTableHeader().setBackground(Color.WHITE);

        tablaFichas.getTableHeader().setForeground(Color.BLACK);

        tablaFichas.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centro =
                new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < 3; i++) {

            tablaFichas.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centro);
        }

        tablaFichas.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(250);

        tablaFichas.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(500);

        tablaFichas.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(250);

        tablaFichas.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(150);

        JScrollPane scrollTabla =
                new JScrollPane(tablaFichas);

        scrollTabla.setBounds(35, 350, 1840, 600);

        scrollTabla.setBorder(
                BorderFactory.createLineBorder(
                        Color.LIGHT_GRAY,
                        1));

        panel.add(scrollTabla);

        // ================= CARGAR DATOS =================

        cargarFichas();

        // ================= AGREGAR =================

        btnAgregar.addActionListener(e -> {

            JTextField txtNumeroFicha = new JTextField();
            JTextField txtPrograma = new JTextField();

            String[] jornadas = {
                    "Mañana",
                    "Tarde",
                    "Noche",
                    "Mixta"
            };

            JComboBox<String> cbJornada =
                    new JComboBox<>(jornadas);

            JPanel formulario = new JPanel(
                    new GridLayout(0, 1));

            formulario.add(new JLabel("Número Ficha"));
            formulario.add(txtNumeroFicha);

            formulario.add(new JLabel("Programa"));
            formulario.add(txtPrograma);

            formulario.add(new JLabel("Jornada"));
            formulario.add(cbJornada);

            int resultado = JOptionPane.showConfirmDialog(
                    null,
                    formulario,
                    "Agregar Ficha",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (resultado == JOptionPane.OK_OPTION) {

                try {

                    Connection con = Conexion.conectar();

                    String sql =
                            "INSERT INTO ficha " +
                            "(programa, numeroFicha, jornada) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement ps =
                            con.prepareStatement(sql);

                    ps.setString(
                            1,
                            txtPrograma.getText().trim());

                    ps.setString(
                            2,
                            txtNumeroFicha.getText().trim());

                    ps.setString(
                            3,
                            cbJornada.getSelectedItem().toString());

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(
                            null,
                            "Ficha agregada correctamente");

                    cargarFichas();

                    ps.close();
                    con.close();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            null,
                            "Error al agregar ficha");
                }
            }
        });

        // ================= ELIMINAR =================

        btnEliminar.addActionListener(e -> {

            int filaSeleccionada = -1;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 3);

                if (seleccionado != null && seleccionado) {

                    filaSeleccionada = i;
                    break;
                }
            }

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione una ficha");

                return;
            }

            String numeroFicha =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0).toString();

            try {

                Connection con = Conexion.conectar();

                String sql =
                        "DELETE FROM ficha " +
                        "WHERE numeroFicha = ?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, numeroFicha);

                int filas = ps.executeUpdate();

                if (filas > 0) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Ficha eliminada correctamente");

                    cargarFichas();
                }

                ps.close();
                con.close();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Error al eliminar ficha");
            }
        });

        // ================= EDITAR =================

        btnEditar.addActionListener(e -> {

            int filaSeleccionada = -1;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 3);

                if (seleccionado != null && seleccionado) {

                    filaSeleccionada = i;
                    break;
                }
            }

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione una ficha");

                return;
            }

            String numeroFichaActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0).toString();

            String programaActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            1).toString();

            String jornadaActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            2).toString();

            JTextField txtNumeroFicha =
                    new JTextField(numeroFichaActual);

            JTextField txtPrograma =
                    new JTextField(programaActual);

            String[] jornadas = {
                    "Mañana",
                    "Tarde",
                    "Noche",
                    "Mixta"
            };

            JComboBox<String> cbJornada =
                    new JComboBox<>(jornadas);

            cbJornada.setSelectedItem(jornadaActual);

            JPanel formulario =
                    new JPanel(new GridLayout(0, 1));

            formulario.add(new JLabel("Número Ficha"));
            formulario.add(txtNumeroFicha);

            formulario.add(new JLabel("Programa"));
            formulario.add(txtPrograma);

            formulario.add(new JLabel("Jornada"));
            formulario.add(cbJornada);

            int resultado = JOptionPane.showConfirmDialog(
                    null,
                    formulario,
                    "Editar Ficha",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (resultado == JOptionPane.OK_OPTION) {

                try {

                    Connection con = Conexion.conectar();

                    String sql =
                            "UPDATE ficha " +
                            "SET programa = ?, " +
                            "numeroFicha = ?, " +
                            "jornada = ? " +
                            "WHERE numeroFicha = ?";

                    PreparedStatement ps =
                            con.prepareStatement(sql);

                    ps.setString(
                            1,
                            txtPrograma.getText().trim());

                    ps.setString(
                            2,
                            txtNumeroFicha.getText().trim());

                    ps.setString(
                            3,
                            cbJornada.getSelectedItem().toString());

                    ps.setString(4, numeroFichaActual);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(
                            null,
                            "Ficha editada correctamente");

                    cargarFichas();

                    ps.close();
                    con.close();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            null,
                            "Error al editar ficha");
                }
            }
        });

        // ================= BUSCAR =================

        btnBuscar.addActionListener(e -> {

            String numeroFicha =
                    txtBuscarFicha.getText().trim();

            buscarFicha(numeroFicha);
        });
    }

    // ================= CARGAR FICHAS =================

    private void cargarFichas() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "SELECT * FROM ficha";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{

                        rs.getString("numeroFicha"),
                        rs.getString("programa"),
                        rs.getString("jornada"),
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

    // ================= BUSCAR FICHA =================

    private void buscarFicha(String numeroFicha) {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "SELECT * FROM ficha " +
                    "WHERE numeroFicha LIKE ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "%" + numeroFicha + "%");

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{

                        rs.getString("numeroFicha"),
                        rs.getString("programa"),
                        rs.getString("jornada"),
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
}
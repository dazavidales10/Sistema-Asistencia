package Vista.coordinador.Gestion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Modelo.Conexion;
import Vista.coordinador.PanelCoordinador;
import Vista.coordinador.Dialogs.dialogAprendiz;

public class gestionAprendices extends JFrame {

    private JTable tablaAprendices;
    private DefaultTableModel modeloTabla;

    private String nombreCoordinador;
    private String areaCoordinador;

    private JTextField txtNumeroFicha;

    public gestionAprendices(
            String nombreCoordinador,
            String areaCoordinador) {

        this.nombreCoordinador = nombreCoordinador;
        this.areaCoordinador = areaCoordinador;

        setTitle("Gestión Aprendices");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        setContentPane(panel);

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
                        areaCoordinador)
                        .setVisible(true);

                dispose();
            }
        });

        JLabel titulo = new JLabel("Gestión Aprendices");
        titulo.setBounds(100, 0, 500, 70);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel header = new JLabel();
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setBounds(0, 0, 1920, 70);
        panel.add(header);

        JLabel lblBuscar = new JLabel("Número Ficha");
        lblBuscar.setBounds(420, 150, 180, 30);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblBuscar);

        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(620, 150, 500, 40);
        panel.add(txtNumeroFicha);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(1160, 150, 180, 40);
        panel.add(btnBuscar);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(260, 260, 240, 70);
        panel.add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(660, 260, 240, 70);
        panel.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(1060, 260, 240, 70);
        panel.add(btnEliminar);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(1460, 260, 240, 70);
        panel.add(btnExportar);

        JButton[] botones = {
                btnBuscar,
                btnAgregar,
                btnEditar,
                btnEliminar,
                btnExportar
        };

        for (JButton boton : botones) {

            boton.setBackground(new Color(0, 180, 0));
            boton.setForeground(Color.WHITE);
            boton.setFont(new Font("Arial", Font.BOLD, 20));
            boton.setBorderPainted(false);
        }

        String[] columnas = {
            "Documento",
            "Nombre",
            "Programa",
            "Teléfono",
            "Número Ficha",
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

        tablaAprendices = new JTable(modeloTabla);

        tablaAprendices.setRowHeight(50);
        tablaAprendices.setFont(new Font("Arial", Font.PLAIN, 18));

        DefaultTableCellRenderer centro =
                new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(
                SwingConstants.CENTER);

        for (int i = 0; i < 5; i++) {

            tablaAprendices.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centro);
        }

        JScrollPane scroll =
                new JScrollPane(tablaAprendices);

        scroll.setBounds(35, 350, 1840, 600);

        panel.add(scroll);

        cargarAprendices();

        // AGREGAR
        btnAgregar.addActionListener(e -> {

            dialogAprendiz dialog =
                    new dialogAprendiz(this);

            dialog.setVisible(true);

            cargarAprendices();
        });

        // EDITAR
        btnEditar.addActionListener(e -> editarAprendiz());

        // ELIMINAR
        btnEliminar.addActionListener(e -> eliminarAprendiz());

        // BUSCAR
        btnBuscar.addActionListener(e -> buscarPorFicha());
    }

    private void cargarAprendices() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "SELECT " +
                    "a.documento, " +
                    "u.nombre, " +
                    "a.programa, " +
                    "a.telefono, " +
                    "a.numeroFicha " +
                    "FROM aprendiz a " +
                    "INNER JOIN usuarios u ON a.id = u.id";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{

                        rs.getString("documento"),
                        rs.getString("nombre"),
                        rs.getString("programa"),
                        rs.getString("telefono"),
                        rs.getString("numeroFicha"),
                        false
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    private void buscarPorFicha() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "SELECT " +
                    "a.documento, " +
                    "u.nombre, " +
                    "a.programa, " +
                    "a.telefono, " +
                    "a.numeroFicha " +
                    "FROM aprendiz a " +
                    "INNER JOIN usuarios u ON a.id = u.id " +
                    "WHERE a.numeroFicha LIKE ? " +
                    "OR a.documento LIKE ? " +
                    "OR u.nombre LIKE ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            String busqueda =
                    "%" + txtNumeroFicha.getText().trim() + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{

                        rs.getString("documento"),
                        rs.getString("nombre"),
                        rs.getString("programa"),
                        rs.getString("telefono"),
                        rs.getString("numeroFicha"),
                        false
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();
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

        String documento = modeloTabla.getValueAt(fila, 0).toString();
        String nombre = modeloTabla.getValueAt(fila, 1).toString();
        String programa = modeloTabla.getValueAt(fila, 2).toString();
        String telefono = modeloTabla.getValueAt(fila, 3).toString();
        String ficha = modeloTabla.getValueAt(fila, 4).toString();

        dialogAprendiz dialog =
                new dialogAprendiz(
                        this,
                        documento,
                        nombre,
                        programa,
                        telefono,
                        ficha);

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
                modeloTabla.getValueAt(
                        fila,
                        0).toString();

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este aprendiz?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            Connection con = Conexion.conectar();

            // Buscar el id del usuario asociado
            String sqlBuscar =
                    "SELECT id FROM usuarios " +
                    "WHERE identificacion = ?";

            PreparedStatement psBuscar =
                    con.prepareStatement(sqlBuscar);

            psBuscar.setString(1, documento);

            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {

                int idUsuario = rs.getInt("id");

                // Eliminar aprendiz
                PreparedStatement psAprendiz =
                        con.prepareStatement(
                                "DELETE FROM aprendiz WHERE id = ?");

                psAprendiz.setInt(1, idUsuario);

                psAprendiz.executeUpdate();

                // Eliminar usuario
                PreparedStatement psUsuario =
                        con.prepareStatement(
                                "DELETE FROM usuarios WHERE id = ?");

                psUsuario.setInt(1, idUsuario);

                int filas = psUsuario.executeUpdate();

                if (filas > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Aprendiz eliminado correctamente");

                    cargarAprendices();
                }

                psAprendiz.close();
                psUsuario.close();
            }

            rs.close();
            psBuscar.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    private int obtenerFilaSeleccionada() {

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {

            Boolean seleccionado =
                    (Boolean) modeloTabla.getValueAt(i, 5);

            if (seleccionado != null && seleccionado) {
                return i;
            }
        }

        return -1;
    }
}
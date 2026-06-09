package Vista.coordinador;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

// import java.sql.ResultSet;
import java.awt.*;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;

// import org.w3c.dom.events.MouseEvent;
import Modelo.Conexion;

public class gestionInstructores extends JFrame {

    private JTable tablaInstructores;
    private DefaultTableModel modeloTabla;

    private JTextField txtNumeroFicha;
    private JTextField txtNombrePrograma;
    private JComboBox<String> cbJornadaS;

    public gestionInstructores() {
        
        setTitle("Panel instructor");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Instructor");
        add(lbl);

        //Paneles  

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setContentPane(panel);

        JLabel arrow = new JLabel("←");
        arrow.setBounds(30, 5, 50, 50);
        arrow.setFont(new Font("Arial", Font.BOLD,40));
        
        arrow.setForeground(Color.white);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        panel.add(arrow);

        JLabel lblCoordinador = new JLabel("Gestion Instructores");
        lblCoordinador.setForeground(Color.white);
        lblCoordinador.setFont(new Font("Arial", Font.BOLD,20));
        lblCoordinador.setBounds(100, 0, 1920, 70);
        panel.add(lblCoordinador);  

        JLabel panelCoordinador = new JLabel();
        panelCoordinador.setOpaque(true);
        panelCoordinador.setBackground(new Color(0, 180, 0));
        panelCoordinador.setForeground(Color.WHITE);
        panelCoordinador.setBounds(0, 0, 1920, 70);
        panel.add(panelCoordinador);


        JLabel lblBuscar = new JLabel("Buscar");
        lblBuscar.setForeground(Color.black);
        lblBuscar.setBounds(440, 155, 180, 25);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblBuscar);
        
        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(620, 150, 600, 40);
        panel.add(txtNumeroFicha);
        
        
        
        //Bonoes para el Coordinador
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(1260, 150, 180, 40);
        btnBuscar.setFont(new Font("Arial", Font.BOLD,20));
        btnBuscar.setBackground(new Color(0, 180, 0));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorderPainted(false);
        panel.add(btnBuscar);
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(260, 260, 240, 70);
        btnAgregar.setFont(new Font("Arial", Font.BOLD,20));
        btnAgregar.setBackground(new Color(0, 180, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorderPainted(false);
        panel.add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(660, 260, 240, 70);
        btnEditar.setFont(new Font("Arial", Font.BOLD,20));
        btnEditar.setBackground(new Color(0, 180, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBorderPainted(false);
        panel.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(1060, 260, 240, 70);
        btnEliminar.setFont(new Font("Arial", Font.BOLD,20));
        btnEliminar.setBackground(new Color(0, 180, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBorderPainted(false);
        panel.add(btnEliminar);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(1460, 260, 240, 70);
        btnExportar.setFont(new Font("Arial", Font.BOLD,20));
        btnExportar.setBackground(new Color(0, 180, 0));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setBorderPainted(false);
        panel.add(btnExportar);


        String[] columnas = {
            "Seleccionar",
            "Documento",
            "Nombre",
            "Tipo Documento",
            "Especialidad"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        tablaInstructores = new JTable(modeloTabla);

        // Apariencia
        tablaInstructores.setRowHeight(50);
        tablaInstructores.setFont(new Font("Arial", Font.BOLD, 18));

        tablaInstructores.setShowGrid(false);
        tablaInstructores.setIntercellSpacing(new Dimension(0, 0));

        tablaInstructores.setSelectionBackground(new Color(220, 255, 220));
        tablaInstructores.setSelectionForeground(Color.BLACK);

        // Encabezado
        tablaInstructores.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 20));

        tablaInstructores.getTableHeader().setBackground(Color.WHITE);
        tablaInstructores.getTableHeader().setForeground(Color.BLACK);
        tablaInstructores.getTableHeader().setReorderingAllowed(false);

        // Centrar texto
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 1; i < tablaInstructores.getColumnCount(); i++) {
            tablaInstructores.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centro);
        }

        // Tamaño columnas
        tablaInstructores.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaInstructores.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaInstructores.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablaInstructores.getColumnModel().getColumn(3).setPreferredWidth(180);
        tablaInstructores.getColumnModel().getColumn(4).setPreferredWidth(250);

        JScrollPane scrollTabla = new JScrollPane(tablaInstructores);

        scrollTabla.setBounds(35, 350, 1840, 600);

        scrollTabla.setBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        panel.add(scrollTabla);

        cargarInstructores();



        //Aciones de los Botones

        btnAgregar.addActionListener(e -> {

            String numeroFicha = txtNumeroFicha.getText();
            String programa = txtNombrePrograma.getText();
            String jornada = cbJornadaS.getSelectedItem().toString();

            try {

                Connection con = Conexion.conectar();

                String sql = "INSERT INTO usuarios (identificacion, tipoDocumento, rol, password, nombre) VALUES (?, ?, ?, 'Instructor', ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, programa);
                ps.setString(2, numeroFicha);
                ps.setString(3, jornada);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Ficha agregada correctamente");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al agregar ficha");
            }
            txtNumeroFicha.setText(" ");
            txtNombrePrograma.setText(" "); 
        });

        btnEliminar.addActionListener(e -> {

            int filaSeleccionada = -1;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                Boolean seleccionado =
                        (Boolean) modeloTabla.getValueAt(i, 0);

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

            String identificacion = modeloTabla.getValueAt(filaSeleccionada, 1).toString();

            try {

                Connection con = Conexion.conectar();

                String sql =
                        "DELETE FROM usuarios WHERE identificacion = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, identificacion);

                int filas = ps.executeUpdate();

                if (filas > 0) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Instructor eliminado");

                    cargarInstructores();
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            } 

        });

        btnEditar.addActionListener(e -> {
            
        });

        
    }

    private void cargarInstructores() {

        modeloTabla.setRowCount(0);

        try {

            Connection con = Conexion.conectar();

            String sql =
                "SELECT u.identificacion, " +
                "u.nombre, " +
                "u.tipoDocumento, " +
                "i.especialidad " +
                "FROM usuarios u " +
                "INNER JOIN instructor i ON u.id = i.id " +
                "WHERE u.rol = 'Instructor'";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{
                    false,
                    rs.getString("identificacion"),
                    rs.getString("nombre"),
                    rs.getString("tipoDocumento"),
                    rs.getString("especialidad")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar instructores");
        }
    }


    public static void main(String[] args) {
        new gestionInstructores().setVisible(true);
    }
}


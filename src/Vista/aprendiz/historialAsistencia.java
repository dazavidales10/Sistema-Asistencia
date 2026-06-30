package Vista.aprendiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class historialAsistencia extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public historialAsistencia(int idAprendiz) {

        setTitle("Historial de Asistencia");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // ==========================
        // BARRA VERDE
        // ==========================

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(35, 210, 20));
        header.setPreferredSize(new Dimension(600, 70));

         JLabel flecha = new JLabel("←");
        flecha.setForeground(Color.WHITE);
        flecha.setFont(new Font("Arial", Font.BOLD, 30));

        // Hace que aparezca la mano al pasar el mouse
        flecha.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Evento para volver al panel principal
        flecha.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                // Cierra la ventana actual
                dispose();
            }
        });

        JLabel titulo = new JLabel("Historial de Asistencia");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        header.add(flecha);
        header.add(titulo);

        add(header, BorderLayout.NORTH);

        // ==========================
        // TABLA
        // ==========================

        modelo = new DefaultTableModel();
        modelo.addColumn("Fecha");
        modelo.addColumn("Asistencia");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // ==========================
        // CARGAR DATOS
        // ==========================

        cargarAsistencias(idAprendiz);
    }

    private void cargarAsistencias(int idAprendiz) {

        try {

            Connection con = Conexion.conectar();

            String sql = """
                    SELECT fecha, estado
                    FROM asistencia
                    WHERE idAprendiz = ?
                    ORDER BY fecha DESC
                    """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAprendiz);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String estado = rs.getString("estado");

                if (estado.equals("Presente")) {
                    estado = "Asistió";
                } else if (estado.equals("Falta")) {
                    estado = "No asistió";
                }

                modelo.addRow(new Object[]{
                        rs.getDate("fecha"),
                        estado
                });
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error cargando asistencias"
            );
        }
    }
}
package Vista.aprendiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.FileOutputStream;

// Librerías iText
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Conexion.Conexion;

public class consultarAsistencia extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public consultarAsistencia(int idAprendiz) {

        setTitle("Asistencia");
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

        JLabel titulo = new JLabel("Asistencia");
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
        // BOTÓN REPORTE
        // ==========================

        JPanel panelBoton = new JPanel();

        JButton btnReporte = new JButton("Generar Reporte");
        btnReporte.setBackground(new Color(35, 210, 20));
        btnReporte.setForeground(Color.WHITE);
        btnReporte.setPreferredSize(new Dimension(180, 50));

        btnReporte.addActionListener(e -> generarReportePDF());

        panelBoton.add(btnReporte);

        add(panelBoton, BorderLayout.SOUTH);

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

    // ==========================
    // GENERAR REPORTE PDF
    // ==========================

    private void generarReportePDF() {

        try {

           String ruta = System.getProperty("user.home")
        + "\\Downloads\\ReporteAsistencia_"
        + System.currentTimeMillis()
        + ".pdf";

            Document documento = new Document();

            PdfWriter.getInstance(
                    documento,
                    new FileOutputStream(ruta)
            );

            documento.open();

            documento.add(new Paragraph("REPORTE DE ASISTENCIA"));
            documento.add(new Paragraph(" "));

            PdfPTable tablaPdf = new PdfPTable(2);

            // Encabezados
            tablaPdf.addCell("Fecha");
            tablaPdf.addCell("Asistencia");

            // Datos
            for (int i = 0; i < modelo.getRowCount(); i++) {

                tablaPdf.addCell(
                        modelo.getValueAt(i, 0).toString()
                );

                tablaPdf.addCell(
                        modelo.getValueAt(i, 1).toString()
                );
            }

            documento.add(tablaPdf);

            documento.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Reporte generado correctamente.\n\nGuardado en:\n" + ruta
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar el reporte:\n" + e.getMessage()
            );
        }
    }
}
package Vista;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportePDF {

    public static void generarReporte(
            JTable tabla,
            String titulo,
            String nombreArchivo) {

        try {

            // ===== RUTA DEL ARCHIVO =====
            String ruta = System.getProperty("user.home")
                    + "\\Downloads\\"
                    + nombreArchivo
                    + "_"
                    + System.currentTimeMillis()
                    + ".pdf";

            // ===== DOCUMENTO =====
            Document documento = new Document(
                    PageSize.A4.rotate(),
                    40, 40, 40, 40
            );

            PdfWriter.getInstance(
                    documento,
                    new FileOutputStream(ruta)
            );

            documento.open();

            // ===== FUENTES =====
            Font tituloFont = new Font(
                    Font.FontFamily.HELVETICA,
                    18,
                    Font.BOLD,
                    new BaseColor(0, 120, 0)
            );

            Font infoFont = new Font(
                    Font.FontFamily.HELVETICA,
                    11,
                    Font.NORMAL
            );

            Font encabezadoFont = new Font(
                    Font.FontFamily.HELVETICA,
                    11,
                    Font.BOLD,
                    BaseColor.WHITE
            );

            Font contenidoFont = new Font(
                    Font.FontFamily.HELVETICA,
                    10
            );

            // ===== LOGO =====
            Image logo = Image.getInstance(
                    "G:\\SENA\\2 trimestre\\Proyecto\\sistema asistencia\\src\\imagenes\\logo-sena-verde-complementario-png-2022.png"
            );
            logo.scaleToFit(80, 80);

            // ===== HEADER (TÍTULO + LOGO) =====
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{4, 1});

            PdfPCell celdaTitulo = new PdfPCell(
                    new Phrase(titulo, tituloFont)
            );
            celdaTitulo.setBorder(Rectangle.NO_BORDER);
            celdaTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaTitulo.setPaddingTop(20);

            PdfPCell celdaLogo = new PdfPCell(logo);
            celdaLogo.setBorder(Rectangle.NO_BORDER);
            celdaLogo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

            header.addCell(celdaTitulo);
            header.addCell(celdaLogo);

            documento.add(header);

            documento.add(new Paragraph(" "));

            // ===== INFO GENERAL =====
            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            documento.add(new Paragraph(
                    "Sistema de Gestión de Asistencia",
                    infoFont));

            documento.add(new Paragraph(
                    "Fecha de generación: " +
                            LocalDateTime.now().format(formato),
                    infoFont));

            documento.add(new Paragraph(
                    "Total de registros: " + tabla.getRowCount(),
                    infoFont));

            documento.add(new Paragraph(" "));

            // ===== SEPARADOR =====
            LineSeparator linea = new LineSeparator();
            linea.setLineColor(new BaseColor(0, 180, 0));
            documento.add(linea);

            documento.add(new Paragraph(" "));

            // ===== TABLA =====
            int columnas = tabla.getColumnCount();

            PdfPTable tablaPdf = new PdfPTable(columnas);
            tablaPdf.setWidthPercentage(100);

            // ENCABEZADOS
            for (int i = 0; i < columnas; i++) {

                PdfPCell celda = new PdfPCell(
                        new Phrase(tabla.getColumnName(i), encabezadoFont)
                );

                celda.setBackgroundColor(new BaseColor(0, 180, 0));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setPadding(8);

                tablaPdf.addCell(celda);
            }

            // CONTENIDO
            BaseColor gris = new BaseColor(240, 240, 240);

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {

                for (int col = 0; col < columnas; col++) {

                    Object valor = tabla.getValueAt(fila, col);

                    PdfPCell celda = new PdfPCell(
                            new Phrase(
                                    valor == null ? "" : valor.toString(),
                                    contenidoFont
                            )
                    );

                    celda.setPadding(6);
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);

                    if (fila % 2 == 0) {
                        celda.setBackgroundColor(gris);
                    }

                    tablaPdf.addCell(celda);
                }
            }

            documento.add(tablaPdf);

            documento.add(new Paragraph(" "));

            // ===== PIE DE PÁGINA =====
            Paragraph pie = new Paragraph(
                    "Documento generado automáticamente por el Sistema de Gestión de Asistencia.",
                    infoFont
            );

            pie.setAlignment(Element.ALIGN_RIGHT);
            documento.add(pie);

            documento.close();

            // ===== MENSAJE =====
            JOptionPane.showMessageDialog(
                    null,
                    "Reporte generado correctamente.\n\nGuardado en:\n" + ruta
            );

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error generando PDF:\n" + e.getMessage()
            );
        }
    }
}
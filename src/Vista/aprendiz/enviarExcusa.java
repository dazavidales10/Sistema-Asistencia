package Vista.aprendiz;

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class enviarExcusa extends JFrame {

    // COMPONENTES
    private JTextField txtAprendiz;
    private JTextField txtInstructor;
    private JTextField txtFicha;
    private JTextArea txtMotivo;
    private JTextField txtArchivo;

    private JButton btnAdjuntar;
    private JButton btnEnviar;

    private File archivoSeleccionado;

    // VARIABLES
    private int idUsuario;
    private int idAprendiz;
    private int idInstructor;

    public enviarExcusa(int idUsuario) {

        this.idUsuario = idUsuario;

        setTitle("Enviar Excusa");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        construirVentana();
        cargarDatos();

        btnAdjuntar.addActionListener(e -> seleccionarPDF());

        btnEnviar.addActionListener(e -> guardarExcusa());
    }

    private void construirVentana() {

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setBackground(new Color(0, 180, 0));
        superior.setPreferredSize(new Dimension(700, 70));

        JLabel flecha = new JLabel("←");
        flecha.setFont(new Font("Arial", Font.BOLD, 28));
        flecha.setForeground(Color.WHITE);
        flecha.setCursor(new Cursor(Cursor.HAND_CURSOR));

        flecha.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
            }
        });

        JLabel titulo = new JLabel("Enviar Excusa");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        superior.add(flecha);
        superior.add(titulo);

        add(superior, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        Font fuente = new Font("Arial", Font.PLAIN, 16);

        gbc.gridx = 0;
        gbc.gridy = 0;

        centro.add(new JLabel("Aprendiz"), gbc);

        gbc.gridx = 1;
        txtAprendiz = new JTextField(25);
        txtAprendiz.setEditable(false);
        txtAprendiz.setFont(fuente);
        centro.add(txtAprendiz, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        centro.add(new JLabel("Instructor"), gbc);

        gbc.gridx = 1;
        txtInstructor = new JTextField(25);
        txtInstructor.setEditable(false);
        txtInstructor.setFont(fuente);
        centro.add(txtInstructor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        centro.add(new JLabel("Ficha"), gbc);

        gbc.gridx = 1;
        txtFicha = new JTextField(25);
        txtFicha.setEditable(false);
        txtFicha.setFont(fuente);
        centro.add(txtFicha, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        centro.add(new JLabel("Motivo"), gbc);

        gbc.gridx = 1;
        txtMotivo = new JTextArea(5, 25);
        JScrollPane scroll = new JScrollPane(txtMotivo);
        centro.add(scroll, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        centro.add(new JLabel("Archivo"), gbc);

        gbc.gridx = 1;
        txtArchivo = new JTextField(20);
        txtArchivo.setEditable(false);
        centro.add(txtArchivo, gbc);

        gbc.gridx = 2;

        btnAdjuntar = new JButton("Adjuntar PDF");
        btnAdjuntar.setBackground(new Color(0, 180, 0));
        btnAdjuntar.setForeground(Color.WHITE);

        centro.add(btnAdjuntar, gbc);

        add(centro, BorderLayout.CENTER);

        JPanel inferior = new JPanel();
        inferior.setBackground(Color.WHITE);

        btnEnviar = new JButton("Enviar Excusa");
        btnEnviar.setPreferredSize(new Dimension(220, 45));
        btnEnviar.setBackground(new Color(0, 180, 0));
        btnEnviar.setForeground(Color.WHITE);

        inferior.add(btnEnviar);

        add(inferior, BorderLayout.SOUTH);

    }

    private void cargarDatos() {

        try {

            Connection con = Conexion.conectar();

            String sql = """
                SELECT
                    u.nombre,
                    a.numeroFicha,
                    a.idAprendiz,
                    i.idInstructor,
                    ui.nombre AS instructor
                FROM usuarios u
                INNER JOIN aprendiz a ON u.id = a.id
                INNER JOIN instructor i ON i.numeroFicha = a.numeroFicha
                INNER JOIN usuarios ui ON ui.id = i.id
                WHERE u.id = ?
                """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                txtAprendiz.setText(rs.getString("nombre"));
                txtInstructor.setText(rs.getString("instructor"));
                txtFicha.setText(rs.getString("numeroFicha"));

                idAprendiz = rs.getInt("idAprendiz");
                idInstructor = rs.getInt("idInstructor");

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar la información."
            );
        }

    }
    private void seleccionarPDF() {

    JFileChooser chooser = new JFileChooser();

    chooser.setDialogTitle("Seleccione un archivo PDF");

    chooser.setFileFilter(
            new FileNameExtensionFilter("Archivos PDF", "pdf")
    );

    int opcion = chooser.showOpenDialog(this);

    if (opcion == JFileChooser.APPROVE_OPTION) {

        archivoSeleccionado = chooser.getSelectedFile();

        txtArchivo.setText(
                archivoSeleccionado.getName()
        );

    }

}
private void guardarExcusa() {

    try {

        if (archivoSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un archivo PDF."
            );

            return;
        }

        if (txtMotivo.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe escribir el motivo."
            );

            return;
        }

        File carpeta = new File("Excusas");

        if (!carpeta.exists()) {

            carpeta.mkdir();

        }

        String nombreArchivo =
                System.currentTimeMillis() + "_"
                        + archivoSeleccionado.getName();

        File destino = new File(carpeta, nombreArchivo);

        Files.copy(
                archivoSeleccionado.toPath(),
                destino.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

            Connection con = Conexion.conectar();

            String sql = """
                INSERT INTO excusa
                (
                    fechaEnvio,
                    motivo,
                    estado,
                    archivo,
                    idAprendiz,
                    idInstructor
                )
                VALUES
                (
                    CURDATE(),
                    ?,
                    'Pendiente',
                    ?,
                    ?,
                    ?
                )
                """;

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtMotivo.getText());

            ps.setString(2, destino.getAbsolutePath());

            ps.setInt(3, idAprendiz);

            ps.setInt(4, idInstructor);

            ps.executeUpdate();

            ps.close();

            con.close();
                    

        JOptionPane.showMessageDialog(
                this,
                "Excusa enviada correctamente."
        );

        dispose();

    } catch(Exception e){

        e.printStackTrace();

        JOptionPane.showMessageDialog(
            this,
            "Error:\n" + e.getMessage()
        );

    }

}

}
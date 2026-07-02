package Vista.aprendiz;

import javax.swing.*;

import Conexion.Conexion;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PanelAprendiz extends JFrame {

    private JLabel lblNombre;
    private JLabel lblFicha;
    private JLabel lblPrograma;
    private JLabel lblAsistencia;
    private JLabel lblFaltas;
    private JLabel lblTardanzas;
    private int idUsuario;
    private int idAprendiz = -1;

    public PanelAprendiz(int idUsuario,
                         String nombre,
                         String ficha) {
        this.idUsuario = idUsuario;

        setTitle("Panel Aprendiz");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= PANEL SUPERIOR =================

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.setBackground(Color.WHITE);

        JLabel header = new JLabel("Panel Aprendiz", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 180, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setPreferredSize(new Dimension(0, 70));

        JPanel panelCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelCerrar.setBackground(Color.WHITE);

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setPreferredSize(new Dimension(180, 50));
        btnCerrar.setBackground(new Color(0, 180, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);

        panelCerrar.add(btnCerrar);

        contenedorSuperior.add(header, BorderLayout.NORTH);
        contenedorSuperior.add(panelCerrar, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);

        // ================= PANEL CENTRAL =================

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font fuenteInfo = new Font("Arial", Font.PLAIN, 22);

        gbc.gridx = 0;
        gbc.gridy = 0;

        lblNombre = new JLabel("Bienvenido: " + nombre);
        lblNombre.setFont(fuenteInfo);
        centerPanel.add(lblNombre, gbc);

        gbc.gridy++;

        lblFicha = new JLabel("Ficha: " + ficha);
        lblFicha.setFont(fuenteInfo);
        centerPanel.add(lblFicha, gbc);

        gbc.gridy++;

        lblPrograma = new JLabel("Programa:");
        lblPrograma.setFont(fuenteInfo);
        centerPanel.add(lblPrograma, gbc);

        gbc.gridy++;

        lblAsistencia = new JLabel("Asistencias: 0");
        lblAsistencia.setFont(fuenteInfo);
        centerPanel.add(lblAsistencia, gbc);

        gbc.gridy++;

        lblFaltas = new JLabel("Faltas: 0");
        lblFaltas.setFont(fuenteInfo);
        centerPanel.add(lblFaltas, gbc);

        gbc.gridy++;

        lblTardanzas = new JLabel("Tardanzas: 0");
        lblTardanzas.setFont(fuenteInfo);
        centerPanel.add(lblTardanzas, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // ================= BOTONES INFERIORES =================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        20
                )
        );

        bottomPanel.setBackground(Color.WHITE);

        JButton btnHistorial = new JButton("Ver Historial");
        JButton btnExcusa = new JButton("Enviar Excusa");
        JButton btnConsultar = new JButton("Consultar Asistencia");

// Evento del botón Enviar Excusa
        btnExcusa.addActionListener(e -> {
        new EnviarExcusa(idUsuario).setVisible(true);
        });

        JButton[] botones = {
                btnHistorial,
                btnExcusa,
                btnConsultar
        };

        for (JButton b : botones) {

            b.setPreferredSize(new Dimension(250, 80));
            b.setBackground(new Color(0, 180, 0));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setFocusPainted(false);

            bottomPanel.add(b);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // ================= BOTÓN CONSULTAR =================

        btnConsultar.addActionListener(e -> {

        System.out.println("Botón Consultar");
        System.out.println("idAprendiz = " + idAprendiz);

        try{

                ConsultarAsistencia c = new ConsultarAsistencia(idAprendiz);

                System.out.println("Objeto creado");

                c.setVisible(true);

                System.out.println("Ventana abierta");

        }catch(Exception ex){

                ex.printStackTrace();

        }

        });
                
        btnHistorial.addActionListener(e -> {

        System.out.println("Botón Historial");
        System.out.println("idAprendiz = " + idAprendiz);

        try {

                HistorialAsistencia h = new HistorialAsistencia(idAprendiz);

                System.out.println("Objeto creado");

                h.setVisible(true);

                System.out.println("Ventana abierta");

        } catch (Exception ex) {

                ex.printStackTrace();

        }

        });

        // ================= CARGAR DATOS =================

        cargarDatosAprendiz(idUsuario);

        // ================= CERRAR SESIÓN =================

        btnCerrar.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Sesión cerrada");

            dispose();

            new Vista.Login().setVisible(true);

        });
    }

    // ================= CONSULTAR APRENDIZ =================

    private void cargarDatosAprendiz(int idUsuario) {

        try {

            Connection con = Conexion.conectar();

            String sql = """
                    SELECT
                        a.idAprendiz,
                        a.programa,
                        a.numeroFicha
                        FROM usuarios u
                        INNER JOIN aprendiz a
                        ON u.identificacion = a.documento
                        WHERE u.id = ?
                        """;

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idUsuario);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                lblPrograma.setText(
                        "Programa: "
                                + rs.getString("programa")
                );

                lblFicha.setText(
                        "Ficha: "
                                + rs.getString("numeroFicha")
                );

                idAprendiz = rs.getInt("idAprendiz");

                System.out.println("ID Aprendiz: " + idAprendiz);

                cargarAsistencias(idAprendiz);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ================= CONSULTAR ASISTENCIAS =================

    private void cargarAsistencias(int idAprendiz) {

        try {

                Connection con = Conexion.conectar();

                String sql = """
                SELECT

                        SUM(CASE
                                WHEN estado='ASISTIO'
                                THEN 1
                                ELSE 0
                        END) AS presentes,

                        SUM(CASE
                                WHEN estado='FALTA'
                                THEN 1
                                ELSE 0
                        END) AS faltas,

                        SUM(CASE
                                WHEN estado='TARDE'
                                THEN 1
                                ELSE 0
                        END) AS tardanzas

                FROM asistencia
                WHERE idAprendiz=?
                """;

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, idAprendiz);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                lblAsistencia.setText(
                        "Asistencias: " + rs.getInt("presentes")
                );

                lblFaltas.setText(
                        "Faltas: " + rs.getInt("faltas")
                );

                lblTardanzas.setText(
                        "Tardanzas: " + rs.getInt("tardanzas")
                );

                }

                rs.close();
                ps.close();
                con.close();

        } catch (Exception e) {

                e.printStackTrace();

        }

 }
}

 
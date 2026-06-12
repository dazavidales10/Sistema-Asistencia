package Vista.coordinador.Gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;
import java.awt.*;

import Modelo.Conexion;

public class gestionFichas extends JFrame {

    private JTextField txtNumeroFicha;
    private JTextField txtNombrePrograma;
    private JComboBox<String> cbJornadaS;

    public gestionFichas() {

        setTitle("Gestión de Fichas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        header.setBackground(new Color(0, 180, 0));

        JLabel titulo = new JLabel("Gestión de Fichas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        header.add(titulo);

        add(header, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuenteLabels = new Font("Arial", Font.BOLD, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblNumeroFicha = new JLabel("Número de Ficha:");
        lblNumeroFicha.setFont(fuenteLabels);
        formulario.add(lblNumeroFicha, gbc);

        gbc.gridx = 1;
        txtNumeroFicha = new JTextField(20);
        formulario.add(txtNumeroFicha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel lblPrograma = new JLabel("Nombre Programa:");
        lblPrograma.setFont(fuenteLabels);
        formulario.add(lblPrograma, gbc);

        gbc.gridx = 1;
        txtNombrePrograma = new JTextField(20);
        formulario.add(txtNombrePrograma, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel lblJornada = new JLabel("Jornada:");
        lblJornada.setFont(fuenteLabels);
        formulario.add(lblJornada, gbc);

        gbc.gridx = 1;

        String[] jornadas = {
                "Mañana",
                "Tarde",
                "Noche",
                "Mixta"
        };

        cbJornadaS = new JComboBox<>(jornadas);
        formulario.add(cbJornadaS, gbc);

        add(formulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panelBotones.setBackground(Color.WHITE);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        JButton[] botones = { btnAgregar, btnEditar, btnEliminar };

        for (JButton btn : botones) {
            btn.setPreferredSize(new Dimension(180, 50));
            btn.setBackground(new Color(0, 180, 0));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            panelBotones.add(btn);
        }

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {

            String numeroFicha = txtNumeroFicha.getText().trim();
            String programa = txtNombrePrograma.getText().trim();
            String jornada = cbJornadaS.getSelectedItem().toString();

            try {

                Connection con = Conexion.conectar();

                String sql = "INSERT INTO ficha (programa, numeroFicha, jornada) VALUES (?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, programa);
                ps.setString(2, numeroFicha);
                ps.setString(3, jornada);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Ficha agregada correctamente");

                txtNumeroFicha.setText("");
                txtNombrePrograma.setText("");

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(this,
                        "Error al agregar ficha");
            }
        });

        btnEliminar.addActionListener(e -> {

            String numeroFicha = txtNumeroFicha.getText().trim();
            String programa = txtNombrePrograma.getText().trim();
            String jornada = cbJornadaS.getSelectedItem().toString();

            try {

                Connection con = Conexion.conectar();

                String sql = """
                        DELETE FROM ficha
                        WHERE programa = ?
                        AND numeroFicha = ?
                        AND jornada = ?
                        """;

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, programa);
                ps.setString(2, numeroFicha);
                ps.setString(3, jornada);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Ficha eliminada correctamente");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No existe una ficha con esos datos");
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(this,
                        "Error al eliminar la ficha");
            }
        });

        btnEditar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Función editar pendiente");
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new gestionFichas().setVisible(true);
        });

    }
}
package Vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// import org.w3c.dom.events.MouseEvent;
import Modelo.Conexion;

public class panelInstructor extends JFrame {

    private JTextField txtNumeroFicha;
    private JTextField txtNombrePrograma;
    private JComboBox<String> cbJornadaS;

    public panelInstructor() {
        
        setTitle("Panel instructor");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Instructor");
        add(lbl);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setContentPane(panel);

        JLabel arrow = new JLabel("←");
        arrow.setBounds(30, 5, 50, 50);
        arrow.setFont(new Font("Arial", Font.BOLD,40));
        // Creates a blue border with a thickness of 5 pixels
        arrow.setForeground(Color.white);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        panel.add(arrow);

        JLabel lblCoordinador = new JLabel("Panel Instructor");
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


        JLabel lblNumeroFicha = new JLabel("Numero de Ficha");
        lblNumeroFicha.setOpaque(true);
        lblNumeroFicha.setBackground(new Color(0, 180, 0));
        lblNumeroFicha.setForeground(Color.WHITE);
        lblNumeroFicha.setBounds(640, 450, 180, 25);
        panel.add(lblNumeroFicha);

        txtNumeroFicha = new JTextField();
        txtNumeroFicha.setBounds(820, 450, 180, 25);
        panel.add(txtNumeroFicha);

        JLabel lblNombrePrograma = new JLabel("Nombre de Programa");
        lblNombrePrograma.setOpaque(true);
        lblNombrePrograma.setBackground(new Color(0, 180, 0));
        lblNombrePrograma.setForeground(Color.WHITE);
        lblNombrePrograma.setBounds(640, 550, 180, 25);
        panel.add(lblNombrePrograma);

        txtNombrePrograma = new JTextField();
        txtNombrePrograma.setBounds(820, 550, 180, 25);
        panel.add(txtNombrePrograma);


        JLabel lblJornada = new JLabel("Jornada");
        lblJornada.setOpaque(true);
        lblJornada.setBackground(new Color(0, 180, 0));
        lblJornada.setForeground(Color.WHITE);
        lblJornada.setBounds(1140, 550, 80, 25);
        panel.add(lblJornada);

        String[] jornadas = {"Mañana", "Tarde", "Noche","Mixta"};
        cbJornadaS = new JComboBox<>(jornadas);
        cbJornadaS.setBounds(1220, 550, 100, 25);
        panel.add(cbJornadaS);



        //Bonoes para el Coordinador
                
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(460, 860, 240, 70);
        btnAgregar.setFont(new Font("Arial", Font.BOLD,20));
        btnAgregar.setBackground(new Color(0, 180, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorderPainted(false);
        panel.add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(860, 860, 240, 70);
        btnEditar.setFont(new Font("Arial", Font.BOLD,20));
        btnEditar.setBackground(new Color(0, 180, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBorderPainted(false);
        panel.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(1260, 860, 240, 70);
        btnEliminar.setFont(new Font("Arial", Font.BOLD,20));
        btnEliminar.setBackground(new Color(0, 180, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBorderPainted(false);
        panel.add(btnEliminar);

        //Aciones de los Botones

        btnAgregar.addActionListener(e -> {

            String numeroFicha = txtNumeroFicha.getText();
            String programa = txtNombrePrograma.getText();
            String jornada = cbJornadaS.getSelectedItem().toString();

            try {

                Connection con = Conexion.conectar();

                String sql = "INSERT INTO ficha (programa, numeroFicha, jornada) VALUES (?, ?, ?)";

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

            String numeroFicha = txtNumeroFicha.getText();
            String programa = txtNombrePrograma.getText();
            String jornada = cbJornadaS.getSelectedItem().toString();

            try {

                Connection con = Conexion.conectar();

                String sql = "DELETE FROM ficha WHERE programa = ? AND numeroFicha = ? AND jornada = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, programa);
                ps.setString(2, numeroFicha);
                ps.setString(3, jornada);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "Ficha eliminada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No existe una ficha con esos datos");
                }
                
                



            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar la ficha");
            }

            txtNumeroFicha.setText(" ");
            txtNombrePrograma.setText(" "); 


        });

        btnEditar.addActionListener(e -> {
            
        });

        
    }
    public static void main(String[] args) {
        new panelInstructor().setVisible(true);
    }
}
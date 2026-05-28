package Vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import java.awt.*;

import org.w3c.dom.events.MouseEvent;
import Modelo.Conexion;

public class PanelCoordinador extends JFrame {

    public PanelCoordinador() {
        
        // nombre = getString()
        // programa = getWarningString(connection.con )

        setTitle("Panel Coordinador");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenido Coordinador");
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

        JLabel lblCoordinador = new JLabel("Panel Coordinador");
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


        JLabel titulo = new JLabel("Coordinador");
        titulo.setBounds(760, 400, 500, 70);
        titulo.setFont(new Font("Arial", Font.PLAIN, 50));
        panel.add(titulo);

        JLabel nombreCoordinador = new JLabel("Nombre: ");
        nombreCoordinador.setBounds(760, 500, 200, 40);
        nombreCoordinador.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(nombreCoordinador);

        JLabel program = new JLabel("Programa: ");
        program.setBounds(760, 600, 200, 40);
        program.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(program);


        //Info para los Lables 
        try {

            Conexion conexion = new Conexion();
            Connection con = conexion.con;

            String sql = "SELECT nombre, programa FROM usarios LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {

                String nombre = resultado.getString("nombre");
                String programa = resultado.getString("programa");

                nombreCoordinador.setText("Nombre: " + nombre);
                program.setText("Programa: " + programa);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }




        //Bonoes para el Coordinador
                
        JButton btnFichas = new JButton("Gestión Fichas");
        btnFichas.setBounds(460, 860, 240, 70);
        btnFichas.setFont(new Font("Arial", Font.BOLD,20));
        btnFichas.setBackground(new Color(0, 180, 0));
        btnFichas.setForeground(Color.WHITE);
        btnFichas.setBorderPainted(false);
        panel.add(btnFichas);

        JButton btnInstr = new JButton("Gestión Instructores");
        btnInstr.setBounds(860, 860, 240, 70);
        btnInstr.setFont(new Font("Arial", Font.BOLD,20));
        btnInstr.setBackground(new Color(0, 180, 0));
        btnInstr.setForeground(Color.WHITE);
        btnInstr.setBorderPainted(false);
        panel.add(btnInstr);

        JButton btnAprendices = new JButton("Gestión Aprendices");
        btnAprendices.setBounds(1260, 860, 240, 70);
        btnAprendices.setFont(new Font("Arial", Font.BOLD,20));
        btnAprendices.setBackground(new Color(0, 180, 0));
        btnAprendices.setForeground(Color.WHITE);
        btnAprendices.setBorderPainted(false);
        panel.add(btnAprendices);
        
        JButton btnExit = new JButton("Cerrar Sesion");
        btnExit.setBounds(1460, 200, 240, 70);
        btnExit.setFont(new Font("Arial", Font.BOLD,20));
        btnExit.setBackground(new Color(0, 180, 0));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBorderPainted(false);
        panel.add(btnExit);

        
    }
    public static void main(String[] args) {
        new PanelCoordinador().setVisible(true);
    }
}
package Vista.instructor;

import javax.swing.*;
import java.awt.*;

import Vista.instructor.GestionInstructor.RegistrarAsitencia.seleccionarFicha;
import Vista.instructor.GestionInstructor.VerAprendices.seleccionarFichaVer;

public class PanelInstructor extends JFrame {

    private int idInstructor;
    private String numeroFicha;

    public PanelInstructor(
            int idInstructor,
            String nombre,
            String especialidad,
            String numeroFicha
    ) {

        this.idInstructor = idInstructor;
        this.numeroFicha = numeroFicha;

        System.out.println("==================================");
        System.out.println("ID Instructor: " + idInstructor);
        System.out.println("Nombre: " + nombre);
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Ficha: " + numeroFicha);
        System.out.println("==================================");

        setTitle("Panel Instructor");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //================ ENCABEZADO ================

        JPanel contenedorSuperior = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Panel Instructor", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0,180,0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial",Font.BOLD,22));
        header.setPreferredSize(new Dimension(0,70));

        JPanel panelCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setBackground(new Color(0,180,0));
        btnCerrar.setForeground(Color.WHITE);

        panelCerrar.add(btnCerrar);

        contenedorSuperior.add(header,BorderLayout.NORTH);
        contenedorSuperior.add(panelCerrar,BorderLayout.SOUTH);

        add(contenedorSuperior,BorderLayout.NORTH);

        //================ CENTRO ====================

        JPanel centerPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;

        centerPanel.add(new JLabel("Bienvenido: " + nombre),gbc);

        gbc.gridy++;

        centerPanel.add(new JLabel("Especialidad: " + especialidad),gbc);

        gbc.gridy++;

        centerPanel.add(new JLabel("Ficha: " + numeroFicha),gbc);

        add(centerPanel,BorderLayout.CENTER);

        //================ BOTONES ===================

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton btnRegistrar = new JButton("Registrar Asistencia");
        JButton btnExcusa = new JButton("Gestionar Excusas");
        JButton btnAprendices = new JButton("Ver Aprendices");
        JButton btnReportes = new JButton("Generar Reportes");

        JButton[] botones = {
                btnRegistrar,
                btnExcusa,
                btnAprendices,
                btnReportes
        };

        for(JButton b : botones){

            b.setPreferredSize(new Dimension(220,70));
            b.setBackground(new Color(0,180,0));
            b.setForeground(Color.WHITE);

            bottomPanel.add(b);

        }

        add(bottomPanel,BorderLayout.SOUTH);

        //================ EVENTOS ===================

        btnRegistrar.addActionListener(e -> {

            new seleccionarFicha(
                    idInstructor,
                    nombre,
                    especialidad
            ).setVisible(true);

            dispose();

        });
        btnAprendices.addActionListener(e -> {

            new seleccionarFichaVer(
                    idInstructor,
                    nombre,
                    especialidad
            ).setVisible(true);

            dispose();

        });

       btnExcusa.addActionListener(e -> {

    System.out.println("Ficha enviada a gestionarExcusas: " + numeroFicha);

    new gestionarExcusas(numeroFicha).setVisible(true);

});

        btnCerrar.addActionListener(e -> {

            dispose();

            new Vista.Login().setVisible(true);

        });

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new PanelInstructor(
                    5,
                    "María Gómez",
                    "Análisis y Desarrollo de Software",
                    "3364343"
            ).setVisible(true);

        });

    }

}
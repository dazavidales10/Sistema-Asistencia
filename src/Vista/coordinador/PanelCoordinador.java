package Vista.coordinador;

import javax.swing.*;

import Vista.Login;
import Vista.coordinador.Gestion.gestionAprendices;
import Vista.coordinador.Gestion.gestionFichas;
import Vista.coordinador.Gestion.gestionInstructores;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.prefs.Preferences;

public class PanelCoordinador extends JFrame {

    // Componentes
    private JLabel arrow;
    private JLabel lblCoordinador;
    private JLabel panelCoordinador;

    private JLabel titulo;
    private JLabel nombreCoordinador;
    private JLabel program;

    private JButton btnFichas;
    private JButton btnInstr;
    private JButton btnAprendices;
    private JButton btnExit;

    private String nombre;
    private String area;

    public PanelCoordinador(String nombre, String area) {

        this.nombre = nombre;
        this.area = area;

        setTitle("Panel Coordinador");
        setSize(1920,1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

        setContentPane(panel);

        //================ HEADER =================

        panelCoordinador = new JLabel();
        panelCoordinador.setOpaque(true);
        panelCoordinador.setBackground(new Color(0,180,0));
        panel.add(panelCoordinador);

        arrow = new JLabel("←");
        arrow.setFont(new Font("Arial",Font.BOLD,40));
        arrow.setForeground(Color.WHITE);
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(arrow);

        lblCoordinador = new JLabel("Panel Coordinador");
        lblCoordinador.setForeground(Color.WHITE);
        lblCoordinador.setFont(new Font("Arial",Font.BOLD,22));
        panel.add(lblCoordinador);

        //================ CONTENIDO =================

        titulo = new JLabel("Coordinador");
        titulo.setFont(new Font("Arial",Font.BOLD,48));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        nombreCoordinador = new JLabel("Nombre: " + nombre);
        nombreCoordinador.setFont(new Font("Arial",Font.PLAIN,28));
        nombreCoordinador.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nombreCoordinador);

        program = new JLabel("Área: " + area);
        program.setFont(new Font("Arial",Font.PLAIN,28));
        program.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(program);

        //================ BOTONES =================

        btnFichas = new JButton("Gestión Fichas");
        styleButton(btnFichas);
        panel.add(btnFichas);

        btnInstr = new JButton("Gestión Instructores");
        styleButton(btnInstr);
        panel.add(btnInstr);

        btnAprendices = new JButton("Gestión Aprendices");
        styleButton(btnAprendices);
        panel.add(btnAprendices);

        btnExit = new JButton("Cerrar Sesión");
        styleButton(btnExit);
        panel.add(btnExit);

                //================ EVENTOS =================

        btnFichas.addActionListener(e -> {

            new gestionFichas(nombre, area).setVisible(true);
            dispose();

        });

        btnInstr.addActionListener(e -> {

            new gestionInstructores(nombre, area).setVisible(true);
            dispose();

        });

        btnAprendices.addActionListener(e -> {

            new gestionAprendices(nombre, area).setVisible(true);
            dispose();

        });

        btnExit.addActionListener(e -> {

            Preferences prefs =
                    Preferences.userNodeForPackage(Login.class);

            prefs.remove("usuario");

            dispose();

            new Login().setVisible(true);

        });

        // Flecha volver
        arrow.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                Preferences prefs =
                        Preferences.userNodeForPackage(Login.class);

                prefs.remove("usuario");

                dispose();

                new Login().setVisible(true);

            }

        });

        // Primera organización
        resizeComponents();

        // Hace el panel responsive
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                resizeComponents();

            }

        });

    }    //================ RESPONSIVE =================

    private void resizeComponents() {

        int w = getWidth();
        int h = getHeight();

        //================ HEADER =================

        panelCoordinador.setBounds(0, 0, w, 70);

        arrow.setBounds(20, 10, 50, 50);

        lblCoordinador.setBounds(
                (w - 250) / 2,
                18,
                250,
                30
        );

        //================ CONTENIDO CENTRADO =================

        int anchoTitulo = 400;
        int anchoTexto = 700;

        // Altura total del bloque
        int altoBloque = 320;

        // Inicio para centrar verticalmente
        int inicioY = (h - altoBloque) / 2;

        titulo.setBounds(
                (w - anchoTitulo) / 2,
                inicioY,
                anchoTitulo,
                60
        );

        nombreCoordinador.setBounds(
                (w - anchoTexto) / 2,
                inicioY + 80,
                anchoTexto,
                40
        );

        program.setBounds(
                (w - anchoTexto) / 2,
                inicioY + 130,
                anchoTexto,
                40
        );

        //================ BOTÓN CERRAR SESIÓN =================

        btnExit.setBounds(
                w - 240,
                90,
                180,
                50
        );


        //Botones de Gestion Ubicacion

        int ancho = 240;
        int alto = 65;
        int separacion = 40;

        int total = (ancho * 3) + (separacion * 2);

        int inicioX = (w - total) / 2;

        // Más abajo (aprox. al 82% de la ventana)
        int yBotones = (int)(h * 0.82);

        btnFichas.setBounds(
                inicioX,
                yBotones,
                ancho,
                alto
        );

        btnInstr.setBounds(
                inicioX + ancho + separacion,
                yBotones,
                ancho,
                alto
        );

        btnAprendices.setBounds(
                inicioX + (ancho + separacion) * 2,
                yBotones,
                ancho,
                alto
        );
    }
    
    //================ ESTILO BOTONES =================

    private void styleButton(JButton btn) {

        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(0,180,0));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

    }

    //================ MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new PanelCoordinador(
                    "Ana Torres",
                    "Coordinación Académica"
            ).setVisible(true);

        });

    }

}
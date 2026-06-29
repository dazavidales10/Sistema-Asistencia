
package Vista.instructor.GestionInstructor.VerAprendices;

import Conexion.Conexion;
import Vista.instructor.DialogsInstructor.dialogClase;
import Vista.instructor.GestionInstructor.RegistrarAsitencia.PanelAprendices;
import Vista.instructor.GestionInstructor.RegistrarAsitencia.seleccionarFicha;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PanelClasesVer extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtBuscar;

    private JLabel lblTema;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JLabel lblEstado;

    private JButton btnIngresar;

    private int idInstructor;
    private String nombreInstructor;
    private String especialidad;
    private String ficha;

    private int idClaseSeleccionada = -1;

    public PanelClasesVer(
            int idInstructor,
            String nombreInstructor,
            String especialidad,
            String ficha)
        {

        this.idInstructor=idInstructor;
        this.nombreInstructor=nombreInstructor;
        this.especialidad=especialidad;
        this.ficha=ficha;
        

        setTitle("Clases Ver");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel header=new JPanel(null);
        header.setPreferredSize(new Dimension(0,70));
        header.setBackground(new Color(0,180,0));

        JLabel arrow=new JLabel("←");
        arrow.setBounds(20,5,60,50);
        arrow.setForeground(Color.WHITE);
        arrow.setFont(new Font("Arial",Font.BOLD,40));
        arrow.setCursor(new Cursor(Cursor.HAND_CURSOR));

        header.add(arrow);

        JLabel titulo=new JLabel(
                "CLASES DE LA FICHA "+ficha,
                SwingConstants.CENTER);

        titulo.setBounds(250,15,700,35);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial",Font.BOLD,24));

        header.add(titulo);

        add(header,BorderLayout.NORTH);

        arrow.addMouseListener(new java.awt.event.MouseAdapter(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){

                new seleccionarFichaVer(
                        idInstructor,
                        nombreInstructor,
                        especialidad
                ).setVisible(true);

                dispose();

            }

        });

        //================ PANEL CENTRAL ====================

        JPanel centro=new JPanel(new BorderLayout());
        JPanel buscador=new JPanel(new FlowLayout());
        txtBuscar=new JTextField(25);
        JButton btnBuscar=new JButton("Buscar");

        JButton btnNuevaClase = new JButton("Nueva clase");
        btnNuevaClase.setBackground(new Color(0,180,0));
        btnNuevaClase.setForeground(Color.WHITE);
        buscador.add(btnNuevaClase);

        btnBuscar.setBackground(new Color(0,180,0));
        btnBuscar.setForeground(Color.WHITE);

        buscador.add(new JLabel("Tema"));
        buscador.add(txtBuscar);
        buscador.add(btnBuscar);
        buscador.add(btnNuevaClase);

        centro.add(buscador,BorderLayout.NORTH);

        modelo=new DefaultTableModel(
                new String[]{
                        "ID",
                        "Fecha",
                        "Hora",
                        "Tema",
                        "Estado"
                },0);

        tabla=new JTable(modelo);

        tabla.setRowHeight(35);

        DefaultTableCellRenderer c=
                new DefaultTableCellRenderer();

        c.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<5;i++){
            tabla.getColumnModel().getColumn(i).setCellRenderer(c);
        }

        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);

        centro.add(new JScrollPane(tabla),BorderLayout.CENTER);
        add(centro,BorderLayout.CENTER);

        //================ PANEL DERECHO ====================

        JPanel derecha=new JPanel();
        derecha.setPreferredSize(new Dimension(300,0));
        derecha.setLayout(new BoxLayout(
                derecha,
                BoxLayout.Y_AXIS));
        derecha.setBorder(
                BorderFactory.createTitledBorder(
                        "Clase seleccionada"));

        derecha.add(Box.createVerticalStrut(20));
        derecha.add(new JLabel("Tema"));

        lblTema=new JLabel("-");
        lblTema.setFont(
                new Font("Arial",
                        Font.BOLD,
                        18));

        derecha.add(lblTema);
        derecha.add(Box.createVerticalStrut(20));
        derecha.add(new JLabel("Fecha"));
        lblFecha=new JLabel("-");
        derecha.add(lblFecha);

        derecha.add(Box.createVerticalStrut(20));
        derecha.add(new JLabel("Hora"));
        lblHora=new JLabel("-");
        derecha.add(lblHora);

        derecha.add(Box.createVerticalStrut(20));
        derecha.add(new JLabel("Estado"));
        lblEstado=new JLabel("-");
        derecha.add(lblEstado);

        derecha.add(Box.createVerticalStrut(40));
        btnIngresar=new JButton("Ingresar");
        btnIngresar.setEnabled(false);

        btnIngresar.setBackground(new Color(0,180,0));
        btnIngresar.setForeground(Color.WHITE);

        derecha.add(btnIngresar);
        
        add(derecha,BorderLayout.EAST);
    
        JButton btnVer = new JButton("Ver Aprendices");
        btnVer.setBackground(new Color(0,180,0));
        btnVer.setForeground(Color.WHITE);
        btnVer.setFont(new Font("Arial",Font.BOLD,18));
        btnVer.setBorderPainted(false);


        // Eventos de los Botones

        btnBuscar.addActionListener(e -> buscar());

            tabla.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
                if (e.getValueIsAdjusting())
                    return;

                int fila = tabla.getSelectedRow();

                if (fila == -1)
                    return;

                idClaseSeleccionada = Integer.parseInt(
                        modelo.getValueAt(fila, 0).toString()
                );

                lblFecha.setText(
                        modelo.getValueAt(fila, 1).toString()
                );

                lblHora.setText(
                        modelo.getValueAt(fila, 2).toString()
                );

                lblTema.setText(
                        modelo.getValueAt(fila, 3).toString()
                );

                lblEstado.setText(
                        modelo.getValueAt(fila, 4).toString()
                );

                btnIngresar.setEnabled(true);

            });

            btnVer.addActionListener(e -> {

                new PanelVerAprendices(
                        idInstructor,
                        nombreInstructor,
                        especialidad,
                        ficha,
                        idClaseSeleccionada
                ).setVisible(true);

                dispose();

            });

            btnNuevaClase.addActionListener(e -> {

                new dialogClase(
                        this,
                        idInstructor,
                        ficha
                );

                cargarClases();

            });

            btnIngresar.addActionListener(e -> {

                int fila = tabla.getSelectedRow();

                if (fila == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Seleccione una clase."
                    );

                    return;
                }

                int idClase = Integer.parseInt(
                        modelo.getValueAt(fila,0).toString()
                );

                new PanelVerAprendices(
                        idInstructor,
                        nombreInstructor,
                        especialidad,
                        ficha,
                        idClase
                ).setVisible(true);

                dispose();

            });

            cargarClases();

        }
        private void cargarClases() {
            System.out.println("Instructor: " + idInstructor);
            System.out.println("Ficha: " + ficha);

        modelo.setRowCount(0);

        String sql = """
                SELECT
                    idClase,
                    fecha,
                    horaInicio,
                    tema,
                    estado
                FROM clase
                WHERE idInstructor = ?
                AND numeroFicha = ?
                ORDER BY fecha DESC, horaInicio DESC
                """;

        try (Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idInstructor);
            ps.setString(2, ficha);

            System.out.println("Consultando aprendices...");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modelo.addRow(new Object[]{

                        rs.getInt("idClase"),
                        rs.getDate("fecha"),
                        rs.getTime("horaInicio"),
                        rs.getString("tema"),
                        rs.getString("estado")

                });

            }

            limpiarSeleccion();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    // metodo buscar

    private void buscar() {

        modelo.setRowCount(0);

        String sql = """
                SELECT
                    idClase,
                    fecha,
                    horaInicio,
                    tema,
                    estado
                FROM clase
                WHERE idInstructor = ?
                AND numeroFicha = ?
                AND tema LIKE ?
                ORDER BY fecha DESC, horaInicio DESC
                """;

        try (Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idInstructor);
            ps.setString(2, ficha);
            ps.setString(3, "%" + txtBuscar.getText().trim() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modelo.addRow(new Object[]{

                        rs.getInt("idClase"),
                        rs.getDate("fecha"),
                        rs.getTime("horaInicio"),
                        rs.getString("tema"),
                        rs.getString("estado")

                });

            }

            limpiarSeleccion();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    // metodo limpiar

    private void limpiarSeleccion() {

        idClaseSeleccionada = -1;

        lblTema.setText("-");
        lblFecha.setText("-");
        lblHora.setText("-");
        lblEstado.setText("-");

        btnIngresar.setEnabled(false);

    }
}



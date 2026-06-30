package Vista.instructor;

import Conexion.Conexion;

import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class gestionarExcusas extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAceptar;
    private JButton btnRechazar;

    private String[] rutasPDF = new String[1000];

    public gestionarExcusas(String ficha){

        setTitle("Gestionar Excusas");

        setSize(950,550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel superior=new JPanel();

        superior.setBackground(new Color(0,180,0));

        JLabel titulo=new JLabel("Gestionar Excusas");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial",Font.BOLD,20));

        superior.add(titulo);

        add(superior,BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{
                    "ID",
                    "Aprendiz",
                    "Fecha",
                    "Motivo",
                    "Archivo",
                    "Estado"
            },
            0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

        tabla=new JTable(modelo);

        add(new JScrollPane(tabla),BorderLayout.CENTER);

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
        
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
        
                if(e.getClickCount()==2){
        
                    int fila = tabla.getSelectedRow();

                    if (fila != -1) {

                        abrirPDF(rutasPDF[fila]);

                    }
        
                }
        
            }
        
        });

        JPanel inferior=new JPanel();

        btnAceptar=new JButton("Aceptar");

        btnAceptar.setBackground(new Color(0,180,0));
        btnAceptar.setForeground(Color.WHITE);

        btnRechazar=new JButton("Rechazar");

        btnRechazar.setBackground(Color.RED);
        btnRechazar.setForeground(Color.WHITE);

        inferior.add(btnAceptar);
        inferior.add(btnRechazar);

        add(inferior,BorderLayout.SOUTH);

        cargarExcusas(ficha);

        btnAceptar.addActionListener(e->actualizarEstado("Aprobada"));

        btnRechazar.addActionListener(e->actualizarEstado("Rechazada"));

    }

    private void cargarExcusas(String ficha) {

    System.out.println("==============================");
    System.out.println("FICHA RECIBIDA: " + ficha);
    System.out.println("==============================");

    modelo.setRowCount(0);

    try {

        Connection con = Conexion.conectar();

        String sql = """
        SELECT
            e.idExcusa,
            u.nombre,
            e.fecha,
            e.motivo,
            e.archivo,
            e.estado
        FROM excusa e
        INNER JOIN aprendiz a
            ON e.idAprendiz = a.idAprendiz
        INNER JOIN usuarios u
            ON a.id = u.id
        WHERE e.idInstructor = (
            SELECT idInstructor
            FROM instructor
            WHERE numeroFicha = ?
        )
        """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, ficha);

        ResultSet rs = ps.executeQuery();

            int fila = 0;

        while (rs.next()) {

            String ruta = rs.getString("archivo");

            rutasPDF[fila] = ruta;

            modelo.addRow(new Object[]{

                    rs.getInt("idExcusa"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("motivo"),
                    new File(ruta).getName(),
                    rs.getString("estado")

            });

            fila++;
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception ex) {

        ex.printStackTrace();

    }

}

    private void actualizarEstado(String estado){

        int fila=tabla.getSelectedRow();

        if(fila==-1){

            JOptionPane.showMessageDialog(this,"Seleccione una excusa.");

            return;

        }

        int idExcusa=(int)modelo.getValueAt(fila,0);

        try{

            Connection con= Conexion.conectar();

            PreparedStatement ps=con.prepareStatement(

                    "UPDATE excusa SET estado=? WHERE idExcusa=?"

            );

            ps.setString(1,estado);

            ps.setInt(2,idExcusa);

            ps.executeUpdate();

            ps.close();

            con.close();

            modelo.setValueAt(estado,fila,5);

            JOptionPane.showMessageDialog(this,"Estado actualizado.");

        }

        catch(Exception ex){

            ex.printStackTrace();

        }

    }

    private void abrirPDF(String ruta){
    
        try{
    
            File archivo=new File(ruta);
    
            if(!archivo.exists()){
    
                JOptionPane.showMessageDialog(
    
                        this,
    
                        "El archivo no existe."
    
                );
    
                return;
    
            }
    
            Desktop.getDesktop().open(archivo);
    
        }
    
        catch(Exception e){
    
            e.printStackTrace();
    
            JOptionPane.showMessageDialog(
    
                    this,
    
                    "No fue posible abrir el archivo."
    
            );
    
        }
    
    }

}
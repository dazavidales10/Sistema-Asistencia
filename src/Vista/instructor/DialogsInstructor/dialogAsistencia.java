package Vista.instructor.DialogsInstructor;

import javax.swing.*;
import java.awt.*;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dialogAsistencia extends JDialog {

    private JComboBox<String> cbEstado;
    private JTextArea txtObservacion;
    private JButton btnGuardar;

    private int idClase;
    private int idAprendiz;

    private boolean modoEditar = false;
    private int idAsistencia = -1;

    public dialogAsistencia(
            JFrame parent,
            int idClase,
            int idAprendiz) {

        super(parent,"Registrar Asistencia",true);

        this.idClase=idClase;
        this.idAprendiz=idAprendiz;

        setSize(450,420);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel titulo=new JLabel("Registrar Asistencia");
        titulo.setBounds(90,20,300,35);
        titulo.setFont(new Font("Arial",Font.BOLD,22));
        add(titulo);

        JLabel lblEstado=new JLabel("Estado");
        lblEstado.setBounds(40,90,120,30);
        add(lblEstado);

        cbEstado=new JComboBox<>(new String[]{
                "ASISTIO",
                "TARDE",
                "FALTA",
                "EXCUSA"
        });

        cbEstado.setBounds(150,90,220,35);
        add(cbEstado);

        JLabel lblObservacion=new JLabel("Observación");
        lblObservacion.setBounds(40,150,120,30);
        add(lblObservacion);

        txtObservacion=new JTextArea();

        JScrollPane scroll=new JScrollPane(txtObservacion);

        scroll.setBounds(150,150,220,100);

        add(scroll);

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarAsistencia());
        btnGuardar.setBounds(120,300,180,45);

        btnGuardar.setBackground(new Color(0,180,0));
        btnGuardar.setForeground(Color.WHITE);

        add(btnGuardar);
        cargarAsistencia();

    }
    private void cargarAsistencia() {

        String sql = """
            SELECT
                idAsistencia,
                estado,
                observacion
            FROM asistencia
            WHERE idClase=?
            AND idAprendiz=?
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setInt(1,idClase);
            ps.setInt(2,idAprendiz);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                modoEditar = true;

                idAsistencia = rs.getInt("idAsistencia");

                cbEstado.setSelectedItem(
                        rs.getString("estado")
                );

                String obs = rs.getString("observacion");

                if(obs != null){
                    txtObservacion.setText(obs);
                }

                btnGuardar.setText("Actualizar");

            }

        }catch(Exception ex){

            ex.printStackTrace();

        }

    }

    private void guardarAsistencia(){

        if(modoEditar){

            actualizarAsistencia();

        }else{

            insertarAsistencia();

        }

    }
    private void insertarAsistencia(){

        String estado = cbEstado.getSelectedItem().toString();

        String observacion =
                txtObservacion.getText().trim();

        String sql = """
            INSERT INTO asistencia
            (
                idClase,
                idAprendiz,
                fechaRegistro,
                horaRegistro,
                estado,
                observacion
            )
            VALUES
            (
                ?,
                ?,
                CURRENT_DATE(),
                CURRENT_TIME(),
                ?,
                ?
            )
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setInt(1,idClase);
            ps.setInt(2,idAprendiz);
            ps.setString(3,estado);
            ps.setString(4,observacion);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Asistencia registrada."
            );

            dispose();

        }catch(Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }

    private void actualizarAsistencia(){

        String estado = cbEstado.getSelectedItem().toString();

        String observacion =
                txtObservacion.getText().trim();

        String sql = """
            UPDATE asistencia
            SET
                estado=?,
                observacion=?,
                fechaRegistro=CURRENT_DATE(),
                horaRegistro=CURRENT_TIME()
            WHERE idAsistencia=?
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setString(1,estado);
            ps.setString(2,observacion);
            ps.setInt(3,idAsistencia);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Asistencia actualizada."
            );

            dispose();

        }catch(Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }

    }
    

}
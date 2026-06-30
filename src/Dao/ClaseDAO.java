package Dao;

import Conexion.Conexion;
import Modelo.Clase;

import java.sql.*;
import java.util.ArrayList;

public class ClaseDAO {

    Connection con;

    public ClaseDAO(){

        con = Conexion.conectar();

    }

    public ArrayList<Clase> listarClases(String ficha){

        ArrayList<Clase> lista = new ArrayList<>();

        String sql = "SELECT * FROM clase WHERE numeroFicha=? ORDER BY fecha DESC";

        try{

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,ficha);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Clase c = new Clase();

                c.setIdClase(rs.getInt("idClase"));
                c.setNumeroFicha(rs.getString("numeroFicha"));
                c.setFecha(rs.getDate("fecha"));
                c.setHoraInicio(rs.getTime("horaInicio"));
                c.setHoraLimite(rs.getTime("horaLimite"));
                c.setTema(rs.getString("tema"));
                c.setEstado(rs.getString("estado"));

                lista.add(c);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return lista;

    }

}
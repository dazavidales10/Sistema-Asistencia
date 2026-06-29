package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3307/sistemaasistencia2";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa");

        } catch (Exception e) {

            System.out.println("Error de conexión");
            e.printStackTrace();
        }

        return con;
    }
}
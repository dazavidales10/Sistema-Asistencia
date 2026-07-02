package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemaasistencia2";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {

            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();

            return null;
        }
    }
}

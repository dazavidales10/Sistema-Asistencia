package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

    public static void main(String[] args) {

        Connection con = null;

        try {

            // Conexión a XAMPP MySQL
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistemaasistencia",
                    "root",
                    ""
            );

            if (con != null) {
                System.out.println(" CONEXIÓN EXITOSA A XAMPP");
            } else {
                System.out.println(" NO SE PUDO CONECTAR");
            }

        } catch (Exception e) {

            System.out.println(" ERROR DE CONEXIÓN");
            e.printStackTrace();
        }
    }
}

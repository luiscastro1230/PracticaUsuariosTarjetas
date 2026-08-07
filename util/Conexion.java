package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    static String url = "jdbc:sqlserver://localhost:1433;databaseName=bd_usuarios_tarjetas;user=sa;password=SQL_Luis2026;encrypt=true;trustServerCertificate=true;";

    public static Connection getConexion() {
        try {
            Connection con = DriverManager.getConnection(url);
            return con;
        } catch (Exception e) {
            System.out.println("error de conexion: " + e.getMessage());
            return null;
        }
    }
}
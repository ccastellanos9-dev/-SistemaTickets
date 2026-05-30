package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL     = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";
    private static final String USUARIO = "sistema1234";
    private static final String CLAVE   = "sistema1234";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return con;
    }
}
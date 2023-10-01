package laboral;

import java.sql.*;

public class ConexionMariaDB {

    private static final String URL = "jdbc:mariadb://localhost:3306/empleados";
    private static final String USUARIO = "root";
    private static final String PASS = "123456";

    private Connection conexion;


    public ConexionMariaDB() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASS);
            System.out.println("Se ha establecido conexion con la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conecctar a la base de datos: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Se ha cerrado la conexion a la base de datos");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}

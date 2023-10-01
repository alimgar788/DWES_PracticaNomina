package laboral;

import laboral.exceptions.CierreRecursosException;

import java.sql.*;
import java.util.List;

public class ConsultasMariaDB {
    public static void cierraRecursosUtilizadosEnDB(Connection conexion, PreparedStatement... statements) throws CierreRecursosException {
        for (PreparedStatement statement : statements) {
            if (statement != null) {
                try {
                    statement.close();
                    conexion.close();
                } catch (SQLException e) {
                    throw new CierreRecursosException("Los recursos utilizandos para la base de datos no se han podido cerrar correctamente");
                }
            }
        }

    }

    public static void insertarEmpleadoEnDB(ConexionMariaDB conexionDB, Empleado empleado, double salario) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatementEmpleado = null;
        PreparedStatement preparedStatementNomina = null;

        try {
            String sqlEmpleado = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES (?,?,?,?,?)";
            preparedStatementEmpleado = conexion.prepareStatement(sqlEmpleado);

            preparedStatementEmpleado.setString(1, empleado.getNombre());
            preparedStatementEmpleado.setString(2, empleado.getDni());
            preparedStatementEmpleado.setString(3, String.valueOf(empleado.getSexo()));
            preparedStatementEmpleado.setInt(4, empleado.getCategoria());
            preparedStatementEmpleado.setDouble(5, empleado.getAnyos());

            preparedStatementEmpleado.executeUpdate();

            String sqlNomina = "INSERT INTO nominas (dni, salario) VALUES (?,?)";
            preparedStatementNomina = conexion.prepareStatement(sqlNomina);

            preparedStatementNomina.setString(1, empleado.getDni());
            preparedStatementNomina.setDouble(2, salario);

            preparedStatementNomina.executeUpdate();

            System.out.println("El empleado se ha insertado en la base de datos");

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar al empleado en la base de datos");
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatementEmpleado, preparedStatementNomina);
        }
    }

    public static void actualizarEmpleadoEnDB(ConexionMariaDB conexionDB, String dni, int nuevaCategoria, double nuevosAnyos, double nuevoSalario) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatementEmpleado = null;
        PreparedStatement preparedStatementNomina = null;

        try {
            String sqlEmpleado = "UPDATE empleados SET categoria = ?, anyos = ? WHERE dni = ?";
            preparedStatementEmpleado = conexion.prepareStatement(sqlEmpleado);

            preparedStatementEmpleado.setInt(1, nuevaCategoria);
            preparedStatementEmpleado.setDouble(2, nuevosAnyos);
            preparedStatementEmpleado.setString(3, dni);

            String sqlNomina = "UPDATE nominas SET salario = ? WHERE dni = ?";
            preparedStatementNomina = conexion.prepareStatement(sqlNomina);

            preparedStatementNomina.setDouble(1, nuevoSalario);
            preparedStatementNomina.setString(2, dni);

            preparedStatementNomina.executeUpdate();

            System.out.println("Los datos del empleado se han actualizado en la base de datos correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatementEmpleado, preparedStatementNomina);
        }
    }

    public static void borrarEmpleadoEnDB(ConexionMariaDB conexionDB, String dni) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM empleados WHERE dni = ?";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, dni);

            preparedStatement.executeUpdate();

            System.out.println("El empleado se ha borrado correctamente de la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatement);
        }
    }


    public static void insertarListaEmpleadosEnDB(ConexionMariaDB conexionDB, List<Empleado> empleados, double salario) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatementEmpleado = null;
        PreparedStatement preparedStatementNomina = null;

        try {
            String sql = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES (?,?,?,?,?)";
            preparedStatementEmpleado = conexion.prepareStatement(sql);

            for (Empleado empleado : empleados) {
                preparedStatementEmpleado.setString(1, empleado.getNombre());
                preparedStatementEmpleado.setString(2, empleado.getDni());
                preparedStatementEmpleado.setString(3, String.valueOf(empleado.getSexo()));

                if (empleado.getCategoria() != 0 && empleado.getAnyos() != 0) {
                    preparedStatementEmpleado.setInt(4, empleado.getCategoria());
                    preparedStatementEmpleado.setDouble(5, empleado.getAnyos());
                } else {
                    //Se asignan los valores por defecto si no se proporciona la categoria y el año
                    preparedStatementEmpleado.setInt(4, 1);
                    preparedStatementEmpleado.setDouble(5, 0.0);
                }
                preparedStatementEmpleado.executeUpdate();

                String sqlNomina = "INSERT INTO nominas (dni, salario) VALUES (?,?)";
                preparedStatementNomina = conexion.prepareStatement(sqlNomina);

                preparedStatementNomina.setString(1, empleado.getDni());
                preparedStatementNomina.setDouble(2, salario);

                preparedStatementNomina.executeUpdate();
            }

            System.out.println("Empleados insertados en la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatementEmpleado, preparedStatementNomina);
        }
    }

    public static void insertarSalariosEnDB(ConexionMariaDB conexionDB, List<Empleado> empleados, double salario) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatement = null;
        try {
            Nomina nomina = new Nomina();
            String sql = "INSERT INTO nominas (dni, salario) VALUES (?,?)";
            preparedStatement = conexion.prepareStatement(sql);

            for (Empleado empleado : empleados) {
                preparedStatement.setString(1, empleado.getDni());
                preparedStatement.setDouble(2, salario);
                preparedStatement.executeUpdate();
            }


            System.out.println("El salario se ha registrado correctamente en la tabla Nomina de la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatement);
        }
    }

    public static void actualizarSalarioEnBD(ConexionMariaDB conexionDB, String dni, double salario) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "UPDATE nominas SET salario = ? WHERE dni = ?";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setDouble(1, salario);
            preparedStatement.setString(2, dni);

            preparedStatement.executeUpdate();

            System.out.println("El salario del empleado se ha actualizado en la base de datos correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatement);
        }
    }

    public static void borrarSalarioEmpleadoEnBD(ConexionMariaDB conexionDB, String dni) throws CierreRecursosException {
        Connection conexion = conexionDB.getConexion();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM nominas WHERE dni = ?";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, dni);

            preparedStatement.executeUpdate();

            System.out.println("El empleado se ha borrado correctamente de la tabla NOMINAS de la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cierraRecursosUtilizadosEnDB(conexion, preparedStatement);
        }
    }
}

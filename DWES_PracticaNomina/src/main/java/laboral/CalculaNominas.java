package laboral;

import laboral.exceptions.DatosNoCorrectosException;

import java.io.*;
import java.util.List;

/**
 * La clase CalculaNominas es una clase que será utilizada como main para hacer las pruebas oportunas.
 * Contiene un método privado para las pruebas del mismo.
 */

public class CalculaNominas {

    /**
     * Método que recibe como parámetro un empleado para el cual se calculará su salario.
     * Para ello, se crea una Nómina y se hace uso del método sueldo de dicha clase
     *
     * @param empl
     */
    private void escribe(Empleado empl) {
        empl.imprime();
        Nomina nomina = new Nomina();
        System.out.println("El salario de " + empl.nombre + " es de: " + nomina.calcularsueldoEmpleado(empl));
    }

    public Empleado buscarEmpleadoPorDni(List<Empleado> listaEmpleados, String dniBuscado) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getDni().equals(dniBuscado)) {
                return empleado;
            }
        }
        //Si no se encuentra ningún empleado con el dni, se devuelve null
        return null;
    }

    public void cambiarAntiguedadEmpleado(Empleado empleado, double nuevoAnyo) throws DatosNoCorrectosException {
        if(empleado.getAnyos()!= nuevoAnyo){
                empleado.setAnyos(nuevoAnyo);
        }
    }
    public void cambiarCategoriaEmpleado(Empleado empleado, int nuevaCategoria) throws DatosNoCorrectosException {
        if(empleado.getCategoria()!= nuevaCategoria){
                empleado.setCategoria(nuevaCategoria);
        }
    }

    public double actualizaSueldoEmpleado(String dni, int categoria, double anyos) throws DatosNoCorrectosException, FileNotFoundException {
        List<Empleado> empleados;
        Nomina nomina = new Nomina();

        empleados = GestionArchivos.crearListaEmpleadosDesdeArchivo();
        Empleado empleadoBuscado = buscarEmpleadoPorDni(empleados, dni);
        return nomina.recalculaSueldoEmpleado(empleadoBuscado, categoria, anyos);
    }

    /**
     * Se prueban los diferentes métodos y sus variaciones para probar que se controlan las excepciones
     * y se emiten los mensajes correspondientes.
     *
     * @param args
     * @throws DatosNoCorrectosException
     */
    public static void main(String[] args) throws DatosNoCorrectosException, FileNotFoundException {
        //Se calcula el salario del empleado según la categoría y los años trabajados
        //CalculaNominas calculaNominas = new CalculaNominas();

        //Empleado empl1 = new Empleado("James Cosling", "32000032G", 'M', 4, 7.0);
        //Empleado empl2 = new Empleado("Ada Lovelace", "32000031R", 'F');

        //  calculaNominas.escribe(empl1);
        //  calculaNominas.escribe(empl2);

        //System.out.println("-------------------------");
        /* Se incrementa en 3 años al empleado 2 y se le asigna nueva categoría al empleado 1.
           Se recalcula su nomina*/

//        empl2.incrAnyo();
        //      empl2.incrAnyo();
        //    empl2.incrAnyo();
        //  empl1.setCategoria(9);

        // calculaNominas.escribe(empl1);
        // calculaNominas.escribe(empl2);

        // System.out.println("-------------------------");

        // Se asigna categoría inexistente para comprobar que se controla la excepción de la categoria

        /*
        try {
            empl1.setCategoria(11);
            calculaNominas.escribe(empl1);

        } catch (DatosNoCorrectosException e) {
            System.out.println("DATOS NO CORRECTOS");
        }*/

        //System.out.println("-------------------------");

        // Se asigna un año negativo para comprobar que se controla la excepción de los años trabajados

        /*
        try {

            empl2 = new Empleado("Adam Lovelace", "32000031R", 'M', 8, -3);
            calculaNominas.escribe(empl2);

        } catch (DatosNoCorrectosException e) {
            System.out.println("DATOS NO CORRECTOS");
        }
        */
        //System.out.println("-------------------------");

        //Se actualizan los años trabajados para el empleado con dni "32000032G" a 9 años
        /*
        try {
            CalculaNominas calculaNominas2 = new CalculaNominas();

            String dniEmpleado = "32000032G";
            double nuevosAnyos = 9.0;

            calculaNominas2.actualizarAnyosTrabajadosEnArchivo(dniEmpleado, nuevosAnyos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // System.out.println("-------------------------");
        List<Empleado> empleados = GestionArchivos.crearListaEmpleadosDesdeArchivo();

        //Se crea instancia de ConexionMariaDB
        ConexionMariaDB conexionDB = new ConexionMariaDB();

        //Se inserta la lista de empleados generada a partir del archivo empleados.txt en la base de datos
        //ConsultasMariaDB.insertarEmpleados(conexionDB,empleados);

        //Se rellena la tabla Nominas con los dni y los salarios de los empleados
        //ConsultasMariaDB.insertarSalariosEnNomina(conexionDB, empleados);

        //Se actualizan los datos del empleado con dni:32000032G y se modifica la categoría a 5 y la antigüedad a 8 años
        //ConsultasMariaDB.actualizarEmpleado(conexionDB, "32000032G", 5, 8.0);


        //Se actualiza a categoria: 9 el empleado con dni: 32000032G en la base de datos
//        CalculaNominas calculaNominas= new CalculaNominas();
//        Nomina nomina = new Nomina();
//        int nuevaCategoria = 9;
//        String dniBuscado = "32000032G";
//
//        List<Empleado> listaEmpleados = GestionArchivos.cargarEmpleadosDesdeArchivo();
//        Empleado empleadoBuscado = calculaNominas.buscarEmpleadoPorDni(listaEmpleados, dniBuscado);
//        double nuevoSalario = nomina.recalculaSueldoEmpleado(empleadoBuscado, nuevaCategoria, empleadoBuscado.getAnyos());
//
//        ConsultasMariaDB.actualizarEmpleado(conexionDB,empleadoBuscado.getDni(), nuevaCategoria, empleadoBuscado.getAnyos(), nuevoSalario);

        //Dar de alta a un empleado en la base de datos que tenga los siguientes valores: "Noelia Ballesteros", "12345678A", 'M', 10, 0.0;

//        Nomina nomina = new Nomina();
//        Empleado empleado = new Empleado("Noelia Ballesteros", "12345678A", 'M', 10, 0.0);
//        double salario = nomina.calcularsueldoEmpleado(empleado);
//        ConsultasMariaDB.altaEmpleado(conexionDB, empleado, salario);

        //




        /*
        //Se crea instancia de ConexionMariaDB
        ConexionMariaDB conexionDB = new ConexionMariaDB();

        //Se llama al método para leer el archivo y crear objetos Empleado
        List<Empleado> empleados = cargarEmpleadosDesdeArchivo();

        //Se calculan y guardan los sueldos en el archivo sueldos.dat
        calcularYguardarSueldosEnArchivoDat(empleados);

        //Se llama al método de inserción en la base de datos
        ConsultasMariaDB.insertarEmpleados(conexionDB, empleados);

        //Se registran los sueldos en la tabla Nomina de la base de datos
        ConsultasMariaDB.insertarSalariosEnNomina(conexionDB, empleados);

        //Se cierra la conexión a la base de datos
        conexionDB.cerrarConexion();
        */
    }
}

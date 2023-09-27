package laboral;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("El salario de " + empl.nombre + " es de: " + nomina.sueldo(empl));
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
        CalculaNominas calculaNominas = new CalculaNominas();

        Empleado empl1 = new Empleado("James Cosling", "32000032G", 'M', 4, 7.0);
        Empleado empl2 = new Empleado("Ada Lovelace", "32000031R", 'F');

        calculaNominas.escribe(empl1);
        calculaNominas.escribe(empl2);

        System.out.println("-------------------------");
        /* Se incrementa en 3 años al empleado 2 y se le asigna nueva categoría al empleado 1.
           Se recalcular sus nominas*/

        empl2.incrAnyo();
        empl2.incrAnyo();
        empl2.incrAnyo();
        empl1.setCategoria(9);

        calculaNominas.escribe(empl1);
        calculaNominas.escribe(empl2);

        System.out.println("-------------------------");

        // Se asigna categoría inexistente para comprobar que se controla la excepción de la categoria

        try {
            empl1.setCategoria(11);
            calculaNominas.escribe(empl1);

        } catch (DatosNoCorrectosException e) {
            System.out.println("DATOS NO CORRECTOS");
        }

        System.out.println("-------------------------");

        // Se asigna un año negativo para comprobar que se controla la excepción de los años trabajados

        try {
            empl2 = new Empleado("Adam Lovelace", "32000031R", 'M', 8, -3);
            calculaNominas.escribe(empl2);

        } catch (DatosNoCorrectosException e) {
            System.out.println("DATOS NO CORRECTOS");
        }

        System.out.println("-------------------------");

        /**
         * Este bloque de código realiza los siguientes pasos:
         * 1. Crea una lista de empleados para almacenar los datos de empleados leídos desde el archivo "empleado.txt".
         * 2. Abre el archivo "empleado.txt" para lectura y utiliza un Scanner para procesar las líneas del archivo.
         * 3. Para cada línea del archivo, divide la línea en partes utilizando ',' como delimitador.
         * 4. Verifica si la línea contiene 3 o 5 partes para asegurarse de que los datos del empleado sean completos.
         * 5. Si los datos son completos, crea un objeto Empleado y lo agrega a la lista de empleados.
         * 6. Si los datos no son completos, muestra un mensaje de error.
         * 7. Maneja cualquier excepción que pueda ocurrir durante el proceso.
         *
         * @param empleados Una lista de objetos Empleado que se llenará con los datos de empleados desde el archivo.
         */

        List<Empleado> empleados = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("empleado.txt"));

            while (sc.hasNextLine()) {

                String linea = sc.nextLine();
                String[] partes = linea.split(",");

                if (partes.length == 3 || partes.length == 5) {
                    String nombre = partes[0];
                    String dni = partes[1];
                    char sexo = partes[2].charAt(0);
                    int categoria;
                    double anyos;
                    if (partes.length == 5) {
                        categoria = Integer.parseInt(partes[3]);
                        anyos = Double.parseDouble(partes[4]);
                        Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
                        empleados.add(empleado);
                    } else {
                        Empleado empleado = new Empleado(nombre, dni, sexo);
                        empleados.add(empleado);
                    }
                } else {
                    System.out.println("Error de lectura: Los datos del empleado no están completos, revisa el documento 'empleado.txt");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------");

        /**
         * Este bloque de código realiza los siguientes pasos:
         * 1. Crea una instancia de la clase Nomina para calcular los salarios de los empleados.
         * 2. Crea un flujo de salida de datos binarios utilizando DataOutputStream para escribir en el archivo "sueldos.dat".
         * 3. Itera a través de la lista de empleados, calcula sus salarios y escribe los datos en el archivo "sueldos.dat".
         * 4. Maneja cualquier excepción que pueda ocurrir durante el proceso.
         */

        try {
            Nomina nomina = new Nomina();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("sueldos.dat"));

            for (Empleado empleado : empleados) {
                double salario = nomina.sueldo(empleado);
                dataOutputStream.writeUTF(empleado.dni);
                dataOutputStream.writeDouble(salario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package laboral;

import laboral.exceptions.DatosNoCorrectosException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionArchivos {

    public static List<Empleado> crearListaEmpleadosDesdeArchivo() throws DatosNoCorrectosException, FileNotFoundException {
        List<Empleado> empleados = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/laboral/files/empleados.txt"));
            String linea;

            while ((linea = br.readLine()) != null) {
                //Se divide la linea en campos tomando como referencia para la separación la coma
                String[] campos = linea.split(",");

                //Se crea un objeto Empleado a partir de los campos
                if (campos.length >= 3) {
                    String nombre = campos[0].trim();
                    String dni = campos[1].trim();
                    char sexo = campos[2].trim().charAt(0);

                    int categoria = 1;
                    double anyos = 0.0;

                    if (campos.length >= 4) {
                        categoria = Integer.parseInt(campos[3].trim());
                    }
                    if (campos.length >= 5) {
                        anyos = Double.parseDouble(campos[4].trim());
                    }
                    Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
                    empleados.add(empleado);

                    System.out.println("La lista de empleados se ha generado correctamente desde el archivo");
                } else {
                    System.out.println("Error de lectura: Los datos del empleado no están completos, revisa el documento 'empleado.txt");
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatosNoCorrectosException ex) {
            throw new RuntimeException(ex);
        }
        return empleados;
    }

    public static Empleado crearEmpleadoDesdeArchivo(String dni) throws DatosNoCorrectosException, IOException {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/laboral/files/empleados.txt"))) {
            String linea = br.readLine();
            while (linea != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3) {
                    String dniEmpleado = campos[1].trim();

                    if (dniEmpleado.equals(dni)) {
                        String nombre = campos[0].trim();
                        char sexo = campos[2].trim().charAt(0);
                        int categoria = 1;
                        double anyos = 0.0;

                        if (campos.length >= 4) {
                            categoria = Integer.parseInt(campos[3].trim());
                        }
                        if (campos.length >= 5) {
                            anyos = Double.parseDouble(campos[4].trim());
                        }
                        Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
                        System.out.println("el empleado con dni" + dni + " se ha obtenido correctamente desde el archivo.");
                        return empleado;
                    } else {
                        System.out.println("Error de lectura: Los datos del empleado no están completos. Revisa el documento empleados.txt");
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        } catch (DatosNoCorrectosException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public static void insertarSalariosEnArchivoDat(List<Empleado> empleados) {
        Nomina nomina = new Nomina();
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("sueldos.dat"))) {
            for (Empleado empleado : empleados) {
                double salario = nomina.calcularsueldoEmpleado(empleado);
                dataOutputStream.writeUTF(empleado.getDni());
                dataOutputStream.writeDouble(salario);
            }
            System.out.println("Sueldos calculados y guardados en el archivo sueldos.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarSueldoEmpleadoEnArchivoDat(Empleado empleado, String archivo) {
        try {
            Nomina nomina = new Nomina();
            String dni = empleado.getDni();
            String lineaNueva = empleado.getDni() + "," + nomina.calcularsueldoEmpleado(empleado);

            String lineaAntigua = recorrerArchivo(dni, archivo);
            if (lineaAntigua != null) {
                guardarCambiosArchivo(archivo, lineaNueva);
            }

        } catch (DatosNoCorrectosException e) {
            throw new RuntimeException(e);
        }
    }


    public void actualizarEmpleadoEnArchivo(Empleado empleado, String archivo) throws DatosNoCorrectosException {
        try {
            String dni = empleado.getDni();
            String lineaNueva = empleado.getNombre() + "," + dni + "," + empleado.getSexo() + "," + empleado.getCategoria()
                    + "," + empleado.getAnyos();

            String lineaAntigua = recorrerArchivo(dni, archivo);

            if (lineaAntigua != null) {
                guardarCambiosArchivo(archivo, lineaNueva);
                actualizarSueldoEmpleadoEnArchivoDat(empleado, archivo);
            } else {
                throw new DatosNoCorrectosException("El empleado con dni: " + dni + " no se encuentra en el archivo");
            }
        } catch (DatosNoCorrectosException e) {
            e.printStackTrace();
        }
    }

    public void actualizarAntiguedadDeEmpleadoEnArchivo(String dni, double nuevoAnyo, String archivo) throws IOException, DatosNoCorrectosException {
        try {
            String lineaEmpleado = recorrerArchivo(dni, archivo);

            if (lineaEmpleado != null) {
                //Se divide la linea en campos
                String[] campos = lineaEmpleado.split(",");
                String anyoAnterior = campos[4].trim();

                //Se remplaza la antiguedad por el nuevo valor
                campos[4] = String.valueOf(nuevoAnyo);

                //Se construye la linea actualizada
                String lineaNueva = String.join(",", campos);

                //Se remplaza la linea antigua por la nueva en el archivo
                guardarCambiosArchivo(archivo, lineaNueva);
            } else {
                throw new DatosNoCorrectosException("El empleado con dni: " + dni + " no se encuentra en el archivo sueldos.dat");
            }
        } catch (DatosNoCorrectosException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarCategoriaEnArchivo(String dni, int nuevaCategoria, String archivo) throws IOException, DatosNoCorrectosException {

        try {
            String lineaEmpleado = recorrerArchivo(dni, archivo);
            if (lineaEmpleado != null) {
                //Se divide la linea en campos
                String[] campos = lineaEmpleado.split(",");
                String categoriaAnterior = campos[3].trim();

                //Se remplaza la categoria por el nuevo valor
                campos[3] = String.valueOf(nuevaCategoria);

                //Se construye la linea actualizada
                String lineaNueva = String.join(",", campos);

                //Se remplaza la linea antigua por la nueva en el archivo
                guardarCambiosArchivo(archivo, lineaNueva);
            } else {
                throw new DatosNoCorrectosException("El empleado con dni: " + dni + " no se encuentra en el archivo sueldos.dat");
            }
        } catch (DatosNoCorrectosException e) {
            throw new RuntimeException(e);
        }
    }

    public static String recorrerArchivo(String dni, String archivo) throws DatosNoCorrectosException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            while (linea != null) {
                String[] campos = linea.split(",");

                if (campos.length >= 3) {
                    String dniEmpleado = campos[1].trim();
                    if (dniEmpleado.equals(dni)) {
                        return linea;
                    }
                }
            }
        } catch (Exception e) {
            throw new DatosNoCorrectosException("Este empleado con dni: " + dni + " no se encuentra registrado en el archivo");
        }
        return null;
    }

    public void escribirLineaEnArchivo(String lineaNueva, String archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(lineaNueva);
            bw.newLine();
        }
    }

    public void guardarCambiosArchivo(String archivo, String nuevaLinea) {
        try {
            escribirLineaEnArchivo(nuevaLinea, archivo);
            System.out.println("Los cambios en el archivo se han guardado");
        } catch (IOException e) {
            System.out.println("oh vaya! No se han podido guardar los cambios en el archivo.");
            e.printStackTrace();
        }

    }

}

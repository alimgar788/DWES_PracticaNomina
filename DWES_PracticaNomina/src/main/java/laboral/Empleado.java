package laboral;

/**
 * La clase Empleado extiende de la clase Persona. Un empleado tiene también una categoria y una antigüedad laboral
 */

public class Empleado extends Persona{
    private int categoria;
    public double anyos;

    /**
     * Constructor con todos los parámetros heredados de la clase Persona.
     * Por defecto se le asigna la categoría 1 y los años de antigüedad en 0
     * @param nombre
     * @param dni
     * @param sexo
     */
    public Empleado(String nombre, String dni, char sexo) {
        super(nombre, dni, sexo);
        categoria = 1;
        anyos = 0;
    }

    /**
     * Constructor con todos los parámetros. En este caso, en este constructor se controla que la asignación de los años
     * de antiguedad sean siempre igual o superior a 0.
     * @param nombre
     * @param dni
     * @param sexo
     * @param categoria
     * @param anyos
     * @throws DatosNoCorrectosException
     */
    public Empleado(String nombre, String dni, char sexo, int categoria, double anyos) throws DatosNoCorrectosException {
        super(nombre, dni, sexo);
        this.categoria = categoria;

        if (anyos <= 0) {
            throw new DatosNoCorrectosException("El año debe ser un número superior a 0");
        }
        this.anyos = anyos;
    }

    /**
     * Método que establece la categoría del empleado, en este método se controla que la categoría se encuentre
     * entre las categorías permitidas.
     * @param categoria
     * @throws DatosNoCorrectosException
     */
    public void setCategoria(int categoria) throws DatosNoCorrectosException {
        if (categoria >= 1 && categoria <= 10) {
            this.categoria = categoria;
        }else{
            throw new DatosNoCorrectosException("La categoría debe estar entre 1 y 10");
        }
    }

    /**
     * Método que devuelve la categoría del empleado
     * @return
     */
    public int getCategoria(){
        return categoria;
    }

    /**
     * Método que incrementa en 1 los años de antigüedad del empleado
     */
    public void incrAnyo(){
        anyos++;
    }

    /**
     * Método que imprime todos los datos del empleado
     */
    public void imprime(){
        System.out.println("Nombre: " + nombre + ", Dni: " + dni + ", Sexo: " + sexo + ", categoria: " + categoria + ", Años trabajados: " + anyos + ".");
    }
}

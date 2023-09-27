package laboral;

/**
 * La clase Persona representa a una persona con un nombre, Dni y sexo
 */

public class Persona {
    public String nombre, dni;
    public char sexo;

    /**
     * Constructor con todos los parámetros
     * @param nombre
     * @param dni
     * @param sexo
     */
    public Persona(String nombre, String dni, char sexo){
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    /**
     * Método que Permite establece el dni de la persona
     * @param dni
     */
    public void setDni(String dni){
        this.dni = dni;
    }

    /**
     * Método que imprime el nombre y el dni de la persona
     */
    public void imprime(){
        System.out.println("Nombre: " + nombre + ", Dni: " + dni + ".");
    }
}

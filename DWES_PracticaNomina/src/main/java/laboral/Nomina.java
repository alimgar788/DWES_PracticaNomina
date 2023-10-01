package laboral;

/**
 * La clase Nomina representa la nómina de un empleado.
 * Permite generar el salario del empleado en función de su categoría
 */

public class Nomina {
    private static final int SUELDO_BASE[] = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};

    /**
     * Método que genera el sueldo del empleado en función a su categoría y antigüedad
     * @param empl
     * @return
     */
    public double calcularsueldoEmpleado(Empleado empl){
        double salario = SUELDO_BASE[empl.getCategoria()-1] + 5000*empl.getAnyos();
        return salario;
    }

    public double recalculaSueldoEmpleado(Empleado empl, int nuevaCategoria, double nuevoAnyo){
        if(empl.getCategoria() != nuevaCategoria && empl.getAnyos()!= nuevoAnyo) {
            return SUELDO_BASE[nuevaCategoria - 1] + 5000 * nuevoAnyo;
        }else if(empl.getCategoria()!=nuevaCategoria){
            return SUELDO_BASE[nuevaCategoria - 1] + 5000 * empl.getAnyos();
        }else if(empl.getAnyos()!= nuevoAnyo){
            return SUELDO_BASE[empl.getCategoria() - 1] + 5000 * nuevoAnyo;
        }else{
            Nomina nomina = new Nomina();
            return nomina.calcularsueldoEmpleado(empl);
        }
    }
//    public double recalcularSueldoPorCategoria(Empleado empl, int nuevaCategoria){
//        Nomina nomina = new Nomina();
//        double salario = nomina.calcularsueldoEmpleado(empl);
//        if(empl.getCategoria()!= nuevaCategoria) {
//            salario = SUELDO_BASE[nuevaCategoria - 1] + 5000 * empl.getAnyos();
//        }
//        return salario;
//    }
//
//    public double recalcularSueldoPorAnyosTrabajados(Empleado empl, double nuevoAnyo){
//        Nomina nomina = new Nomina();
//        double salario = nomina.calcularsueldoEmpleado(empl);
//        if(empl.getAnyos()!= nuevoAnyo) {
//            salario = SUELDO_BASE[empl.getCategoria() - 1] + 5000 * nuevoAnyo;
//        }
//        return salario;
//    }

}

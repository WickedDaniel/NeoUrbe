package controlador;

import java.util.ArrayList;
import modelo.EstacionEnergia;

public class ControlEstaciones {
    private static final ArrayList<EstacionEnergia> estaciones = new ArrayList<>();;

    public ControlEstaciones(){
    }

    public boolean agregar(String ID, String descripcion, String calle, int capacidad){
        if (consultar(ID) != null) return false;
        EstacionEnergia nuevaEstacion = new EstacionEnergia(ID,descripcion,calle,capacidad);
        estaciones.add(nuevaEstacion);
        return true;
    }

    public static EstacionEnergia consultar(String ID){
        for (EstacionEnergia estacion : estaciones) {
            if (estacion.getID().equals(ID)) {
                return estacion;
            }
        }
        return null;
    }

    public ArrayList<EstacionEnergia> getEstaciones(){
        return estaciones;
    }

    public boolean modificar(String ID, EstacionEnergia estacionActualizada){
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionEnergia estacion = estaciones.get(i);
            if (estacion.getID().equals(ID)) {
                estaciones.set(i, estacionActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String ID){
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionEnergia estacion = estaciones.get(i);
            if (estacion.getID().equals(ID)) {
                estaciones.remove(i);
                return true;
            }
        }
        return false;
    }
}

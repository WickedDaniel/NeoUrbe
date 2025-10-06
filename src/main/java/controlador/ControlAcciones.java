package controlador;

import java.util.ArrayList;
import modelo.Accion;

public class ControlAcciones {
    private final static ArrayList<Accion> acciones = new ArrayList<>();;

    public ControlAcciones () {}

    public ArrayList<Accion> getAcciones() {
        return acciones;
    }
    public boolean agregar(String Descripcion){
        acciones.add(new Accion(Descripcion));
        return true;
    }

    public Accion consultar(int ID){
        for (Accion accion : acciones) {
            if (accion.getID() == ID) {
                return accion;
            }
        }
        return null;
    }

    public boolean modificar(int ID, Accion accionActualizada){
        for (int i = 0; i < acciones.size(); i++) {
            Accion accion = acciones.get(i);
            if (accion.getID() == ID) {
                acciones.set(i, accionActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int ID){
        for (int i = 0; i < acciones.size(); i++) {
            Accion accion = acciones.get(i);
            if (accion.getID() == ID) {
                acciones.remove(i);
                return true;
            }
        }
        return false;
    }
}

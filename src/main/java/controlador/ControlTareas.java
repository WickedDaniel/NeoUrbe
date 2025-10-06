package controlador;

import java.util.ArrayList;
import modelo.Tarea;

public class ControlTareas {
    private static final ArrayList<Tarea> tareas = new ArrayList<>();;

    public ControlTareas(){
    }

    public ArrayList<Tarea> obtenerTareas() {
        return tareas;
    }
    public boolean agregar(String descripcion, int consumo){
        tareas.add(new Tarea(descripcion, consumo));
        return true;
    }

    public Tarea consultar(int ID){
        for (Tarea tarea : tareas) {
            if (tarea.getID()==ID) {
                return tarea;
            }
        }
        return null;
    }

    public boolean modificar(int ID, Tarea tareaActualizada){
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
            if (tarea.getID()==ID) {
                tareas.set(i, tareaActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int ID){
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
            if (tarea.getID()==ID) {
                tareas.remove(i);
                return true;
            }
        }
        return false;
    }
}

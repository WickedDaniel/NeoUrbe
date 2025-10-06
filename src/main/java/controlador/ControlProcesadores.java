package controlador;

import java.util.ArrayList;
import modelo.Procesador;

public class ControlProcesadores {
    private static final ArrayList<Procesador> procesadores = new ArrayList<>();;

    public ControlProcesadores(){}

    public Procesador agregar(){
        Procesador procesador = new Procesador();
        procesadores.add(procesador);
        return procesador;
    }

    public Procesador consultar(int ID){
        for (Procesador procesador : procesadores) {
            if (procesador.getID() == ID) {
                return procesador;
            }
        }
        return null;
    }

    public boolean modificar(int ID, Procesador procesadorActualizado){
        for (int i = 0; i < procesadores.size(); i++) {
            Procesador procesador = procesadores.get(i);
            if (procesador.getID() == ID) {
                procesadores.set(i, procesadorActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int ID){
        for (int i = 0; i < procesadores.size(); i++) {
            Procesador procesador = procesadores.get(i);
            if (procesador.getID() == ID) {
                procesadores.remove(i);
                return true;
            }
        }
        return false;
    }
}

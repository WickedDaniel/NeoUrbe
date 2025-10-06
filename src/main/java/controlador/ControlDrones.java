package controlador;

import java.util.ArrayList;
import modelo.DroneVigilancia;
import modelo.EdificioInteligente;
import modelo.Procesador;

public class ControlDrones {
    private static ArrayList<DroneVigilancia> drones = new ArrayList<>();

    public ControlDrones (){
    }

    public ArrayList<DroneVigilancia> getDrones() {
        return drones;
    }

    public boolean agregar(Procesador procesador, EdificioInteligente edificio){
        drones.add(new DroneVigilancia(procesador,edificio));
        return true;
    }

    public DroneVigilancia consultar(Procesador CPU){
        for (DroneVigilancia drone : drones) {
            if (drone.getCPU().equals(CPU)) {
                return drone;
            }
        }
        return null;
    }

    public boolean modificar(Procesador CPU, DroneVigilancia droneActualizado){
        for (int i = 0; i < drones.size(); i++) {
            DroneVigilancia drone = drones.get(i);
            if (drone.getCPU().equals(CPU)) {
                drones.set(i, droneActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(Procesador CPU){
        for (int i = 0; i < drones.size(); i++) {
            DroneVigilancia drone = drones.get(i);
            if (drone.getCPU().equals(CPU)) {
                drones.remove(i);
                return true;
            }
        }
        return false;
    }
}

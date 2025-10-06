package controlador;

import java.util.ArrayList;
import modelo.Ciudadano;
import modelo.EdificioInteligente;

public class ControlCiudadanos {
    private final static ArrayList<Ciudadano> ciudadanos = new ArrayList<>();;

    public ControlCiudadanos (){
    }

    public boolean agregar(EdificioInteligente edificio){
        if (edificio != null) {
            if (edificio.getCiudadanos().size() >= edificio.getCapacidad()) return false;
            Ciudadano newCiudadano = new Ciudadano(edificio);
            ciudadanos.add(newCiudadano);
            edificio.AsignarCiudadano(newCiudadano);
            return true;
        }
        return false;
    }

    public ArrayList<Ciudadano> getCiudadanos(){
        return ciudadanos;
    }

    public boolean agregar(EdificioInteligente edificio, int cantidad){
        if (edificio != null) {
            if (edificio.getCiudadanos().size() >= edificio.getCapacidad()) return false;
            for (int i = 0; i<cantidad; i++){
                if (edificio.getCiudadanos().size() >= edificio.getCapacidad()) return true;
                Ciudadano newCiudadano = new Ciudadano(edificio);
                ciudadanos.add(newCiudadano);
                edificio.AsignarCiudadano(newCiudadano);
            }
            return true;
        }
        return false;
    }

    public Ciudadano consultar(int ID){
        for (Ciudadano ciudadano : ciudadanos) {
            if (ciudadano.getID() == ID) {
                return ciudadano;
            }
        }
        return null;
    }

    public boolean modificar(int ID, Ciudadano ciudadanoActualizado){
        for (int i = 0; i < ciudadanos.size(); i++) {
            Ciudadano ciudadano = ciudadanos.get(i);
            if (ciudadano.getID() == ID) {
                ciudadanos.set(i, ciudadanoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int ID){
        Ciudadano ciudadano = consultar(ID);
        if (ciudadano == null) return false;
        ciudadanos.remove(ciudadano);
        ciudadano.getEdificio().RemoverCiudadano(ciudadano);
        return true;
    }
}

package controlador;

import java.util.ArrayList;
import modelo.EdificioInteligente;

public class ControlEdificios {
    private static final ArrayList<EdificioInteligente> edificios = new ArrayList<>();;

    public ControlEdificios(){
    }

    public EdificioInteligente agregar(String ID, String nombre,  String calle, int capacidad){
        if (consultar(ID) != null) return null;
        EdificioInteligente nuevoEdificio = new EdificioInteligente(ID, nombre, calle, capacidad);
        edificios.add(nuevoEdificio);
        return nuevoEdificio;
    }

    public ArrayList<EdificioInteligente> getEdificios(){
        return edificios;
    }

    public static EdificioInteligente consultar(String ID){
        for (EdificioInteligente edificio : edificios) {
            if (edificio.getID().equals(ID)) {
                return edificio;
            }
        }
        return null;
    }

    public boolean modificar(String ID, EdificioInteligente edificioActualizado){
        for (int i = 0; i < edificios.size(); i++) {
            EdificioInteligente edificio = edificios.get(i);
            if (edificio.getID().equals(ID)) {
                edificios.set(i, edificioActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String ID){
        for (int i = 0; i < edificios.size(); i++) {
            EdificioInteligente edificio = edificios.get(i);
            if (edificio.getID().equals(ID)) {
                edificios.remove(i);
                return true;
            }
        }
        return false;
    }
}

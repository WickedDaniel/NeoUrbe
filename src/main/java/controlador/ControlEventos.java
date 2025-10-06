package controlador;

import java.util.ArrayList;
import modelo.Evento;

public class ControlEventos {
    private final static ArrayList<Evento> eventos = new ArrayList<>();;

    public ControlEventos(){
    }

    public boolean agregar(String descripcion){
        Evento evento = new Evento(descripcion);
        eventos.add(evento);
        return false;
    }

    public ArrayList<Evento> getEventos(){
        return eventos;
    }

    public Evento consultar(int ID){
        for (Evento evento : eventos) {
            if (evento.getID() == ID) {
                return evento;
            }
        }
        return null;
    }

    public boolean modificar(int ID, Evento eventoActualizado){
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento.getID() == ID) {
                eventos.set(i, eventoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(int ID){
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento.getID() == ID) {
                eventos.remove(i);
                return true;
            }
        }
        return false;
    }
}

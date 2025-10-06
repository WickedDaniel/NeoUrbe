package controlador.Perfiles;

import controlador.*;
import modelo.*;

public class PerfilAdministrador {
    private ControlEdificios controlEdificios;
    private ControlEstaciones controlEstaciones;
    private ControlEventos controlEventos;
    private ControlAcciones controlAcciones;
    private ControlProcesadores controlProcesadores;
    private ControlDrones controlDrones;
    private ControlReglas controlReglas;
    public PerfilAdministrador() {
        controlEdificios = new ControlEdificios();
        controlEstaciones = new ControlEstaciones();
        controlEventos = new ControlEventos();
        controlAcciones = new ControlAcciones();
        controlProcesadores = new ControlProcesadores();
        controlDrones = new ControlDrones();
        controlReglas = new ControlReglas();
    }

    public boolean crearEstacion(String ID, String descripcion, String calle, int capacidad) {
        return controlEstaciones.agregar(ID, descripcion, calle, capacidad);
    }
    public boolean crearEdificio(String ID, String nombre, String calle, int capacidad) {
        Procesador newProcesadorA = controlProcesadores.agregar();
        Procesador newProcesadorB = controlProcesadores.agregar();
        EdificioInteligente newEdificio = controlEdificios.agregar(ID, nombre, calle, capacidad);
        if (newProcesadorA == null || newProcesadorB == null) return false;
        if (newEdificio == null) return false;
        controlDrones.agregar(newProcesadorA, newEdificio);
        controlDrones.agregar(newProcesadorB, newEdificio);
        return true;
    }
    public boolean actualizarEstadoEstacion(String ID, TEstadoEstacion Estado) {
        EstacionEnergia estacion = controlEstaciones.consultar(ID);
        if (estacion == null) return false;
        estacion.setEstado(Estado);
        return true;
    }
    public boolean registrarAccionPorEvento(int eventoID, int accionID) {
        Accion accion = controlAcciones.consultar(accionID);
        Evento evento = controlEventos.consultar(eventoID);
        return evento.RegistrarRespuesta(accion);
    }
    public boolean removeAccionPorEvento(int eventoID, int accionID) {
        Accion accion = controlAcciones.consultar(accionID);
        Evento evento = controlEventos.consultar(eventoID);
        return evento.RegistrarRespuesta(accion);
    }
    public boolean habilitarRegla(int ID) {
        Regla regla = controlReglas.consultar(ID);
        regla.habilitar();
        return true;
    }
    public boolean deshabilitarRegla(int ID) {
        Regla regla = controlReglas.consultar(ID);
        regla.deshabilitar();
        return true;
    }
}

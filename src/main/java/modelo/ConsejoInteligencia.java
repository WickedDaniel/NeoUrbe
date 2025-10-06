package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class ConsejoInteligencia {
    private static ArrayList<Evento> Eventos =  new ArrayList<Evento>();
    private static ArrayList<Registro> Historial = new ArrayList<>();
    public ConsejoInteligencia() {}

    public static void ResolverAnomalia(DroneVigilancia drone, Evento evento) {
        for (Accion accion: evento.getRespuesta()) {
            RegistrarReporte(drone, evento, accion);
        }
     }

    public static void RegistrarReporte(DroneVigilancia drone, Evento evento, Accion accion) {
        Registro newReport = new Registro(evento, accion, drone, LocalDate.now());
        Historial.add(newReport);
    }

    public static ArrayList<Registro> getHistorial() {
        return Historial;
    }
}

package modelo;

import java.util.ArrayList;

public class Reglas {
    private final static ArrayList<Regla> reglasEstablecidas = new ArrayList<>();

    private Reglas() {}
    public static void inicializarReglas() {
        if (!reglasEstablecidas.isEmpty()) return;
        reglasEstablecidas.add(new Regla(1, "Alerta de bateria Drone", false));
        reglasEstablecidas.add(new Regla(2, "Alerta de bateria Robot", false));
    }
    public static ArrayList<Regla> obtenerReglas() {
        return reglasEstablecidas;
    }
    public static void habilitarRegla(Regla regla) {
        regla.habilitar();
    }
    public static void deshabilitarRegla(Regla regla) {
        regla.deshabilitar();
    }
}

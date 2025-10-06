package controlador;

import modelo.Regla;
import modelo.Reglas;
import java.util.ArrayList;


public class ControlReglas {
    public ControlReglas() {}
    public ArrayList<Regla> obtenerReglas() {
        return Reglas.obtenerReglas();
    }
    public Regla consultar(int ID) {
        for(Regla regla : this.obtenerReglas()) {
            if(regla.getID() != ID) continue;
            return regla;
        };
        return null;
    }

    public void habilitar(Regla regla) {
        regla.habilitar();
    }
    public void deshabilitar(Regla regla) {
        regla.deshabilitar();
    }
}

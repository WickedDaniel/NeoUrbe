package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class EdificioInteligente {
    private final String ID;
    private String Nombre;
    private String Ubicacion;
    private int Capacidad;

    private ArrayList<Ciudadano> Ciudadanos;

    public EdificioInteligente(String ID, String Nombre, String Ubicacion, int Capacidad) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Ubicacion = Ubicacion;
        this.Capacidad = Capacidad;
        this.Ciudadanos = new ArrayList<>();
    }
    public boolean AsignarCiudadano(Ciudadano ciudadano){
        if (ciudadano == null || Ciudadanos.size() >= Capacidad) {return false;}
        for (Ciudadano c : Ciudadanos) {
            if (ciudadano.equals(c)) {
                return false;
            }
        }
        Ciudadanos.add(ciudadano);
        return true;
    }
    public boolean RemoverCiudadano(Ciudadano ciudadano){
        if (ciudadano == null) return false;
        for (Ciudadano c : Ciudadanos) {
            if (ciudadano.equals(c)) {
                Ciudadanos.remove(c);
                return true;
            }
        }
        return false;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<Ciudadano> getCiudadanos() {
        return Ciudadanos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "EdificioInteligente{" +
                "ID='" + ID + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Ubicacion='" + Ubicacion + '\'' +
                ", Capacidad=" + Capacidad +
                ", Ciudadanos=" + Ciudadanos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EdificioInteligente that = (EdificioInteligente) o;
        return Capacidad == that.Capacidad && Objects.equals(ID, that.ID) && Objects.equals(Nombre, that.Nombre) && Objects.equals(Ubicacion, that.Ubicacion) && Objects.equals(Ciudadanos, that.Ciudadanos);
    }
}
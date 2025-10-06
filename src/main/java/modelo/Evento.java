package modelo;

import java.util.ArrayList;

public class Evento {
    private final int ID;
    private String Descripcion;
    private static int Consecutivo = 0;

    private ArrayList<Accion> respuesta =  new ArrayList<>();


    public Evento(String descripcion){
        this.Descripcion = descripcion;
        Consecutivo++;
        ID = Consecutivo;
    }

    public int getID() { return ID; }

    public boolean EjecutarRespuesta(){
        for (Accion accion : respuesta){
            if (accion.Ejecutar()){
                return true;
            }
        }
        return false;
    }

    public boolean RegistrarRespuesta(Accion accion) {
        if (respuesta.contains(accion)) return false;
        respuesta.add(accion);
        return true;
    }

    public boolean RemoverRespuesta(Accion accion) {
        if (!respuesta.contains(accion)) return false;
        respuesta.remove(accion);
        return true;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public static int getConsecutivo() {
        return Consecutivo;
    }

    public static void setConsecutivo(int consecutivo) {
        Consecutivo = consecutivo;
    }

    public ArrayList<Accion> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(ArrayList<Accion> respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "Evento +"+ID+"{"+"Descripcion=" + Descripcion + '}';
    }
}

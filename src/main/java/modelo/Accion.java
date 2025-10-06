package modelo;

public class Accion {
    private final int ID;
    private  String descripcion;
    private static int consecutivo = 0;

    public Accion(String descripcion){
        this.descripcion = descripcion;
        consecutivo++;
        ID = consecutivo;
    }
    public boolean Ejecutar(){
        return true;
    }

    public int getID() {
        return ID;
    }
    public String getDescription() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "ID=" + ID +
                "Description=" + descripcion +
                '}';
    }

}

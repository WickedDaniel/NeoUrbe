package modelo;

public class Tarea {
    private int ID;
    private String Descripcion;
    private int Consumo;
    private static int Consecutivo = 0;

    public Tarea(String descripcion, int consumo) {
        Consecutivo++;
        this.ID = Consecutivo;
        this.Descripcion = descripcion;
        this.Consumo = consumo;
    }

    public int getID() {
        return ID;
    }

    public int getConsumo() {
        return Consumo;
    }

    public void setConsumo(int consumo) {
        this.Consumo = consumo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "ID=" + ID +
                ", Descripcion='" + Descripcion + '\'' +
                ", Consumo=" + Consumo +
                '}';
    }
}

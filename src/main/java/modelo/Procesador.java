package modelo;

public class Procesador {
    private final int ID;
    private static int Consecutivo = 0;

    public Procesador(){
        Consecutivo++;
        ID = Consecutivo;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Procesador{" +
                "ID=" + ID +
                '}';
    }

}

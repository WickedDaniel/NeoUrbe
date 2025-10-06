package modelo;

public class Regla {
    private Integer ID;
    private String Description;
    private boolean Estado;

    public Regla(Integer ID, String Description, boolean Estado) {
        this.ID = ID;
        this.Description = Description;
        this.Estado = Estado;
    }

    public void habilitar() {
        this.Estado = true;
    }
    public void deshabilitar() {
        this.Estado = false;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    @Override
    public String toString() {
        return "Regla{" + "ID=" + ID + ", Description=" + Description + ", Estado=" + Estado + '}';
    }
}

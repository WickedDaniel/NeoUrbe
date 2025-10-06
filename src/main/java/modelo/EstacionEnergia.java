package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class EstacionEnergia {
    private final String ID;
    private String Descripcion;
    private String Ubicacion;
    private Integer Capacidad;
    private Integer Ocupacion;
    private TEstadoEstacion estado;
    private ArrayList<Registro> historial;

    public EstacionEnergia(String ID, String Descripcion, String Ubicacion, int Capacidad) {
        this.ID = ID;
        this.Descripcion = Descripcion;
        this.Ubicacion = Ubicacion;
        this.Capacidad = Capacidad;
        this.Ocupacion = 0;
        this.historial = new ArrayList<>();
    }

    public boolean AtenderRobot(RobotAsistente robot) {
        if (robot == null) { return false; }
        robot.RecargarBateria();
        historial.add(new Registro(this, robot, LocalDate.now()));
        return true;
    }

    public boolean AtenderDrone(DroneVigilancia dronVigilancia){
        if (dronVigilancia == null) { return false; }
        if (Ocupacion >= Capacidad) { return false; }

        dronVigilancia.RecargarBateria();
        dronVigilancia.setEnEstacion(this);

        historial.add(new Registro(this, dronVigilancia, LocalDate.now()));
        return true;
    }

    public boolean RetirarDrone(DroneVigilancia dronVigilancia){
        if (dronVigilancia == null) { return false; }
        if (dronVigilancia.getEnEstacion() == null) { return false; }
        if (!dronVigilancia.getEnEstacion().equals(this)) { return false; }
        dronVigilancia.setEnEstacion(null);
        Capacidad = Capacidad--;
        return true;
    }


    public TEstadoEstacion getEstado() {
        return estado;
    }

    public String getID() {
        return ID;
    }

    public String getNombre() {
        return Descripcion;
    }

    public void setNombre(String nombre) {
        Descripcion = nombre;
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

    public void setEstado(TEstadoEstacion estado) {
        this.estado = estado;
    }

    public ArrayList<Registro> getHistorial() {
        return historial;
    }
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setCapacidad(Integer capacidad) {
        Capacidad = capacidad;
    }

    public Integer getOcupacion() {
        return Ocupacion;
    }

    public void setOcupacion(Integer ocupacion) {
        Ocupacion = ocupacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EstacionEnergia that = (EstacionEnergia) o;
        return Capacidad == that.Capacidad && Objects.equals(ID, that.ID) && Objects.equals(Descripcion, that.Descripcion) && Objects.equals(Ubicacion, that.Ubicacion) && Objects.equals(estado, that.estado) && Objects.equals(historial, that.historial);
    }

    @Override
    public String toString() {
        return "EstacionEnergia{" +
                "ID='" + ID + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Ubicacion='" + Ubicacion + '\'' +
                ", Capacidad=" + Capacidad +
                ", estado=" + estado +
                ", historial=" + historial +
                '}';
    }
}


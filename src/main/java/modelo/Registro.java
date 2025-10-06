package modelo;

import java.time.LocalDate;

public class Registro {
    private Tarea DatosTarea;
    private DroneVigilancia DatosDrone;
    private RobotAsistente DatosRobot;
    private Evento DatosEvento;
    private LocalDate Fecha;
    private Accion DatosAccion;
    private EstacionEnergia DatosEstacion;

    Registro(){}
    Registro(Tarea tarea, LocalDate fechaCompleta) {
        this.DatosTarea = tarea;
        this.Fecha = fechaCompleta;
    }
    Registro(EstacionEnergia estacion,RobotAsistente clanker, LocalDate fechaCompleta) {
        this.DatosEstacion = estacion;
        this.Fecha = fechaCompleta;
        this.DatosRobot = clanker;
    }
    Registro(EstacionEnergia estacion, DroneVigilancia clanker, LocalDate fechaCompleta) {
        this.DatosEstacion = estacion;
        this.Fecha = fechaCompleta;
        this.DatosDrone = clanker;
    }
    Registro(Evento evento, Accion accion, DroneVigilancia drone, LocalDate fechaCompleta) {
        this.DatosEvento = evento;
        this.DatosAccion = accion;
        this.Fecha = fechaCompleta;
        this.DatosDrone = drone;
    }

    public Accion getDatosAccion() {
        return DatosAccion;
    }

    public void setDatosAccion(Accion datosAccion) {
        DatosAccion = datosAccion;
    }

    public EstacionEnergia getDatosEstacion() {
        return DatosEstacion;
    }

    public void setDatosEstacion(EstacionEnergia datosEstacion) {
        DatosEstacion = datosEstacion;
    }

    public Tarea getDatosTarea() {
        return DatosTarea;
    }

    public void setDatosTarea(Tarea datosTarea) {
        DatosTarea = datosTarea;
    }

    public DroneVigilancia getDatosDrone() {
        return DatosDrone;
    }

    public void setDatosDrone(DroneVigilancia datosDrone) {
        DatosDrone = datosDrone;
    }

    public RobotAsistente getDatosRobot() {
        return DatosRobot;
    }

    public void setDatosRobot(RobotAsistente datosRobot) {
        DatosRobot = datosRobot;
    }

    public Evento getDatosEvento() {
        return DatosEvento;
    }

    public void setDatosEvento(Evento datosEvento) {
        DatosEvento = datosEvento;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    @Override
    public String toString() {
        if  (DatosTarea != null) {
            return "Registro{"+"DatosTarea="+DatosTarea.toString()+", Fecha=+"+Fecha+"}";
        }
        if  (DatosDrone != null && DatosEvento != null && DatosAccion != null) {
            return "Registro{"+"DatosDrone="+DatosDrone.toString()+", "+"DatosEvento="+DatosEvento.getDescripcion()+", Respuesta="+DatosAccion.getDescription()+", Fecha="+ Fecha+"}";
        }
        if  (DatosRobot != null &&  DatosEstacion != null) {
            return "Registro{"+"DatosRobot="+DatosRobot.toString()+", Fecha="+Fecha.toString()+"}";
        }
        if (DatosDrone != null && DatosEstacion != null) {
            return "Registro{"+"DatosDrone="+DatosDrone.toString()+", Fecha="+Fecha.toString()+"}";
        }
        return null;
    }
}

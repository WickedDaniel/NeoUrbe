package modelo;

import controlador.ControlTareas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class RobotAsistente {
    private Procesador CPU;
    private int Bateria;
    private ArrayList<Tarea> Tareas;
    private TEstadoRobots Estado;
    private ArrayList<Registro> Historial;
    private static int BateriaMinima;
    private EstacionEnergia EnEstacion;
    Random random= new Random();

    public RobotAsistente(Procesador CPU) {
        Tareas = new ArrayList<>();
        Historial = new ArrayList<>();
        Bateria =  random.nextInt(20,101);
        this.CPU = CPU;
        Estado = TEstadoRobots.ACTIVO;
    }

    public void agregarTarea(Tarea tarea){
        Tareas.add(tarea);
    }
    public boolean RevisarSistemas(){
        if (Bateria <= BateriaMinima)
            Estado = TEstadoRobots.ALERTA;
        return true;
    }

    public boolean RealizarTarea(Tarea tarea){
        if (Bateria - tarea.getConsumo() < 0) {
            RevisarSistemas();
            return false;
        }
        if (Bateria <= BateriaMinima || getEstado() == TEstadoRobots.ALERTA) {
            RevisarSistemas();
            return false;
        }
        for (Tarea tareaActual: Tareas) {
            if (tareaActual.equals(tarea)) {
                Bateria = Bateria - tarea.getConsumo();
                Historial.add(new Registro(tarea, LocalDate.now()));
                RevisarSistemas();
                return true;
            }
        }
        return false;
    }
    public boolean ReducirBateria(int porcentaje){
        if (porcentaje <= Bateria)
            return false;
        Bateria = Bateria - porcentaje;
        return true;
    }
    public boolean RecargarBateria(){
        Bateria = 100;
        return true;
    }

    public Procesador getCPU() {
        return CPU;
    }

    public int getBateria() {
        return Bateria;
    }

    public ArrayList<Tarea> getTareas() {
        return Tareas;
    }

    public ArrayList<Registro> getHistorial() {
        return Historial;
    }

    public static int getBateriaMinima() {
        return BateriaMinima;
    }
    public static void setBateriaMinima(int bateriaMinima) {
        RobotAsistente.BateriaMinima = bateriaMinima;
    }

    public void setBateria(int bateria) {
        this.Bateria = bateria;
    }

    public TEstadoRobots getEstado() {
        return Estado;
    }

    public void setEstado(TEstadoRobots estado) {
        this.Estado = estado;
    }

    @Override
    public String toString() {
        return "RobotAsistente{" +
                "Cpu=" + CPU +
                ", Bateria=" + Bateria +
                ", Tareas=" + Tareas +
                ", Estado=" + Estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RobotAsistente that = (RobotAsistente) o;
        return Bateria == that.Bateria && Objects.equals(CPU, that.CPU) && Objects.equals(Tareas, that.Tareas) && Objects.equals(Estado, that.Estado) && Objects.equals(Historial, that.Historial);
    }
}

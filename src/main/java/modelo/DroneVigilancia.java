package modelo;

import controlador.ControlEventos;

import java.util.Objects;
import java.util.Random;

public class DroneVigilancia {
    private Procesador CPU;
    private EdificioInteligente Edificio;
    private EstacionEnergia EnEstacion;
    private int Bateria;
    private int CapacidadVuelo;
    private TEstadoRobots Estado;
    private static int BateriaMinima;

    public DroneVigilancia(Procesador cpu, EdificioInteligente edificio){
        CPU = cpu;
        Edificio = edificio;
        CapacidadVuelo = new Random().nextInt(1,5);
        Bateria = 25*CapacidadVuelo;
        Estado = TEstadoRobots.ACTIVO;
    }

    public boolean RevisarSistemas(){
        if (Bateria <= BateriaMinima) {
            Estado = TEstadoRobots.ALERTA;
            return false;
        }
        return true;
    }
    private Evento GenerarEvento() {
        ControlEventos eventos = new ControlEventos();
        return eventos.getEventos().get(new Random().nextInt(eventos.getEventos().size()));
    }
    public Evento Patrullar(){
        if (Bateria - 25 < 0) return null;

        Bateria = Bateria - 25;
        RevisarSistemas();
        return GenerarEvento();
    }
    public boolean Reportar(){
        return false;
    }
    public boolean ReducirBateria(int porcentaje){
        if (porcentaje <= Bateria)
            return false;
        Bateria = Bateria - porcentaje;
        return true;
    }

    public boolean RecargarBateria(){
        Bateria = 25*CapacidadVuelo;
        return true;
    }

    public Procesador getCPU() {
        return CPU;
    }

    public EdificioInteligente getEdificio() {
        return Edificio;
    }

    public int getCapacidadVuelo() {
        return CapacidadVuelo;
    }

    public static int getBateriaMinima() {
        return BateriaMinima;
    }
    public static void setBateriaMinima(int bateriaMinima) {
        BateriaMinima = bateriaMinima;
    }

    public void setEdificio(EdificioInteligente edificio) {
        this.Edificio = edificio;
    }

    public TEstadoRobots getEstado() {
        return Estado;
    }

    public void setEstado(TEstadoRobots estado) {
        this.Estado = estado;
    }

    public int getBateria() {
        return Bateria;
    }

    public void setBateria(int bateria) {
        this.Bateria = bateria;
    }

    public EstacionEnergia getEnEstacion() {
        return EnEstacion;
    }

    public void setEnEstacion(EstacionEnergia enEstacion) {
        EnEstacion = enEstacion;
    }

    @Override
    public String toString() {
        return "DroneVigilancia{" +
                "cpu=" + CPU +
                ", edificio=" + Edificio.getNombre() +
                ", bateria=" + Bateria +
                ", capacidadVuelo=" + CapacidadVuelo +
                ", estado=" + Estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DroneVigilancia that = (DroneVigilancia) o;
        return Bateria == that.Bateria && CapacidadVuelo == that.CapacidadVuelo && Objects.equals(CPU, that.CPU) && Objects.equals(Edificio, that.Edificio) && Objects.equals(Estado, that.Estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CPU, Edificio, Bateria, CapacidadVuelo, Estado);
    }
}

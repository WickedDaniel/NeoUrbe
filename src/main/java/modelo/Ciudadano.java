package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Ciudadano {
    private static int consecutivo = 0;
    private final int ID;
    EdificioInteligente edificio;

    private ArrayList<RobotAsistente> robotsAsignados;

    public Ciudadano(EdificioInteligente edificio){
        this.edificio = edificio;
        consecutivo++;
        this.ID = consecutivo;
        this.robotsAsignados = new ArrayList<>();
    }
    public boolean AsignarRobot(RobotAsistente robot){
        if (robotsAsignados.contains(robot)) return false;
        robotsAsignados.add(robot);
        return true;
    }
    public boolean RemoverRobot(RobotAsistente robot){
        if (!robotsAsignados.contains(robot)) return false;
        robotsAsignados.remove(robot);
        return true;
    }

    public int getID() {
        return ID;
    }

    public EdificioInteligente getEdificio() {
        return edificio;
    }

    public void setEdificio(EdificioInteligente edificio) {
        this.edificio = edificio;
    }

    public ArrayList<RobotAsistente> getRobotsAsignados() {
        return robotsAsignados;
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "edificio=" + edificio.getNombre() +
                ", robotsAsignados=" + robotsAsignados +
                ", ID=" + ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ciudadano ciudadano = (Ciudadano) o;
        return ID == ciudadano.ID && Objects.equals(edificio, ciudadano.edificio) && Objects.equals(robotsAsignados, ciudadano.robotsAsignados);
    }
}

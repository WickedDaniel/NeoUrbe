package controlador;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.robot.Robot;
import modelo.DroneVigilancia;
import modelo.RobotAsistente;
import modelo.Procesador;
import modelo.Tarea;

public class ControlRobots {
    private static final ArrayList<RobotAsistente> robots = new ArrayList<>();

    public ControlRobots (){
    }

    public ArrayList<RobotAsistente> getRobots(){
        return robots;
    }
    public RobotAsistente agregar(Procesador procesador){
        RobotAsistente nuevoRobot = new RobotAsistente(procesador);
        ControlTareas controlTareas = new ControlTareas();
        for (Tarea tarea: controlTareas.obtenerTareas()) {
            boolean randomBoolean =  new Random().nextBoolean();
            if (!randomBoolean) continue;
            nuevoRobot.agregarTarea(tarea);
        }
        robots.add(nuevoRobot);
        return nuevoRobot;
    }

    public RobotAsistente consultar(Procesador CPU){
        for (RobotAsistente robot : robots) {
            if (robot.getCPU().equals(CPU)) {
                return robot;
            }
        }
        return null;
    }

    public boolean modificar(Procesador CPU, RobotAsistente robotActualizado){
        for (int i = 0; i < robots.size(); i++) {
            RobotAsistente robot = robots.get(i);
            if (robot.getCPU().equals(CPU)) {
                robots.set(i, robotActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(Procesador CPU){
        for (int i = 0; i < robots.size(); i++) {
            RobotAsistente robot = robots.get(i);
            if (robot.getCPU().equals(CPU)) {
                robots.remove(i);
                return true;
            }
        }
        return false;
    }
}

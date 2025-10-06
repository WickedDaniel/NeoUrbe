package com.poo.neourbe;

import controlador.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.*;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.util.*;

public class generalControlador implements Initializable {

    // Botones principales
    public Button actualizarDashboard;
    public Button cambiarPerfil;

    // Tab Energía - Robots
    public TextField robotsEnAlerta;
    public TextField robotsPorcentajeAlerta;

    // Tab Energía - Drones
    public TextField dronesEnAlerta;
    public TextField dronesPorcentajeAlerta;

    // Tab Energía - Desgloses
    public ListView<String> energiaEdificios;
    public ListView<String> energiaTareas;

    // Tab Energia - Grafico
    public StackedBarChart<String, Number> barChartEnergia;

    // Tab Infraestructura
    public TextField estacionesDisponibles;
    public TextField estacionesPorcentaje;
    public ListView<String> infraestructuraEstaciones;

    // Tab Seguridad
    public TextField edificiosImpactados;
    public TextField ciudadanosAfectados;
    public ListView<String> seguridadIncidentes;
    public ListView<String> seguridadAcciones;
    public TextField edificioReincidencia;

    // Tab Bienestar Ciudadano
    public TextField ciudadanosConRobot;
    public TextField relacionRobotsCiudadano;
    public TextField ciudadanosSinRobotCantidad;
    public TextField ciudadanosSinRobotPorcentaje;
    public ListView<String> topOcupacion;
    public ListView<String> topSinRobot;
    public ListView<String> topRobotsAlerta;

    private ControlCiudadanos controlCiudadanos;
    private ControlRobots controlRobots;
    private ControlDrones controlDrones;
    private ControlEdificios controlEdificios;
    private ControlEstaciones controlEstaciones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar controladores
        controlCiudadanos = new ControlCiudadanos();
        controlRobots = new ControlRobots();
        controlDrones = new ControlDrones();
        controlEdificios = new ControlEdificios();
        controlEstaciones = new ControlEstaciones();

        // Configurar botón de cambio de perfil
        cambiarPerfil.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cambioPerfil.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Configurar botón de actualización
        if (actualizarDashboard != null) {
            actualizarDashboard.setOnAction(event -> actualizarDashboard());
        }

        // Cargar datos iniciales
        actualizarDashboard();
    }

    @FXML
    private void actualizarDashboard() {
        System.out.println("Actualizando dashboard");
        actualizarEnergia();
        actualizarInfraestructura();
        actualizarSeguridad();
        actualizarBienestar();
    }

    private void actualizarEnergia() {
        // Robots en alerta
        int robotsAlerta = 0;
        int totalRobots = controlRobots.getRobots().size();

        for (RobotAsistente robot : controlRobots.getRobots()) {
            if (robot.getEstado() == TEstadoRobots.ALERTA) {
                robotsAlerta++;
            }
        }

        if (robotsEnAlerta != null) robotsEnAlerta.setText(String.valueOf(robotsAlerta));
        if (robotsPorcentajeAlerta != null && totalRobots > 0) {
            double porcentaje = (robotsAlerta * 100.0) / totalRobots;
            robotsPorcentajeAlerta.setText(String.format("%.2f", porcentaje));
        } else if (robotsPorcentajeAlerta != null) {
            robotsPorcentajeAlerta.setText("0.00");
        }

        // Drones en alerta
        int dronesAlerta = 0;
        int totalDrones = controlDrones.getDrones().size();

        for (DroneVigilancia drone : controlDrones.getDrones()) {
            if (drone.getEstado() == TEstadoRobots.ALERTA) {
                dronesAlerta++;
            }
        }

        if (dronesEnAlerta != null) dronesEnAlerta.setText(String.valueOf(dronesAlerta));
        if (dronesPorcentajeAlerta != null && totalDrones > 0) {
            double porcentaje = (dronesAlerta * 100.0) / totalDrones;
            dronesPorcentajeAlerta.setText(String.format("%.2f", porcentaje));
        } else if (dronesPorcentajeAlerta != null) {
            dronesPorcentajeAlerta.setText("0.00");
        }

        // Desgloses por edificio: robots_totales, robots_en_alerta, %alerta
        if (energiaEdificios != null) {
            energiaEdificios.getItems().clear();
            for (EdificioInteligente edificio : controlEdificios.getEdificios()) {
                int robotsEdificio = 0;
                int robotsAlertaEdificio = 0;

                for (Ciudadano ciudadano : edificio.getCiudadanos()) {
                    for (RobotAsistente robot : ciudadano.getRobotsAsignados()) {
                        robotsEdificio++;
                        if (robot.getEstado() == TEstadoRobots.ALERTA) {
                            robotsAlertaEdificio++;
                        }
                    }
                }

                double porcentajeAlerta = robotsEdificio > 0 ? (robotsAlertaEdificio * 100.0) / robotsEdificio : 0.0;

                energiaEdificios.getItems().add(
                        edificio.getNombre() + " - Robots totales: " + robotsEdificio +
                                ", En alerta: " + robotsAlertaEdificio +
                                ", % Alerta: " + String.format("%.2f%%", porcentajeAlerta)
                );
            }
        }

        if (energiaTareas != null) {
            energiaTareas.getItems().clear();
            ControlTareas controlTareas = new ControlTareas();

            // Calcular para cada tarea: cuántos robots la tienen y cuántos tienen batería insuficiente
            Map<Integer, Integer> robotsPorTarea = new HashMap<>();
            Map<Integer, Integer> robotsSinBateriaPorTarea = new HashMap<>();

            for (RobotAsistente robot : controlRobots.getRobots()) {
                for (Tarea tarea : robot.getTareas()) {
                    robotsPorTarea.put(tarea.getID(), robotsPorTarea.getOrDefault(tarea.getID(), 0) + 1);

                    // Si el robot no tiene batería suficiente para esta tarea
                    if (robot.getBateria() < tarea.getConsumo()) {
                        robotsSinBateriaPorTarea.put(tarea.getID(),
                                robotsSinBateriaPorTarea.getOrDefault(tarea.getID(), 0) + 1);
                    }
                }
            }

            for (Map.Entry<Integer, Integer> entry : robotsPorTarea.entrySet()) {
                Tarea tarea = controlTareas.consultar(entry.getKey());
                if (tarea != null) {
                    totalRobots = entry.getValue();
                    int robotsSinBateria = robotsSinBateriaPorTarea.getOrDefault(entry.getKey(), 0);
                    double tasaRechazo = totalRobots > 0 ? (robotsSinBateria * 100.0) / totalRobots : 0.0;

                    energiaTareas.getItems().add(
                            tarea.getDescripcion() + " (" + tarea.getConsumo() + "%) - " +
                                    "Robots: " + totalRobots +
                                    ", Batería insuficiente: " + robotsSinBateria +
                                    ", Tasa rechazo: " + String.format("%.2f%%", tasaRechazo)
                    );
                }
            }
        }

        // Gráfico: Barras apiladas - robots en alerta vs no alerta por edificio
        if (barChartEnergia != null) {
            barChartEnergia.getData().clear();

            XYChart.Series<String, Number> serieEnAlerta = new XYChart.Series<>();
            serieEnAlerta.setName("En Alerta");

            XYChart.Series<String, Number> serieActivos = new XYChart.Series<>();
            serieActivos.setName("Activos");

            for (EdificioInteligente edificio : controlEdificios.getEdificios()) {
                int robotsEdificio = 0;
                int robotsAlertaEdificio = 0;

                for (Ciudadano ciudadano : edificio.getCiudadanos()) {
                    for (RobotAsistente robot : ciudadano.getRobotsAsignados()) {
                        robotsEdificio++;
                        if (robot.getEstado() == TEstadoRobots.ALERTA) {
                            robotsAlertaEdificio++;
                        }
                    }
                }

                int robotsActivos = robotsEdificio - robotsAlertaEdificio;

                serieEnAlerta.getData().add(new XYChart.Data<>(edificio.getNombre(), robotsAlertaEdificio));
                serieActivos.getData().add(new XYChart.Data<>(edificio.getNombre(), robotsActivos));
            }

            barChartEnergia.getData().addAll(serieEnAlerta, serieActivos);
        }
    }
    private void actualizarInfraestructura() {
        int disponibles = 0;
        int total = controlEstaciones.getEstaciones().size();

        for (EstacionEnergia estacion : controlEstaciones.getEstaciones()) {
            if (estacion.getEstado() == TEstadoEstacion.DISPONIBLE) {
                disponibles++;
            }
        }

        if (estacionesDisponibles != null) estacionesDisponibles.setText(String.valueOf(disponibles));
        if (estacionesPorcentaje != null && total > 0) {
            double porcentaje = (disponibles * 100.0) / total;
            estacionesPorcentaje.setText(String.format("%.2f", porcentaje));
        } else if (estacionesPorcentaje != null) {
            estacionesPorcentaje.setText("0.00");
        }

        if (infraestructuraEstaciones != null) {
            infraestructuraEstaciones.getItems().clear();
            for (EstacionEnergia estacion : controlEstaciones.getEstaciones()) {
                String estado = estacion.getEstado() != null ? estacion.getEstado().toString() : "SIN ESTADO";
                infraestructuraEstaciones.getItems().add(
                        estacion.getNombre() + " - " + estado +
                                " (Cap: " + estacion.getCapacidad() + ", Ocup: " + estacion.getOcupacion() + ")"
                );
            }
        }
    }

    private void actualizarSeguridad() {
        Set<EdificioInteligente> edificiosAfectados = new HashSet<>();
        Set<Ciudadano> ciudadanosAfectadosSet = new HashSet<>();
        Map<EdificioInteligente, Integer> incidentesPorEdificio = new HashMap<>();

        ArrayList<Registro> historial = ConsejoInteligencia.getHistorial();

        for (Registro registro : historial) {
            if (registro.getDatosDrone() != null && registro.getDatosEvento() != null) {
                EdificioInteligente edificio = registro.getDatosDrone().getEdificio();
                if (edificio != null) {
                    edificiosAfectados.add(edificio);
                    ciudadanosAfectadosSet.addAll(edificio.getCiudadanos());
                    incidentesPorEdificio.put(edificio, incidentesPorEdificio.getOrDefault(edificio, 0) + 1);
                }
            }
        }

        if (edificiosImpactados != null) edificiosImpactados.setText(String.valueOf(edificiosAfectados.size()));
        if (ciudadanosAfectados != null) ciudadanosAfectados.setText(String.valueOf(ciudadanosAfectadosSet.size()));

        if (seguridadIncidentes != null) {
            seguridadIncidentes.getItems().clear();
            for (Map.Entry<EdificioInteligente, Integer> entry : incidentesPorEdificio.entrySet()) {
                seguridadIncidentes.getItems().add(
                        entry.getKey().getNombre() + ": " + entry.getValue() + " incidentes"
                );
            }
        }

        if (seguridadAcciones != null) {
            seguridadAcciones.getItems().clear();
            Map<String, Integer> accionesContador = new HashMap<>();

            for (Registro registro : historial) {
                if (registro.getDatosAccion() != null) {
                    String accion = registro.getDatosAccion().getDescription();
                    accionesContador.put(accion, accionesContador.getOrDefault(accion, 0) + 1);
                }
            }

            for (Map.Entry<String, Integer> entry : accionesContador.entrySet()) {
                seguridadAcciones.getItems().add(entry.getKey() + ": " + entry.getValue() + " veces");
            }
        }

        if (edificioReincidencia != null && !incidentesPorEdificio.isEmpty()) {
            EdificioInteligente edificioMax = null;
            int maxIncidentes = 0;

            for (Map.Entry<EdificioInteligente, Integer> entry : incidentesPorEdificio.entrySet()) {
                if (entry.getValue() > maxIncidentes) {
                    maxIncidentes = entry.getValue();
                    edificioMax = entry.getKey();
                }
            }

            if (edificioMax != null) {
                edificioReincidencia.setText(edificioMax.getNombre() + " (" + maxIncidentes + " incidentes)");
            }
        } else if (edificioReincidencia != null) {
            edificioReincidencia.setText("Sin datos");
        }
    }

    private void actualizarBienestar() {
        int totalCiudadanos = controlCiudadanos.getCiudadanos().size();
        int ciudadanosConRobots = 0;
        int ciudadanosSinRobots = 0;
        int totalRobotsAsignados = 0;

        for (Ciudadano ciudadano : controlCiudadanos.getCiudadanos()) {
            if (!ciudadano.getRobotsAsignados().isEmpty()) {
                ciudadanosConRobots++;
                totalRobotsAsignados += ciudadano.getRobotsAsignados().size();
            } else {
                ciudadanosSinRobots++;
            }
        }

        if (ciudadanosConRobot != null && totalCiudadanos > 0) {
            double porcentaje = (ciudadanosConRobots * 100.0) / totalCiudadanos;
            ciudadanosConRobot.setText(String.format("%.2f", porcentaje));
        } else if (ciudadanosConRobot != null) {
            ciudadanosConRobot.setText("0.00");
        }

        if (relacionRobotsCiudadano != null && totalCiudadanos > 0) {
            double relacion = (double) totalRobotsAsignados / totalCiudadanos;
            relacionRobotsCiudadano.setText(String.format("%.2f", relacion));
        } else if (relacionRobotsCiudadano != null) {
            relacionRobotsCiudadano.setText("0.00");
        }

        if (ciudadanosSinRobotCantidad != null) {
            ciudadanosSinRobotCantidad.setText(String.valueOf(ciudadanosSinRobots));
        }

        if (ciudadanosSinRobotPorcentaje != null && totalCiudadanos > 0) {
            double porcentaje = (ciudadanosSinRobots * 100.0) / totalCiudadanos;
            ciudadanosSinRobotPorcentaje.setText(String.format("%.2f", porcentaje));
        } else if (ciudadanosSinRobotPorcentaje != null) {
            ciudadanosSinRobotPorcentaje.setText("0.00");
        }

        // Top 3 edificios por ocupación
        if (topOcupacion != null) {
            topOcupacion.getItems().clear();
            ArrayList<EdificioInteligente> edificios = new ArrayList<>(controlEdificios.getEdificios());
            edificios.sort((e1, e2) -> {
                double ocp1 = (e1.getCiudadanos().size() * 100.0) / e1.getCapacidad();
                double ocp2 = (e2.getCiudadanos().size() * 100.0) / e2.getCapacidad();
                return Double.compare(ocp2, ocp1);
            });

            for (int i = 0; i < Math.min(3, edificios.size()); i++) {
                EdificioInteligente e = edificios.get(i);
                double ocupacion = (e.getCiudadanos().size() * 100.0) / e.getCapacidad();
                topOcupacion.getItems().add(
                        e.getNombre() + ": " + String.format("%.2f%%", ocupacion) +
                                " (" + e.getCiudadanos().size() + "/" + e.getCapacidad() + ")"
                );
            }
        }

        // Top 3 edificios con mayor % sin robot
        if (topSinRobot != null) {
            topSinRobot.getItems().clear();
            ArrayList<EdificioInteligente> edificios = new ArrayList<>(controlEdificios.getEdificios());
            edificios.sort((e1, e2) -> {
                int sinRobot1 = 0;
                for (Ciudadano c : e1.getCiudadanos()) {
                    if (c.getRobotsAsignados().isEmpty()) sinRobot1++;
                }
                int sinRobot2 = 0;
                for (Ciudadano c : e2.getCiudadanos()) {
                    if (c.getRobotsAsignados().isEmpty()) sinRobot2++;
                }

                double pct1 = e1.getCiudadanos().isEmpty() ? 0 : (sinRobot1 * 100.0) / e1.getCiudadanos().size();
                double pct2 = e2.getCiudadanos().isEmpty() ? 0 : (sinRobot2 * 100.0) / e2.getCiudadanos().size();
                return Double.compare(pct2, pct1);
            });

            for (int i = 0; i < Math.min(3, edificios.size()); i++) {
                EdificioInteligente e = edificios.get(i);
                int sinRobot = 0;
                for (Ciudadano c : e.getCiudadanos()) {
                    if (c.getRobotsAsignados().isEmpty()) sinRobot++;
                }
                double pct = e.getCiudadanos().isEmpty() ? 0 : (sinRobot * 100.0) / e.getCiudadanos().size();
                topSinRobot.getItems().add(
                        e.getNombre() + ": " + String.format("%.2f%%", pct) + " sin robot"
                );
            }
        }

        // Top 3 edificios con más robots en alerta
        if (topRobotsAlerta != null) {
            topRobotsAlerta.getItems().clear();
            Map<EdificioInteligente, Integer> robotsAlertaPorEdificio = new HashMap<>();

            for (EdificioInteligente edificio : controlEdificios.getEdificios()) {
                int alerta = 0;
                for (Ciudadano ciudadano : edificio.getCiudadanos()) {
                    for (RobotAsistente robot : ciudadano.getRobotsAsignados()) {
                        if (robot.getEstado() == TEstadoRobots.ALERTA) alerta++;
                    }
                }
                robotsAlertaPorEdificio.put(edificio, alerta);
            }

            ArrayList<Map.Entry<EdificioInteligente, Integer>> sorted = new ArrayList<>(robotsAlertaPorEdificio.entrySet());
            sorted.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            for (int i = 0; i < Math.min(3, sorted.size()); i++) {
                topRobotsAlerta.getItems().add(
                        sorted.get(i).getKey().getNombre() + ": " + sorted.get(i).getValue() + " robots en alerta"
                );
            }
        }
    }
}
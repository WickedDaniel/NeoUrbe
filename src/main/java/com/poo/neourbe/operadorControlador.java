package com.poo.neourbe;

import controlador.*;
import controlador.Perfiles.PerfilOperador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class operadorControlador implements Initializable {
    // Panel Ciudadanos
    public Button administrarCiudadanos;
    public Button administrarRobots;
    public Button administrarDrones;
    public Button generarSimulacion;
    public Button cambiarPerfil;
    // administrarCiudadanos
    public Button agregarCiudadano;
    public Button agregarCiudadanos;
    public Button consultarCiudadano;
    public Button cargarCiudadanoAModificar;
    public Button eliminarCiudadano;
    // generarSimulacion
    public Button accionarSimulacion;
    public ListView<String> simulacionTareas;
    public ListView<String> simulacionAtencionEstaciones;
    public ListView<String> simulacionReportesConsejo;

    // region CiudadanosElementos
    public AnchorPane panelCiudadanos;
    public AnchorPane panelRobots;
    public AnchorPane panelDrones;
    public AnchorPane panelSimulacion;
    public Label feedbackAddSingleCitizen;
    public Label feedbackAddMultipleCitizen;
    public Label feedbackGetCitizen;
    public Label feedbackModificarCiudadano;
    public Label feedbackEliminarUsuario;
    public ComboBox<String> edificioSeleccionado;
    public ComboBox<String> edificioSeleccionadoBloque;
    public ComboBox<String> edificioAModificarCiudadano;
    public Spinner<Integer> cantidadBloqueCiudadanos;
    public Spinner<Integer> consultaCiudadanoID;
    public Spinner<Integer> modificarCiudadanoID;
    public Spinner<Integer> eliminarCiudadanoID;
    public Tab modificarCiudadanoTab;
    public Tab consultarCiudadanoTab;
    public Tab eliminarCiudadanoTab;
    // Panel Ciudadanos: Fin
    // endregion

    // Panel Robots
    public Button agregarRobot;
    public Button agregarRobots;
    public Button asignarRobot;
    public Button desasignarRobot;
    public Button verificarCiudadano;
    public Button consultarRobot;
    public Button modificarRobotSeleccionado;
    public Button eliminarRobot;
    public Button cargarRobotsCiudadano;
    public Label feedbackAgregarRobot;
    public Label feedbackAddMultipleRobots;
    public Label feedbackRobotsAsignados;
    public Label feedbackRobots;
    public Label feedbackRobotsModificar;
    public Label feedbackRobotEliminado;
    public Label feedbackDesasignacion;
    public Spinner<Integer> cantidadBloqueRobots;
    public Spinner<Integer> verCiudadanoID;
    public Spinner<Integer> desasignacionSpinner;
    public ChoiceBox<Integer> robotSeleccionadoDesasignar;
    public ComboBox<Integer> robotSeleccionado;
    public ComboBox<Integer> robotConsultar;
    public ComboBox<Integer> robotModificar;
    public ComboBox<Integer> robotEliminar;

    public ComboBox<TEstadoRobots> robotModificarEstado;
    public Tab asignacionRobotTab;
    public Tab consultaRobotTab;
    public Tab modificarRobotTab;
    public Tab eliminarRobotTab;
    // Panel robots: Fin

    // Panel Drones
    public Label feedbackGetDrone;
    public Tab consultarDroneTab;
    public Button consultarDrone;
    public Spinner<Integer> consultaDroneCPU;
    // Panel Drones: Fin

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //region Initialization
        // Daniel
        ocultarPaneles();
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
            Stage stage = (Stage) cambiarPerfil.getScene().getWindow();
            stage.close();
        });

        PerfilOperador operador = new PerfilOperador();
        ControlEdificios controlEdificios = new ControlEdificios();
        ControlCiudadanos controlCiudadanos = new ControlCiudadanos();

        administrarRobots.setOnAction(e -> {ocultarPaneles(); panelRobots.setVisible(true);});
        administrarCiudadanos.setOnAction(e -> {ocultarPaneles(); panelCiudadanos.setVisible(true);});
        administrarDrones.setOnAction(e -> {ocultarPaneles(); panelDrones.setVisible(true);});
        generarSimulacion.setOnAction(e -> {ocultarPaneles(); panelSimulacion.setVisible(true);});
        // endregion

        // region Ciudadanos
        // Agregar Ciudadanos.
        SpinnerValueFactory<Integer> bloqueSpinnerValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        cantidadBloqueCiudadanos.setValueFactory(bloqueSpinnerValues);

        SpinnerValueFactory<Integer> consultaAllowedValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controlCiudadanos.getCiudadanos().size(), 1);
        consultaCiudadanoID.setValueFactory(consultaAllowedValues);
        modificarCiudadanoID.setValueFactory(consultaCiudadanoID.getValueFactory());
        eliminarCiudadanoID.setValueFactory(consultaCiudadanoID.getValueFactory());

        ArrayList<EdificioInteligente> edificiosRegistrados = controlEdificios.getEdificios();
        HashMap<String, EdificioInteligente> nombreEdificioRegistrado = new HashMap<>();

        for (EdificioInteligente edificio : edificiosRegistrados) {
            nombreEdificioRegistrado.put(edificio.getNombre(), edificio);
            edificioSeleccionado.getItems().addAll(edificio.getNombre());
            edificioSeleccionadoBloque.getItems().addAll(edificio.getNombre());
            edificioAModificarCiudadano.getItems().addAll(edificio.getNombre());
        }

        // Deshabilitar por defecto para evitar errores
        agregarCiudadano.setDisable(true);
        agregarCiudadanos.setDisable(true);
        cantidadBloqueCiudadanos.setDisable(true);
        consultarCiudadanoTab.setDisable(true);
        modificarCiudadanoTab.setDisable(true);
        eliminarCiudadanoTab.setDisable(true);
        if (!controlCiudadanos.getCiudadanos().isEmpty()) {
            modificarCiudadanoTab.setDisable(false);
            consultarCiudadanoTab.setDisable(false);
            eliminarCiudadanoTab.setDisable(false);
        }

        // cuando se seleccione un edificio se habilita el boton de agregar
        edificioSeleccionado.setOnAction(event -> {
            agregarCiudadano.setDisable(false);
        });
        edificioSeleccionadoBloque.setOnAction(event -> {
            agregarCiudadanos.setDisable(false);
            cantidadBloqueCiudadanos.setDisable(false);
        });

        agregarCiudadano.setOnAction(e -> {
            String nombreEdificio = edificioSeleccionado.getSelectionModel().getSelectedItem();
            EdificioInteligente edificio = nombreEdificioRegistrado.get(nombreEdificio);
            if (nombreEdificio == null) return;

            if (operador.agregarCiudadano(edificio.getID())) {
                feedbackAddSingleCitizen.setText("Ciudadano agregado exitosamente");
                updateTransactionTabs(controlCiudadanos);
                return;
            }
            feedbackAddSingleCitizen.setText("Hubo un error al agregar el ciudadano");
        });

        agregarCiudadanos.setOnAction(e -> {
            String nombreEdificio = edificioSeleccionadoBloque.getSelectionModel().getSelectedItem();
            EdificioInteligente edificio = nombreEdificioRegistrado.get(nombreEdificio);
            if (nombreEdificio == null) return;

            if (operador.agregarCiudadano(edificio.getID(), cantidadBloqueCiudadanos.getValue())) {
                feedbackAddMultipleCitizen.setText("Ciudadanos agregados exitosamente");
                updateTransactionTabs(controlCiudadanos);
                return;
            }
            feedbackAddMultipleCitizen.setText("Hubo un error al agregar el ciudadano");
        });

        consultarCiudadano.setOnAction(e -> {
            Ciudadano ciudadano = operador.consultarCiudadano(consultaCiudadanoID.getValue());
            System.out.println(ciudadano);
            feedbackGetCitizen.setText(ciudadano.toString());
        });

        edificioAModificarCiudadano.setDisable(true);
        cargarCiudadanoAModificar.setOnAction(e -> {
            Ciudadano ciudadano = operador.consultarCiudadano(modificarCiudadanoID.getValue());
            edificioAModificarCiudadano.setDisable(true);
            if (ciudadano == null) return;
            edificioAModificarCiudadano.setDisable(false);
            edificioAModificarCiudadano.getSelectionModel().select(ciudadano.getEdificio().getNombre());
        });

        edificioAModificarCiudadano.setOnAction(e -> {
            Ciudadano ciudadano = controlCiudadanos.consultar(modificarCiudadanoID.getValue());
            String nombreEdificio = edificioAModificarCiudadano.getSelectionModel().getSelectedItem();
            if (ciudadano.getEdificio().getNombre().equals(nombreEdificio)) return;

            EdificioInteligente edificio = nombreEdificioRegistrado.get(nombreEdificio);
            if (edificio.getCapacidad() > edificio.getCiudadanos().size()) {
                ciudadano.setEdificio(edificio);
                if (controlCiudadanos.modificar(modificarCiudadanoID.getValue(), ciudadano)) {
                    feedbackModificarCiudadano.setText("Ciudadano modificado exitosamente, cambio de edificio exitoso.");
                    return;
                }
            };
            edificioAModificarCiudadano.getSelectionModel().select(ciudadano.getEdificio().getNombre());
            feedbackModificarCiudadano.setText("Error al modificar ciudadano");
        });

        eliminarCiudadano.setOnAction(e -> {
            Ciudadano ciudadano = controlCiudadanos.consultar(modificarCiudadanoID.getValue());
            if (ciudadano == null) return;
            if (controlCiudadanos.eliminar(modificarCiudadanoID.getValue())) {
                feedbackEliminarUsuario.setText("Ciudadano eliminado exitosamente");
                updateTransactionTabs(controlCiudadanos);
                return;
            }
            feedbackEliminarUsuario.setText("Error al eliminar ciudadano");
        });
        // Fin Daniel

        // endregion

        // Inicio Alex - Panel Robots
        desasignacionSpinner.setValueFactory(consultaAllowedValues);
        ControlRobots controlRobots = new ControlRobots();
        ControlTareas controlTareas = new ControlTareas();
        ControlProcesadores controlProcesadores = new ControlProcesadores();

        ArrayList<RobotAsistente> robotsRegistrados = controlRobots.getRobots();

        agregarRobot.setOnAction(e -> {
            if (operador.agregarRobot()) {
                feedbackAgregarRobot.setText("Robot agregado exitosamente");
                actualizarRobots();
                System.out.println(controlRobots.getRobots());
            }
        });

        SpinnerValueFactory<Integer> bloqueSpinnerValuesRobot = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        cantidadBloqueRobots.setValueFactory(bloqueSpinnerValuesRobot);
        agregarRobots.setOnAction(e -> {
            if (operador.agregarRobot(cantidadBloqueCiudadanos.getValue())) {
                feedbackAddMultipleRobots.setText("Robots agregados exitosamente");
                actualizarRobots();
                System.out.println(controlRobots.getRobots());
            }
        });

        verCiudadanoID.setValueFactory(consultaAllowedValues);
        desasignacionSpinner.setValueFactory(consultaAllowedValues);
        robotSeleccionado.setDisable(true);
        verificarCiudadano.setOnAction(e -> {
            if (controlCiudadanos.getCiudadanos().isEmpty()) {
                feedbackRobotsAsignados.setText("No hay ciudadanos a consultar");
                return;
            }


            Ciudadano ciudadano = controlCiudadanos.consultar(verCiudadanoID.getValue());
            if (ciudadano == null) {
                feedbackRobotsAsignados.setText("El ciudadano con el ID " + verCiudadanoID.getValue() + " no existe.");
                return;
            };
            if (ciudadano.getRobotsAsignados().isEmpty()) {
                feedbackRobotsAsignados.setText("El ciudadano con el ID: " + ciudadano.getID() + ": No tiene robots asignados.");
            } else{
                feedbackRobotsAsignados.setText(ciudadano.getRobotsAsignados().toString());
            }
            robotSeleccionado.setDisable(false);
        });

        asignarRobot.setDisable(true);
        robotSeleccionado.setOnAction(event -> {
            asignarRobot.setDisable(false);
        });
        asignarRobot.setOnAction(e -> {
            if (controlCiudadanos.getCiudadanos().isEmpty()) {
                feedbackRobotsAsignados.setText("No hay ciudadanos");
                return;
            }

            Integer procesadorID = robotSeleccionado.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            Ciudadano ciudadano = controlCiudadanos.consultar(verCiudadanoID.getValue());
            if (!operador.asignarRobot(procesadorID, ciudadano.getID())) {
                feedbackRobotsAsignados.setText("No se ha logrado asignar el robot");
                return;
            };
            feedbackRobotsAsignados.setText("Se ha asignado correctamente");
        });
        desasignarRobot.setDisable(true);
        robotSeleccionadoDesasignar.setDisable(true);
        cargarRobotsCiudadano.setOnAction(event -> {
            robotSeleccionadoDesasignar.setDisable(true);
            robotSeleccionadoDesasignar.getSelectionModel().clearSelection();
            Ciudadano ciudadano = controlCiudadanos.consultar(desasignacionSpinner.getValue());
            if (ciudadano == null) {
                feedbackDesasignacion.setText("El ciudadano no existe");
                return;
            }
            if (ciudadano.getRobotsAsignados().isEmpty()) {
                feedbackDesasignacion.setText("El ciudadano no tiene robots adjuntos");
                return;
            }
            robotSeleccionadoDesasignar.setDisable(false);
            actualizarRobotsCiudadano(ciudadano);
        });

        robotSeleccionadoDesasignar.setOnAction(event -> {
            desasignarRobot.setDisable(false);
        });
        desasignarRobot.setOnAction(e -> {
            if (controlCiudadanos.getCiudadanos().isEmpty()) {
                feedbackRobotsAsignados.setText("No hay ciudadanos");
                return;
            }
            Integer procesadorID = robotSeleccionadoDesasignar.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            Ciudadano ciudadano = controlCiudadanos.consultar(verCiudadanoID.getValue());
            if (!operador.desasignarRobot(procesadorID, ciudadano.getID())){
                feedbackRobotsAsignados.setText("Ocurrio un error al desasignar el robot");
                return;
            };
            feedbackRobotsAsignados.setText("Se ha desasignado el robot con exito");
            robotSeleccionadoDesasignar.getSelectionModel().clearSelection();
            actualizarRobotsCiudadano(ciudadano);
        });

        consultarRobot.setDisable(true);
        robotConsultar.setOnAction(event -> {
            consultarRobot.setDisable(false);
        });
        consultarRobot.setOnAction(e -> {
            Integer procesadorID = robotConsultar.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            RobotAsistente robot = controlRobots.consultar(procesador);
            if (robot == null) {
                feedbackRobots.setText("El robot con el procesador indicado no existe");
                return;
            }
            feedbackRobots.setText(robot.toString());
        });

        modificarRobotSeleccionado.setDisable(true);
        robotModificar.setOnAction(event -> {
            modificarRobotSeleccionado.setDisable(false);
        });
        modificarRobotSeleccionado.setOnAction(e -> {
            Integer procesadorID = robotModificar.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            RobotAsistente robot = controlRobots.consultar(procesador);
            if (robot == null) {
                feedbackRobots.setText("El robot con el procesador indicado no existe");
                return;
            }
            robotModificarEstado.getItems().setAll(TEstadoRobots.values());
            robotModificarEstado.getSelectionModel().select(robot.getEstado());
        });
        robotModificarEstado.setOnAction(e -> {
            Integer procesadorID = robotModificar.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            RobotAsistente robot = controlRobots.consultar(procesador);
            if (robot == null) {
                feedbackRobots.setText("El robot con el procesador indicado no existe");
                return;
            }
            if (robotModificarEstado.getSelectionModel().getSelectedItem() == null) return;
            if (robot.getEstado() == robotModificarEstado.getSelectionModel().getSelectedItem()) return;
            robot.setEstado(robotModificarEstado.getSelectionModel().getSelectedItem());
            feedbackRobots.setText("Estado de robot modificado");
        });

        eliminarRobot.setDisable(true);
        robotEliminar.setOnAction(event -> {
            eliminarRobot.setDisable(false);
        });
        eliminarRobot.setOnAction(e -> {
            Integer procesadorID = robotEliminar.getSelectionModel().getSelectedItem();
            Procesador procesador = controlProcesadores.consultar(procesadorID);
            RobotAsistente robot = controlRobots.consultar(procesador);
            if (robot == null) {
                feedbackRobots.setText("El robot con el procesador indicado no existe");
                return;
            }
            operador.eliminarRobot(procesadorID);
            actualizarRobots();
        });
        actualizarRobots();
        // Fin Alex

        // Inicio David - Panel Drones
        ControlDrones controlDrones = new ControlDrones();

        SpinnerValueFactory<Integer> consultaAllowedValuesDrones = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controlDrones.getDrones().size(), 1);
        consultaDroneCPU.setValueFactory(consultaAllowedValuesDrones);

        consultarDroneTab.setDisable(true);
        if (!controlDrones.getDrones().isEmpty()) {
            consultarDroneTab.setDisable(false);
        }

        consultarDrone.setOnAction(e -> {
            if (consultaDroneCPU.getValue() == null) {
                return;
            }

            Procesador procesador = controlProcesadores.consultar(consultaDroneCPU.getValue());
            System.out.println(consultaDroneCPU.getValue());
            DroneVigilancia drone = controlDrones.consultar(procesador);

            System.out.println(drone);
            feedbackGetDrone.setText(drone.toString());
        });
        // Fin David
    }

    @FXML
    protected void clearFeedbackCiudadanos() {
        feedbackModificarCiudadano.setText("");
        feedbackAddMultipleCitizen.setText("");
        feedbackGetCitizen.setText("");
        feedbackEliminarUsuario.setText("");
    }

    @FXML
    protected void generarSimulacion() {
        ControlEdificios controlEdificios = new ControlEdificios();
        ControlCiudadanos controlCiudadanos = new ControlCiudadanos();
        ControlDrones controlDrones = new ControlDrones();
        ControlTareas controlTareas = new ControlTareas();
        ControlEstaciones controlEstaciones = new ControlEstaciones();
        ControlEventos controlEventos = new ControlEventos();

        for (Evento evento : controlEventos.getEventos()) {
            if (evento.getRespuesta().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Respuesta vacía");
                alert.setContentText("Se encontró un evento con respuesta vacía.");
                alert.showAndWait();
                return;
            }
        }

        for (Ciudadano ciudadano : controlCiudadanos.getCiudadanos()) {
            if (ciudadano.getRobotsAsignados().isEmpty()) continue;
            for (RobotAsistente robot : ciudadano.getRobotsAsignados()) {
                boolean randomBoolean = new Random().nextBoolean();
                if (!randomBoolean) continue;
                Tarea tareaAleatoria = robot.getTareas().get(new Random().nextInt(0, robot.getTareas().size()));
                System.out.println("Simulacion: A realizar tarea");
                if (robot.RealizarTarea(tareaAleatoria)) {
                    System.out.println("Tarea realizada");
                    simulacionTareas.getItems().add("Ciudadano:" + ciudadano.getID() + ", " + robot.getHistorial().getLast().toString());
                } else {
                    if (robot.getEstado() == TEstadoRobots.ALERTA) {
                        if (robot.getBateria() > RobotAsistente.getBateriaMinima()) {
                            robot.setEstado(TEstadoRobots.ACTIVO);
                            continue;
                        }
                        for (EstacionEnergia estacion : controlEstaciones.getEstaciones()) {
                            if (estacion.AtenderRobot(robot)) {
                                simulacionAtencionEstaciones.getItems().add(estacion.getHistorial().getLast().toString());
                                break;
                            };
                        }
                    }
                }
            }
        }

        for (DroneVigilancia drone : controlDrones.getDrones()) {
            boolean randomBoolean = new Random().nextBoolean();
            if (!randomBoolean) continue;
            if (drone.getEstado() == TEstadoRobots.ALERTA && drone.getBateria() == drone.getCapacidadVuelo() * 25 && drone.getEnEstacion() != null) {
                drone.getEnEstacion().RetirarDrone(drone);
            }

            if (drone.getEstado() != TEstadoRobots.ACTIVO) continue;
            Evento eventoPatrullado = drone.Patrullar();
            if (eventoPatrullado != null) {
                ConsejoInteligencia.ResolverAnomalia(drone, eventoPatrullado);
                simulacionReportesConsejo.getItems().add(ConsejoInteligencia.getHistorial().getLast().toString());
            }
            if (drone.getEstado() == TEstadoRobots.ALERTA) {
                for (EstacionEnergia estacion : controlEstaciones.getEstaciones()) {
                    if (estacion.AtenderDrone(drone)) {
                        simulacionAtencionEstaciones.getItems().add(estacion.getHistorial().getLast().toString());
                        break;
                    };
                }
            }
        }
    }

    private void actualizarRobotsCiudadano(Ciudadano ciudadano) {
        if (ciudadano.getRobotsAsignados().isEmpty()) {
            desasignarRobot.setDisable(true);
            robotSeleccionadoDesasignar.setDisable(true);
            robotSeleccionadoDesasignar.getItems().clear();
            return;
        };
        ArrayList<Integer> procesadoresID = new ArrayList<>();
        for (RobotAsistente robot: ciudadano.getRobotsAsignados()) {
            procesadoresID.add(robot.getCPU().getID());
        }
        robotSeleccionadoDesasignar.setDisable(false);
        robotSeleccionadoDesasignar.getItems().setAll(procesadoresID);
    }

    private void actualizarRobots() {
        ControlRobots controlRobots =  new ControlRobots();
        robotSeleccionado.getSelectionModel().clearSelection();
        robotSeleccionadoDesasignar.getSelectionModel().clearSelection();
        robotConsultar.getSelectionModel().clearSelection();
        robotModificar.getSelectionModel().clearSelection();
        robotEliminar.getSelectionModel().clearSelection();
        modificarRobotSeleccionado.setDisable(true);
        eliminarRobot.setDisable(true);
        consultarRobot.setDisable(true);

        asignacionRobotTab.setDisable(controlRobots.getRobots().isEmpty());
        eliminarRobotTab.setDisable(controlRobots.getRobots().isEmpty());
        consultaRobotTab.setDisable(controlRobots.getRobots().isEmpty());
        modificarRobotTab.setDisable(controlRobots.getRobots().isEmpty());
        if (controlRobots.getRobots().isEmpty()) {
            robotSeleccionado.setDisable(true);
            return;
        };
        ArrayList<Integer> procesadoresID = new ArrayList<>();
        for (RobotAsistente robot: controlRobots.getRobots()) {
            procesadoresID.add(robot.getCPU().getID());
        }
        robotConsultar.getItems().setAll(procesadoresID);
        robotSeleccionado.getItems().setAll(procesadoresID);
        robotModificar.getItems().setAll(procesadoresID);
        robotEliminar.getItems().setAll(procesadoresID);
    }

    private void updateTransactionTabs(ControlCiudadanos controlCiudadanos) {
        if (controlCiudadanos.getCiudadanos().isEmpty()) {
            modificarCiudadanoTab.setDisable(true);
            consultarCiudadanoTab.setDisable(true);
            eliminarCiudadanoTab.setDisable(true);
            asignacionRobotTab.setDisable(true);
            return;
        }

        asignacionRobotTab.setDisable(false);
        modificarCiudadanoTab.setDisable(false);
        consultarCiudadanoTab.setDisable(false);
        eliminarCiudadanoTab.setDisable(false);
        consultaCiudadanoID.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(controlCiudadanos.getCiudadanos().getFirst().getID(), controlCiudadanos.getCiudadanos().getLast().getID(), controlCiudadanos.getCiudadanos().getFirst().getID()));
        modificarCiudadanoID.setValueFactory(consultaCiudadanoID.getValueFactory());
        verCiudadanoID.setValueFactory(consultaCiudadanoID.getValueFactory());
        desasignacionSpinner.setValueFactory(consultaCiudadanoID.getValueFactory());
        eliminarCiudadanoID.setValueFactory(consultaCiudadanoID.getValueFactory());
    }

    private void ocultarPaneles() {
        panelCiudadanos.setVisible(false);
        panelRobots.setVisible(false);
        panelDrones.setVisible(false);
        panelSimulacion.setVisible(false);
    }
}
package com.poo.neourbe;

import controlador.ControlAcciones;
import controlador.ControlEdificios;
import controlador.ControlEstaciones;
import controlador.ControlEventos;
import controlador.Perfiles.PerfilAdministrador;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.*;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class administradorControlador implements Initializable {
    private final PerfilAdministrador perfilAdministrador = new PerfilAdministrador();
    static boolean inicializacionCompleta = false;
    static int currentInitStep = 0;
    public ArrayList<VBox> edificiosDatos = new ArrayList<>();
    public ArrayList<TextField> edificiosID = new ArrayList<>();
    public ArrayList<VBox> estacionesDatos = new ArrayList<>();
    public ArrayList<TextField> estacionesID = new ArrayList<>();

    public Spinner<Integer> edificiosACrear;
    public Spinner<Integer> estacionesACrear;
    public Spinner<Integer> bateriaAlertaRobot;
    public Spinner<Integer> bateriaAlertaDrone;
    public Button randomEdificiosACrear;
    public Button randomEstacionesACrear;
    public Button siguienteStepOne;
    public VBox stepOne;
    public VBox stepTwo;
    public VBox stepThree;
    public VBox edificioTemplateForm;
    public FlowPane creacionEdificiosPanel;
    public FlowPane creacionEstacionesPanel;
    public AnchorPane relacionEventos;
    public AnchorPane reglasSistema;
    public AnchorPane estacionesSistema;
    public AnchorPane edificiosSistema;
    public VBox acciones;
    public ScrollPane parametrizacionPanel;
    public ChoiceBox<String> eventos;
    public ListView<String> accionesEnEvento;
    public ToggleButton accion1;
    public ToggleButton accion2;
    public ToggleButton accion3;
    public ToggleButton accion4;
    public ToggleButton regla1;
    public ToggleButton regla2;
    public TreeView<String> edificioDisplay;
    public TreeView<String> estacionDisplay;
    public Button cambiarPerfil;

    private void animateButton(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(25), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        SpinnerValueFactory<Integer> valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10, 3);
        edificiosACrear.setValueFactory(valueFactory);

        valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10, 3);
        estacionesACrear.setValueFactory(valueFactory);

        valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        bateriaAlertaRobot.setValueFactory(valueFactory);

        valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        bateriaAlertaDrone.setValueFactory(valueFactory);

        // para relacionar eventos con acciones
        ArrayList<Evento> eventosRegistrados = new ControlEventos().getEventos();
        ArrayList<Accion> accionesRegistradas = new ControlAcciones().getAcciones();

        // nombrar los botones
        accion1.setText(accionesRegistradas.get(0).getDescription());
        accion2.setText(accionesRegistradas.get(1).getDescription());
        accion3.setText(accionesRegistradas.get(2).getDescription());
        accion4.setText(accionesRegistradas.get(3).getDescription());

        // almacenar accion de referencia en boton
        accion1.setUserData(accionesRegistradas.get(0));
        accion2.setUserData(accionesRegistradas.get(1));
        accion3.setUserData(accionesRegistradas.get(2));
        accion4.setUserData(accionesRegistradas.get(3));

        for (Evento evento:  eventosRegistrados){
            eventos.getItems().add(evento.getDescripcion());
        }

        eventos.setOnAction(event -> {
            disableAllAcciones();
            actualizarAcciones();
            enableAccionesRelacionadas();
        });
        // para las reglas
        regla1.setOnAction(event -> {
            Regla reglaRelacionada = Reglas.obtenerReglas().get(0);
            Reglas.habilitarRegla(reglaRelacionada);
            bateriaAlertaRobot.setDisable(!((ToggleButton)event.getSource()).isSelected());
            System.out.println(reglaRelacionada.getDescription());
        });
        regla2.setOnAction(event -> {
            Regla reglaRelacionada = Reglas.obtenerReglas().get(1);
            Reglas.habilitarRegla(reglaRelacionada);
            bateriaAlertaDrone.setDisable(!((ToggleButton)event.getSource()).isSelected());

            System.out.println(reglaRelacionada.getDescription());
        });

        bateriaAlertaDrone.setDisable(!regla1.isSelected());
        bateriaAlertaRobot.setDisable(!regla2.isSelected());
        bateriaAlertaDrone.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Nuevo nivel de bateria para drone: " + newValue);
            DroneVigilancia.setBateriaMinima(newValue);
        });
        bateriaAlertaRobot.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Nuevo nivel de bateria para robot: " + newValue);
            RobotAsistente.setBateriaMinima(newValue);
        });

        if (!inicializacionCompleta) {
            parametrizacionPanel.setVisible(true);
            stepOne.setVisible(true);
            acciones.setVisible(false);
        } else {
            showActions();
            actualizarEdificios();
            actualizarEstaciones();
        }
    }

    private void actualizarEstaciones() {
        ControlEstaciones controlEstaciones = new ControlEstaciones();
        TreeItem<String> estaciones = new TreeItem<>("Estaciones");
        for (EstacionEnergia estacionEnergia: controlEstaciones.getEstaciones()) {
            TreeItem<String> estacion = new TreeItem<>(estacionEnergia.getNombre());
            estacion.getChildren().add(new TreeItem<>("ID: "+estacionEnergia.getID()));
            estacion.getChildren().add(new TreeItem<>("Calle: "+estacionEnergia.getUbicacion()));
            estacion.getChildren().add(new TreeItem<>("Capacidad: "+estacionEnergia.getCapacidad()));
            estacion.getChildren().add(new TreeItem<>("Descripción: "+estacionEnergia.getDescripcion()));
            estacion.getChildren().add(new TreeItem<>("Ocupacion: "+estacionEnergia.getOcupacion()));
            estaciones.getChildren().add(estacion);
        }
        estacionDisplay.setRoot(estaciones);
    }
    private void actualizarEdificios() {
        ControlEdificios  controlEdificios = new ControlEdificios();
        TreeItem<String> edificios = new TreeItem<>("Edificios");
        for (EdificioInteligente edificioInteligente: controlEdificios.getEdificios()){
            TreeItem<String> edificio = new TreeItem<>(edificioInteligente.getNombre());
            edificio.getChildren().add(new TreeItem<>("ID: "+edificioInteligente.getID()));
            edificio.getChildren().add(new TreeItem<>("Calle: "+edificioInteligente.getUbicacion()));
            edificio.getChildren().add(new TreeItem<>("Capacidad: "+edificioInteligente.getCapacidad()));

            if (!edificioInteligente.getCiudadanos().isEmpty()) {
                TreeItem<String> ciudadanosEnEdificio = new TreeItem<>("Ciudadanos ID:");
                for (Ciudadano ciudadano : edificioInteligente.getCiudadanos()) {
                    ciudadanosEnEdificio.getChildren().add(new TreeItem<>(String.valueOf(ciudadano.getID())));
                }
                edificio.getChildren().add(ciudadanosEnEdificio);
            }
            edificios.getChildren().add(edificio);
        }
        edificioDisplay.setRoot(edificios);
    }

    private void enableAccionesRelacionadas() {
        ArrayList<Evento> eventosRegistrados = new ControlEventos().getEventos();
        Evento eventoSeleccionado = eventosRegistrados.get(eventos.getSelectionModel().getSelectedIndex());
        if (eventoSeleccionado.getRespuesta() == null) return;

        accion1.setSelected(eventoSeleccionado.getRespuesta().contains((Accion) accion1.getUserData()));
        accion2.setSelected(eventoSeleccionado.getRespuesta().contains((Accion) accion2.getUserData()));
        accion3.setSelected(eventoSeleccionado.getRespuesta().contains((Accion) accion3.getUserData()));
        accion4.setSelected(eventoSeleccionado.getRespuesta().contains((Accion) accion4.getUserData()));
    }

    private void disableAllAcciones() {
        accion1.setSelected(false);
        accion2.setSelected(false);
        accion3.setSelected(false);
        accion4.setSelected(false);
    }


    private void actualizarAcciones() {
        ArrayList<Evento> eventosRegistrados = new ControlEventos().getEventos();
        ArrayList<Accion> accionesRegistradas = new ControlAcciones().getAcciones();
        Evento eventoSeleccionado = eventosRegistrados.get(eventos.getSelectionModel().getSelectedIndex());

        // Crear la observable list
        ObservableList<String> accionesRelacionadas = FXCollections.observableArrayList();
        if (eventoSeleccionado.getRespuesta() == null) return;

        for (Accion accion: eventoSeleccionado.getRespuesta()) {
            accionesRelacionadas.add(accion.getDescription());
        }
        accionesEnEvento.setItems(accionesRelacionadas);
    }

    @FXML
    protected void relacionarAccion(ActionEvent event) {
        ToggleButton button = (ToggleButton) event.getSource();
        Accion accion = (Accion) button.getUserData();

        ArrayList<Evento> eventosRegistrados = new ControlEventos().getEventos();
        Evento eventoSeleccionado = eventosRegistrados.get(eventos.getSelectionModel().getSelectedIndex());
        if (button.isSelected()) {
            System.out.println("Respuesta Registrada");
            eventoSeleccionado.RegistrarRespuesta(accion);
            actualizarAcciones();
            return;
        }
        eventoSeleccionado.RemoverRespuesta(accion);
        actualizarAcciones();
    }

    @FXML
    protected void edificiosACrearRandom(ActionEvent event) {
        int randomValue = ThreadLocalRandom.current().nextInt(3, 10 + 1);
        if (randomValue == estacionesACrear.getValueFactory().getValue()) {
            randomValue = ThreadLocalRandom.current().nextInt(3, 10 + 1);
            System.out.println("Random Value is the same, switching");
        }
        edificiosACrear.getValueFactory().setValue(randomValue);
        animateButton(randomEdificiosACrear);
    }

    @FXML
    protected void estacionesACrearRandom(ActionEvent event) {
        int randomValue = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        if (randomValue == estacionesACrear.getValueFactory().getValue()) {
            randomValue = ThreadLocalRandom.current().nextInt(5, 8 + 1);
            System.out.println("Random Value is the same, switching");
        }
        estacionesACrear.getValueFactory().setValue(randomValue);
        animateButton(randomEstacionesACrear);
    }

    protected boolean checkDuplicateID(ArrayList<TextField> textFields, TextField typingIn) {
        for (TextField textField : textFields) {
            if (textField.equals(typingIn)) continue;
            if (textField.getText().equals(typingIn.getText())) {
                return true;
            }
        }
        if (textFields.equals(edificiosID)) return ControlEdificios.consultar(typingIn.getText()) != null;
        if (textFields.equals(estacionesID)) return ControlEstaciones.consultar(typingIn.getText()) != null;
        return false;
    }
    protected boolean checkAllFieldsComplete(ArrayList<VBox> datos) {
        for (VBox vBox : datos) {
            for (Node Child: vBox.getChildren()) {
                if (Child instanceof TextField textField) {
                    textField.setStyle("-fx-border-color: #c7a5a5; -fx-border-width: 2px; -fx-border-radius: 3px;");
                    if (textField.getText().isBlank()) return false;
                    if (textField.getText().isEmpty()) return false;
                    if (Objects.equals(textField.getId(), "edificioID") || Objects.equals(textField.getId(), "estacionID")) {
                        if (ControlEdificios.consultar(textField.getText()) != null && (datos.equals(edificiosDatos))) return false;
                        if (ControlEstaciones.consultar(textField.getText()) != null && (datos.equals(estacionesDatos))) return false;
                    }
                    textField.setStyle("-fx-border-color: #b0e1b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
                }
            }
        }
        return true;
    }

    private void hideAllActions() {
        relacionEventos.setVisible(false);
        edificiosSistema.setVisible(false);
        estacionesSistema.setVisible(false);
        reglasSistema.setVisible(false);
    }
    @FXML
    protected void showReglas(ActionEvent event) {
        hideAllActions();
        reglasSistema.setVisible(true);
    }

    @FXML
    protected void showEventos(ActionEvent event) {
        hideAllActions();
        relacionEventos.setVisible(true);
    }

    @FXML
    protected void showEstaciones(ActionEvent event) {
        hideAllActions();
        estacionesSistema.setVisible(true);
    }

    @FXML
    protected void showEdificios(ActionEvent event) {
        hideAllActions();
        edificiosSistema.setVisible(true);
    }

    @FXML
    protected void nextInitStepTwo(ActionEvent event) {
        if (!checkAllFieldsComplete(edificiosDatos)) return;
        nextInitStep(event);
    }

    @FXML
    protected void nextInitStepThree(ActionEvent event) {
        if (!checkAllFieldsComplete(estacionesDatos)) return;
        nextInitStep(event);
    }

    @FXML
    protected void nextInitStep(ActionEvent event) {
        animateButton((Button) event.getSource());
        currentInitStep++;
        if (currentInitStep == 1) {
            stepOne.setVisible(false);
            stepTwo.setVisible(true);
            int buildings =  edificiosACrear.getValueFactory().getValue();

            for (int i = 1; i <= buildings; i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(NeoUrbeApp.class.getResource("agregarEdificioTemplate.fxml"));
                    Node buildingForm = loader.load();
                    buildingForm.setVisible(true);
                    buildingForm.setDisable(false);

                    VBox vboxForm =(VBox) buildingForm;
                    for (Node Child: vboxForm.getChildren()) {
                        if (Child instanceof TextField textField) {

                            if (textField.getId() == null) continue;
                            if (textField.getId().equals("edificioID")) {
                                edificiosID.add(textField);
                                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!checkDuplicateID(edificiosID, textField)) {
                                        textField.setStyle("-fx-border-color: #b0e1b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
                                        return;
                                    };
                                    textField.setStyle("-fx-border-color: #c7a5a5; -fx-border-width: 2px; -fx-border-radius: 3px;");
                                });
                            }
                            if (textField.getId().equals("edificioCapacidad")) {
                                textField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (!event.getCharacter().matches("\\d")) {
                                            event.consume(); // Prevent non-digit characters
                                        }
                                    }
                                });
                            }
                        }
                    };
                    edificiosDatos.add(vboxForm);
                    creacionEdificiosPanel.getChildren().add(buildingForm);
                } catch(IOException _) {}
            }
        }

        if (currentInitStep == 2) {
            stepTwo.setVisible(false);
            stepThree.setVisible(true);
            int stations =  estacionesACrear.getValueFactory().getValue();
            for (int i = 1; i <= stations; i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(NeoUrbeApp.class.getResource("agregarEstacionTemplate.fxml"));
                    Node stationForm = loader.load();
                    stationForm.setVisible(true);
                    stationForm.setDisable(false);

                    VBox vboxForm = (VBox) stationForm;

                    for (Node Child : vboxForm.getChildren()) {
                        if (Child instanceof TextField textField) {
                            if (textField.getId() != null && textField.getId().equals("estacionID")) {
                                estacionesID.add(textField);
                                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!checkDuplicateID(estacionesID, textField)) {
                                        textField.setStyle("-fx-border-color: #b0e1b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
                                        return;
                                    };
                                    textField.setStyle("-fx-border-color: #c7a5a5; -fx-border-width: 2px; -fx-border-radius: 3px;");
                                });
                            }
                            if (textField.getId() != null && textField.getId().equals("estacionCapacidad")) {
                                textField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (!event.getCharacter().matches("\\d")) {
                                            event.consume(); // Prevent non-digit characters
                                        }
                                    }
                                });
                            }
                        }
                    };

                    estacionesDatos.add(vboxForm);
                    creacionEstacionesPanel.getChildren().add(stationForm);
                } catch (IOException _) {
                }
            }

        }

        if (currentInitStep == 3) {
            stepThree.setVisible(false);
            crearEstaciones();
            crearEdificios();
            inicializacionCompleta = true;
            showActions();
        }
    }

    private void showActions() {
        parametrizacionPanel.setVisible(false);
        acciones.setVisible(true);
    }

    private void crearEdificios(){
        for (VBox vboxForm : edificiosDatos) {
            TextField ID = (TextField) vboxForm.lookup("#edificioID");
            TextField Nombre = (TextField) vboxForm.lookup("#edificioNombre");
            TextField Calle = (TextField) vboxForm.lookup("#edificioCalle");
            TextField Capacidad = (TextField) vboxForm.lookup("#edificioCapacidad");
            perfilAdministrador.crearEdificio(ID.getText(), Nombre.getText(), Calle.getText(), Integer.parseInt(Capacidad.getText()));
        }
        actualizarEdificios();
    }
    private void crearEstaciones() {
        for (VBox vboxForm : estacionesDatos) {
            TextField ID = (TextField) vboxForm.lookup("#estacionID");
            TextField Calle = (TextField) vboxForm.lookup("#estacionCalle");
            TextField Capacidad = (TextField) vboxForm.lookup("#estacionCapacidad");
            TextField Descripcion = (TextField) vboxForm.lookup("#estacionDescripcion");
            perfilAdministrador.crearEstacion(ID.getText(), Descripcion.getText(), Calle.getText(), Integer.parseInt(Capacidad.getText()));
        }
        actualizarEstaciones();
    }
}


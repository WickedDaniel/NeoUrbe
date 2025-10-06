package com.poo.neourbe;

import controlador.*;
import controlador.Perfiles.PerfilAdministrador;
import controlador.Perfiles.PerfilOperador;
import javafx.application.Application;
import modelo.Reglas;

public class Launcher {
    public static void main(String[] args) {
        Reglas.inicializarReglas();
        PerfilOperador perfilOperador = new PerfilOperador();
        PerfilAdministrador perfilAdministrador = new PerfilAdministrador();
        ControlEventos controlEventos = new ControlEventos();
        ControlAcciones controlAcciones = new ControlAcciones();
        ControlEdificios controlEdificios = new ControlEdificios();
        ControlTareas controlTareas = new ControlTareas();

        controlEventos.agregar("Colisión vehicular");
        controlEventos.agregar("Congestión vehicular");
        controlEventos.agregar("Desarrollo de obra pública");
        controlEventos.agregar("Derrames de sustancias peligrosas en carretera");
        controlEventos.agregar("Incendio");
        controlEventos.agregar("Presencia de humo");
        controlEventos.agregar("Presencia de gases");
        controlEventos.agregar("Accidente grave");
        controlEventos.agregar("Presencia de ambulancias en estado de emergencia");
        controlAcciones.agregar("Contactar a los bomberos");
        controlAcciones.agregar("Contactar a oficiales de tránsito a apersonarse al lugar");
        controlAcciones.agregar("Llamar al 911");
        controlAcciones.agregar("Convocar ambulancias");
        controlTareas.agregar("Agendar una cita médica",5);
        controlTareas.agregar("Asear el dormitorio",15);
        controlTareas.agregar("Elaborar una lista de alimentos para comprar",10);
        controlTareas.agregar("Regar las plantas",5);
        controlTareas.agregar("Dar un paseo con el ciudadano en el exterior",20);
        controlTareas.agregar("Asistir con el ciudadano a una reunión en otro edificio inteligente",25);

        perfilAdministrador.crearEstacion("1", "Rabble", "1", 1);
        perfilAdministrador.crearEdificio("1", "Raffles", "1", 1);
        perfilAdministrador.crearEdificio("2", "Rafflez", "1", 1);
        perfilOperador.agregarCiudadano("1");
        Application.launch(operadorInterfaz.class, args);
    }
}

package ar.edu.unlu.escobade15;


import Controlador.ControladorJuego;
import Modelo.Juego;
import Vista.VistaConsola;
import Vista.VistaConsola;

public class Main {
    public static void main(String[] args) {

        Juego juego = new Juego();
        VistaConsola vista = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(juego,vista);
        vista.iniciar();

    }
}

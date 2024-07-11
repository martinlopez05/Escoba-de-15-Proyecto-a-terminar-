package ar.edu.unlu.escobade15;


import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Juego;
import ar.edu.unlu.escobade15.Vista.VistaConsola;


public class Main {
    public static void main(String[] args) {

        Juego juego = new Juego();
        VistaConsola vistaConsola = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(juego,vistaConsola);
        vistaConsola.iniciar();

    }
}


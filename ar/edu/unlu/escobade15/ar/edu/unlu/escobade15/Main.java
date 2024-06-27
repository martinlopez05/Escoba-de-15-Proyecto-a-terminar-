package ar.edu.unlu.escobade15;


import Controlador.ControladorJuego;
import Modelo.Juego;
import Vista.VistaConsola;


public class Main {
    public static void main(String[] args) {

        Juego juego = new Juego();
        VistaConsola vistaConsola = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(juego,vistaConsola);
        vistaConsola.iniciar();




    }
}


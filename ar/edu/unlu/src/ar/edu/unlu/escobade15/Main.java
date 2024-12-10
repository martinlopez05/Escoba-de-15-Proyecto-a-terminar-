package ar.edu.unlu.escobade15;


import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Juego;
import ar.edu.unlu.escobade15.Vista.Ivista;
import ar.edu.unlu.escobade15.Vista.VentanaGrafica;
import ar.edu.unlu.escobade15.Vista.VistaConsola;


public class Main {
    public static void main(String[] args) {

        Juego juego = new Juego();
        Ivista vistaConsola = new VistaConsola();
        Ivista vistaGrafica = new VentanaGrafica();
        ControladorJuego controlador = new ControladorJuego(juego,vistaGrafica);





    }
}


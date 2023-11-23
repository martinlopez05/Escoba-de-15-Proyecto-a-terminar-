package ar.edu.unlu.escobade15;


import Controlador.ControladorAltajugador;
import Modelo.Baraja;
import Modelo.Carta;
import Modelo.Juego;
import Modelo.Jugador;
import Modelo.ModeloJugadores;
import Vista.VistaAltajugador;

public class Main {
    public static void main(String[] args) {
        
        


        Juego juego = new Juego();

        Jugador jugador1 = new Jugador("martin");
        Jugador jugador2  = new Jugador("juan");
        Jugador jugador3 = new Jugador("MATIAS");
        Jugador  jugador4 = new Jugador("pablo");


        juego.agregarJugador("Martin");
        juego.agregarJugador("juan");
        juego.agregarJugador("nancy");
        juego.agregarJugador("fevola");


        System.out.println("baraja iniciada");
        juego.baraja.mostrarBaraja();


        juego.RepartirCartas();

        juego.jugadorActual.mostrarCartasdeJugador();





        juego.mesajuego.mostrarCartasenMesa();

        juego.baraja.mostrarBaraja();








        


    }

}

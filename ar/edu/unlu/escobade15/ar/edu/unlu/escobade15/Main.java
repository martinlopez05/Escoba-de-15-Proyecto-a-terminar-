package ar.edu.unlu.escobade15;


import Controlador.ControladorAltajugador;
import Modelo.Baraja;
import Modelo.Carta;
import Modelo.Juego;
import Modelo.Jugador;
import Modelo.ModeloJugadores;
import Modelo.Palo;
import Vista.VistaAltajugador;

public class Main {
    public static void main(String[] args) {
        
        

        Baraja baraja = new Baraja();
        Juego juego = new Juego();

        Jugador jugador1 = new Jugador("martin");
        Jugador jugador2  = new Jugador("juan");
        Jugador jugador3 = new Jugador("MATIAS");
        Jugador  jugador4 = new Jugador("pablo");


        juego.AgregarJugador(jugador1);
        juego.AgregarJugador(jugador2);
        juego.AgregarJugador(jugador4);
        juego.AgregarJugador(jugador3);


        juego.RepartirCartas();

        System.out.println("CARTAS DEL JUGADOR UNO");
        for (Carta carta : jugador1.getCartasEnMano()) {
            System.out.println(carta.toString());
            
        }









       

        baraja.mostrarBaraja(); 




        ModeloJugadores modeloJugadores = new ModeloJugadores();
        VistaAltajugador vistaAltaJugador= new VistaAltajugador();
        ControladorAltajugador cntAltaJugador = new ControladorAltajugador(modeloJugadores, vistaAltaJugador);
        cntAltaJugador.IniciarAlta();
        cntAltaJugador.IniciarAlta();
        cntAltaJugador.IniciarAlta();
        cntAltaJugador.IniciarAlta();


        


    }

}

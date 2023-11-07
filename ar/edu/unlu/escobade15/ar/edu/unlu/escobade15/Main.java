package ar.edu.unlu.escobade15;


import Controlador.ControladorAltajugador;
import Modelo.Baraja;
import Modelo.Carta;
import Modelo.ModeloJugadores;
import Modelo.Palo;
import Vista.VistaAltajugador;

public class Main {
    public static void main(String[] args) {
        
        

        Baraja baraja = new Baraja();


        baraja.mezclarCartas();

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

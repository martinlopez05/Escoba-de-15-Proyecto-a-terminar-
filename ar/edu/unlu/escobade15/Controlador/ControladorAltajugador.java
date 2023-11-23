package Controlador;

import Modelo.Juego;
import Modelo.Jugador;
import Vista.VistaAltajugador;

public class ControladorAltajugador {
    Juego modelo;
    VistaAltajugador vista;

    public ControladorAltajugador(Juego modelo, VistaAltajugador vista){
        this.modelo=modelo;
        this.vista=vista;
    }

    /*public void IniciarAlta(){
        Jugador jugador = vista.SolcitarDatosJugador();
        modelo.agregarJugador(jugador);
    }*/
    
    public void agregarJugador(String nombre){
        modelo.agregarJugador(nombre);
    }

    public void nofiticar(Object o){

    }
    
}

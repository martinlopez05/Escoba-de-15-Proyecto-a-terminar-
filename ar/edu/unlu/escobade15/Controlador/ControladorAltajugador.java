package Controlador;

import Modelo.Jugador;
import Modelo.ModeloJugadores;
import Vista.VistaAltajugador;

public class ControladorAltajugador {
    ModeloJugadores modelo;
    VistaAltajugador vista;

    public ControladorAltajugador(ModeloJugadores modelo, VistaAltajugador vista){
        this.modelo=modelo;
        this.vista=vista;
    }

    public void IniciarAlta(){
        Jugador jugador = vista.SolcitarDatosJugador();
        modelo.AgregarJugador(jugador);
    }
    

    
}

package Controlador;

import Modelo.Carta;
import Modelo.Juego;
import Modelo.Jugador;
import Modelo.Observer;
import Vista.VistaAltajugador;

import java.util.Scanner;

public class ControladorJuego implements Observer {
    Juego modelo;
    VistaAltajugador vista;

    public ControladorJuego(Juego modelo, VistaAltajugador vista){
        this.modelo=modelo;
        this.vista=vista;
    }

    public void jugarTurno() {
        if (!modelo.hayCartasenlaMesa()) {
            vista.Mostrarmensaje("No hay cartas en la mesa, elija una para dejar:");
            Carta cartaelegida = modelo.jugadorActual.elegirCartaaBajar();
            modelo.dejarCartaenMesa(cartaelegida);
            modelo.jugadorActual.sacarCarta(cartaelegida);
        } else if (modelo.sepuedeEscobadeMano()) {
            vista.Mostrarmensaje("¡Hay escoba de mano, felicitaciones!");
            modelo.hacerEscobadeMano();
        } else {
            Carta cartaelegida = modelo.jugadorActual.elegirCartaaBajar();
            if (modelo.sepuedeEscoba(cartaelegida, modelo.mesajuego.getCartasMesa())) {
                vista.Mostrarmensaje("Se puede hacer escoba");
                Scanner sc = new Scanner(System.in);
                vista.Mostrarmensaje("¿Quieres hacer escoba? (Sí/No):");
                String respuesta = sc.next().toLowerCase();
                if ("si".equals(respuesta)) {
                    modelo.hacerEscoba(cartaelegida, modelo.mesajuego.getCartasMesa());
                } else {
                    modelo.dejarCartaenMesa(cartaelegida);
                    modelo.jugadorActual.sacarCarta(cartaelegida);
                }
            }
        }

        modelo.actualizaTurno();
    }


    public void agregarJugador(String nombre){
        modelo.agregarJugador(nombre);
    }



    @Override
    public void update() {

    }
}

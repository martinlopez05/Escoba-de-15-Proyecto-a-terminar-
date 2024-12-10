package ar.edu.unlu.escobade15.Controlador;

import ar.edu.unlu.escobade15.Modelo.*;
import ar.edu.unlu.escobade15.Vista.Ivista;
import ar.edu.unlu.escobade15.util.PatronObserver.Observer;
import ar.edu.unlu.escobade15.Vista.VistaConsola;

import java.util.List;

public class ControladorJuego implements Observer {
    Juego modelo;
    Ivista vista;


    public ControladorJuego(Juego modelo,Ivista vista){
        this.modelo=modelo;
        this.vista = vista;
        vista.setControlador(this);
        modelo.agregarObservador(this);
    }



    public void iniciarjuego() {
        if(modelo.sePuedeIniciarpartida()){
            modelo.repartirMano();
            jugarPartida();

        }

    }






    public void jugarPartida() {

        while (!modelo.getBaraja().esVacia()) {
            if(modelo.jugadoresTienenCartas()) {

                vista.mostrarTurno();
                Mesa mesa = modelo .getMesajuego();
                vista.mostrarMesa(mesa.getCartasMesa());
                vista.mostrarCartajugador(modelo.getJugadorActual());

                if(modelo.sepuedeEscobadeMano()){
                    modelo.hacerEscobaDeMano();
                }

                vista.opcionJugador();



            }
            else{
                modelo.repartirMano();
            }
            modelo.actualizaTurno();

        }


        juegoTerminado();
    }


    public Jugador getJugadorActual(){
        return modelo.getJugadorActual();
    }

    public Mesa getMesaActual(){
        return modelo.getMesajuego();
    }




    public void agregarJugador(String nombre) {

        if(modelo.obtenercantJugadores() < 4){
            modelo.agregarJugador(nombre);
        }
        if (modelo.obtenercantJugadores() == 4) {
            vista.mostrarMensaje("Alcanzo la capacidad Maxima de jugadores");

        }

    }

    public void bajaCarta(){
        Carta carta = vista.solicitarCartaaBajar(modelo.cartaEnManoJugador());
        modelo.jugadorBajaCarta(carta);
    }


    public void recogeCartaMesa(){
        Carta carta = vista.solicitarCartaArecoger(modelo.cartasMesa());
    }







    public void juegoTerminado(){
        vista.mostrarPuntosjugador(modelo.getJugadores());
        modelo.sumarPuntoalFinal();
        vista.mostrarGanador(modelo.obtenerGanador());
    }





    @Override
    public void update(Object dato) {
        if(dato == Evento.JUGADOR_AGREGADO){
            vista.mostrarMensaje("Jugador agregado correctamente ");

        }
        if(dato == Evento.FIN_PARTIDA){
            vista.mostrarMensaje("Fin de la partida...");
            vista.mostrarMensaje("***Recuento de puntos***");


        }
        if(dato== Evento.CAMBIO_DE_TURNO){
            vista.mostrarMensaje("\nCambiando de turno...");
        }

        if(dato==Evento.REPARTIR_CARTAS){
            vista.mostrarMensaje("Repartiendo cartas...");
        }

        if(dato==Evento.HAY_ESCOBA){
            vista.mostrarMensaje("¡Puede hacer escoba!");
        }

        if(dato == Evento.FALTA_JUGADORES){
            vista.mostrarMensaje("Faltan jugadores...");
        }


        if(dato == Evento.JUGADOR_SIN_CARTAS){
            vista.mostrarMensaje("El jugador no tiene mas cartas en mano");
        }

        if(dato == Evento.NO_HAY_EN_MESA){
            vista.mostrarMensaje("No hay cartas en mesas");
        }

        if(dato == Evento.HAY_ESCOBA_DE_MANO){
            vista.mostrarMensaje("Felicitaciones hay escoba de mano!");

        }




    }

}

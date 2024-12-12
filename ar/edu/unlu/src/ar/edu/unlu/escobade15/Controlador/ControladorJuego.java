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

    public boolean sePuedeIniciar(){
        return modelo.sePuedeIniciarpartida();
    }

    public boolean jugadoresSinCartas(){
        return modelo.jugadoresSinCartas();
    }


    public Jugador getJugadorActual(){
        return modelo.getJugadorActual();
    }

    public Mesa getMesaActual(){
        return modelo.getMesajuego();
    }

    public boolean faltanCartas(){
        return modelo.faltanCartas();
    }


    public void repartirCartas(){
        modelo.repartirMano();
    }

    public boolean barajaEsVacia(){
        return modelo.barajaEsVacia();
    }

    public void actualizarTurno(){
        modelo.actualizarTurno();
    }

    public void comenzarAjugar(){
        vista.mostrarTurno();
        vista.mostrarMesa(getMesaActual().getCartasMesa());
        vista.mostrarCartajugador(getJugadorActual());
        vista.opcionJugador();
        actualizarTurno();

    }


    public void agregarJugador(String nombre) {
        modelo.agregarJugador(nombre);

    }

    public void bajaCarta(Carta carta){
        modelo.jugadorBajaCarta(carta);

    }

    public void iniciarPartida(){
        modelo.iniciarPartida();
    }


   public List<Carta> getCartasJugadorActual(){
        return modelo.cartaEnManoJugador();
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

        if(dato == Evento.CAPACIDAD_ALCAZADA_JUGADORES){
            vista.mostrarMensaje("No se pueden agregar mas jugadores");
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

        if(dato == Evento.PARTIDA_INICIADA){
            
        }




    }

}

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




    public void SeleccionarCartaAjugar(Carta carta){
        modelo.seleccionarCartaJugar(carta);
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

   public int obtenerCantidadJugadores(){
        return modelo.obtenercantJugadores();
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
            vista.mostrarMensaje("####### PARTIDA INICIADA #######");
        }

        if(dato == Evento.SUMAN_15){
              vista.mostrarMensaje("La carta seleccionada suman 15 con carta de la mesa... ¡Felicitaciones!");
        }
        if(dato == Evento.NO_SUMAN_15){
            vista.mostrarMensaje("Su carta no suma 15 con ninguna/s de la mesa...");
        }

        if(dato == Evento.SUMAN_15_CON_TODAS){
            vista.mostrarMensaje("Su carta suma 15 con TODAS  de la mesa... ¡Felicitaciones!");
        }

        if(dato == Evento.JUGADOR_SUMA_PUNTO){
            System.out.println("¡El Jugador " + getJugadorActual().getNombreJugador() + "suma punto!");
        }


    }

}

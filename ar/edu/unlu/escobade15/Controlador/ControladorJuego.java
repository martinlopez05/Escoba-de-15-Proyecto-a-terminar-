package Controlador;

import Modelo.Carta;
import Modelo.Evento;
import Modelo.Juego;
import util.PatronObserver.Observer;
import Vista.VistaConsola;

public class ControladorJuego implements Observer {
    Juego modelo;
    VistaConsola vista;

    public ControladorJuego(Juego modelo,VistaConsola vista){
        this.modelo=modelo;
        this.vista = vista;
        vista.setControlador(this);
        modelo.agregarObservador(this);
    }



    public void iniciarjuego() {

        if (modelo.obtenercantJugadores() < 2) {
            vista.mostrarmensaje("¡No se puede iniciar el juego por falta de jugadores!");
            return;
        }

        modelo.repartirMano();

        jugarPartida();
    }




    public void jugarPartida() {

        while (!modelo.getBaraja().esVacia()) {
            if(modelo.jugadoresTienenCartas()) {


                vista.mostrarTurno(modelo.getJugadorActual());
                vista.mostrarMesa(modelo.getMesajuego().getCartasMesa());
                vista.mostrarCartajugador(modelo.getJugadorActual());
                int opcion= vista.elegirOpcionJugador();


                switch (opcion) {
                    case 1:
                        Carta carta = vista.solicitarCartaaBajar(modelo.cartasdeJugadorActual());
                        modelo.jugadorBajaCarta(carta);
                        break;
                    case 2:
                        break;


                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            }
            else{
                modelo.repartirMano();
            }
            modelo.actualizaTurno();

        }


        seTermino();
    }




    public void agregarJugador(){
        String nombre = vista.solicitarNombreJugador();
        modelo.agregarJugador(nombre);

    }



    public void seTermino(){
        vista.mostrarPuntosjugador(modelo.getJugadores());
        modelo.sumarPuntoalFinal();
        vista.mostrarGanador(modelo.obtenerGanador());
    }





    @Override
    public void update(Object dato) {
        if(dato == Evento.JUGADOR_AGREGADO){
            vista.mostrarmensaje("Jugador agregado correctamente");

        }
        if(dato == Evento.FIN_PARTIDA){
            vista.mostrarmensaje("Fin de la partida...");
            vista.mostrarmensaje("***Recuento de puntos***");


        }
        if(dato== Evento.CAMBIO_DE_TURNO){
            vista.mostrarmensaje("\nCambiando de turno...");
        }

        if(dato==Evento.REPARTIR_CARTAS){
            vista.mostrarmensaje("Repartiendo cartas...");
        }

        if(dato==Evento.HAY_ESCOBA){
            vista.mostrarmensaje("¡Puede hacer escoba!");
        }




    }




}

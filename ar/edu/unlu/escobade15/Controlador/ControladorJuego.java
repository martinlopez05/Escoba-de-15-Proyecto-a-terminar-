package Controlador;

import Modelo.Carta;
import Modelo.Evento;
import Modelo.Juego;
import Modelo.Jugador;
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
        if(modelo.sePuedeIniciarpartida()){
            modelo.repartirMano();
            jugarPartida();

        }

    }










    public void jugarPartida() {

        while (!modelo.getBaraja().esVacia()) {
            if(modelo.jugadoresTienenCartas()) {


                vista.mostrarTurno();
                vista.mostrarMesa(modelo.getMesajuego().getCartasMesa());
                vista.mostrarCartajugador(this.getJugadorActual());
                int opcion= vista.elegirOpcionJugador();


                switch (opcion) {
                    case 1:
                        Carta carta = vista.solicitarCartaaBajar(modelo.cartasdeJugadorActual());
                        modelo.jugadorBajaCarta(carta);
                        break;
                    case 2:
                        Carta carta1 = vista.solicitarCartaArecoger(modelo.getMesajuego().getCartasMesa());
                        modelo.jugadorAgarraCarta(carta1);
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


        juegoTerminado();
    }


    public Jugador getJugadorActual(){
        return modelo.getJugadorActual();
    }




    public void agregarJugador(String nombre) {

        if(modelo.obtenercantJugadores() < 4){
            modelo.agregarJugador(nombre);

        }
        if (modelo.obtenercantJugadores() == 4) {
            vista.mostrarmensaje("Alcanzo la capacidad Maxima de jugadores");

        }

    }



    public void juegoTerminado(){
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

        if(dato == Evento.FALTA_JUGADORES){
            vista.mostrarmensaje("Faltan jugadores...");
        }


        if(dato == Evento.JUGADOR_SIN_CARTAS){
            vista.mostrarmensaje("El jugador no tiene mas cartas en mano");
        }

        if(dato == Evento.NO_HAY_EN_MESA){
            vista.mostrarmensaje("No hay cartas en mesas");
        }




    }




}

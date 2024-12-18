package ar.edu.unlu.escobade15.Controlador;

import ar.edu.unlu.escobade15.Modelo.*;
import ar.edu.unlu.escobade15.Vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.List;

public class ControladorJuego implements IControladorRemoto {
    IJuego modelo;
    IVista vista;


    public ControladorJuego( IVista vista){
        this.vista = vista;
        vista.setControlador(this);

    }

    public boolean sePuedeIniciar() throws RemoteException {
        return modelo.sePuedeIniciarpartida();
    }

    public boolean jugadoresSinCartas() throws RemoteException {
        return modelo.jugadoresSinCartas();
    }


    public Jugador getJugadorActual() throws RemoteException {
        return modelo.getJugadorActual();

    }

    public Mesa getMesaActual() throws RemoteException {
        return modelo.getMesajuego();
    }

    public List<Jugador> getJugadores() throws RemoteException {
        return modelo.getJugadores();
    }


    public void repartirCartas(){
        try{
            modelo.repartirMano();
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public boolean barajaEsVacia() throws RemoteException {
        return modelo.barajaEsVacia();
    }

    public void actualizarTurno(){
        try{
            modelo.actualizarTurno();
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void comenzarAjugar() {
        try {
            vista.mostrarTurno();
            vista.mostrarMesa(getMesaActual().getCartasMesa());
            vista.mostrarCartajugador(getJugadorActual());
            if (modelo.sepuedeEscobadeMano()) {
                modelo.hacerEscobaDeMano();
                actualizarTurno();
            } else {
                vista.opcionJugador();
                actualizarTurno();
            }
        }
        catch (RemoteException e){
            e.printStackTrace();
        }

    }


    public void terminarRonda(){

        try{
            modelo.terminarRonda();
            for(Jugador jugador : getJugadores()){
                vista.mostrarMasoRonda(jugador);
            }
            vista.mostrarPuntosJugadores(getJugadores());
            modelo.iniciarNuevaRonda();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


    public boolean rondaTerminada() throws RemoteException {
        return modelo.rondaTerminada();
    }

    public boolean partidaTerminada() throws RemoteException {
        return modelo.partidaTerminada();
    }


    public void jugarPartida() throws RemoteException {
        if (obtenerCantidadJugadores() < 2) {
            vista.mostrarMensaje("¡FALTAN JUGADORES! ---- mínimo de jugadores (2) ---- máximo de jugadores (4)");
        } else {
            iniciarPartida();
            while (!partidaTerminada()) {

                while (!rondaTerminada()) {
                    comenzarAjugar();
                    if (jugadoresSinCartas()) {
                        repartirCartas();
                    }
                }
               terminarRonda();
            }
            vista.mostrarMensaje("GANADOR EL JUGADOR " + modelo.obtenerGanador().getNombreJugador());

            try{
                modelo.terminarPartida();
            }
            catch (RemoteException e){
                e.printStackTrace();
            }


        }
    }



    public void SeleccionarCartaAjugar(Carta carta){
        try{
            modelo.seleccionarCartaJugar(carta);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }


    public void agregarJugador(String nombre) {
        try{
            modelo.agregarJugador(nombre);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void bajaCarta(Carta carta){
        try{
            modelo.jugadorBajaCarta(carta);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }

    }

    public void iniciarPartida(){
        try{
            modelo.iniciarPartida();
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }


   public List<Carta> getCartasJugadorActual() throws RemoteException {
        return modelo.cartaEnManoJugador();
   }

   public int obtenerCantidadJugadores() throws RemoteException {
        return modelo.obtenercantJugadores();
   }


    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
           this.modelo = (IJuego) modeloRemoto;
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {
        if(o == Evento.JUGADOR_AGREGADO){
            vista.mostrarMensaje("Jugador agregado correctamente ");

        }
        if(o == Evento.FIN_DE_RONDA){
            vista.mostrarMensaje("Fin de la Ronda...");
            vista.mostrarMensaje("***Recuento de puntos***");


        }
        if(o== Evento.CAMBIO_DE_TURNO){
            vista.mostrarMensaje("\nCambiando de turno...");
        }

        if(o==Evento.REPARTIR_CARTAS){
            vista.mostrarMensaje("Repartiendo cartas...");
        }

        if(o==Evento.HAY_ESCOBA){
            vista.mostrarMensaje("¡Puede hacer escoba!");
        }

        if(o == Evento.FALTA_JUGADORES){
            vista.mostrarMensaje("Faltan jugadores...");
        }

        if(o == Evento.CAPACIDAD_ALCAZADA_JUGADORES){
            vista.mostrarMensaje("No se pueden agregar mas jugadores");
        }


        if(o == Evento.JUGADOR_SIN_CARTAS){
            vista.mostrarMensaje("El jugador no tiene mas cartas en mano");
        }

        if(o == Evento.NO_HAY_EN_MESA){
            vista.mostrarMensaje("No hay cartas en mesas");
        }

        if(o == Evento.HAY_ESCOBA_DE_MANO){
            vista.mostrarMensaje("HAY ESCOBA DE MANO ... ¡Felicitaciones!");

        }

        if(o == Evento.PARTIDA_INICIADA){
            vista.mostrarMensaje("####### PARTIDA INICIADA #######");
        }

        if(o == Evento.SUMAN_15){
            vista.mostrarMensaje("La carta seleccionada suman 15 con carta de la mesa... ¡Felicitaciones!");
        }
        if(o == Evento.NO_SUMAN_15){
            vista.mostrarMensaje("Su carta no suma 15 con ninguna/s de la mesa...");
            vista.opcionJugador();
        }

        if(o == Evento.SUMAN_15_CON_TODAS){
            vista.mostrarMensaje("HAY ESCOBA ... ¡Felicitaciones!");
        }

        if(o == Evento.JUGADOR_SUMA_PUNTO){
            vista.mostrarMensaje("¡El Jugador " + getJugadorActual().getNombreJugador() + " suma punto!");
        }

        if(o == Evento.RONDA_NUEVA_INICIADA){
            vista.mostrarMensaje("\n####### NUEVA RONDA INICIADA #######");
        }

        if(o == Evento.PARTIDA_FINALIZADA){
            vista.mostrarMensaje("\n####### PARTIDA FINALIZADA #######");
        }
    }
}

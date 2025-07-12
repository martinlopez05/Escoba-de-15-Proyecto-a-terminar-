package ar.edu.unlu.escobade15.Controlador;

import ar.edu.unlu.escobade15.Modelo.*;
import ar.edu.unlu.escobade15.Vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.List;

public class ControladorJuego implements IControladorRemoto {
    IJuego modelo = new Juego();
    IVista vista;


    public ControladorJuego( IVista vista){
        this.vista = vista;
        vista.setControlador(this);

    }


    public void reinciarPartida() throws RemoteException {
        modelo.reiniciarPartida();
    }

    public Jugador getJugadorActual() throws RemoteException {
        return modelo.getJugadorActual();

    }

    public List<Carta> getCartasMesa() throws RemoteException {
        return modelo.getMesajuego().getCartasMesa();
    }

    public List<Jugador> getJugadores() throws RemoteException {
        return modelo.getJugadores();
    }


    public String getNombreJugadorAnfitrion() throws RemoteException {
        return modelo.getNombreJugadorAnfitrion();
    }


    public void actualizarTurno(){
        try{
            modelo.actualizarTurno();
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public int obtenerCantJugadores() throws RemoteException {
        return modelo.obtenercantJugadores();
    }


    public List<Carta> getCartasEnMano() throws RemoteException {
        return modelo.getJugadorActual().getCartasEnMano();
    }


    public void iniciarNuevaRonda() throws RemoteException {
        modelo.iniciarNuevaRonda();
    }


    public String getNombreJugadorAgregado() throws RemoteException {
        return modelo.getNombreJugadorAgregado();
    }


    public void jugarCarta(Carta carta){
        try{
            modelo.jugarCarta(carta);
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

   public String getNombreGanador() throws RemoteException {
        return modelo.getNombreJugadorGanador();
   }


    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
           this.modelo = (IJuego) modeloRemoto;
    }



    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {

        if(o == Evento.FIN_DE_RONDA){
            vista.terminarRonda();
        }

        if(o== Evento.CAMBIO_DE_TURNO){
            vista.comenzarTurnos();
        }

        if(o == Evento.RONDA_NUEVA_INICIADA){
            vista.comenzarTurnos();
        }

        if(o == Evento.PARTIDA_INICIADA){
            vista.comenzarTurnos();
        }

        if(o == Evento.NO_SUMA_NINGUNA_15){
            vista.mostrarNoSuma15JugadorActual();
        }

        if(o == Evento.SUMAN_15_CON_TODAS){
            vista.mostrarHizoEscobaJugadorActual();
        }

        if(o == Evento.PARTIDA_FINALIZADA){
            vista.terminarPartida();
        }

        if(o == Evento.JUGADOR_AGREGADO){
            vista.jugadorSeUnioApartida();
        }

        if(o == Evento.SE_PUEDE_INICIAR){
            vista.habilitarInicioPartida();
        }

        if(o == Evento.SUMAN_15) {
            vista.mostrarSuma15JugadorActual();
        }

        if(o == Evento.CAPACIDAD_ALCAZADA_JUGADORES){

        }
    }
}

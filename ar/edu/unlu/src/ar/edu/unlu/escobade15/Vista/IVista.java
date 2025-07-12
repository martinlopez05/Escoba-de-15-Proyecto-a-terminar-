package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import java.rmi.RemoteException;
import java.util.List;

public interface IVista {
    void setControlador(ControladorJuego controlador);
    void iniciar() throws RemoteException;

    boolean esAnfitrion() throws RemoteException;
    void solicitarNombre();
    void mostrarMenu();

    void mostrarReglas();

    void mostrarTurno() throws RemoteException;
    void mostrarMesa() throws RemoteException;
    void solicitarCartaAjugar() throws RemoteException;
    void solicitarCartaABajar() throws RemoteException;
    void mostrarCartajugador() throws RemoteException;
    void comenzarTurnos() throws RemoteException;

    // Le enviamos mensaje al jugador actual

    // Le enviamos mensaje al jugador actual
    void mostrarSuma15JugadorActual() throws RemoteException;

    void mostrarHizoEscobaJugadorActual() throws RemoteException;

    void mostrarNoSuma15JugadorActual() throws RemoteException;

    void mostrarMasoRonda() throws RemoteException;
    void mostrarGanador(Jugador jugador);
    void terminarRonda() throws RemoteException;
    void unirseApartida() throws RemoteException;
    void jugadorSeUnioApartida() throws RemoteException;

    void habilitarInicioPartida() throws RemoteException;

    void terminarPartida() throws RemoteException;

    void reiniciarVista();
}

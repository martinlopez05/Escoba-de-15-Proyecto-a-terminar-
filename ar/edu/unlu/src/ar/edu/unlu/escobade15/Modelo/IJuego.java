package ar.edu.unlu.escobade15.Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IJuego extends Serializable, IObservableRemoto {
    String getNombreJugadorAnfitrion() throws RemoteException;

    //retorna una lista de los jugador en la partida
    List<Jugador> getJugadores() throws RemoteException;

    //retorna el Jugador Actual -> el que esta en su turno de juego
    Jugador getJugadorActual() throws RemoteException;

    Mesa getMesajuego() throws RemoteException;

    Baraja getBaraja() throws RemoteException;

    String getNombreJugadorAgregado() throws RemoteException;

    String getNombreJugadorGanador() throws RemoteException;

    void reiniciarPartida() throws RemoteException;

    // metodo para repartir cartas a cada jugador
    void repartirMano() throws RemoteException;

    //metodo para repartir cartas en mesa
    void repartirMesa() throws RemoteException;

    //metodo que retorna true  si los jugadores se quedan sin carta
    boolean jugadoresSinCartas() throws RemoteException;

    //metodo para iniciar una partida
    void iniciarPartida() throws RemoteException;

    //metodo que retorna true si la baraja se queda sin cartas
    boolean barajaEsVacia() throws RemoteException;

    //metodo para iniciar Nueva Ronda
    void iniciarNuevaRonda() throws RemoteException;

    boolean sePuedeIniciarPartida() throws RemoteException;

    boolean partidaTerminada() throws RemoteException;


    //metodo que cambia al jugadorActual en la partida
    void actualizarTurno() throws RemoteException;

    //metodo donde un jugador baja una carta a la mesa
    void jugadorBajaCarta(Carta carta) throws RemoteException;

    //metodo para obtener la combinacion de carta/s de la mesa que suman 15 con la carta del jugador
    List<Carta> obtenerCombinacion15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException;

    //metodo que retorna true si se encuentra alguna combinacion de carta/s de la mesa que suman 15 con la del jugador
    boolean suman15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException;

    //metodo donde el jugador selecciona una carta de su mano y suma 15 con alguna/s de la mesa
    void jugarCarta(Carta cartaJugador) throws RemoteException;

    //metodo para generar las combinaciones de las cartas de la mesa y se retorna una lista de esas combinaciones
    default List<List<Carta>> generarCombinaciones(List<Carta> cartas) throws RemoteException {
        List<List<Carta>> combinacionesPosibles = new ArrayList<>();
        int n = cartas.size();
        for (int i = 0; i < (1 << n); i++) {
            List<Carta> subconjuntoCartas = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subconjuntoCartas.add(cartas.get(j));
                }
            }
            combinacionesPosibles.add(subconjuntoCartas);
        }
        return combinacionesPosibles;
    }

    //retorna las cartas de la mesa
    List<Carta> cartasMesa() throws RemoteException;

    //retorna las carta en mano del jugador
    List<Carta> cartaEnManoJugador() throws RemoteException;

    //metodo para agregar un jugador dado su nombre
    void agregarJugador(String nombre) throws RemoteException;

    //metodo en el cual se sortea el turno en el inicio de la partida
    void sortearTurno() throws RemoteException;

    //metodo para obtener el ganador de la partida
    String obtenerGanador() throws RemoteException;

    void terminarRonda() throws RemoteException;

    void terminarPartida() throws RemoteException;

    //metodo para obtener la cantidad de jugadores
    int obtenercantJugadores() throws RemoteException;

    //metodo que retorna true si hay escoba de mano (todas las cartas de la mesa suman exactamente 15)
    boolean sepuedeEscobadeMano() throws RemoteException;

    //metodo donde se realiza el manejo de las cartas si hay escoba de mano
    void hacerEscobaDeMano() throws RemoteException;

    //metodo en el cual se suma un punto al jugador actual
    void sumarpunto(int punto) throws RemoteException;

    //metodo para sumar puntos al final de la Ronda
    void sumarPuntoalFinal() throws RemoteException;
}

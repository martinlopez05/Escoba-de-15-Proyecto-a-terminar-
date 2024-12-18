package ar.edu.unlu.escobade15.Modelo;

import ar.edu.unlu.escobade15.util.PatronObserver.Observable;
import ar.edu.unlu.escobade15.util.PatronObserver.Observer;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends ObservableRemoto implements IJuego {

    public final int puntuacionParaGanar = 4;

    private Baraja baraja;
    private List<Jugador> jugadores;
    public Jugador jugadorActual;
    public Mesa mesajuego;


    public Juego(){
        this.baraja = new Baraja();
        jugadores = new ArrayList<>();
        this.mesajuego = new Mesa();
    }

    //retorna una lista de los jugador en la partida
    @Override
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    //retorna el Jugador Actual -> el que esta en su turno de juego
    @Override
    public Jugador getJugadorActual() {
        return jugadorActual;
    }


    @Override
    public Mesa getMesajuego() {
        return mesajuego;
    }

    @Override
    public Baraja getBaraja() {
        return baraja;
    }



    // metodo para repartir cartas a cada jugador
    @Override
    public void repartirMano() throws RemoteException {

        for (Jugador jugador : jugadores){
            for(int i=0 ; i<3 ; i++){
                if(!barajaEsVacia()){
                    jugador.recibirCarta(baraja.sacarCarta());
                }
                else{
                    break;
                }
            }
        }

        notificarObservadores(Evento.REPARTIR_CARTAS);
    }

    //metodo para repartir cartas en mesa
    @Override
    public void repartirMesa(){
        for(int i=0; i<4 ; i++){
            if(!barajaEsVacia()){
                mesajuego.agregarCarta(baraja.sacarCarta());
            }
            else{
                break;
            }
        }
    }


    //metodo que retorna true  si los jugadores se quedan sin carta
    @Override
    public boolean jugadoresSinCartas(){
        return jugadores.stream().allMatch(Jugador::faltanCartasenMano);
    }


    //metodo para iniciar una partida
    @Override
    public void iniciarPartida() throws RemoteException {
        if(sePuedeIniciarpartida()) {
            baraja.mezclarCartas();
            notificarObservadores(Evento.PARTIDA_INICIADA);
            repartirMano();
            repartirMesa();
            sortearTurno();
        }
    }

    //metodo que retorna true si la baraja se queda sin cartas
    @Override
    public boolean barajaEsVacia(){
        return baraja.esVacia();
    }


    //metodo para iniciar Nueva Ronda
    @Override
    public void iniciarNuevaRonda() throws RemoteException {
        for(Jugador jugador: jugadores){
            jugador.getMasoRonda().vaciarMaso();
        }
        mesajuego.vaciar();
        Baraja baraja = new Baraja();
        this.baraja = baraja;
        repartirMesa();
        repartirMano();
        notificarObservadores(Evento.RONDA_NUEVA_INICIADA);
    }

    @Override
    public boolean rondaTerminada(){
        if(barajaEsVacia()){
            return true;
        }
        return false;
    }

    @Override
    public boolean partidaTerminada(){
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() >= puntuacionParaGanar) {
                return true;
            }
        }
        return false;
    }




    /*
    public boolean faltanCartas(){
        boolean faltanCartasJugadores = false;
        for(Jugador jugador : jugadores){
            if(jugador.faltanCartasenMano()){
                return true;
            }
        }
        if(mesajuego.mesaVacia() || faltanCartasJugadores ){
            return true;
        }
        else{
            return false;
        }

    }*/


    //metodo que retorna si se puede empezar una partida
    @Override
    public boolean sePuedeIniciarpartida() throws RemoteException {
        if(jugadores.size() >= 2){
            return true;
        }
        else{
            notificarObservadores(Evento.FALTA_JUGADORES);
            return false;

        }
    }


    //metodo que cambia al jugadorActual en la partida
    @Override
    public void actualizarTurno() throws RemoteException {
        int indiceActual = jugadores.indexOf(jugadorActual);
        if (indiceActual != -1) {
            int siguienteIndice = (indiceActual + 1) % jugadores.size();
            jugadorActual = jugadores.get(siguienteIndice);
            notificarObservadores(Evento.CAMBIO_DE_TURNO);
        }

    }


    //metodo donde un jugador baja una carta a la mesa
    @Override
    public void jugadorBajaCarta(Carta carta) throws RemoteException {
        if (!jugadorActual.getCartasEnMano().isEmpty()) {
            mesajuego.agregarCarta(carta);
            jugadorActual.sacarCartaMano(carta);
        } else {
            notificarObservadores(Evento.JUGADOR_SIN_CARTAS);
        }
    }

    //metodo para obtener la combinacion de carta/s de la mesa que suman 15 con la carta del jugador
    @Override
    public List<Carta> obtenerCombinacion15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException {
        List<List<Carta>> subconjuntos = generarCombinaciones(cartasMesa);

        for(List<Carta> subconjunto : subconjuntos){
            int suma = cartaJugador.getValor();
            for(Carta carta : subconjunto){
                suma+=carta.getValor();
            }
            if(suma == 15){
                return subconjunto;
            }

        }
        return null;

    }


    //metodo que retorna true si se encuentra alguna combinacion de carta/s de la mesa que suman 15 con la del jugador
    @Override
    public boolean suman15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException {

        List<List<Carta>> combinacionesPosibles = generarCombinaciones(cartasMesa);
        for(List<Carta> combinacion : combinacionesPosibles){
            int suma = cartaJugador.getValor();
            for(Carta carta : combinacion){
                suma+=carta.getValor();
            }
            if(suma == 15){
                if(combinacion.size() == mesajuego.getCartasMesa().size()){
                    notificarObservadores(Evento.SUMAN_15_CON_TODAS);
                    return true;
                }
                else{
                    notificarObservadores(Evento.SUMAN_15);
                    return true;
                }

            }

        }
        notificarObservadores(Evento.NO_SUMAN_15);
        return false;
    }


    //metodo donde el jugador selecciona una carta de su mano y suma 15 con alguna/s de la mesa
    @Override
    public void seleccionarCartaJugar(Carta cartaJugador) throws RemoteException {
        if(suman15(cartaJugador,mesajuego.getCartasMesa())){
            List<Carta> cartaEnJuegoMesa = obtenerCombinacion15(cartaJugador,mesajuego.getCartasMesa());

            if(cartaEnJuegoMesa.size() == mesajuego.getCartasMesa().size()){
                sumarpunto(1);
            }

            for(Carta carta : cartaEnJuegoMesa){
                mesajuego.sacarCarta(carta);
                jugadorActual.agregarCartaMasoRonda(carta);
            }
            jugadorActual.sacarCartaMano(cartaJugador);
            jugadorActual.agregarCartaMasoRonda(cartaJugador);


        }

    }


    //retorna las cartas de la mesa
    @Override
    public List<Carta> cartasMesa(){
        return getMesajuego().getCartasMesa();
    }

    //retorna las carta en mano del jugador
    @Override
    public List<Carta> cartaEnManoJugador(){
        return getJugadorActual().getCartasEnMano();
    }


   //metodo para agregar un jugador dado su nombre
   @Override
   public void agregarJugador(String nombre) throws RemoteException {
        if(obtenercantJugadores()>=4){
            notificarObservadores(Evento.CAPACIDAD_ALCAZADA_JUGADORES);
        }
        else{
            Jugador jugador = new Jugador(nombre);
            jugadores.add(jugador);
            jugadorActual = jugadores.get(0);
            notificarObservadores(Evento.JUGADOR_AGREGADO);
        }

    }


    //metodo en el cual se sortea el turno en el inicio de la partida
    @Override
    public void sortearTurno(){
        if (!jugadores.isEmpty()) {
            Random random = new Random();
            int indiceAleatorio = random.nextInt(jugadores.size());
            jugadorActual = jugadores.get(indiceAleatorio);
        }
    }


    //metodo para obtener el ganador de la partida
    @Override
    public Jugador obtenerGanador() {
        Jugador ganador = null;

        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() == puntuacionParaGanar) {
                ganador = jugador;
            }
        }
        return ganador;
    }

    @Override
    public void terminarRonda() throws RemoteException {
        List<Carta> cartasEnMesa = new ArrayList<>(cartasMesa());
        if(!mesajuego.mesaVacia()){
            for(Carta carta : cartasEnMesa){
                jugadorActual.agregarCartaMasoRonda(carta);
                mesajuego.sacarCarta(carta);
            }
            for(Jugador jugador: jugadores){
                for(Carta carta : cartaEnManoJugador()){
                    jugador.agregarCartaMasoRonda(carta);
                }
                jugador.getCartasEnMano().clear();
            }
        }
        sumarPuntoalFinal();
    }


    @Override
    public void terminarPartida() throws RemoteException {
        jugadores.clear();
        notificarObservadores(Evento.PARTIDA_FINALIZADA);
    }


   //metodo para obtener la cantidad de jugadores
   @Override
   public int  obtenercantJugadores(){
        return jugadores.size();

    }


    //metodo que retorna true si hay escoba de mano (todas las cartas de la mesa suman exactamente 15)
    @Override
    public boolean sepuedeEscobadeMano() throws RemoteException {
        if(mesajuego.escobaDeMano()){
            notificarObservadores(Evento.HAY_ESCOBA_DE_MANO);
            return true;
        }
        else {
            return false;
        }

    }

    //metodo donde se realiza el manejo de las cartas si hay escoba de mano
    @Override
    public void hacerEscobaDeMano() throws RemoteException {
        List<Carta> cartasEnMesa = new ArrayList<>(cartasMesa());
        for(Carta carta : cartasEnMesa){
            mesajuego.sacarCarta(carta);
            jugadorActual.agregarCartaMasoRonda(carta);
        }
        sumarpunto(1);

    }

    //metodo en el cual se suma un punto al jugador actual
    @Override
    public void sumarpunto(int punto) throws RemoteException {
        jugadorActual.sumarpunto(1);
        notificarObservadores(Evento.JUGADOR_SUMA_PUNTO);
    }



    //metodo para sumar puntos al final de la Ronda
    @Override
    public void sumarPuntoalFinal() throws RemoteException {

        notificarObservadores(Evento.FIN_DE_RONDA);
        List<Jugador> maxCartas = new ArrayList<>();
        List<Jugador> maxOros = new ArrayList<>();
        List<Jugador> maxSietes = new ArrayList<>();

        int maxCantCartas = 0;
        int maxCantOros = 0;
        int maxCantSietes = 0;

        for (Jugador jugador : jugadores) {
            MasoJugador maso = jugador.getMasoRonda();


            if (maso.todoslosOros()) {
                jugador.sumarpunto(1);
            }
            if (maso.los4sieste()) {
                jugador.sumarpunto(1);
            }
            if (maso.tiene7oro()) {
                jugador.sumarpunto(1);
            }


            int cantOros = maso.cantoros();
            if (cantOros > maxCantOros) {
                maxCantOros = cantOros;
                maxOros.clear();
                maxOros.add(jugador);
            } else if (cantOros == maxCantOros) {
                maxOros.add(jugador);
            }


            int cantCartas = maso.cantCartas();
            if (cantCartas > maxCantCartas) {
                maxCantCartas = cantCartas;
                maxCartas.clear();
                maxCartas.add(jugador);
            } else if (cantCartas == maxCantCartas) {
                maxCartas.add(jugador);
            }


            int cantSietes = maso.cantSiestes();
            if (cantSietes > maxCantSietes) {
                maxCantSietes = cantSietes;
                maxSietes.clear();
                maxSietes.add(jugador);
            } else if (cantSietes == maxCantSietes) {
                maxSietes.add(jugador);
            }
        }


        for (Jugador jugador : maxOros) {
            jugador.sumarpunto(1);
        }

        for (Jugador jugador : maxCartas) {
            jugador.sumarpunto(1);
        }

        for (Jugador jugador : maxSietes) {
            jugador.sumarpunto(1);
        }
    }


    /*
    public void agregarObservador(Observer observador) {
        listaObservadores.add(observador);
    }

    /*public void notificar(Evento o) {
        for(Observer observer: listaObservadores){
            observer.update(o);
        }

    }*/


    /*
    public void eliminarObservador(Observer observador) {
        listaObservadores.remove(observador);

    }*/
}

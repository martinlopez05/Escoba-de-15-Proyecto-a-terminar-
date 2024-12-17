package ar.edu.unlu.escobade15.Modelo;

import ar.edu.unlu.escobade15.util.PatronObserver.Observable;
import ar.edu.unlu.escobade15.util.PatronObserver.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego implements Observable {

    public final int puntuacionParaGanar = 4;

    private List<Observer> listaObservadores;
    private Baraja baraja;
    private List<Jugador> jugadores;
    public Jugador jugadorActual;
    public Mesa mesajuego;


    public Juego() {
        this.baraja = new Baraja();
        jugadores = new ArrayList<>();
        this.mesajuego = new Mesa();
        this.listaObservadores = new ArrayList<>();
    }

    //retorna una lista de los jugador en la partida
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    //retorna el Jugador Actual -> el que esta en su turno de juego
    public Jugador getJugadorActual() {
        return jugadorActual;
    }


    public Mesa getMesajuego() {
        return mesajuego;
    }

    public Baraja getBaraja() {
        return baraja;
    }



    // metodo para repartir cartas a cada jugador
    public void repartirMano() {

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

        notificar(Evento.REPARTIR_CARTAS);
    }

    //metodo para repartir cartas en mesa
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
    public boolean jugadoresSinCartas(){
        return jugadores.stream().allMatch(Jugador::faltanCartasenMano);
    }


    //metodo para iniciar una partida
    public void iniciarPartida(){
        if(sePuedeIniciarpartida()) {
            baraja.mezclarCartas();
            notificar(Evento.PARTIDA_INICIADA);
            repartirMano();
            repartirMesa();
            sortearTurno();
        }
    }

    //metodo que retorna true si la baraja se queda sin cartas
    public boolean barajaEsVacia(){
        return baraja.esVacia();
    }


    //metodo para iniciar Nueva Ronda
    public void iniciarNuevaRonda(){
        for(Jugador jugador: jugadores){
            jugador.getMasoRonda().vaciarMaso();
        }
        mesajuego.vaciar();
        Baraja baraja = new Baraja();
        this.baraja = baraja;
        repartirMesa();
        repartirMano();
        this.notificar(Evento.RONDA_NUEVA_INICIADA);
    }

    public boolean rondaTerminada(){
        if(barajaEsVacia()){
            return true;
        }
        return false;
    }

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
    public boolean sePuedeIniciarpartida(){
        if(jugadores.size() >= 2){
            return true;
        }
        else{
            notificar(Evento.FALTA_JUGADORES);
            return false;

        }
    }


    //metodo que cambia al jugadorActual en la partida
    public void actualizarTurno() {
        int indiceActual = jugadores.indexOf(jugadorActual);
        if (indiceActual != -1) {
            int siguienteIndice = (indiceActual + 1) % jugadores.size();
            jugadorActual = jugadores.get(siguienteIndice);
            notificar(Evento.CAMBIO_DE_TURNO);
        }

    }


    //metodo donde un jugador baja una carta a la mesa
    public void jugadorBajaCarta(Carta carta) {
        if (!jugadorActual.getCartasEnMano().isEmpty()) {
            mesajuego.agregarCarta(carta);
            jugadorActual.sacarCartaMano(carta);
        } else {
            notificar(Evento.JUGADOR_SIN_CARTAS);
        }
    }

    //metodo para obtener la combinacion de carta/s de la mesa que suman 15 con la carta del jugador
    public List<Carta> obtenerCombinacion15(Carta cartaJugador, List<Carta> cartasMesa){
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
    public boolean suman15(Carta cartaJugador, List<Carta> cartasMesa){

        List<List<Carta>> combinacionesPosibles = generarCombinaciones(cartasMesa);
        for(List<Carta> combinacion : combinacionesPosibles){
            int suma = cartaJugador.getValor();
            for(Carta carta : combinacion){
                suma+=carta.getValor();
            }
            if(suma == 15){
                if(combinacion.size() == mesajuego.getCartasMesa().size()){
                    this.notificar(Evento.SUMAN_15_CON_TODAS);
                    return true;
                }
                else{
                    this.notificar(Evento.SUMAN_15);
                    return true;
                }

            }

        }
        this.notificar(Evento.NO_SUMAN_15);
        return false;
    }


    //metodo donde el jugador selecciona una carta de su mano y suma 15 con alguna/s de la mesa
    public void seleccionarCartaJugar(Carta cartaJugador){
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


    //metodo para generar las combinaciones de las cartas de la mesa y se retorna una lista de esas combinaciones
    private List<List<Carta>> generarCombinaciones(List<Carta> cartas) {
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
    public List<Carta> cartasMesa(){
        return getMesajuego().getCartasMesa();
    }

    //retorna las carta en mano del jugador
    public List<Carta> cartaEnManoJugador(){
        return getJugadorActual().getCartasEnMano();
    }


   //metodo para agregar un jugador dado su nombre
    public void agregarJugador(String nombre) {
        if(obtenercantJugadores()>=4){
            this.notificar(Evento.CAPACIDAD_ALCAZADA_JUGADORES);
        }
        else{
            Jugador jugador = new Jugador(nombre);
            jugadores.add(jugador);
            jugadorActual = jugadores.get(0);
            this.notificar(Evento.JUGADOR_AGREGADO);
        }

    }


    //metodo en el cual se sortea el turno en el inicio de la partida
    public void sortearTurno(){
        if (!jugadores.isEmpty()) {
            Random random = new Random();
            int indiceAleatorio = random.nextInt(jugadores.size());
            jugadorActual = jugadores.get(indiceAleatorio);
        }
    }


    //metodo para obtener el ganador de la partida
    public Jugador obtenerGanador() {
        Jugador ganador = null;

        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() == puntuacionParaGanar) {
                ganador = jugador;
            }
        }
        return ganador;
    }

    public void terminarRonda(){
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


    public void terminarPartida(){
        jugadores.clear();
        this.notificar(Evento.PARTIDA_FINALIZADA);
    }


   //metodo para obtener la cantidad de jugadores
    public int  obtenercantJugadores(){
        return jugadores.size();

    }


    //metodo que retorna true si hay escoba de mano (todas las cartas de la mesa suman exactamente 15)
    public boolean sepuedeEscobadeMano(){
        if(mesajuego.escobaDeMano()){
            notificar(Evento.HAY_ESCOBA_DE_MANO);
            return true;
        }
        else {
            return false;
        }

    }

    //metodo donde se realiza el manejo de las cartas si hay escoba de mano
    public void hacerEscobaDeMano(){
        List<Carta> cartasEnMesa = new ArrayList<>(cartasMesa());
        for(Carta carta : cartasEnMesa){
            mesajuego.sacarCarta(carta);
            jugadorActual.agregarCartaMasoRonda(carta);
        }
        sumarpunto(1);

    }

    //metodo en el cual se suma un punto al jugador actual
    public void sumarpunto(int punto){
        jugadorActual.sumarpunto(1);
        this.notificar(Evento.JUGADOR_SUMA_PUNTO);
    }



    //metodo para sumar puntos al final de la Ronda
    public void sumarPuntoalFinal() {

        notificar(Evento.FIN_DE_RONDA);
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


    @Override
    public void agregarObservador(Observer observador) {
        listaObservadores.add(observador);
    }

    @Override
    public void notificar(Evento o) {
        for(Observer observer: listaObservadores){
            observer.update(o);
        }

    }

    @Override
    public void eliminarObservador(Observer observador) {
        listaObservadores.remove(observador);

    }
}

package ar.edu.unlu.escobade15.Modelo;

import ar.edu.unlu.escobade15.util.PatronObserver.Observable;
import ar.edu.unlu.escobade15.util.PatronObserver.Observer;

import java.util.ArrayList;
import java.util.List;

public class Juego implements Observable {

    private List<Observer> listaObservadores;
    private Baraja baraja;
    private List<Jugador> jugadores;
    public Jugador jugadorActual;
    public MasoAjugar masoAjugar;
    public Mesa mesajuego;


    public Juego() {
        this.baraja = new Baraja();
        jugadores = new ArrayList<>();
        this.mesajuego = new Mesa();
        this.listaObservadores = new ArrayList<>();
    }


    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }


    // corregir retornar la lista de carta mesa
    public Mesa getMesajuego() {
        return mesajuego;
    }

    public Baraja getBaraja() {
        return baraja;
    }


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
        if (mesajuego.mesaVacia()) {
            for (int i = 0; i < 4; i++) {
                if (!barajaEsVacia()) {
                    mesajuego.agregarCarta(baraja.sacarCarta());
                } else {
                    break;
                }
            }
        }
        notificar(Evento.REPARTIR_CARTAS);
    }


    public boolean jugadoresSinCartas(){
        return jugadores.stream().allMatch(Jugador::faltanCartasenMano);
    }


    public void iniciarPartida(){
        if(sePuedeIniciarpartida()) {
            baraja.mezclarCartas();
            repartirMano();
            notificar(Evento.PARTIDA_INICIADA);
        }
    }


    public boolean barajaEsVacia(){
        return baraja.esVacia();
    }


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

    }



    public boolean sePuedeIniciarpartida(){
        if(jugadores.size() >= 2){
            return true;
        }
        else{
            notificar(Evento.FALTA_JUGADORES);
            return false;

        }
    }


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
            jugadorActual.sacarCarta(carta);
        } else {
            notificar(Evento.JUGADOR_SIN_CARTAS);
        }
    }

    //metodo para verificar si suman 15 la carta del jugador con alguna/s de la mesa
    public boolean suman15(Carta cartaJugador,List<Carta> cartasMesa){
        int sumaMesa=0;
        for(Carta carta : cartasMesa){
            sumaMesa+= carta.getValor();
        }
        return (cartaJugador.getValor() + sumaMesa)== 15;

    }

    public void jugarCarta(Carta cartaJugador,List<Carta> cartasMesa) {
        sum
    }






    public List<Carta> cartasMesa(){
        return getMesajuego().getCartasMesa();
    }

    public List<Carta> cartaEnManoJugador(){
        return getJugadorActual().getCartasEnMano();
    }



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



    public Jugador obtenerGanador() {
        Jugador ganador = jugadores.get(0);

        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() > ganador.getPuntuacion()) {
                ganador = jugador;
            }
        }

        return ganador;
    }



    public int  obtenercantJugadores(){
        return jugadores.size();

    }



    public boolean sepuedeEscobadeMano(){
        if(mesajuego.escobaDeMano()){
            notificar(Evento.HAY_ESCOBA_DE_MANO);
            return true;
        }
        else {
            return false;
        }

    }


    public void hacerEscobaDeMano(){
        for(Carta carta : cartasMesa()){
            mesajuego.sacarCarta(carta);
            jugadorActual.agregarCartaMasoRonda(carta);
            jugadorActual.sumarpunto(1);
        }

    }




    //meotod para sumar puntos al final de la partida
    public void sumarPuntoalFinal(){
        notificar(Evento.FIN_PARTIDA);
        Jugador jmaxCartas = jugadores.get(0);
        Jugador jmaxoro = jugadores.get(0);
        Jugador jmaxsiete = jugadores.get(0);
        for(Jugador jugador : jugadores){
            if(jugador.getMasoRonda().todoslosOros()){
                jugador.sumarpunto(2);
            }
            if(jugador.getMasoRonda().cantoros()>jmaxoro.getMasoRonda().cantoros()){
                jmaxoro=jugador;
            }

            if(jugador.getMasoRonda().cantCartas()>jmaxCartas.getMasoRonda().cantCartas()){
                jmaxCartas=jugador;

            }
            if(jugador.getMasoRonda().los4sieste()){
                jugador.sumarpunto(1);
            }
            if(jugador.getMasoRonda().tiene7oro()){
                jugador.sumarpunto(1);
            }
            if(jugador.getMasoRonda().cantSiestes()> jmaxsiete.getMasoRonda().cantSiestes()){
                jmaxsiete=jugador;
            }


        }
        jmaxoro.sumarpunto(1);
        jmaxsiete.sumarpunto(1);
        jmaxCartas.sumarpunto(1);

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

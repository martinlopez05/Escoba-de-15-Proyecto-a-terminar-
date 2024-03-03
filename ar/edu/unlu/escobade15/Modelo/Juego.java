package Modelo;

import util.PatronObserver.Observable;
import util.PatronObserver.Observer;

import java.util.ArrayList;
import java.util.List;

public class Juego implements Observable {

    private List<Observer> listaObservadores;
    private Baraja baraja;
    private List<Jugador> jugadores;
    public Jugador jugadorActual;

    public Mesa mesajuego;



    public Juego(){
        this.baraja=new Baraja();
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


    public List<Carta> cartasdeJugadorActual(){
        return jugadorActual.getCartasEnMano();
    }


    public void repartirMano(){
        if(!baraja.esVacia()) {
            for (Jugador jugador : jugadores) {
                for (int i = 0; i < 3; i++) {
                    jugador.recibirCarta(baraja.sacarCarta());

                }
            }
            if (mesajuego.MesaVacia()) {
                for (int i = 0; i < 4; i++) {
                    mesajuego.dejarCarta(baraja.sacarCarta());
                }
            }
            notificar(Evento.REPARTIR_CARTAS);
        }
        else{
            System.out.println("\nNo hay mas cartas en la baraja...");
        }


    }


    public void actualizaTurno() {
        int indiceActual = jugadores.indexOf(jugadorActual);
        if (indiceActual != -1) {
            int siguienteIndice = (indiceActual + 1) % jugadores.size();
            jugadorActual = jugadores.get(siguienteIndice);
            notificar(Evento.CAMBIO_DE_TURNO);
        }

    }





    public boolean jugadoresTienenCartas(){
        boolean encontrado=false;
        for(Jugador jugador : jugadores){
            if(!jugador.getCartasEnMano().isEmpty()){
                encontrado = true;
            }
        }

        return encontrado;

    }



    public void jugadorBajaCarta(Carta carta) {
        if (!jugadorActual.getCartasEnMano().isEmpty()) {
            mesajuego.dejarCarta(carta);
            jugadorActual.sacarCarta(carta);
        } else {
            System.out.println("El jugador no tiene más cartas en la mano.");
        }
    }




    public boolean hayCartasenlaMesa(){
        if(!mesajuego.MesaVacia()){
            return true;
        }
        else{
            return false;
        }
    }


    public boolean sepuedeEscobadeMano(){
        int suma = 0;
        for (Carta carta : mesajuego.getCartasMesa()){
            suma += carta.getValor();
        }
        return suma == 15;
    }


    public boolean suma15ConMesa(Carta cartaJugador) {
        int suma = cartaJugador.getValor();
        for (Carta cartaMesa : mesajuego.getCartasMesa()) {
            suma += cartaMesa.getValor();
        }
        notificar(Evento.HAY_ESCOBA);
        return suma == 15;
    }

    public void jugadorHaceEscoba(Carta cartaJugador) {
        if (suma15ConMesa(cartaJugador)) {
            hacerEscoba(cartaJugador, mesajuego.getCartasMesa());
        }

    }




    public void hacerEscobadeMano(){
        for(Carta cartaMesa: mesajuego.getCartasMesa()){
            mesajuego.agarrarCarta(cartaMesa);
            jugadorActual.agregarCartaalMasoRonda(cartaMesa);
        }
        jugadorActual.sumarpunto(1);

    }



    public void hacerEscoba(Carta cartaJugador, List<Carta> cartasMesa){
        jugadorActual.agregarCartaalMasoRonda(cartaJugador);
        jugadorActual.sacarCarta(cartaJugador);
        for(Carta carta : cartasMesa) {
            jugadorActual.agregarCartaalMasoRonda(carta);
            mesajuego.agarrarCarta(carta);

        }
        jugadorActual.sumarpunto(1);

    }




    public void agregarJugador(String nombreJugador) {
        Jugador jugador = new Jugador(nombreJugador);


        if (jugadores.size() < 4) {
            jugadores.add(jugador);
            jugadorActual = jugadores.get(0);
            this.notificar(Evento.JUGADOR_AGREGADO);
        }
        if (jugadores.size() == 4) {
                System.out.println("¡Ya se alcanzó la capacidad máxima de jugadores!");
        }

    }





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

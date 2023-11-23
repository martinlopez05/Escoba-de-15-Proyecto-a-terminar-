package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Juego implements Observable {

    public Juego(){
        this.baraja=new Baraja();
        jugadores = new ArrayList<>();
        this.mesajuego = new Mesa();

    }

    //lista de observadores
    List<Observer> listaObservadores;
    public Baraja baraja;
    private List<Jugador> jugadores;
    private boolean turno;
    public Jugador jugadorActual;

    public Mesa mesajuego;

    public void RepartirCartas(){
        for (Jugador jugador : jugadores) {
            baraja.darCarta(jugador);
        }
        if(mesajuego.hayCartasenMesa() == false){
            for(int i=0; i<4 ; i++){
                mesajuego.dejarCarta(baraja.sacarCarta());
            }
        }

    }


    public void jugarCarta() {
        if (!mesajuego.hayCartasenMesa()){
            mesajuego.dejarCarta(jugadorActual.elegirCartaaBajar());
        } else {

            for (Carta cartaJugador : jugadorActual.getCartasEnMano()) {
                if (sepuedeEscoba(cartaJugador, mesajuego.getCartasMesa())) {
                    System.out.println("¡puede hacer escoba!:");
                    hacerEscoba(cartaJugador,mesajuego.getCartasMesa());
                }
                else{


                }

            }
        }
    }

    private void hacerJuego(Carta cartaJugador, Carta cartaMesa) {
        jugadorActual.MasoRonda.agregarCarta(cartaJugador);
        jugadorActual.MasoRonda.agregarCarta(cartaMesa);
        jugadorActual.sacarCarta(cartaJugador);
        mesajuego.agarrarCarta(cartaMesa);
    }



    private boolean sepuedeEscoba(Carta cartaJugador, List<Carta> cartasMesa) {
        int suma = cartaJugador.getNumero();
        for (Carta cartaMesa : cartasMesa) {
            suma += cartaMesa.getNumero();
        }
        return suma == 15;
    }

    private void hacerEscoba(Carta cartaJugador, List<Carta> cartasMesa){
        jugadorActual.MasoRonda.agregarCarta(cartaJugador);
        for(Carta carta : cartasMesa) {
            jugadorActual.MasoRonda.agregarCarta(carta);
            jugadorActual.MasoRonda.agregarCarta(carta);
        }

    }













    public void agregarJugador(String nombreJugador) {
        Jugador jugador = new Jugador(nombreJugador);
        jugadorActual = jugador;
        if (jugadores.size() < 4) {
            jugadores.add(jugador);
            this.notificar(Evento.JUGADOR_AGREGADO);
            System.out.println("Jugador " + jugador.getNombreJugador() + " agregado exitosamente.");
        }
        if (jugadores.size() == 4) {
                System.out.println("¡Ya se alcanzó la capacidad máxima de jugadores!");
        }

    }


    @Override
    public void agregarObservador(Observer observador) {
        listaObservadores.add(observador);
    }

    @Override
    public void notificar(Evento o) {

    }

    @Override
    public void eliminarObservador(Observer observador) {
        listaObservadores.remove(observador);

    }
}

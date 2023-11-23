package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Juego implements Observable {

    public Juego(){
        this.baraja=new Baraja();
        jugadores = new ArrayList<>();
        this.mesajuego = new Mesa();

    }

    //lista de observadores
    private List<Observer> listaObservadores;
    public Baraja baraja;
    private List<Jugador> jugadores;
    private boolean turno;
    public Jugador jugadorActual;

    public Mesa mesajuego;

    public void repartirCartas(){
        for (Jugador jugador : jugadores) {
            for (int i=0;i<3;i++) {
                jugador.recibirCarta(baraja.sacarCarta());

            }
        }
        if(!mesajuego.hayCartasenMesa()){
            for(int i=0; i<4 ; i++){
                mesajuego.dejarCarta(baraja.sacarCarta());
            }
        }

    }


    public void TurnoJugador() {
        if (!mesajuego.hayCartasenMesa()) {
            Carta cartaJugador = jugadorActual.elegirCartaaBajar();
            mesajuego.dejarCarta(cartaJugador);
            jugadorActual.sacarCarta(cartaJugador);
        } else {

            if (mesajuego.sepuedeEscobadeMano()) {
                hacerEscobadeMano();
            } else {

                for(Carta cartaJugador : jugadorActual.getCartasEnMano()){
                    if(sepuedeEscoba(cartaJugador,mesajuego.getCartasMesa())){
                        System.out.println("Puede hacer escoba ");
                        hacerEscoba(cartaJugador,mesajuego.getCartasMesa());
                    }
                    else{



                    }

                }


            }
        }
    }

    private void hacerJuego(Carta cartaJugador, Carta cartaMesa) {
        jugadorActual.agregarCartaalMasoRonda(cartaJugador);
        jugadorActual.agregarCartaalMasoRonda(cartaMesa);
        jugadorActual.sacarCarta(cartaJugador);
        mesajuego.agarrarCarta(cartaMesa);
    }




    public boolean puedoSumar15ConCarta(Carta cartaJugador, List<Carta> cartasMesa) {
        for (Carta cartaMesa : cartasMesa) {
            int suma = cartaJugador.getValor() + cartaMesa.getValor();
            // Si la suma es 15 o puedo sumar 15 con las otras cartas de la mesa, devuelve true
            if (suma == 15 || puedoSumar15ConOtrasCartas(cartaJugador, cartasMesa, suma, 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean puedoSumar15ConOtrasCartas(Carta cartaJugador, List<Carta> cartasMesa, int sumaParcial, int indice) {
        // Caso base: si la suma parcial es igual a 15, devuelve true
        if (sumaParcial == 15) {
            return true;
        }

        // Iterar sobre las cartas restantes en la mesa
        for (int i = indice; i < cartasMesa.size(); i++) {
            Carta otraCarta = cartasMesa.get(i);
            // Intentar sumar la otra carta a la suma parcial
            if (puedoSumar15ConOtrasCartas(cartaJugador, cartasMesa, sumaParcial + otraCarta.getValor(), i + 1)) {
                return true;
            }
        }

        // No se encontró ninguna combinación que sume 15
        return false;
    }




    private boolean sepuedeEscoba(Carta cartaJugador, List<Carta> cartasMesa) {
        int suma = cartaJugador.getValor();
        for (Carta cartaMesa : cartasMesa) {
            suma += cartaMesa.getValor();
        }
        return suma == 15;
    }




    private void hacerEscobadeMano(){
        for(Carta cartaMesa: mesajuego.getCartasMesa()){
            mesajuego.agarrarCarta(cartaMesa);
            jugadorActual.agregarCartaalMasoRonda(cartaMesa);
        }

    }




    private void hacerEscoba(Carta cartaJugador, List<Carta> cartasMesa){
        jugadorActual.agregarCartaalMasoRonda(cartaJugador);
        jugadorActual.sacarCarta(cartaJugador);
        for(Carta carta : cartasMesa) {
            jugadorActual.agregarCartaalMasoRonda(carta);
            mesajuego.agarrarCarta(carta);

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

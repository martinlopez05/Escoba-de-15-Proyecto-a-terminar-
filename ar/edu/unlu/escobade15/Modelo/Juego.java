package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Juego {

    public Juego(){
        jugadores = new ArrayList<>();
    }

    
    Baraja baraja;
    List<Jugador> jugadores;
    private boolean turno;
    private Jugador jugadorActual;

    public void RepartirCartas(){
        this.baraja=new Baraja();
        for (Jugador jugador : jugadores) {
            baraja.darCarta(jugador);
        }
    }

    public void AgregarJugador(Jugador jugador) {
        if (jugadores.size() < 4) {
            jugadores.add(jugador);
            System.out.println("Jugador " + jugador.getNombreJugador() + " agregado exitosamente.");
        }
        if (jugadores.size() == 4) {
                System.out.println("¡Ya se alcanzó la capacidad máxima de jugadores!");
        }
        
        
    }

    

     









}

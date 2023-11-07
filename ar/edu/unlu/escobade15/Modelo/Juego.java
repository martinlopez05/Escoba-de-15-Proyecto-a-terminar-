package Modelo;

import java.util.List;

public class Juego {
    
    Baraja baraja;
    List<Jugador> jugadores;
    private boolean turno;

    public void RepartirCartas(){
        for (Jugador jugador : jugadores) {
            baraja.Repartir(jugador);
        }
    }


}

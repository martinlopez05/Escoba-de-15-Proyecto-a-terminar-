package ar.edu.unlu.escobade15;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombreJugador;
    private List<Carta> CartadelJuagdor;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.CartadelJuagdor = new ArrayList<>();

    }

    public void recibirCarta(Carta carta) {
        CartadelJuagdor.add(carta);

    }

    public void dejarCarta(Carta carta) {
        if (CartadelJuagdor.contains(carta)) {
            CartadelJuagdor.remove(carta);

        } else {
            System.out.println("El jugador no tiene  mas cartas para tirar");
        }

    }

}

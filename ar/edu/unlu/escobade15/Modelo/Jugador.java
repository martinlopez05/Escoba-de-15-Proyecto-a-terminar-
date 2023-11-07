package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombreJugador;
    private List<Carta> CartasMano;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        CartasMano = new ArrayList<Carta>();
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public List<Carta> getCartasEnMano() {
        return CartasMano;
    }

    public void recibirCarta(Carta carta) {
        CartasMano.add(carta);
    }

    public void jugarCarta(Carta carta) {
        
        CartasMano.remove(carta);
        
    }
}






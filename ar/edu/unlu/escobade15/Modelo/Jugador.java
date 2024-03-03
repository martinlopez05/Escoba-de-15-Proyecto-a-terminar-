package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private String nombreJugador;
    private List<Carta> CartasMano;

    private int puntuacion = 0;

    private MasoJugador MasoRonda;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        CartasMano = new ArrayList<Carta>();
        MasoRonda = new MasoJugador();
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public List<Carta> getCartasEnMano() {
        return CartasMano;
    }

    public MasoJugador getMasoRonda() {
        return MasoRonda;
    }

    public int getPuntuacion() {
        return puntuacion;
    }


    public void recibirCarta(Carta carta) {
        CartasMano.add(carta);
    }

    public void sacarCarta(Carta carta) {
        
        CartasMano.remove(carta);
        
    }

    public void agregarCartaalMasoRonda(Carta carta){
        MasoRonda.agregarCarta(carta);

    }



    public void sumarpunto(int numero){
        puntuacion+=numero;
    }


}






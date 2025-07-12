package ar.edu.unlu.escobade15.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jugador implements Serializable {
    private String nombreJugador;
    private List<Carta> CartasEnMano;
    private int escobas;
    private int puntuacion;

    private MasoJugador MasoRonda;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        CartasEnMano = new ArrayList<Carta>();
        MasoRonda = new MasoJugador();
        this.puntuacion = 0;
    }

    public int getEscobas() {
        return escobas;
    }

    public void agregarEscoba(){
        escobas ++;
    }

    public void vaciarEscobas(){
        this.escobas = 0;
    }

    public MasoJugador getMasoRonda() {
        return MasoRonda;
    }

    public int getPuntuacion() {
        return puntuacion;
    }


    public void recibirCarta(Carta carta) {
        CartasEnMano.add(carta);
    }

    public void sacarCartaMano(Carta carta) {
        CartasEnMano.remove(carta);
    }

    public void agregarCartaMasoRonda(Carta carta){
        MasoRonda.agregarCarta(carta);

    }


    public boolean faltanCartasenMano(){
        if(getCartasEnMano().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }


    public void sumarpunto(int numero){
        puntuacion+=numero;
    }




    public String getNombreJugador() {
        return nombreJugador;
    }


    public List<Carta> getCartasEnMano() {
        return CartasEnMano;
    }
}






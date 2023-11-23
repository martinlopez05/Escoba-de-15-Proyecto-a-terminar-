package Modelo;

import java.util.*;

public class MasoJugador {

    public MasoJugador(){
        Cartas = new ArrayList<>();

    }
    private List<Carta> Cartas;

    public void agregarCarta(Carta carta){
        Cartas.add(carta);


    };

    public void vaciarMaso(){
        for(Carta carta : Cartas){
            Cartas.remove(carta);
        }
    }


}

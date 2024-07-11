package ar.edu.unlu.escobade15.Modelo;

import java.util.List;

public class MasoAjugar {
    private List<Carta> cartas;


    public MasoAjugar(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public void agregarCarta(Carta carta){
        cartas.add(carta);
    }

    public void sacarCarta(Carta carta){
        cartas.remove(carta);
    }


    public boolean suman15() {
        int suma = 0;
        for (Carta carta : cartas) {
            suma += carta.getValor();
        }

        return suma == 15;
    }



    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

}

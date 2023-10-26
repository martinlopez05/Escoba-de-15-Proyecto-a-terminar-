package ar.edu.unlu.escobade15;

import java.util.Collections;
import java.util.Stack;

public class Baraja {
    private Stack<Carta> Cartas;

    public Baraja() {
        this.Cartas = new Stack<Carta>();

    }

    public void agregarCartas(Carta carta) {
        Cartas.push(carta);

    }

    public Carta sacarCarta() {
        return Cartas.pop();
    }

    public void mezclarCartas() {
        Collections.shuffle(Cartas);

    }

    public void mostrarBaraja() {
        int i = 1;
        for (Carta carta : Cartas) {
            System.out.println("Carta: " + i + carta.toString());
            i++;

        }

    }

}

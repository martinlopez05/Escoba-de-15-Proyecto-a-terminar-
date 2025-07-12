package ar.edu.unlu.escobade15.Modelo;

import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

public class Baraja implements Serializable {
    private Stack<Carta> cartas;

    public Baraja() {
        this.cartas = new Stack<Carta>();
        crearCartas();
        mezclarCartas();

    }

    private void crearCartas() {
        for (int numero = 1; numero <= 12; numero++) {
            for (Palo palo : Palo.values()) {
                if (numero != 8 && numero != 9) {
                    if (numero == 10) {
                        Carta carta = new Carta(numero, palo, 8);
                        cartas.push(carta);
                    } else if (numero == 11) {
                        Carta carta = new Carta(numero, palo, 9);
                        cartas.push(carta);
                    } else if (numero == 12) {
                        Carta carta = new Carta(numero, palo, 10);
                        cartas.push(carta);
                    } else {
                        Carta carta = new Carta(numero, palo, numero);
                        cartas.push(carta);
                    }
                }
            }
        }
    }


    public int obtenerCantCartas(){
        return cartas.size();
    }

    public  Carta sacarCarta() {
        return cartas.pop();

    }


    public void mezclarCartas() {
        Collections.shuffle(cartas);

    }



    public boolean esVacia(){
        return cartas.isEmpty();
    }


    

}

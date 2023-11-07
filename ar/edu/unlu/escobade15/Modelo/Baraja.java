package Modelo;

import java.util.Collections;
import java.util.Stack;

public class Baraja {
    private Stack<Carta> Cartas;

    public Baraja() {
        this.Cartas = new Stack<Carta>();
        crearCartas();

    }

    private void crearCartas() {
        // Crear todas las cartas de la baraja
        for (int numero = 1; numero <= 12; numero++) {
            for (Palo palo : Palo.values()) {
                Carta carta = new Carta(numero, palo);
                Cartas.push(carta);
            }
        }
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

    public void Repartir(Jugador jugador){
        for (int i=0;i<3;i++) {
            jugador.recibirCarta(sacarCarta());
            
        }

    }

}

package Modelo;

import java.util.Collections;
import java.util.Stack;

public class Baraja {
    private Stack<Carta> Cartas;

    public Baraja() {
        this.Cartas = new Stack<Carta>();
        crearCartas();
        mezclarCartas();

    }

    private void crearCartas() {
        // Crear todas las cartas de la baraja
        for (int numero = 1; numero <= 12; numero++) {
            for (Palo palo : Palo.values()) {
                if (numero != 8 && numero != 9) {
                    if (numero == 10) {
                        Carta carta = new Carta(numero, palo, 8);
                        Cartas.push(carta);
                    } else if (numero == 11) {
                        Carta carta = new Carta(numero, palo, 9);
                        Cartas.push(carta);
                    } else if (numero == 12) {
                        Carta carta = new Carta(numero, palo, 10);
                        Cartas.push(carta);
                    } else {
                        Carta carta = new Carta(numero, palo, numero);
                        Cartas.push(carta);
                    }
                }
            }
        }
    }




    public  Carta sacarCarta() {
        return Cartas.pop();

    }
    
    public void darCarta(Jugador jugador) {
        for (int i=0;i<3;i++) {
            jugador.recibirCarta(Cartas.pop());
            
        }
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

    /*public void elegirCartaaBajar(){
        for(Carta carta : Cartas){
            if(numero.)
        }
    }*/

    

}

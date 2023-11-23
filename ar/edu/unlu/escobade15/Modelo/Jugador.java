package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private String nombreJugador;
    private List<Carta> CartasMano;

    private MasoJugador MasoRonda;

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

    public void sacarCarta(Carta carta) {
        
        CartasMano.remove(carta);
        
    }

    public void agregarCartaalMasoRonda(Carta carta){
        MasoRonda.agregarCarta(carta);

    }



    public void mostrarCartasdeJugador(){
        for(Carta carta : CartasMano){
            System.out.println("" + carta.toString());
        }

    }

    public Carta elegirCartaaBajar() {
        Scanner sc = new Scanner(System.in);
        int numero;
        Palo palo;

        do {
            System.out.println("Indique el número de la carta a bajar: ");
            numero = sc.nextInt();

            System.out.println("Indique el palo de la carta a bajar (DIAMANTES, CORAZONES, TREBOLES, ESPADAS): ");
            palo = Palo.valueOf(sc.next().toUpperCase());


            if (!cartaExiste(numero, palo)) {
                System.out.println("Por favor, seleccione una carta válida.");
            }

        } while (!cartaExiste(numero, palo));

        return obtenerCartaPorNumeroYpalo(numero, palo);
    }



    private boolean cartaExiste(int numero, Palo palo) {
        for (Carta carta : CartasMano) {
            if (carta.getNumero() == numero && carta.getPalo() == palo) {
                return true;
            }
        }
        return false;
    }

    private Carta obtenerCartaPorNumeroYpalo(int numero, Palo palo) {
        for (Carta carta : CartasMano) {
            if (carta.getNumero() == numero && carta.getPalo() == palo) {
                return carta;
            }
        }
        return null;
    }

    
}






package Modelo;

public class Carta {
    private int numero;
    private Palo palo;

    public Carta(int numero, Palo palo) {
        this.numero = numero;
        this.palo = palo;

    }

    public int getNumero() {
        return numero;
    }

    public Palo getPalo() {
        return palo;
    }

    public String toString() {
        return "[numero: " + getNumero() + ",palo: " + getPalo() + "]";
    }

   

}

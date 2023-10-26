package ar.edu.unlu.escobade15;

public class Carta {
    private int numero;
    private String palo;

    public Carta(int numero, String palo) {
        this.numero = numero;
        this.palo = palo;

    }

    public int getNumero() {
        return numero;
    }

    public String getPalo() {
        return palo;
    }

    public String toString() {
        return "[numero: " + getNumero() + ",palo: " + getPalo() + "]";
    }

}

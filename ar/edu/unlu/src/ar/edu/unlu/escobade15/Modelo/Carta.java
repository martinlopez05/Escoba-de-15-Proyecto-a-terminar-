package ar.edu.unlu.escobade15.Modelo;

public class Carta {
    private int numero;
    private Palo palo;

    private int valor;

    public Carta(int numero, Palo palo , int valor) {
        this.numero = numero;
        this.palo = palo;
        this.valor = valor;

    }

    public int getNumero() {
        return numero;
    }

    public Palo getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

    public String toString() {

        return "[numero: " + getNumero() + ",palo: " + getPalo() + ",valor: " + getValor() + "]";
    }

   

}

package ar.edu.unlu.escobade15.Modelo;

import javax.swing.*;
import java.io.Serializable;

public class Carta implements Serializable {
    private int numero;
    private Palo palo;
    private int valor;
    private ImageIcon imagen;

    public Carta(int numero, Palo palo , int valor) {
        this.numero = numero;
        this.palo = palo;
        this.valor = valor;
        //this.imagen = new ImageIcon("imagenCarta/" +  palo.name().toLowerCase() + numero + ".jpg");

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

    /*public ImageIcon getImagen() {
        return imagen;
    }*/
}

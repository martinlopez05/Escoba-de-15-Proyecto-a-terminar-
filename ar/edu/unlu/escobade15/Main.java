package ar.edu.unlu.escobade15;

public class Main {
    public static void main(String[] args) {
        Carta carta1 = new Carta(1, "Basto");
        Carta carta2 = new Carta(2, "Oro");
        Carta carta3 = new Carta(6, "Espada");
        Carta carta4 = new Carta(10, "Copa");
        Carta carta5 = new Carta(12, "Basto");
        Carta carta6 = new Carta(11, "Oro");

        Baraja baraja = new Baraja();

        baraja.agregarCartas(carta1);
        baraja.agregarCartas(carta2);
        baraja.agregarCartas(carta3);
        baraja.agregarCartas(carta4);
        baraja.agregarCartas(carta5);
        baraja.agregarCartas(carta6);

        baraja.mezclarCartas();

        baraja.mostrarBaraja();

    }

}

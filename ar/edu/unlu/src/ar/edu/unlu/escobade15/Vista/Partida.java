package ar.edu.unlu.escobade15.Vista;

import javax.swing.*;
import java.awt.*;

public class Partida {

    private JFrame frame;
    private JPanel panelPrincipal;
    private JPanel panelCartasMesa;
    private JPanel panelBotonesJugador;
    private JButton botonCarta1, botonCarta2, botonCarta3, botonCarta4;
    private JButton botonJugador1, botonJugador2, botonJugador3;

    public Partida() {
        iniciar();
    }

    public void iniciar() {
        // Crear el JFrame
        frame = new JFrame("Partida");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelCartasMesa = new JPanel();
        panelCartasMesa.setLayout(new GridLayout(1, 4, 10, 10));
        botonCarta1 = new JButton("Carta 1");
        botonCarta2 = new JButton("Carta 2");
        botonCarta3 = new JButton("Carta 3");
        botonCarta4 = new JButton("Carta 4");

        panelCartasMesa.add(botonCarta1);
        panelCartasMesa.add(botonCarta2);
        panelCartasMesa.add(botonCarta3);
        panelCartasMesa.add(botonCarta4);

        panelBotonesJugador = new JPanel();
        panelBotonesJugador.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        botonCarta1.setIcon(new ImageIcon("/imagenCarta/espada1.jpg"));

        botonJugador1 = new JButton("Jugador Carta 1");
        botonJugador2 = new JButton("Jugador Carta 2");
        botonJugador3 = new JButton("Jugador Carta 3");

        panelBotonesJugador.add(botonJugador1);
        panelBotonesJugador.add(botonJugador2);
        panelBotonesJugador.add(botonJugador3);

        panelPrincipal.add(panelCartasMesa, BorderLayout.CENTER);
        panelPrincipal.add(panelBotonesJugador, BorderLayout.SOUTH);


        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Partida partida = new Partida();
    }
}
package Vista;

import Controlador.ControladorJuego;
import Modelo.Carta;
import Modelo.Jugador;
import Modelo.Palo;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Ivista{

    private Scanner sc;
    ControladorJuego controlador;

    public VistaConsola(){
        this.sc = new Scanner(System.in);
    }




    public void mostrarMenuPrincipal() {
        System.out.println("############################");
        System.out.println("####### Escoba de 15 #######");
        System.out.println("############################");
        System.out.println();
        System.out.println("Selecciona una opción:");
        System.out.println("1 - Agregar jugador");
        System.out.println("2 - Comenzar el juego");
        System.out.println("3 - Salir ");

    }

    @Override
    public void setControlador(ControladorJuego controlador) {
        this.controlador=controlador;
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            System.out.println("Ingresa una opcion:");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                sc.next();
            }

            opcion = sc.nextInt();


            if (opcion < 1 || opcion > 3) {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            } else {
                switch (opcion) {
                    case 1: {

                        controlador.agregarJugador();
                        break;
                    }
                    case 2: {

                        controlador.iniciarjuego();
                        break;
                    }

                    case 3: {
                        System.out.println("Saliendo del juego. ¡Hasta luego!");
                        break;
                    }
                }
            }

        } while (opcion != 3);
    }


    public String solicitarNombreJugador(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su nuevo nombre de usuario:");
        String nombre = sc.nextLine();
        return nombre;

    }


    public void mostrarmensaje(String mensaje){
        System.out.println(mensaje);
    }




    public void mostrarGanador(Jugador jugador){
        System.out.println("El ganador de la escoba de 15 es : " + jugador.getNombreJugador() +  "con  " + jugador.getPuntuacion() + "puntos");

    }




    public void mostrarPuntosjugador(List<Jugador> jugadores){
        System.out.println("***Puntos obtenidos de cada jugador:");
        for(Jugador jugador : jugadores){
            System.out.println("Jugador  " + jugador.getNombreJugador() + " finalizo con " + jugador.getPuntuacion() + " puntos\n");
        }


    }

    public Carta solicitarCartaaBajar(List<Carta> cartasEnMano) {

        do {
            System.out.println("Cartas en tu mano:");
            for (int i = 0; i < cartasEnMano.size(); i++) {
                System.out.println((i + 1) + ": " + cartasEnMano.get(i).toString());
            }

            System.out.println("Elige una carta a jugar de tu mano (1, 2, 3, ...):");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                sc.next();
            }

            int opcion = sc.nextInt();

            if (opcion < 1 || opcion > cartasEnMano.size()) {
                System.out.println("Opción no válida. Por favor, elige una opción dentro del rango válido.");
            } else {
                return cartasEnMano.get(opcion - 1);
            }

        } while (true);
    }





    public void mostrarOpcionJugador() {
        System.out.println("\n####### ¿Qué deseas hacer? #######");
        System.out.println("Selecciona una opción:");
        System.out.println("1 - Dejar carta en mesa");
        System.out.println("2 - Recoger carta/s de la mesa");

    }


    public int elegirOpcionJugador() {
        int opcion;

        do {
            mostrarOpcionJugador();
            System.out.println("Ingresa una opción:");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                sc.next(); // Consumir entrada no válida
            }

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Dejar carta en mesa");
                    break;
                case 2:
                    System.out.println("Recoger carta/s de la mesa");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion < 1 || opcion > 2);

        return opcion;
    }


    public void mostrarTurno(Jugador jugador){
        System.out.println("Turno del jugador: " + jugador.getNombreJugador());

    }







    public void mostrarMesa(List<Carta> cartas) {
        if (cartas.isEmpty()) {
            System.out.println("No hay cartas en la mesa.");
        } else {
            System.out.println("\nCartas en mesa:");
            for (int i = 0; i < cartas.size(); i++) {
                System.out.println("" + cartas.get(i).toString());
            }
        }
    }


    public void mostrarCartajugador(Jugador jugador){
        int i=1;
        if(jugador.getCartasEnMano().isEmpty()){
            System.out.println("No hay cartas que tenga el jugador en mano");
        }
        else {
            System.out.println("\nCarta del jugador: " + jugador.getNombreJugador() + "\n");
            for (Carta carta : jugador.getCartasEnMano()) {

                System.out.println("" + i + carta.toString());
                i++;
            }
        }
    }





}

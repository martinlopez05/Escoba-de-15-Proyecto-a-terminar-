package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;


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
                        controlador.agregarJugador(obtenerNombrejugador());
                        presionarEnter();
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



    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }



    public String obtenerNombrejugador(){
        System.out.println("Indicar el nombre del jugador : ");
        String nombrejugador = sc.next();
        return nombrejugador;
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
        System.out.println("3 - Seleccionar carta a jugar");
        System.out.println("4 - Seleccionar carta de mesa a jugar");

    }




    //arreglar funcion con controlador
    public void opcionJugador() {
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
                    controlador.bajaCarta();
                    break;
                case 2:
                    controlador.recogeCartaMesa();
                    break;
                case 3:
                    //aca va la opcion de seleccionar carta a jugar de la mano

                case 4 :
                    // aca va la opcion de seleccionar carta/s a jugar de la mesa

                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion < 1 || opcion > 4);


    }


    public void mostrarTurno(){
        System.out.println("Turno del jugador: " + controlador.getJugadorActual().getNombreJugador());

    }


    public Carta solicitarCartaArecoger( List<Carta> cartasEnMesa){
        do {
            System.out.println("Cartas en tu mano:");
            for (int i = 0; i < cartasEnMesa.size(); i++) {
                System.out.println((i + 1) + ": " + cartasEnMesa.get(i).toString());
            }

            System.out.println("Elige una carta a jugar de la mesa (1, 2, 3, ...):");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                sc.next();
            }

            int opcion = sc.nextInt();

            if (opcion < 1 || opcion > cartasEnMesa.size()) {
                System.out.println("Opción no válida. Por favor, elige una opción dentro del rango válido.");
            } else {
                return cartasEnMesa.get(opcion - 1);
            }

        } while (true);
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



    public void presionarEnter(){
        System.out.println("Presiona Enter para continuar..");
        sc.nextLine();
    }



    






}

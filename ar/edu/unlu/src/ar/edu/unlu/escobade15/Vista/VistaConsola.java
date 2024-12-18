package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;


import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class VistaConsola implements IVista {

    private Scanner sc;
    ControladorJuego controlador;

    public VistaConsola(){
        this.sc = new Scanner(System.in);
    }




    public void mostrarMenuPrincipal() {
        System.out.println("\n####### MENÚ PRINCIPAL #######");;
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

    public void iniciar() throws RemoteException {
        int opcion;
        System.out.println("¡BIENVENIDO A ESCOBA DE 15!");
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
                        if(controlador.obtenerCantidadJugadores() < 4){
                            controlador.agregarJugador(obtenerNombrejugador());
                            presionarEnter();
                        }
                        else{
                            mostrarMensaje("No se pueden agregar mas jugadores");
                        }

                        break;
                    }
                    case 2: {
                        controlador.jugarPartida();
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


    public Carta solicitarCartaaBajar(List<Carta> cartasEnMano) {

        do {
            System.out.println("Cartas en tu mano:");
            for (int i = 0; i < cartasEnMano.size(); i++) {
                System.out.println((i + 1) + ": " + cartasEnMano.get(i).toString());
            }

            System.out.println("Elige una carta a bajar de tu mano (1, 2, 3, ...):");
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
        System.out.println("2 - Seleccionar carta a jugar");

    }





    public void opcionJugador() throws RemoteException {
        int opcion;

        do {
            mostrarOpcionJugador();
            System.out.println("Ingresa una opción:");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                sc.next();
            }

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    Carta cartaAbajar = solicitarCartaaBajar(controlador.getCartasJugadorActual());
                    controlador.bajaCarta(cartaAbajar);
                    break;
                case 2:
                    Carta cartaAjugar = solicitarCartaAjugar(controlador.getCartasJugadorActual());
                    controlador.SeleccionarCartaAjugar(cartaAjugar);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion < 1 || opcion > 2);


    }


    public void mostrarTurno() throws RemoteException {
        System.out.println("\nTurno del jugador: " + controlador.getJugadorActual().getNombreJugador());

    }


    public Carta solicitarCartaAjugar(List<Carta> cartasEnMano){
        do {
            System.out.println("Cartas en tu mano:");
            for (int i = 0; i < cartasEnMano.size(); i++) {
                System.out.println((i + 1) + ": " + cartasEnMano.get(i).toString());
            }
            System.out.println("Elige una carta a jugar de tu mano: (1, 2, 3):");
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



    public void mostrarMesa(List<Carta> cartas) {
        if (cartas.isEmpty()) {
            System.out.println("No hay cartas en la mesa.");
        } else {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Cartas en mesa:");
            for (int i = 0; i < cartas.size(); i++) {
                System.out.println("" + cartas.get(i).toString());
            }
            System.out.println("-----------------------------------------------------------");

        }
    }


    public void mostrarCartajugador(Jugador jugador){
        int i=1;
        if(jugador.getCartasEnMano().isEmpty()){
            System.out.println("No hay cartas que tenga el jugador en mano");
        }
        else {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Carta del jugador: " + jugador.getNombreJugador() + "\n");
            for (Carta carta : jugador.getCartasEnMano()) {

                System.out.println("" + i + carta.toString());
                i++;
            }
            System.out.println("-----------------------------------------------------------");

        }
    }


    public void mostrarPuntosJugadores(List<Jugador> jugadores){
        System.out.println("\n####### PUNTOS DE JUGADORES #######");
        for(Jugador jugador : jugadores){
            System.out.println("Puntos del jugador " + jugador.getNombreJugador() + ": " + jugador.getPuntuacion());
        }
    }

    public void mostrarMasoRonda(Jugador jugador){
        System.out.println("\n####### CARTAS EN EL MASO JUGADOR " + jugador.getNombreJugador() + " #######");
        for(Carta carta : jugador.getMasoRonda().getCartas()){
            System.out.println(carta.toString());
        }
    }



    public void presionarEnter(){
        System.out.println("Presiona Enter para continuar..");
        sc.nextLine();
        sc.nextLine();
    }




}

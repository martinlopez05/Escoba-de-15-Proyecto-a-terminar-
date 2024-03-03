package Vista;

import Controlador.ControladorJuego;
import Modelo.Carta;
import Modelo.Jugador;
import Modelo.Palo;


import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Ivista{

    private Scanner sc;
    ControladorJuego controlador;

    public VistaConsola(){
        this.sc = new Scanner(System.in);
    }




    public void mostrarMenuPrincipal() {
        System.out.println("########################");
        System.out.println("####### Escoba de 15 #######");
        System.out.println("########################");
        System.out.println();
        System.out.println("Selecciona una opción:");
        System.out.println("1 - Agregar jugador");
        System.out.println("2 - Comenzar el juego");
        System.out.println("3 - ");  // Puedes completar con otras opciones según las necesidades
        System.out.println();
        System.out.println("4 - Salir");
    }

    @Override
    public void setControlador(ControladorJuego controlador) {
        this.controlador=controlador;
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: {
                    // Agregar jugador
                    controlador.agregarJugador();
                    break;
                }
                case 2: {
                    // Comenzar el juego
                    controlador.iniciarjuego();
                    break;
                }
                // Puedes agregar más casos según las opciones disponibles en tu menú
                case 4: {
                    System.out.println("Saliendo del juego. ¡Hasta luego!");
                    break;
                }
                default: {
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
                }
            }

        } while (opcion != 4);
    }


    public String solicitarNombredeUsuario(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Ingrese su nuevo nombre de usuario:");
        String nombre = sc.nextLine();
        return nombre;
    }
    public void mostrarmensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void mostrarGanador(Jugador jugador){
        System.out.println("El ganador de la escoba de 15 es : " + jugador.getNombreJugador() +  "con : " + jugador.getPuntuacion() + "puntos");

    }

    public Carta solicitarCartaaBajar(List<Carta> cartasEnMano) {
        System.out.println("Cartas en tu mano:");
        for (int i = 0; i < cartasEnMano.size(); i++) {
            System.out.println((i + 1) + ": " + cartasEnMano.get(i).toString());
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Elige una carta para bajar (1, 2, 3, ...):");

        int opcion = sc.nextInt();
        return cartasEnMano.get(opcion - 1);
    }


    public void mostrarMesa(List<Carta> cartas){
        System.out.println("\nCartas en mesa: \n");
        for(Carta carta: cartas){
            System.out.println(carta.toString());
        }

    }





}

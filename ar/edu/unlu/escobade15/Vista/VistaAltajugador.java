package Vista;

import java.util.Scanner;

import Modelo.Jugador;

public class VistaAltajugador extends  Vista{
    public Jugador SolcitarDatosJugador(){
        Scanner sc = new Scanner(System.in);
        Mostrarmensaje("\nIngrese los datos del nuevo jugador:");
        System.out.println("Ingrese su nombre de jugador: ");
        String nombre = sc.nextLine();
        return new Jugador(nombre);
        
    }
    
}

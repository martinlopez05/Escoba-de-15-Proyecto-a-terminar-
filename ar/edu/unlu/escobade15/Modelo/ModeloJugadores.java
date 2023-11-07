package Modelo;

import java.util.ArrayList;

public class ModeloJugadores {
    ArrayList<Jugador> Jugadores;

    public ModeloJugadores(){
        Jugadores = new ArrayList<>();

    }

    public void AgregarJugador(Jugador jugador) {
        if (Jugadores.size() < 4) {
            Jugadores.add(jugador);
            System.out.println("Jugador " + jugador.getNombreJugador() + " agregado exitosamente.");
        }
        if (Jugadores.size() == 4) {
                System.out.println("¡Ya se alcanzó la capacidad máxima de jugadores!");
        }
        
        
    }
    

    public ArrayList<Jugador> getJugadores() {
        return Jugadores;
    }
}

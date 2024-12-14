package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import java.util.List;

public interface Ivista {
    public void setControlador(ControladorJuego controlador);
    public void iniciar();
    public void mostrarTurno();
    public void mostrarMesa(List<Carta> cartasMesa);
    public void mostrarMensaje(String mensaje);
    public Carta solicitarCartaAjugar(List<Carta> cartasMesa);
    public Carta solicitarCartaaBajar(List<Carta> cartas);
    public void mostrarCartajugador(Jugador jugadorActual);
    public void opcionJugador();
    public void mostrarMasoRonda(Jugador jugador);
    public void mostrarPuntosJugadores(List<Jugador> jugadores);
    public void mostrarGanador(Jugador jugador);
}

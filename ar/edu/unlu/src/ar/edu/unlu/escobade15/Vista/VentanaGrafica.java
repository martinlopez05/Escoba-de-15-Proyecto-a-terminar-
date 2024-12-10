package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaGrafica extends JFrame implements Ivista{

    ControladorJuego controlador;
    private JButton btnAgregarJugador;
    private JPanel panel1;

    public VentanaGrafica() {
        setVisible(true);
        setLocationRelativeTo(null);
        this.createUIComponents();

    }



    @Override
    public void setControlador(ControladorJuego controlador) {
        this.controlador = controlador;
    }

    @Override
    public void iniciar() {
        btnAgregarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog(VentanaGrafica.this, "Ingrese el nombre del jugador:",
                        "Agregar Jugador", JOptionPane.PLAIN_MESSAGE);
                if (nombre != null && !nombre.trim().isEmpty()) {
                    mostrarMensaje("Jugador agregado Correctamente","info","agregado exitoso");
                } else if (nombre != null) {
                    mostrarMensaje("El nombre del jugador no puede estar vacío","error","error al agregar");
                }

            }
        });
    }

    @Override
    public void mostrarTurno() {

    }

    @Override
    public void mostrarMesa(List<Carta> cartasMesa) {

    }

    @Override
    public void mostrarMensaje(String mensaje) {

    }


    public void mostrarMensaje(String mensaje, String tipo, String titulo) {
        JOptionPane optionpane = new JOptionPane(mensaje);
        if (tipo.equals("info")) {
            optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        }
        if (tipo.equals("error")) {
            optionpane.setMessageType(JOptionPane.ERROR_MESSAGE);
        }

        JDialog dialog = optionpane.createDialog(titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }


    @Override
    public Carta solicitarCartaArecoger(List<Carta> cartasMesa) {
        return null;
    }

    @Override
    public Carta solicitarCartaaBajar(List<Carta> cartas) {
        return null;
    }

    @Override
    public void mostrarCartajugador(Jugador jugadorActual) {

    }

    @Override
    public void opcionJugador() {

    }

    @Override
    public void mostrarGanador(Jugador jugador) {

    }

    @Override
    public void mostrarPuntosjugador(List<Jugador> jugadores) {

    }

    private void createUIComponents() {
        btnAgregarJugador = new JButton("Agregar Jugador");
        panel1 = new JPanel();
        panel1.add(btnAgregarJugador);
    }
}

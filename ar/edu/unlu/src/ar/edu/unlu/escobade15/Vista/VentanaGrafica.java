package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaGrafica extends JFrame implements Ivista{

    ControladorJuego controlador;
    private JButton btnAgregarJugador;
    private JPanel panel1;
    private JLabel labelTitulo;

    public VentanaGrafica() {
        setTitle("Escoba de 15");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.createUIComponents();
        setContentPane(panel1);
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
                    controlador.agregarJugador(nombre);
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
        JOptionPane optionpane = new JOptionPane(mensaje);
        optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionpane.createDialog("error");
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
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
        labelTitulo = new JLabel("Bienvenido a Escoba de 15"); // Texto del label
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel1 = new JPanel();
        panel1.setBackground(Color.green);
        panel1.add(btnAgregarJugador);
        panel1.add(labelTitulo);
    }
}

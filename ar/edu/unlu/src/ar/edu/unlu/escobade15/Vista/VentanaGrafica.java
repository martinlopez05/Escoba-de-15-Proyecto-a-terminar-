package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaGrafica implements Ivista{

    ControladorJuego controlador;
    private DialogoNombre dialogoNombre;
    private String nombreJugador;
    private JPanel panel1;
    private JLabel labelTitulo;
    private JButton jugarPartidaButton;
    private JButton reglasButton;
    private JButton partidasGuardadasButton;
    private JButton salirButton;
    private JFrame frame;

    public VentanaGrafica(){

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        jugarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //controlador.iniciarjuego();

            }
        });
    }


    public void registrarJugador(){
        nombreJugador = dialogoNombre.getNombreJugador();
        controlador.agregarJugador(nombreJugador);
    }



    @Override
    public void setControlador(ControladorJuego controlador) {
        this.controlador = controlador;
    }

    @Override
    public void iniciar() {
        frame = new JFrame();
        frame.setTitle("Escoba de 15");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel1);
        frame.setVisible(true);
        labelTitulo = new JLabel("Bienvenido a Escoba de 15");
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel1 = new JPanel();
        panel1.setBackground(Color.green);
        panel1.add(labelTitulo);
        dialogoNombre = new DialogoNombre();
        dialogoNombre.setSize(400, 200);
        dialogoNombre.setLocationRelativeTo(null);
        dialogoNombre.setVisible(true);
        registrarJugador();
        dialogoNombre.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


    }

    @Override
    public void mostrarTurno() {
        mostrarMensaje("Turno del jugador : " + controlador.getJugadorActual().getNombreJugador());
    }

    @Override
    public void mostrarMesa(List<Carta> cartasMesa) {

    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane optionpane = new JOptionPane(mensaje);
        optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionpane.createDialog("Informacion");
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }

    @Override
    public Carta solicitarCartaAjugar(List<Carta> cartasMesa) {
        return null;
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
    public void mostrarMasoRonda(Jugador jugador) {

    }

    @Override
    public void mostrarPuntosJugadores(List<Jugador> jugadores) {

    }

    @Override
    public void mostrarGanador(Jugador jugador) {

    }






}

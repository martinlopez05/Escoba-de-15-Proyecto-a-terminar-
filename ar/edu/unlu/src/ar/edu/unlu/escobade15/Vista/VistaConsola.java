package ar.edu.unlu.escobade15.Vista;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Carta;
import ar.edu.unlu.escobade15.Modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.List;


public class VistaConsola implements IVista {

    private ControladorJuego controlador;
    private String nombreJugador;
    private boolean jugadorEnPartida = false;
    private JTextField input;
    private JTextArea salida;
    private JButton btnOk;
    private static final Font GAME_FONT = new Font("Courier New", Font.PLAIN, 14);
    private final JFrame escobaFrame = new JFrame("Escoba - Main Menu");

    private final WindowAdapter exit = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            //
        }
    };

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void iniciarVentana() {
        escobaFrame.setTitle("escoba de 15");
        escobaFrame.setResizable(false);
        escobaFrame.setSize(1024, 684);
        escobaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/ar/edu/unlu/escobade15/images/ImagenMenu/escoba15.png"));
        escobaFrame.setIconImage(icon.getImage());
        escobaFrame.setLayout(new BorderLayout());

        // Panel principal (zona negra)
        salida = new JTextArea();
        salida.setBackground(Color.BLACK);
        salida.setForeground(Color.WHITE);
        salida.setFont(GAME_FONT);
        salida.setEditable(false); // Solo lectura
        JScrollPane scrollPane = new JScrollPane(salida);
        escobaFrame.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior (zona gris con JTextField y JButton)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.DARK_GRAY);

        // Campo de texto
        input = new JTextField();
        input.setBackground(Color.BLACK);
        input.setForeground(Color.WHITE);
        input.setCaretColor(Color.BLACK);
        input.setFont(GAME_FONT);
        panelInferior.add(input, BorderLayout.CENTER);

        // Botón
        btnOk = new JButton("OK");
        btnOk.setBackground(Color.green);
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(GAME_FONT);
        panelInferior.add(btnOk, BorderLayout.EAST);

        escobaFrame.add(panelInferior, BorderLayout.SOUTH);

        escobaFrame.setVisible(true);
    }


    @Override
    public void setControlador(ControladorJuego controlador) {
        this.controlador = controlador;
    }

    @Override
    public void iniciar() throws RemoteException {
        iniciarVentana();
        mostrarMenu();
    }

    @Override
    public boolean esAnfitrion() throws RemoteException {
        return (controlador.getNombreJugadorAnfitrion().equals(nombreJugador));
    }


    @Override
    public void solicitarNombre() {
        escobaFrame.removeWindowListener(exit);
        escobaFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        limpiarActionListeners(input, btnOk);

        salida.append("*** BIENVENIDO A LA ESCOBA DE 15 ***\n");
        salida.append("Dame tu nombre jugador: \n");

        // Listener para el campo de texto (Enter) y el botón
        ActionListener login = e -> {
            String nombre = input.getText().trim();
            if (!nombre.isEmpty()) {
                setNombreJugador(nombre);
                this.jugadorEnPartida = true;
                controlador.agregarJugador(nombreJugador);
                limpiar();
                limpiarActionListeners(input,btnOk);
                salida.append("Esperando a que más jugadores se unan...\n");
            } else {
                salida.append("El nombre no puede estar en blanco. Por favor, escriba un nombre.\n");

            }
        };
        btnOk.addActionListener(login);
        input.addActionListener(login);
    }

    @Override
    public void mostrarMenu() {
        limpiarActionListeners(input, btnOk);
        salida.append("*** MENÚ DEL JUEGO ***\n");
        salida.append("1 - Unirse a la partida\n");
        salida.append("2 - Top de jugadores\n");
        salida.append("3 - Reglas\n");
        salida.append("4 - Salir\n");

        ActionListener menu = _ -> {
            String opcion = input.getText().trim();
            input.setText("");

            switch (opcion) {
                case "1": // unirse a partida
                    try {
                        unirseApartida();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "2": // top de jugadores
                    //falta implementar
                    break;
                case "3": // mostrar reglas
                    mostrarReglas();
                    break;
                case "4": //salir
                    System.exit(0);
                    break;
                default: // Opción inválida
                    salida.append("Opción no válida. Por favor, elige una opción entre 1 y 3.\n");
            }
        };
        input.addActionListener(menu);
        btnOk.addActionListener(menu);
    }



    public void comenzarTurnos() throws RemoteException {
        limpiarActionListeners(input, btnOk);
        if (controlador.getJugadorActual().getNombreJugador().equals(nombreJugador)) {
            mostrarTurno();
        } else {
            limpiar();
            mostrarMesa();
            esperarTurno();
        }
    }

    @Override
    public void mostrarReglas(){
        limpiar();
        salida.append("- Reglas de la ESCOBA DE 15\n");
        salida.append("Objetivo del juego:\n");
        salida.append("El objetivo es sumar 15 con una carta de tu mano utilizando las cartas disponibles en la mesa. Cada vez que un jugador logra exactamente 15, realiza una \"escoba\" y gana un punto extra.\n\n");

        salida.append("Cómo se juega:\n");
        salida.append("Cada jugador recibe tres cartas, y se colocan cuatro cartas descubiertas en la mesa. En su turno, un jugador puede usar una carta de su mano para sumar 15 junto con las cartas de la mesa. Luego se vuelve a repartir tres cartas cuando se terminan las que tiene en la mano.\n\n");

        salida.append("Valor de las cartas:\n");
        salida.append("Las cartas tienen su valor numérico natural. La sota vale 8, el caballo 9 y el rey 10. No se usan comodines.\n\n");

        salida.append("Escoba:\n");
        salida.append("Si un jugador logra llevarse todas las cartas de la mesa sumando exactamente 15, se anota una escoba (representada normalmente con un palito o marca).\n\n");

        salida.append("Fin de la ronda:\n");
        salida.append("Una vez que se reparten todas las cartas y se juegan, se hace el conteo de puntos.\n\n");

        salida.append("Puntaje al final de la ronda:\n");
        salida.append("- 1 punto por cada escoba realizada.\n");
        salida.append("- 1 punto por tener más cartas.\n");
        salida.append("- 1 punto por tener más oros.\n");
        salida.append("- 1 punto por tener el 7 de oros (llamado 'siete de oro').\n");
        salida.append("- 1 punto por tener más sietes (se valora quién tiene el mayor número de sietes).\n\n");

        salida.append("Ganador de la partida:\n");
        salida.append("La partida finaliza cuando un jugador alcanza los 21 puntos. También puede definirse otra cantidad de puntos como objetivo según acuerdo previo entre jugadores.\n\n");

        limpiarActionListeners(input,btnOk);

        salida.append(">> Presiona ENTER para volver al menú <<");
        ActionListener volverAmenu = _ -> {
            limpiar();
            mostrarMenu();
        };

        input.addActionListener(volverAmenu);
        btnOk.addActionListener(volverAmenu);
    }

    public void esperarTurno() throws RemoteException {
        salida.append("\nTurno de : " + controlador.getJugadorActual().getNombreJugador() + "\n");
        salida.append("Esperando ... \n");
    }


    @Override
    public void mostrarTurno() throws RemoteException {
        System.out.println("TURNO EMPEZADO");
        limpiarActionListeners(input, btnOk);
        limpiar();


        salida.append("Turno del jugador : " + nombreJugador +  "\n");
        mostrarMesa();
        mostrarCartajugador();
        salida.append("### ¿Que desea hacer? ###\n");
        salida.append("1 - Bajar carta \n");
        salida.append("2 - Seleccionar carta a jugar ");

        ActionListener menuTurno = _ -> {
            String opcion = input.getText().trim();
            input.setText("");

            switch (opcion) {
                case "1":
                    try {
                        solicitarCartaABajar();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "2":
                    try {
                        solicitarCartaAjugar();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default: // Opción inválida
                    salida.append("Opción no válida. Por favor, elige una opción entre 1 y 3.\n");
            }
        };
        input.addActionListener(menuTurno);
        btnOk.addActionListener(menuTurno);

    }


    @Override
    public void solicitarCartaAjugar() throws RemoteException {
        limpiarActionListeners(input, btnOk);
        limpiar();
        mostrarMesa();
        mostrarCartajugador();
        salida.append("Elige la carta a jugar (1 2 3): \n");
        ActionListener jugarCarta = e -> {
            String entrada = input.getText().trim();

            if (!entrada.matches("\\d+")) {
                salida.append("Por favor, ingresa un número válido.\n");
                input.setText("");
                return;
            }

            int opcion = Integer.parseInt(entrada);

            try {
                if (opcion < 1 || opcion > controlador.getCartasEnMano().size()) {
                    salida.append("Opción no válida. Por favor, elige una opción dentro del rango válido.\n");
                    input.setText("");
                    return;
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            Carta cartaSeleccionada = null;
            try {
                cartaSeleccionada = controlador.getCartasEnMano().get(opcion - 1);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            salida.append("Seleccionaste la carta: " + cartaSeleccionada + "\n");
            controlador.jugarCarta(cartaSeleccionada);
        };
        btnOk.addActionListener(jugarCarta);
        input.addActionListener(jugarCarta);
    }

    @Override
    public void solicitarCartaABajar() throws RemoteException {
        limpiarActionListeners(input, btnOk);
        limpiar();
        mostrarMesa();
        mostrarCartajugador();

        List<Carta> cartasEnMano = controlador.getCartasEnMano();


        salida.append("Elige la carta a bajar (1 2 3): \n");
        ActionListener bajaCarta = e -> {
            String entrada = input.getText().trim();

            if (!entrada.matches("\\d+")) {
                salida.append("Por favor, ingresa un número válido.\n");
                input.setText("");
                return;
            }

            int opcion = Integer.parseInt(entrada);

            if (opcion < 1 || opcion > cartasEnMano.size()) {
                salida.append("Opción no válida. Por favor, elige una opción dentro del rango válido.\n");
                input.setText("");
                return;
            }

            Carta cartaSeleccionada = null;
            cartaSeleccionada = cartasEnMano.get(opcion - 1);

            salida.append("Seleccionaste la carta: " + cartaSeleccionada + "\n");
            controlador.bajaCarta(cartaSeleccionada);
        };
        btnOk.addActionListener(bajaCarta);
        input.addActionListener(bajaCarta);
    }


    @Override
    public void mostrarCartajugador() throws RemoteException {
        List<Carta> cartasEnMano = controlador.getCartasEnMano();
        int i = 1;
        if (cartasEnMano.isEmpty()) {
            salida.append("No hay cartas que tenga el jugador en mano\n");
        } else {
            salida.append("\n-----------------------------------------------------------\n");
            salida.append("Cartas del jugador: " + nombreJugador + "\n\n");
            mostrarCartas(cartasEnMano);
            salida.append("-----------------------------------------------------------\n");
        }

    }

    @Override
    public void mostrarMesa() throws RemoteException {
        List<Carta> cartasMesa = controlador.getCartasMesa();
        if (cartasMesa.isEmpty()) {
            salida.append("No hay cartas en la mesa.");
        } else {
            salida.append("\n-----------------------------------------------------------\n");
            salida.append("Cartas en mesa: \n");
            mostrarCartas(cartasMesa);
            System.out.println("-----------------------------------------------------------");

        }
    }



    public void mostrarCartas(List<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            salida.append((i + 1) + ": " + cartas.get(i).toString() + "\n");
        }
    }


    // Le enviamos mensaje al jugador actual
    @Override
    public void mostrarSuma15JugadorActual() throws RemoteException {
        if(controlador.getJugadorActual().getNombreJugador().equals(nombreJugador)){
            salida.append(" TU CARTA SUMA 15 CON ALGUNA/S DE LA MESA \nPresiona ENTER para continuar...\n");
            limpiarActionListeners(input, btnOk);
            input.requestFocusInWindow();
            input.setText("");
            ActionListener esperaEnter = e -> {

                controlador.actualizarTurno();
                limpiarActionListeners(input, btnOk);
            };

            input.addActionListener(esperaEnter);
            btnOk.addActionListener(esperaEnter);
        }
    }

    @Override
    public void mostrarHizoEscobaJugadorActual() throws RemoteException {
        if(controlador.getJugadorActual().getNombreJugador().equals(nombreJugador)){
            salida.append("¡HICISTE ESCOBA FELICITACIONES! \nPresiona ENTER para continuar...\n");
            limpiarActionListeners(input, btnOk);
            input.requestFocusInWindow();
            input.setText("");
            ActionListener esperaEnter = e -> {

                controlador.actualizarTurno();
                limpiarActionListeners(input, btnOk);
            };

            input.addActionListener(esperaEnter);
            btnOk.addActionListener(esperaEnter);
        }
        else{
            salida.append("¡El jugador " + controlador.getJugadorActual().getNombreJugador() + " hizo escoba! \n");
        }
    }

    @Override
    public void mostrarNoSuma15JugadorActual() throws RemoteException {
        if(controlador.getJugadorActual().getNombreJugador().equals(nombreJugador)){
            salida.append(" TU CARTA NO SUMA 15 CON NINGUNA DE LA MESA  \nPresiona ENTER para volver..\n");
            limpiarActionListeners(input, btnOk);
            input.requestFocusInWindow();
            input.setText("");
            ActionListener esperaEnter = e -> {
                try {
                    mostrarTurno();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            };

            input.addActionListener(esperaEnter);
            btnOk.addActionListener(esperaEnter);
        }
    }



    @Override
    public void mostrarMasoRonda() throws RemoteException {
        List<Jugador> jugadores = controlador.getJugadores();
        System.out.println("MAZO RONDA DE CADA JUGADOR");
        for (Jugador jugador : jugadores) {
            System.out.println("\nnombre del jugador: " + jugador.getNombreJugador());
            System.out.println("cartas del mazo: \n");
            for (Carta carta : jugador.getMasoRonda().getCartas()) {
                System.out.println(carta.toString());
            }
        }
    }


    @Override
    public void mostrarGanador(Jugador jugador) {
    }


    @Override
    public void terminarRonda() throws RemoteException {
        mostrarPuntosJugadores();
        System.out.println("ronda terminada");
        mostrarMasoRonda();
        salida.append("\n>>> PRESIONA ENTER PARA INICIAR LA SIGUIENTE RONDA <<<\n");

        limpiarActionListeners(input, btnOk);
        input.setText("");
        input.requestFocusInWindow();

        ActionListener continuar = new ActionListener() {
            boolean yaContinuo = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (yaContinuo) return; // solo se ejecuta una vez
                yaContinuo = true;
                limpiarActionListeners(input, btnOk);
                try {
                    controlador.iniciarNuevaRonda();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        };

        input.addActionListener(continuar);
        btnOk.addActionListener(continuar);
    }

    @Override
    public void unirseApartida() throws RemoteException {
        if(controlador.obtenerCantJugadores() == 4){
            limpiar();
            salida.append("Error al unirse\n");
            salida.append("Capacidad de jugadores alcanzada\n");
            limpiarActionListeners(input,btnOk);

            salida.append(">> Presiona ENTER para volver al menú <<");
            ActionListener volverAmenu = _ -> {
                limpiar();
                mostrarMenu();
            };

            input.addActionListener(volverAmenu);
            btnOk.addActionListener(volverAmenu);
        }
        else{
            solicitarNombre();
        }

    }




    @Override
    public void jugadorSeUnioApartida() throws RemoteException {
        if (jugadorEnPartida) {
            String jugadorAgregado = controlador.getNombreJugadorAgregado();
            salida.append("jugador " + jugadorAgregado + " se unio a la partida\n");
        }
    }

    @Override
    public void habilitarInicioPartida() throws RemoteException {
        if (esAnfitrion()) {
            salida.append("Sos el anfitrión. Podés iniciar la partida cuando quieras (ENTER o botón OK).\n");

            // Solo agregamos una vez el listener
            limpiarActionListeners(input, btnOk);

            ActionListener iniciarPartidaListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlador.iniciarPartida();
                }
            };

            btnOk.addActionListener(iniciarPartidaListener);
            input.addActionListener(iniciarPartidaListener);
        }
    }

    @Override
    public void terminarPartida() throws RemoteException {
        limpiar();
        mostrarPuntosJugadores();
        String nombreGanador = controlador.getNombreGanador();
        salida.append("\n¡EL GANADOR DE LA PARTIDA ES EL USUARIO: " + nombreGanador + "\n");
        salida.append("\n¡FELICITACIONES!\n");
        salida.append("Presione ENTER para volver al menu ...\n");
        limpiarActionListeners(input, btnOk);
        input.setText("");
        SwingUtilities.invokeLater(() -> input.requestFocusInWindow());

        ActionListener continuar = new ActionListener() {
            boolean yaContinuo = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (yaContinuo) return;
                yaContinuo = true;
                limpiar();
                try {
                    reiniciarVista();
                    controlador.reinciarPartida();
                    mostrarMenu();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
        input.addActionListener(continuar);
        btnOk.addActionListener(continuar);
    }


    @Override
    public void reiniciarVista(){
        nombreJugador = null;
        jugadorEnPartida = false;
        limpiar();
    }

    public void mostrarPuntosJugadores() throws RemoteException {
        List<Jugador> jugadores = controlador.getJugadores();

        salida.append("\n*** RONDA TERMINADA ***\n");
        salida.append("*** RECUENTOS DE PUNTOS ***\n");
        salida.append(String.format("%-20s | %-10s\n", "JUGADOR", "PUNTOS"));
        salida.append("---------------------+-----------\n");
        for (Jugador jugador : jugadores) {
            salida.append(String.format("%-20s | %-10d\n", jugador.getNombreJugador(), jugador.getPuntuacion()));

        }
        salida.append("---------------------+-----------\n");
    }


    private void limpiarActionListeners(JTextField textField, JButton botonOk) {
        for (ActionListener al : textField.getActionListeners()) {
            textField.removeActionListener(al);
        }
        for (ActionListener al : botonOk.getActionListeners()) {
            botonOk.removeActionListener(al);
        }
    }

    public void limpiar() {
        salida.setText("");
        input.setText("");
    }

}

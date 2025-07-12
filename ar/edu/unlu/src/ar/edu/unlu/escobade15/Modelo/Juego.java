package ar.edu.unlu.escobade15.Modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends ObservableRemoto implements IJuego {

    public final int puntuacionParaGanar = 3;


    private Baraja baraja;
    private List<Jugador> jugadores;
    public Jugador jugadorActual;
    public Mesa mesajuego;
    private boolean rondaTerminada;
    private String nombreJugadorAgregado;
    private String nombreJugadorAnfitrion;
    private String nombreJugadorGanador;

    public Juego() {
        jugadores = new ArrayList<>();
    }

    @Override
    public String getNombreJugadorAnfitrion() throws RemoteException {
        return this.nombreJugadorAnfitrion;
    }

    //retorna una lista de los jugador en la partida
    @Override
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    //retorna el Jugador Actual -> el que esta en su turno de juego
    @Override
    public Jugador getJugadorActual() {
        return jugadorActual;
    }


    @Override
    public Mesa getMesajuego() {
        return mesajuego;
    }

    @Override
    public Baraja getBaraja() {
        return baraja;
    }

    @Override
    public String getNombreJugadorAgregado() {
        return nombreJugadorAgregado;
    }

    @Override
    public String getNombreJugadorGanador() {
        return this.nombreJugadorGanador;
    }

    @Override
    public void reiniciarPartida() throws RemoteException {
        jugadores.clear();
        nombreJugadorAnfitrion = null;
        rondaTerminada = false;
        nombreJugadorGanador = null;
    }


    // metodo para repartir cartas a cada jugador
    @Override
    public void repartirMano() throws RemoteException {

        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 3; i++) {
                jugador.recibirCarta(baraja.sacarCarta());
            }

        }
    }


    //metodo para repartir cartas en mesa
    @Override
    public void repartirMesa() {
        for (int i = 0; i < 4; i++) {
            if (!barajaEsVacia()) {
                mesajuego.agregarCarta(baraja.sacarCarta());
            } else {
                break;
            }
        }
    }


    //metodo que retorna true  si los jugadores se quedan sin carta
    @Override
    public boolean jugadoresSinCartas() {
        return jugadores.stream().allMatch(Jugador::faltanCartasenMano);
    }


    //metodo para iniciar una partida
    @Override
    public void iniciarPartida() throws RemoteException {
        if (jugadores.size() >= 2) {
            this.baraja = new Baraja();
            this.mesajuego = new Mesa();
            baraja.mezclarCartas();
            repartirMano();
            repartirMesa();
            sortearTurno();
            notificarObservadores(Evento.PARTIDA_INICIADA);
        }
    }

    //metodo que retorna true si la baraja se queda sin cartas
    @Override
    public boolean barajaEsVacia() {
        return baraja.esVacia();
    }


    //metodo para iniciar Nueva Ronda
    @Override
    public void iniciarNuevaRonda() throws RemoteException {
        this.rondaTerminada = false;
        for (Jugador jugador : jugadores) {
            jugador.getMasoRonda().vaciarMaso();
            jugador.vaciarEscobas();
        }
        mesajuego.vaciar();
        Baraja baraja = new Baraja();
        this.baraja = baraja;
        repartirMesa();
        repartirMano();
        notificarObservadores(Evento.RONDA_NUEVA_INICIADA);
    }


    //metodo que retorna un booleano que verifica si la partida se puede terminar
    @Override
    public boolean partidaTerminada() {
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() >= puntuacionParaGanar) {
                return true;
            }
        }
        return false;
    }


    //metodo que cambia al jugadorActual en la partida
    @Override
    public void actualizarTurno() throws RemoteException {

        if (rondaTerminada) {
            return;
        }

        int indiceActual = jugadores.indexOf(jugadorActual);
        if (indiceActual != -1) {
            int siguienteIndice = (indiceActual + 1) % jugadores.size();
            jugadorActual = jugadores.get(siguienteIndice);
            notificarObservadores(Evento.CAMBIO_DE_TURNO);
        }

    }


    //metodo donde un jugador baja una carta a la mesa
    @Override
    public void jugadorBajaCarta(Carta carta) throws RemoteException {
        if (!jugadorActual.getCartasEnMano().isEmpty()) {
            mesajuego.agregarCarta(carta);
            jugadorActual.sacarCartaMano(carta);

            if (jugadoresSinCartas()) {
                int cartasNecesarias = jugadores.size() * 3;

                if (barajaEsVacia() || baraja.obtenerCantCartas() < cartasNecesarias) {
                    terminarRonda(); // ← Terminás la ronda directamente
                } else {
                    repartirMano();
                    actualizarTurno();
                }
            } else {
                actualizarTurno();
            }
        }


    }

    //metodo para generar las combinaciones de las cartas de la mesa y se retorna una lista de esas combinaciones
    public  List<List<Carta>> generarCombinaciones(List<Carta> cartas) throws RemoteException {
        List<List<Carta>> combinacionesPosibles = new ArrayList<>();
        int n = cartas.size();
        for (int i = 0; i < (1 << n); i++) {
            List<Carta> subconjuntoCartas = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subconjuntoCartas.add(cartas.get(j));
                }
            }
            combinacionesPosibles.add(subconjuntoCartas);
        }
        return combinacionesPosibles;
    }



    //metodo para obtener la combinacion de carta/s de la mesa que suman 15 con la carta del jugador actual
    @Override
    public List<Carta> obtenerCombinacion15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException {
        List<List<Carta>> subconjuntos = generarCombinaciones(cartasMesa);

        for (List<Carta> subconjunto : subconjuntos) {
            int suma = cartaJugador.getValor();
            for (Carta carta : subconjunto) {
                suma += carta.getValor();
            }
            if (suma == 15) {
                return subconjunto;
            }

        }
        return null;

    }

    //metodo que retorna un booleano que verifica si se puede iniciar partida
    public boolean sePuedeIniciarPartida() {
        return jugadores.size() >= 2;
    }


    //metodo que retorna true si se encuentra alguna combinacion de carta/s de la mesa que suman 15 con la del jugador
    @Override
    public boolean suman15(Carta cartaJugador, List<Carta> cartasMesa) throws RemoteException {

        List<List<Carta>> combinacionesPosibles = generarCombinaciones(cartasMesa);
        for (List<Carta> combinacion : combinacionesPosibles) {
            int suma = cartaJugador.getValor();
            for (Carta carta : combinacion) {
                suma += carta.getValor();
            }
            if (suma == 15) {
                if (combinacion.size() == mesajuego.getCartasMesa().size()) {
                    notificarObservadores(Evento.SUMAN_15_CON_TODAS);
                    jugadorActual.agregarEscoba();
                    return true;
                } else {
                    notificarObservadores(Evento.SUMAN_15);
                    return true;
                }

            }
        }
        notificarObservadores(Evento.NO_SUMA_NINGUNA_15);
        return false;
    }


    //metodo donde el jugador selecciona una carta de su mano y suma 15 con alguna/s de la mesa
    @Override
    public void jugarCarta(Carta cartaJugador) throws RemoteException {
        if (suman15(cartaJugador, mesajuego.getCartasMesa())) {
            List<Carta> cartaEnJuegoMesa = obtenerCombinacion15(cartaJugador, mesajuego.getCartasMesa());

            for (Carta carta : cartaEnJuegoMesa) {
                mesajuego.sacarCarta(carta);
                jugadorActual.agregarCartaMasoRonda(carta);
            }
            jugadorActual.sacarCartaMano(cartaJugador);
            jugadorActual.agregarCartaMasoRonda(cartaJugador);

            if (jugadoresSinCartas()) {
                int cartasNecesarias = jugadores.size() * 3;

                if (barajaEsVacia() || baraja.obtenerCantCartas() < cartasNecesarias) {
                    terminarRonda(); // se termina la ronda
                } else {
                    repartirMano();
                    actualizarTurno();
                }
            }


        }

    }

    //retorna las cartas de la mesa
    @Override
    public List<Carta> cartasMesa() {
        return getMesajuego().getCartasMesa();
    }

    //retorna las carta en mano del jugador
    @Override
    public List<Carta> cartaEnManoJugador() {
        return getJugadorActual().getCartasEnMano();
    }


    //metodo para agregar un jugador dado su nombre
    @Override
    public void agregarJugador(String nombre) throws RemoteException {
        Jugador jugador = new Jugador(nombre);
        jugadores.add(jugador);

        if (jugadores.size() == 1) {
            nombreJugadorAnfitrion = nombre;
        }

        this.nombreJugadorAgregado = nombre;
        notificarObservadores(Evento.JUGADOR_AGREGADO);
        if (sePuedeIniciarPartida()) {
            notificarObservadores(Evento.SE_PUEDE_INICIAR);

        }
    }

        //metodo en el cual se sortea el turno en el inicio de la partida
        @Override
        public void sortearTurno () {
            if (!jugadores.isEmpty()) {
                Random random = new Random();
                int indiceAleatorio = random.nextInt(jugadores.size());
                jugadorActual = jugadores.get(indiceAleatorio);
            }
        }


        //metodo para obtener el nombre del ganador de la partida
        @Override
        public String obtenerGanador () {
            Jugador ganador = null;

            for (Jugador jugador : jugadores) {
                if (jugador.getPuntuacion() >= puntuacionParaGanar) {
                    ganador = jugador;
                }
            }
            return ganador.getNombreJugador();
        }

        //metodo para terminar la ronda actual
        @Override
        public void terminarRonda () throws RemoteException {
            List<Carta> cartasEnMesa = new ArrayList<>(cartasMesa());
            if (!mesajuego.mesaVacia()) {
                for (Carta carta : cartasEnMesa) {
                    jugadorActual.agregarCartaMasoRonda(carta);
                    mesajuego.sacarCarta(carta);
                }
                for (Jugador jugador : jugadores) {
                    for (Carta carta : jugador.getCartasEnMano()) {
                        jugador.agregarCartaMasoRonda(carta);
                    }
                    jugador.getCartasEnMano().clear();
                }
            }
            sumarPuntoalFinal();
            this.rondaTerminada = true;
            if (partidaTerminada()) {
                terminarPartida();
            } else {
                notificarObservadores(Evento.FIN_DE_RONDA);
            }
        }


        //metodo para terminar la partida
        @Override
        public void terminarPartida () throws RemoteException {
            this.nombreJugadorGanador = obtenerGanador();
            notificarObservadores(Evento.PARTIDA_FINALIZADA);
        }


        //metodo para obtener la cantidad de jugadores
        @Override
        public int obtenercantJugadores () {
            return jugadores.size();
        }


        //metodo que retorna true si hay escoba de mano (todas las cartas de la mesa suman exactamente 15)
        @Override
        public boolean sepuedeEscobadeMano () throws RemoteException {
            if (mesajuego.escobaDeMano()) {
                notificarObservadores(Evento.HAY_ESCOBA_DE_MANO);
                return true;
            } else {
                return false;
            }

        }

        //metodo donde se realiza el manejo de las cartas si hay escoba de mano
        @Override
        public void hacerEscobaDeMano () throws RemoteException {
            List<Carta> cartasEnMesa = new ArrayList<>(cartasMesa());
            for (Carta carta : cartasEnMesa) {
                mesajuego.sacarCarta(carta);
                jugadorActual.agregarCartaMasoRonda(carta);
            }
            sumarpunto(1);

        }

        //metodo en el cual se suma un punto al jugador actual
        @Override
        public void sumarpunto ( int punto) throws RemoteException {
            jugadorActual.sumarpunto(1);
        }


        //metodo para sumar puntos al final de la Ronda
        @Override
        public void sumarPuntoalFinal () throws RemoteException {
            List<Jugador> maxCartas = new ArrayList<>();
            List<Jugador> maxOros = new ArrayList<>();
            List<Jugador> maxSietes = new ArrayList<>();

            int maxCantCartas = 0;
            int maxCantOros = 0;
            int maxCantSietes = 0;

            for (Jugador jugador : jugadores) {
                MasoJugador maso = jugador.getMasoRonda();

                jugador.sumarpunto(jugador.getEscobas());

                if (maso.todoslosOros()) {
                    jugador.sumarpunto(1);
                }
                if (maso.los4sieste()) {
                    jugador.sumarpunto(1);
                }
                if (maso.tiene7oro()) {
                    jugador.sumarpunto(1);
                }


                int cantOros = maso.cantoros();
                if (cantOros > maxCantOros) {
                    maxCantOros = cantOros;
                    maxOros.clear();
                    maxOros.add(jugador);
                } else if (cantOros == maxCantOros) {
                    maxOros.add(jugador);
                }


                int cantCartas = maso.cantCartas();
                if (cantCartas > maxCantCartas) {
                    maxCantCartas = cantCartas;
                    maxCartas.clear();
                    maxCartas.add(jugador);
                } else if (cantCartas == maxCantCartas) {
                    maxCartas.add(jugador);
                }


                int cantSietes = maso.cantSiestes();
                if (cantSietes > maxCantSietes) {
                    maxCantSietes = cantSietes;
                    maxSietes.clear();
                    maxSietes.add(jugador);
                } else if (cantSietes == maxCantSietes) {
                    maxSietes.add(jugador);
                }
            }


            for (Jugador jugador : maxOros) {
                jugador.sumarpunto(1);
            }

            for (Jugador jugador : maxCartas) {
                jugador.sumarpunto(1);
            }

            for (Jugador jugador : maxSietes) {
                jugador.sumarpunto(1);
            }
        }


    }

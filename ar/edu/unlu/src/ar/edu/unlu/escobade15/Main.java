package ar.edu.unlu.escobade15;


import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Modelo.Juego;
import ar.edu.unlu.escobade15.Vista.IVista;
import ar.edu.unlu.escobade15.Vista.VistaConsola;

import java.rmi.RemoteException;


public class Main {
    public static void main(String[] args) throws RemoteException {

        Juego juego = new Juego();
        IVista vistaConsola = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(vistaConsola);
        vistaConsola.iniciar();


    }

}


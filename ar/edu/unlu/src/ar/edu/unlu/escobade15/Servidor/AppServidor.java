package ar.edu.unlu.escobade15.Servidor;

import ar.edu.unlu.escobade15.Modelo.IJuego;
import ar.edu.unlu.escobade15.Modelo.Juego;
import ar.edu.unlu.rmimvc.RMIMVCException;

import java.rmi.RemoteException;

public class AppServidor {
    public static void main(String[] args) {
        try {
            IJuego juego = new Juego();
            ar.edu.unlu.rmimvc.servidor.Servidor servidor = new ar.edu.unlu.rmimvc.servidor.Servidor("127.0.0.1",45000);
            servidor.iniciar(juego);
            System.out.println("SERVIDOR CORRIENDO...");
        }catch (RemoteException | RMIMVCException e){
            e.printStackTrace();
        }
    }
}

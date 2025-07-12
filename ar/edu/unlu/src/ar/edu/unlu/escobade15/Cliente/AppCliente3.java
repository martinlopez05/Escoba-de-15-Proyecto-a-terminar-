package ar.edu.unlu.escobade15.Cliente;

import ar.edu.unlu.escobade15.Controlador.ControladorJuego;
import ar.edu.unlu.escobade15.Vista.IVista;
import ar.edu.unlu.escobade15.Vista.VistaConsola;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;

import java.rmi.RemoteException;

public class AppCliente3 {
    public static void main(String[] args) {
        IVista vista = new VistaConsola();
        IControladorRemoto controladorRemoto = new ControladorJuego(vista);
        ar.edu.unlu.rmimvc.cliente.Cliente cliente = new ar.edu.unlu.rmimvc.cliente.Cliente("127.0.0.1", 45003, "127.0.0.1", 45000);
        try {
            cliente.iniciar(controladorRemoto);
            vista.iniciar();
        } catch (RMIMVCException | RemoteException e) {
            e.printStackTrace();
        }
    }
}

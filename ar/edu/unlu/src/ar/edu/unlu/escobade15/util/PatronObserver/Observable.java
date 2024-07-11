package ar.edu.unlu.escobade15.util.PatronObserver;

import ar.edu.unlu.escobade15.Modelo.Evento;

public interface Observable {
    public void agregarObservador(Observer observador);
    public void notificar (Evento o);

    public void eliminarObservador ( Observer observador);
}

package Modelo;

public interface Observable {
    public void agregarObservador(Observer observador);
    public void notificar (Evento o);

    public void eliminarObservador ( Observer observador);
}

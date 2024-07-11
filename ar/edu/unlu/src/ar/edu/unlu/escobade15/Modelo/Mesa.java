package ar.edu.unlu.escobade15.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {


    private List<Carta> CartasMesa;

    public Mesa (){
        this.CartasMesa = new ArrayList<>();
    }



    public void agregarCarta(Carta carta) {
        CartasMesa.add(carta);
    }

    public void sacarCarta(Carta carta){
        CartasMesa.remove(carta);
    }

    public boolean MesaVacia() {
        return CartasMesa.isEmpty();
    }

    public List<Carta> getCartasMesa() {
        return CartasMesa;
    }

    public boolean escobaDeMano(){
        int suma = 0;

        for (Carta carta : CartasMesa) {
            suma += carta.getValor();

        }

        return suma == 15;
    }
;


}

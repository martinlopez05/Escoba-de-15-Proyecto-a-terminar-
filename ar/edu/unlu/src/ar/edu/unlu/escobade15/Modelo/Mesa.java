package ar.edu.unlu.escobade15.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {


    private List<Carta> cartasMesa;

    public Mesa (){
        this.cartasMesa = new ArrayList<>();
    }



    public void agregarCarta(Carta carta) {
        cartasMesa.add(carta);
    }

    public void sacarCarta(Carta carta){
        cartasMesa.remove(carta);
    }

    public boolean mesaVacia() {
        return cartasMesa.isEmpty();
    }

    public List<Carta> getCartasMesa() {
        return cartasMesa;
    }

    public boolean escobaDeMano(){
        int suma = 0;

        for (Carta carta : cartasMesa) {
            suma += carta.getValor();

        }

        return suma == 15;
    }
;


}

package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {

    public Mesa (){
        this.CartasMesa = new ArrayList<>();
    }

    private List<Carta> CartasMesa;

    public void dejarCarta(Carta carta) {
        CartasMesa.add(carta);
    }

    public void agarrarCarta(Carta carta){
        CartasMesa.remove(carta);
    }

    public boolean hayCartasenMesa() {
        if(CartasMesa.size()==0){
            return false;
        }
        else{
            return true;
        }

    }

    public List<Carta> getCartasMesa() {
        return CartasMesa;
    }


    public void mostrarCartasenMesa() {
        System.out.println("\nCarta en la mesa\n");
        for(Carta carta : CartasMesa){
            System.out.println("" + carta.toString());

        }

    }


    public boolean sepuedeEscobadeMano(){
        int suma = 0;
        for (Carta cartaMesa : CartasMesa) {
            suma += cartaMesa.getValor();
        }
        return suma == 15;
    }

}

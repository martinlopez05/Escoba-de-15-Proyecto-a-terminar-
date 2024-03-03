package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {


    private List<Carta> CartasMesa;
    public Mesa (){

        this.CartasMesa = new ArrayList<>();
    }



    public void dejarCarta(Carta carta) {
        CartasMesa.add(carta);
    }

    public void agarrarCarta(Carta carta){
        CartasMesa.remove(carta);
    }

    public boolean MesaVacia() {
        return CartasMesa.isEmpty();
    }

    public List<Carta> getCartasMesa() {
        return CartasMesa;
    }


}

package Modelo;

import java.util.List;

public class Mesa {

    private List<Carta> CartaMesa;

    public void DejarCarta(Carta carta) {
        CartaMesa.add(carta);
    }

    public boolean HayCartasenMesa() {
        if(CartaMesa.size()==0){
            return false;
        }
        else{
            return true;
        }

    }

}

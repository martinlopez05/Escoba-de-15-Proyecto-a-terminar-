package ar.edu.unlu.escobade15.Modelo;

import java.util.*;

public class MasoJugador {

    private List<Carta> cartas;


    public MasoJugador(){
        cartas = new ArrayList<>();

    }


    public void agregarCarta(Carta carta){
        cartas.add(carta);

    };

    public void vaciarMaso(){
        cartas.clear();
    }

    public int cantSiestes(){
        int cont = 0;
        for(Carta carta : cartas){
            if(carta.getNumero()==7){
                cont++;
            }
        }

        return cont;
    }

    public int cantCartas(){
        int cont = 0;
        for(Carta carta : cartas){
            cont++;
        }

        return cont;
    }

    public boolean los4sieste(){
        int cont= 0;
        List<Carta> cartas = new ArrayList<>();
        for(Carta carta : cartas){
            if(carta.getNumero()==7){
                cartas.add(carta);
            }
        }

        for (Carta carta : cartas){
            if(carta.getPalo()== Palo.ORO){
                cont++;
            }
            if(carta.getPalo() == Palo.BASTO){
                cont++;
            }

            if(carta.getPalo() == Palo.ESPADA){
                cont++;
            }
            if(carta.getPalo() == Palo.COPA){
                cont++;
            }
        }

        return cont==4;
    }


    public int cantoros(){
        int cont = 0;
        for(Carta carta : cartas){
            if(carta.getPalo() == Palo.ORO){
                cont++;
            }
        }
        return cont;
    }

    public boolean todoslosOros(){
        int cont =0 ;
        for(Carta carta : cartas){
            if(carta.getPalo()==Palo.ORO){
                if(carta.getNumero() == 1){
                    cont++;
                }
                if(carta.getNumero() == 2){
                    cont++;
                }
                if(carta.getNumero() == 3){
                    cont++;
                }
                if(carta.getNumero() == 4){
                    cont++;
                }
                if(carta.getNumero() == 5){
                    cont++;
                }
                if(carta.getNumero() == 6){
                    cont++;
                }
                if(carta.getNumero() == 7){
                    cont++;
                }
                if(carta.getNumero() == 10){
                    cont++;
                }
                if(carta.getNumero() == 11){
                    cont++;
                }
                if(carta.getNumero() == 12){
                    cont++;
                }


            }
        }
        return cont==10;

    }

    public boolean tiene7oro(){
        for(Carta carta : cartas){
            if(carta.getNumero()==7 && carta.getPalo()==Palo.ORO){
                return true;
            }
        }

        return false;
    }




}

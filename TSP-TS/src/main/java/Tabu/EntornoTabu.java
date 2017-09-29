package Tabu;

public class EntornoTabu {

    public int [][] distancias;

    public EntornoTabu(){

    }

    public int funcionObjetivo(int solucion[]){

        int valor = 0;

        for(int i = 0 ; i < solucion.length-1; i++){
            valor+= distancias[solucion[i]][solucion[i+1]];
        }

        return valor;

    }
}

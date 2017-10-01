package Tabu;

import java.awt.*;

public class Tabu {

    private int[][] distancias;
    private int[] caminoInicial;

    public Tabu(){
        //prueba
        this.distancias = //Distance matrix, 5x5, used to represent distances
                new int[][]{{0, 1, 3, 4, 5},
                        {1, 0, 1, 4, 8},
                        {3, 1, 0, 5, 1},
                        {4, 4, 5, 0, 2},
                        {5, 8, 1, 2, 0}};


        this.caminoInicial = new int[]{0, 1, 2, 3, 4, 0};
    }

    /**
     * Funcion objetivo: suma de distancias
     */
    private int funcionObjetivo(int[] solucion){
        int valor = 0;

        for(int i = 0 ; i < solucion.length-1; i++){
            valor += this.distancias[solucion[i]][solucion[i+1]];
        }
        return valor;
    }

    public int[] generarCamino(int numeroIteraciones, int listaTabuTamahno, int listaTabuPermanencia){
        int[] solucionActual =  new int[this.caminoInicial.length];  //inicializar solucion
        System.arraycopy(this.caminoInicial, 0, solucionActual, 0, this.caminoInicial.length);

        ListaTabu listaTabu = new ListaTabu(listaTabuTamahno, listaTabuPermanencia);

        int[] mejorSol = new int[solucionActual.length]; //la mejor solucion hasta el momento
        System.arraycopy(solucionActual, 0, mejorSol, 0, mejorSol.length);
        int mejorCosto = this.funcionObjetivo(mejorSol);

        for (int i = 0; i < numeroIteraciones; i++) { // iterar segun parametro

            solucionActual = this.obtenerMejoresVecinos(listaTabu, this.distancias, solucionActual);

            int costoActual = this.funcionObjetivo(solucionActual);

            if (costoActual < mejorCosto) {
                System.arraycopy(solucionActual, 0, mejorSol, 0, mejorSol.length);
                mejorCosto = costoActual;
            }
        }

        return mejorSol;
    }

    private int[] obtenerMejoresVecinos(ListaTabu listaTabu,
                                        int[][] distancias,
                                        int[] initSolution) {

        int[] mejorSol = new int[initSolution.length]; //this is the best Solution So Far
        System.arraycopy(initSolution, 0, mejorSol, 0, mejorSol.length);
        int mejorCosto = this.funcionObjetivo(initSolution);
        int nodo1 = 0;
        int nodo2 = 0;
        boolean primerVecino = true;

        for (int i = 1; i < mejorSol.length - 1; i++) {
            for (int j = 2; j < mejorSol.length - 1; j++) {
                if (i == j) {
                    continue;
                }

                int[] mejorSolActual = new int[mejorSol.length]; //mejor solucion actual
                System.arraycopy(mejorSol, 0, mejorSolActual, 0, mejorSolActual.length);

                mejorSolActual = Tabu.intercambiarNodos(i, j, initSolution); //Intercambiar nodos i y j
                // calcular el nuevo mejor costo
                int mejorCostoActual = this.funcionObjetivo(mejorSolActual);

                if ((mejorCostoActual > mejorCosto || primerVecino) && !listaTabu.contieneMovimiento(i,j)) { //if better move found, store it
                    primerVecino = false;
                    nodo1 = i;
                    nodo2 = j;
                    System.arraycopy(mejorSolActual, 0, mejorSol, 0, mejorSolActual.length);
                    mejorCosto = mejorCostoActual;
                }
            }
        }

        if (nodo1 != 0) {
            listaTabu.decrementarListaTabu();
            listaTabu.agregarNodo(nodo1, nodo2);
        }
        return mejorSol;


    }

    // intercambiar dos nodos
    public static int[] intercambiarNodos(int nodo1, int nodo2, int[] solucion) {
        int temp = solucion[nodo1];
        solucion[nodo1] = solucion[nodo2];
        solucion[nodo2] = temp;
        return solucion;
    }
}

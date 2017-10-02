package Tabu;

import java.awt.*;

public class Tabu {

    private int[][] distancias;
    private int[] caminoInicial;
    private long duracion;

    public Tabu(){
        //prueba
        this.distancias = //Distance matrix, 5x5, used to represent distances
                new int[][]{{0, 1, 3, 4, 5},
                        {1, 0, 1, 4, 8},
                        {3, 1, 0, 5, 1},
                        {4, 4, 5, 0, 2},
                        {5, 8, 1, 2, 0}};


        this.caminoInicial = new int[]{0, 1, 2, 3, 4, 0};
        this.duracion = 0;
    }


    public Tabu(int[][] distancias, int[] caminoInicial){
        this.distancias = distancias;
        this.caminoInicial = caminoInicial;
        this.duracion = 0;
    }

    /**
     * Funcion objetivo: suma de distancias
     */
    public int funcionObjetivo(int[] solucion){
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

        int i = 0;

        long time_start = System.currentTimeMillis();
        while(i < numeroIteraciones){// iterar segun parametro - condicion de parada
            solucionActual = this.obtenerMejoresVecinos(listaTabu, this.distancias, solucionActual);

            int costoActual = this.funcionObjetivo(solucionActual);

            if (costoActual < mejorCosto) {
                System.arraycopy(solucionActual, 0, mejorSol, 0, mejorSol.length);
                mejorCosto = costoActual;
            }
            i++;
        }
        this.duracion = ( System.currentTimeMillis() - time_start );

        return mejorSol;
    }

    private int[] obtenerMejoresVecinos(ListaTabu listaTabu,
                                        int[][] distancias,
                                        int[] initSolution) {

        int[] mejorSol = new int[initSolution.length]; //la mejor solucion local
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

                mejorSolActual = this.intercambiarNodos(i, j, initSolution); //Intercambiar nodos i y j
                // calcular el nuevo mejor costo
                int mejorCostoActual = this.funcionObjetivo(mejorSolActual);

                if ((mejorCostoActual < mejorCosto || primerVecino) && !listaTabu.contieneMovimiento(i,j)) { //si se encontro un mejor movimiento, guardar
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
    private int[] intercambiarNodos(int nodo1, int nodo2, int[] solucion) {
        int[] sol_aux = new int[solucion.length];
        System.arraycopy(solucion,0,sol_aux,0,solucion.length);

        int temp = sol_aux[nodo1];
        sol_aux[nodo1] = sol_aux[nodo2];
        sol_aux[nodo2] = temp;
        return sol_aux;
    }

    public double getDuracion(){
        return this.duracion;
    }
}

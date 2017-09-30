package Tabu;

import java.awt.*;

public class Tabu {

    private EntornoTabu entorno;
    private int[] caminoInicial;

    public Tabu(){
        this.entorno = new EntornoTabu();
        //prueba
        entorno.distancias = //Distance matrix, 5x5, used to represent distances
                new int[][]{{0, 1, 3, 4, 5},
                        {1, 0, 1, 4, 8},
                        {3, 1, 0, 5, 1},
                        {4, 4, 5, 0, 2},
                        {5, 8, 1, 2, 0}};


        this.caminoInicial = new int[]{0, 1, 2, 3, 4, 0};

    }

    public int[] generarCamino(int listaTabuTamahno, int listaTabuPermanencia){
        int[] solucionActual =  new int[this.caminoInicial.length];  //inicializar solucion
        System.arraycopy(this.caminoInicial, 0, solucionActual, 0, this.caminoInicial.length);

        int numeroIteraciones = 100;

        ListaTabu listaTabu = new ListaTabu(listaTabuTamahno, listaTabuPermanencia);

        int[] mejorSol = new int[solucionActual.length]; //la mejor solucion hasta el momento
        System.arraycopy(solucionActual, 0, mejorSol, 0, mejorSol.length);
        int mejorCosto = this.entorno.funcionObjetivo(mejorSol);

        for (int i = 0; i < numeroIteraciones; i++) { // iterar segun parametro

            solucionActual = Tabu.obtenerMejoresVecinos(listaTabu, this.entorno, solucionActual);

            int costoActual = this.entorno.funcionObjetivo(solucionActual);

            //System.out.println("Current best cost = " + tspEnvironment.getObjectiveFunctionValue(currSolution));
            if (costoActual < mejorCosto) {
                System.arraycopy(solucionActual, 0, mejorSol, 0, mejorSol.length);
                mejorCosto = costoActual;
            }
        }

        return mejorSol;
    }

    private static int[] obtenerMejoresVecinos(ListaTabu listaTabu,
                                         EntornoTabu entorno,
                                         int[] initSolution) {

        int[] mejorSol = new int[initSolution.length]; //this is the best Solution So Far
        System.arraycopy(initSolution, 0, mejorSol, 0, mejorSol.length);
        int mejorCosto = entorno.funcionObjetivo(initSolution);
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
                int mejorCostoActual = entorno.funcionObjetivo(mejorSolActual);

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

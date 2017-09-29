package Tabu;

import java.awt.*;

public class Tabu {

    EntornoTabu entorno;

    public Tabu(){
        this.entorno = new EntornoTabu();

        entorno.distancias = //Distance matrix, 5x5, used to represent distances
                new int[][]{{0, 1, 3, 4, 5},
                        {1, 0, 1, 4, 8},
                        {3, 1, 0, 5, 1},
                        {4, 4, 5, 0, 2},
                        {5, 8, 1, 2, 0}};
        //Between cities. 0,1 represents distance between cities 0 and 1, and so on.

        int[] currSolution = new int[]{0, 1, 2, 3, 4, 0};   //initial solution
        //city numbers start from 0
        // the first and last cities' positions do not change

        int numberOfIterations = 100;
        int tabuLength = 10;
        ListaTabu tabuList = new ListaTabu(tabuLength);

        int[] bestSol = new int[currSolution.length]; //this is the best Solution So Far
        System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = this.entorno.funcionObjetivo(bestSol);

        for (int i = 0; i < numberOfIterations; i++) { // perform iterations here

            currSolution = Tabu.getBestNeighbour(tabuList, this.entorno, currSolution);
            //printSolution(currSolution);
            int currCost = this.entorno.funcionObjetivo(currSolution);

            //System.out.println("Current best cost = " + tspEnvironment.getObjectiveFunctionValue(currSolution));

            if (currCost < bestCost) {
                System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
                bestCost = currCost;
            }
        }

    }

    public static int[] getBestNeighbour(ListaTabu tabuList,
                                         EntornoTabu tspEnviromnet,
                                         int[] initSolution) {

        int[] bestSol = new int[initSolution.length]; //this is the best Solution So Far
        System.arraycopy(initSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = tspEnviromnet.funcionObjetivo(initSolution);
        int city1 = 0;
        int city2 = 0;
        boolean firstNeighbor = true;

        for (int i = 1; i < bestSol.length - 1; i++) {
            for (int j = 2; j < bestSol.length - 1; j++) {
                if (i == j) {
                    continue;
                }

                int[] newBestSol = new int[bestSol.length]; //this is the best Solution So Far
                System.arraycopy(bestSol, 0, newBestSol, 0, newBestSol.length);

                newBestSol = intercambiarNodos(i, j, initSolution); //Try swapping cities i and j
                // , maybe we get a bettersolution
                int newBestCost = tspEnviromnet.funcionObjetivo(newBestSol);

                if ((newBestCost > bestCost || firstNeighbor) && tabuList.listaTabu[i][j] == 0) { //if better move found, store it
                    firstNeighbor = false;
                    city1 = i;
                    city2 = j;
                    System.arraycopy(newBestSol, 0, bestSol, 0, newBestSol.length);
                    bestCost = newBestCost;
                }


            }
        }

        if (city1 != 0) {
            tabuList.decrementarListaTabu();
            tabuList.agregarNodo(city1, city2);
        }
        return bestSol;


    }

    //swaps two cities
    public static int[] intercambiarNodos(int nodo1, int nodo2, int[] solucion) {
        int temp = solucion[nodo1];
        solucion[nodo1] = solucion[nodo2];
        solucion[nodo2] = temp;
        return solucion;
    }
}

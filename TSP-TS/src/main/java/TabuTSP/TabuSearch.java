package TabuTSP;

import java.util.List;

public class TabuSearch {

    public static int [] obtenerMejorVecino(ListaTabu listaTabu,
                                                EntornoTSP entornoTSP,int [] solucionInicial){
    int [] mejorSol =new int[solucionInicial.length];
    System.arraycopy(solucionInicial,0,mejorSol,0,mejorSol.length);
    int mejorCosto = entornoTSP.obtenerFuncionObjetivo(solucionInicial);
    int item1 = 0;
    int item2 = 0;

    boolean primerVecino =true;

        for (int i = 0; i < mejorSol.length-1; i++) {
            for (int j = 0; j < mejorSol.length-1; j++) {
                if (i == j) {
                    continue;
                }
                int [] nuevaMejorSol = new int [mejorSol.length];
                System.arraycopy(mejorSol,0,nuevaMejorSol,0,nuevaMejorSol.length);
                nuevaMejorSol = intercambiar(i,j,solucionInicial);
                int nuevoMejorCosto = entornoTSP.obtenerFuncionObjetivo(nuevaMejorSol);
                if ((nuevoMejorCosto>mejorCosto || primerVecino) && listaTabu.listaTabu[i][j]==0) {
                    primerVecino = false;
                    item1 = i;
                    item2 = j;
                    System.arraycopy(nuevoMejorCosto,0,mejorSol,0,nuevaMejorSol.length);
                    mejorCosto=nuevoMejorCosto;
                    
                }
            }
        }

        if (item1 !=0){
            listaTabu.decremtoTabu();
            listaTabu.movimientoTabu(item1,item2);
        }
        return mejorSol;
    }

    public static int [] intercambiar(int item1,int item2,int[] solucion){
        int tmp = solucion[item1];
        solucion[item1] = solucion[item2];
        solucion[item2] = tmp;
        return solucion;
    }


    public static void main (String[] args){
        EntornoTSP entornoTSP = new EntornoTSP();
        entornoTSP.distancias=new int[][]{{0, 1, 3, 4, 5},
            {1, 0, 1, 4, 8},
            {3, 1, 0, 5, 1},
            {4, 4, 5, 0, 2},
            {5, 8, 1, 2, 0}};

        int[] solucionActual = new int []{1,2,3,4,5,6,7,8};

        int numIteraciones = 100;
        int longTabu = 10;
        ListaTabu longitudTabu = new ListaTabu(longTabu);





    }
}

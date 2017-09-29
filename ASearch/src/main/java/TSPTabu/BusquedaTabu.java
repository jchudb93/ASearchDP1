package TSPTabu;

public class BusquedaTabu {
    private ListaTabu listaTabu;
    private final Matriz matriz;

    int [] solActual;
    int numIteraciones;
    int tamanioProblema;

    private int[] mejorSol;
    private int mejorCosto;

    public BusquedaTabu(Matriz matriz){
        this.matriz = matriz;
        tamanioProblema = matriz.getnAristas();
        numIteraciones = tamanioProblema * 10;
        listaTabu = new ListaTabu(tamanioProblema);
        obtenerSolActual();
        obtenerMejorSol();
    }

    private void obtenerSolActual(){
        solActual = new int[tamanioProblema+1];
        for (int i = 0; i <tamanioProblema; i++) {
            solActual [i] = i;
        }
        solActual[tamanioProblema] =0;
    }

    private void obtenerMejorSol(){
        mejorSol = new int [tamanioProblema+1];
        System.arraycopy(solActual,0,mejorSol,0, mejorSol.length);
        mejorCosto = matriz.calcularDistancia(mejorSol);
    }

    private void imprimirSolucion(int[] solucion){
        System.out.println("Solucion actual");
        for (int i = 0; i < solucion.length; i++) {
            System.out.print(solucion[i] + " ");
        }
        System.out.println();
    }
    public void solucionar(){
        for (int i = 0; i < numIteraciones; i++) {
            int item1 =0 ;
            int item2 =0;

            for (int j = 1; j <solActual.length-1 ; j++) {
                for (int k = 2; k < solActual.length-2; k++) {
                    if (j != k) {
                        swap(j, k);
                        int costoActual = matriz.calcularDistancia(solActual);
                        if ((costoActual < mejorCosto) && listaTabu.listaTabu[j][k] == 0) {
                            item1 = j;
                            item2 = k;
                            System.arraycopy(solActual, 0, mejorSol, 0, mejorSol.length);
                            mejorCosto = costoActual;
                        }
                    }
                }
            }

            if(item1 !=0){
                listaTabu.decrementarTabu();
                listaTabu.mover(item1,item2);
            }
        }
        imprimirSolucion(mejorSol);
    }
    private void swap(int i, int j){
        int temp = solActual[i];
        solActual[i] = solActual[j];
        solActual[j] = temp;
    }
}

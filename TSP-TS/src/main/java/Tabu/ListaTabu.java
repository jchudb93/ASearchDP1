package Tabu;

public class ListaTabu {

    int [][] listaTabu ;
    public ListaTabu (int numNodos){
        listaTabu = new int[numNodos][numNodos];
    }

    public void agregarNodo(int nodo1, int nodo2){
        listaTabu[nodo1][nodo2]+= 5;
        listaTabu[nodo2][nodo1]+= 5;

    }

    public void decrementarListaTabu(){
        for(int i = 0; i<listaTabu.length; i++){
            for(int j = 0; j<listaTabu.length; j++){
                listaTabu[i][j]-=listaTabu[i][j]<=0?0:1;
            }
        }
    }

}

package Tabu;

import java.util.ArrayList;

public class ListaTabu {

    //public int [][] listaTabu ;

    public ArrayList<TabuMovimiento> listaTabu;
    private int permanencia;
    private int tamanho;

    public ListaTabu (int tamanho, int permanencia){
        //this.listaTabu = new int[numNodos][numNodos];

        this.listaTabu = new ArrayList<TabuMovimiento>();
        this.tamanho = tamanho;
        this.permanencia = permanencia;
    }

    public boolean contieneMovimiento(int nodo1, int nodo2){

        for(TabuMovimiento mov: this.listaTabu){
            if(mov.igualA(nodo1, nodo2)){
                return true;
            }
        }

        return false;
    }

    public void agregarNodo(int nodo1, int nodo2){
        /*
        listaTabu[nodo1][nodo2]+= 5;
        listaTabu[nodo2][nodo1]+= 5;
        */

        listaTabu.add(new TabuMovimiento(nodo1, nodo2, this.permanencia));
        if(listaTabu.size() > this.tamanho){
            listaTabu.remove(0);
        }
    }

    public void decrementarListaTabu(){
        /*
        for(int i = 0; i<listaTabu.length; i++){
            for(int j = 0; j<listaTabu.length; j++){
                listaTabu[i][j]-=listaTabu[i][j]<=0?0:1;
            }
        }
        */
        ArrayList<TabuMovimiento> nuevaLista = new ArrayList<TabuMovimiento>();
        for(TabuMovimiento mov: this.listaTabu){
            mov.tiempo -= 1;
            if(mov.tiempo != 0){
                nuevaLista.add(mov);
            }
        }
        this.listaTabu = nuevaLista;
    }


    private class TabuMovimiento {
        public int nodo1;
        public int nodo2;
        public int tiempo;

        public TabuMovimiento(int a, int b, int tiempo){
            if (a < b){
                this.nodo1 = a;
                this.nodo2 = b;
            } else {
                this.nodo1 = b;
                this.nodo2 = a;
            }
            this.tiempo = tiempo;
        }

        public boolean igualA(int a, int b){
            if (a < b){
                return (this.nodo1 == a && this.nodo2 == b);
            } else {
                return (this.nodo1 == b && this.nodo2 == a);
            }

        }
    }

}

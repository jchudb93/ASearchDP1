package TabuTSP;

public class ListaTabu {

    int [][] listaTabu;

    public ListaTabu() {
    }

    public ListaTabu(int numItems) {
        this.listaTabu = new int [numItems][numItems];
    }

    public void movimientoTabu(int item1,int item2){
        listaTabu[item1][item2]+=5;
        listaTabu[item2][item1]+=5;
    }

    public void decremtoTabu(){
        for (int i = 0; i < listaTabu.length; i++) {
            for (int j = 0; j < listaTabu.length; j++) {
                listaTabu[i][j] -= listaTabu[i][j]<=0?0:1;

            }
        }
    }
}

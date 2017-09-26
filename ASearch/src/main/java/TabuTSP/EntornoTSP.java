package TabuTSP;

public class EntornoTSP {
    public int [][] distancias;

    public EntornoTSP() {
    }

    public int obtenerFuncionObjetivo(int solucion[]){
        int costo =0;
        for (int i = 0; i < solucion.length; i++) {
            costo+=distancias[solucion[i]][solucion[i+1]];
        }
        return costo;
    }
}

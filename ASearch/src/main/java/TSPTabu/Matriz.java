package TSPTabu;

import java.util.Random;

public class Matriz {
    private int [][] matriz;
    private int nAristas;

    public Matriz(int nAristas) {
        this.matriz = new int[nAristas][nAristas];
        this.nAristas = nAristas;
        generarMatriz(nAristas);
    }

    public Matriz(int[][] matriz) {
        this.matriz = matriz;
        nAristas = matriz.length;
    }

    public int getnAristas() {
        return nAristas;
    }

    public  int obtenerPesos(int desde, int hasta){
        return matriz[desde][hasta];
    }
    public int obtenerTamanio(){
        return  nAristas;
    }

    public void imprimirMatriz(){
        for (int i = 0; i < obtenerTamanio(); i++) {
            for (int j = 0; j < obtenerTamanio(); j++) {
                System.out.println(matriz[i][j] + " ");

            }
            System.out.println("\n");
        }
    }

    public int calcularDistancia(int solucion[]){
        int costo = 0;
        for (int i = 0; i < solucion.length-1 ; i++) {
            costo += matriz[solucion[i]][solucion[i+1]];
        }
        return costo;
    }

    private void generarMatriz(int nAristas){
        Random random = new Random();
        for (int i = 0; i < nAristas; i++) {
            for (int j = 0; j < nAristas; j++) {
                if(i!=j){
                    int valor = random.nextInt(100)+1;
                    matriz[i][j] = valor;
                    matriz[i][j] = valor;
                }
            }
        }
    }
}

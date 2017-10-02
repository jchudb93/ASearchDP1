import Controllers.GestorAlmacen;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Tabu.*;

public class Main {

    public static void main(String[] args) {

        /*
        Almacen alm = new Almacen(20,20);

        GestorAlmacen.generarRacksAletorios(alm);
        GestorAlmacen.generarNodos(alm);

        GestorAlmacen.imprimirAlmacen(alm, "almacen.txt");
        GestorAlmacen.imprimirNodos(alm, "nodos.txt");
        alm.imprimirRacks();
        */


        Tabu tabu = new Tabu();
        int[] solucion = tabu.generarCamino(100, 10,10);


        imprimirArrayInt(solucion);


        int[][] distancias = {
        {   0,     2,   9999,	9999,	9999,	9999,	9999,	9999,	9999,	   5,	9999,	9999,	9999,	9999,	9999,	9999,	9999},
        {   2,     0,      5,      1,   9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999},
        {9999,	   5,	   0,	9999,	   1,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999},
        {9999,	   1,   9999,	   0,      5,      2,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999},
        {9999,	9999,	   1,	   5,      0,	9999,	9999,	9999,	   2,   9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999},
        {9999,	9999,   9999,	   2,   9999,	   0,      1,	9999,	9999,	9999,	   2,   9999,	9999,	9999,	9999,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	   1,      0,	   2,   9999,	9999,	9999,	   2,	9999,	9999,	9999,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	   2,	   0,      2,	9999,	9999,	9999,	9999,	9999,	9999,	   5,	9999},
        {9999,	9999,	9999,	9999,	   2,   9999,	9999,	   2,      0,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   5},
        {   5,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   0,      2,	9999,	   3,	9999,	9999,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	   2,   9999,	9999,	9999,	   2,      0,	   1,	9999,	   3,	9999,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	   2,   9999,	9999,	9999,	   1,      0,	9999,	9999,	   3,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   3,   9999,	9999,	   0,	   2,	9999,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   3,   9999,	   2,	   0,	   1,	9999,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   3,	9999,	   1,	   0,	   2,	9999},
        {9999,	9999,	9999,	9999,	9999,	9999,	9999,	   5,   9999,	9999,	9999,	9999,	9999,	9999,	   2,	   0,	   2},
        {9999,	9999,	9999,	9999,	9999,	9999,	9999,	9999,	   5,   9999,	9999,	9999,	9999,	9999,	9999,	   2,	   0}};

        int[] caminoInicial = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,0};

        Tabu tabu2 = new Tabu(distancias, caminoInicial);
        int[] sol2 = tabu2.generarCamino(1000000, 5, 5);
        int val = tabu2.funcionObjetivo(sol2);

        System.out.println();
        imprimirArrayInt(sol2);
        int[] x = new int[]{1,2,3,4,5,6};
        int[] y = swap(2,4, x);
        //imprimirArrayInt(x);
        System.out.println();
        //imprimirArrayInt(y);
        //System.out.println(val);

    }

    private static void imprimirArrayInt(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(String.valueOf(arr[i]) + " ");
        }
    }

    private static int[] swap(int x, int x2, int[] arr){
        int tmp = arr[x];
        arr[x] = arr[x2];
        arr[x2] = tmp;
        return arr;
    }


}

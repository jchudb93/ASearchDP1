import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Tabu.*;

public class Main {

    public static void main(String[] args) {


        Almacen alm = new Almacen(10,10);

        GestorAlmacen.generarRacksAletorios(alm);
        GestorAlmacen.generarNodos(alm);

        GestorAlmacen.imprimirAlmacen(alm, "almacen.txt");
        GestorAlmacen.imprimirNodos(alm, "nodos.txt");
        alm.imprimirRacks();

        GestorDistancias dist = new GestorDistancias(alm);
        dist.calcularDistancias();

        //Tabu tabu = new Tabu();
        //int[] solucion = tabu.generarCamino(100, 10,10);
        //imprimirArrayInt(solucion);


        int[][] distancias = dist.getMatrizDistancia();
        int[] caminoInicial = new int[dist.obtenerNumeroNodos() + 1];
        for(int i = 0; i < dist.obtenerNumeroNodos(); i++){
            caminoInicial[i] = i;
        }
        caminoInicial[dist.obtenerNumeroNodos()] = 0;

        Tabu tabu2 = new Tabu(distancias, caminoInicial);
        int[] sol2 = tabu2.generarCamino(1000000, 5, 5);

        System.out.println();
        imprimirArrayInt(sol2);
        System.out.println();

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

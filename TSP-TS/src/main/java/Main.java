import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorImpresion;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Tabu.*;

public class Main {

    public static void main(String[] args) {


        Almacen alm = new Almacen(10,10);

        GestorAlmacen.generarRacksAletorios(alm);
        GestorAlmacen.generarNodos(alm);
        //GestorAlmacen.imprimirAlmacen(alm, "almacen.txt");
        //GestorAlmacen.imprimirNodos(alm, "nodos.txt");

        GestorDistancias dist = new GestorDistancias(alm);
        dist.calcularDistancias();
        //dist.imprimirMatriz();

        int[][] distancias = dist.getMatrizDistancia();
        int[] caminoInicial = new int[dist.obtenerNumeroNodos() + 1];
        for(int i = 0; i < dist.obtenerNumeroNodos(); i++){
            caminoInicial[i] = i;
        }
        caminoInicial[dist.obtenerNumeroNodos()] = 0;

        Tabu tabu = new Tabu(distancias, caminoInicial);
        //Tabu tabu = new Tabu();
        int[] sol2 = tabu.generarCamino(100000, 20, 20);
        int val = tabu.funcionObjetivo(sol2);
        System.out.println();
        imprimirArrayInt(sol2);
        System.out.println();
        System.out.println();
        System.out.println("Duracion: ");
        System.out.println(tabu.getDuracion());
        System.out.println();
        System.out.println();
        System.out.println("Costo del camino:");
        System.out.println(val);
        System.out.println();
        System.out.println("Camino: ");
        System.out.println();
        GestorImpresion.imprimirAlmancen(alm,dist.getPosNodos(),sol2);

    }

    private static void imprimirArrayInt(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(String.valueOf(arr[i]) + " ");
        }
    }

}

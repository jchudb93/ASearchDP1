import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorImpresion;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Tabu.*;
import Utils.NearestInsertion;
import Utils.NearestNeighbor;

public class Main {

    public static void main(String[] args) {


        Almacen alm = new Almacen(25,25);

        GestorAlmacen.generarRacksAletorios(alm);
        GestorAlmacen.escogerPuntoPartida(alm, 0,0);
        GestorAlmacen.generarNodos(alm);


        GestorDistancias dist = new GestorDistancias(alm);
        dist.calcularDistancias();

        int[][] distancias = dist.getMatrizDistancia();
        int puntoPartida = dist.obtenerNodoId(0,0);

        /*
        NearestInsertion nearestInsertion = new NearestInsertion(distancias, puntoPartida);
        nearestInsertion.generar();
        System.out.println("Nearest Insertion Costo:");
        System.out.println(nearestInsertion.obtenerCosto());
        System.out.println("Nearest Insertion Duracion:");
        System.out.println(nearestInsertion.obtenerDuracion());
        */
        NearestNeighbor nearestNeighbor = new NearestNeighbor(distancias, puntoPartida);
        nearestNeighbor.generar();
        System.out.println("Nearest Neighbor Costo:");
        System.out.println(nearestNeighbor.obtenerCosto());
        System.out.println("Nearest Neighbor Duracion:");
        System.out.println(nearestNeighbor.obtenerDuracion());

        int[] caminoInicial = nearestNeighbor.obtenerSolucion();


        Tabu tabu = new Tabu(distancias, caminoInicial);
        //Tabu tabu = new Tabu();
    int[] sol2 = tabu.generarCamino(1000, 50, 50);
        int val = tabu.funcionObjetivo(sol2);
        System.out.println();
        imprimirArrayInt(sol2);
        System.out.println();
        System.out.println("Duracion: ");
        System.out.println(tabu.getDuracion());
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

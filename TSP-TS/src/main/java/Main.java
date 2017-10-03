import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorImpresion;
import Controllers.GestorProducto;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Models.Producto;
import Tabu.*;
import Utils.NearestInsertion;
import Utils.NearestNeighbor;
import Utils.NearestNeighborNoCyclic;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        Almacen alm = new Almacen(100,100);
        Point puntoInicio = new Point(0,0);
        GestorAlmacen.generarRacksAletorios(alm);
        ArrayList<Producto> productos = GestorProducto.generarProductos(alm, 50, puntoInicio);
        GestorAlmacen.llenarConProdYPtoPartida(alm, productos);
        //GestorAlmacen.guardarAlmacenXML(alm, "almacen.xml");
        //GestorAlmacen.escogerPuntoPartida(alm, 0,0);
        GestorAlmacen.generarNodos(alm);


        GestorDistancias dist = new GestorDistancias(alm);
        dist.calcularDistancias();
        int[][] distanciasVert = dist.getMatrizDistancia();
        //int puntoPartida = dist.obtenerNodoId(puntoInicio.x,puntoInicio.y);



        //int[][] distancias = GestorProducto.obtenerMatrizDistancia(productos);
        int[] prodEnMatrizDistancia = dist.obtenerProdsId(productos);
        NearestNeighborNoCyclic nnnc = new NearestNeighborNoCyclic(distanciasVert, prodEnMatrizDistancia);
        nnnc.generar();
        int[][] matDistancias = nnnc.obtenerMatrizNodos();
        int[][] distancias = dist.obtenerDistanciasProductos(prodEnMatrizDistancia, matDistancias, productos);

        NearestNeighbor nearestNeighbor = new NearestNeighbor(distancias, 0);
        nearestNeighbor.generar();
        System.out.println("Nearest Neighbor Costo:");
        System.out.println(nearestNeighbor.obtenerCosto());
        System.out.println("Nearest Neighbor Duracion:");
        System.out.println(nearestNeighbor.obtenerDuracion());


        int[] caminoInicial = nearestNeighbor.obtenerSolucion();

        Tabu tabu = new Tabu(distancias, caminoInicial);
        int[] solucion = tabu.generarCamino(100000, 100000, 5, 5);
        int val = tabu.funcionObjetivo(solucion);
        System.out.println("===========================");
        System.out.println("T A B U");
        System.out.println("Duracion: ");
        System.out.println(tabu.getDuracion());
        System.out.println("Costo del camino:");
        System.out.println(val);
        System.out.println("Numero de iteraciones final:");
        System.out.println(tabu.getIteracion());
        System.out.println("Camino Inicial: ");
        imprimirArrayInt(solucion);
        System.out.println("Solucion: ");
        imprimirArrayInt(caminoInicial);

        System.out.println();
        GestorProducto.imprimirCaminoProductos(productos, solucion);
    }

    private static void imprimirArrayInt(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(String.valueOf(arr[i]) + " ");
        }
        System.out.println();
    }

}

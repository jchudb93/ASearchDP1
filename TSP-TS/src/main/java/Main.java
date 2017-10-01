import Models.Almacen;

import Models.GeneradorAlmacen;

public class Main {

    public static void main(String[] args) {

        Almacen alm = new Almacen(20,20);

        alm.genRandom();

        alm.imprimirMatriz(alm.getAlmacen(), alm.getAncho(), alm.getAlto(), "almacen.txt");



        alm.imprimirMatriz(alm.obtenerNodos(), alm.getAncho(), alm.getAlto(), "nodos.txt");

        alm.imprimirRacks();
    }


}

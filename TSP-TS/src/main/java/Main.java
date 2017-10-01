import Controllers.GestorAlmacen;
import Models.Almacen;

import Models.GeneradorAlmacen;

public class Main {

    public static void main(String[] args) {

        Almacen alm = new Almacen(20,20);

        GestorAlmacen.generarRacksAletorios(alm);
        GestorAlmacen.generarNodos(alm);

        GestorAlmacen.imprimirAlmacen(alm, "almacen.txt");

        GestorAlmacen.imprimirNodos(alm, "nodos.txt");

        alm.imprimirRacks();
    }


}

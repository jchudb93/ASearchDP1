import Models.Almacen;

public class Main {

    public static void main(String[] args) {
        Almacen alm = new Almacen(20,20);

        //boolean x = alm.unidad[1][1];
        //System.out.println(x);
        alm.genRandom();

        alm.imprimirMatriz(alm.almacen, alm.ancho, alm.alto, "almacen.txt");

        boolean[][] nodos = alm.obtenerNodos();

        alm.imprimirMatriz(nodos, alm.ancho, alm.alto, "nodos.txt");

    }


}

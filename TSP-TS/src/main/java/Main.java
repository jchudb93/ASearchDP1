import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorImpresion;
import Controllers.GestorProducto;
import Models.Almacen;
import Models.GeneradorAlmacen;
import Models.Producto;
import Tabu.*;
import Utils.ExpNumerica;
import Utils.NearestInsertion;
import Utils.NearestNeighbor;
import Utils.NearestNeighborNoCyclic;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ExpNumerica expNumerica = new ExpNumerica(1, 40, 50);

        expNumerica.generarRandom("almacen.xml", "productos.xml");
    }

}

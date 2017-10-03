package Utils;

import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorProducto;
import Models.Almacen;
import Models.Producto;
import Tabu.Tabu;
import Recocido.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ExpNumerica {

    private static Random random = new Random();

    private int numMuestras;
    private int productosMin;
    private int productosMax;

    private ArrayList<Resultado> resultados;

    private class Resultado{
        public long tiempoTabu;
        public long iteracionesTabu;
        public int costoTabu;


        public long tiempoRecocido;
        public long iteracionesRecocido;
        public int costoRecocido;

        public Resultado(){
        }

        public void imprimirResultado(){

        }
    }

    public  ExpNumerica(int numMuestras, int productosMin, int productosMax){
        this.numMuestras = numMuestras;
        this.productosMin = productosMin;
        this.productosMax = productosMax;

        this.resultados = new ArrayList<Resultado>();
    }

    public void generarRandom(String archivoAlm, String archivoProd){
        //Generar almacen rack
        Almacen alm = new Almacen(100,100);
        Point puntoInicio = new Point(0,0);

        GestorAlmacen.generarRacksAletorios(alm);

        for(int i = 0; i < this.numMuestras; i++){
            int numProductos = random.nextInt(this.productosMax - this.productosMin) + this.productosMin;

            ArrayList<Producto> productos = GestorProducto.generarProductos(alm, numProductos, puntoInicio);

            GestorAlmacen.llenarConProdYPtoPartida(alm, productos);
            //GestorAlmacen.guardarAlmacenXML(alm, "almacen.xml");
            //GestorAlmacen.escogerPuntoPartida(alm, 0,0);
            GestorAlmacen.generarNodos(alm);

            GestorDistancias dist = new GestorDistancias(alm);
            dist.calcularDistancias();
            int[][] distanciasVert = dist.getMatrizDistancia();
            //int puntoPartida = dist.obtenerNodoId(puntoInicio.x,puntoInicio.y);

            //Obtener matriz de distancia de los productos
            int[] prodEnMatrizDistancia = dist.obtenerProdsId(productos);
            NearestNeighborNoCyclic nnnc = new NearestNeighborNoCyclic(distanciasVert, prodEnMatrizDistancia);
            nnnc.generar();
            int[][] matDistancias = nnnc.obtenerMatrizNodos();
            int[][] distancias = dist.obtenerDistanciasProductos(prodEnMatrizDistancia, matDistancias, productos);

            //Solucion inicial
            NearestNeighbor nn = new NearestNeighbor(distancias, 0);
            nn.generar();
            System.out.println(nn.obtenerCosto());
            System.out.println(nn.obtenerDuracion());
            int[] caminoInicial = nn.obtenerSolucion();
            
            //Busqueda Tabu
            Tabu tabu = new Tabu(distancias, caminoInicial);
            int[] solucion = tabu.generarCamino(100000, 100000, 5, 5);


            Recocido recocido = new Recocido(distancias, caminoInicial);
            recocido.setNumeroIteraciones(100000);
            int[] solucionRecocido = recocido.generarCamino();

            //Guardar soluciones
            Resultado resultado = new Resultado();

            resultado.costoTabu = tabu.funcionObjetivo(solucion);
            resultado.tiempoTabu = tabu.getDuracion();
            resultado.iteracionesTabu = tabu.getIteracion();

            resultado.costoRecocido = recocido.getFuncionObjetivo();
            resultado.tiempoRecocido = recocido.getDuracion();
            resultado.iteracionesRecocido = recocido.getNumeroIteraciones();

            this.resultados.add(resultado);
        }
    }

    public void generarConDatosCargados(String archivoAlm, String archivoProd){

    }

    private void imprimirArrayInt(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(String.valueOf(arr[i]) + " ");
        }
        System.out.println();
    }
}

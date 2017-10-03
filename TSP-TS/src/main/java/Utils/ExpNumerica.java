package Utils;

import Controllers.GestorAlmacen;
import Controllers.GestorDistancias;
import Controllers.GestorProducto;
import Models.Almacen;
import Models.Producto;
import Models.Rack;
import Tabu.Tabu;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
        GestorAlmacen.generarRacksAletorios(alm);
        Point puntoInicio = new Point(0,0);

        for(int i = 0; i < this.numMuestras; i++){
            alm.limpiarNodos();
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
            int[] caminoInicial = nn.obtenerSolucion();
            
            //Busqueda Tabu
            Tabu tabu = new Tabu(distancias, caminoInicial);
            int[] solucion = tabu.generarCamino(1000, 1000, 5, 5);

            //Guardar soluciones
            Resultado resultado = new Resultado();

            resultado.costoTabu = tabu.funcionObjetivo(solucion);
            resultado.tiempoTabu = tabu.getDuracion();
            resultado.iteracionesTabu = tabu.getIteracion();

            this.resultados.add(resultado);
        }
        this.guardarResultados("resultados.csv");
    }

    public void generarConDatosCargados(String archivoAlm, String archivoProd){

    }

    private void imprimirArrayInt(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(String.valueOf(arr[i]) + " ");
        }
        System.out.println();
    }

    private void guardarResultados(String nombre_archivo){
        try{
            PrintStream out = new PrintStream(new FileOutputStream(nombre_archivo));
            System.setOut(out);
            out.println("tabu_iteraciones, tabu_costo, tabu_tiempo, " +
                        "recocido_iteraciones, recocido_costo, recocido_tiempo");
            for (Resultado res: this.resultados) {
                out.print(res.iteracionesTabu + ", " + res.costoTabu + ", " + res.tiempoTabu + ", " +
                        res.iteracionesRecocido + ", " + res.costoRecocido + ", " + res.tiempoRecocido);
                out.println();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

package Models;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Almacen {

    private boolean almacen[][];
    private int ancho;
    private int alto;
    private ArrayList<Rack> racks;

    private static Random random = new Random();
    private static int MIN_LARGO = 3;
    private static int MAX_LARGO = 5;


    private enum DIR {
        UP(1,0), RIGTH(0,1), DOWN(-1,0), LEFT(0,-1);

        private final int dx;
        private final int dy;

        DIR(int dx, int dy){
            this.dx = dx;
            this.dy = dy;
        }
        private int getDx(){
            return this.dx;
        }
        private int getDy(){
            return this.dy;
        }
    }

    public Almacen(int ancho, int alto){

        this.ancho = ancho;
        this.alto = alto;
        
        this.almacen = new boolean[ancho][alto];
        this.racks = new ArrayList<Rack>();
    }

    /**
     *  Genera un almacen aleatorio de racks
     *
     */
    public void genRandom(){
        for(int i = 1; i < this.ancho - 2; i++){
            for(int j = 1; j < this.alto - 2; j++){
                int largo = (int) (random.nextDouble()*(MAX_LARGO - MIN_LARGO) + MIN_LARGO);
                DIR dir = DIR.values()[random.nextInt(DIR.values().length)];
                Rack rack = crearRackPosible(i,j,largo,dir);
                if(rack != null){
                    this.racks.add(rack);
                }
            }
        }
    }

    /**
     * Crear un rack si fuese posible
     */
    private Rack crearRackPosible(int iniX, int iniY, int largo, DIR dir){
        if(dir.getDx() == 0){ //izquierda o derecha
            int finY = iniY + largo*dir.getDy();
            if(finY<=0) finY = 1;
            if(finY>=this.alto -1) finY = this.alto - 2;

            if (Math.abs(iniY - finY) < MIN_LARGO) return null;
            int iniY_aux = iniY;
            int finY_aux = finY;
            //verificar
            boolean crear = true;
            finY += 2*dir.getDy();
            iniY -= 2*dir.getDy();
            while(iniY != finY){
                if(iniY < 0 || iniY >= this.ancho){
                    iniY += dir.getDy();
                    continue;
                }
                //verifia extremos vacios
                if (this.almacen[iniX][iniY] || this.almacen[iniX-1][iniY] || this.almacen[iniX+1][iniY]){
                    crear = false;
                    break;
                }
                iniY += dir.getDy();
            }
            //crear rack si fuese posible
            iniY = iniY_aux;
            finY = finY_aux;
            if(crear){
                Point ini = new Point(iniX, iniY);
                Point fin = new Point(iniX, finY);
                while(iniY != finY){
                    this.almacen[iniX][iniY] = true;
                    iniY += dir.getDy();
                }
                return new Rack(ini, fin);
            }
        } else { //arriba o abajo
            int finX = iniX + largo*dir.getDx();
            if(finX <= 0) finX = 1;
            if(finX >= this.ancho - 1) finX = this.ancho - 2;
            if (Math.abs(iniX - finX) < MIN_LARGO) return null;
            int iniX_aux = iniX;
            int finX_aux = finX;
            //verificar
            boolean crear = true;
            finX += 2*dir.getDx();
            iniX -= 2*dir.getDx();
            while(iniX != finX){
                if(iniX < 0 || iniX >= this.ancho){
                    iniX += dir.getDx();
                    continue;
                }
                //verifia extremos vacios
                if (this.almacen[iniX][iniY] || this.almacen[iniX][iniY-1] || this.almacen[iniX][iniY+1]){
                    crear = false;
                    break;
                }
                iniX += dir.getDx();
            }
            //crear rack si fuese posible
            iniX = iniX_aux;
            finX = finX_aux;
            if(crear){
                Point ini = new Point(iniX, iniY);
                Point fin = new Point(finX, iniY);
                while(iniX != finX){
                    this.almacen[iniX][iniY] = true;
                    iniX += dir.getDx();
                }
                return new Rack(ini, fin);
            }
        }
        return null;
    }


    /**
     * Obtener una matriz de los nodos más importantes a partir de la matriz de almacen
     */
    public boolean[][] obtenerNodos(){

        boolean [][] nodos = new boolean[this.ancho][this.alto];
        //inicializar nodos (false)
        for(int i = 0; i < this.ancho; i++){
            for(int j = 0; j < this.alto; j++){
                nodos[i][j] = false;
            }
        }

        //obtener vertices
        for(int i = 1; i < this.ancho - 1; i++){
            for(int j = 1; j < this.alto - 1; j++){
                //si es un bloque del almacen
                if(this.almacen[i][j]){
                    boolean arriba = this.almacen[i+1][j];
                    boolean abajo = this.almacen[i-1][j];
                    //si ese bloque es un extremo de un rack vertical
                    if(arriba ^ abajo){
                        if(arriba){
                            nodos[i-1][j-1] = true;
                            nodos[i-1][j+1] = true;
                        }
                        if(abajo){
                            nodos[i+1][j-1] = true;
                            nodos[i+1][j+1] = true;
                        }
                    }
                    boolean derecha = this.almacen[i][j+1];
                    boolean izq = this.almacen[i][j-1];
                    //si el bloque es un extremo de un rack horizontal
                    if(derecha ^ izq){
                        if(derecha){
                            nodos[i+1][j-1] = true;
                            nodos[i-1][j-1] = true;
                        }
                        if(izq){
                            nodos[i+1][j+1] = true;
                            nodos[i-1][j+1] = true;
                        }
                    }
                }
            }
        }

        //obtener movimientos en L de un vertice a otro.
        for(int i = 0; i < this.ancho; i++){
            for(int j = 0; j < this.alto; j++){
                //si es un vertice
                if(nodos[i][j]){
                    //arriba y derecha
                    int y = j + 1;
                    while(y < this.alto && !this.almacen[i][y] && !nodos[i][y]){ //iterar hacia arriba (x+1)
                        int x = i + 1;
                        boolean encontreVertice = false;
                        while(x < this.ancho && !this.almacen[x][y] && !nodos[x][y]){ //iterar hacia la derecha (y+1)
                            if(encontreVertice = nodos[x][y]) break;
                            x++;
                        }
                        if(encontreVertice){
                            nodos[x][y] = true;
                        }
                        y++;
                    }

                    //arriba y derecha
                    int y2 = j - 1;
                    while(y2 >= 0 && !this.almacen[i][y2] && !nodos[i][y2]){ //iterar hacia abajo (x+1)
                        int x = i+1;
                        boolean encontreVertice = false;
                        while(x < this.ancho && !this.almacen[x][y2] && !nodos[x][y2]){ //iterar hacia la derecha (y+1)
                            if(encontreVertice = nodos[x][y2]) break;
                            x++;
                        }
                        if(encontreVertice){
                            nodos[x][y2] = true;
                        }
                        y2--;
                    }

                    //derecha y arriba
                    int x = i + 1;
                    while(x < this.ancho && !this.almacen[x][j] && !nodos[x][j]){ //iterar hacia derecha (x+1)
                        y = j + 1;
                        boolean encontreVertice = false;
                        while(y < this.alto && !this.almacen[x][y] && !(nodos[x][y])){ //iterar hacia arriba (y+1)
                            if(encontreVertice = nodos[x][y]) break;
                            y++;
                        };
                        if(encontreVertice){
                            nodos[i][y] = true;
                            x++;
                            continue;
                        }
                        encontreVertice = false;
                        y2 = j - 1;
                        while(y2 >= 0 && !this.almacen[x][y2] && !(nodos[x][y2])){ //iterar hacia abajo (y-1)
                            if(encontreVertice = nodos[x][y2]) break;
                            y2--;
                        };
                        if(encontreVertice){
                            nodos[i][y2] = true;
                        }
                        x++;
                    }
                    i = x - 1;
                }
            }
        }

        return nodos;
    }

    /**
     *  Imprimir almacen
     */
    public void imprimirMatriz(boolean[][] matriz, int ancho, int alto, String nombre_archivo){
        System.out.print(nombre_archivo);
        try {
            PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    if (matriz[i][j]) {
                        writer.print("\u25A0");
                        System.out.print("\u25A0");
                    } else {
                        writer.print(" ");
                        System.out.print(" ");
                    }
                }
                System.out.println();
                writer.println();
            }
            writer.close();
        } catch(IOException e){

        }
    }

    public void imprimirRacks(){
        for(Rack rack: this.racks){
            rack.imprimir();
        }
    }

    public boolean[][] getAlmacen(){
        return this.almacen;
    }

    public ArrayList<Rack> getRacks() {
        return this.racks;
    }

    public int getAncho(){
        return this.ancho;
    }

    public int getAlto(){
        return this.alto;
    }
}

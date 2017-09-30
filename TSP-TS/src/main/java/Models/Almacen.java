package Models;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Almacen {

    public boolean almacen[][];
    public int ancho;
    public int alto;

    private static Random random = new Random();
    private static int MIN_LARGO = 3;
    private static int MAX_LARGO = 5;

    private enum DIR {
        UP(1,0), RIGTH(0,1), DOWN(-1,0), LEFT(0,-1);

        private final int dx;
        private final int dy;

        private DIR(int dx, int dy){
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
        
        GeneradorAlmacen crearCaso = new GeneradorAlmacen();
        crearCaso.generarCaso();
        
        this.ancho = crearCaso.varAncho;
        this.alto = crearCaso.varAltura;
        
        //this.almacen = new boolean[ancho][alto];
        
        this.almacen = crearCaso._almacen();
    }

    /**
     *  Genera un almacen aleatorio de racks
     *
     */
    public void genRandom(){
        for(int i = 1; i < this.ancho - 2; i++){
            for(int j = 1; j < this.alto - 2; j++){
                int largo = (int) (random.nextDouble()*(MAX_LARGO - MIN_LARGO) + MIN_LARGO);
                DIR dir = DIR.values()[(int) (random.nextDouble()*3)];
                crearRackPosible(i,j,largo,dir);
            }
        }
    }

    /**
     * Crear un rack si fuese posible
     */
    private void crearRackPosible(int iniX, int iniY, int largo, DIR dir){
        if(dir.getDx() == 0){
            int finY = iniY + largo*dir.getDy();
            if(finY<=0) finY = 1;
            if(finY>=this.alto -1) finY = this.alto - 2;

            if (Math.abs(iniY - finY) < MIN_LARGO) return;
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
                while(iniY != finY){
                    this.almacen[iniX][iniY] = true;
                    iniY += dir.getDy();
                }
            }
        } else {
            int finX = iniX + largo*dir.getDx();
            if(finX <= 0) finX = 1;
            if(finX >= this.ancho - 1) finX = this.ancho - 2;
            if (Math.abs(iniX - finX) < MIN_LARGO) return;
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
                while(iniX != finX){
                    this.almacen[iniX][iniY] = true;
                    iniX += dir.getDx();
                }
            }
        }
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
                    //abajo
                    int y = j + 1;
                    while(y < this.alto && !this.almacen[i][y] && !nodos[i][y]){
                        int x = i + 1;
                        boolean encontreVertice = false;
                        while(x < this.ancho && !this.almacen[x][y] && !nodos[x][y]){
                            if(encontreVertice = nodos[x][y]) break;
                            x++;
                        }
                        if(encontreVertice){
                            nodos[x][y] = true;
                        }
                        y++;
                    }

                    //derecha
                    int x = i + 1;
                    while(x < this.ancho && !this.almacen[x][j] && !nodos[x][j]){
                        y = j + 1;
                        boolean encontreVertice = false;
                        while(y < this.alto && !this.almacen[x][y] && !(nodos[x][y])){
                            if(encontreVertice = nodos[x][y]) break;
                            y++;
                        };
                        if(encontreVertice){
                            nodos[i][y] = true;
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
}

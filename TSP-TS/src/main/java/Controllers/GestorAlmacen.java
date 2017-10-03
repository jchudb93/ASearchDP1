package Controllers;

import Models.*;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import Models.Almacen;

public class GestorAlmacen {

    private static Random random = new Random();

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

    /**
     *  Genera un almacen aleatorio de racks
     */
    public static void generarRacksAletorios(Almacen alm){
        int ancho = alm.getAncho();
        int alto = alm.getAlto();
        boolean[][] almacen = new boolean[ancho][alto];
        ArrayList<Rack> racks = new ArrayList<Rack>();

        for(int i = 1; i < ancho - 2; i++){
            for(int j = 1; j < alto - 2; j++){
                int largo = random.nextInt(Almacen.MAX_LARGO - Almacen.MIN_LARGO + 1) + Almacen.MIN_LARGO;
                DIR dir = DIR.values()[random.nextInt(DIR.values().length)];
                Rack rack = crearRackPosible(i,j,largo,dir, almacen, ancho, alto);
                if(rack != null){
                    racks.add(rack);
                }
            }
        }
        alm.setRacks(racks);
        alm.setAlmacen(almacen);
    }

    /**
     * Crear un rack si fuese posible
     */
    private static Rack crearRackPosible(int iniX, int iniY, int largo, DIR dir, boolean[][] almacen, int ancho, int alto){

        if(dir.getDx() == 0){ //izquierda o derecha
            int finY = iniY + largo*dir.getDy();
            if(finY <= 0) finY = 1;
            if(finY >= alto -1) finY = alto - 2;

            if (Math.abs(iniY - finY) < Almacen.MIN_LARGO) return null;
            int iniY_aux = iniY;
            int finY_aux = finY;
            //verificar
            boolean crear = true;
            finY += 2*dir.getDy();
            iniY -= 2*dir.getDy();
            while(iniY != finY){
                if(iniY < 0 || iniY >= ancho){
                    iniY += dir.getDy();
                    continue;
                }
                //verifia extremos vacios
                if (almacen[iniX][iniY] || almacen[iniX-1][iniY] || almacen[iniX+1][iniY]){
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
                    almacen[iniX][iniY] = true;
                    iniY += dir.getDy();
                }
                return new Rack(ini, fin);
            }
        } else { //arriba o abajo
            int finX = iniX + largo*dir.getDx();
            if(finX <= 0) finX = 1;
            if(finX >= ancho - 1) finX = ancho - 2;
            if (Math.abs(iniX - finX) < Almacen.MIN_LARGO) return null;
            int iniX_aux = iniX;
            int finX_aux = finX;
            //verificar
            boolean crear = true;
            finX += 2*dir.getDx();
            iniX -= 2*dir.getDx();
            while(iniX != finX){
                if(iniX < 0 || iniX >= ancho){
                    iniX += dir.getDx();
                    continue;
                }
                //verifia extremos vacios
                if (almacen[iniX][iniY] || almacen[iniX][iniY-1] || almacen[iniX][iniY+1]){
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
                    almacen[iniX][iniY] = true;
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
    public static void generarNodos(Almacen alm){

        boolean [][] almacen = alm.getAlmacen();
        int ancho = alm.getAncho();
        int alto = alm.getAlto();
        boolean [][] nodos = alm.getNodos();

        //obtener vertices
        for(int i = 1; i < ancho - 1; i++){
            for(int j = 1; j < alto - 1; j++){
                //si es un bloque del almacen
                if(almacen[i][j]){
                    boolean arriba = almacen[i+1][j];
                    boolean abajo = almacen[i-1][j];
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
                    boolean derecha = almacen[i][j+1];
                    boolean izq = almacen[i][j-1];
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
        for(int i = 0; i < ancho; i++){
            for(int j = 0; j < alto; j++){
                //si es un vertice
                if(nodos[i][j]){
                    //arriba y derecha/izquierda
                    int y = j + 1;
                    while(y < alto && !almacen[i][y] && !nodos[i][y]){ //iterar hacia arriba (y+1)
                        int x = i + 1;
                        boolean encontreVertice = false;
                        while(x < ancho && !almacen[x][y]){ //iterar hacia la derecha (x+1)
                            if(encontreVertice = nodos[x][y]) break;
                            x++;
                        }
                        if(encontreVertice){
                            nodos[i][y] = true;
                            y++;
                            continue;
                        }
                        encontreVertice = false;
                        int x2 = i - 1;
                        while(x2 >= 0 && !almacen[x2][y]){ //iterar hacia la izquierda (x-1)
                            if(encontreVertice = nodos[x2][y]) break;
                            x2--;
                        }
                        if(encontreVertice){
                            nodos[i][y] = true;
                        }
                        y++;
                    }

                    //abajo y derecha/izquierda
                    int y2 = j - 1;
                    while(y2 >= 0 && !almacen[i][y2] && !nodos[i][y2]){ //iterar hacia abajo (y-1)
                        int x = i+1;
                        boolean encontreVertice = false;
                        while(x < ancho && !almacen[x][y2]){ //iterar hacia la derecha (x+1)
                            if(encontreVertice = nodos[x][y2]) break;
                            x++;
                        }
                        if(encontreVertice){
                            nodos[i][y2] = true;
                            y2--;
                            continue;
                        }
                        encontreVertice = false;
                        int x2 = i - 1;
                        while(x2 >= 0 && !almacen[x2][y2]){ //iterar hacia la izquierda (x-1)
                            if(encontreVertice = nodos[x2][y2]) break;
                            x2--;
                        }
                        if(encontreVertice){
                            nodos[i][y2] = true;
                        }
                        y2--;
                    }

                    //derecha y arriba/abajo
                    int x = i + 1;
                    while(x < ancho && !almacen[x][j] && !nodos[x][j]){ //iterar hacia derecha (x+1)
                        y = j + 1;
                        boolean encontreVertice = false;
                        while(y < alto && !almacen[x][y]){ //iterar hacia arriba (y+1)
                            if(encontreVertice = nodos[x][y]) break;
                            y++;
                        };
                        if(encontreVertice){
                            nodos[x][j] = true;
                            x++;
                            continue;
                        }
                        encontreVertice = false;
                        y2 = j - 1;
                        while(y2 >= 0 && !almacen[x][y2]){ //iterar hacia abajo (y-1)
                            if(encontreVertice = nodos[x][y2]) break;
                            y2--;
                        };
                        if(encontreVertice){
                            nodos[x][j] = true;
                        }
                        x++;
                    }

                    //izquierda y arriba/abajo
                    int x2 = i - 1;
                    while(x2 > 0 && !almacen[x2][j] && !nodos[x2][j]){ //iterar hacia izquierda (x-1)
                        y = j + 1;
                        boolean encontreVertice = false;
                        while(y < alto && !almacen[x2][y] ){ //iterar hacia arriba (y+1)
                            if(encontreVertice = nodos[x2][y]) break;
                            y++;
                        };
                        if(encontreVertice){
                            nodos[x2][j] = true;
                            x2++;
                            continue;
                        }
                        encontreVertice = false;
                        y2 = j - 1;
                        while(y2 >= 0 && !almacen[x2][y2] ){ //iterar hacia abajo (y-1)
                            if(encontreVertice = nodos[x2][y2]) break;
                            y2--;
                        };
                        if(encontreVertice){
                            nodos[x2][j] = true;
                        }
                        x2--;
                    }
                }
            }
        }

        alm.setNodos(nodos);
    }

    public static void escogerPuntoPartida(Almacen alm, int i, int j){
        boolean[][] nodos = alm.getNodos();

        nodos[i][j] = true;

        alm.setNodos(nodos);
    }
    /**
     *  Imprimir almacen
     */
    public static void imprimirAlmacen(Almacen almacen, String nombre_archivo){
        System.out.println(nombre_archivo);
        int ancho = almacen.getAncho();
        int alto = almacen.getAlto();
        boolean[][] matriz = almacen.getAlmacen();

        imprimirMatriz(matriz, ancho, alto, nombre_archivo);
    }

    public static void imprimirNodos(Almacen almacen, String nombre_archivo){
        System.out.println(nombre_archivo);
        int ancho = almacen.getAncho();
        int alto = almacen.getAlto();
        boolean[][] matriz = almacen.getNodos();

        imprimirMatriz(matriz, ancho, alto, nombre_archivo);
    }

    private static void imprimirMatriz(boolean[][] matriz, int ancho, int alto, String nombre_archivo){
        try {
            PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    if (matriz[i][j]) {
                        writer.print("x ");
                        System.out.print("x ");
                    } else {
                        writer.print("_ ");
                        System.out.print("_ ");
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

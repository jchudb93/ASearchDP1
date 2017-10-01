package Models;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Almacen {

    private boolean almacen[][];
    private boolean nodos[][];
    private int ancho;
    private int alto;
    private ArrayList<Rack> racks;


    public static int MIN_LARGO = 3;
    public static int MAX_LARGO = 5;

    public Almacen(int ancho, int alto){

        this.ancho = ancho;
        this.alto = alto;

        this.almacen = new boolean[ancho][alto];
        this.nodos = new boolean[ancho][alto];
        this.racks = new ArrayList<Rack>();
    }


    public void imprimirRacks(){
        for(Rack rack: this.racks){
            rack.imprimir();
        }
    }

    public boolean[][] getNodos(){
        return this.nodos;
    }

    public void setNodos(boolean[][] nodos){
        for(int i = 0; i < this.ancho; i++){
            for(int j = 0; j < this.alto; j++){
                this.nodos[i][j] = nodos[i][j];
            }
        }
    }

    public boolean[][] getAlmacen(){
        return this.almacen;
    }
    public void setAlmacen(boolean[][] almacen){
        for(int i = 0; i < this.ancho; i++){
            for(int j = 0; j < this.alto; j++){
                this.almacen[i][j] = almacen[i][j];
            }
        }
    }

    public boolean estaOcupado(int i, int j){
        return this.almacen[i][j];
    }

    public ArrayList<Rack> getRacks() {
        return this.racks;
    }
    public void setRacks(ArrayList<Rack> racks){
        this.racks = racks;
    }

    public int getAncho(){
        return this.ancho;
    }

    public int getAlto(){
        return this.alto;
    }
}

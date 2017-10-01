package Models;

import java.awt.Point;

public class Rack {
    private Point posIni;
    private Point posFin;
    private int niveles;

    public Rack (Point ini, Point fin){
        this.posIni = new Point(ini.x, ini.y);
        this.posFin = new Point(fin.x, fin.y);
        this.niveles = 1; //por defecto - no se utilizará
    }

    public Point getPosIni(){
        return new Point(this.posIni.x, this.posIni.y);
    }

    public Point getPosFin(){
        return new Point(this.posFin.x, this.posFin.y);
    }

    public void imprimir(){
        String posIni = "(" + String.valueOf(this.posIni.x) + ", " + String.valueOf(this.posIni.y) + ")";
        String posFin = "(" + String.valueOf(this.posFin.x) + ", " + String.valueOf(this.posFin.y) + ")";
        System.out.println("INI: " + posIni + " FIN: " + posFin);
    }
}

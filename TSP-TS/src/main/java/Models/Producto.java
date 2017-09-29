package Models;

import java.awt.Point;

public class Producto {
    private String descripcion;
    private Point posicion;

    public Producto(String descripciton, Point posicion){
        this.setDescripcion(descripciton);
        this.setPosicion(new Point(posicion.x, posicion.y));
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = new Point(posicion.x, posicion.y);
    }
    public void setPosicion(int x, int y){
        this.posicion = new Point(x,y);
    }
}

package Recocido;
import javax.rmi.CORBA.Util;
import java.awt.*;
import java.util.Random;
import java.lang.*;

public class Recocido {


    private int [][] distancias;
    private int [] caminoInicial;

    //temperatura inicial
    private double temperatura = 1000;
    //que va disminuyendo en cada iteracion segun:
    private double velocidadEnfriamiento = 0.01;

    //constructor recibe las di
    public Recocido(int [][] distancias, int [] caminoInicial) {
        this.setCaminoInicial(caminoInicial);
        this.setDistancias(distancias);
    }

    public int[] generarCamino(){

        System.out.println("Distancia Total Solución Inicial: " + funcionObjetivo(this.getCaminoInicial()));
        System.out.print("Camino inicial: ");
        Utiles.imprimirCamino(this.getCaminoInicial());

        //me va a ayudar para que el swap sea aleatorio
        Random rand = new Random();

        //copio la solucion inicial en un arreglo para el algoritmo
        int [] solucionActual = Utiles.copiarCamino(this.getCaminoInicial());
        //la nueva que me va a servir para comparar
        int [] solucionNueva;

        //auxiliar para hacer swap
        int aux = 0;

        //dos variables para comparar funcion objetivo actual y final
        int fObjetivoActual = this.funcionObjetivo(solucionActual);;
        int fObjetivoNueva = 0;

        //para la probabilidad de aceptacion
        double probAcept;

        while(temperatura > 0){

            //elijo dos posiciones al azar para hacer swap
            int pos1 = rand.nextInt(solucionActual.length);
            int pos2 = rand.nextInt(solucionActual.length);
            //por si acaso salen iguales las dos posiciones, itero hasta que sean distintas
            while(pos1 == pos2) {pos2 = rand.nextInt(solucionActual.length);}

            //swap
            solucionNueva = Utiles.copiarCamino(solucionActual);

            aux = solucionNueva[pos1];
            solucionNueva[pos1] = solucionNueva[pos2];
            solucionNueva[pos2] = aux;

            //calcular valor de funcion objetivo luego de swap
            fObjetivoNueva = this.funcionObjetivo(solucionNueva);

            //si la solucion es mejor
            if(fObjetivoNueva < fObjetivoActual){
                solucionActual = Utiles.copiarCamino(solucionNueva);
                fObjetivoActual = fObjetivoNueva;
            }
            else {
                probAcept = Utiles.probabilidadAceptacion(fObjetivoActual, fObjetivoNueva, temperatura);
                if(probAcept > Utiles.randomDouble()){
                    solucionActual = Utiles.copiarCamino(solucionNueva);
                    fObjetivoActual = fObjetivoNueva;
                }
            }
            //enfriamiento
            temperatura = temperatura - velocidadEnfriamiento;

            System.out.println("Temperatura actual: " + temperatura);
            System.out.print("Solucion actual: ");
            Utiles.imprimirCamino(this.getCaminoInicial());
            System.out.print("Costo Actual: ");
            System.out.println();

        }

        return solucionActual;
    }


    //funcion objetivo es la suma de distancias
    //en el caso del recocido esta funcion es la "energia" del sistema
    public int funcionObjetivo(int [] solucion){
        int valor = 0;

        for(int i = 0 ; i < solucion.length-1; i++){
            valor += this.distancias[solucion[i]][solucion[i+1]];
        }
        return valor;
    }

    public int[][] getDistancias() {
        return distancias;
    }

    public void setDistancias(int[][] distancias) {
        this.distancias = distancias;
    }

    public int[] getCaminoInicial() {
        return caminoInicial;
    }

    public void setCaminoInicial(int[] caminoInicial) {
        this.caminoInicial = caminoInicial;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public double getVelocidadEnfriamiento() {
        return velocidadEnfriamiento;
    }

    public void setVelocidadEnfriamiento(double velocidadEnfriamiento) {
        this.velocidadEnfriamiento = velocidadEnfriamiento;
    }
}
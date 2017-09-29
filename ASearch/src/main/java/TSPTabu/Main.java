package TSPTabu;

public class Main {

    public static void main(String[]args){
        int nAristas = ;
        //while (nAristas <2000){
            Matriz matriz = new Matriz(nAristas);
            BusquedaTabu busquedaTabu = new BusquedaTabu(matriz);
            Long inicio = System.currentTimeMillis();
            busquedaTabu.solucionar();
            Long fin = System.currentTimeMillis() - inicio;
            System.out.println(String.format("tamanho: %d\t tiempo: %d ",nAristas,fin));
            //nAristas += 10;
        //}
        System.out.println("success");
    }

}

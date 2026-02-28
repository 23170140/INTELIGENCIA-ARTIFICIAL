package Busqueda;

//Autor: Gerardo Leon Perez
public class App {
    public static void main(String[] args) {
        
        String estadoInicial = "1238 4765";  
        String estadoObjetivo = "1284376 5";

        System.out.println("Buscando...");
        System.out.println("Estado inicial: " + estadoInicial);
        System.out.println("Estado objetivo: " + estadoObjetivo);

        Nodo raiz = new Nodo(estadoInicial);
        ArbolDeBusqueda arbol = new ArbolDeBusqueda(raiz);

        
        long inicio = System.currentTimeMillis();
        Nodo solucion = arbol.busquedaPrimeroAnchura(estadoObjetivo); //Anchura
        long fin = System.currentTimeMillis();

        
        //arbol.imprimirCamino(solucion);
        System.out.println("Busqueda en Anchura:");
        System.out.println("Tiempo total de ejecucion: " + (fin - inicio) + " ms");
        System.out.println("********************************************************************************");

        long inicio2 = System.currentTimeMillis();
        Nodo solucion2 = arbol.busquedaProfundidad(estadoObjetivo); //Profundidad
        long fin2 = System.currentTimeMillis();
        //arbol.imprimirCamino(solucion2);
        System.out.println("Busqueda en Profundidad:");
        System.out.println("Tiempo total de ejecucion: " + (fin2 - inicio2) + " ms");
        System.out.println("********************************************************************************");

        long inicio3 = System.currentTimeMillis();
        System.out.println("Busqueda de Costo Uniforme:");
        Nodo solucion3 = arbol.busquedaCostoUniforme(estadoObjetivo);
        long fin3 = System.currentTimeMillis();

    
        //arbol.imprimirCamino(solucion3);
        System.out.println("Tiempo total de ejecucion: " + (fin3 - inicio3) + " ms");
    }
}
package puzzle8_menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class GamePuzzle {

    public static void main(String[] args) {
        int [][] inicio = {{4,1,0},{2,6,3},{7,5,8}};
        int [][] fin = {{1,2,3},{4,5,6},{7,8,0}};

        Scanner sc = new Scanner(System.in);
        System.out.println("========== 8 PUZZLE ==========");
        System.out.println("Estado inicial:");
        imprimirEstado(inicio);
        System.out.println("Estado objetivo:");
        imprimirEstado(fin);
        System.out.println("==============================");
        System.out.println("Seleccione el tipo de busqueda:");
        System.out.println("1. BFS (Busqueda en Anchura)");
        System.out.println("2. DFS (Busqueda en Profundidad)");
        System.out.println("3. Costo Uniforme");
        System.out.println("4. Busqueda simple (original)");
        System.out.print("Opcion: ");
        int opcion = sc.nextInt();
        sc.close();

        Nodo nodoInicio = new Nodo(inicio);
        Nodo sol = null;

        switch (opcion) {
            case 1:
                System.out.println("\n--- BFS ---");
                ArbolDeBusqueda arbolBFS = new ArbolDeBusqueda(nodoInicio, fin);
                sol = arbolBFS.buscarBFS();
                break;
            case 2:
                System.out.println("\n--- DFS ---");
                ArbolDeBusqueda arbolDFS = new ArbolDeBusqueda(nodoInicio, fin);
                sol = arbolDFS.buscarDFS();
                break;
            case 3:
                System.out.println("\n--- Costo Uniforme ---");
                ArbolDeBusqueda arbolCU = new ArbolDeBusqueda(nodoInicio, fin);
                sol = arbolCU.buscarCostoUniforme();
                break;
            case 4:
                System.out.println("\n--- Busqueda Simple ---");
                sol = buscarSolucion(nodoInicio, fin);
                break;
            default:
                System.out.println("Opcion no valida");
                return;
        }

        if (sol == null) {
            System.out.println("No se encontro solucion");
            return;
        }

        // Reconstruir camino
        ArrayList<Nodo> camino = new ArrayList<Nodo>();
        Nodo actual = sol;
        while (actual != null) {
            camino.add(actual);
            actual = actual.getPadre();
        }
        Collections.reverse(camino);

        System.out.println("\n========== SOLUCION ==========");
        System.out.println("Pasos: " + (camino.size() - 1));
        System.out.println();
        for (int i = 0; i < camino.size(); i++) {
            System.out.println("Paso " + i + ":");
            imprimirEstado(camino.get(i).getEstado());
            System.out.println("-------------------");
        }
    }

    public static Nodo buscarSolucion(Nodo inicio, int[][]fin){
        ArrayList<Nodo> abiertos = new ArrayList<Nodo>();
        ArrayList<Nodo> visitados = new ArrayList<Nodo>();
        abiertos.add(inicio);
        int cont = 0;
        while(abiertos.size()!=0){

           ArrayList<Nodo> hijos = new ArrayList<Nodo>();
           Nodo revisar = abiertos.remove(0);
           imprimirEstado(revisar.getEstado());
           int [] pcero = encontrarCero(revisar.getEstado());
              cont++;
              System.out.println("Iteracion: " + cont);

           if(Arrays.deepEquals(revisar.getEstado(), fin)){
               System.out.println("Solucion encontrada");
               return revisar;
           }

           visitados.add(revisar);
            if(pcero[0]!=0){
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int arriba=hijo.getEstado()[pcero[0]-1][pcero[1]];
                hijo.getEstado()[pcero[0]-1][pcero[1]]=0;
                hijo.getEstado()[pcero[0]][pcero[1]]=arriba;
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            if(pcero[0]!=2){
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[pcero[0]+1][pcero[1]];
                hijo.getEstado()[pcero[0]+1][pcero[1]]=0;
                hijo.getEstado()[pcero[0]][pcero[1]]=abajo;
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            if(pcero[1]!=0){
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int izquierda=hijo.getEstado()[pcero[0]][pcero[1]-1];
                hijo.getEstado()[pcero[0]][pcero[1]-1]=0;
                hijo.getEstado()[pcero[0]][pcero[1]]=izquierda;
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            if(pcero[1]!=2){
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[pcero[0]][pcero[1]+1];
                hijo.getEstado()[pcero[0]][pcero[1]+1]=0;
                hijo.getEstado()[pcero[0]][pcero[1]]=derecha;
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            revisar.setHijos(hijos);
            
        }
        return null;
    }

    public static void imprimirEstado(int [][] estado){
                for(int i=0; i<estado.length; i++){
                    for(int j=0; j<estado[i].length; j++){
                        System.out.print(estado[i][j] + " ");
                    }
                    System.out.println();
            } 
    }


    private static int[] encontrarCero(int [][] estado){
        int [] pos = new int[2];
        for(int i=0; i<estado.length; i++){
            for(int j=0; j<estado[i].length; j++){
                if(estado[i][j] == 0){
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        System.out.println("Posicion del cero: " + pos[0] + "," + pos[1]);
        return pos;
    }
    private static int[][] clonar(int [][] estado){
        int [][] nuevo = new int[estado.length][estado[0].length];
        for(int i=0; i<estado.length; i++){
            for(int j=0; j<estado[i].length; j++){
                nuevo[i][j] = estado[i][j];
            }
        }
        return nuevo;
    }

    private static boolean estaVisitado(ArrayList<Nodo> visitados, Nodo hijo) {
        for (Nodo n : visitados) {
            if (Arrays.deepEquals(n.getEstado(), hijo.getEstado())) {
                return true;
            }
        }
        return false;
    }
}


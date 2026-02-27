package Puzzle08.Puzzle24;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GamePuzzle {

    static int[][] fin = {{1,2,3},{4,5,6},{7,8,0}};

    public static void main(String[] args) {
        int [][] inicio = {{4,1,0},{2,6,3},{7,5,8}};
        Nodo nodoInicio = new Nodo(inicio);
        nodoInicio.setG(0);
        nodoInicio.setH(calcularManhattan(inicio, fin));
        nodoInicio.calcularF();

        Nodo sol = buscarSolucion(nodoInicio, fin);

        if (sol == null) {
            System.out.println("No se encontro solucion");
            return;
        }

        // Reconstruir camino
        ArrayList<Nodo> camino = new ArrayList<Nodo>();
        Nodo actual = sol;
        while (actual != null) {
            camino.add(actual);
            actual = actual.padre;
        }
        Collections.reverse(camino);

        System.out.println("========== SOLUCION ==========");
        System.out.println("Pasos: " + (camino.size() - 1));
        System.out.println();
        for (int i = 0; i < camino.size(); i++) {
            System.out.println("Paso " + i + " (f=" + camino.get(i).getF() 
                + " g=" + camino.get(i).getG() + " h=" + camino.get(i).getH() + "):");
            imprimirEstado(camino.get(i).getEstado());
            System.out.println("-------------------");
        }
    }

    public static Nodo buscarSolucion(Nodo inicio, int[][] fin) {
        ArrayList<Nodo> abiertos = new ArrayList<Nodo>();
        ArrayList<Nodo> visitados = new ArrayList<Nodo>();

        abiertos.add(inicio);
        int cont = 0;

        while (abiertos.size() != 0) {
            // Ordenar por f(n) = g(n) + h(n) -> el menor primero
            Collections.sort(abiertos);

            Nodo revisar = abiertos.remove(0);
            cont++;
            System.out.println("Iteracion: " + cont + " | f=" + revisar.getF() 
                + " g=" + revisar.getG() + " h=" + revisar.getH());
            imprimirEstado(revisar.getEstado());

            if (Arrays.deepEquals(revisar.getEstado(), fin)) {
                System.out.println("Solucion encontrada en " + cont + " iteraciones");
                return revisar;
            }

            visitados.add(revisar);
            int[] pcero = encontrarCero(revisar.getEstado());
            ArrayList<Nodo> hijos = new ArrayList<Nodo>();

            // Mover arriba
            if (pcero[0] != 0) {
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int arriba = hijo.getEstado()[pcero[0] - 1][pcero[1]];
                hijo.getEstado()[pcero[0] - 1][pcero[1]] = 0;
                hijo.getEstado()[pcero[0]][pcero[1]] = arriba;
                hijo.setG(revisar.getG() + 1);
                hijo.setH(calcularManhattan(hijo.getEstado(), fin));
                hijo.calcularF();
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            // Mover abajo
            if (pcero[0] != 2) {
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int abajo = hijo.getEstado()[pcero[0] + 1][pcero[1]];
                hijo.getEstado()[pcero[0] + 1][pcero[1]] = 0;
                hijo.getEstado()[pcero[0]][pcero[1]] = abajo;
                hijo.setG(revisar.getG() + 1);
                hijo.setH(calcularManhattan(hijo.getEstado(), fin));
                hijo.calcularF();
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            // Mover izquierda
            if (pcero[1] != 0) {
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int izquierda = hijo.getEstado()[pcero[0]][pcero[1] - 1];
                hijo.getEstado()[pcero[0]][pcero[1] - 1] = 0;
                hijo.getEstado()[pcero[0]][pcero[1]] = izquierda;
                hijo.setG(revisar.getG() + 1);
                hijo.setH(calcularManhattan(hijo.getEstado(), fin));
                hijo.calcularF();
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            // Mover derecha
            if (pcero[1] != 2) {
                Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                int derecha = hijo.getEstado()[pcero[0]][pcero[1] + 1];
                hijo.getEstado()[pcero[0]][pcero[1] + 1] = 0;
                hijo.getEstado()[pcero[0]][pcero[1]] = derecha;
                hijo.setG(revisar.getG() + 1);
                hijo.setH(calcularManhattan(hijo.getEstado(), fin));
                hijo.calcularF();
                if (!estaVisitado(visitados, hijo)) {
                    abiertos.add(hijo);
                    hijos.add(hijo);
                }
            }
            revisar.setHijos(hijos);
        }
        return null;
    }

    /**
     * Heuristica: Distancia Manhattan
     * Suma de las distancias de cada ficha a su posicion objetivo
     */
    public static int calcularManhattan(int[][] estado, int[][] objetivo) {
        int distancia = 0;
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                int valor = estado[i][j];
                if (valor != 0) {
                    // Buscar posicion objetivo de este valor
                    int[] posObj = encontrarValor(objetivo, valor);
                    distancia += Math.abs(i - posObj[0]) + Math.abs(j - posObj[1]);
                }
            }
        }
        return distancia;
    }

    private static int[] encontrarValor(int[][] estado, int valor) {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                if (estado[i][j] == valor) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    public static void imprimirEstado(int[][] estado) {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                System.out.print(estado[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] encontrarCero(int[][] estado) {
        int[] pos = new int[2];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                if (estado[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }

    private static int[][] clonar(int[][] estado) {
        int[][] nuevo = new int[estado.length][estado[0].length];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
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

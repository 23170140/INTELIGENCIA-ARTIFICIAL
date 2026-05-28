package puzzle8_menu;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ArbolDeBusqueda {

    private final Nodo raiz;
    private final int[][] objetivo;

    public ArbolDeBusqueda(Nodo raiz, int[][] objetivo) {
        this.raiz = raiz;
        this.objetivo = objetivo;
    }

    // BUSQUEDA EN ANCHURA (BFS)
    public Nodo buscarBFS() {
        if (raiz == null) return null;

        Set<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();
        int cont = 0;

        cola.add(raiz);
        visitados.add(raiz.estadoToString());

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            cont++;

            System.out.println("BFS - Iteracion: " + cont);
            GamePuzzle.imprimirEstado(actual.getEstado());

            if (Arrays.deepEquals(actual.getEstado(), objetivo)) {
                System.out.println("Solucion encontrada en " + cont + " iteraciones (BFS)");
                return actual;
            }

            for (Nodo hijo : actual.generarSucesores()) {
                if (!visitados.contains(hijo.estadoToString())) {
                    visitados.add(hijo.estadoToString());
                    cola.add(hijo);
                }
            }
        }
        return null;
    }

    // BUSQUEDA EN PROFUNDIDAD (DFS)
    public Nodo buscarDFS() {
        if (raiz == null) return null;

        Set<String> visitados = new HashSet<>();
        Deque<Nodo> pila = new ArrayDeque<>();
        int cont = 0;

        pila.push(raiz);
        visitados.add(raiz.estadoToString());

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();
            cont++;

            System.out.println("DFS - Iteracion: " + cont);
            GamePuzzle.imprimirEstado(actual.getEstado());

            if (Arrays.deepEquals(actual.getEstado(), objetivo)) {
                System.out.println("Solucion encontrada en " + cont + " iteraciones (DFS)");
                return actual;
            }

            List<Nodo> hijos = actual.generarSucesores();
            for (int i = hijos.size() - 1; i >= 0; i--) {
                Nodo hijo = hijos.get(i);
                if (!visitados.contains(hijo.estadoToString())) {
                    visitados.add(hijo.estadoToString());
                    pila.push(hijo);
                }
            }
        }
        return null;
    }

    // BUSQUEDA DE COSTO UNIFORME
    public Nodo buscarCostoUniforme() {
        if (raiz == null) return null;

        Set<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();
        int cont = 0;

        cola.add(raiz);
        visitados.add(raiz.estadoToString());

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            cont++;

            System.out.println("Costo Uniforme - Iteracion: " + cont);
            GamePuzzle.imprimirEstado(actual.getEstado());

            if (Arrays.deepEquals(actual.getEstado(), objetivo)) {
                System.out.println("Solucion encontrada en " + cont + " iteraciones (Costo Uniforme)");
                return actual;
            }

            List<Nodo> hijos = actual.generarSucesores();
            Nodo mejor = null;
            int mejorScore = -1;

            for (Nodo hijo : hijos) {
                if (visitados.contains(hijo.estadoToString())) continue;

                // Calcular score: cuantas fichas estan en su posicion correcta
                int score = 0;
                for (int i = 0; i < hijo.getEstado().length; i++) {
                    for (int j = 0; j < hijo.getEstado()[i].length; j++) {
                        if (hijo.getEstado()[i][j] == objetivo[i][j]) {
                            score++;
                        }
                    }
                }
                hijo.setCosto(score);

                if (score > mejorScore) {
                    mejor = hijo;
                    mejorScore = score;
                }
            }

            if (mejor != null) {
                visitados.add(mejor.estadoToString());
                cola.add(mejor);
            } else {
                for (Nodo hijo : hijos) {
                    if (!visitados.contains(hijo.estadoToString())) {
                        visitados.add(hijo.estadoToString());
                        cola.add(hijo);
                    }
                }
            }
        }
        return null;
    }
}

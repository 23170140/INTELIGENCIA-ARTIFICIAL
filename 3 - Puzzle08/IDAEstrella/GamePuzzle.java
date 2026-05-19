package IDAEstrella;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 24-Puzzle (5x5) resuelto con IDA* (Iterative Deepening A*)
 * 
 * IDA* combina la optimalidad de A* con el bajo consumo de memoria de DFS.
 * Usa un umbral (threshold) basado en f(n) = g(n) + h(n).
 * En cada iteracion realiza una busqueda en profundidad podando nodos
 * cuyo f supere el umbral actual. Si no encuentra solucion, incrementa
 * el umbral al minimo f que excedio el umbral anterior.
 * 
 * Heuristica: Distancia Manhattan (admisible y consistente).
 */
public class GamePuzzle {

    // Estado objetivo del 24-puzzle (5x5)
    static int[][] fin = {
        { 1,  2,  3,  4,  5},
        { 6,  7,  8,  9, 10},
        {11, 12, 13, 14, 15},
        {16, 17, 18, 19, 20},
        {21, 22, 23, 24,  0}
    };

    // Constante para indicar "solucion encontrada" en la busqueda recursiva
    private static final int ENCONTRADO = -1;

    // Contador de nodos expandidos
    private static int nodosExpandidos = 0;

    // Nodo solucion encontrado
    private static Nodo solucionEncontrada = null;

    public static void main(String[] args) {
        // Estado inicial: unas pocas fichas movidas para demostrar el algoritmo
        // (un puzzle 5x5 con muchos movimientos puede tardar mucho)
        int[][] inicio = {
            { 1,  2,  3,  4,  5},
            { 6,  7,  8,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19,  0},
            {21, 22, 23, 24, 20}
        };

        System.out.println("========== 24 PUZZLE - IDA* ==========");
        System.out.println("Estado inicial:");
        imprimirEstado(inicio);
        System.out.println("Estado objetivo:");
        imprimirEstado(fin);
        System.out.println("=======================================\n");

        Nodo nodoInicio = new Nodo(inicio);
        nodoInicio.setG(0);
        nodoInicio.setH(calcularManhattan(inicio, fin));
        nodoInicio.calcularF();

        long tiempoInicio = System.currentTimeMillis();
        Nodo sol = buscarIDAEstrella(nodoInicio);
        long tiempoFin = System.currentTimeMillis();

        if (sol == null) {
            System.out.println("No se encontro solucion");
            return;
        }

        // Reconstruir camino
        ArrayList<Nodo> camino = new ArrayList<>();
        Nodo actual = sol;
        while (actual != null) {
            camino.add(actual);
            actual = actual.getPadre();
        }
        Collections.reverse(camino);

        System.out.println("\n========== SOLUCION ==========");
        System.out.println("Pasos: " + (camino.size() - 1));
        System.out.println("Nodos expandidos: " + nodosExpandidos);
        System.out.println("Tiempo: " + (tiempoFin - tiempoInicio) + " ms");
        System.out.println();
        for (int i = 0; i < camino.size(); i++) {
            System.out.println("Paso " + i + " (f=" + camino.get(i).getF()
                    + " g=" + camino.get(i).getG() + " h=" + camino.get(i).getH() + "):");
            imprimirEstado(camino.get(i).getEstado());
            System.out.println("-------------------");
        }
    }

    /**
     * Algoritmo IDA* (Iterative Deepening A*)
     * 
     * 1. Inicializa el umbral con h(inicio)
     * 2. Realiza DFS limitado por f(n) <= umbral
     * 3. Si encuentra solucion, retorna el nodo
     * 4. Si no, actualiza el umbral al minimo f que supero el anterior
     * 5. Repite hasta encontrar solucion o determinar que no existe
     */
    public static Nodo buscarIDAEstrella(Nodo inicio) {
        int umbral = inicio.getF();
        nodosExpandidos = 0;
        solucionEncontrada = null;

        System.out.println("IDA* - Umbral inicial: " + umbral);

        while (true) {
            Set<String> caminoActual = new HashSet<>();
            caminoActual.add(inicio.estadoToString());

            int resultado = busquedaRecursiva(inicio, umbral, caminoActual);

            if (resultado == ENCONTRADO) {
                return solucionEncontrada;
            }
            if (resultado == Integer.MAX_VALUE) {
                // No hay solucion
                return null;
            }

            umbral = resultado;
            System.out.println("IDA* - Nuevo umbral: " + umbral + " | Nodos expandidos: " + nodosExpandidos);
        }
    }

    /**
     * Busqueda recursiva en profundidad limitada por el umbral.
     * 
     * @param nodo    Nodo actual
     * @param umbral  Umbral de f(n) para esta iteracion
     * @param camino  Conjunto de estados en el camino actual (evita ciclos)
     * @return ENCONTRADO si se encontro la solucion,
     *         o el minimo f que excedio el umbral
     */
    private static int busquedaRecursiva(Nodo nodo, int umbral, Set<String> camino) {
        int f = nodo.getF();

        // Si f supera el umbral, podar esta rama
        if (f > umbral) {
            return f;
        }

        // Verificar si es el estado objetivo
        if (Arrays.deepEquals(nodo.getEstado(), fin)) {
            solucionEncontrada = nodo;
            return ENCONTRADO;
        }

        int minUmbral = Integer.MAX_VALUE;
        nodosExpandidos++;

        // Generar y explorar sucesores
        for (Nodo hijo : nodo.generarSucesores()) {
            String estadoStr = hijo.estadoToString();

            // Evitar ciclos: no revisitar estados del camino actual
            if (camino.contains(estadoStr)) {
                continue;
            }

            hijo.setH(calcularManhattan(hijo.getEstado(), fin));
            hijo.calcularF();

            camino.add(estadoStr);
            int resultado = busquedaRecursiva(hijo, umbral, camino);
            camino.remove(estadoStr);

            if (resultado == ENCONTRADO) {
                return ENCONTRADO;
            }
            if (resultado < minUmbral) {
                minUmbral = resultado;
            }
        }

        return minUmbral;
    }

    /**
     * Heuristica: Distancia Manhattan
     * Suma de las distancias de cada ficha a su posicion objetivo.
     * Es admisible (nunca sobreestima) y consistente para el n-puzzle.
     */
    public static int calcularManhattan(int[][] estado, int[][] objetivo) {
        int distancia = 0;
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                int valor = estado[i][j];
                if (valor != 0) {
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
                System.out.printf("%3d", estado[i][j]);
            }
            System.out.println();
        }
    }
}

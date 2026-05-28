package puzzle24;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main del 24-Puzzle (5x5) con IDA*.
 * Menu para elegir heuristica o comparar ambas.
 */
public class GamePuzzle {

    static final int[][] OBJETIVO = {
        { 1,  2,  3,  4,  5},
        { 6,  7,  8,  9, 10},
        {11, 12, 13, 14, 15},
        {16, 17, 18, 19, 20},
        {21, 22, 23, 24,  0}
    };

    // Estructura para guardar resultados de una ejecucion
    static class Resultado {
        String nombre;
        int nodos;
        long tiempo;
        int movimientos;

        Resultado(String nombre, int nodos, long tiempo, int movimientos) {
            this.nombre = nombre;
            this.nodos = nodos;
            this.tiempo = tiempo;
            this.movimientos = movimientos;
        }
    }

    public static void main(String[] args) {
        int[][] inicio = {
            { 1,  2,  3,  4,  5},
            { 6,  7,  0,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19,  20},
            {21, 22, 23, 24, 8}
        };

        System.out.println("=== 24-PUZZLE (5x5) - IDA* ===\n");
        System.out.println("Seleccione la heuristica:");
        System.out.println("  1. Distancia Manhattan");
        System.out.println("  2. Conflicto Lineal");
        System.out.println("  3. Comparar ambas (tabla de rendimiento)");
        System.out.print("Opcion [1/2/3]: ");

        Scanner scanner = new Scanner(System.in);
        String opcion = scanner.nextLine().trim();
        scanner.close();

        System.out.println();
        System.out.println("Estado inicial:");
        Puzzle24.imprimirEstado(inicio);
        System.out.println("Estado objetivo:");
        Puzzle24.imprimirEstado(OBJETIVO);

        if (opcion.equals("3")) {
            compararHeuristicas(inicio);
        } else {
            boolean usarManhattan = !opcion.equals("2");
            ejecutarIndividual(inicio, usarManhattan);
        }
    }

    /** Ejecuta IDA* con una sola heuristica y muestra el camino. */
    private static void ejecutarIndividual(int[][] inicio, boolean usarManhattan) {
        String nombre = usarManhattan ? "Distancia Manhattan" : "Conflicto Lineal";
        System.out.println("Heuristica: " + nombre);

        Resultado res = ejecutar(inicio, usarManhattan, nombre);
        if (res == null) {
            System.out.println("No se encontro solucion.");
            return;
        }

        System.out.println("\n=== SOLUCION ===");
        System.out.println("Heuristica: " + nombre);
        System.out.println("Pasos: " + res.movimientos);
        System.out.println("Nodos expandidos: " + res.nodos);
        System.out.println("Tiempo: " + res.tiempo + " ms");
    }

    /** Ejecuta IDA* con ambas heuristicas y muestra tabla comparativa. */
    private static void compararHeuristicas(int[][] inicio) {
        String[] nombres = {"Distancia Manhattan", "Conflicto Lineal"};
        boolean[] modos = {true, false};
        ArrayList<Resultado> resultados = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            System.out.println("--- " + nombres[i] + " ---");
            Resultado res = ejecutar(inicio, modos[i], nombres[i]);
            if (res != null) resultados.add(res);
            else System.out.println("Sin solucion.\n");
        }

        // Tabla comparativa
        if (resultados.size() == 2) {
            Resultado r1 = resultados.get(0), r2 = resultados.get(1);
            System.out.println("\n=== ANALISIS DE RENDIMIENTO ===");
            System.out.printf("%-22s | %-14s | %-14s%n", "Metrica", r1.nombre, r2.nombre);
            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-22s | %-14d | %-14d%n", "Movimientos (pasos)", r1.movimientos, r2.movimientos);
            System.out.printf("%-22s | %-14d | %-14d%n", "Nodos expandidos", r1.nodos, r2.nodos);
            System.out.printf("%-22s | %-14d | %-14d%n", "Tiempo (ms)", r1.tiempo, r2.tiempo);
            System.out.println("-------------------------------------------------------------");

            if (r1.nodos > 0 && r2.nodos > 0) {
                double reduccion = (1.0 - (double) r2.nodos / r1.nodos) * 100;
                if (reduccion > 0)
                    System.out.printf("Conflicto Lineal redujo nodos en %.1f%% respecto a Manhattan.%n", reduccion);
                else if (reduccion < 0)
                    System.out.printf("Manhattan uso %.1f%% menos nodos que Conflicto Lineal.%n", -reduccion);
                else
                    System.out.println("Ambas expandieron la misma cantidad de nodos.");
            }
        }
    }

    /** Ejecuta IDA* y retorna el resultado con estadisticas. Imprime el camino. */
    private static Resultado ejecutar(int[][] inicio, boolean usarManhattan, String nombre) {
        Nodo raiz = new Nodo(inicio);
        ArbolDeBusqueda buscador = new ArbolDeBusqueda(OBJETIVO, usarManhattan);

        long t0 = System.currentTimeMillis();
        Nodo solucion = buscador.buscar(raiz);
        long t1 = System.currentTimeMillis();

        if (solucion == null) return null;

        LinkedList<Nodo> camino = new LinkedList<>();
        for (Nodo n = solucion; n != null; n = n.getPadre())
            camino.addFirst(n);

        for (int i = 0; i < camino.size(); i++) {
            Nodo n = camino.get(i);
            System.out.println("Paso " + i + " (f=" + n.getF() + " g=" + n.getG() + " h=" + n.getH() + "):");
            Puzzle24.imprimirEstado(n.getEstado());
        }

        return new Resultado(nombre, buscador.getNodosExpandidos(), t1 - t0, solucion.getG());
    }
}

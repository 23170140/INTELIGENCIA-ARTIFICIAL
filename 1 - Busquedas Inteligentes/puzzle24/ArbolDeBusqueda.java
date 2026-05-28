package puzzle24;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


 // IDA* para el 24-Puzzle.
 
public class ArbolDeBusqueda {

    private static final int ENCONTRADO = -1;

    private final int[][] objetivo;
    private final boolean usarManhattan;
    private int nodosExpandidos;
    private Nodo solucionEncontrada;

    public ArbolDeBusqueda(int[][] objetivo, boolean usarManhattan) {
        this.objetivo = objetivo;
        this.usarManhattan = usarManhattan;
    }

    /** Ejecuta IDA* desde el nodo raiz. Retorna el nodo solucion o null. */
    public Nodo buscar(Nodo raiz) {
        raiz.setH(Puzzle24.calcularHeuristica(raiz.getEstado(), objetivo, usarManhattan));
        int umbral = raiz.getF();
        nodosExpandidos = 0;
        solucionEncontrada = null;

        while (true) {
            Set<String> camino = new HashSet<>();
            camino.add(raiz.estadoToString());

            int resultado = busquedaRecursiva(raiz, umbral, camino);

            if (resultado == ENCONTRADO) return solucionEncontrada;
            if (resultado == Integer.MAX_VALUE) return null;

            umbral = resultado;
        }
    }

    private int busquedaRecursiva(Nodo nodo, int umbral, Set<String> camino) {
        int f = nodo.getF();
        if (f > umbral) return f;

        if (Arrays.deepEquals(nodo.getEstado(), objetivo)) {
            solucionEncontrada = nodo;
            return ENCONTRADO;
        }

        int minUmbral = Integer.MAX_VALUE;
        nodosExpandidos++;

        for (Nodo hijo : nodo.generarSucesores()) {
            String estadoStr = hijo.estadoToString();
            if (camino.contains(estadoStr)) continue;

            hijo.setH(Puzzle24.calcularHeuristica(hijo.getEstado(), objetivo, usarManhattan));

            camino.add(estadoStr);
            int resultado = busquedaRecursiva(hijo, umbral, camino);
            camino.remove(estadoStr);

            if (resultado == ENCONTRADO) return ENCONTRADO;
            if (resultado < minUmbral) minUmbral = resultado;
        }

        return minUmbral;
    }

    public int getNodosExpandidos() {
        return nodosExpandidos;
    }
}

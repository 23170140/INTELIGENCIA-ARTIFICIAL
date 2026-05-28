package puzzle24;


 //Heuristicas para el 24-Puzzle (5x5).

public class Puzzle24 {

    private static final int N = 5;

    //Distancia Manhattan
    public static int calcularManhattan(int[][] estado, int[][] objetivo) {
        int distancia = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int valor = estado[i][j];
                if (valor != 0) {
                    int[] pos = encontrarValor(objetivo, valor);
                    distancia += Math.abs(i - pos[0]) + Math.abs(j - pos[1]);
                }
            }
        }
        return distancia;
    }

    /**
     * Conflicto Lineal: Manhattan + 2 por cada par de fichas en su fila/columna
     * correcta pero en orden inverso.
     */
    public static int calcularConflictoLineal(int[][] estado, int[][] objetivo) {
        int manhattan = calcularManhattan(estado, objetivo);
        int conflictos = 0;

        // Conflictos en filas
        for (int fila = 0; fila < N; fila++) {
            for (int j = 0; j < N; j++) {
                int vJ = estado[fila][j];
                if (vJ == 0) continue;
                int[] posJ = encontrarValor(objetivo, vJ);
                if (posJ[0] != fila) continue;

                for (int k = j + 1; k < N; k++) {
                    int vK = estado[fila][k];
                    if (vK == 0) continue;
                    int[] posK = encontrarValor(objetivo, vK);
                    if (posK[0] != fila) continue;
                    if (posJ[1] > posK[1]) conflictos++;
                }
            }
        }

        // Conflictos en columnas
        for (int col = 0; col < N; col++) {
            for (int i = 0; i < N; i++) {
                int vI = estado[i][col];
                if (vI == 0) continue;
                int[] posI = encontrarValor(objetivo, vI);
                if (posI[1] != col) continue;

                for (int k = i + 1; k < N; k++) {
                    int vK = estado[k][col];
                    if (vK == 0) continue;
                    int[] posK = encontrarValor(objetivo, vK);
                    if (posK[1] != col) continue;
                    if (posI[0] > posK[0]) conflictos++;
                }
            }
        }

        return manhattan + 2 * conflictos;
    }

    /** Calcula la heuristica segun la opcion: true = Manhattan, false = Conflicto Lineal. */
    public static int calcularHeuristica(int[][] estado, int[][] objetivo, boolean usarManhattan) {
        return usarManhattan
                ? calcularManhattan(estado, objetivo)
                : calcularConflictoLineal(estado, objetivo);
    }

    private static int[] encontrarValor(int[][] estado, int valor) {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (estado[i][j] == valor) return new int[]{i, j};
        return new int[]{0, 0};
    }

    /** Imprime el tablero formateado. */
    public static void imprimirEstado(int[][] estado) {
        for (int[] fila : estado) {
            for (int val : fila) System.out.printf("%3d", val);
            System.out.println();
        }
        System.out.println();
    }
}

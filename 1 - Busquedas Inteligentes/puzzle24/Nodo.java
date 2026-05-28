package puzzle24;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private final int[][] estado;
    private Nodo padre;
    private int g; // costo acumulado (profundidad)
    private int h; // heuristica
    private int f; // f = g + h

    public Nodo(int[][] estado) {
        this.estado = estado;
        this.padre = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    public Nodo(int[][] estado, Nodo padre) {
        this.estado = estado;
        this.padre = padre;
        this.g = padre.g + 1;
        this.h = 0;
        this.f = 0;
    }

    // Getters
    public int[][] getEstado() {
         return estado; 
        }
    public Nodo getPadre() { 
        return padre; 
    }
    public int getG() { 
        return g; 
    }
    public int getH() { 
        return h; 
    }
    public int getF() { 
        return f; 
    }

    public void setH(int h) { 
        this.h = h; 
        this.f = this.g + this.h;
     }

    public String estadoToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] fila : estado) {
            for (int val : fila) {
                sb.append(val).append(",");
            }
        }
        return sb.toString();
    }

    // Genera sucesores moviendo la casilla vacia (arriba, abajo, izquierda, derecha). 
    public List<Nodo> generarSucesores() {
        List<Nodo> sucesores = new ArrayList<>();
        int[] pos = encontrarCero();
        int fila = pos[0], col = pos[1];
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] d : dirs) {
            int nf = fila + d[0], nc = col + d[1];
            if (nf >= 0 && nf < estado.length && nc >= 0 && nc < estado[0].length) {
                int[][] nuevo = clonar(estado);
                nuevo[fila][col] = nuevo[nf][nc];
                nuevo[nf][nc] = 0;
                sucesores.add(new Nodo(nuevo, this));
            }
        }
        return sucesores;
    }

    private int[] encontrarCero() {
        for (int i = 0; i < estado.length; i++)
            for (int j = 0; j < estado[i].length; j++)
                if (estado[i][j] == 0) return new int[]{i, j};
        return new int[]{0, 0};
    }

    private int[][] clonar(int[][] original) {
        int[][] copia = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++)
            copia[i] = original[i].clone();
        return copia;
    }
}

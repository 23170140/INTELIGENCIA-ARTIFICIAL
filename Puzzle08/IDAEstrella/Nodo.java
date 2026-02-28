package IDAEstrella;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo> {
    int[][] estado;
    ArrayList<Nodo> hijos = new ArrayList<Nodo>();
    Nodo padre;
    int g; // costo acumulado (profundidad)
    int h; // heuristica (distancia Manhattan)
    int f; // f = g + h

    public Nodo(int[][] estado) {
        this.estado = estado;
        hijos = null;
        padre = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
        if (hijos != null) {
            for (Nodo hijo : hijos) {
                hijo.setPadre(this);
            }
        }
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public int getG() { return g; }
    public void setG(int g) { this.g = g; }
    public int getH() { return h; }
    public void setH(int h) { this.h = h; }
    public int getF() { return f; }
    public void setF(int f) { this.f = f; }

    public void calcularF() {
        this.f = this.g + this.h;
    }

    /**
     * Convierte el estado a String para comparaciones rapidas
     */
    public String estadoToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] fila : estado) {
            for (int val : fila) {
                sb.append(val).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Genera todos los sucesores (movimientos posibles del cero) en tablero 5x5
     */
    public List<Nodo> generarSucesores() {
        List<Nodo> sucesores = new ArrayList<>();
        int[] pcero = encontrarCero();
        int fila = pcero[0];
        int col = pcero[1];

        // Mover arriba
        if (fila > 0) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila - 1][col];
            nuevo[fila - 1][col] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setG(this.g + 1);
            sucesores.add(hijo);
        }
        // Mover abajo
        if (fila < estado.length - 1) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila + 1][col];
            nuevo[fila + 1][col] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setG(this.g + 1);
            sucesores.add(hijo);
        }
        // Mover izquierda
        if (col > 0) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila][col - 1];
            nuevo[fila][col - 1] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setG(this.g + 1);
            sucesores.add(hijo);
        }
        // Mover derecha
        if (col < estado[0].length - 1) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila][col + 1];
            nuevo[fila][col + 1] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setG(this.g + 1);
            sucesores.add(hijo);
        }
        return sucesores;
    }

    private int[] encontrarCero() {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                if (estado[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    private int[][] clonar(int[][] original) {
        int[][] nuevo = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                nuevo[i][j] = original[i][j];
            }
        }
        return nuevo;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.f, otro.f);
    }
}

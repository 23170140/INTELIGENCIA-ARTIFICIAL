package puzzle8_menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Nodo {
    int [][] estado;
    ArrayList<Nodo> hijos = new ArrayList<Nodo>();
    Nodo padre;
    int nivel;
    int costo;

    public Nodo(int[][] estado) {
        this.estado = estado;
        hijos = null;
        padre = null;
        nivel = 0;
        costo = 0;
    }

    public int [][] getEstado() {
        return estado;
    }
    public void setEstado(int [][] estado) {
        this.estado = estado;
    }
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
        if(hijos != null) {
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
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getCosto() {
        return costo;
    }
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Convierte el estado a String para usar en HashSet
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
     * Genera todos los sucesores (movimientos posibles del cero)
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
            hijo.setNivel(this.nivel + 1);
            sucesores.add(hijo);
        }
        // Mover abajo
        if (fila < estado.length - 1) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila + 1][col];
            nuevo[fila + 1][col] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setNivel(this.nivel + 1);
            sucesores.add(hijo);
        }
        // Mover izquierda
        if (col > 0) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila][col - 1];
            nuevo[fila][col - 1] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setNivel(this.nivel + 1);
            sucesores.add(hijo);
        }
        // Mover derecha
        if (col < estado[0].length - 1) {
            int[][] nuevo = clonar(estado);
            nuevo[fila][col] = nuevo[fila][col + 1];
            nuevo[fila][col + 1] = 0;
            Nodo hijo = new Nodo(nuevo);
            hijo.setPadre(this);
            hijo.setNivel(this.nivel + 1);
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
}

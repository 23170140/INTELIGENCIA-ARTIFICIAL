package AEstrella;
import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
    int [][] estado;
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

    public int getG() { return g; }
    public void setG(int g) { this.g = g; }
    public int getH() { return h; }
    public void setH(int h) { this.h = h; }
    public int getF() { return f; }
    public void setF(int f) { this.f = f; }

    public void calcularF() {
        this.f = this.g + this.h;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.f, otro.f);
    }
}

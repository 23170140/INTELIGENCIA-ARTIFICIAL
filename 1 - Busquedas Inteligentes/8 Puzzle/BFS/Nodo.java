package BFS;
import java.util.ArrayList;

public class Nodo {
    int [][] estado;
    ArrayList<Nodo> hijos = new ArrayList<Nodo>();
    Nodo padre;
    
    public Nodo(int[][] estado) {
        this.estado = estado;
        hijos = null;
        padre = null;
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

}

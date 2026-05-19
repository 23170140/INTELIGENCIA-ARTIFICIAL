package Busqueda;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    String estado;
    Nodo padre;
    int nivel;

    // Constructor raíz
    public Nodo(String estado) {
        this.estado = estado;
        this.padre = null;
        this.nivel = 0;
    }

    // Constructor para los hijos 
    public Nodo(String estado, Nodo padre) {
        this.estado = estado;
        this.padre = padre;
        this.nivel = padre.nivel + 1;
    }

    
    public static List<String> getSucesores(String state) {
        List<String> sucesores = new ArrayList<>();
        
        // Buscamos el indice del espacio vacio
        int index = state.indexOf(" "); 

        
        switch (index) {
            case 0: // Arriba a la izquierda
                sucesores.add(intercambiaCaracteres(state, 0, 1)); 
                sucesores.add(intercambiaCaracteres(state, 0, 3));
                break;
            case 1: // Arriba en el centro
                sucesores.add(intercambiaCaracteres(state, 1, 0));
                sucesores.add(intercambiaCaracteres(state, 1, 2));
                sucesores.add(intercambiaCaracteres(state, 1, 4));
                break;
            case 2: // Arriba a la derecha
                sucesores.add(intercambiaCaracteres(state, 2, 1));
                sucesores.add(intercambiaCaracteres(state, 2, 5));
                break;
            case 3: // Medio a la izquierda
                sucesores.add(intercambiaCaracteres(state, 3, 0));
                sucesores.add(intercambiaCaracteres(state, 3, 4));
                sucesores.add(intercambiaCaracteres(state, 3, 6));
                break;
            case 4: // Centro
                sucesores.add(intercambiaCaracteres(state, 4, 1)); 
                sucesores.add(intercambiaCaracteres(state, 4, 3)); 
                sucesores.add(intercambiaCaracteres(state, 4, 5)); 
                sucesores.add(intercambiaCaracteres(state, 4, 7)); 
                break;
            case 5: // Medio a la derecha
                sucesores.add(intercambiaCaracteres(state, 5, 2));
                sucesores.add(intercambiaCaracteres(state, 5, 4));
                sucesores.add(intercambiaCaracteres(state, 5, 8));
                break;
            case 6: // Abajo a la izquierda
                sucesores.add(intercambiaCaracteres(state, 6, 3));
                sucesores.add(intercambiaCaracteres(state, 6, 7));
                break;
            case 7: // Abajo en el centro
                sucesores.add(intercambiaCaracteres(state, 7, 4));
                sucesores.add(intercambiaCaracteres(state, 7, 6));
                sucesores.add(intercambiaCaracteres(state, 7, 8));
                break;
            case 8: // Abajo a la derecha
                sucesores.add(intercambiaCaracteres(state, 8, 5));
                sucesores.add(intercambiaCaracteres(state, 8, 7));
                break;
        }
        return sucesores;
    }

    // Metodo para intercambiar caracteres
    private static String intercambiaCaracteres(String state, int i, int j) {
        char[] chars = state.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
}
package Busqueda;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ArbolDeBusqueda {
    Nodo raiz;

    public ArbolDeBusqueda(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo busquedaPrimeroAnchura(String estadoObjetivo) {
        if (raiz == null) return null;

        HashSet<String> visitados = new HashSet<>(); // Nos sirve para evitar regresarnos a un nodo ya visitado
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);
        
        // Contador de nodos explorados
        int nodosExplorados = 0; 

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            nodosExplorados++;

            // Test Objetivo
            if (actual.estado.equals(estadoObjetivo)) {
                System.out.println("\nSe exploraron: " + nodosExplorados + " nodos.");
                return actual;
            }

            List<String> sucesores = Nodo.getSucesores(actual.estado);

            for (String estadoSucesor : sucesores) {
                if (!visitados.contains(estadoSucesor)) {
                    visitados.add(estadoSucesor);
                    cola.add(new Nodo(estadoSucesor, actual));
                }
            }
        }

        // Si la cola se vacia y sale del while, significa que se agoto todas las jugadas posibles
        // y no se encontro la solucion
        System.out.println("\nNo se encontro la busqueda. Nodos explorados: " + nodosExplorados);
        return null; 
    }

    public Nodo busquedaProfundidad(String estadoObjetivo) {
        if (raiz == null) return null;

        HashSet<String> visitados = new HashSet<>(); 
        Stack<Nodo> pila = new Stack<>(); // Utilizamos una Pila en lugar de una Cola

        pila.push(raiz);
        
        int nodosExplorados = 0; 

        while (!pila.isEmpty()) {
            // Sacamos el ultimo nodo que entro
            Nodo actual = pila.pop();
            
            
            if (visitados.contains(actual.estado)) {
                continue;
            }
            visitados.add(actual.estado);
            
            nodosExplorados++;

            // Test Objetivo
            if (actual.estado.equals(estadoObjetivo)) {
                System.out.println("\nSe exploraron: " + nodosExplorados + " nodos");
                return actual;
            }

            List<String> sucesores = Nodo.getSucesores(actual.estado);

            // Agregamos los sucesores a la pila
            for (String estadoSucesor : sucesores) {
                if (!visitados.contains(estadoSucesor)) {
                    pila.push(new Nodo(estadoSucesor, actual));
                }
            }
        }

        System.out.println("\nNo se encontro la solucion. Nodos explorados: " + nodosExplorados);
        return null; 
    }

    public Nodo busquedaCostoUniforme(String estadoObjetivo) {
        if (raiz == null) return null;

        HashSet<String> visitados = new HashSet<>(); 
        
        // La Cola de Prioridad ordenara los nodos basándose en su nivel nuestro costo actual
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(Comparator.comparingInt(n -> n.nivel));

        frontera.add(raiz);
        int nodosExplorados = 0; 

        while (!frontera.isEmpty()) {
            // Sacamos el nodo con el menor costo acumulado
            Nodo actual = frontera.poll();
            
            // Si ya evaluamos este estado con un costo menor o igual, lo saltamos
            if (visitados.contains(actual.estado)) {
                continue;
            }
            visitados.add(actual.estado);
            
            nodosExplorados++;

            // Se hace la prueba al sacar el nodo, no al generarlo
            if (actual.estado.equals(estadoObjetivo)) {
                System.out.println("\nSe exploraron: " + nodosExplorados + " nodos");
                return actual;
            }

            List<String> sucesores = Nodo.getSucesores(actual.estado);

            for (String estadoSucesor : sucesores) {
                if (!visitados.contains(estadoSucesor)) {
                    // El constructor de Nodo ya se encarga de sumar +1 al nivel (costo) del padre
                    frontera.add(new Nodo(estadoSucesor, actual));
                }
            }
        }

        System.out.println("\nNo se encontro la solucion. Nodos explorados: " + nodosExplorados);
        return null; 
    }

    public void imprimirCamino(Nodo nodoFinal) {
        if (nodoFinal == null) {
            System.out.println("Resultado: No se encontro solucion.\n");
            return;
        }

        LinkedList<String> camino = new LinkedList<>();
        Nodo nodoActual = nodoFinal;

        while (nodoActual != null) {
            camino.addFirst(nodoActual.estado);
            nodoActual = nodoActual.padre;
        }

        System.out.println("Se realizaron " + (camino.size() - 1) + " movimientos");
        for (String paso : camino) {
            imprimirTablero(paso);
        }
    }

    private void imprimirTablero(String estado) {
        System.out.println(estado.substring(0, 3));
        System.out.println(estado.substring(3, 6));
        System.out.println(estado.substring(6, 9));
        System.out.println("-----");
    }
}
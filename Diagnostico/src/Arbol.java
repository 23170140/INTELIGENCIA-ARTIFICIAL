package com.mycompany.examendiagnosticoia;

public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public Nodo buscarNodo(String nombre) {
        return buscarRecursiva(raiz, nombre);
    }

    private Nodo buscarRecursiva(Nodo actual, String nombre) {
        if (actual == null || actual.nombre.equals(nombre)) {
            return actual;
        }

        if (nombre.compareTo(actual.nombre) < 0) {
            return buscarRecursiva(actual.izquierdo, nombre);
        }

        return buscarRecursiva(actual.derecho, nombre);
    }

    public void insertar(String nombre) {
        raiz = insertarRecursivo(raiz, nombre);
    }

    private Nodo insertarRecursivo(Nodo actual, String nombre) {
        if (actual == null) {
            return new Nodo(nombre);
        }
        if (nombre.compareTo(actual.nombre) < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, nombre);
        } else if (nombre.compareTo(actual.nombre) > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, nombre);
        }
        return actual;
    }
    
    public void imprimirArbol() {
        if (vacio()) {
            System.out.println("El arbol está vacio :((");
        } else {
            System.out.println("Estructura del arbol PreOrder:");
            OrdenarEnPreOrder(raiz, "");
        }
    }

    private void OrdenarEnPreOrder(Nodo actual, String prefijo) {
        if (actual != null) {
            System.out.println(prefijo + actual.nombre);
            OrdenarEnPreOrder(actual.izquierdo, prefijo + "  ");
            OrdenarEnPreOrder(actual.derecho, prefijo + "  ");
        }
    }
    
}

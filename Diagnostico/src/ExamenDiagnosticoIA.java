
package com.mycompany.examendiagnosticoia;

public class ExamenDiagnosticoIA {

    public static void main(String[] args) {
        Arbol miArbol = new Arbol();

        miArbol.insertar("Carlos");    
        miArbol.insertar("Norberto");  
        miArbol.insertar("Josefina");    
        miArbol.insertar("Roldan");   
        miArbol.insertar("Jesus");   

        miArbol.imprimirArbol();

        System.out.println("\n --Busqueda--");
        String nombreABuscar = "Linda";
        Nodo resultado = miArbol.buscarNodo(nombreABuscar);
        
        if (resultado != null) {
            System.out.println("Si se encontro: " + resultado.nombre);
        } else {
            System.out.println("No se encontro a: " + nombreABuscar);
        }

    }
    }


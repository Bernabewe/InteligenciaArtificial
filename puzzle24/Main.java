package puzzle24;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Metricas {
        String nombre;
        int nodosExpandidos;
        long tiempo;
        int pasos;

        Metricas(String nombre, int nodosExpandidos, long tiempo, int pasos) {
            this.nombre = nombre;
            this.nodosExpandidos = nodosExpandidos;
            this.tiempo = tiempo;
            this.pasos = pasos;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String estadoObjetivo = "ABCDEFGHIJKLMNOPQRSTUVWX ";
        System.out.println("Ingrese el estado inicial: ");
        String estadoInicial = sc.nextLine(); //"ABCDEFGHIJKLMNO PQRSTUVWX" 31 pasos
        
        List<Metricas> resultadosTabla = new ArrayList<>();

        // 1. PRUEBA: IDA* MANHATTAN
        ejecutarAlgoritmo("IDA* Manhattan", estadoInicial, estadoObjetivo, resultadosTabla);

        // 2. PRUEBA: IDA* CONFLICTO LINEAL
        ejecutarAlgoritmo("IDA* ConflictoLineal", estadoInicial, estadoObjetivo, resultadosTabla);

        // --- TABLA COMPARATIVA FINAL ---
        imprimirTablaFinal(resultadosTabla);
        

        /*
        //5 pasos, 15 pasos, 25 pasos
        String[] estadosIniciales = {"ABCDEFGHIJKLMNOPQWRTU VSX", "ABCJDFG IEKLHRNPQSMOUVWXT", "ABCDEFL IJHNGWOKQMTXPUVSR"};
        String estadoObjetivo = "ABCDEFGHIJKLMNOPQRSTUVWX ";
        List<Metricas> resultadosTabla;
        for(int i = 0; i < 3; i++){
            resultadosTabla = new ArrayList<>();
            ejecutarAlgoritmo("IDA* Manhattan", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            ejecutarAlgoritmo("IDA* ConflictoLineal", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            imprimirTablaFinal(resultadosTabla);
        }
        */
    }

    public static void ejecutarAlgoritmo(String metodo, String inicio, String objetivo, List<Metricas> tabla) {
        Nodo nodoRaiz = new Nodo(inicio, null);
        ArbolDeBusqueda arbol = new ArbolDeBusqueda(nodoRaiz);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EJECUTANDO: " + metodo);
        System.out.println("=".repeat(50));
        
        long startTime = System.currentTimeMillis();
        Nodo solucion = null;

        // Selección del algoritmo
        switch (metodo) {
            case "IDA* Manhattan":
                solucion = arbol.busquedaIDAEstrella(objetivo, "Manhattan");
                break;
            case "IDA* ConflictoLineal":
                solucion = arbol.busquedaIDAEstrella(objetivo, "ConflictoLineal");
                break;
        }
        long endTime = System.currentTimeMillis();
        long tiempoTotal = endTime - startTime;

        // Imprimir el camino y detalles inmediatos
        procesarResultado(solucion, tiempoTotal);

        // Guardar en la lista para la tabla comparativa
        int pasos = (solucion != null) ? solucion.getNivel() : 0;
        tabla.add(new Metricas(metodo, arbol.getNodosExpandidos(), tiempoTotal, pasos));
    }

    public static void procesarResultado(Nodo solucion, long tiempo) {
        System.out.println("Tiempo de ejecución: " + tiempo + " ms");
        if (solucion != null) {
            System.out.println("¡Solución encontrada en " + solucion.getNivel() + " pasos!");
            System.out.println("Camino detallado:");
            imprimirCamino(solucion);
        } else {
            System.out.println("No se pudo encontrar una solución.");
        }
    }

    // --- MÉTODOS AUXILIARES DE VISUALIZACIÓN ---

    public static void imprimirCamino(Nodo nodo) {
        if (nodo == null) return;
        imprimirCamino(nodo.getPadre()); // Recursión para ir desde el inicio
        System.out.println("Paso " + nodo.getNivel() + ":");
        imprimirTablero(nodo.getEstado());
        System.out.println("-----------");
    }

    public static void imprimirTablero(String estado) {
        for (int i = 0; i < estado.length(); i++) {
            if (i % 5 == 0 && i != 0) System.out.println();
            char p = estado.charAt(i);
            System.out.print("[" + (p == ' ' ? " " : p) + "] ");
        }
        System.out.println();
    }

    public static void imprimirTablaFinal(List<Metricas> lista) {
        System.out.println("\n\n" + "=".repeat(95));
        System.out.println("                      RESUMEN DE RESULTADOS COMPARATIVOS");
        System.out.println("=".repeat(95));
        System.out.printf("%-20s | %-20s | %-15s | %-15s%n", 
                          "ALGORITMO", "NODOS EXPANDIDOS", "TIEMPO (ms)", "PASOS (NIVEL)");
        System.out.println("-".repeat(95));

        for (Metricas m : lista) {
            System.out.printf("%-20s | %-20d | %-15d | %-15d%n", 
                              m.nombre, m.nodosExpandidos, m.tiempo, m.pasos);
        }
        System.out.println("=".repeat(95));
    }
}
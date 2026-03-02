package puzzle8;

import java.util.ArrayList;
import java.util.List;

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
        
        String estadoObjetivo = "12345678 ";
        String estadoInicial = "123456 78";
        
        List<Metricas> resultadosTabla = new ArrayList<>();

        // 1. PRUEBA: Anchura
        ejecutarAlgoritmo("Anchura", estadoInicial, estadoObjetivo, resultadosTabla);

        // 2. PRUEBA: Profundidad
        ejecutarAlgoritmo("Profundidad", estadoInicial, estadoObjetivo, resultadosTabla);

        // 3. PRUEBA: COSTO UNIFORME (CostoUniforme)
        ejecutarAlgoritmo("CostoUniforme", estadoInicial, estadoObjetivo, resultadosTabla);

        // 4. PRUEBA: A* MANHATTAN
        ejecutarAlgoritmo("A* Manhattan", estadoInicial, estadoObjetivo, resultadosTabla);

        // 5. PRUEBA: A* PERSONALIZADA
        ejecutarAlgoritmo("A* Personalizada", estadoInicial, estadoObjetivo, resultadosTabla);

        // --- TABLA COMPARATIVA FINAL ---
        imprimirTablaFinal(resultadosTabla);
        

        /*
        // 6 pasos, 14 pasos, 22 pasos
        String[] estadosIniciales = {"4125 3786", " 52743816", " 72456831"};
        String estadoObjetivo = "12345678 ";
        List<Metricas> resultadosTabla;
        for(int i = 0; i < 3; i++){
            resultadosTabla = new ArrayList<>();
            ejecutarAlgoritmo("Anchura", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            ejecutarAlgoritmo("Profundidad", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            ejecutarAlgoritmo("CostoUniforme", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            ejecutarAlgoritmo("A* Manhattan", estadosIniciales[i], estadoObjetivo, resultadosTabla);
            ejecutarAlgoritmo("A* Personalizada", estadosIniciales[i], estadoObjetivo, resultadosTabla);
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
            case "Anchura":
                solucion = arbol.busquedaPrimeroAnchura(objetivo);
                break;
            case "Profundidad":
                solucion = arbol.busquedaPrimeroProfundidad(objetivo);
                break;
            case "CostoUniforme":
                solucion = arbol.busquedaPrimeroCostoUniforme(objetivo);
                break;
            case "A* Manhattan":
                solucion = arbol.busquedaAEstrella(objetivo, "Manhattan");
                break;
            case "A* Personalizada":
                solucion = arbol.busquedaAEstrella(objetivo, "Personalizada");
                break;
        }
        long endTime = System.currentTimeMillis();
        long tiempoTotal = endTime - startTime;

        // Imprimir el camino y detalles inmediatos
        procesarResultado(solucion, tiempoTotal);

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
        imprimirCamino(nodo.getPadre());
        System.out.println("Paso " + nodo.getNivel() + ":");
        imprimirTablero(nodo.getEstado());
        System.out.println("-----------");
    }

    public static void imprimirTablero(String estado) {
        for (int i = 0; i < estado.length(); i++) {
            if (i % 3 == 0 && i != 0) System.out.println();
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
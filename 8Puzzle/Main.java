public class Main {
    public static void main(String[] args) {
        
        String estadoInicial = "1238 4765"; //4321 8765
        String estadoObjetivo = "12 843765"; //12345678 

        Nodo raiz = new Nodo(estadoInicial, null);
        ArbolDeBusqueda arbol = new ArbolDeBusqueda(raiz);

        // ---------------------------------------------------------
        // 1. PRUEBA: BÚSQUEDA PRIMERO EN ANCHURA
        // ---------------------------------------------------------
        System.out.println("=========================================");
        System.out.println("=== BÚSQUEDA PRIMERO EN ANCHURA (BFS) ===");
        System.out.println("=========================================");
        long inicioAnchura = System.currentTimeMillis();
        Nodo solucionAnchura = arbol.busquedaPrimeroAnchura(estadoObjetivo);
        long finAnchura = System.currentTimeMillis();
        procesarResultado(solucionAnchura, finAnchura - inicioAnchura);

        // ---------------------------------------------------------
        // 2. PRUEBA: BÚSQUEDA PRIMERO EN PROFUNDIDAD
        // ---------------------------------------------------------
        System.out.println("\n=============================================");
        System.out.println("=== BÚSQUEDA PRIMERO EN PROFUNDIDAD (DFS) ===");
        System.out.println("=============================================");
        long inicioProfundidad = System.currentTimeMillis();
        Nodo solucionProfundidad = arbol.busquedaPrimeroProfundidad(estadoObjetivo);
        long finProfundidad = System.currentTimeMillis();
        procesarResultado(solucionProfundidad, finProfundidad - inicioProfundidad);

        // ---------------------------------------------------------
        // 3. PRUEBA: BÚSQUEDA DE COSTO UNIFORME
        // ---------------------------------------------------------
        System.out.println("\n==========================================");
        System.out.println("=== BÚSQUEDA DE COSTO UNIFORME (UCS)   ===");
        System.out.println("==========================================");
        long inicioCosto = System.currentTimeMillis();
        Nodo solucionCosto = arbol.busquedaPrimeroCostoUniforme(estadoObjetivo);
        long finCosto = System.currentTimeMillis();
        procesarResultado(solucionCosto, finCosto - inicioCosto);
    }

    // --- NUEVO MÉTODO AUXILIAR ---
    // Nos ayuda a no repetir la lógica de validación y cálculo en el main
    public static void procesarResultado(Nodo solucion, long tiempoEjecucion) {
        System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " ms");

        if (solucion != null) {
            System.out.println("Solución encontrada.");
            System.out.println("Estado final: " + solucion.getEstado());
            System.out.println("Nivel (Cantidad de pasos): " + solucion.getNivel());
            System.out.println("Camino recorrido:");
            imprimirCamino(solucion);
        } else {
            System.out.println("No se encontró solución.");
        }
    }

    // --- MÉTODOS ORIGINALES INTACTOS ---
    public static void imprimirCamino(Nodo nodo) {
        if (nodo == null) return;
        
        // Al ser recursivo, imprimirá desde la raíz hasta el objetivo
        imprimirCamino(nodo.getPadre());
        imprimirTablero(nodo.getEstado());
    }

    public static void imprimirTablero(String estado) {
        for (int i = 0; i < estado.length(); i++) {
            if (i % 3 == 0 && i != 0) System.out.println();
            System.out.print(" " + estado.charAt(i) + " ");
        }
        System.out.print("\n----------\n"); // Añadí un salto de línea extra para mayor claridad visual
    }
}
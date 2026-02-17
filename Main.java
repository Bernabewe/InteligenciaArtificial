
public class Main {
    public static void main(String[] args) {
        
        String estadoInicial = "1238 4765"; //4321 8765

        String estadoObjetivo = "12 843765"; //12345678 

        Nodo raiz = new Nodo(estadoInicial, null);

        ArbolDeBusqueda arbol = new ArbolDeBusqueda(raiz);

        long inicio = System.currentTimeMillis();

        Nodo solucion = arbol.busquedaPrimeroProfundidad(estadoObjetivo);

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");

        if (solucion != null) {
            System.out.println("Solucion encontrada");
            System.out.println("Estado final: " + solucion.getEstado());
            System.out.println("Nivel: " + solucion.getNivel());
            imprimirCamino(solucion);
        } else {
            System.out.println("No se encontro solucion");
        }
        
    }

    public static void imprimirCamino(Nodo nodo) {
        if (nodo == null) return;
        
        imprimirCamino(nodo.getPadre());
        imprimirTablero(nodo.getEstado());
    }

    public static void imprimirTablero(String estado) {
    for (int i = 0; i < estado.length(); i++) {

        if (i % 3 == 0) System.out.println();

        System.out.print(" " + estado.charAt(i) + " ");
    }
    System.out.print("\n----------");
}

}

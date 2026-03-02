package puzzle24;

import java.util.List;

public class ArbolDeBusqueda {

    final private Nodo raiz;
    private int nodosExpandidos;
    private Nodo solucionIDA;

    public ArbolDeBusqueda(Nodo raiz){
        this.raiz = raiz;
        this.nodosExpandidos = 0;
    }

    public int getNodosExpandidos() {
        return nodosExpandidos;
    }

    public void reiniciarNodosExpandidos() {
        this.nodosExpandidos = 0;
    }

    public Nodo busquedaIDAEstrella(String estadoObjetivo, String tipoHeuristica) {
        reiniciarNodosExpandidos();
        solucionIDA = null;

        if (raiz == null) return null;

        int umbral = calcularH(raiz.estado, estadoObjetivo, tipoHeuristica);

        while (true) {
            // Mandamos a buscar usando el método recursivo (ver abajo)
            int nuevoUmbral = buscarRecursivo(raiz, 0, umbral, estadoObjetivo, tipoHeuristica);

            if (nuevoUmbral == -1) {
                return solucionIDA;
            }
            
            // Si no lo encontró, actualizamos el límite para la siguiente vuelta
            umbral = nuevoUmbral;
        }
    }

    private int buscarRecursivo(Nodo actual, int g, int umbral, String estadoObjetivo, String tipoHeuristica) {
        nodosExpandidos++;

        int h = calcularH(actual.estado, estadoObjetivo, tipoHeuristica);
        int f = g + h;

        if (f > umbral) return f;

        if (actual.estado.equals(estadoObjetivo)) {
            solucionIDA = actual;
            return -1; // el -1 es para cuadno lleguemos al estado objetivo
        }

        int minimo = Integer.MAX_VALUE;

        // 4. Tu misma lógica para sacar sucesores
        List<Nodo> sucesores = Puzzle24.getSucesores(actual);

        for (Nodo s : sucesores) {
            // En lugar de HashSet, solo evitamos regresar al nodo padre inmediato
            if (actual.padre != null && s.estado.equals(actual.padre.estado)) {
                continue; 
            }

            // Configuramos el hijo (lo que antes hacías antes del pila.push)
            s.setNivel(g + 1);
            s.padre = actual;

            // ¡LA MAGIA! En lugar de meterlo a la pila, llamamos al mismo método
            int resultadoHijo = buscarRecursivo(s, g + 1, umbral, estadoObjetivo, tipoHeuristica);

            if (resultadoHijo == -1) {
                return -1; // Si el hijo encontró la meta, pasamos el aviso hacia arriba
            }
            
            // Si no encontró la meta, guardamos el costo más bajo por el que nos pasamos
            if (resultadoHijo < minimo) {
                minimo = resultadoHijo;
            }
        }
        
        return minimo;
    }

    // Metodo auxiliar para elegir la heuristica
    private int calcularH(String estado, String objetivo, String tipo) {
        if (tipo.equalsIgnoreCase("Manhattan")) {
            return Puzzle24.calcularManhattan(estado, objetivo);
        } else if (tipo.equalsIgnoreCase("ConflictoLineal")) {
            return Puzzle24.calcularConflictoLineal(estado, objetivo);
        }
        return 0; // Si no hay heuristica
    }
}

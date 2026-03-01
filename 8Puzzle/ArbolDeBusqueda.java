import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ArbolDeBusqueda {

    final private Nodo raiz;
    private int nodosExpandidos;

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

    public Nodo busquedaPrimeroAnchura(String estadoObjetivo){
        reiniciarNodosExpandidos();
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);
        
        while(!cola.isEmpty()){
            nodosExpandidos++;
            Nodo actual = cola.poll();

            if(actual.estado.equals(estadoObjetivo)) {
                return actual;
            }
            
            List<Nodo> sucesores = Puzzle8.getSucesores(actual);
            
            for(Nodo s : sucesores) {
                if(!visitados.contains(s.estado)) {
                    s.setNivel(actual.getNivel() + 1);
                    cola.add(s);
                    visitados.add(s.estado);
                }
            }
        }

        return null;
    }

    public Nodo busquedaPrimeroProfundidad(String estadoObjetivo){
        reiniciarNodosExpandidos();
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Stack<Nodo> pila = new Stack<>();

        pila.push(raiz);
        visitados.add(raiz.estado);
        
        while(!pila.isEmpty()){
            nodosExpandidos++;
            Nodo actual = pila.pop();

            if(actual.estado.equals(estadoObjetivo)) {
                return actual;
            }
            
            List<Nodo> sucesores = Puzzle8.getSucesores(actual);
            
            for(Nodo s : sucesores) {
                if(!visitados.contains(s.estado)) {
                    s.setNivel(actual.getNivel() + 1);
                    pila.push(s);
                    visitados.add(s.estado);
                }
            }
        }

        return null;
    }

    public Nodo busquedaPrimeroCostoUniforme(String estadoObjetivo){
        reiniciarNodosExpandidos();
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new PriorityQueue<>(new NodePriorityComparator());

        cola.add(raiz);
        visitados.add(raiz.estado);
        
        while(!cola.isEmpty()){
            nodosExpandidos++;
            Nodo actual = cola.poll();

            if(actual.estado.equals(estadoObjetivo)) {
                return actual;
            }
            
            List<Nodo> sucesores = Puzzle8.getSucesores(actual);
            
            for(Nodo s : sucesores) {
                if(!visitados.contains(s.estado)) {
                    s.setNivel(actual.getNivel() + 1);
                    s.setCosto(actual.getCosto() + 1);
                    cola.add(s);
                    visitados.add(s.estado);
                }
            }
        }

        return null;
    }

    public Nodo busquedaAEstrella(String estadoObjetivo, String tipoHeuristica) {
        reiniciarNodosExpandidos();
        if (raiz == null) return null;

        // Usamos el comparador especial para A* que creamos antes
        PriorityQueue<Nodo> cola = new PriorityQueue<>(new NodePriorityComparatorAStar());
        HashSet<String> visitados = new HashSet<>();

        // Calculamos la heuristica inicial para la raiz
        raiz.setHeuristica(calcularH(raiz.estado, estadoObjetivo, tipoHeuristica));
        
        cola.add(raiz);
        visitados.add(raiz.estado);

        while (!cola.isEmpty()) {
            nodosExpandidos++;
            Nodo actual = cola.poll();

            if (actual.estado.equals(estadoObjetivo)) {
                return actual;
            }

            List<Nodo> sucesores = Puzzle8.getSucesores(actual);

            for (Nodo s : sucesores) {
                if (!visitados.contains(s.estado)) {
                    // g(n): Costo acumulado (nivel)
                    s.setNivel(actual.getNivel() + 1);
                    s.setCosto(actual.getCosto() + 1);
                    
                    // h(n): Heuristica elegida
                    s.setHeuristica(calcularH(s.estado, estadoObjetivo, tipoHeuristica));
                    
                    cola.add(s);
                    visitados.add(s.estado);
                }
            }
        }
        return null;
    }

    // Metodo auxiliar para elegir la heuristica
    private int calcularH(String estado, String objetivo, String tipo) {
        if (tipo.equalsIgnoreCase("Manhattan")) {
            return Puzzle8.calcularManhattan(estado, objetivo);
        } else if (tipo.equalsIgnoreCase("Personalizada")) {
            return Puzzle8.heuristicaPersonalizada(estado, objetivo);
        }
        return 0; // Si no hay heuristica
    }
}

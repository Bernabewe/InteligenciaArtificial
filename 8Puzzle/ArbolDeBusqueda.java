import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ArbolDeBusqueda {

    final private Nodo raiz;

    public ArbolDeBusqueda(Nodo raiz){
        this.raiz = raiz;
    }

    public Nodo busquedaPrimeroAnchura(String estadoObjetivo){
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);
        
        while(!cola.isEmpty()){
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

    public Nodo busquedaPrimeroCostoUniforme(String estadoObjetivo){
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new PriorityQueue<>(new NodePriorityComparator());

        cola.add(raiz);
        visitados.add(raiz.estado);
        
        while(!cola.isEmpty()){
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

    public Nodo busquedaPrimeroProfundidad(String estadoObjetivo){
        if(raiz == null) return null;

        HashSet<String> visitados = new HashSet<>();
        Stack<Nodo> pila = new Stack<>();

        pila.push(raiz);
        visitados.add(raiz.estado);
        
        while(!pila.isEmpty()){
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
}

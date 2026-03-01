import java.util.Comparator;

public class NodePriorityComparatorAStar implements Comparator<Nodo> {
    @Override
    public int compare(Nodo x, Nodo y) {
        // Comparamos el valor F = G + H
        return Integer.compare(x.getF(), y.getF());
    }
}
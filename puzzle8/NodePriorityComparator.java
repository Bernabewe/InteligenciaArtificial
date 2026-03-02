package puzzle8;

import java.util.Comparator;

public class NodePriorityComparator implements Comparator<Nodo> {
 
    @Override
    public int compare(Nodo x, Nodo y) {
        if (x.getCosto() < y.getCosto()) {
            return -1;
        }
        if (x.getCosto() > y.getCosto()) {
            return 1;
        }
        return 0;
    }
}
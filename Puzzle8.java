import java.util.ArrayList;
import java.util.List;

public class Puzzle8 {

    public static List<Nodo> getSucesores(Nodo actual) {
        List<Nodo> sucesores = new ArrayList<>();
        String estadoActual = actual.getEstado();
        int indice = estadoActual.indexOf(" ");

        switch (indice) {
            case 0:
                sucesores.add(new Nodo(intercambiar(estadoActual, 0, 1), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 0, 3), actual));
                break;
            case 1:
                sucesores.add(new Nodo(intercambiar(estadoActual, 1, 0), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 1, 2), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 1, 4), actual));
                break;
            case 2:
                sucesores.add(new Nodo(intercambiar(estadoActual, 2, 1), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 2, 5), actual));
                break;
            case 3:
                sucesores.add(new Nodo(intercambiar(estadoActual, 3, 0), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 3, 4), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 3, 6), actual));
                break;
            case 4:
                sucesores.add(new Nodo(intercambiar(estadoActual, 4, 1), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 4, 3), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 4, 5), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 4, 7), actual));
                break;
            case 5:
                sucesores.add(new Nodo(intercambiar(estadoActual, 5, 2), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 5, 4), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 5, 8), actual));
                break;
            case 6:
                sucesores.add(new Nodo(intercambiar(estadoActual, 6, 3), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 6, 7), actual));
                break;
            case 7:
                sucesores.add(new Nodo(intercambiar(estadoActual, 7, 4), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 7, 6), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 7, 8), actual));
                break;
            case 8:
                sucesores.add(new Nodo(intercambiar(estadoActual, 8, 5), actual));
                sucesores.add(new Nodo(intercambiar(estadoActual, 8, 7), actual));
                break;
        }

        return sucesores;
    }

    private static String intercambiar(String estado, int i, int j) {
        char[] caracteres = estado.toCharArray();
        char temp = caracteres[i];
        caracteres[i] = caracteres[j];
        caracteres[j] = temp;
        return new String(caracteres);
    }
}
package puzzle8;

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

    public static int calcularManhattan(String estadoActual, String estadoObjetivo) {
        int distanciaTotal = 0;
        for (int i = 0; i < estadoActual.length(); i++) {
            char pieza = estadoActual.charAt(i);
            if (pieza != ' ') { // No contamos el espacio vacio
                int posActual = i;
                int posObjetivo = estadoObjetivo.indexOf(pieza);
                
                // Convertimos posicion lineal a coordenadas (x, y)
                int filaActual = posActual / 3;
                int colActual = posActual % 3;
                int filaObjetivo = posObjetivo / 3;
                int colObjetivo = posObjetivo % 3;
                
                distanciaTotal += Math.abs(filaActual - filaObjetivo) + Math.abs(colActual - colObjetivo);
            }
        }
        return distanciaTotal;
    }

    public static int heuristicaPersonalizada(String estadoActual, String estadoObjetivo) {
        // Calcula la distancia de la posicion actual a la objetivo con el Teorema de Pitagoras
        // Esta funcion es mas optimista, por lo que tiende equivocarse mas
        double h = 0;
        for (int i = 0; i < estadoActual.length(); i++) {
            char pieza = estadoActual.charAt(i);
            
            if (pieza != ' ') {
                int posActual = i;
                int posObjetivo = estadoObjetivo.indexOf(pieza);
                
                int x1 = posActual % 3;
                int y1 = posActual / 3;
                int x2 = posObjetivo % 3;
                int y2 = posObjetivo / 3;
                
                h += Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            }
        }
        // Retornamos el entero para que sea compatible con la estructura de costos
        return (int) h;
    }
}
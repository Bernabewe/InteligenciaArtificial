package puzzle24;

import java.util.ArrayList;
import java.util.List;

public class Puzzle24 {

    public static List<Nodo> getSucesores(Nodo actual) {
        List<Nodo> sucesores = new ArrayList<>();
        String estadoActual = actual.getEstado();
        int ancho = 5; // Esto porque es un 5x5
        int i = estadoActual.indexOf(" ");
        int fila = i / ancho;
        int col = i % ancho;

        // arriba
        if (fila > 0) {
            sucesores.add(new Nodo(intercambiar(estadoActual, i, i - ancho), actual));
        }
        // abajo
        if (fila < ancho - 1) {
            sucesores.add(new Nodo(intercambiar(estadoActual, i, i + ancho), actual));
        }
        // izquierda
        if (col > 0) {
            sucesores.add(new Nodo(intercambiar(estadoActual, i, i - 1), actual));
        }
        // derecha
        if (col < ancho - 1) {
            sucesores.add(new Nodo(intercambiar(estadoActual, i, i + 1), actual));
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
            if (pieza != ' ') {
                int posActual = i;
                int posObjetivo = estadoObjetivo.indexOf(pieza);
                
                // Convertimos posicion lineal a coordenadas (x, y)
                int filaActual = posActual / 5;
                int colActual = posActual % 5;
                int filaObjetivo = posObjetivo / 5;
                int colObjetivo = posObjetivo % 5;
                
                distanciaTotal += Math.abs(filaActual - filaObjetivo) + Math.abs(colActual - colObjetivo);
            }
        }
        return distanciaTotal;
    }

    public static int calcularConflictoLineal(String estadoActual, String estadoObjetivo) {
        int h = 0;
        for (int i = 0; i < estadoActual.length(); i++) {
            char piezaA = estadoActual.charAt(i);
            
            if (piezaA != ' ') {
                int posPiezaA = i;
                int posObjetivoA = estadoObjetivo.indexOf(piezaA);
                
                int filaActualA = posPiezaA / 5;
                int colActualA = posPiezaA % 5;
                int filaObjetivoA = posObjetivoA / 5;
                int colObjetivoA = posObjetivoA % 5;
                
                h += Math.abs(filaActualA - filaObjetivoA) + Math.abs(colActualA - colObjetivoA);

                //Aqui empiza el conflicto lineal

                //Esta seccion es para buscar conflictos en filas
                if(colActualA < 4){
                    if(filaActualA == filaObjetivoA){
                        for (int j = i + 1; j < ((filaActualA * 5) + 5); j++) {
                            char piezaB = estadoActual.charAt(j);
                            
                            if (piezaB != ' ') {
                                int posPiezaB = j;
                                int posObjetivoB = estadoObjetivo.indexOf(piezaB);

                                int filaActualB = posPiezaB / 5;
                                int filaObjetivoB = posObjetivoB / 5;
                                int colObjetivoB = posObjetivoB % 5;

                                if(filaActualB == filaObjetivoB){
                                    if(colObjetivoB < colObjetivoA){
                                        //Si existe un conflicto
                                        //Se suma 2 pensando en que la pieza a quitara (+1) y se reintegrara a la fila (+1)
                                        h += 2;
                                    }
                                }
                            }
                        }
                    }
                }

                //Esta seccion es para buscar conflictos en columnas
                if(filaActualA < 4){
                    if(colActualA == colObjetivoA){
                        for (int j = i + 5; j < 25; j += 5) {
                            char piezaB = estadoActual.charAt(j);
                            
                            if (piezaB != ' ') {
                                int posPiezaB = j;
                                int posObjetivoB = estadoObjetivo.indexOf(piezaB);

                                int colActualB = posPiezaB % 5;
                                int filaObjetivoB = posObjetivoB / 5;
                                int colObjetivoB = posObjetivoB % 5;

                                if(colActualB == colObjetivoB){
                                    if(filaObjetivoB < filaObjetivoA){
                                        //Si existe un conflicto
                                        //Se suma 2 pensando en que la pieza a quitara (+1) y se reintegrara a la fila (+1)
                                        h += 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return h;
    }
}
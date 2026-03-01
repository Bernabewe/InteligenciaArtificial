
public class Nodo implements Comparable<Nodo>{
    String estado;
    Nodo padre;
    int nivel;
    int costo;
    int heuristica;

    public Nodo(String estado, Nodo padre){
        this.estado = estado;
        this.padre = padre;
        this.nivel = 0;
        this.costo = 0;
    }

    // GETTERS

    public String getEstado() {
        return estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCosto() {
        return costo;
    }

    public int getHeuristica() { 
        return heuristica; 
    }

    public int getF() {
        return costo + heuristica;
    }

    // SETTERS

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setHeuristica(int heuristica) { 
        this.heuristica = heuristica; 
    }

    // Si son iguales devuelve 0, si el primero es menor devuelve -1, si el primero es mayor devuelve 1
    @Override
    public int compareTo(Nodo o) {
        // Implementar la comparación por defecto basada en el costo 
        // para evitar el UnsupportedOperationException.
        if (this.costo < o.getCosto()) {
            return -1;
        }
        if (this.costo > o.getCosto()) {
            return 1;
        }
        return 0;
    }
}

package Pila;

/**
 *
 * @author Charl
 */
public class Nodo {

    public String valor;
    public Nodo der;
    public Nodo izq;

    public Nodo(String valor) {
        this.valor = valor;
        this.der = izq = null;
    }

}

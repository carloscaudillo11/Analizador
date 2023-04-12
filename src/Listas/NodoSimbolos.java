
package Listas;
import Objetos.Simbolo;
/**
 *
 * @author Charl
 */
public class NodoSimbolos {
    private Simbolo simbolo;
    private NodoSimbolos siguiente;

    public NodoSimbolos(Simbolo simbolo) {
        this.simbolo = simbolo;
        this.siguiente = null;
    }
    
    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    public void setSiguiente(NodoSimbolos siguiente) {
        this.siguiente = siguiente;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

    public NodoSimbolos getSiguiente() {
        return siguiente;
    }
}

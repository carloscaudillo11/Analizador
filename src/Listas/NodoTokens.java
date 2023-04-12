package Listas;

import Objetos.Token;

/**
 *
 * @author Charl
 */
public class NodoTokens {
    private Token simbolo;
    private NodoTokens siguiente;

    public NodoTokens(Token simbolo) {
        this.simbolo = simbolo;
        this.siguiente = null;
    }
    
    public void setSimbolo(Token simbolo) {
        this.simbolo = simbolo;
    }

    public void setSiguiente(NodoTokens siguiente) {
        this.siguiente = siguiente;
    }

    public Token getSimbolo() {
        return simbolo;
    }

    public NodoTokens getSiguiente() {
        return siguiente;
    }
}
package Listas;

/**
 *
 * @author Charl
 */
public class NodoGramatica {
    protected NodoGramatica next;
    protected int index;
    protected String simbolo;

    public NodoGramatica (String symbol, int index){
        this.next = null;
        this.simbolo = symbol;
        this.index = index;
    }

    public void setNext(NodoGramatica next){
        this.next = next;
    }

    public void setSymbol (String simbolo){
        this.simbolo = simbolo;
    }

    public void setIndex (int index) {
        this.index = index;
    }

    public String getSymbol(){
        return simbolo;
    }

    public NodoGramatica getNext(){
        return next;
    }

    public int getIndex(){
        return index;
    }
}

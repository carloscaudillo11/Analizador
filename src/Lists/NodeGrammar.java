package Lists;

/**
 *
 * @author Charl
 */
public class NodeGrammar {
    protected NodeGrammar next;
    protected int index;
    protected String terminal;

    public NodeGrammar(String symbol, int index){
        this.next = null;
        this.terminal = symbol;
        this.index = index;
    }

    public void setNext(NodeGrammar next){
        this.next = next;
    }

    public void setSymbol (String simbolo){
        this.terminal = simbolo;
    }

    public void setIndex (int index) {
        this.index = index;
    }

    public String getSymbol(){
        return terminal;
    }

    public NodeGrammar getNext(){
        return next;
    }

    public int getIndex(){
        return index;
    }
}

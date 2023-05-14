package Stack;

public class GrammarNode {

    public String value;
    public GrammarNode der;
    public GrammarNode izq;

    public GrammarNode(String valor) {
        this.value = valor;
        this.der = izq = null;
    }

}

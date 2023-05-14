package Stack;

/**
 * @author Charl
 */
public class GrammarStack {
    private GrammarNode start, end;


    public GrammarStack() {
        start = end = null;
    }

    public void push(String s) {
        GrammarNode nuevo = new GrammarNode(s);

        if (start == null) {
            start = end = nuevo;
        } else {
            end.der = nuevo;
            nuevo.izq = end;
            end = nuevo;
        }

    }

    public boolean isEmpty() {
        return start == end && start == null;
    }

    public String top() {
        return end == null ? null : end.value;
    }


    public void pop() {
        GrammarNode temp;
        if (start == end) {
            start = end = null;
        } else {
            temp = end;
            end = end.izq;
            end.der = null;
            temp.izq = null;
        }
    }
}


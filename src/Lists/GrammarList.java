package Lists;

/**
 * @author Charl
 */
public class GrammarList {
    protected NodeGrammar start, end;
    protected String type;
    protected int size;

    public GrammarList(String type) {
        this.start = end = null;
        this.type = type;
        this.size = 0;
    }

    public void toCatch(String symbol) {
        NodeGrammar nue = new NodeGrammar(symbol, size);
        if (start == null) {
            start = end = nue;
        } else {
            end.setNext(nue);
            end = nue;
        }
        size++;
    }

    public void print() {
        NodeGrammar temp;
        temp = start;
        System.out.println(this.type);

        while (temp != null) {
            String s = temp.getSymbol();
            System.out.println(s);
            temp = temp.getNext();
        }
    }

    public boolean existsSymbol(String search) {
        boolean exists = false;
        NodeGrammar temp = start;

        while (temp != null) {
            if (temp.getSymbol().equals(search)) {
                exists = true;
                break;
            }
            temp = temp.getNext();
        }
        return exists;
    }

    public String getElement(int index) {
        String element = null;
        NodeGrammar tmp = start;
        while (tmp != null) {
            if (tmp.getIndex() == index) {
                element = tmp.getSymbol();
            }
            tmp = tmp.getNext();
        }
        return element;
    }

    public int size() {
        return size;
    }
}


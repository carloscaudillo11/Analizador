package Lists;

import Objects.Token;
import java.util.Iterator;

/**
 *
 * @author Charl
 */
public class TokensList {
    protected Node<Token> start, end;

    public TokensList() {
        start = null;
        end = null;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public void add(Token token) {
        if (!isEmpty()) {
            end.setNext(new Node<>(token));
            end = end.getNext();
        } else {
            start = end = new Node<>(token);
        }
    }

    public void printTokens() {
        Node<Token> recorrer = start;
        while (recorrer != null) {
            System.out.printf("%10s%20s%23s%8s%8s%n",
                    recorrer.getObject().getLexema(),
                    recorrer.getObject().getToken(),
                    recorrer.getObject().getDescripcion(),
                    recorrer.getObject().getAtributo(),
                    recorrer.getObject().getLinea());
            recorrer = recorrer.getNext();
        }
    }

    public Iterator<Token> iterator() {
        return new Iterator<>() {
            private Node<Token> iteratorNode = start;

            @Override
            public boolean hasNext() {
                return iteratorNode != null;
            }

            @Override
            public Token next() {
                Token item = iteratorNode.getObject();
                iteratorNode = iteratorNode.getNext();
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void printErrors() {
        Node<Token> recorrer = start;
        while (recorrer != null) {
            System.out.printf("\u001B[31m" + recorrer.getObject().getLexema() + " "
                    + recorrer.getObject().getDescripcion() + "\u001B[0m");
            System.out.println("\n");
            recorrer = recorrer.getNext();
        }
    }
}
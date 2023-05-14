
package Lists;

import Objects.Symbol;

import java.util.Iterator;

public class SymbolsList {
    protected Node<Symbol> start, end;
    
    public SymbolsList(){
        start = null;
        end = null;
    }
	
    public boolean isEmpty(){
        return start == null;
    }
	
    public void add(Symbol simbolo){
        if(!isEmpty()){
            end.setNext(new Node<>(simbolo));
            end = end.getNext();
        }else{
            start = end = new Node<>(simbolo);
        }
    }
    
    public void printSymbols(){
        Node<Symbol> recorrer = start;
        while(recorrer != null){
            System.out.printf("%10s%13s%20s%17s%15s%13s%13s%n",
                    recorrer.getObject().getToken(),
                    recorrer.getObject().getClasificacion(),
                    recorrer.getObject().getTipo(),
                    recorrer.getObject().getRepeticiones(),
                    recorrer.getObject().getLinea().toString(),
                    recorrer.getObject().getAtributo(),
                    recorrer.getObject().getValor());
            recorrer = recorrer.getNext();
        }
    }
    
  
    
    public Iterator<Symbol> iterator() {
        return new Iterator<>() {
            private Node<Symbol> iteratorNode = start;
    
            @Override
            public boolean hasNext() {
                return iteratorNode != null;
            }
          
            @Override
            public Symbol next() {
                Symbol item = iteratorNode.getObject();
                iteratorNode = iteratorNode.getNext();
                return item;
            }
   
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean verify(Symbol tk){
        Node<Symbol> n;
        n = start;
        while(n != null){
            if(n.getObject().getToken().equals(tk.getToken()))
                return false;
            n = n.getNext();
        }
        return true;
    }   
}

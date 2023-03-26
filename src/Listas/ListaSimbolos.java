
package Listas;

import Obj.Simbolo;
import java.util.Iterator;

public class ListaSimbolos {
    protected NodoSimbolos inicio, fin;
    
    public ListaSimbolos(){
        inicio = null;
        fin = null;
    }
	
    public boolean estaVacia(){
        return inicio == null;
    }
	
    public void add(Simbolo simbolo){
        if(!estaVacia()){
            fin.setSiguiente(new NodoSimbolos(simbolo));
            fin = fin.getSiguiente();    
        }else{
            inicio = fin = new NodoSimbolos(simbolo);
        }
    }
    
    public void listarSimbolos(){
        NodoSimbolos recorrer = inicio;
        while(recorrer != null){
            System.out.printf("%10s%13s%20s%17s%15s%13s%13s%n",
                    recorrer.getSimbolo().getToken(),
                    recorrer.getSimbolo().getClasificacion(),
                    recorrer.getSimbolo().getTipo(),
                    recorrer.getSimbolo().getRepeticiones(),
                    recorrer.getSimbolo().getLinea().toString(),
                    recorrer.getSimbolo().getAtributo(),
                    recorrer.getSimbolo().getValor());
            recorrer = recorrer.getSiguiente();
        }
    }
    
  
    
    public Iterator< Simbolo > iterator() {
        return new Iterator< Simbolo >() {
            private NodoSimbolos iteratorNode = inicio;
    
            @Override
            public boolean hasNext() {
                return iteratorNode != null;
            }
          
            @Override
            public Simbolo next() {
                Simbolo item = iteratorNode.getSimbolo();
                iteratorNode = iteratorNode.getSiguiente();
                return item;
            }
   
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    //método para identificar si hay un elemento 
    public boolean verificar(Simbolo tk){
        NodoSimbolos n;
        n = inicio;
        while(n != null){
            if(n.getSimbolo().getToken().equals(tk.getToken()))
                return false;
            n = n.getSiguiente();
        }
        return true;
    }   
}

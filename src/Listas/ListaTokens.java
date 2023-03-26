package Listas;

import Obj.Token;
import java.util.Iterator;

/**
 *
 * @author Charl
 */
public class ListaTokens{
    //atributos
    protected NodoTokens inicio, fin;
    
    public ListaTokens(){
        inicio = null;
        fin = null;
    }
	
    public boolean estaVacia(){
        return inicio == null;
    }
	
    public void add(Token token){
        if(!estaVacia()){
            fin.setSiguiente(new NodoTokens(token));
            fin = fin.getSiguiente();    
        }else{
            inicio = fin = new NodoTokens(token);
        }
    }
    
    public void listarTokens(){
        NodoTokens recorrer = inicio;
        while(recorrer != null){
            System.out.printf("%10s%20s%23s%8s%8s%n",
                    recorrer.getSimbolo().getLexema(),
                    recorrer.getSimbolo().getToken(),
                    recorrer.getSimbolo().getDescripcion(),
                    recorrer.getSimbolo().getAtributo(),
                    recorrer.getSimbolo().getLinea());
            recorrer = recorrer.getSiguiente();
        }
    }
    
    public Iterator< Token > iterator() {
        return new Iterator< Token >() {
            private NodoTokens iteratorNode = inicio;
    
            @Override
            public boolean hasNext() {
                return iteratorNode != null;
            }
          
            @Override
            public Token next() {
                Token item = iteratorNode.getSimbolo();
                iteratorNode = iteratorNode.getSiguiente();
                return item;
            }
   
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public void listarErrores(){
        NodoTokens recorrer = inicio;
        while(recorrer != null){
            System.out.printf("\u001B[31m"+recorrer.getSimbolo().getLexema()+" "
                    +recorrer.getSimbolo().getDescripcion()+ "\u001B[0m");
            System.out.println("\n");
            recorrer = recorrer.getSiguiente();
        }
    }
    
    //método para identificar si hay un elemento 
    public boolean verificar(Token tk){
        NodoTokens n;
        n = inicio;
        while(n != null){
            if(n.getSimbolo().getLexema().equals(tk.getLexema()))
                return false;
            n = n.getSiguiente();
        }
        return true;
    }   
}
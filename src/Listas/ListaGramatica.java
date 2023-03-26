package Listas;

/**
 *
 * @author Charl
 */
public class ListaGramatica {
    protected NodoGramatica start, end;
    protected String type;
    protected int tamaño;

    public ListaGramatica(){
        this.start = end = null;
        this.type = null;
        this.tamaño = 0;
    }

    public ListaGramatica(String type){
        this.start = end = null;
        this.type = type;
        this.tamaño = 0;
    }

    public void toCatch(String symbol){
        NodoGramatica nue = new NodoGramatica (symbol, tamaño);
        if (start == null){
            start = end = nue;
        } else {
            end.setNext(nue);
            end = nue;
        }
        tamaño++;
    }

    public void print(){
        NodoGramatica temp;
        temp = start;
        System.out.println(this.type);

        while (temp != null){
            String s = temp.getSymbol();
            System.out.println(s);
            temp = temp.getNext();
        }
    }
    
    public boolean existsSymbol(String search){
        boolean exists = false;
        NodoGramatica temp = start;

        while(temp != null){
            if(temp.getSymbol().equals(search))
                exists = true;
            temp = temp.getNext();
        }
        
        return exists;
    }

    public String getElemento (int index){
        String element = null;
        NodoGramatica tmp = start;
        while (tmp != null){
            if(tmp.getIndex()==index){
                element = tmp.getSymbol();
            }
            tmp = tmp.getNext();
        }
        return element;
    }

    public int size(){
        return tamaño;
    }
}


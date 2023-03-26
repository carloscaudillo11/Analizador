package Pila;

/**
 *
 * @author Charl
 */
public class Pila{
    private Nodo inicio, fin;
    
    
    public Pila () {
        inicio = fin = null;
    }    
       
    public void push(String s) {
        Nodo nuevo = new Nodo(s);
        
        //Comprobar que la lista este vacia
        if (inicio == null) {
            inicio = fin = nuevo;
        } else {
            //Ampliamos la lista, y posicionamos 'nuevo' en el final
            fin.der = nuevo;
            nuevo.izq = fin;
            fin = nuevo;           
        }
        
    }
    
    public boolean isEmpty(){
        return inicio == fin && inicio == null && fin == null;
    }
    
    public String top(){
        return fin == null? null : fin.valor;
    }

    public void imprime() {
        //Recorremos la lista e impriminos todos su contenido
        //Cont recorre la lista elemento x elemento
        Nodo cont = inicio ;
        recorrer(cont);
        
    }
    public void recorrer(Nodo s){
        if(s != null){
            System.out.print(s.valor + ",");
            recorrer(s.der);
        }
    }
    //Contar los elementos de la lista
    public int cuenta() {
       int elem = 0;
       Nodo cont = inicio ;
        while (cont != null) {
            elem ++;
            //Se recorre la lista una posicion 
            cont = cont.der;
        }
       
       return elem; 
    }

    public Nodo pop () {
        Nodo temp = null;
        if(inicio == null){
            
        }else if (inicio == fin) {
            temp = inicio;
            inicio = fin = null;
        } else {
            temp = fin;
            fin = fin.izq;
            fin.der = null;
            temp.izq = null;
        }
       return temp;       
    }
    
    public void invertir(){
        Pila a;
        Pila b;
        a = new Pila();
        b = new Pila();
        
        Nodo n = this.pop();
        while (n!= null){
            a.push(n.valor);
            n = this.pop();
        }
        
       n = a.pop();
        while (n!= null){
            b.push(n.valor);
            n = b.pop();
        }
        
        n = b.pop();
        while (n!= null){
            this.push(n.valor);
            n = b.pop();
        }
    }
    
    public Nodo penultimo(){
        if(this.cuenta()>1){
            Nodo temp = this.pop();
            Nodo retorno = this.pop();
            this.push(temp.valor);
            return retorno;
        }
       return null;         
    }
    
    public Nodo regresarFondo(){ 
        return null;
    }
}

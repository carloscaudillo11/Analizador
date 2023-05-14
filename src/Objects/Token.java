package Objects;
/**
 *
 * @author Charl
 */
public class Token {
    private String lexema;
    private String token;
    private String descripcion;
    private Object atributo;
    private int linea;
     
    public Token(String lexema, String token, String descripcion,
                Object atributo, int linea) {
        this.lexema = lexema;
        this.token = token;
        this.descripcion = descripcion;
        this.atributo = atributo;
        this.linea = linea;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setToken(String token) { 
        this.token = token; 
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  
    public void setAtributo(int atributo) { 
        this.atributo = atributo; 
    } 
    
    public String getToken() { 
        return token; 
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }

    public Object getAtributo() { 
        return atributo; 
    }

    @Override
    public String toString() { 
        return getLexema()+" "+getToken()+" "+ getDescripcion()+" "+getAtributo() +" "+getLinea(); 
    }
}

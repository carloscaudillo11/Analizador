package Objects;

import java.util.List;

/**
 *
 * @author Charl
 */
public class Symbol {

    private String token;
    private String tipo;
    private String clasificacion;
    private List<Integer> linea;
    private int repeticiones;
    private Object atributo;
    private Object valor;

    public Symbol(String token, String clasificacion, String tipo, List<Integer> linea, int repeticiones,
                  Object atributo, Object valor) {
        this.token = token;
        this.clasificacion = clasificacion;
        this.tipo = tipo;
        this.repeticiones = repeticiones;
        this.linea = linea;
        this.atributo = atributo;
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Integer> getLinea() {
        return linea;
    }

    public void setLinea(List<Integer> linea) {
        this.linea = linea;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Object getAtributo() {
        return atributo;
    }

    public void setAtributo(Object valor) {
        this.atributo = valor;
    }
}

package Obj;

import java.util.List;

/**
 *
 * @author Charl
 */
public class Simbolo {

    private String token;
    private String tipo;
    private String clasificacion;
    private List linea;
    private int repeticiones;
    private Object atributo;
    private Object valor;

    public Simbolo(String token, String clasificacion, String tipo, List linea, int repeticiones, Object atributo, Object valor) {
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

    public List getLinea() {
        return linea;
    }

    public void setLinea(List linea) {
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

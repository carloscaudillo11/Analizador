package Analizadores;

import Pila.Pila;
import Herramientas.Matriz;
import Obj.Token;

/**
 *
 * @author Charl
 */
public class Sintactico {

    private final Pila pila;
    private final Gramatica gramar;
    private final int[][] matriz;
    protected Lexico lexico;
    private Token a;
    private String x;
    protected boolean error;

    public Sintactico() throws Exception {
        this.gramar = new Gramatica();
        this.pila = new Pila();
        this.matriz = Matriz.MATRIZ;
        this.lexico = new Lexico();
        this.lexico.analizar();
        this.error = false;
    }

    public void analizar() {
        if (!lexico.getErrores().estaVacia()) {
            System.out.println("\u001B[31m"
                    + "Error"
                    + "\u001B[0m");
            lexico.getErrores().listarErrores();
        } else {
            gramar.readGrammar();
            liAlgoritmo();
            if (error) {
                System.out.println("\u001B[31m"
                        + "Syntax Error");
                System.out.println("\u001B[31m"
                        +"No se esperaba "
                        + "'" + a.getLexema() + "' "
                        + "en la linea " + a.getLinea()
                        + "\u001B[0m");
            }
        }
    }

    public void mostrarTabla() {
        lexico.imprimirTablaTokens();
        System.out.println("\n");
    }

    private void liAlgoritmo() {
        pila.push(gramar.noTerminals.getElemento(0));
        x = pila.top();
        
        a = lexico.getTokenActual();
        while (!pila.isEmpty() && !error) {
            if (gramar.noTerminals.existsSymbol(x)) {
                if (predict(x, a) != 0) {
                    pila.pop();
                    produccion(predict(x, a));
                    x = pila.top();
                } else {
                    error = true;
                }
            } else {
                if (match(x, a)) {
                    pila.pop();
                    x = pila.top();
                    a = lexico.getTokenActual();

                } else {
                    error = true;
                }
            }
        }
    }

    private int predict(String x, Token a) {
        int indice_x = -1;
        int indice_a = -1;

        for (int i = 0; i < gramar.noTerminals.size(); i++) {
            if (gramar.noTerminals.getElemento(i).equals(x)) {
                indice_x = i;
            }
        }
        for (int j = 0; j < gramar.terminals.size(); j++) {
            if (gramar.terminals.getElemento(j).
                    equals(a.getLexema())) {
                indice_a = j;
            } else if (gramar.terminals.getElemento(j).
                    equals(a.getToken())) {
                indice_a = j;
            }
        }
        if (indice_x != -1 && indice_a != -1) {
            //System.out.println(matriz[indice_x][indice_a]);
            return matriz[indice_x][indice_a];
        } else {
            return 0;
        }
    }

    private void produccion(int produccionIndex) {
        String ladoIzq = gramar.productions.getElemento(produccionIndex - 1);
        String prod = "";
        try {
            for (int i = ladoIzq.length() - 1; i >= 0; i--) {
                if (ladoIzq.charAt(i) == ' ') {
                    pila.push(prod);
                    prod = "";
                } else if (i == 0) {
                    prod = ladoIzq.charAt(i) + prod;
                    pila.push(prod);
                } else {
                    prod = ladoIzq.charAt(i) + prod;
                }
            }
        } catch (NullPointerException e) {
            error = true;
        }
    }

    private boolean match(String x, Token a) {
        boolean matchExito = false;

        if (x.equals(a.getLexema())) {
            matchExito = true;
        }
        if (x.equals(a.getToken())) {
            matchExito = true;
        }
        if (a.getAtributo().equals(210)) {
            matchExito = false;
            error = true;
        }
        return matchExito;
    }

}

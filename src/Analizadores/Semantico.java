package Analizadores;

import Listas.ListaSimbolos;
import Objetos.Simbolo;
import Objetos.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Charl
 */
public class Semantico {

    protected Sintactico sintactico;
    protected Token simbolo;
    private final ListaSimbolos ts;
    private int error;
    private Simbolo aux;
    private int lineAux;

    public Semantico() throws Exception {
        this.ts = new ListaSimbolos();
        this.sintactico = new Sintactico();
        this.sintactico.analizar();
        this.error = 0;
        this.aux = null;
        this.lineAux = 0;
    }

    public void analizar() {
        if (!sintactico.error && sintactico.lexico.errores.estaVacia()) {
            generaTS();
            semantico();
            switch (error) {
                case 201 -> {
                    System.out.println("\033[33m"
                            + "Warning");
                    System.out.println("La variable \"" + aux.getToken() + "\" declarada y "
                            + "no usada en la "
                            + "linea " + Line(aux)
                            + "\u001B[0m");
                }
                case 200 -> {
                    System.out.println("\u001B[31m"
                            + "Error");
                    System.out.println("La variable \"" + aux.getToken() + "\" no esta "
                            + "declarada en la linea " + Line(aux)
                            + "\u001B[0m");
                }
                case 202 -> {
                    System.out.println("\u001B[31m"
                            + "Error");
                    System.out.println("Los tipos no coinciden en la linea " + lineAux
                            + "\u001B[0m");
                }
                default -> {
                    sintactico.mostrarTabla();
                    imprimirTablaSimbolos();
                }
            }
        }
    }

    private void imprimirTablaSimbolos() {
        System.out.println("-------------------------------------------SIMBOLOS------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%10s%18s%15s%20s%12s%15s%12s%n", "TOKEN", "CLASIFICACION", "TIPO", "REPETICIONES", "LINEA", "ATRIBUTO", "VALOR");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        ts.listarSimbolos();
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    private Simbolo generarSimbolo(String token, String clasificacion, String tipo, List linea,
            int repeticiones, Object atributo, Object valor) {
        Simbolo simb;
        simb = new Simbolo(token, clasificacion, tipo, linea, repeticiones, atributo, valor);
        return simb;
    }

    private int generaRepeticiones(Token id) {
        int count = 0;
        Iterator<Token> iterador = sintactico.lexico.tokens.iterator();
        Token token;
        while (iterador.hasNext()) {
            token = iterador.next();
            if (token.getLexema().equals(id.getLexema())) {
                count++;
            }
        }
        return count;
    }

    private List generaLineas(Token id) {
        List<Integer> list = new ArrayList<>();
        Iterator<Token> iterador = sintactico.lexico.tokens.iterator();
        Token token;
        while (iterador.hasNext()) {
            token = iterador.next();
            if (token.getLexema().equals(id.getLexema())) {
                list.add(token.getLinea());
            }
        }
        return list;
    }

    private Object valor(Token tk) {
        switch (tk.getToken()) {
            case "Id" -> {
                return "";
            }
            case "Entero" -> {
                return tk.getAtributo();
            }
            case "Real" -> {
                return tk.getAtributo();
            }
        }
        return "";
    }

    private void compareTypes(Simbolo simbolo) {
        Iterator<Token> iterador = sintactico.lexico.tokens.iterator();
        Token t;
        while (iterador.hasNext()) {
            t = iterador.next();
            if (t.getToken().equals("Id")||
                    t.getToken().equals("Entero")||
                    t.getToken().equals("Real")) {
                if (simbolo.getLinea().contains(t.getLinea())) {
                    if (!simbolo.getTipo().equals(t.getDescripcion())) {
                        lineAux = t.getLinea();
                        error = 202;
                    }
                }
            }
        }
    }

    private void generaTS() {
        Iterator<Token> iterador = sintactico.lexico.tokens.iterator();
        Token tk;
        while (iterador.hasNext()) {
            tk = iterador.next();
            switch (tk.getToken()) {
                case "Id" -> {
                    Simbolo temp = generarSimbolo(tk.getLexema(), tk.getToken(),
                            tk.getDescripcion(), generaLineas(tk), generaRepeticiones(tk),
                            tk.getAtributo(), valor(tk));
                    if (ts.verificar(temp)) {
                        ts.add(temp);
                    }
                }
                case "Entero" -> {
                    Simbolo temp;
                    temp = generarSimbolo(tk.getLexema(), tk.getToken(), tk.getDescripcion(),
                            generaLineas(tk), generaRepeticiones(tk), tk.getAtributo(), valor(tk));
                    ts.add(temp);
                }
                case "Real" -> {
                    Simbolo temp;
                    temp = generarSimbolo(tk.getLexema(), tk.getToken(), tk.getDescripcion(),
                            generaLineas(tk), generaRepeticiones(tk), tk.getAtributo(), valor(tk));
                    ts.add(temp);
                }
            }
        }
    }

    private int Line(Simbolo t) {
        for (Object line : t.getLinea()) {
            return (Integer) line;
        }
        return 0;
    }

    private void semantico() {
        Iterator<Simbolo> iterador = ts.iterator();
        while (iterador.hasNext()) {
            Simbolo t = iterador.next();
            if (t.getClasificacion().equals("Id")) {
                if (t.getTipo().equals("")) {
                    aux = t;
                    error = 200;
                } else if (t.getRepeticiones() == 1) {
                    aux = t;
                    error = 201;
                }else{
                    compareTypes(t);
                }
            }
        }
    }
}

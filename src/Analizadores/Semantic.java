package Analizadores;

import Lists.SymbolsList;
import Objects.Symbol;
import Objects.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Charl
 */
public class Semantic {
    protected Syntactic sintactico;
    private final SymbolsList ts;
    private int error;
    private Symbol aux;
    private int lineAux;

    public Semantic() {
        this.ts = new SymbolsList();
        this.sintactico = new Syntactic();
        this.sintactico.analyze();
        this.error = 0;
        this.aux = null;
        this.lineAux = 0;
    }

    public void analyze() {
        if (!sintactico.error && sintactico.lexico.errors.isEmpty()) {
            generaTS();
            semantic();
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
                    sintactico.showTable();
                    printSymbolsTable();
                }
            }
        }
    }

    public SymbolsList getTS(){
        return ts;
    }

    private void printSymbolsTable() {
        System.out.println("-------------------------------------------SIMBOLOS------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%10s%18s%15s%20s%12s%15s%12s%n", "TOKEN", "CLASIFICACION", "TIPO", "REPETICIONES", "LINEA", "ATRIBUTO", "VALOR");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        ts.printSymbols();
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    private Symbol generateSymbol(String token, String clasificacion, String tipo, List<Integer> linea,
                                  int repeticiones, Object atributo, Object valor) {
        Symbol symbol;
        symbol = new Symbol(token, clasificacion, tipo, linea, repeticiones, atributo, valor);
        return symbol;
    }

    private int generateRepetitions(Token id) {
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

    private List<Integer> generateLines(Token id) {
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

    private Object value(Token tk) {
        switch (tk.getToken()) {
            case "Id" -> {
                return "";
            }
            case "Entero", "Real" -> {
                return tk.getAtributo();
            }
        }
        return "";
    }

    private void compareTypes(Symbol simbolo) {
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
                    Symbol temp = generateSymbol(tk.getLexema(), tk.getToken(),
                            tk.getDescripcion(), generateLines(tk), generateRepetitions(tk),
                            tk.getAtributo(), value(tk));
                    if (ts.verify(temp)) {
                        ts.add(temp);
                    }
                }
                case "Entero", "Real" -> {
                    Symbol temp;
                    temp = generateSymbol(tk.getLexema(), tk.getToken(), tk.getDescripcion(),
                            generateLines(tk), generateRepetitions(tk), tk.getAtributo(), value(tk));
                    ts.add(temp);
                }
            }
        }
    }

    private int Line(Symbol t) {
        for (Integer line : t.getLinea()) {
            return line;
        }
        return 0;
    }

    private void semantic() {
        Iterator<Symbol> iterador = ts.iterator();
        while (iterador.hasNext()) {
            Symbol t = iterador.next();
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

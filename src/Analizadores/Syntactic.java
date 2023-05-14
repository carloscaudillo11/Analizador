package Analizadores;

import Stack.GrammarStack;
import Tools.Matrix;
import Objects.Token;

/**
 *
 * @author Charl
 */
public class Syntactic {

    private final GrammarStack grammarStack;
    private final Grammar grammar;
    private final int[][] matrix;
    protected Lexico lexico;
    private Token a;
    protected boolean error;

    public Syntactic() {
        this.grammar = new Grammar();
        this.grammarStack = new GrammarStack();
        this.matrix = Matrix.Matrix;
        this.lexico = new Lexico();
        this.lexico.analyze();
        this.error = false;
    }

    public void analyze() {
        if (!lexico.getErrors().isEmpty()) {
            System.out.println("\u001B[31m"
                    + "Error"
                    + "\u001B[0m");
            lexico.getErrors().printErrors();
        } else {
            grammar.readGrammar();
            algorithm();
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

    public void showTable() {
        lexico.printTokensTable();
        System.out.println("\n");
    }

    private void algorithm() {
        grammarStack.push(grammar.noTerminals.getElement(0));
        String x = grammarStack.top();
        
        a = lexico.getCurrentToken();
        while (!grammarStack.isEmpty() && !error) {
            if (grammar.noTerminals.existsSymbol(x)) {
                if (predict(x, a) != 0) {
                    grammarStack.pop();
                    production(predict(x, a));
                    x = grammarStack.top();
                } else {
                    error = true;
                }
            } else {
                if (match(x, a)) {
                    grammarStack.pop();
                    x = grammarStack.top();
                    a = lexico.getCurrentToken();

                } else {
                    error = true;
                }
            }
        }
    }

    private int predict(String x, Token a) {
        int index_x = -1;
        int index_a = -1;

        for (int i = 0; i < grammar.noTerminals.size(); i++) {
            if (grammar.noTerminals.getElement(i).equals(x)) {
                index_x = i;
            }
        }
        for (int j = 0; j < grammar.terminals.size(); j++) {
            if (grammar.terminals.getElement(j).
                    equals(a.getLexema())) {
                index_a = j;
            } else if (grammar.terminals.getElement(j).
                    equals(a.getToken())) {
                index_a = j;
            }
        }
        if (index_x != -1 && index_a != -1) {
            return matrix[index_x][index_a];
        } else {
            return 0;
        }
    }

    private void production(int index) {
        String ladoIzq = grammar.productions.getElement(index - 1);
        StringBuilder prod = new StringBuilder();
        try {
            for (int i = ladoIzq.length() - 1; i >= 0; i--) {
                if (ladoIzq.charAt(i) == ' ') {
                    grammarStack.push(prod.toString());
                    prod = new StringBuilder();
                } else if (i == 0) {
                    prod.insert(0, ladoIzq.charAt(i));
                    grammarStack.push(prod.toString());
                } else {
                    prod.insert(0, ladoIzq.charAt(i));
                }
            }
        } catch (NullPointerException e) {
            error = true;
        }
    }

    private boolean match(String x, Token a) {
        boolean matchSuccess = x.equals(a.getLexema());

        if (x.equals(a.getToken())) {
            matchSuccess = true;
        }
        if (a.getAtributo().equals(210)) {
            matchSuccess = false;
            error = true;
        }
        return matchSuccess;
    }

}

package Analizadores;

import FilesManagement.File;
import Objects.Token;

import java.util.*;

public class PrefixNotation {
    protected Semantic semantic;
    protected Stack<Token> operatorStack;
    protected Stack<Token> finalStack;
    private final List<Token> list;
    private final File file;

    public PrefixNotation() {
        this.semantic = new Semantic();
        this.semantic.analyze();
        this.finalStack = new Stack<>();
        this.operatorStack = new Stack<>();
        this.list = new ArrayList<>();
        this.file = new File();
    }

    public void Analyze() {
        getNotation();
        infixToPrefix();
        writeFile();
    }

    private void writeFile(){
        StringBuilder data = new StringBuilder();
        while (!finalStack.isEmpty()) {
            data.append(finalStack.pop().getLexema());
            data.append(" ");
        }
        file.openFile("src/Tools/prefix.txt");
        file.writeFile(data.toString());
    }

    private void search(int line){
        Iterator<Token> iterador = semantic.sintactico.lexico.tokens.iterator();
        while (iterador.hasNext()) {
            Token next = iterador.next();
            if (next.getLinea() == line && next.getLexema().equals("=")){
                while (iterador.hasNext()) {
                    Token n = iterador.next();
                    if (n.getLinea() == line && !n.getLexema().equals(";")){
                        list.add(n);
                    }
                } 
            }
        }
    }

    private void getNotation(){
        Iterator<Token> iterador = semantic.sintactico.lexico.tokens.iterator();
        while (iterador.hasNext()) {
            Token next = iterador.next();
            if (next.getLexema().equals("+") || next.getLexema().equals("-")){
                search(next.getLinea());
                break;
            }
        }
    }

    private void infixToPrefix(){
        Collections.reverse(list);
        for (Token t: list) {
            if (t.getToken().equals("Id") ||
                    t.getToken().equals("Entero") ||
                    t.getToken().equals("Real")){
                finalStack.push(t);
            }else if (t.getToken().equals("Caracter Simple")){
                operatorStack.push(t);
            }
        }
        while (!operatorStack.isEmpty()) {
            finalStack.push(operatorStack.pop());
        }
    }
}

package Analizadores;

import Lists.GrammarList;
import FilesManagement.File;

/**
 *
 * @author Charl
 */
public class Grammar {
    protected File file;
    public GrammarList productions, noTerminals, terminals;
    protected String[] lines;

    public Grammar() {
        this.file = new File();
        String ruta = "src/Tools/grammar.txt";
        this.file.openFile(ruta);
        this.productions = new GrammarList("----Productions----");
        this.noTerminals = new GrammarList("----No Terminals------");
        this.terminals = new GrammarList("-----Terminals------");
        this.lines = this.file.readFile();
    }
    
    public void readGrammar(){
        this.getNT();
        this.getProduction();
        this.getTerminals();
    }

    public String getLeftSide(String line){
        StringBuilder symbol = new StringBuilder();
        char state = 'i';
        for(int i = 0; i < line.length(); i++){
            char car = line.charAt(i);
            switch (state) {
                case 'i' -> {
                    state = 's';
                    symbol.append(car);
                }
                case 's' -> {
                    switch (car) {
                        case '-' -> state = '-';
                        case ' ', '\t' -> state = 'v';
                        default -> symbol.append(car);
                    }
                }
                case '-' -> {
                    if (car == '>') {
                        state = '>';
                    } else {
                        state = 'e';
                    }
                }
                case 'e' -> symbol = null;
            }
        }
        assert symbol != null;
        return symbol.toString();
    }
    
    
    private void getNT() {
        for(String line:lines){
            String symbol = this.getLeftSide(line);
            if(symbol != null && !symbol.equals("") && ! this.noTerminals.existsSymbol(symbol)){
                this.noTerminals.toCatch(symbol);
            }
        }
    }

    private String getRightSide(String line){
        char status = 'i';
        StringBuilder production = new StringBuilder();

        for(int i =0; i<line.length(); i++){
            char c = line.charAt(i);
            switch (status) {
                case 'i' -> {
                    if (c == '-') {
                        status = '-';
                    }
                }
                case '-' -> {
                    if (c == '>') {
                        status = '>';
                    } else {
                        status = 'e';
                    }
                }
                case '>' -> {
                    if (c == ' ' || c == '\t') {
                        status = 's';
                    } else {
                        status = 'e';
                    }
                }
                case 's' -> {
                    if (c != '\n' || i <= line.length() - 1) {
                        production.append(c);
                    } else {
                        status = 'e';
                    }
                }
                case 'e' -> production = null;
            }
            
        }
        assert production != null;
        return production.toString();
    }

    private void saveTerminal(String terminal){
        if(!terminal.equals("") &&
            !this.noTerminals.existsSymbol(terminal) &&
            !this.terminals.existsSymbol(terminal)){

                this.terminals.toCatch(terminal);
            }
    }

    private void getTerminals(){
        for(int i = 0; i< this.productions.size(); i++){
            String production = this.productions.getElement(i);
            StringBuilder terminal = new StringBuilder();
            for(int j = 0; j<production.length(); j++){
                char c = production.charAt(j);
                
                if(c == ' ' || c == '\t'){
                    this.saveTerminal(terminal.toString());
                    terminal = new StringBuilder();
                } else {
                    terminal.append(c);
                    if(j == production.length()-1){
                        this.saveTerminal(terminal.toString());
                    }
                }
            }
        }
    }

    private void getProduction(){
        for(String line: lines){
            String symbol = this.getRightSide(line);
            if(!this.terminals.existsSymbol(symbol)){
                this.productions.toCatch(symbol.trim());
            }
        }
    }
}
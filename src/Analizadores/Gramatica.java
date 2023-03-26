package Analizadores;

import ManejoArchivos.ArchivoGramatica;
import Listas.ListaGramatica;

/**
 *
 * @author Charl
 */
public class Gramatica {
    protected ArchivoGramatica file;
    public ListaGramatica productions, noTerminals, terminals;
    protected String[] lines;

    public Gramatica() throws Exception{
        this.file = new ArchivoGramatica();
        this.file.abrirArchivo();
        this.productions = new ListaGramatica("----Produciones----");
        this.noTerminals = new ListaGramatica("----No Terminales------");
        this.terminals = new ListaGramatica("-----Terminales------");
        this.lines = this.file.leerArchivo();
    }
    
    public void readGrammar(){
        this.getNT();
        this.getProduction();
        this.getTerminals();
    }

    public void print(){
        System.out.println("----Grammar----");
        this.printGrammar();
        System.out.println("");
        this.noTerminals.print();
        System.out.println("");
        this.terminals.print();
        System.out.println("");
        this.productions.print();
    }

    public void printGrammar(){
        for(String line: lines){
            System.out.println(line);
        }
    }

    public String obtainLeftSide(String line){
        String symbol = "";
        char state = 'i';
        for(int i = 0; i < line.length(); i++){
            char car = line.charAt(i);
            switch(state){
                case 'i':
                    if(car != '-' || car != ' ' || car != '\t'){
                        state = 's';
                        symbol += car;
                    }else{
                        state = 'e';
                    }
                    break;
                case 's':
                switch (car) {
                    case '-':
                        state = '-';
                        break;
                    case ' ':
                    case '\t':
                        state = 'v';
                        break;
                    default:
                        symbol += car;
                        break;
                }
                    break;

                case '-':
                    if(car == '>'){
                        state = '>';
                    }else{
                        state = 'e';
                    }
                    break;
                case 'e':
                    symbol = null;
                    break;
            }  
        } 
        return symbol;
    }
    
    
    private void getNT() {
        for(String line:lines){
            String symbol = this.obtainLeftSide(line);
            if(symbol != null && !symbol.equals("") && ! this.noTerminals.existsSymbol(symbol)){
                this.noTerminals.toCatch(symbol);
            }
        }
    }

    private String obtenerLadoD (String line){
        char statu = 'i';
        String production = "";

        for(int i =0; i<line.length(); i++){
            char carac = line.charAt(i);
            switch(statu){
                case 'i':
                    if(carac == '-'){
                         statu = '-';
                    }
                    break;
                case '-':
                    if(carac == '>'){
                        statu = '>';
                    }else {
                        statu = 'e';
                    }
                    break; 
                case '>': 
                    if(carac == ' ' || carac == '\t'){
                        statu = 's';
                    }else {
                        statu = 'e';
                    }
                    break;
                case 's':
                    if(carac != '\n' || i <= line.length()-1){
                        production += carac;
                    }else{
                        statu = 'e';
                    }
                    break;
                case 'e':
                    production = null;
                    break;
            }
            
        }
        return production;
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
            String production = this.productions.getElemento(i);
            String terminal = "";
            for(int j = 0; j<production.length(); j++){
                char carac = production.charAt(j);
                
                if(carac == ' ' || carac == '\t'){
                    this.saveTerminal(terminal);
                    terminal = "";
                } else {
                    terminal += carac;
                    if(j == production.length()-1){
                        this.saveTerminal(terminal);
                    }
                }
            }
        }
    }

    private void getProduction(){
        for(String line: lines){
            String symbol = this.obtenerLadoD(line);
            if(symbol != null
                        && !this.terminals.existsSymbol(symbol)){
                this.productions.toCatch(symbol.trim());
            }
        }
    }
}
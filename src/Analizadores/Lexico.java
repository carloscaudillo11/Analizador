package Analizadores;

import Obj.Token;
import ManejoArchivos.Archivo;
import ER.Regex;
import Listas.ListaSimbolos;
import Listas.ListaTokens;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class Lexico {

    private final Archivo archivo;
    private final String[] lineas;
    private final Token[] Arreglo;
    public final Regex regex;
    private final Map<String, Integer> atributos;
    private final Map<String, String> tipoDato;
    private int contador, index, line, atributo, bandera;
    public ListaTokens tokens, errores;
    public ListaSimbolos ts;

    public Lexico() throws IOException {
        this.archivo = new Archivo();
        this.regex = new Regex();
        this.Arreglo = new Token[1000];
        this.archivo.abrirArchivo();
        this.lineas = archivo.leerArchivo();
        this.tokens = new ListaTokens();
        this.errores = new ListaTokens();
        this.contador = 0;
        this.atributo = 500;
        this.line = 0;
        this.bandera = 0;
        this.atributos = new Hashtable();
        this.tipoDato = new Hashtable();
        this.ts = new ListaSimbolos();
    }
    
    public ListaTokens getErrores() {
        return errores;
    }

    public void analizar() {
        for (String linea : lineas) {
            line++;
            afd(linea);
        }
    }

    public void imprimirTablaTokens() {
        System.out.println("---------------------------------TOKENS-------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s%17s%25s%11s%10s%n", "LEXEMA", "TOKEN", "CLASIFICACION", "ATRIBUTO", "LINEA");
        System.out.println("-----------------------------------------------------------------------------------");
        tokens.listarTokens();
        System.out.println("------------------------------------------------------------------------------------");
    }
    

    private int generaAtributo(String lexema) {
        int a = 0;
        switch (lexema) {
            case "inicio" ->
                a = 401;
            case "entero" ->
                a = 402;
            case "real" ->
                a = 403;
            case "leer" ->
                a = 404;
            case "escribir" ->
                a = 405;
            case "fin" ->
                a = 406;
        }
        return a;
    }

    private int generaAtributoVariable(String lexema) {
        if (atributos.containsKey(lexema)) {
            atributo = atributos.get(lexema);
        } else {
            atributos.put(lexema, atributo);
        }
        atributo++;
        return atributo;
    }

    private String generaTipoDato(String lexema) {
        String tipo ="";
        if (tipoDato.containsKey(lexema)) {
            tipo = tipoDato.get(lexema);
        } else {
            if (bandera == 1) {
                tipoDato.put(lexema, "Entero");
                tipo = "Entero";
            } else if(bandera == 2) {
                tipoDato.put(lexema, "Real");
                tipo = "Real";
            }
        }
        return tipo;
    }

    private String generaDescripcion(String lexema){
        String clasificacion;
        switch (lexema) {
            case "inicio" ->
                clasificacion = "Iniciar Programa";
            case "entero" -> {
                clasificacion = "Tipo de Dato";
                bandera = 1;
            }
            case "real" -> {
                clasificacion = "Tipo de Dato";
                bandera = 2;
            }
            case "leer" ->
                clasificacion = "Funcion";
            case "escribir" ->
                clasificacion = "Funcion";
            case "fin" ->
                clasificacion = "Fin del Programa";
            case "+" ->
                clasificacion = "Operador Suma";
            case "-" ->
                clasificacion = "Operador Resta";
            case ":" ->
                clasificacion = "Doble punto";
            case "(" ->
                clasificacion = "Parentesis que Abre";
            case ")" ->
                clasificacion = "Parentesis que Cierra";
            case ";" -> {
                clasificacion = "Punto y Coma";
                bandera = 0;
            }
            case "=" ->
                clasificacion = "Asignacion";
            case "," ->
                clasificacion = "Coma";
            default -> {
                clasificacion = generaTipoDato(lexema);
            }
        }
        return clasificacion;
    }

    private Token generarToken(String lexema, String token, String descripcion,
            Object atributo, int linea) {
        Token toke;
        toke = new Token(lexema, token, descripcion, atributo, linea);
        this.Arreglo[index] = toke;
        index++;
        return toke;
    }

    private void afd(String linea) {
        int estado = 0;
        String lexema = "";
        for (int i = 0; i <= linea.length(); i++) {
            switch (estado) {
                case 0 -> {
                    if (i < linea.length() && linea.charAt(i)
                            >= 'a' && linea.charAt(i) <= 'z') {
                        lexema += linea.charAt(i);
                        estado = 1;
                    } else {
                        estado = 3;
                        i--;
                    }
                }
                case 1 -> {
                    if (i < linea.length()
                            && regex.esLETRA(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 2;
                        i--;
                    }
                }case 2 -> {
                    if (i < linea.length() && regex.esRESERVADA(lexema)) {
                        Token temp;
                        temp = generarToken(lexema, "Reservada",
                                generaDescripcion(lexema),
                                generaAtributo(lexema), line);
                        tokens.add(temp);
                    } else {
                        Token temp;
                        temp = generarToken(lexema, "Id",
                                generaDescripcion(lexema),
                                generaAtributoVariable(lexema),
                                line);
                        tokens.add(temp);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }
                case 3 -> {
                    if (i < linea.length() && linea.charAt(i) >= '0'
                            && linea.charAt(i) <= '9') {
                        lexema += linea.charAt(i);
                        estado = 4;
                    } else {
                        estado = 7;
                        i--;
                    }
                }
                case 4 -> {
                    if (i < linea.length()
                            && regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 5;
                        i--;
                    }
                }
                case 5 -> {
                    if (i < linea.length() && linea.charAt(i) == '.') {
                        lexema += linea.charAt(i);
                        estado = 12;
                    } else {
                        estado = 6;
                        i--;
                    }
                }
                case 12 -> {
                    if (i < linea.length() && linea.charAt(i)
                            >= '0' && linea.charAt(i) <= '9') {
                        lexema += linea.charAt(i);
                        estado = 13;
                    } else {
                        lexema += linea.charAt(i);
                        estado = 14;
                    }
                }
                case 13 -> {
                    if (i < linea.length()
                            && regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 6;
                        i--;
                    }
                }
                case 6 -> {
                    if (regex.esENTERO(lexema)) {
                        Token temp;
                        temp = generarToken(lexema, "Entero",
                                "Entero",
                                Integer.valueOf(lexema), line);
                        tokens.add(temp);
                    } else if (regex.esFLOTANTE(lexema)) {
                        Token temp;
                        temp = generarToken(lexema, "Real",
                                "Real",
                                Double.valueOf(lexema), line);
                        tokens.add(temp);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }
                case 7 -> {
                    if (i < linea.length()
                            && regex.esOPERADOR(String.valueOf(
                                    linea.charAt(i)))) {
                        lexema += linea.charAt(i);
                        Token temp;
                        temp = generarToken(lexema, "Caracter Simple",
                                generaDescripcion(lexema),
                                (int) lexema.charAt(0), line);
                        tokens.add(temp);
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 8;
                        i--;
                    }
                }
                case 8 -> {
                    if (i < linea.length() && linea.charAt(i) == ' ') {
                        estado = 0;
                    } else {
                        estado = 9;
                        i--;
                    }
                }
                case 9 -> {
                    if (i < linea.length()
                            && regex.esDELIMITADOR(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                        Token temp;
                        temp = generarToken(lexema, "Caracter Simple",
                                generaDescripcion(lexema),
                                (int) lexema.charAt(0), line);
                        tokens.add(temp);
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 10;
                        i--;
                    }
                }
                case 10 -> {
                    if (i < linea.length()
                            && regex.esMAYUSCULA(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                        estado = 11;
                    } else {
                        estado = 17;
                        i--;
                    }
                }
                case 11 -> {
                    if (i < linea.length()
                            && regex.esLETRA(linea.charAt(i))
                            || regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 12;
                        i--;
                    }
                }
                case 14 -> {
                    Token temp;
                    temp = generarToken(lexema, "error",
                            " No pertenece a ninguna categoria lexica "
                                    + "en la linea "+line,
                            210, line);
                    errores.add(temp);
                    estado = 0;
                    lexema = "";
                    i--;
                }
                case 17 -> {
                    if (i < linea.length()
                            && !regex.estaALFABETO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                        estado = 14;
                    }
                }
            }
        }
    }

    public Token getTokenActual() {
        Token tem = this.Arreglo[this.contador];
        contador++;
        return tem;
    }
   
}

package Analizadores;

import Objects.Token;
import FilesManagement.File;
import ER.Regex;
import Lists.TokensList;

import java.util.Hashtable;
import java.util.Map;

public class Lexico {

    private final String[] lines;
    private final Token[] array;
    private final Regex regex;
    private final Map<String, Integer> attributes;
    private final Map<String, String> tipoDato;
    private int count, index, line, attribute, flag;
    public TokensList tokens, errors;

    public Lexico() {
        File file = new File();
        String ruta = "src/Tools/code.txt";
        file.openFile(ruta);
        this.regex = new Regex();
        this.array = new Token[1000];
        this.lines = file.readFile();
        this.tokens = new TokensList();
        this.errors = new TokensList();
        this.count = 0;
        this.attribute = 500;
        this.line = 0;
        this.flag = 0;
        this.attributes = new Hashtable<>();
        this.tipoDato = new Hashtable<>();
    }

    public TokensList getErrors() {
        return errors;
    }

    public void analyze() {
        for (String linea : lines) {
            line++;
            afd(linea);
        }
    }

    public void printTokensTable() {
        System.out.println("---------------------------------TOKENS-------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s%17s%25s%11s%10s%n", "LEXEMA", "TOKEN", "CLASIFICACION", "ATRIBUTO", "LINEA");
        System.out.println("-----------------------------------------------------------------------------------");
        tokens.printTokens();
        System.out.println("------------------------------------------------------------------------------------");
    }

    private int generateAttribute(String lexema) {
        int a = 0;
        switch (lexema) {
            case "inicio" -> a = 401;
            case "entero" -> a = 402;
            case "real" -> a = 403;
            case "leer" -> a = 404;
            case "escribir" -> a = 405;
            case "fin" -> a = 406;
        }
        return a;
    }

    private int generateAttributeId(String lexema) {
        if (attributes.containsKey(lexema)) {
            attribute = attributes.get(lexema);
        } else {
            attributes.put(lexema, attribute);
        }
        attribute++;
        return attribute;
    }

    private String generateDataType(String lexema) {
        String tipo = "";
        if (tipoDato.containsKey(lexema)) {
            tipo = tipoDato.get(lexema);
        } else {
            if (flag == 1) {
                tipoDato.put(lexema, "Entero");
                tipo = "Entero";
            } else if (flag == 2) {
                tipoDato.put(lexema, "Real");
                tipo = "Real";
            }
        }
        return tipo;
    }

    private String generateDescription(String lexema) {
        String clasificacion;
        switch (lexema) {
            case "inicio" -> clasificacion = "Iniciar Programa";
            case "entero" -> {
                clasificacion = "Tipo de Dato";
                flag = 1;
            }
            case "real" -> {
                clasificacion = "Tipo de Dato";
                flag = 2;
            }
            case "leer", "escribir" -> clasificacion = "Funcion";
            case "fin" -> clasificacion = "Fin del Programa";
            case "+" -> clasificacion = "Operador Suma";
            case "-" -> clasificacion = "Operador Resta";
            case ":" -> clasificacion = "Doble punto";
            case "(" -> clasificacion = "Parentesis que Abre";
            case ")" -> clasificacion = "Parentesis que Cierra";
            case ";" -> {
                clasificacion = "Punto y Coma";
                flag = 0;
            }
            case "=" -> clasificacion = "Asignacion";
            case "," -> clasificacion = "Coma";
            default -> clasificacion = generateDataType(lexema);

        }
        return clasificacion;
    }

    private Token generateToken(String lexema, String token, String descripcion,
            Object atributo, int linea) {
        Token toke;
        toke = new Token(lexema, token, descripcion, atributo, linea);
        this.array[index] = toke;
        index++;
        return toke;
    }

    private void afd(String linea) {
        int estado = 0;
        String lexema = "";
        for (int i = 0; i <= linea.length(); i++) {
            switch (estado) {
                case 0 -> {
                    if (i < linea.length() && linea.charAt(i) >= 'a' && linea.charAt(i) <= 'z') {
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
                }
                case 2 -> {
                    if (i < linea.length() && regex.esRESERVADA(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Reservada",
                                generateDescription(lexema),
                                generateAttribute(lexema), line);
                        tokens.add(temp);
                    } else {
                        Token temp;
                        temp = generateToken(lexema, "Id",
                                generateDescription(lexema),
                                generateAttributeId(lexema),
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
                    if (i < linea.length() && linea.charAt(i) >= '0' && linea.charAt(i) <= '9') {
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
                        temp = generateToken(lexema, "Entero",
                                "Entero",
                                Integer.valueOf(lexema), line);
                        tokens.add(temp);
                    } else if (regex.esFLOTANTE(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Real",
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
                        temp = generateToken(lexema, "Caracter Simple",
                                generateDescription(lexema),
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
                        temp = generateToken(lexema, "Caracter Simple",
                                generateDescription(lexema),
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
                    temp = generateToken(lexema, "error",
                            " No pertenece a ninguna categoria lexica "
                                    + "en la linea " + line,
                            210, line);
                    errors.add(temp);
                    estado = 0;
                    lexema = "";
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

    public Token getCurrentToken() {
        Token tem = this.array[this.count];
        count++;
        return tem;
    }

}

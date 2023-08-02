package Analizadores;

import ER.Regex;
import FilesManagement.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CodigoIntermedio {
    private final Stack<String> prefixStack;
    private final String[] lines;
    private final Regex regex;
    private final File file;

    public CodigoIntermedio() {
        PrefixNotation prefix = new PrefixNotation();
        prefix.Analyze();
        this.prefixStack = new Stack<>();
        this.regex = new Regex();
        this.file = new File();
        file.openFile("src/Tools/prefix.txt");
        this.lines = file.readFile();
    }

    public void Analyze() {
        for (String line : lines) {
            generateCode(line);
            Cuartetos();
        }
    }


    private void generateCode(String line) {
        int estado = 0;
        String lexema = "";
        for (int i = 0; i <= line.length(); i++) {
            switch (estado) {
                case 0 -> {
                    if (i < line.length() && line.charAt(i)
                            >= 'a' && line.charAt(i) <= 'z') {
                        lexema += line.charAt(i);
                        estado = 1;
                    } else {
                        estado = 3;
                        i--;
                    }
                }

                case 1 -> {
                    if (i < line.length()
                            && regex.esLETRA(line.charAt(i))) {
                        lexema += line.charAt(i);
                    } else {
                        estado = 2;
                        i--;
                    }
                }

                case 2 -> {
                    if (i < line.length() && regex.esID(lexema)) {
                        prefixStack.push(lexema);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }

                case 3 -> {
                    if (i < line.length() && line.charAt(i) >= '0'
                            && line.charAt(i) <= '9') {
                        lexema += line.charAt(i);
                        estado = 4;
                    } else {
                        estado = 7;
                        i--;
                    }
                }

                case 4 -> {
                    if (i < line.length()
                            && regex.esDIGITO(line.charAt(i))) {
                        lexema += line.charAt(i);
                    } else {
                        estado = 5;
                        i--;
                    }
                }

                case 5 -> {
                    if (i < line.length() && line.charAt(i) == '.') {
                        lexema += line.charAt(i);
                        estado = 12;
                    } else {
                        estado = 6;
                        i--;
                    }
                }

                case 12 -> {
                    if (i < line.length() && line.charAt(i)
                            >= '0' && line.charAt(i) <= '9') {
                        lexema += line.charAt(i);
                        estado = 13;
                    } else {
                        lexema += line.charAt(i);
                        estado = 14;
                    }
                }

                case 13 -> {
                    if (i < line.length()
                            && regex.esDIGITO(line.charAt(i))) {
                        lexema += line.charAt(i);
                    } else {
                        estado = 6;
                        i--;
                    }
                }

                case 6 -> {
                    if (regex.esENTERO(lexema)) {
                        prefixStack.push(lexema);
                    } else if (regex.esFLOTANTE(lexema)) {
                        prefixStack.push(lexema);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }

                case 7 -> {
                    if (i < line.length()
                            && regex.esOPERADOR(String.valueOf(
                            line.charAt(i)))) {
                        lexema += line.charAt(i);
                        prefixStack.push(lexema);
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 8;
                        i--;
                    }
                }

                case 8 -> {
                    if (i < line.length() && line.charAt(i) == ' ') {
                        estado = 0;
                    } else {
                        estado = 9;
                        i--;
                    }
                }

                case 9 -> {
                    if (i < line.length()
                            && regex.esDELIMITADOR(line.charAt(i))) {
                        lexema += line.charAt(i);
                        prefixStack.push(lexema);
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 10;
                        i--;
                    }
                }
            }
        }
    }

    private void Cuartetos() {
        Stack<String> pilaOperandos = new Stack<>();
        int temporalCount = 1;
        List<String> cuartetos = new ArrayList<>();
        while (!prefixStack.isEmpty()) {
            String element = prefixStack.pop();
            if (esOperador(element)) {
                String operando1 = pilaOperandos.pop();
                String operando2 = pilaOperandos.pop();
                if (!esReduccionAlgebraica(element, operando1, operando2)) {
                    String temporal = "temp" + temporalCount++;
                    String quartet = element + "\t" + operando1 + "\t" + operando2 + "\t" + temporal;
                    cuartetos.add(quartet);
                    pilaOperandos.push(temporal);
                }else{
                    String temporal = "temp" + temporalCount++;
                    pilaOperandos.push(temporal);
                }
            } else {
                pilaOperandos.push(element);
            }
        }
        StringBuilder codigo =  new StringBuilder();
        for (String cuarteto : cuartetos) {
            codigo.append(cuarteto).append("\n");
        }
        System.out.println(codigo);
        writeFile(codigo.toString());
    }

    private boolean esReduccionAlgebraica(String operador, String operando1, String operando2) {
        if (operador.equals("+") && operando1.equals("0") || operando2.equals("0")) {
            return true; // La operación suma con cero es reducible
        }
        return operador.equals("-") && operando1.equals("0");
    }

    private void writeFile(String codigo) {
        File file = new File();
        file.openFile("src/Tools/codigoIntermedio.txt");
        file.writeFile(codigo);
    }

    private static boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

}

package ER;

/**
 *
 * @author Charl
 */
public interface ER {
    
    public static String ALFABETO  = "(^[a-z][a-z]*|[0-9]*\\.?[0-9]*|"
                                    + "inicio|fin|leer|escribir|"
                                    + "entero|real|\\+|\\-|\\:|\\(|\\)|\\;|\\=|\\,)";
    public static String OPERADOR = "(\\+|\\-|\\=)";
    public static String DELIMITADOR  = "(\\:|\\;|\\(|\\)|\\,)";
    public static String DIGITO = "([0-9])";
    public static String ENTERO = "([0-9]+)";
    public static String FLOTANTE = "(^-?\\d+(?:.\\d+)?$)";
    public static String ID = "(^[a-z][a-z0-9]*)";
    public static String LETRA = "([a-z])";
    public static String MAYUSCULA = "([A-Z])";
    public static String RESERVADA = "(inicio|fin|leer|escribir|entero|real)";
}

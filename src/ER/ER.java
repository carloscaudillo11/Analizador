package ER;

/**
 *
 * @author Charl
 */
public interface ER {
    String ALFABETO  = "([a-z0-9]|\\+|\\-|\\:|\\(|\\)|\\;|\\=|\\,|\\.)";
    String OPERADOR = "(\\+|\\-|\\=)";
    String DELIMITADOR  = "(\\:|\\;|\\(|\\)|\\,)";
    String DIGITO = "([0-9])";
    String ENTERO = "([0-9]+)";
    String FLOTANTE = "(^-?\\d+(?:.\\d+)?$)";
    String ID = "(^[a-z][a-z0-9]*)";
    String LETRA = "([a-z])";
    String MAYUSCULA = "([A-Z])";
    String RESERVADA = "(inicio|fin|leer|escribir|entero|real)";
}

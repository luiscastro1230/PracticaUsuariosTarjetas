package util;

public class Validaciones {

    public static boolean esVacio(String texto) {
        if (texto == null || texto.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean validarEmail(String email) {
        if (esVacio(email)) {
            return false;
        }
        if (email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }

    public static boolean validarEdad(int edad) {
        if (edad > 0 && edad < 120) {
            return true;
        }
        return false;
    }

    public static boolean validarTipo(String tipo) {
        if (tipo == null) {
            return false;
        }
        if (tipo.equalsIgnoreCase("credito") || tipo.equalsIgnoreCase("debito")) {
            return true;
        }
        return false;
    }
}
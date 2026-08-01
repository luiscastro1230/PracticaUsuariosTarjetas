package util;

import java.util.Random;

public class GeneradorID {

    public static String generarNumeroTarjeta() {
        Random r = new Random();
        String numero = "";
        for (int i = 0; i < 4; i++) {
            if (i != 0) {
                numero = numero + " ";
            }
            for (int j = 0; j < 4; j++) {
                numero = numero + r.nextInt(10);
            }
        }
        return numero;
    }
}

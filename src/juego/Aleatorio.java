package juego;

import java.util.Random;

/**
 * Clase con métodos utilitarios para número aleatorios.
 */
public class Aleatorio {

    private static  Random random = new Random();

    /**
     * Devuelve un número decimal aleatorio entre los límites especificados.
     * @param min el mínimo
     * @param max el máximo
     * @return el número de aleatorio
     */
    public static double decimalRandom(double min, double max){
        if(min > max){
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        final var rango = max - min;
        return random.nextDouble() * rango + min;
    }

    public static int enteroRandom(int min, int max){
        if(min > max){
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        final var rango = max - min;
        // max - min - 1 + min = max - 1
        return random.nextInt(rango) + min;
    }
}

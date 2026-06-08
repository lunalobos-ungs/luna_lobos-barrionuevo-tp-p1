package juego;

import java.util.Random;

/**
 * Clase con métodos utilitarios para número aleatorios.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Aleatorio {

    private static final Random random = new Random();

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

    /**
     * Devuelve un entero aleatorio que desde min inclusive hasta max (no inclusive).
     *
     * @param min el valor mínimo (incluido)
     * @param max el máximo (excluido)
     * @return el entero aleatorio
     */
    public static int enteroRandom(int min, int max){
        if(min > max){
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        final var rango = max - min;
        // max - min - 1 + min = max - 1
        if(rango == 0) {
            return min;
        }
        return random.nextInt(rango) + min;
    }
}

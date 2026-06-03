package juego;

/**
 * Clase con métodos utilitarios para número aleatorios.
 */
public class Aleatorio {

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
        return Math.random() * rango + min;
    }
}

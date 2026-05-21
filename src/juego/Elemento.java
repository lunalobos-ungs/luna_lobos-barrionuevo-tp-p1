package juego;

import entorno.Entorno;

/**
 * Todos los elementos que se renderizan deberán implementar esta interfaz.
 *
 * @author Miguel Angel Luna Lobos
 */
public interface Elemento {

    /**
     * Un identificador único para el elemento.
     * @return el identificador del elemento
     */
    Integer id();

    /**
     * El tipo del elemento (por ejemplo: princesa, isla flotante, enemigo, proyectil, etc)
     * @return el tipo del elemento
     */
    String tipo();

    /**
     * El ángulo de dirección del elemento.
     * @return el ángulo del elemento
     */
    double angulo();

    /**
     * La coordenada x del centro del elemento.
     * @return la coordenada x del elemento
     */
    double x();

    /**
     * La coordenada y del centro del elemento.
     * @return la coordenada y del elemento
     */
    double y();

    /**
     * Devuelve true cuando las coordenadas introducidas están dentro de los límites del elemento.
     * @param x la coordenada x a verificar
     * @param y la coordenada y a verificar
     */
    void estaContenido(double x, double y);

    /**
     * Renderiza el elemento en pantalla.
     *
     * @param entorno el entorno
     */
    void dibujar(Entorno entorno);

    /**
     * Mueve el elemento en el ángulo indicado.
     *
     * @param angulo el ángulo en radianes
     */
    void mover(float angulo);

    /**
     * Para aquellos casos en los que haya colisiones u otros eventos,
     * la idea es llamar a este método en cada tick. Las implementaciones
     * deben detectar de que evento se trata y actuar en consecuencia.
     *
     * @param entorno el entorno
     * @param contexto el contexto
     */
    void reaccionar(Entorno entorno, Contexto contexto);

    /**
     * Cambia las propiedades del elemento argumento si este está en colisión.
     * @param elemento el elemento que sufre los efectos de la colisión
     */
    void actuar(Elemento elemento);

}

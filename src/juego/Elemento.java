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
    int id();

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
    void mover(float angulo, Entorno entorno);

    /**
     * Cambia las propiedades del elemento argumento si este está en colisión.
     * @param elemento el elemento que sufre los efectos de la colisión
     */
    void actuar(Elemento elemento);

    /**
     * Establece el angulo.
     * @param angulo el nuevo angulo
     */
    void establecerAngulo(double angulo);

    /**
     * Establece la coordenada x
     * @param x la nueva coordenada x
     */
    void establecerX(double x);

    /**
     * Establece la coordenada y.
     * @param y la nueva coordenada y
     */
    void establecerY(double y);

    /**
     * La idea de este método es que las implementaciones lo usen cuando
     * necesiten que ocurra un cambio que no solo involucre el ángulo,
     * o las coordenadas x o y. Por ejemplo, que cuando un enemigo muere
     * muestre otra imagen, o que cuando la princesa está saltando muestre
     * otra imágen, etc. Quien llame a este método deberá colocar un mensaje
     * válido, esto dependerá de la implementación y del contexto en cual
     * se llame al método.
     * @param mensaje el mensaje que recibe el elemento, debe ser válido
     * @param entorno el entorno
     */
    void recibirMensaje(String mensaje, Entorno entorno);
}

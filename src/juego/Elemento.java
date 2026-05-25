package juego;

import entorno.Entorno;

/**
 * Todos los elementos que se renderizan deberán implementar esta interfaz.
 *
 * @author Miguel Angel Luna Lobos
 */
public interface Elemento extends Rectangulo{

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
     * Renderiza el elemento en pantalla.
     *
     * @param entorno el entorno
     */
    void dibujar(Entorno entorno);

    /**
     * Actualiza la posición del elemento.
     *
     * @param entorno el entorno del juego
     */
    void mover(Entorno entorno);

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
     * muestre otra imagen. Quien llame a este método deberá colocar un
     * mensaje válido, esto dependerá de la implementación y del contexto
     * en cual se llame al método.
     * @param mensaje el mensaje que recibe el elemento, debe ser válido
     */
    void recibirMensaje(String mensaje);

    /**
     * Dispara un proyectil desde la posición del elemento hacia el cursor del mouse.
     * Por defecto lanza {@link UnsupportedOperationException}; las subclases que
     * soporten esta operación deben sobreescribir este método.
     *
     * @param contexto     el contexto del juego donde se agrega el proyectil
     * @param entorno      el entorno del juego
     * @param generadorId  el generador de IDs para el nuevo proyectil
     */
    default void disparar(Contexto contexto, Entorno entorno, GeneradorId generadorId){
        throw new UnsupportedOperationException("sin implementar");
    }
}

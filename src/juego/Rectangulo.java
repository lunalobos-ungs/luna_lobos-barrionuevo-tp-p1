package juego;

/**
 * Modela un rectángulo centrado en un punto (x, y) con un ancho y alto dados.
 * Los bordes se calculan como desplazamientos desde el centro.
 *
 * @author Miguel Angel Luna Lobos
 */
public interface Rectangulo {

    /**
     * @return la coordenada x del centro
     */
    double x();

    /**
     * @return la coordenada y del centro
     */
    double y();

    /**
     * @return el ancho del rectángulo
     */
    double ancho();

    /**
     * @return el alto del rectángulo
     */
    double alto();

    /**
     * Establece la coordenada x del centro.
     *
     * @param x la nueva coordenada x
     */
    void establecerX(double x);

    /**
     * Establece la coordenada y del centro.
     *
     * @param y la nueva coordenada y
     */
    void establecerY(double y);

    /**
     * Establece el ancho del rectángulo.
     *
     * @param ancho el nuevo ancho del elemento
     */
    void establecerAncho(double ancho);

    /**
     * Establece el alto del rectángulo.
     *
     * @param alto el nuevo alto del elemento
     */
    void establecerAlto(double alto);

    /**
     * @return la coordenada y del borde superior ({@code y - alto / 2})
     */
    default double bordeSuperior() {
        return y() - alto() / 2.0;
    }

    /**
     * @return la coordenada y del borde inferior ({@code y + alto / 2})
     */
    default double bordeInferior() {
        return y() + alto() / 2.0;
    }

    /**
     * @return la coordenada x del borde derecho ({@code x + ancho / 2})
     */
    default double bordeDerecho() {
        return x() + ancho() / 2.0;
    }

    /**
     * @return la coordenada x del borde izquierdo ({@code x - ancho / 2})
     */
    default double bordeIzquierdo() {
        return x() - ancho() / 2.0;
    }
}

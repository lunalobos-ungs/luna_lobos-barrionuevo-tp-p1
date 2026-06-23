package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Clase para trabajar con colisiones.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Rectangulo {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    public Rectangulo(double x, double y, double ancho, double alto) {
        super();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double ancho() {
        return ancho;
    }

    public double alto() {
        return alto;
    }

    /**
     * @return la coordenada y del borde superior (y - alto / 2)
     */
    double bordeSuperior() {
        return y() - alto() / 2.0;
    }

    /**
     * @return la coordenada y del borde inferior (y + alto / 2)
     */
    double bordeInferior() {
        return y() + alto() / 2.0;
    }

    /**
     * @return la coordenada x del borde derecho (x + ancho / 2)
     */
    double bordeDerecho() {
        return x() + ancho() / 2.0;
    }

    /**
     * @return la coordenada x del borde izquierdo (x - ancho / 2)
     */
    double bordeIzquierdo() {
        return x() - ancho() / 2.0;
    }

    /**
     * Dibuja el rectángulo con un color dado.
     *
     * @param entorno el entorno
     * @param color   el color de relleno
     */
    void dibujarRectangulo(Entorno entorno, Color color) {
        entorno.dibujarRectangulo(x(), y(), ancho(), alto(), 0.0, color);
    }

    public Rectangulo escalar(double proporcionX, double proporcionY) {
        return new Rectangulo(x, y, ancho * proporcionX, alto * proporcionY);
    }

    /**
     * Devuelve el área del rectángulo.
     *
     * @return el área del rectángulo
     */
    public double area() {
        return ancho() * alto();
    }
}

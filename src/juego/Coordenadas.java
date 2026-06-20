package juego;

import entorno.Entorno;

/**
 * Clase usada para guardar coordenadas x, y. Pensada para trabajar
 * con transformaciones entre sistemas de coordenadas.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Coordenadas {

    /**
     * Transforma el punto x, y provisto por uno relativo a la ubicación de la princesa.
     * @param x la coordenada x
     * @param y la coordenada y
     * @param mundo el mundo
     * @param entorno el entorno
     * @return las coordenadas relativas a la princesa
     */
    public static Coordenadas transformar(double x, double y, Mundo mundo, Entorno entorno) {
        Rectangulo rectanguloPrincesa = mundo.princesa().rectangulo();
        double dx = entorno.ancho() / 2.0;
        double dy = entorno.alto() / 2.0;
        return new Coordenadas(x - rectanguloPrincesa.x() + dx, y - rectanguloPrincesa.y() + dy);
    }

    private  double x;
    private  double y;

    public Coordenadas(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * La coordenada x.
     * @return la coordenada x
     */
    public double x() {
        return x;
    }

    /**
     * La coordenada y.
     * @return la coordenada y
     */
    public double y() {
        return y;
    }
}

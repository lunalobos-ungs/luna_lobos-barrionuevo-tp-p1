package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Representa al jefe.
 */
public class Jefe {
    private double x;
    private double y;
    private final double ancho;
    private final double alto;
    private final Image jefe;
    private double angulo;
    private final double velocidad;
    private boolean vivo = true;

    Jefe(double x, double y, double ancho, double alto, Image jefe) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.jefe = jefe;
        velocidad = 1.0;
        angulo = Math.PI / 2.0;
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
     * Dibuja al jefe.
     *
     * @param entorno el entorno
     * @param mundo   el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        final var coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        final var x = coordenadasRelativas.x();
        final var y = coordenadasRelativas.y();
        entorno.dibujarImagen(jefe, x, y, 0);
    }

    public void mover(Entorno entorno) {
        if (angulo == 0) {
            x = x + velocidad;
        } else {
            x = x - velocidad;
        }
    }

    /**
     * Recibe mensajes.
     *
     * @param mensaje el mensaje
     */
    public void recibirMensaje(String mensaje) {
        if (mensaje.equals("morir")) {
            vivo = false;
        }
    }

    /**
     * Indica si debe eliminarse.
     * @return true si debe eliminarse, falso de lo contrario
     */
    public boolean debeEliminarse() {
        return !vivo;
    }

    /**
     * El rectángulo de colisión.
     * @return el rectángulo de colisión
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }

}

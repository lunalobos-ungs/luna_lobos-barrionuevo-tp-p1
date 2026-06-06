package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Clase del castillo.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Castillo {
    private double x;
    private double y;
    private final double ancho;
    private final double alto;
    private final Image castillo;

    /**
     * Crea un nuevo castillo.
     * @param x la coordenada x
     * @param y la coordenada y
     * @param ancho el ancho
     * @param alto el alto
     * @param castillo la imagen del castillo
     */
    public Castillo(double x, double y, double ancho, double alto, Image castillo){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.castillo = castillo;

    }

    /**
     * Dibuja el castillo.
     * @param entorno el entorno
     * @param mundo el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo){
        final var coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        final var x = coordenadasRelativas.x();
        final var y = coordenadasRelativas.y();
        entorno.dibujarImagen(castillo, x, y, 0);
    }

    /**
     * Traslada el castillo a nuevo punto x, y.
     * @param x la coordenada x
     * @param y la coordenada y
     */
    public void trasladar(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double alto(){
        return alto;
    }

    public Rectangulo rectangulo(){
        return new Rectangulo(x, y, ancho * 0.2, alto);
    }
}

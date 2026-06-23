package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Proyectil disparado por la princesa en dirección al cursor del mouse.
 * Se mueve en línea recta a velocidad constante y rebota contra la tierra firme.
 *
 * @author Miguel Angel Luna Lobos
 */
public class ProyectilPrincesa {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double sen;
    private double cos;
    private Image proyectil;
    private double velocidad;

    /**
     * Crea un proyectil en la posición indicada con la dirección definida por las
     * componentes trigonométricas del ángulo de disparo.
     *
     * @param x   coordenada x inicial
     * @param y   coordenada y inicial
     * @param cos coseno del ángulo de disparo (componente horizontal)
     * @param sen seno del ángulo de disparo (componente vertical)
     */
    public ProyectilPrincesa(double x, double y, double cos, double sen) {
        this.x = x;
        this.y = y;
        this.sen = sen;
        this.cos = cos;
        this.ancho = 15.0;
        this.alto = 15.0;
        this.velocidad = 8.0;
        this.proyectil = Juego.cargarYEscalar("proyectil_princesa.png", ancho, alto);
    }

    /**
     * Dibuja el proyectil.
     *
     * @param entorno  el entorno
     * @param princesa la princesa
     */
    public void dibujar(Entorno entorno, Princesa princesa) {
        double x = Juego.transformarX(this.x, princesa, entorno);
        double y = Juego.transformarY(this.y, princesa, entorno);
        entorno.dibujarImagen(proyectil, x, y, 0);
    }

    /**
     * Ejecuto el movimiento del proyectil.
     */
    public void mover() {
        x += velocidad * cos;
        y += velocidad * sen;
    }

    public void reboteVertical() {
        sen = -sen;
    }

    public void reboteHorizontal() {
        cos = -cos;
    }

    /**
     * El rectángulo de colisión.
     *
     * @return el rectángulo de colisión
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }
}

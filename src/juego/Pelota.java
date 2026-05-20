package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Clase del pong. No la vamos a usar directamente, pero su código quizás nos sirva.
 */
public class Pelota {

    private final double diametro;
    private final double radio;
    private double x;
    private double y;

    private final Color color;

    private final double velocidad;

    private double angulo;

    public Pelota(double x, double y) {
        this.x = x;
        this.y = y;
        color = Color.WHITE;
        diametro = 40.0;
        velocidad = 3.0;
        angulo = -Math.PI/4;
        radio = diametro / 2;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(x, y, diametro, color);
    }

    public void mover() {
        x = x + velocidad * Math.cos(angulo);
        y = y + velocidad * Math.sin(angulo);
    }

    public void rebotar(Entorno entorno) {
        if( y - radio <= 0 || y + radio >= entorno.alto()){
            angulo = angulo * (-1);
        } else {
            angulo = Math.PI - angulo;
        }
    }

    public boolean colisionBorde(Entorno entorno) {
        return x - radio <= 0 || x + radio >= entorno.ancho() || y - radio <= 0 || y + radio >= entorno.alto();
    }

    public double getDiametro() {
        return diametro;
    }

    public double getRadio() {
        return radio;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public double getAngulo() {
        return angulo;
    }
}

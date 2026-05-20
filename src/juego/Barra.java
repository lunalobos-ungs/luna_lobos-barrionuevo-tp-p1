package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Clase del pong. No la vamos a usar directamente, pero su código quizás nos sirva.
 */
public class Barra {

    private double x;
    private double y;

    private double ancho;
    private double alto;

    private double velocidad;

    private Color color;

    public Barra(double x, double y) {
        this.x = x;
        this.y = y;
        ancho = 100.0;
        alto = 10.0;
        color = Color.WHITE;
        velocidad = 3;
    }

    public void dibujar(Entorno entorno){
        entorno.dibujarRectangulo(x,y,ancho,alto,0, color);
    }

    public void mover(){
        x = x + velocidad;
    }
}

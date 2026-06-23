package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Representa al jefe.
 *
 * @author Noelia Barrionuevo
 * @author Miguel Angel Luna Lobos
 */
public class Jefe {

    public static double altoJefe = 130;
    public static double anchoJefe = 120;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private Image jefeHaciaDerecha;
    private Image jefeHaciaIzquierda;
    private Image jefe;
    private double cos;
    private double velocidad;
    private int vidas;
    private Isla isla;

    public Jefe(double x, double y, double ancho, double alto, Image jefeHaciaDerecha, Image jefeHaciaIzquierda) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.jefeHaciaDerecha = jefeHaciaDerecha;
        this.jefeHaciaIzquierda = jefeHaciaIzquierda;
        this.jefe = jefeHaciaIzquierda;
        velocidad = 1.0;
        cos = Math.cos(Math.PI);
        isla = null;
        vidas = 10;
    }

    public void establecerIsla(Isla isla){
        this.isla = isla;
        this.x = isla.x();
        this.y = isla.y() - alto / 2.0 - Isla.altoIsla / 2.0;
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

    public int vidas(){
        return vidas;
    }

    /**
     * Dibuja al jefe.
     *
     * @param entorno el entorno
     * @param mundo   el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        double x = Juego.transformarX(this.x, mundo, entorno);
        double y = Juego.transformarY(this.y, mundo, entorno);
        Rectangulo rectanguloRojo = new Rectangulo(x, y - alto / 2.0, 100, 10);
        double proporcion = vidas / 10.0;
        double diferencia = rectanguloRojo.ancho() - proporcion * 100;
        Rectangulo rectanguloNegro = new Rectangulo(x - diferencia / 2, y - alto / 2.0, 100 * proporcion, 10);
        rectanguloRojo.dibujarRectangulo(entorno, Color.RED);
        rectanguloNegro.dibujarRectangulo(entorno, Color.BLACK);
        entorno.dibujarImagen(jefe, x, y, 0);
    }

    public void mover() {
        if(x <= isla.rectangulo().bordeIzquierdo() || x >= isla.rectangulo().bordeDerecho()){
            cos = -cos;
            if(cos < 0){
                jefe = jefeHaciaIzquierda;
            } else {
                jefe = jefeHaciaDerecha;
            }
        }
        x += velocidad * cos;
    }

    public void pierdeUnaVida(){
        vidas--;
    }

    /**
     * El rectángulo de colisión.
     * @return el rectángulo de colisión
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }

}

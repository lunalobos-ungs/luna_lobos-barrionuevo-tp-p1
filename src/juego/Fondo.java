package juego;

import entorno.Entorno;

import java.awt.*;
//import java.time.Instant;

/**
 * Clase creada para el fondo del juego.
 *
 * @author Noelia Barrionuevo
 */
public class Fondo {
    private  double x;
    private  double y;
    private  Image fondo;

    /**
     * Crea un nuevo fondo.
     * @param x la coordenada x
     * @param y la coordenada y
     * @param fondo la imagen del fondo
     */
    public Fondo(double x, double y, Image fondo) {
        this.x = x;
        this.y = y;
        this.fondo = fondo;
    }

    /**
     * Dibuja el fondo.
     * @param entorno el entorno
     */
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(fondo, x, y, 0);
    }
}

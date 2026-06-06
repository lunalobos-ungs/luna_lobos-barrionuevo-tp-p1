package juego;

import entorno.Entorno;

import java.awt.*;
import java.time.Instant;

/**
 * Representa a un enemigo del juego.
 *
 * @author Noelia Barrionuevo
 * @author Miguel Angel Luna Lobos
 */
public class Enemigo {
    private double x;
    private final double y;
    private final double ancho;
    private final double alto;
    private final Image enemigo;
    private final int id;
    private final double velocidad;
    private final double angulo;
    private boolean vivo = true;

    /**
     * Crea un nuevo enemigo.
     * @param generadorId el generador de identificadores únicos
     * @param x la coordenada x
     * @param y la coordenada y
     * @param ancho el ancho
     * @param alto el alto
     * @param angulo el ángulo
     * @param enemigo la imagen del enemigo
     */
    Enemigo(GeneradorId generadorId, double x, double y, double ancho, double alto, double angulo, Image enemigo) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.enemigo = enemigo;

        id = generadorId.nuevoId();
        velocidad = 1.0;
        this.angulo = angulo;
    }

    /**
     * El identificador del enemigo.
     * @return el identificador del enemigo
     */
    public int id() {
        return id;
    }

    /**
     * Dibuja al enemigo.
     * @param entorno el entorno
     * @param mundo el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        final var coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        final var x = coordenadasRelativas.x();
        final var y = coordenadasRelativas.y();
        entorno.dibujarImagen(enemigo, x, y, 0);
    }

    /**
     * Mueve al enemigo.
     */
    public void mover() {
        x = x + velocidad * Math.cos(angulo);
    }

    /**
     * Recibe un mensaje.
     * @param mensaje
     */
    public void recibirMensaje(String mensaje) {
        if (mensaje.equals("morir")) {
            vivo = false;
        }
    }

    /**
     * Indica si este enemigo debe eliminarse.
     * @return true si debe eliminarse, false de lo contrario
     */
    public boolean debeEliminarse() {
        return !vivo;
    }

    /**
     * El rectángulo de colisión del enemigo.
     * @return el rectángulo de colisión del enemigo
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }
}

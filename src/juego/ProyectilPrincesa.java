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
    private final double ancho;
    private final double alto;
    private double sen;
    private double cos;
    private final Image proyectil;
    private final double velocidad;

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
        Image proyectilOriginal = Imagenes.cargar("proyectil_princesa.png");
        this.proyectil = Imagenes.escalar(proyectilOriginal, ancho, alto);
    }

    /**
     * Dibuja el proyectil.
     *
     * @param entorno el entorno
     * @param mundo   el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        final var coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        final var x = coordenadasRelativas.x();
        final var y = coordenadasRelativas.y();
        entorno.dibujarImagen(proyectil, x, y, 0);
    }

    /**
     * Ejecuto el movimiento del proyectil.
     */
    public void mover() {
        x += velocidad * cos;
        y += velocidad * sen;
    }

    /**
     * Recibe un mensaje.
     *
     * @param mensaje el mensaje
     */
    public void recibirMensaje(String mensaje) {
        switch (mensaje) {
            case "rebotar desde arriba":
            case "rebotar desde abajo":
                // la superficie de impacto es horizontal: se invierte la componente vertical
                sen = -sen;
                break;
            case "rebotar desde la derecha":
            case "rebotar desde la izquierda":
                // la superficie de impacto es vertical: se invierte la componente horizontal
                cos = -cos;
                break;
            default:
                throw new IllegalArgumentException("el proyectil no entiende el mensaje %s".formatted(mensaje));
        }
    }

    /**
     * El rectángulo de colisión.
     * @return el rectángulo de colisión
     */
    public Rectangulo rectangulo() {
        return new Rectangulo (x,y,ancho,alto);
    }
}

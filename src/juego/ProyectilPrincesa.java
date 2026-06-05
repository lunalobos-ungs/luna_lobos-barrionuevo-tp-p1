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

    private final int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double sen;
    private double cos;
    private final Image proyectil;
    private final double velocidad;
    private boolean activo = true;


    /**
     * Crea un proyectil en la posición indicada con la dirección definida por las
     * componentes trigonométricas del ángulo de disparo.
     *
     * @param x           coordenada x inicial
     * @param y           coordenada y inicial
     * @param cos         coseno del ángulo de disparo (componente horizontal)
     * @param sen         seno del ángulo de disparo (componente vertical)
     * @param generadorId generador de IDs para asignar un identificador único
     */
    public ProyectilPrincesa(double x, double y, double cos, double sen, GeneradorId generadorId) {
        this.id = generadorId.nuevoId();
        this.x = x;
        this.y = y;
        this.sen = sen;
        this.cos = cos;
        this.ancho = 15.0;
        this.alto = 15.0;

        this.velocidad = 8.0;
        Image proyectilOriginal = Imagenes.cargarImagen("proyectil_princesa.png");
        this.proyectil = Imagenes.escalar(proyectilOriginal, ancho, alto);
    }

    public int id() {
        return id;
    }

    public String tipo() {
        return "proyectil";
    }


    public double angulo() {
        throw new UnsupportedOperationException("los proyectiles no almacenan su ángulo");
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


    public void establecerAncho(double ancho) {
        this.ancho = ancho;
    }


    public void establecerAlto(double alto) {
        this.alto = alto;
    }


    public void dibujar(Entorno entorno, Mundo mundo) {
        final var princesa = mundo.princesa();
        final var dx = entorno.ancho()/2.0;
        final var dy = entorno.alto()/2.0;
        final var x = this.x - princesa.x() + dx;
        final var y = this.y - princesa.y() + dy;
        entorno.dibujarImagen(proyectil, x, y, 0);
    }

    public void mover(Entorno entorno) {
        x += velocidad * cos;
        y += velocidad * sen;
    }

    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("no se puede cambiar el ángulo de un misil");
    }


    public void establecerX(double x) {
        throw new UnsupportedOperationException("no se puede cambiar las coordenadas de un misil");
    }


    public void establecerY(double y) {
        throw new UnsupportedOperationException("no se puede cambiar las coordenadas de un misil");
    }


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


    public boolean debeEliminarse() {
        return !activo;
    }

    public Rectangulo rectangulo() {
        return new Rectangulo (x,y,ancho,alto);
    }
}

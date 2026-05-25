package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Proyectil disparado por la princesa en dirección al cursor del mouse.
 * Se mueve en línea recta a velocidad constante y rebota contra la tierra firme.
 *
 * @author Miguel Angel Luna Lobos
 */
public class ProyectilPrincesa implements Elemento {

    private final int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double sin;
    private double cos;
    private final Image proyectil;
    private final double velocidad;

    /**
     * Crea un proyectil en la posición indicada con la dirección definida por las
     * componentes trigonométricas del ángulo de disparo.
     *
     * @param x           coordenada x inicial
     * @param y           coordenada y inicial
     * @param cos         coseno del ángulo de disparo (componente horizontal)
     * @param sin         seno del ángulo de disparo (componente vertical)
     * @param generadorId generador de IDs para asignar un identificador único
     */
    public ProyectilPrincesa(double x, double y, double cos, double sin, GeneradorId generadorId) {
        this.id = generadorId.nuevoId();
        this.x = x;
        this.y = y;
        this.sin = sin;
        this.cos = cos;
        this.ancho = 15.0;
        this.alto = 15.0;
        this.velocidad = 8.0;
        Image proyectilOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("proyectil.png"))).getImage();
        this.proyectil = proyectilOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return "proyectil";
    }

    @Override
    public double angulo() {
        throw new UnsupportedOperationException("los proyectiles no almacenan su ángulo");
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double ancho() {
        return ancho;
    }

    @Override
    public double alto() {
        return alto;
    }

    @Override
    public void establecerAncho(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public void establecerAlto(double alto) {
        this.alto = alto;
    }

    @Override
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(proyectil, x, y, 0);
    }

    @Override
    public void mover(Entorno entorno) {
        x += velocidad * cos;
        y += velocidad * sin;
    }


    @Override
    public void actuar(Elemento elemento) {
        switch (elemento.tipo()) {
            case "enemigo":
            case "jefe":
                elemento.recibirMensaje("morir");
                break;
        }
    }

    @Override
    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("no se puede cambiar el ángulo de un misil");
    }

    @Override
    public void establecerX(double x) {
        throw new UnsupportedOperationException("no se puede cambiar las coordenadas de un misil");
    }

    @Override
    public void establecerY(double y) {
        throw new UnsupportedOperationException("no se puede cambiar las coordenadas de un misil");
    }

    @Override
    public void recibirMensaje(String mensaje) {
        switch (mensaje){
            case "rebotar desde arriba":
            case "rebotar desde abajo":
                // la superficie de impacto es horizontal: se invierte la componente vertical
                sin = -sin;
                break;
            case "rebotar desde la derecha":
            case "rebotar desde la izquierda":
                // la superficie de impacto es vertical: se invierte la componente horizontal
                cos = -cos;
                break;
            default: throw new IllegalArgumentException("el proyectil no entiende el mensaje %s".formatted(mensaje));
        }
    }
}

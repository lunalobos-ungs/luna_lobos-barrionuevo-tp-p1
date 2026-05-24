package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class Princesa implements Elemento {

    private final Image princesaOriginal;
    private Image princesa;
    private double x;
    private double y;
    private double ancho;
    private double alto;

    private double angulo;

    private final int id;

    private final double velocidad;

    private double velocidadCaidaLibre;

    private final double velocidadSalto;

    private boolean enSalto;

    private double aceleracionGravitatoria;
    private Instant marcaTemporalDeCaida;

    public Princesa(GeneradorId generadorId, Entorno entorno) {
        x = entorno.ancho() / 2.0;
        y = 0;
        ancho = 100.0;
        alto = 100.0;
        princesaOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("princesa.png"))).getImage();
        princesa = princesaOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
        id = generadorId.nuevoId();
        velocidad = 3.0;
        velocidadCaidaLibre = 5.0;
        velocidadSalto = 8.0;
        enSalto = false;
        angulo = 0.0;
        aceleracionGravitatoria = 10.0;
        cayendo();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return "princesa";
    }

    @Override
    public double angulo() {
        return angulo;
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
        return ancho * 0.55;
    }

    @Override
    public double alto() {
        return alto * 0.80;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
        this.princesa = princesaOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    @Override
    public void estaContenido(double x, double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(princesa, x + 5, y, 0, 1);
    }

    @Override
    public void mover(Entorno entorno) {
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            angulo = 0;
            movimientoLateral();
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            angulo = Math.PI;
            movimientoLateral();
        }
        if (entorno.sePresiono('a') && aceleracionGravitatoria <= 0.1) {
            enSalto = true;
            cayendo();
        }
        gravedad(entorno);
        if (enSalto) {
            movimiento(-Math.PI / 2, velocidadSalto);
        }
    }

    private void movimientoLateral() {
        if(aceleracionGravitatoria <= 0.1){
            movimiento(angulo, velocidad * 0.75);
        } else {
            movimiento(angulo, velocidad);
        }
    }

    /**
     * Un método para efectivamente mover a la princesa sin condiciones
     *
     * @param angulo
     * @param velocidad
     */
    private void movimiento(double angulo, double velocidad) {
        x += Math.cos(angulo) * velocidad;
        y += Math.sin(angulo) * velocidad;
    }

    /**
     * La princesa sufre los efectos de la gravedad
     */
    private void gravedad(Entorno entorno) {
        if(aceleracionGravitatoria <= 0.1){
            marcaTemporalDeCaida = Instant.now();
        } else {
            double lapso = Instant.now().toEpochMilli() - marcaTemporalDeCaida.toEpochMilli();
            velocidadCaidaLibre = aceleracionGravitatoria * lapso / 1000;
            movimiento(Math.PI / 2, velocidadCaidaLibre);
        }
        aceleracionGravitatoria = 10.0;
    }

    @Override
    public void actuar(Elemento elemento) {
        // la princesa no actua sobre otros elementos directamente
    }

    @Override
    public void establecerAngulo(double angulo) {
        this.angulo = angulo;
    }

    @Override
    public void establecerX(double x) {
        this.x = x;
    }

    @Override
    public void establecerY(double y) {
        this.y = y;
    }

    @Override
    public void recibirMensaje(String mensaje) {
        switch (mensaje) {
            case "morir": // :C
                cayendo();
                break;
            case "estas en tierra firme":
                enTierraFirme();
                break;
            default:
                throw new IllegalArgumentException("así no se le habla a la princesa -> mensaje: " + mensaje);
        }
    }

    private void cayendo() {
        marcaTemporalDeCaida = Instant.now();
        velocidadCaidaLibre = 0.0;
    }

    private void enTierraFirme() {
        aceleracionGravitatoria = 0.0;
        enSalto = false;
        marcaTemporalDeCaida = null;
        velocidadCaidaLibre = 0.0;
    }
}

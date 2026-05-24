package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TierraFirme implements Elemento{
    private int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private final Image tierraOriginal;
    private Image tierra;
    public TierraFirme(GeneradorId generadorId, Entorno entorno){
        this.id = generadorId.nuevoId();
        ancho = 500.0;
        alto = 50.0;
        x = entorno.ancho() / 2.0;
        y = entorno.alto() - alto;
        tierraOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("tierra.png"))).getImage();
        tierra = tierraOriginal.getScaledInstance((int)ancho, (int)alto, Image.SCALE_DEFAULT);
    }
    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return "tierra firme";
    }

    @Override
    public double angulo() {
        return 0;
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
        tierra = tierraOriginal.getScaledInstance((int)ancho, (int)alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void establecerAlto(double alto) {
        this.alto = alto;
        tierra = tierraOriginal.getScaledInstance((int)ancho, (int)alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void estaContenido(double x, double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(tierra, x, y, 0);
    }

    @Override
    public void mover(float angulo, Entorno entorno) {
        throw new UnsupportedOperationException("no implementado");
    }

    @Override
    public void mover(Entorno entorno) {
        // por el momento la tierra no se mueve
    }

    @Override
    public void actuar(Elemento elemento) {
        if(elemento.tipo().equals("princesa")){
            elemento.recibirMensaje("estas en tierra firme");
        }
    }

    @Override
    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("la tierra firme no admite cambios de ángulo");
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
    public void recibirMensaje(String mensaje, Entorno entorno) {
        // la tierra firme es inerte
    }
}

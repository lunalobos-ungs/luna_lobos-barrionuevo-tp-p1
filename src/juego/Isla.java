package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Representa una isla flotante del juego. Implementación provisional;
 * la mayoría de sus métodos aún no están implementados.
 *
 * @author Noelia Barrionuevo
 */
public class Isla implements Elemento {
    private int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private final Image tierraOriginal;
    private Image tierra;

    Isla(GeneradorId generadorId, Entorno entorno, int x, int y, int ancho) {
        this.id = generadorId.nuevoId();
        // x , y ; cordenadas en el plano
        this.x = x;
        this.y = y;
        //ancho en x de la isla
        this.ancho = ancho;
        // alto de la isla en y; fijo
        alto = 20;
        tierraOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("tierra.png"))).getImage();
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);

    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return "isla";
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
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void establecerAlto(double alto) {
        this.alto = alto;
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(tierra, x, y, 0);
    }

    @Override
    public void mover(Entorno entorno) {

    }

    /*
     * Detecta colision de la princesa con parte "desde arriba" de la isla
     * falta detectar colision de la princesa con la isla, desde abajo y bordes
     */
    @Override
    public void actuar(Elemento elemento) {

        switch (elemento.tipo()) {
            case "princesa":
            case "jefe":
                actuarSobreCuerpo(elemento);
                break;
            case "proyectil":
                actuarSobreProyectil(elemento);
                break;
        }
    }

    private void actuarSobreCuerpo(Elemento elemento){
        String tipoDeColision = Mundo.tipoDeColision(elemento, this);

        switch (tipoDeColision){
            case "desde arriba":
                elemento.recibirMensaje("estas en tierra firme");
                break;
            case "desde abajo":
                elemento.recibirMensaje("chocaste con el techo");
                break;
            case "desde la derecha":
                elemento.recibirMensaje("chocaste con un muro desde tu derecha");
                break;
            case "desde la izquierda":
                elemento.recibirMensaje("chocaste con un muro desde tu izquierda");
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no válido".formatted(tipoDeColision));
        }
    }

    private void actuarSobreProyectil(Elemento elemento){
        String tipoDeColision = Mundo.tipoDeColision(elemento, this);
        switch (tipoDeColision){
            case "desde arriba":
                elemento.recibirMensaje("rebotar desde arriba");
                break;
            case "desde abajo":
                elemento.recibirMensaje("rebotar desde abajo");
                break;
            case "desde la derecha":
                elemento.recibirMensaje("rebotar desde la derecha");
                break;
            case "desde la izquierda":
                elemento.recibirMensaje("rebotar desde la izquierda");
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no soportado".formatted(tipoDeColision));
        }
    }

    @Override
    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void establecerX(double x) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void establecerY(double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void recibirMensaje(String mensaje) {
        // las islas son inalterables
    }
}

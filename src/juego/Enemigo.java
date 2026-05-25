package juego;

import entorno.Entorno;

/**
 * Representa un enemigo del juego. Implementación provisional;
 * la mayoría de sus métodos aún no están implementados.
 *
 */
public class Enemigo implements Elemento {
    @Override
    public int id() {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public String tipo() {
        return "enemigo";
    }

    @Override
    public double angulo() {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public double x() {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public double y() {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public double ancho() {
        return 0;
    }

    @Override
    public double alto() {
        return 0;
    }

    @Override
    public void establecerAncho(double ancho) {

    }

    @Override
    public void establecerAlto(double alto) {

    }

    @Override
    public void dibujar(Entorno entorno) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void mover(Entorno entorno) {

    }

    @Override
    public void actuar(Elemento elemento) {
        throw new UnsupportedOperationException("método aún sin implementar");
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
        throw new UnsupportedOperationException("método aún sin implementar");
    }
}

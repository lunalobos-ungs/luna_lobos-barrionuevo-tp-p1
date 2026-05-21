package juego;

import entorno.Entorno;

public class Princesa implements Elemento{
    @Override
    public int id() {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public String tipo() {
        return "princesa";
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
    public void estaContenido(double x, double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void dibujar(Entorno entorno) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void mover(float angulo, Entorno entorno) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void actuar(Elemento elemento) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }
}

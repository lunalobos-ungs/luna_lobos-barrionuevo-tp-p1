package juego;

import entorno.Entorno;

/**
 * Métodos utilitarios de elementos
 */
public class Elementos {

    /**
     * Convierte el elemento provisto en un elemento que solo tiene las
     * propiedades del rectangul, el ID del elemento provisto y el
     * tipo provisto. Ideal para colisiones especiales, como las de las
     * islas.
     *
     * @param elemento el elemento origen
     * @param tipo  el nuevo tipo del elemento
     * @param rectangulo el nuevo rectángulo del elemento
     * @return un nuevo elemento con el mismo id que el provisto,
     * pero con el tipo provisto, las propiedades de rectángulo provistas
     * y sin ninguna otra implementación.
     */
    public static Elemento aPseudoElemento(Elemento elemento, String tipo, Rectangulo rectangulo){
        return new PseudoElemento(elemento.id(), tipo, rectangulo);
    }
}


class PseudoElemento implements Elemento {
    private final int id;
    private final String tipo;
    private final Rectangulo rectangulo;
    public PseudoElemento(int id, String tipo, Rectangulo rectangulo){
        this.id = id;
        this.tipo = tipo;
        this.rectangulo = rectangulo;
    }
    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return tipo;
    }

    @Override
    public double angulo() {
        return 0;
    }

    @Override
    public void dibujar(Entorno entorno) {

    }

    @Override
    public void mover(Entorno entorno) {

    }

    @Override
    public void actuar(Elemento elemento) {

    }

    @Override
    public void establecerAngulo(double angulo) {

    }

    @Override
    public double x() {
        return rectangulo.x();
    }

    @Override
    public double y() {
        return rectangulo.y();
    }

    @Override
    public double ancho() {
        return rectangulo.ancho();
    }

    @Override
    public double alto() {
        return rectangulo.alto();
    }

    @Override
    public void establecerX(double x) {

    }

    @Override
    public void establecerY(double y) {

    }

    @Override
    public void establecerAncho(double ancho) {

    }

    @Override
    public void establecerAlto(double alto) {

    }

    @Override
    public void recibirMensaje(String mensaje) {

    }

    @Override
    public boolean debeEliminarse() {
        return false;
    }
}
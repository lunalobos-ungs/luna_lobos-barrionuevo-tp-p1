package juego;

/**
 * Implementación concreta de {@link Rectangulo} de uso interno.
 * Se utiliza, por ejemplo, para representar los límites del mundo en {@link Mundo}.
 */
class RectanguloSimple implements Rectangulo {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    /**
     * Crea un rectángulo con el centro y las dimensiones indicados.
     *
     * @param x     coordenada x del centro
     * @param y     coordenada y del centro
     * @param ancho ancho del rectángulo
     * @param alto  alto del rectángulo
     */
    public RectanguloSimple(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
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
    public void establecerX(double x) {
        this.x = x;
    }

    @Override
    public void establecerY(double y) {
        this.y = y;
    }

    @Override
    public void establecerAncho(double ancho) {
        this.ancho = ancho;
    }

    @Override
    public void establecerAlto(double alto) {
        this.alto = alto;
    }
}

package juego;


/**
 * Clase para métodos utilitarios de rectángulos.
 */
public class Rectangulos {

    /**
     * Detecta si dos rectángulos se encuentran o no en colisión.
     * @param r1 el primer rectángulo
     * @param r2 el segundo rectángulo
     * @return true si están en colisión o false de lo contrario
     */
    public static boolean enColision(Rectangulo r1, Rectangulo r2) {
        final double x1 = r1.x();
        final double x2 = r2.x();
        final double y1 = r1.y();
        final double y2 = r2.y();
        final double bordeIzquierdo1 = r1.bordeIzquierdo();
        final double bordeDerecho1 = r1.bordeDerecho();
        final double bordeSuperior1 = r1.bordeSuperior();
        final double bordeInferior1 = r1.bordeInferior();
        final double bordeIzquierdo2 = r2.bordeIzquierdo();
        final double bordeDerecho2 = r2.bordeDerecho();
        final double bordeSuperior2 = r2.bordeSuperior();
        final double bordeInferior2 = r2.bordeInferior();
        return ((bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2) || (bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2))
                && ((bordeSuperior1 <= bordeInferior2 && y1 >= y2) || (bordeInferior1 >= bordeSuperior2 && y1 <= y2));
    }

    /**
     * Determina la dirección desde la que {@code r1} llega a colisionar con {@code r2}.
     *
     * @param r1 el rectángulo cuya dirección de llegada se determina
     * @param r2 el rectángulo impactado
     * @return {@code "desde arriba"}, {@code "desde abajo"}, {@code "desde la izquierda"}
     * o {@code "desde la derecha"}
     * @throws UnsupportedOperationException si los rectángulos no están en colisión
     */
    public static String tipoDeColision(Rectangulo r1, Rectangulo r2) {
        final double x1 = r1.x();
        final double x2 = r2.x();
        final double y1 = r1.y();
        final double y2 = r2.y();
        final double bordeIzquierdo1 = r1.bordeIzquierdo();
        final double bordeDerecho1 = r1.bordeDerecho();
        final double bordeSuperior1 = r1.bordeSuperior();
        final double bordeInferior1 = r1.bordeInferior();
        final double bordeIzquierdo2 = r2.bordeIzquierdo();
        final double bordeDerecho2 = r2.bordeDerecho();
        final double bordeSuperior2 = r2.bordeSuperior();
        final double bordeInferior2 = r2.bordeInferior();

        double deltaY = 0.0;
        double deltaX = 0.0;
        boolean desdeArriba = bordeInferior1 >= bordeSuperior2 && y1 <= y2;
        boolean desdeAbajo = bordeSuperior1 <= bordeInferior2 && y1 >= y2;
        boolean desdeLaDerecha = bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2;
        boolean desdeLaIzquierda = bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2;

        if (desdeArriba) {
            deltaY = Math.min(bordeInferior1 - bordeSuperior2, r1.alto());
        }

        if (desdeAbajo) {
            deltaY = Math.min(bordeInferior2 - bordeSuperior1, r1.alto());
        }

        if (desdeLaDerecha) {
            deltaX = Math.min(bordeDerecho1 - bordeIzquierdo2, r1.ancho());
        }

        if (desdeLaIzquierda) {
            deltaX = Math.min(bordeDerecho2 - bordeIzquierdo1, r1.ancho());
        }

        if (deltaY >= deltaX) { // lateral
            if (desdeLaDerecha) {
                return "desde la derecha";
            } else if (desdeLaIzquierda) {
                return "desde la izquierda";
            }
        } else { // vertical
            if (desdeArriba) {
                return "desde arriba";
            } else if (desdeAbajo) {
                return "desde abajo";
            }
        }

        throw new UnsupportedOperationException("no hay colisión");
    }

    /**
     * Provee una implementación básica de un rectángulo.
     * @param x la coordenada x del centro del rectángulo
     * @param y la coordenada y del centro del rectángulo
     * @param ancho el ancho del rectángulo
     * @param alto el alto del rectángulo
     * @return una instancia que implementa Rectángulo
     */
    public static Rectangulo crearRectangulo(double x, double y, double ancho, double alto) {
        return new RectanguloSimple(x, y, ancho, alto);
    }
}

/**
 * Implementación concreta de Retangulo. Nos parece mejor que la clase no sea publica para evitar tener
 * muchos archivos.
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


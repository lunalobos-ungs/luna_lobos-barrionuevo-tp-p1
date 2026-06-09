package juego;


/**
 * Clase para métodos utilitarios de rectángulos.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Rectangulos {

    /**
     * Determina la dirección desde la que r1 llega a colisionar con r2.
     *
     * @param r1 el rectángulo cuya dirección de llegada se determina
     * @param r2 el rectángulo impactado
     * @return "desde arriba", "desde abajo", "desde la izquierda"
     * o "desde la derecha"
     * @throws IllegalArgumentException si los rectángulos no están en colisión
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

        throw new IllegalArgumentException("no hay colisión");
    }

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

}

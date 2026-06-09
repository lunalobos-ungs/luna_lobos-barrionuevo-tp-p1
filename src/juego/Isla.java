package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Representa una isla flotante del juego.
 *
 * @author Noelia Barrionuevo
 * @author Miguel Angel Luna Lobos
 */
public class Isla {
    private final double x;
    private final double y;
    private final double ancho;
    private final double alto;
    private final Image isla;

    public Isla(double x, double y, double ancho, double alto, Image isla) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.isla = isla;
    }

    /**
     * La coordenada x.
     * @return la coordenada x
     */
    public double x() {
        return x;
    }

    /**
     * La coordenada y.
     * @return la coordenada y
     */
    public double y() {
        return y;
    }

    /**
     * Dibuja la isla.
     * @param entorno el entorno
     * @param mundo el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        final var coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        final var x = coordenadasRelativas.x();
        final var y = coordenadasRelativas.y();
        entorno.dibujarImagen(isla, x, y, 0);
    }

    /**
     * Actúa sobre la princesa.
     * @param princesa la princesa
     */
    public void actuarSobrePrincesa(Princesa princesa) {
        String tipoDeColision = Rectangulos.tipoDeColision(princesa.rectangulo(), this.rectangulo());

        switch (tipoDeColision) {
            case "desde arriba":
                princesa.trasladar(princesa.x(), y() - alto / 2.0 - princesa.alto() / 2.0);
                princesa.recibirMensaje("estas en tierra firme");
                break;
            case "desde abajo":
                princesa.recibirMensaje("chocaste con el techo");
                break;
            case "desde la derecha":
                princesa.recibirMensaje("chocaste con un muro desde tu derecha");
                break;
            case "desde la izquierda":
                princesa.recibirMensaje("chocaste con un muro desde tu izquierda");
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no válido".formatted(tipoDeColision));
        }
    }

    /**
     * Actúa sobre un proyectil de la princesa.
     * @param proyectil el proyectil
     */
    public void actuarSobreProyectilPrincesa(ProyectilPrincesa proyectil) {
        String tipoDeColision = Rectangulos.tipoDeColision(proyectil.rectangulo(), this.rectangulo());
        switch (tipoDeColision) {
            case "desde arriba":
                proyectil.recibirMensaje("rebotar desde arriba");
                break;
            case "desde abajo":
                proyectil.recibirMensaje("rebotar desde abajo");
                break;
            case "desde la derecha":
                proyectil.recibirMensaje("rebotar desde la derecha");
                break;
            case "desde la izquierda":
                proyectil.recibirMensaje("rebotar desde la izquierda");
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no soportado".formatted(tipoDeColision));
        }
    }

    /**
     * El rectángulo de colisión de la isla.
     * @return el rectángulo de colisión de la isla
     */
    public Rectangulo rectangulo() {
    	return new Rectangulo(x,y,ancho,alto) ;
    }
}

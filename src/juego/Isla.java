package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Representa una isla flotante del juego. Implementación provisional;
 * la mayoría de sus métodos aún no están implementados.
 *
 * @author Noelia Barrionuevo
 */
public class Isla {

    private int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private final Image tierraOriginal;
    private Image tierra;
    private boolean activo = true;
    private Rectangulo rectangulo ;


    Isla(GeneradorId generadorId, double x, double y, double ancho, double alto, double factorFronteraAncho, double factorFronteraAlto) {
        this.id = generadorId.nuevoId();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        rectangulo = new Rectangulo(x, y, ancho, alto);
        tierraOriginal = Imagenes.cargarImagen("tierra.png");
        tierra = Imagenes.escalar(tierraOriginal, ancho, alto);
    }


    public int id() {
        return id;
    }


    public String tipo() {
        return "isla";
    }


    public double angulo() {
        return 0;
    }


    public double x() {
        return x;
    }


    public double y() {
        return y;
    }


    public double ancho() {
        return ancho;
    }


    public double alto() {
        return alto;
    }


    public void establecerAncho(double ancho) {
        this.ancho = ancho;
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }


    public void establecerAlto(double alto) {
        this.alto = alto;
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }


    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(tierra, x, y, 0);
    }


    public void mover(Entorno entorno) {

    }

    public void actuarSobreJefe(Jefe jefe) {
        String tipoDeColision = Rectangulos.tipoDeColision(jefe.rectangulo(), this.rectangulo());

        switch (tipoDeColision) {
            case "desde arriba":
                jefe.recibirMensaje("estas en tierra firme");
                break;
            case "desde abajo":
                jefe.recibirMensaje("chocaste con el techo");
                break;
            case "desde la derecha":
                jefe.recibirMensaje("chocaste con un muro desde tu derecha");
                break;
            case "desde la izquierda":
                jefe.recibirMensaje("chocaste con un muro desde tu izquierda");
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no válido".formatted(tipoDeColision));
        }
    }

    public void actuarSobrePrincesa(Princesa princesa) {
        String tipoDeColision = Rectangulos.tipoDeColision(princesa.rectangulo(), this.rectangulo());

        switch (tipoDeColision) {
            case "desde arriba":
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

    public void actuarSobreProyectilPrincesa(ProyectilPrincesa proyectil) {
        String tipoDeColision = Rectangulos.tipoDeColision(proyectil.rectangulo(), this.rectangulo);
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

    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("la isla no trabaja con ángulo aún");
    }


    public void establecerX(double x) {
        throw new UnsupportedOperationException("las islas aún no se mueven");
    }


    public void establecerY(double y) {
        throw new UnsupportedOperationException("las islas aún no se mueven");
    }


    public void recibirMensaje(String mensaje) {
        // las islas son inalterables
    }

    public boolean debeEliminarse() {
        return false;
    }


    public Rectangulo rectangulo() {
    	return rectangulo ;
    }
}

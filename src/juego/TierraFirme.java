package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Plataforma estática ubicada en la parte inferior de la pantalla.
 * Detiene a los personajes que la pisan y rebota los proyectiles que la impactan.
 *
 * @author Miguel Angel Luna Lobos
 */
public class TierraFirme implements Elemento {
    private int id;
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private final Image tierraOriginal;
    private Image tierra;

    /**
     * Crea la tierra firme centrada horizontalmente en la parte inferior del entorno.
     *
     * @param generadorId generador de IDs para asignar un identificador único
     * @param entorno     el entorno del juego, usado para obtener las dimensiones de la pantalla
     */
    public TierraFirme(GeneradorId generadorId, Entorno entorno) {
        this.id = generadorId.nuevoId();
        ancho = 800.0;
        alto = 30.0;
        x = entorno.ancho() / 2.0;
        y = entorno.alto() - alto;
        tierraOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("tierra.png"))).getImage();
        tierra = tierraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
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
        // por el momento la tierra no se mueve
    }

    @Override
    public void actuar(Elemento elemento) {
        switch (elemento.tipo()){
            case "princesa":
            case "jefe":
                actuarSobreCuerpo(elemento);
                break;
            case "proyectil":
                actuarSobreProyectil(elemento);
                break;
        }
    }

    /**
     * Reacciona ante la colisión con un cuerpo (princesa o jefe).
     * Si llega desde arriba notifica que está en tierra firme; si llega desde otro lado
     * lo redirige hacia arriba.
     *
     * @param elemento el cuerpo en colisión
     */
    private void actuarSobreCuerpo(Elemento elemento){
        String tipoDeColision = Mundo.tipoDeColision(elemento, this);
        switch (tipoDeColision) {
            case "desde arriba":
                elemento.recibirMensaje("estas en tierra firme");
                break;
            case "desde abajo":
            case "desde la derecha":
            case "desde la izquierda":
                elemento.establecerAngulo(-Math.PI / 2);
                break;
            default:
                throw new IllegalArgumentException("tipo de colisión %s no soportado".formatted(tipoDeColision));
        }
    }

    /**
     * Reacciona ante la colisión con un proyectil enviándole el mensaje de rebote
     * correspondiente según la dirección de llegada.
     *
     * @param elemento el proyectil en colisión
     */
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
    public void recibirMensaje(String mensaje) {
        // la tierra firme es inerte
    }
}

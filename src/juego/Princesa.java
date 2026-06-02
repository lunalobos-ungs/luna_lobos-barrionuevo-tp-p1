package juego;

import entorno.Entorno;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Objects;

/**
 * Personaje principal del juego. Se mueve horizontalmente con las teclas de dirección,
 * salta con la tecla {@code 'a'} cuando está en tierra firme y cae por gravedad.
 * Dispara {@link ProyectilPrincesa} hacia el cursor al presionar el botón izquierdo del mouse.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Princesa implements Elemento {

    private final Image princesaOriginal;
    private Image princesa;
    private double x;
    private double y;
    private double ancho;
    private double alto;

    private double angulo;

    private final int id;

    private final double velocidad;

    private double velocidadCaidaLibre;

    private final double velocidadSalto;

    private boolean enSalto;

    private double aceleracionGravitatoria;
    private Instant marcaTemporalDeCaida;
    private boolean activo = true ;

    private boolean xNoCrece;
    private boolean xNoDecrece;

    /**
     * Crea a la princesa en el centro horizontal de la pantalla e inicia la simulación
     * de caída libre.
     *
     * @param generadorId generador de IDs para asignar un identificador único
     * @param entorno     el entorno del juego
     */
    public Princesa(GeneradorId generadorId, Entorno entorno) {
        x = entorno.ancho() / 2.0;
        y = 0;
        ancho = 100.0;
        alto = 100.0;
        princesaOriginal = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("princesa.png"))).getImage();
        princesa = princesaOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
        id = generadorId.nuevoId();
        velocidad = 3.0;
        velocidadCaidaLibre = 5.0;
        velocidadSalto = 8.0;
        enSalto = false;
        angulo = 0.0;
        aceleracionGravitatoria = 10.0;
        cayendo();
        xNoCrece = false;
        xNoDecrece = false;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String tipo() {
        return "princesa";
    }

    @Override
    public double angulo() {
        return angulo;
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
        return ancho * 0.55;
    }

    @Override
    public double alto() {
        return alto * 0.80;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
        this.princesa = princesaOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(princesa, x + 5, y, 0, 1);
    }

    @Override
    public void mover(Entorno entorno) {
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            angulo = 0;
            movimientoLateral();
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            angulo = Math.PI;
            movimientoLateral();
        }
        if (entorno.sePresiono('a') && aceleracionGravitatoria <= 0.1) {
            enSalto = true;
            cayendo();
        }
        gravedad(entorno);
        if (enSalto) {
            movimiento(-Math.PI / 2, velocidadSalto);
        }
    }

    /**
     * Aplica el movimiento lateral de la princesa, reduciendo la velocidad si está
     * en tierra firme para evitar deslizamiento excesivo.
     */
    private void movimientoLateral() {
        if (aceleracionGravitatoria <= 0.1) {
            movimiento(angulo, velocidad * 0.75);
        } else {
            movimiento(angulo, velocidad);
        }
    }

    /**
     * Desplaza a la princesa en la dirección y velocidad indicadas, sin ninguna
     * condición adicional.
     *
     * @param angulo    el ángulo de desplazamiento en radianes
     * @param velocidad la velocidad de desplazamiento en píxeles por frame
     */
    private void movimiento(double angulo, double velocidad) {
        double deltaX = Math.cos(angulo) * velocidad;

        if (deltaX > 0.0 && xNoCrece) {
            deltaX = 0.0;
            xNoCrece = false;
        } else if (deltaX < 0 && xNoDecrece) {
            deltaX = 0.0;
            xNoDecrece = false;
        }

        x += deltaX;
        y += Math.sin(angulo) * velocidad;
    }

    /**
     * Aplica la simulación de gravedad: acumula velocidad de caída libre con el tiempo
     * y la anula cuando la princesa está en tierra firme.
     *
     * @param entorno el entorno del juego
     */
    private void gravedad(Entorno entorno) {
        if (aceleracionGravitatoria <= 0.1) {
            marcaTemporalDeCaida = Instant.now();
        } else {
            double lapso = Instant.now().toEpochMilli() - marcaTemporalDeCaida.toEpochMilli();
            velocidadCaidaLibre = aceleracionGravitatoria * lapso / 1000;
            movimiento(Math.PI / 2, velocidadCaidaLibre);
        }
        aceleracionGravitatoria = 10.0;
    }

    @Override
    public void actuar(Elemento elemento) {
        // la princesa no actua sobre otros elementos directamente
    }

    @Override
    public void establecerAngulo(double angulo) {
        this.angulo = angulo;
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
        throw new UnsupportedOperationException("no se puede modificar las dimensiones de la princesa");
    }

    @Override
    public void establecerAlto(double alto) {
        throw new UnsupportedOperationException("no se puede modificar las dimensiones de la princesa");
    }

    @Override
    public void recibirMensaje(String mensaje) {

        switch (mensaje) {
            case "morir": // :C
                cayendo();
                break;
            case "estas en tierra firme":
                enTierraFirme();
                break;
            case "chocaste con el techo":
                chocarTecho();
                break;
            case "chocaste con un muro desde tu derecha":
                System.out.println(mensaje);
                chocarMuroPorDerecha();
                break;
            case "chocaste con un muro desde tu izquierda":
                System.out.println(mensaje);
                chocarMuroPorIzquierda();
                break;
            default:
                throw new IllegalArgumentException("así no se le habla a la princesa -> mensaje: " + mensaje);
        }
    }

    private void chocarMuroPorIzquierda() {
        enSalto = false;
        xNoDecrece = true;
    }

    private void chocarTecho() {
        enSalto = false;
    }

    private void chocarMuroPorDerecha() {
        enSalto = false;
        xNoCrece = true;
    }

    /**
     * Inicializa el estado de caída libre reseteando la velocidad y registrando
     * el instante actual como marca de inicio de caída.
     */
    private void cayendo() {
        marcaTemporalDeCaida = Instant.now();
        velocidadCaidaLibre = 0.0;
    }

    /**
     * Establece el estado de la princesa cuando toca tierra firme: detiene la
     * gravedad, cancela el salto y resetea la velocidad de caída.
     */
    private void enTierraFirme() {
        aceleracionGravitatoria = 0.0;
        enSalto = false;
        marcaTemporalDeCaida = null;
        velocidadCaidaLibre = 0.0;
    }

    public void disparar(Contexto contexto, Entorno entorno, GeneradorId generadorId) {
        double mouseX = entorno.mouseX();
        double mouseY = entorno.mouseY();
        double distanciaX = mouseX - x;
        double distanciaY = mouseY - y;
        double distancia = Math.sqrt(Math.pow(distanciaX, 2.0) + Math.pow(distanciaY, 2.0));
        double cos = (distanciaX) / distancia;
        double sin = (distanciaY) / distancia;
        Elemento proyectil = new ProyectilPrincesa(x, y, cos, sin, generadorId);
        contexto.agregar(proyectil);
    }
    
    public boolean debeEliminarse () {
    	return ! activo ;
    }
}

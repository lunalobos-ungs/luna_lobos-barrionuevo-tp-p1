package juego;

import entorno.Entorno;

import java.awt.*;
import java.time.Instant;
import entorno.Herramientas;

/**
 * Personaje principal del juego.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Princesa {
    public static final double altoPrincesa = 100;
    public static final double anchoPrincesa = 100;
    public static final double ladoCorazon = 30.0;
    private final Image princesa;
    private final Image corazon;
    private double x;
    private double y;
    private final double ancho;
    private final double alto;
    private double angulo;
    private final double velocidad;
    private double velocidadCaidaLibre;
    private final double velocidadSalto;
    private boolean enSalto;
    private double aceleracionGravitatoria;
    private Instant marcaTemporalDeCaida;
    private boolean xNoCrece;
    private boolean xNoDecrece;
    private int vidas;

    /**
     * Crea a la princesa en el centro horizontal de la pantalla e inicia la simulación
     * de caída libre.
     *
     * @param x la coordenada x
     * @param y la coordenada y
     * @param ancho el ancho
     * @param alto el alto
     * @param princesa la imagen de la princesa
     * @param corazon la imagen del corazón
     */
    public Princesa(double x, double y, double ancho, double alto, Image princesa, Image corazon) {
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
        this.princesa = princesa;
        this.corazon = corazon;
        vidas = 10;
        xNoCrece = false; // booleano que indica que no se puede avanzar hacia la derecha
        xNoDecrece = false; // booleano que indica que no se puede avanzar hacia la izquierda
        velocidad = 3.0; // la velocidad de movimientos laterales
        velocidadSalto = 7.5; // la velocidad que alcanza la princesa tras saltar
        enSalto = false; // indica si la princesa está en un salto
        angulo = 0.0; // el ángulo en el que se mueve la princesa
        aceleracionGravitatoria = 10.0; // la constante g de este mundo
        velocidadCaidaLibre = 0.0; // la velocidad en caída libre, comienza en cero
        marcaTemporalDeCaida = Instant.now(); // iniciamos con la princesa en caída libre
    }

    public int vidas() {
        return vidas;
    }

    public void trasladar(double x, double y) {
        this.x = x;
        this.y = y;
        marcaTemporalDeCaida = Instant.now();
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
        return y + alto * 0.05;
    }

    /**
     * El ancho.
     * @return el ancho
     */
    public double ancho() {
        return ancho * 0.45;
    }

    /**
     * El alto.
     * @return el alto
     */
    public double alto() {
        return alto * 0.80;
    }


    public void dibujar(Entorno entorno) {
        var espacio = 5.0;
        for (var i = 0; i < vidas; i++) {
            double l = ladoCorazon * (i + 0.5) + (i + 1) * 5.0;
            entorno.dibujarImagen(
                    corazon,
                    l, espacio + ladoCorazon / 2.0,
                    0,
                    1
            );
        }
        final var dx = entorno.ancho() / 2.0;
        final var dy = entorno.alto() / 2.0;
        entorno.dibujarImagen(princesa, dx, dy, 0, 1);
    }

    /**
     * Ejecuta el movimiento de la princesa.
     * @param entorno el entorno
     */
    public void mover(Entorno entorno) {
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            angulo = 0;
            movimientoLateral();
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            angulo = Math.PI;
            movimientoLateral();
        }
        if ((entorno.sePresiono('a') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) && aceleracionGravitatoria <= 0.1) {
            enSalto = true;
            cayendo();
        }
        gravedad();
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
     */
    private void gravedad() {
        if (aceleracionGravitatoria <= 0.1) {
            marcaTemporalDeCaida = Instant.now();
        } else {
            double lapso = Instant.now().toEpochMilli() - marcaTemporalDeCaida.toEpochMilli();
            velocidadCaidaLibre = aceleracionGravitatoria * lapso / 1000;
            movimiento(Math.PI / 2, velocidadCaidaLibre);
        }
        aceleracionGravitatoria = 10.0;
    }

    /**
     * Recibe mensajes.
     * @param mensaje el mensaje
     */
    public void recibirMensaje(String mensaje) {

        switch (mensaje) {
            case "una vida menos": // :C
                vidas--;
                break;
            case "estas en tierra firme":
                enTierraFirme();
                break;
            case "chocaste con el techo":
                chocarTecho();
                break;
            case "chocaste con un muro desde tu derecha":
                chocarMuroPorDerecha();
                break;
            case "chocaste con un muro desde tu izquierda":
                chocarMuroPorIzquierda();
                break;
            case "morir":
                vidas = 0;
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

    private void cayendo() {
        marcaTemporalDeCaida = Instant.now();
        velocidadCaidaLibre = 0.0;
    }

    private void enTierraFirme() {
        aceleracionGravitatoria = 0.0;
        enSalto = false;
        marcaTemporalDeCaida = null;
        velocidadCaidaLibre = 0.0;
    }

    /**
     * Dispara un proyectil.
     * @param mundo el mundo
     * @param entorno el entorno
     */
    public void disparar(Mundo mundo, Entorno entorno) {
        final var mouseX = entorno.mouseX() + x - entorno.ancho() / 2.0;
        final var mouseY = entorno.mouseY() + y - entorno.alto() / 2.0;
        final var distanciaX = mouseX - x;
        final var distanciaY = mouseY - y;
        final var distancia = Math.sqrt(Math.pow(distanciaX, 2.0) + Math.pow(distanciaY, 2.0));
        final var cos = (distanciaX) / distancia;
        final var sin = (distanciaY) / distancia;
        final var proyectil = new ProyectilPrincesa(x, y, cos, sin);
        // se agrega sonido cuando la princesa dispara
        // se llamo a la clase de herramientas play
        try {
            Herramientas.play("recursos/sonidoPoder.wav");
        }
        catch (Exception error){
            System.out.println ("No se puede reproducir sonido");
        }
        mundo.establecerProyectilPrincesa(proyectil);
    }

    /**
     * El rectángulo de colisión
     * @return el rectángulo de colisión
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x(), y(), ancho(), alto());
    }
}

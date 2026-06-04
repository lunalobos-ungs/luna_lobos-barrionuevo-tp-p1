package juego;

import entorno.Entorno;

import java.awt.*;
import java.time.Instant;

/**
 * Personaje principal del juego. Se mueve horizontalmente con las teclas de dirección,
 * salta con la tecla {@code 'a'} cuando está en tierra firme y cae por gravedad.
 * Dispara {@link ProyectilPrincesa} hacia el cursor al presionar el botón izquierdo del mouse.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Princesa {

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
    private boolean activo;
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
        // El DNI de la princesa
        id = generadorId.nuevoId();
        // Propiedades del rectángulo de la princesa
        x = entorno.ancho() / 2.0;
        y = 0;
        ancho = 100.0;
        alto = 100.0;
        // Imagenes de la princesa
        princesaOriginal = Imagenes.cargarImagen("princesa.png");
        princesa = Imagenes.escalar(princesaOriginal, ancho, alto);
        // Propiedades de vida de la princesa
        activo = true; // tal vez aca nos falta un contador de vidas

        // Propiedades dinámicas de la princesa
        xNoCrece = false; // booleano que indica que no se puede avanzar hacia la derecha
        xNoDecrece = false; // booleano que indica que no se puede avanzar hacia la izquierda
        velocidad = 3.0; // la velocidad de movimientos laterales
        velocidadSalto = 7.0; // la velocidad que alcanza la princesa tras saltar
        enSalto = false; // indica si la princesa está en un salto
        angulo = 0.0; // el ángulo en el que se mueve la princesa
        aceleracionGravitatoria = 10.0; // la constante g de este mundo
        velocidadCaidaLibre = 0.0; // la velocidad en caída libre, comienza en cero
        marcaTemporalDeCaida = Instant.now(); // iniciamos con la princesa en caída libre
    }


    public int id() {
        return id;
    }


    public String tipo() {
        return "princesa";
    }


    public double angulo() {
        return angulo;
    }


    public double x() {
        return x;
    }

    // cambiada para mejorar la jugabilidad

    public double y() {
        return y + alto * 0.05;
    }

    // cambiada para mejorar la jugabilidad

    public double ancho() {
        return ancho * 0.45;
    }

    // cambiada para mejorar la jugabilidad

    public double alto() {
        return alto * 0.80;
    }


    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(princesa, x + 5, y, 0, 1);
    }


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

    public void establecerAngulo(double angulo) {
        this.angulo = angulo;
    }


    public void establecerX(double x) {
        this.x = x;
    }


    public void establecerY(double y) {
        this.y = y;
    }


    public void establecerAncho(double ancho) {
        throw new UnsupportedOperationException("no se puede modificar las dimensiones de la princesa");
    }


    public void establecerAlto(double alto) {
        throw new UnsupportedOperationException("no se puede modificar las dimensiones de la princesa");
    }


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
                chocarMuroPorDerecha();
                break;
            case "chocaste con un muro desde tu izquierda":
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

    public void disparar(Mundo mundo, Entorno entorno, GeneradorId generadorId) {
        final var mouseX = entorno.mouseX();
        final var mouseY = entorno.mouseY();
        final var distanciaX = mouseX - x;
        final var distanciaY = mouseY - y;
        final var distancia = Math.sqrt(Math.pow(distanciaX, 2.0) + Math.pow(distanciaY, 2.0));
        final var cos = (distanciaX) / distancia;
        final var sin = (distanciaY) / distancia;
        final var proyectil = new ProyectilPrincesa(x, y, cos, sin, generadorId);
        mundo.establecerProyectilPrincesa(proyectil);
    }

    public boolean debeEliminarse() {
        return !activo;
    }
    public Rectangulo rectangulo() {
    	return new Rectangulo (x,y,ancho*0.47,alto*0.8) ;
    }
}

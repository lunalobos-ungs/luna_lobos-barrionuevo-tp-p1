package juego;

import entorno.Entorno;

import java.awt.*;
import java.time.Instant;

/**
 * Representa a un enemigo del juego.
 *
 * @author Noelia Barrionuevo
 * @author Miguel Angel Luna Lobos
 */
public class Enemigo {

    private static double altoEnemigo = 40.0;
    private static double anchoEnemigo = 40.0;

    public static int minimoEnemigos = Isla.niveles / 2;

    private static Image imagenEnemigo = Juego.cargarYEscalar("enemigo.png", anchoEnemigo, altoEnemigo);

    /**
     * Crea un nuevo enemigo que ingresa en pantalla por la derecha.
     * @param generadorId el generador de identificadores únicos
     * @param mundo el mundo
     * @param entorno el entorno
     * @return un nuevo enemigo por derecha
     */
    public static Enemigo nuevoEnemigoDerecha(GeneradorId generadorId, Mundo mundo, Entorno entorno) {
        Princesa princesa = mundo.princesa();
        Enemigo enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() + entorno.ancho() / 2.0, Math.PI);
        int intentos = 0;
        while (mundo.enemigosEnColision(enemigo.rectangulo()).length > 0) {
            enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() + entorno.ancho() / 2.0, Math.PI);
            if (intentos++ == 100) {
                System.out.println("advertencia, no se pudo generar un enemigo");
                return null;
            }
        }
        return enemigo;
    }

    /**
     * Crea un nuevo enemigo que ingresa en pantalla por la derecha.
     * @param generadorId el generador de identificadores únicos
     * @param mundo el mundo
     * @param entorno el entorno
     * @return un nuevo enemigo por izquierda
     */
    public static Enemigo nuevoEnemigoIzquierda(GeneradorId generadorId, Mundo mundo, Entorno entorno) {
        Princesa princesa = mundo.princesa();
        Enemigo enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() - entorno.ancho() / 2.0,  0.0);
        int intentos = 0;
        while (mundo.enemigosEnColision(enemigo.rectangulo()).length > 0) {
            enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() - entorno.ancho() / 2.0, 0.0);
            if (intentos++ == 100) {
                System.out.println("advertencia, no se pudo generar un enemigo");
                return null;
            }
        }
        return enemigo;
    }

    private static Enemigo enemigoAleatorio(GeneradorId generadorId, Mundo mundo, double x, double angulo) {
        double  altoMundo = mundo.limitesMundo().alto();
        double  alto = Isla.proporcionAlto * altoMundo;
        double  alturaMinima = Isla.proporcionAlturaMinima * altoMundo;
        double  yMax = altoMundo - alturaMinima;
        double  yMin = altoMundo - alturaMinima - alto;
        double  y = alturaAleatoria(Isla.niveles, yMin, yMax);

        return new Enemigo(generadorId, x, y, anchoEnemigo, altoEnemigo, angulo, imagenEnemigo);
    }

    private static double alturaAleatoria(int n, double yMin, double yMax) {
        if (n < 2) {
            throw new IllegalArgumentException("n no puede ser inferior a 2");
        }
        int q = Juego.enteroRandom(0, n - 1);
        int indice = 2 * q + 1;
        double rango = yMax - yMin;
        return indice * rango / (2 * (n - 1)) + yMin;
    }

    private double x;
    private double y;
    private double ancho;
    private double alto;
    private Image enemigo;
    private int id;
    private double velocidad;
    private double angulo;
    private boolean vivo = true;

    /**
     * Crea un nuevo enemigo.
     * @param generadorId el generador de identificadores únicos
     * @param x la coordenada x
     * @param y la coordenada y
     * @param ancho el ancho
     * @param alto el alto
     * @param angulo el ángulo
     * @param enemigo la imagen del enemigo
     */
    Enemigo(GeneradorId generadorId, double x, double y, double ancho, double alto, double angulo, Image enemigo) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.enemigo = enemigo;

        id = generadorId.nuevoId();
        velocidad = 1.0;
        this.angulo = angulo;
    }

    /**
     * El identificador del enemigo.
     * @return el identificador del enemigo
     */
    public int id() {
        return id;
    }

    /**
     * Dibuja al enemigo.
     * @param entorno el entorno
     * @param mundo el mundo
     */
    public void dibujar(Entorno entorno, Mundo mundo) {
        Coordenadas coordenadasRelativas = Coordenadas.transformar(this.x, this.y, mundo, entorno);
        double x = coordenadasRelativas.x();
        double y = coordenadasRelativas.y();
        entorno.dibujarImagen(enemigo, x, y, 0);
    }

    /**
     * Mueve al enemigo.
     */
    public void mover() {
        x = x + velocidad * Math.cos(angulo);
    }

    /**
     * Recibe un mensaje.
     * @param mensaje
     */
    public void recibirMensaje(String mensaje) {
        if (mensaje.equals("morir")) {
            vivo = false;
        }
    }

    /**
     * Indica si este enemigo debe eliminarse.
     * @return true si debe eliminarse, false de lo contrario
     */
    public boolean debeEliminarse() {
        return !vivo;
    }

    /**
     * El rectángulo de colisión del enemigo.
     * @return el rectángulo de colisión del enemigo
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }
}

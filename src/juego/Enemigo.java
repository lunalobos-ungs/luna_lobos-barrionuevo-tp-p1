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
     * @param id el identificador único del enemigo
     * @param mundo el mundo
     * @param entorno el entorno
     * @return un nuevo enemigo por derecha
     */
    public static Enemigo nuevoEnemigoDerecha(int id, Mundo mundo, Entorno entorno) {
        Princesa princesa = mundo.princesa();
        Enemigo enemigo = enemigoAleatorio(id, mundo, princesa.x() + entorno.ancho() / 2.0, Math.PI);
        int intentos = 0;
        while (mundo.enemigosEnColision(enemigo.rectangulo()).length > 0) {
            enemigo = enemigoAleatorio(id, mundo, princesa.x() + entorno.ancho() / 2.0, Math.PI);
            if (intentos++ == 100) {
                System.out.println("advertencia, no se pudo generar un enemigo");
                return null;
            }
        }
        return enemigo;
    }

    /**
     * Crea un nuevo enemigo que ingresa en pantalla por la derecha.
     * @param id el identificador único del enemigo
     * @param mundo el mundo
     * @param entorno el entorno
     * @return un nuevo enemigo por izquierda
     */
    public static Enemigo nuevoEnemigoIzquierda(int id, Mundo mundo, Entorno entorno) {
        Princesa princesa = mundo.princesa();
        Enemigo enemigo = enemigoAleatorio(id, mundo, princesa.x() - entorno.ancho() / 2.0,  0.0);
        int intentos = 0;
        while (mundo.enemigosEnColision(enemigo.rectangulo()).length > 0) {
            enemigo = enemigoAleatorio(id, mundo, princesa.x() - entorno.ancho() / 2.0, 0.0);
            if (intentos++ == 100) {
                System.out.println("advertencia, no se pudo generar un enemigo");
                return null;
            }
        }
        return enemigo;
    }

    private static Enemigo enemigoAleatorio(int id, Mundo mundo, double x, double angulo) {
        double  altoMundo = mundo.limitesMundo().alto();
        double  alto = Isla.proporcionAlto * altoMundo;
        double  alturaMinima = Isla.proporcionAlturaMinima * altoMundo;
        double  yMax = altoMundo - alturaMinima;
        double  yMin = altoMundo - alturaMinima - alto;
        double  y = alturaAleatoria(Isla.niveles, yMin, yMax);

        return new Enemigo(id, x, y, anchoEnemigo, altoEnemigo, angulo, imagenEnemigo);
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


    Enemigo(int id, double x, double y, double ancho, double alto, double angulo, Image enemigo) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.enemigo = enemigo;
        this.id = id;
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
        double x = Juego.transformarX(this.x, mundo, entorno);
        double y = Juego.transformarY(this.y, mundo, entorno);
        entorno.dibujarImagen(enemigo, x, y, 0);
    }

    /**
     * Mueve al enemigo.
     */
    public void mover() {
        x = x + velocidad * Math.cos(angulo);
    }

    public void morir(){
        vivo = false;
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

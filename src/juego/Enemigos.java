package juego;

import entorno.Entorno;

import java.awt.*;

/**
 * Clase con métodos utilitarios relacionados con enemigos.
 *
 * @author Miguel Angel Luna Lobos
 * @author Noelia Barrionuevo
 */
public class Enemigos {
    private static final double altoEnemigo = 40.0;
    private static final double anchoEnemigo = 40.0;

    public static final int minimoEnemigos = Isla.niveles / 2;

    private static final Image imagenEnemigo = Imagenes.cargarYEscalar("enemigo.png", anchoEnemigo, altoEnemigo);

    /**
     * Crea un nuevo enemigo que ingresa en pantalla por la derecha.
     * @param generadorId el generador de identificadores únicos
     * @param mundo el mundo
     * @param entorno el entorno
     * @return un nuevo enemigo por derecha
     */
    public static Enemigo nuevoEnemigoDerecha(GeneradorId generadorId, Mundo mundo, Entorno entorno) {
        var princesa = mundo.princesa();
        var enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() + entorno.ancho() / 2.0, Math.PI);
        var intentos = 0;
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
        var princesa = mundo.princesa();
        var enemigo = enemigoAleatorio(generadorId, mundo, princesa.x() - entorno.ancho() / 2.0,  0.0);
        var intentos = 0;
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
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = Isla.proporcionAlto * altoMundo;
        final var alturaMinima = Isla.proporcionAlturaMinima * altoMundo;
        final var yMax = altoMundo - alturaMinima;
        final var yMin = altoMundo - alturaMinima - alto;
        final var y = alturaAleatoria(Isla.niveles, yMin, yMax);

        return new Enemigo(generadorId, x, y, anchoEnemigo, altoEnemigo, angulo, imagenEnemigo);
    }

    private static double alturaAleatoria(int n, double yMin, double yMax) {
        if (n < 2) {
            throw new IllegalArgumentException("n no puede ser inferior a 2");
        }
        var q = Aleatorio.enteroRandom(0, n - 1);
        var indice = 2 * q + 1;
        var rango = yMax - yMin;
        return indice * rango / (2 * (n - 1)) + yMin;
    }
}
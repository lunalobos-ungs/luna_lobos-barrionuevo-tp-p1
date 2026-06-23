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
    public static double proporcionAlto = 0.8;
    public static double proporcionAlturaMinima = 0.0;
    public static double altoIsla = 20.0;
    public static double anchoMinimo = 400.0;
    public static double anchoMaximo = 500.0;
    public static double factorFronteraAncho = 2.0;
    public static double factorFronteraAlto = 1.0;
    public static int niveles = 8;
    public static double proporcionNivelesAltos = 0.6;
    private static double anchoIslaNivelBajo = 200.0;
    public static double proporcionIslasBajas = 10.0 / (800.0 * 600.0);
    public static double proporcionIslasAltas = 10.0 / (800.0 * 600.0);

    private static Image crearImagenIsla(double ancho) {
        return Juego.cargarYEscalar("isla.png", ancho, altoIsla);
    }

    static double decimalRandom(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        double rango = max - min;
        return Juego.random.nextDouble() * rango + min;
    }

    public static Isla nuevaNivelBajo(Mundo mundo) {
        Isla isla = islaAleatoriaNivelBajo(mundo);
        int intentos = 0;
        while (mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0) {
            isla = islaAleatoriaNivelBajo(mundo);
            if (intentos++ == 1000) {
                System.out.println("advertencia, no se pudo generar una isla de nivel bajo");
                return null;
            }
        }
        return isla;
    }

    public static Isla nuevaNivelAlto(Mundo mundo) {
        Isla isla = islaAleatoriaNivelAlto(mundo);
        int intentos = 0;
        while (mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0) {
            isla = islaAleatoriaNivelAlto(mundo);
            if (intentos++ == 1000) {
                System.out.println("advertencia, no se pudo generar una isla de nivel alto");
                return null;
            }
        }
        return isla;
    }

    private static Isla islaAleatoriaNivelBajo(Mundo mundo) {
        double anchoMundo = mundo.limitesMundo().ancho();
        double altoMundo = mundo.limitesMundo().alto();
        double alto = proporcionAlto * altoMundo;
        double alturaMinima = proporcionAlturaMinima * altoMundo;
        double yMax = altoMundo - alturaMinima;
        double yMin = altoMundo - alturaMinima - alto;
        int indice = Juego.enteroRandom((int) Math.floor(niveles * proporcionNivelesAltos), niveles);
        double rango = yMax - yMin;
        double y = indice * rango / (niveles - 1) + yMin;
        double ancho = anchoIslaNivelBajo;
        double xMin = 0.0 + ancho / 2.0;
        double xMax = anchoMundo - ancho / 2.0;
        double x = decimalRandom(xMin, xMax);
        return new Isla(x, y, anchoIslaNivelBajo, altoIsla, crearImagenIsla(anchoIslaNivelBajo));
    }

    private static Isla islaAleatoriaNivelAlto(Mundo mundo) {
        double anchoMundo = mundo.limitesMundo().ancho();
        double altoMundo = mundo.limitesMundo().alto();
        double alto = proporcionAlto * altoMundo;
        double alturaMinima = proporcionAlturaMinima * altoMundo;
        double yMax = altoMundo - alturaMinima;
        double yMin = altoMundo - alturaMinima - alto;
        int indice = Juego.enteroRandom(0, (int) Math.floor(niveles * proporcionNivelesAltos));
        double rango = yMax - yMin;
        double y = indice * rango / (niveles - 1) + yMin;
        double ancho = decimalRandom(anchoMinimo, anchoMaximo);
        double xMin = 0.0 + ancho / 2.0;
        double xMax = anchoMundo - ancho / 2.0;
        double x = decimalRandom(xMin, xMax);
        return new Isla(x, y, ancho, altoIsla, crearImagenIsla(ancho));
    }

    private double x;
    private double y;
    private double ancho;
    private double alto;
    private Image isla;

    public Isla(double x, double y, double ancho, double alto, Image isla) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.isla = isla;
    }

    /**
     * La coordenada x.
     *
     * @return la coordenada x
     */
    public double x() {
        return x;
    }

    /**
     * La coordenada y.
     *
     * @return la coordenada y
     */
    public double y() {
        return y;
    }

    /**
     * Dibuja la isla.
     *
     * @param entorno  el entorno
     * @param princesa la princesa
     */
    public void dibujar(Entorno entorno, Princesa princesa) {
        double x = Juego.transformarX(this.x, princesa, entorno);
        double y = Juego.transformarY(this.y, princesa, entorno);
        entorno.dibujarImagen(isla, x, y, 0);
    }

    /**
     * El rectángulo de colisión de la isla.
     *
     * @return el rectángulo de colisión de la isla
     */
    public Rectangulo rectangulo() {
        return new Rectangulo(x, y, ancho, alto);
    }
}

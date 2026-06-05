package juego;

/**
 * La clase que se encarga de generar islas en el mapa de manera aleatoria.
 *
 * @author Noelia Barrionuevo y Miguel Angel Luna Lobos
 */
public class Islas {
    public static final double proporcionAlto = 0.8;
    public static final double proporcionAlturaMinima = 0.0;
    public static final double altoIsla = 20.0;
    public static final double anchoMinimo = 100.0;
    public static final double anchoMaximo = 300.0;
    public static final double factorFronteraAncho = 2.0;
    public static final double factorFronteraAlto = 1.0;
    public static final int niveles = 7;
    public static final double proporcionNivelesAltos = 0.6;
    private static final double anchoIslaNivelBajo = 100.0;

    public static final double proporcionIslasBajas = 4.0 / (800.0 * 600.0);

    public static final double proporcionIslasAltas = 3.0 / (800.0 * 600.0);

    public static Isla nuevaNivelBajo(GeneradorId generadorId, Mundo mundo) {
        var isla = islaAleatoriaNivelBajo(generadorId, mundo);
        while (mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0) {
            isla = islaAleatoriaNivelBajo(generadorId, mundo);
        }
        return isla;
    }

    public static Isla nuevaNivelAlto(GeneradorId generadorId, Mundo mundo) {
        var isla = islaAleatoriaNivelAlto(generadorId, mundo);
        while (mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0) {
            isla = islaAleatoriaNivelAlto(generadorId, mundo);
        }
        return isla;
    }

    private static Isla islaAleatoriaNivelBajo(GeneradorId generadorId, Mundo mundo) {

        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo;
        final var alturaMinima = proporcionAlturaMinima * altoMundo;
        final var yMax = altoMundo - alturaMinima;
        final var yMin = altoMundo - alturaMinima - alto;
        final var indice = Aleatorio.enteroRandom((int) Math.floor(niveles * proporcionNivelesAltos), niveles);
        final var rango = yMax - yMin;
        final var y = indice * rango / (niveles - 1) + yMin;
        final var ancho = anchoIslaNivelBajo;
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho / 2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, anchoIslaNivelBajo, altoIsla);
    }

    private static Isla islaAleatoriaNivelAlto(GeneradorId generadorId, Mundo mundo) {
        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo;
        final var alturaMinima = proporcionAlturaMinima * altoMundo;
        final var yMax = altoMundo - alturaMinima;
        final var yMin = altoMundo - alturaMinima - alto;
        final var indice = Aleatorio.enteroRandom(0, (int) Math.floor(niveles * proporcionNivelesAltos));
        final var rango = yMax - yMin;
        final var y = indice * rango / (niveles - 1) + yMin;
        final var ancho = Aleatorio.decimalRandom(anchoMinimo, anchoMaximo);
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho / 2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, ancho, altoIsla);
    }

}





package juego;

import entorno.Entorno;

/**
 * La clase que se encarga de generar islas en el mapa de manera aleatoria.
 *
 * @author Noelia Barrionuevo y Miguel Angel Luna Lobos
 */
public class Islas {
    public static final double proporcionAlto = 0.7;
    public static final double proporcionAlturaMinima = 0.0;
    public static final double altoIsla = 20.0;
    public static final double anchoMinimo = 100.0;
    public static final double anchoMaximo = 300.0;
    public static final double factorFronteraAncho = 2.0;
    public static final double factorFronteraAlto = 1.0;

    public static final int nivelesBajos = 3;
    public static final int nivelesAltos = 6;

    private static final double anchoIslaNivelBajo = 100.0;

    public static Isla nuevaNivelBajo(GeneradorId generadorId, Mundo mundo){
        var isla = islaAleatoriaNivelBajo(generadorId, mundo);
        while(mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0){
            isla = islaAleatoriaNivelBajo(generadorId, mundo);
        }
        return isla;
    }

    public static Isla nuevaNivelAlto(GeneradorId generadorId, Mundo mundo){
        var isla = islaAleatoriaNivelAlto(generadorId, mundo);
        while(mundo.islasEnColision(isla.rectangulo(), "fronteraIsla").length > 0){
            isla = islaAleatoriaNivelAlto(generadorId, mundo);
        }
        return isla;
    }

    private static Isla islaAleatoriaNivelBajo(GeneradorId generadorId, Mundo mundo){
        System.out.println("generando isla de nivel bajo");
        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo * 0.4;
        final var alturaMinima = proporcionAlturaMinima * altoMundo;
        final var yMax = altoMundo - alturaMinima - altoIsla / 2.0;
        final var yMin = altoMundo - alturaMinima - alto + altoIsla / 2.0;
        final var y = alturaAleatoria(nivelesBajos, yMin, yMax);
        final var ancho = anchoIslaNivelBajo;
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho/2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, ancho, altoIsla);
    }

    private static double alturaAleatoria(int niveles, double yMax, double yMin) {
        if(niveles < 2){
            throw new IllegalArgumentException("niveles no puede ser inferior a 2");
        }
        int indice = Aleatorio.enteroRandom(0, niveles);
        double rango = yMax - yMin;
        return indice * rango / (niveles - 1) + yMin;
    }

    private static Isla islaAleatoriaNivelAlto(GeneradorId generadorId, Mundo mundo){
        System.out.println("generando isla de nivel alto");
        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo;
        final var alturaMinima = proporcionAlturaMinima * altoMundo + alto / 2.0;
        final var yMax = altoMundo - alturaMinima - altoIsla / 2.0;
        final var yMin = altoMundo - alturaMinima - alto + altoIsla / 2.0;
        final var y = alturaAleatoria(nivelesAltos, yMin, yMax);
        final var ancho = Aleatorio.decimalRandom(anchoMinimo, anchoMaximo);
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho/2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, ancho, altoIsla);
    }


}





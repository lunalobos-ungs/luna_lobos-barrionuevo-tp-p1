package juego;

import entorno.Entorno;

/**
 * La clase que se encarga de generar islas en el mapa de manera aleatoria.
 *
 * @author Noelia Barrionuevo y Miguel Angel Luna Lobos
 */
public class Islas {
    private static final double proporcionAlto = 0.7;
    private static final double proporcionAlturaMinima = 0.0;
    private static final double altoIsla = 20.0;
    private static final double anchoMinimo = 100.0;
    private static final double anchoMaximo = 300.0;
    private static final double factorFronteraAncho = 2.0;
    private static final double factorFronteraAlto = 6.0;

    private static final double anchoIslaNivelBajo = 100.0;

    public Isla nuevaNivelBajo(GeneradorId generadorId, Mundo mundo){
        var isla = islaAleatoriaNivelBajo(generadorId, mundo);
        while(mundo.enColisionCon(Elementos.aPseudoElemento(isla, "fronteraIsla", isla.espacio())).length > 0){
            isla = islaAleatoriaNivelBajo(generadorId, mundo);
        }
        return isla;
    }

    public Isla nuevaNivelAlto(GeneradorId generadorId, Mundo mundo){
        var isla = islaAleatoriaNivelAlto(generadorId, mundo);
        while(mundo.enColisionCon(Elementos.aPseudoElemento(isla, "fronteraIsla", isla.espacio())).length > 0){
            isla = islaAleatoriaNivelAlto(generadorId, mundo);
        }
        return isla;
    }

    private Isla islaAleatoriaNivelAlto(GeneradorId generadorId, Mundo mundo){
        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo *0.6;
        final var alturaMinima = proporcionAlturaMinima * altoMundo + proporcionAlto * altoMundo / 2.0;
        final var yMax = altoMundo - alturaMinima - altoIsla / 2.0;
        final var yMin = altoMundo - alturaMinima - alto + altoIsla / 2.0;
        final var y = Aleatorio.decimalRandom(yMin, yMax);
        final var ancho = Aleatorio.decimalRandom(anchoMinimo, anchoMaximo);
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho/2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, ancho, altoIsla, factorFronteraAncho, factorFronteraAlto);
    }

    private Isla islaAleatoriaNivelBajo(GeneradorId generadorId, Mundo mundo){
        final var anchoMundo = mundo.limitesMundo().ancho();
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = proporcionAlto * altoMundo * 0.4;
        final var alturaMinima = proporcionAlturaMinima * altoMundo;
        final var yMax = altoMundo - alturaMinima - altoIsla / 2.0;
        final var yMin = altoMundo - alturaMinima - alto + altoIsla / 2.0;
        final var y = Aleatorio.decimalRandom(yMin, yMax);
        final var ancho = anchoIslaNivelBajo;
        final var xMin = 0.0 + ancho / 2.0;
        final var xMax = anchoMundo - ancho/2.0;
        final var x = Aleatorio.decimalRandom(xMin, xMax);
        return new Isla(generadorId, x, y, ancho, altoIsla, factorFronteraAncho, factorFronteraAlto);
    }
}





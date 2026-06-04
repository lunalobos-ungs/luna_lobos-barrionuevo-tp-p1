package juego;

import entorno.Entorno;

public class Enemigos {
    private static final double altoEnemigo = 40.0;
    private static final double anchoEnemigo = 40.0;

    /*
     * Hay que tener en cuenta que no ocupe ninguna posicion de la isla
     * cuando hacemos el random para la posicion hay que chequear que no haya una isla en esa Y
     */
    public int generarPosicion(Isla isla1, Isla isla2, Isla isla3, Isla isla4) {
        // considero un margen por el alto/grosor de la isla
        // hago por 500*0,9 porq es lo maximo que quiero que valga la posicion
        // del enemigo para que este por sobre la tierra firme .
        int margen = 40;
        int y;
        y = (int) (Math.random() * 500);
        while
        (Math.abs(y - isla1.y()) < margen || Math.abs(y - isla2.y()) < margen ||
                Math.abs(y - isla3.y()) < margen || Math.abs(y - isla4.y()) < margen) {
            y = (int) (Math.random() * 500);
        }
        return y;

    }

    /*
     * Enemigo 1 , lo voy a posicionar del lado izquierdo
     */
    public Enemigo enemigo1(GeneradorId generadorId, Entorno entorno, Isla isla1, Isla isla2, Isla isla3, Isla isla4) {
        int x = 25;
        int posicion1 = generarPosicion(isla1, isla2, isla3, isla4);
        return new Enemigo(generadorId, entorno, x, posicion1, 0);

    }
    /*
     * Enemigo 2 se va a crear en el margen del lado derecho por eso usamos como angulo Math.PI= -1
     */

    public Enemigo enemigo2(GeneradorId generadorId, Entorno entorno, Isla isla1, Isla isla2, Isla isla3, Isla isla4) {
        int x = 760;
        int posicion2 = generarPosicion(isla1, isla2, isla3, isla4);
        return new Enemigo(generadorId, entorno, x, posicion2, Math.PI);
    }

    /*
     * Enemigo3 se va a crear y posicionar del lado derecho
     * no puede tener la misma posicion con enemigo 1
     * con el while lo que hacemos es que se repita la operacion hasta que se produza
     * una posicion valida
     */
    public Enemigo enemigo3(GeneradorId generadorId, Entorno entorno,
                            Isla isla1, Isla isla2, Isla isla3, Isla isla4, Enemigo enemigo1) {
        int x = 25;
        int posicion3 = generarPosicion(isla1, isla2, isla3, isla4);

        while (posicion3 == enemigo1.y()) {
            posicion3 = generarPosicion(isla1, isla2, isla3, isla4);
        }

        return new Enemigo(generadorId, entorno, x, posicion3, 0);
    }

    /*
     * Se toma en consideracion no repetir posicion de enemigo2
     */
    public Enemigo enemigo4(GeneradorId generadorId, Entorno entorno,
                            Isla isla1, Isla isla2, Isla isla3, Isla isla4, Enemigo enemigo2) {
        int x = 760;
        int posicion3 = generarPosicion(isla1, isla2, isla3, isla4);

        while (posicion3 == enemigo2.y()) {
            posicion3 = generarPosicion(isla1, isla2, isla3, isla4);
        }

        return new Enemigo(generadorId, entorno, x, posicion3, Math.PI);
    }

    /*
     * Sugerencia : Crear un random que me arroje unicamnete dos posiciones de x,
     * para que aparezca unicamnete por margen derecho o margen izquierdo
     */
    public Jefe enemigoExtra(GeneradorId generadorId, Entorno entorno) {
        int x = 20;
        int y = 520;
        return new Jefe(generadorId, entorno, x, y);
    }

    public static Enemigo enemigoAleatorioNielBajo(GeneradorId generadorId, Mundo mundo, double x, double angulo) {
        final var altoMundo = mundo.limitesMundo().alto();
        final var alto = Islas.proporcionAlto * altoMundo;
        final var alturaMinima = Islas.proporcionAlturaMinima * altoMundo + alto / 2.0;
        final var yMax = altoMundo - alturaMinima - Islas.altoIsla / 2.0;
        final var yMin = altoMundo - alturaMinima - alto + altoEnemigo / 2.0;
        final var y = alturaAleatoria(Islas.nivelesBajos, yMin, yMax);
        return new Enemigo(generadorId, x, y, anchoEnemigo, alto, angulo);
    }

    private static double alturaAleatoria(int n, double yMin, double yMax) {
        if (n < 2) {
            throw new IllegalArgumentException("n no puede ser inferior a 2");
        }
        var q = Aleatorio.enteroRandom(0, n - 2);
        var indice = 2 * q + 1;
        var rango = yMax - yMin;
        return indice * rango / (n - 1) + yMin;


    }
}
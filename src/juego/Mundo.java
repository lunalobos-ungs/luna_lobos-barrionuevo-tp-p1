package juego;


import java.util.Objects;

/**
 * Contiene los objetos que se van dibujando en pantalla.
 *
 * @author Miguel Angel Luna Lobos
 * @author Noelia Barrionuevo
 */
public class Mundo {
    public static double proporcionAnchoMundo = 8.0;
    public static double proporcionAltoMundo = 2.0;
    private Isla[] islas;
    private Castillo castillo;
    private int largoIslas;
    private Rectangulo limitesMundo;
    private Rectangulo limitesPantalla;
    private Fondo fondo;

    /**
     * Crea una nueva instancia de Mundo.
     *
     * @param limitesPantalla el rectángulo que indica los límites de la pantalla
     * @param limitesMundo    el rectángulo que indica los límites del mundo
     * @param fondo           el fondo
     * @param castillo        el castillo
     */
    public Mundo(Rectangulo limitesPantalla, Rectangulo limitesMundo, Fondo fondo, Castillo castillo) {
        this.limitesPantalla = Objects.requireNonNull(limitesPantalla, "limitesPantalla no puede ser null");
        this.limitesMundo = Objects.requireNonNull(limitesMundo, "limitesMundo no puede ser null");
        this.fondo = Objects.requireNonNull(fondo, "fondo no puede ser null");
        this.castillo = Objects.requireNonNull(castillo, "castillo no puede ser null");
        islas = new Isla[10];
        largoIslas = 0;
    }

    /**
     * Los límites del mundo.
     *
     * @return un rectángulo que representa los límites del mundo
     */
    public Rectangulo limitesMundo() {
        return limitesMundo;
    }

    /**
     * Los límites de la pantalla.
     *
     * @return un rectángulo que representa los límites de la pantalla.
     */
    public Rectangulo limitesPantalla(Princesa princesa) {
        return new Rectangulo(princesa.x(), princesa.y(), limitesPantalla.ancho(), limitesPantalla.alto());
    }

    /**
     * El fondo del juego.
     *
     * @return el fondo
     */
    public Fondo fondo() {
        return fondo;
    }

    /**
     * El castillo.
     *
     * @return el castillo
     */
    public Castillo castillo() {
        return castillo;
    }

    /**
     * Devuelve un array con las islas en colisión con el rectángulo provisto.
     *
     * @param rectangulo el rectángulo de colisión
     * @param tipo       el tipo de elemento
     * @return un array que lista las islas en colisión con el rectángulo argumento
     */
    public Isla[] islasEnColision(Rectangulo rectangulo, String tipo) {
        if (tipo.equals("fronteraIsla")) {
            Rectangulo frontera = rectangulo.escalar(Isla.factorFronteraAncho, Isla.factorFronteraAlto);
            return enColisionIsla(frontera);
        }
        Isla[] almacenador = new Isla[largoIslas];
        int contador = 0;
        for (int i = 0; i < largoIslas; i++) {
            Isla isla_ = islas[i];
            if (Juego.enColision(rectangulo, isla_.rectangulo())) {
                almacenador[contador++] = isla_;
            }
        }
        Isla[] salida = new Isla[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    private Isla[] enColisionIsla(Rectangulo rectangulo) {
        Isla[] almacenador = new Isla[largoIslas];
        int contador = 0;
        for (int i = 0; i < largoIslas; i++) {
            Isla isla = islas[i];
            Rectangulo frontera = isla.rectangulo().escalar(Isla.factorFronteraAncho, Isla.factorFronteraAlto);
            if (Juego.enColision(rectangulo, frontera)) {
                almacenador[contador++] = isla;
            }
        }
        Isla[] salida = new Isla[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    /**
     * Agrega una isla
     *
     * @param isla la isla agregar
     */
    public void agregarIsla(Isla isla) {
        if (largoIslas == islas.length) {
            Isla[] nuevoArray = new Isla[islas.length * 2];
            System.arraycopy(islas, 0, nuevoArray, 0, islas.length);
            islas = nuevoArray;
        }
        islas[largoIslas++] = isla;
    }

    public void borrarIslas() {
        islas = new Isla[10];
        largoIslas = 0;
    }

    public Isla[] islas() {
        Isla[] copiaIslas = new Isla[largoIslas];
        System.arraycopy(islas, 0, copiaIslas, 0, largoIslas);
        return copiaIslas;
    }
}

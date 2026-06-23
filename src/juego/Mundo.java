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
    private Princesa princesa;
    private Enemigo[] enemigos;
    private Isla[] islas;
    private Jefe jefe;
    private ProyectilPrincesa proyectilPrincesa;
    private Castillo castillo;
    private int largoEnemigos;
    private int largoIslas;
    private Rectangulo limitesMundo;
    private Rectangulo limitesPantalla;
    private Fondo fondo;

    /**
     * Crea una nueva instancia de Mundo.
     * @param limitesPantalla el rectángulo que indica los límites de la pantalla
     * @param limitesMundo el rectángulo que indica los límites del mundo
     * @param princesa la princesa
     * @param jefe el jefe
     * @param fondo el fondo
     * @param castillo el castillo
     */
    public Mundo(
            Rectangulo limitesPantalla,
            Rectangulo limitesMundo,
            Princesa princesa,
            Jefe jefe,
            Fondo fondo,
            Castillo castillo
    ) {

        this.limitesPantalla = Objects.requireNonNull(limitesPantalla, "limitesPantalla no puede ser null");
        this.limitesMundo = Objects.requireNonNull(limitesMundo, "limitesMundo no puede ser null");
        this.princesa = Objects.requireNonNull(princesa, "princesa no puede ser null");
        this.jefe = jefe;
        this.fondo = Objects.requireNonNull(fondo, "fondo no puede ser null");
        this.castillo = Objects.requireNonNull(castillo, "castillo no puede ser null");
        islas = new Isla[10];
        enemigos = new Enemigo[10];
        largoEnemigos = 0;
        largoIslas = 0;
    }

    /**
     * Los límites del mundo.
     * @return un rectángulo que representa los límites del mundo
     */
    public Rectangulo limitesMundo(){
        return limitesMundo;
    }

    /**
     * Los límites de la pantalla.
     * @return un rectángulo que representa los límites de la pantalla.
     */
    public Rectangulo limitesPantalla() {
        return new Rectangulo(princesa.x(), princesa.y(), limitesPantalla.ancho(), limitesPantalla.alto());
    }

    /**
     * El fondo del juego.
     * @return el fondo
     */
    public Fondo fondo(){
        return fondo;
    }

    /**
     * La princesa.
     * @return la princesa
     */
    public Princesa princesa() {
        return princesa;
    }

    /**
     * El castillo.
     * @return el castillo
     */
    public Castillo castillo() { return castillo; }

    /**
     * El jefe.
     * @return el jefe o null si no existe
     */
    public Jefe jefe() {
        return jefe;
    }

    /**
     * El proyectil que disparó la princesa. Puede ser null.
     * @return el proyectil disparado por la princesa, o null si no existe
     */
    public ProyectilPrincesa proyectilPrincesa(){
        return proyectilPrincesa;
    }

    /**
     * Devuelve un array con las islas en colisión con el rectángulo provisto.
     * @param rectangulo el rectángulo de colisión
     * @param tipo el tipo de elemento
     * @return un array que lista las islas en colisión con el rectángulo argumento
     */
    public Isla[] islasEnColision(Rectangulo rectangulo, String tipo){
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
     * Devuelve un array con los enemigos en colisión con el rectángulo provisto.
     * @param rectangulo el rectángulo de colisión
     * @return un array que lista los enemigos en colisión con el rectángulo argumento
     */
    public Enemigo[] enemigosEnColision(Rectangulo rectangulo){
        Enemigo[] almacenador = new Enemigo[largoEnemigos];
        int contador = 0;
        for (int i = 0; i < largoEnemigos; i++) {
            Enemigo enemigo = enemigos[i];
            if (Juego.enColision(rectangulo, enemigo.rectangulo())) {
                almacenador[contador++] = enemigo;
            }
        }
        Enemigo[] salida = new Enemigo[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    /**
     * Agrega un enemigo.
     * @param enemigo el enemigo a agregar
     */
    public void agregarEnemigo(Enemigo enemigo) {
        if (largoEnemigos == enemigos.length) {
            Enemigo[] nuevoArray = new Enemigo[enemigos.length * 2];
            System.arraycopy(enemigos, 0, nuevoArray, 0, enemigos.length);
            enemigos = nuevoArray;
        }
        enemigos[largoEnemigos++] = enemigo;
    }

    /**
     * Agrega una isla
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

    /**
     * Quita un enemigo.
     * @param enemigo el enemigo a quitar
     */
    public void quitarEnemigo(Enemigo enemigo) {
        int indice = indiceDeEnemigo(enemigo);
        if (indice >= 0) {
            for (int i = indice; i < largoEnemigos - 1; i++) {
                enemigos[i] = enemigos[i + 1];
            }
            enemigos[largoEnemigos - 1] = null;
            largoEnemigos--;
        }
    }

    private int indiceDeEnemigo(Enemigo enemigo) {
        int indiceMinimo = 0;
        int indiceMaximo = largoEnemigos - 1;
        while (indiceMinimo <= indiceMaximo) {
            int indiceIntermedio = (indiceMinimo + indiceMaximo) / 2;
            Enemigo elementoIntermedio = enemigos[indiceIntermedio];
            int comparacion = compararEnemigos(elementoIntermedio, enemigo);

            if (comparacion < 0) {
                indiceMinimo = indiceIntermedio + 1;
            } else if (comparacion > 0) {
                indiceMaximo = indiceIntermedio - 1;
            } else {
                return indiceIntermedio; // Encontrado (comparacion == 0)
            }
        }
        return -(indiceMinimo + 1);
    }

    private int compararEnemigos(Enemigo enemigo1, Enemigo enemigo2) {
        if (enemigo1.id() < enemigo2.id()) {
            return -1;
        } else if (enemigo1.id() == enemigo2.id()) {
            return 0; // esta condición nunca debería ocurrir porque los ids son únicos
        } else {
            return 1;
        }
    }

    /**
     * Se ocupa de eliminar a los enemigos que se salén de la pantalla así como de eliminar a los que
     * deben morir por otras razones.
     */
    public void purgar() {
        int i = 0;
        while (i < largoEnemigos) {
            Enemigo enemigo = enemigos[i];
            if (!Juego.enColision(enemigo.rectangulo(), limitesPantalla()) || enemigo.debeEliminarse()) {
                quitarEnemigo(enemigo);
            } else {
                i++;
            }
        }

        if(jefe != null && jefe.vidas() <= 0){
            jefe = null;
        }

        if(proyectilPrincesa != null && !Juego.enColision(proyectilPrincesa.rectangulo(), limitesPantalla())){
            proyectilPrincesa = null;
        }
    }

    /**
     * Establece el proyectil de la princesa.
     * @param proyectilPrincesa el proyectil de la princesa
     */
    public void establecerProyectilPrincesa(ProyectilPrincesa proyectilPrincesa){
        this.proyectilPrincesa = proyectilPrincesa;
    }

    public void borrarIslas(){
        islas = new Isla[10];
        largoIslas = 0;
    }

    public Enemigo[] enemigos() {
        Enemigo[] copiaEnemigos = new Enemigo[largoEnemigos];
        System.arraycopy(enemigos, 0, copiaEnemigos, 0, largoEnemigos);
        return copiaEnemigos;
    }

    public Isla[] islas() {
        Isla[] copiaIslas = new Isla[largoIslas];
        System.arraycopy(islas, 0, copiaIslas, 0, largoIslas);
        return copiaIslas;
    }

    /**
     * Indica si faltan enemigos.
     * @return true si se deben generar más enemigos.
     */
    public boolean faltanEnemigos() {
        return largoEnemigos < Enemigo.minimoEnemigos;
    }
}

package juego;


import entorno.Entorno;

import java.util.Objects;

/**
 * Contiene los objetos que se van dibujando en pantalla.
 *
 * @author Miguel Angel Luna Lobos y Noelia Barrionuevo
 */
public class Mundo {

    private static final double proporcionAnchoMundo = 30.0;
    private static final double proporcionAltoMundo = 2.0;
    private final Princesa princesa;
    private Enemigo[] enemigos;
    private Isla[] islas;
    private Jefe jefe;
    private ProyectilPrincesa proyectilPrincesa;

    private int largoEnemigos;

    private int largoIslas;

    private final Rectangulo limitesMundo;

    private final Rectangulo pantalla;

    public Mundo(Entorno entorno, Princesa princesa, Jefe jefe) {
        this.princesa = Objects.requireNonNull(princesa, "la princesa no puede ser null");
        this.jefe = jefe;
        var anchoPantalla = entorno.ancho();
        var altoPantalla = entorno.alto();
        largoEnemigos = 0;
        largoIslas = 0;
        var anchoMundo = anchoPantalla * proporcionAnchoMundo;
        var altoMundo = altoPantalla * proporcionAltoMundo;
        limitesMundo = new Rectangulo(anchoMundo / 2.0, altoMundo / 2.0, anchoMundo, altoMundo);
        pantalla = new Rectangulo(anchoPantalla / 2.0, altoPantalla / 2.0, anchoPantalla, altoPantalla);
        islas = new Isla[10];
        enemigos = new Enemigo[10];
    }

    public Rectangulo limitesMundo(){
        return limitesMundo;
    }

    public Rectangulo limitesPantalla() {
        return new Rectangulo(princesa.x(), princesa.y(), pantalla.ancho(), pantalla.alto());
    }

    public Princesa princesa() {
        return princesa;
    }

    public Jefe jefe() {
        return jefe;
    }

    public ProyectilPrincesa proyectilPrincesa(){
        return proyectilPrincesa;
    }
    public Isla[] islasEnColision(Rectangulo rectangulo, String tipo){
        if (tipo.equals("fronteraIsla")) {
            var frontera = rectangulo.escalar(Islas.factorFronteraAncho, Islas.factorFronteraAlto);
            return enColisionIsla(frontera);
        }
        final Isla[] almacenador = new Isla[largoIslas];
        int contador = 0;
        for (int i = 0; i < largoIslas; i++) {
            Isla isla_ = islas[i];
            if (Rectangulos.enColision(rectangulo, isla_.rectangulo())) {
                almacenador[contador++] = isla_;
            }
        }
        final Isla[] salida = new Isla[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    private Isla[] enColisionIsla(Rectangulo rectangulo) {
        final Isla[] almacenador = new Isla[largoIslas];
        int contador = 0;
        for (int i = 0; i < largoIslas; i++) {
            var isla = islas[i];
            var frontera = isla.rectangulo().escalar(Islas.factorFronteraAncho, Islas.factorFronteraAlto);
            if (Rectangulos.enColision(rectangulo, frontera)) {
                almacenador[contador++] = isla;
            }
        }
        final Isla[] salida = new Isla[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    public Enemigo[] enemigosEnColision(Rectangulo rectangulo){
        final Enemigo[] almacenador = new Enemigo[largoEnemigos];
        int contador = 0;
        for (int i = 0; i < largoEnemigos; i++) {
            Enemigo enemigo = enemigos[i];
            if (Rectangulos.enColision(rectangulo, enemigo.rectangulo())) {
                almacenador[contador++] = enemigo;
            }
        }
        final Enemigo[] salida = new Enemigo[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    public void agregarEnemigo(Enemigo enemigo) {
        if (largoEnemigos == enemigos.length) {
            Enemigo[] nuevoArray = new Enemigo[enemigos.length * 2];
            System.arraycopy(enemigos, 0, nuevoArray, 0, enemigos.length);
            enemigos = nuevoArray;
        }
        enemigos[largoEnemigos++] = enemigo;
    }

    public void agregarIsla(Isla isla) {
        if (largoIslas == islas.length) {
            Isla[] nuevoArray = new Isla[islas.length * 2];
            System.arraycopy(islas, 0, nuevoArray, 0, islas.length);
            islas = nuevoArray;
        }
        islas[largoIslas++] = isla;
    }

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

    public void quitarIsla(Isla isla) {
        int indice = indiceDeIsla(isla);
        if (indice >= 0) {
            for (int i = indice; i < largoIslas - 1; i++) {
                islas[i] = islas[i + 1];
            }
            islas[largoIslas - 1] = null;
            largoIslas--;
        }
    }

    private int indiceDeIsla(Isla isla) {
        int indiceMinimo = 0;
        int indiceMaximo = largoIslas - 1;
        while (indiceMinimo <= indiceMaximo) {
            int indiceIntermedio = (indiceMinimo + indiceMaximo) / 2;
            Isla elementoIntermedio = islas[indiceIntermedio];
            int comparacion = compararIslas(elementoIntermedio, isla);

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

    private int compararIslas(Isla isla1, Isla isla2) {
        if (isla1.id() < isla2.id()) {
            return -1;
        } else if (isla1.id() == isla2.id()) {
            return 0; // esta condición nunca debería ocurrir porque los ids son únicos
        } else {
            return 1;
        }
    }

    /*
     * 1° elimina elementos que salgan de la pantalla
     * 2° eliminar enemigos que tienen como estado "muerto"
     * 3° eliminar enemigo cuando toca la princesa
     */
    public void purgar() {

        int i = 0;
        while (i < largoEnemigos) {
            var enemigo = enemigos[i];
            if (!Rectangulos.enColision(enemigo.rectangulo(), limitesPantalla()) || enemigo.debeEliminarse()) {
                System.out.println("purgando enemigo");
                quitarEnemigo(enemigo);
            } else {
                i++;
            }
        }

        if(jefe != null && (jefe.debeEliminarse() || !Rectangulos.enColision(jefe.rectangulo(), limitesMundo))){
            System.out.println("purgando jefe");
            jefe = null;
        }

        if(proyectilPrincesa != null && !Rectangulos.enColision(proyectilPrincesa.rectangulo(), limitesPantalla())){
            System.out.println("purgando proyectil princesa");
            proyectilPrincesa = null;
        }
    }
    public void establecerProyectilPrincesa(ProyectilPrincesa proyectilPrincesa){
        this.proyectilPrincesa = proyectilPrincesa;
    }
    public IteradorIslas iteradorIslas(){
        return new IteradorIslas(islas, largoIslas);
    }

    public IteradorEnemigos iteradorEnemigos(){
        return new IteradorEnemigos(enemigos, largoEnemigos);
    }

    public boolean faltanEnemigos() {
        return largoEnemigos < Enemigos.minimoEnemigos;
    }
}
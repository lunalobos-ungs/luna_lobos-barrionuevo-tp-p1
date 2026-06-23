package juego;


import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Juego extends InterfaceJuego {
    public static Random random = new Random();

    static int enteroRandom(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        int rango = max - min;
        // max - min - 1 + min = max - 1
        if (rango == 0) {
            return min;
        }
        return random.nextInt(rango) + min;
    }

    public static Image cargarYEscalar(String nombreArchivo, double ancho, double alto) {
        Image imagen = Herramientas.cargarImagen("recursos/" + nombreArchivo);
        return imagen.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    /**
     * Determina la dirección desde la que r1 llega a colisionar con r2.
     *
     * @param r1 el rectángulo cuya dirección de llegada se determina
     * @param r2 el rectángulo impactado
     * @return "desde arriba", "desde abajo", "desde la izquierda"
     * o "desde la derecha"
     * @throws IllegalArgumentException si los rectángulos no están en colisión
     */
    public static String tipoDeColision(Rectangulo r1, Rectangulo r2) {
        double x1 = r1.x();
        double x2 = r2.x();
        double y1 = r1.y();
        double y2 = r2.y();
        double bordeIzquierdo1 = r1.bordeIzquierdo();
        double bordeDerecho1 = r1.bordeDerecho();
        double bordeSuperior1 = r1.bordeSuperior();
        double bordeInferior1 = r1.bordeInferior();
        double bordeIzquierdo2 = r2.bordeIzquierdo();
        double bordeDerecho2 = r2.bordeDerecho();
        double bordeSuperior2 = r2.bordeSuperior();
        double bordeInferior2 = r2.bordeInferior();

        double deltaY = 0.0;
        double deltaX = 0.0;
        boolean desdeArriba = bordeInferior1 >= bordeSuperior2 && y1 <= y2;
        boolean desdeAbajo = bordeSuperior1 <= bordeInferior2 && y1 >= y2;
        boolean desdeLaDerecha = bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2;
        boolean desdeLaIzquierda = bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2;

        if (desdeArriba) {
            deltaY = Math.min(bordeInferior1 - bordeSuperior2, r1.alto());
        }

        if (desdeAbajo) {
            deltaY = Math.min(bordeInferior2 - bordeSuperior1, r1.alto());
        }

        if (desdeLaDerecha) {
            deltaX = Math.min(bordeDerecho1 - bordeIzquierdo2, r1.ancho());
        }

        if (desdeLaIzquierda) {
            deltaX = Math.min(bordeDerecho2 - bordeIzquierdo1, r1.ancho());
        }

        if (deltaY >= deltaX) { // lateral
            if (desdeLaDerecha) {
                return "desde la derecha";
            } else if (desdeLaIzquierda) {
                return "desde la izquierda";
            }
        } else { // vertical
            if (desdeArriba) {
                return "desde arriba";
            } else if (desdeAbajo) {
                return "desde abajo";
            }
        }

        throw new IllegalArgumentException("no hay colisión");
    }

    /**
     * Detecta si dos rectángulos se encuentran o no en colisión.
     *
     * @param r1 el primer rectángulo
     * @param r2 el segundo rectángulo
     * @return true si están en colisión o false de lo contrario
     */
    public static boolean enColision(Rectangulo r1, Rectangulo r2) {
        double x1 = r1.x();
        double x2 = r2.x();
        double y1 = r1.y();
        double y2 = r2.y();
        double bordeIzquierdo1 = r1.bordeIzquierdo();
        double bordeDerecho1 = r1.bordeDerecho();
        double bordeSuperior1 = r1.bordeSuperior();
        double bordeInferior1 = r1.bordeInferior();
        double bordeIzquierdo2 = r2.bordeIzquierdo();
        double bordeDerecho2 = r2.bordeDerecho();
        double bordeSuperior2 = r2.bordeSuperior();
        double bordeInferior2 = r2.bordeInferior();
        return ((bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2) || (bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2)) && ((bordeSuperior1 <= bordeInferior2 && y1 >= y2) || (bordeInferior1 >= bordeSuperior2 && y1 <= y2));
    }

    public static double transformarX(double x, Princesa princesa, Entorno entorno) {
        Rectangulo rectanguloPrincesa = princesa.rectangulo();
        double dx = entorno.ancho() / 2.0;
        return x - rectanguloPrincesa.x() + dx;
    }

    public static double transformarY(double y, Princesa princesa, Entorno entorno) {
        Rectangulo rectanguloPrincesa = princesa.rectangulo();
        double dy = entorno.alto() / 2.0;
        return y - rectanguloPrincesa.y() + dy;
    }

    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private Mundo mundo;

    private Princesa princesa;

    private Jefe jefe;

    private Enemigo[] enemigos;
    private int largoEnemigos;

    private ProyectilPrincesa proyectilPrincesa;
    private boolean victoria;
    private boolean derrota;

    private int idEnemigoActual;

    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...
        victoria = false;
        derrota = false;
        double anchoPantalla = entorno.ancho();
        double altoPantalla = entorno.alto();
        double anchoMundo = anchoPantalla * Mundo.proporcionAnchoMundo;
        double altoMundo = altoPantalla * Mundo.proporcionAltoMundo;
        Rectangulo limitesPantalla = new Rectangulo(anchoPantalla / 2.0, altoPantalla / 2.0, anchoPantalla, altoPantalla);
        Rectangulo limitesMundo = new Rectangulo(anchoMundo / 2.0, altoMundo / 2.0, anchoMundo, altoMundo);
        Image imagenPrincesa = cargarYEscalar("princesa.png", Princesa.anchoPrincesa, Princesa.altoPrincesa);
        Image imagenCorazon = cargarYEscalar("corazon.png", Princesa.ladoCorazon, Princesa.ladoCorazon);
        princesa = new Princesa(0.0, 0.0, Princesa.anchoPrincesa, Princesa.altoPrincesa, imagenPrincesa, imagenCorazon);
        Image imagenJefeHaciaDerecha = cargarYEscalar("jefe_hacia_derecha.png", Jefe.anchoJefe, Jefe.altoJefe);
        Image imagenJefeHaciaIzquierda = cargarYEscalar("jefe_hacia_izquierda.png", Jefe.anchoJefe, Jefe.altoJefe);
        jefe = new Jefe(0, 0, Jefe.anchoJefe, Jefe.altoJefe, imagenJefeHaciaDerecha, imagenJefeHaciaIzquierda);
        Image imagenFondo = cargarYEscalar("fondo.png", anchoPantalla, altoPantalla);
        Fondo fondo = new Fondo(anchoPantalla / 2.0, altoPantalla / 2.0, imagenFondo);
        Image imagenCastillo = cargarYEscalar("castillo.png", Isla.anchoMinimo, Isla.anchoMinimo);
        Castillo castillo = new Castillo(0.0, 0.0, Isla.anchoMinimo, Isla.anchoMinimo, imagenCastillo);
        mundo = new Mundo(limitesPantalla, limitesMundo, fondo, castillo);
        enemigos = new Enemigo[10];
        largoEnemigos = 0;
        double cantidadIslasBajas = Isla.proporcionIslasBajas * mundo.limitesMundo().area();
        double cantidadIslasAltas = Isla.proporcionIslasAltas * mundo.limitesMundo().area();
        int contadorExcepciones = 0;

        Isla primeraIsla = null;
        Isla anteUltimaIsla = null;
        Isla ultimaIsla = null;

        while (contadorExcepciones < 1000) {
            try {
                mundo.borrarIslas();
                int contador = 0;
                while (contador < cantidadIslasBajas) {
                    Isla isla = Isla.nuevaNivelBajo(mundo);
                    if (isla == null) {
                        break;
                    }
                    mundo.agregarIsla(isla);
                    contador++;
                }
                contador = 0;
                double xMin = mundo.limitesMundo().ancho();
                double xMax = 0.0;
                double yMin = mundo.limitesMundo().alto();
                while (contador < cantidadIslasAltas) {
                    Isla isla = Isla.nuevaNivelAlto(mundo);

                    if (isla == null) {
                        break;
                    }
                    if (isla.x() < xMin) {
                        xMin = isla.x();
                        primeraIsla = isla;
                    }
                    if (isla.x() > xMax && isla.y() <= yMin) {
                        xMax = isla.x();
                        yMin = isla.y();
                        anteUltimaIsla = ultimaIsla;
                        ultimaIsla = isla;
                    }

                    mundo.agregarIsla(isla);
                    contador++;
                }
                Objects.requireNonNull(primeraIsla, "no se ha encontrado una isla en la cual colocar a la princesa");
                Objects.requireNonNull(anteUltimaIsla, "no se ha encontrado una isla en la cual colocar al jefe");
                Objects.requireNonNull(ultimaIsla, "no se ha encontrado una isla en la cual colocar el castillo");
                break;
            } catch (NullPointerException e) {
                contadorExcepciones++;
            }
        }
        Objects.requireNonNull(primeraIsla, "no se ha encontrado una isla en la cual colocar a la princesa");
        Objects.requireNonNull(anteUltimaIsla, "no se ha encontrado una isla en la cual colocar al jefe");
        Objects.requireNonNull(ultimaIsla, "no se ha encontrado una isla en la cual colocar el castillo");

        princesa.trasladar(primeraIsla.x(), primeraIsla.y() - Isla.altoIsla / 2.0 - princesa.alto() / 2.0);
        jefe.establecerIsla(anteUltimaIsla);
        mundo.castillo().trasladar(ultimaIsla.x(), ultimaIsla.y() - Isla.altoIsla / 2.0 - mundo.castillo().alto() / 2.0);
        // Inicia el juego!
        this.entorno.iniciar();
    }


    /**
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle).
     */
    public void tick() {
        // Procesamiento de un instante de tiempo
        // ...
        mundo.fondo().dibujar(entorno);
        if (victoria) {
            dibujarEnemigos();
            colisionesYDibujoIslas();
            colisionesYDibujoJefe();
            colisionesYDibujoProyectilPrincesa();
            colisionesYDibujoCastillo();
            double x = princesa.x();
            double y = princesa.y();
            princesa.trasladar(x, y);
            entorno.cambiarFont("Arial", 72, Color.BLUE);
            entorno.escribirTexto("¡Ganaste!", entorno.ancho() / 2.0 - 160, entorno.alto() / 2.0);
            return;
        }
        if (derrota) {
            dibujarEnemigos();
            colisionesYDibujoIslas();
            colisionesYDibujoJefe();
            colisionesYDibujoProyectilPrincesa();
            colisionesYDibujoCastillo();
            double x = princesa.x();
            double y = princesa.y();
            princesa.trasladar(x, y);
            entorno.cambiarFont("Arial", 72, Color.RED);
            entorno.escribirTexto("Perdiste", entorno.ancho() / 2.0 - 150, entorno.alto() / 2.0);
            return;
        }

        if (faltanEnemigos()) {
            Enemigo enemigo;

            if (random.nextBoolean()) {
                enemigo = Enemigo.nuevoEnemigoDerecha(idEnemigoActual++, princesa, mundo, entorno, this);
            } else {
                enemigo = Enemigo.nuevoEnemigoIzquierda(idEnemigoActual++, princesa, mundo, entorno, this);
            }

            if (enemigo != null) {
                agregarEnemigo(enemigo);
            }
        }

        int i = 0;
        while (i < largoEnemigos) {
            Enemigo enemigo = enemigos[i];
            if (!Juego.enColision(enemigo.rectangulo(), mundo.limitesPantalla(princesa)) || enemigo.debeEliminarse()) {
                quitarEnemigo(enemigo);
            } else {
                i++;
            }
        }
        if (jefe != null && jefe.vidas() <= 0) {
            jefe = null;
        }
        if (proyectilPrincesa != null && !Juego.enColision(proyectilPrincesa.rectangulo(), mundo.limitesPantalla(princesa))) {
            proyectilPrincesa = null;
        }

        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && proyectilPrincesa == null) {
            princesa.disparar(entorno, this);
        }
        colisionesYDibujoIslas();
        colisionesYDibujoJefe();
        colisionesYDibujoProyectilPrincesa();
        dibujarEnemigos();
        colisionesYDibujoCastillo();
        colisionesYDibujoPrincesa();
    }

    public boolean faltanEnemigos() {
        return largoEnemigos < Enemigo.minimoEnemigos;
    }

    public void agregarEnemigo(Enemigo enemigo) {
        if (largoEnemigos == enemigos.length) {
            Enemigo[] nuevoArray = new Enemigo[enemigos.length * 2];
            System.arraycopy(enemigos, 0, nuevoArray, 0, enemigos.length);
            enemigos = nuevoArray;
        }
        enemigos[largoEnemigos++] = enemigo;
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

    public Enemigo[] enemigosEnColision(Rectangulo rectangulo) {
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

    private void dibujarEnemigos() {
        for (int i = 0; i < largoEnemigos; i++) {
            Enemigo enemigo = enemigos[i];
            enemigo.mover();
            enemigo.dibujar(entorno, princesa);
        }
    }

    private void colisionesYDibujoIslas() {
        ProyectilPrincesa proyectil = proyectilPrincesa;
        Isla[] islas = mundo.islas();
        for (Isla isla : islas) {
            Rectangulo rectanguloIsla = isla.rectangulo();
            if (enColision(princesa.rectangulo(), rectanguloIsla)) {
                String tipoDeColision = Juego.tipoDeColision(princesa.rectangulo(), rectanguloIsla);

                switch (tipoDeColision) {
                    case "desde arriba":
                        princesa.trasladar(princesa.x(), rectanguloIsla.y() - rectanguloIsla.alto() / 2.0 - princesa.alto() / 2.0);
                        princesa.enTierraFirme();
                        break;
                    case "desde abajo":
                        princesa.chocarTecho();
                        break;
                    case "desde la derecha":
                        princesa.chocarMuroPorDerecha();
                        break;
                    case "desde la izquierda":
                        princesa.chocarMuroPorIzquierda();
                        break;
                    default:
                        throw new IllegalArgumentException("tipo de colisión " + tipoDeColision + " no válido");
                }
            }
            if (proyectil != null && enColision(proyectil.rectangulo(), rectanguloIsla)) {
                String tipoDeColision = Juego.tipoDeColision(proyectil.rectangulo(), rectanguloIsla);
                switch (tipoDeColision) {
                    case "desde arriba":
                    case "desde abajo":
                        proyectil.reboteVertical();
                        break;
                    case "desde la derecha":
                    case "desde la izquierda":
                        proyectil.reboteHorizontal();
                        break;
                    default:
                        throw new IllegalArgumentException("tipo de colisión %s no soportado".formatted(tipoDeColision));
                }
            }
            isla.dibujar(entorno, princesa);
        }
    }

    private void colisionesYDibujoJefe() {
        if (jefe != null && jefe.vidas() <= 0) {
            jefe = null;
        }
        if (jefe == null) {
            return;
        }
        if (enColision(jefe.rectangulo(), princesa.rectangulo())) {
            princesa.morir();
        }
        jefe.mover();
        jefe.dibujar(entorno, princesa);
    }

    private void colisionesYDibujoProyectilPrincesa() {
        if (proyectilPrincesa == null) {
            return;
        }
        if (jefe != null && enColision(jefe.rectangulo(), proyectilPrincesa.rectangulo())) {
            proyectilPrincesa = null;
            jefe.pierdeUnaVida();
            // El jefe emite sonido cuando un proyectil lo colisiona
            try {
                Herramientas.play("recursos/dragon.wav");
            } catch (Exception error) {
                System.out.println("No se puede reproducir sonido de dragon");
            }
            return;
        }

        Enemigo[] enemigosEnColision = enemigosEnColision(proyectilPrincesa.rectangulo());
        if (enemigosEnColision.length > 0) {
            proyectilPrincesa = null;
            for (Enemigo enemigo : enemigosEnColision) {
                enemigo.morir();
            }
            return;
        }

        proyectilPrincesa.mover();
        proyectilPrincesa.dibujar(entorno, princesa);
    }

    private void colisionesYDibujoPrincesa() {
        if (princesa.vidas() <= 0) {
            derrota = true;
        }
        if (princesa.y() > mundo.limitesMundo().alto() * 2.5) {
            // se agrega sonido cuando la princesa cae al vacío y pierde una vida
            try {
                Herramientas.play("recursos/dragon2.wav");
            } catch (Exception error) {
                System.out.println("Princesa cae al vacio no funciona el sonido");
            }
            princesaCaeAlVacio();
        }
        Enemigo[] enemigosEnColision = enemigosEnColision(princesa.rectangulo());
        for (Enemigo enemigo : enemigosEnColision) {
            enemigo.morir();
            // la princesa emite sonido cuando le sacan vidas
            try {
                Herramientas.play("recursos/PrincesaPierdeVidas.wav");
            } catch (Exception error) {
                System.out.println("Sonido de pierde vidas con enemigo");
            }
            princesa.pierdeUnaVida();
        }
        princesa.mover(entorno);
        princesa.dibujar(entorno);
    }

    private void princesaCaeAlVacio() {
        princesa.pierdeUnaVida();
        if (princesa.vidas() <= 0) {
            derrota = true;
            return;
        }

        Isla islaMasCercana = null;
        double deltaX = Double.POSITIVE_INFINITY;
        Isla[] islas = mundo.islas();
        for (Isla isla : islas) {
            double nuevoDeltaX = Math.abs(isla.x() - princesa.x());
            if (nuevoDeltaX <= deltaX) {
                deltaX = nuevoDeltaX;
                islaMasCercana = isla;
            }
        }
        Objects.requireNonNull(islaMasCercana, "error fatal, no se ha encontrado una isla donde colocar a la princesa");
        princesa.trasladar(islaMasCercana.x(), islaMasCercana.y() - Isla.altoIsla / 2.0 - princesa.alto() / 2.0);
    }

    private void colisionesYDibujoCastillo() {
        Castillo castillo = mundo.castillo();
        if (enColision(princesa.rectangulo(), castillo.rectangulo())) {
            victoria = true;
        }
        mundo.castillo().dibujar(entorno, princesa);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }

    public void establecerProyectilPrincesa(ProyectilPrincesa proyectil) {
        proyectilPrincesa = proyectil;
    }
}

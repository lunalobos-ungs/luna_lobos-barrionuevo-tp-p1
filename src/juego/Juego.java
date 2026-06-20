package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

import entorno.Herramientas ;

public class Juego extends InterfaceJuego {
    public static Random random = new Random();

    static int enteroRandom(int min, int max){
        if(min > max){
            throw new IllegalArgumentException("max debe ser mayor a min");
        }
        int rango = max - min;
        // max - min - 1 + min = max - 1
        if(rango == 0) {
            return min;
        }
        return random.nextInt(rango) + min;
    }
    public static Image cargarYEscalar(String nombreArchivo, double ancho, double alto){
        Image imagen = Herramientas.cargarImagen("recursos/" + nombreArchivo);
        return imagen.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private GeneradorId generadorId;
    private Mundo mundo;

    private boolean victoria;
    private boolean derrota;

    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...

        generadorId = new GeneradorId();
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
        Princesa princesa = new Princesa(0.0, 0.0, Princesa.anchoPrincesa, Princesa.altoPrincesa, imagenPrincesa, imagenCorazon);
        Image imagenJefeHaciaDerecha = cargarYEscalar("jefe_hacia_derecha.png", Jefe.anchoJefe, Jefe.altoJefe);
        Image imagenJefeHaciaIzquierda = cargarYEscalar("jefe_hacia_izquierda.png", Jefe.anchoJefe, Jefe.altoJefe);
        Jefe jefe = new Jefe( 0, 0, Jefe.anchoJefe, Jefe.altoJefe, imagenJefeHaciaDerecha, imagenJefeHaciaIzquierda);
        Image imagenFondo = cargarYEscalar("fondo.png", anchoPantalla, altoPantalla);
        Fondo fondo = new Fondo(anchoPantalla / 2.0, altoPantalla / 2.0, imagenFondo);
        Image imagenCastillo = cargarYEscalar("castillo.png", Isla.anchoMinimo, Isla.anchoMinimo);
        Castillo castillo = new Castillo(0.0, 0.0, Isla.anchoMinimo, Isla.anchoMinimo, imagenCastillo);
        mundo = new Mundo(limitesPantalla, limitesMundo, princesa, jefe, fondo, castillo);

        double cantidadIslasBajas = Isla.proporcionIslasBajas * mundo.limitesMundo().area();
        double cantidadIslasAltas = Isla.proporcionIslasAltas * mundo.limitesMundo().area();
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
        Isla primeraIsla = null;
        Isla anteUltimaIsla = null;
        Isla ultimaIsla = null;
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
        mundo.princesa().trasladar(primeraIsla.x(), primeraIsla.y() - Isla.altoIsla / 2.0 - mundo.princesa().alto() / 2.0);
        mundo.jefe().establecerIsla(anteUltimaIsla);
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
            colisionesIslas();
            colisionesJefe();
            colisionesProyectilPrincesa();
            colisionesCastillo();
            double x = mundo.princesa().x();
            double y = mundo.princesa().y();
            mundo.princesa().trasladar(x, y);
            entorno.cambiarFont("Arial", 72, Color.BLUE);
            entorno.escribirTexto("¡Ganaste!", entorno.ancho() / 2.0 - 160, entorno.alto() / 2.0);
            return;
        }
        if (derrota) {
            dibujarEnemigos();
            colisionesIslas();
            colisionesJefe();
            colisionesProyectilPrincesa();
            colisionesCastillo();
            double x = mundo.princesa().x();
            double y = mundo.princesa().y();
            mundo.princesa().trasladar(x, y);
            entorno.cambiarFont("Arial", 72, Color.RED);
            entorno.escribirTexto("Perdiste", entorno.ancho() / 2.0 - 150, entorno.alto() / 2.0);
            return;
        }
        if (mundo.faltanEnemigos()) {
            int opcion = Juego.enteroRandom(0, 2);
            Enemigo enemigo;
            switch (opcion) {
                case 0:
                    enemigo = Enemigo.nuevoEnemigoDerecha(generadorId, mundo, entorno);
                    break;
                case 1:
                    enemigo = Enemigo.nuevoEnemigoIzquierda(generadorId, mundo, entorno);
                    break;
                default:
                    throw new RuntimeException("error desconocido");
            }

            if (enemigo != null) {
                mundo.agregarEnemigo(enemigo);
            }
        }
        mundo.purgar(); // eliminamos a aquellos que se salen del mundo
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && mundo.proyectilPrincesa() == null) {
            mundo.princesa().disparar(mundo, entorno);
        }
        colisionesIslas();
        colisionesJefe();
        colisionesProyectilPrincesa();
        dibujarEnemigos();
        colisionesCastillo();
        colisionesPrincesa();
    }

    private void dibujarEnemigos() {
        IteradorEnemigos iterador = mundo.iteradorEnemigos();
        while (iterador.tieneOtro()) {
            Enemigo enemigo = iterador.proximo();
            enemigo.mover();
            enemigo.dibujar(entorno, mundo);
        }
    }

    private void colisionesIslas() {
        IteradorIslas iterador = mundo.iteradorIslas();
        Princesa princesa = mundo.princesa();
        ProyectilPrincesa proyectil = mundo.proyectilPrincesa();
        while (iterador.tieneOtro()) {
            Isla isla = iterador.proximo();
            if (Rectangulos.enColision(isla.rectangulo(), princesa.rectangulo())) {
                isla.actuarSobrePrincesa(princesa);
            }
            if (proyectil != null && Rectangulos.enColision(isla.rectangulo(), proyectil.rectangulo())) {
                isla.actuarSobreProyectilPrincesa(proyectil);
            }
            isla.dibujar(entorno, mundo);
        }
    }

    private void colisionesJefe() {
        Jefe jefe = mundo.jefe();
        if (jefe == null) {
            return;
        }

        Princesa princesa = mundo.princesa();
        if (Rectangulos.enColision(jefe.rectangulo(), princesa.rectangulo())) {
            princesa.recibirMensaje("morir");
        }
        jefe.mover();
        jefe.dibujar(entorno, mundo);
    }

    private void colisionesProyectilPrincesa() {
        ProyectilPrincesa proyectil = mundo.proyectilPrincesa();
        if (proyectil == null) {
            return;
        }
        Jefe jefe = mundo.jefe();
        if (jefe != null && Rectangulos.enColision(jefe.rectangulo(), proyectil.rectangulo())) {
            mundo.establecerProyectilPrincesa(null);
            jefe.recibirMensaje("una vida menos");
            // El dragon emite sonido cuando un
            //proyectil lo colisiona
            try {
                Herramientas.play("recursos/dragon.wav");
            }
            catch (Exception error){
            System.out.println ("No se puede reproducir sonido de dragon");
            }
        }
        proyectil = mundo.proyectilPrincesa();
        if(proyectil == null){
            return;
        }
        Enemigo[] enemigosEnColision = mundo.enemigosEnColision(proyectil.rectangulo());
        if(enemigosEnColision.length > 0){
            mundo.establecerProyectilPrincesa(null);
        }
        for (int i = 0; i < enemigosEnColision.length; i++) {
            enemigosEnColision[i].recibirMensaje("morir");
        }
        proyectil = mundo.proyectilPrincesa();
        if(proyectil == null){
            return;
        }
        proyectil.mover();
        proyectil.dibujar(entorno, mundo);
    }

    private void colisionesPrincesa() {
        Princesa princesa = mundo.princesa();
        if (princesa.vidas() <= 0) {
            derrota = true;
        }
        if (princesa.y() > mundo.limitesMundo().alto()*2.5) {
            //se agrega sonido cuando la princesa cae al vacio y pierde una vida
            try{
                Herramientas.play ("recursos/dragon2.wav");
            }catch (Exception error){
                System.out.println("Princesa cae al vacio no funciona el sonido");
            }
            princesaCaeAlVacio();
        }
        Enemigo[] enemigosEnColision = mundo.enemigosEnColision(princesa.rectangulo());
        for (int i = 0; i < enemigosEnColision.length; i++) {
            enemigosEnColision[i].recibirMensaje("morir");
            //la princesa emite sonido cuando le sacan vidas
            try{
                Herramientas.play ("recursos/PrincesaPierdeVidas.wav");
            } catch (Exception error){
                System.out.println ("Sonido de pierde vidas con enemigo");
            }
            princesa.recibirMensaje("una vida menos");
        }
        princesa.mover(entorno);
        princesa.dibujar(entorno);
    }

    private void princesaCaeAlVacio(){
        Princesa princesa = mundo.princesa();
        princesa.recibirMensaje("una vida menos");
        if(princesa.vidas() <= 0){
            derrota = true;
            return;
        }
        IteradorIslas iteradorIslas = mundo.iteradorIslas();
        Isla islaMasCercana = iteradorIslas.proximo();
        double deltaX = Math.abs(islaMasCercana.x() - princesa.x());
        while(iteradorIslas.tieneOtro()){
            Isla isla = iteradorIslas.proximo();
            double nuevoDeltaX = Math.abs(isla.x() - princesa.x());
            if(nuevoDeltaX <= deltaX){
                deltaX = nuevoDeltaX;
                islaMasCercana = isla;
            }
        }
        princesa.trasladar(islaMasCercana.x(), islaMasCercana.y() - Isla.altoIsla / 2.0 - princesa.alto() / 2.0);
    }

    private void colisionesCastillo() {
        Princesa princesa = mundo.princesa();
        Castillo castillo = mundo.castillo();
        if (Rectangulos.enColision(princesa.rectangulo(), castillo.rectangulo())) {
            victoria = true;
        }
        mundo.castillo().dibujar(entorno, mundo);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

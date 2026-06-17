package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.awt.*;
import java.util.Objects;
import entorno.Herramientas ;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private final Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private final GeneradorId generadorId;
    private final Mundo mundo;

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
        final var anchoPantalla = entorno.ancho();
        final var altoPantalla = entorno.alto();
        final var anchoMundo = anchoPantalla * Mundo.proporcionAnchoMundo;
        final var altoMundo = altoPantalla * Mundo.proporcionAltoMundo;
        final var limitesPantalla = new Rectangulo(anchoPantalla / 2.0, altoPantalla / 2.0, anchoPantalla, altoPantalla);
        final var limitesMundo = new Rectangulo(anchoMundo / 2.0, altoMundo / 2.0, anchoMundo, altoMundo);
        final var imagenPrincesa = Imagenes.cargarYEscalar("princesa.png", Princesa.anchoPrincesa, Princesa.altoPrincesa);
        final var imagenCorazon = Imagenes.cargarYEscalar("corazon.png", Princesa.ladoCorazon, Princesa.ladoCorazon);
        final var princesa = new Princesa(0.0, 0.0, Princesa.anchoPrincesa, Princesa.altoPrincesa, imagenPrincesa, imagenCorazon);
        final var imagenJefeHaciaDerecha = Imagenes.cargarYEscalar("jefe_hacia_derecha.png", Jefe.anchoJefe, Jefe.altoJefe);
        final var imagenJefeHaciaIzquierda = Imagenes.cargarYEscalar("jefe_hacia_izquierda.png", Jefe.anchoJefe, Jefe.altoJefe);
        final var jefe = new Jefe( 0, 0, Jefe.anchoJefe, Jefe.altoJefe, imagenJefeHaciaDerecha, imagenJefeHaciaIzquierda);
        final var imagenFondo = Imagenes.cargarYEscalar("fondo.png", anchoPantalla, altoPantalla);
        final var fondo = new Fondo(anchoPantalla / 2.0, altoPantalla / 2.0, imagenFondo);
        final var imagenCastillo = Imagenes.cargarYEscalar("castillo.png", Isla.anchoMinimo, Isla.anchoMinimo);
        final var castillo = new Castillo(0.0, 0.0, Isla.anchoMinimo, Isla.anchoMinimo, imagenCastillo);
        mundo = new Mundo(limitesPantalla, limitesMundo, princesa, jefe, fondo, castillo);

        var cantidadIslasBajas = Isla.proporcionIslasBajas * mundo.limitesMundo().area();
        var cantidadIslasAltas = Isla.proporcionIslasAltas * mundo.limitesMundo().area();
        var contador = 0;
        while (contador < cantidadIslasBajas) {
            var isla = Isla.nuevaNivelBajo(mundo);
            if (isla == null) {
                break;
            }
            mundo.agregarIsla(isla);
            contador++;
        }
        contador = 0;
        var xMin = mundo.limitesMundo().ancho();
        var xMax = 0.0;
        var yMin = mundo.limitesMundo().alto();
        Isla primeraIsla = null;
        Isla anteUltimaIsla = null;
        Isla ultimaIsla = null;
        while (contador < cantidadIslasAltas) {
            var isla = Isla.nuevaNivelAlto(mundo);

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
            var x = mundo.princesa().x();
            var y = mundo.princesa().y();
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
            var x = mundo.princesa().x();
            var y = mundo.princesa().y();
            mundo.princesa().trasladar(x, y);
            entorno.cambiarFont("Arial", 72, Color.RED);
            entorno.escribirTexto("Perdiste", entorno.ancho() / 2.0 - 150, entorno.alto() / 2.0);
            return;
        }
        if (mundo.faltanEnemigos()) {
            var opcion = Aleatorio.enteroRandom(0, 2);
            Enemigo enemigo;
            switch (opcion) {
                case 0:
                    enemigo = Enemigos.nuevoEnemigoDerecha(generadorId, mundo, entorno);
                    break;
                case 1:
                    enemigo = Enemigos.nuevoEnemigoIzquierda(generadorId, mundo, entorno);
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
        var iterador = mundo.iteradorEnemigos();
        while (iterador.tieneOtro()) {
            var enemigo = iterador.proximo();
            enemigo.mover();
            enemigo.dibujar(entorno, mundo);
        }
    }

    private void colisionesIslas() {
        var iterador = mundo.iteradorIslas();
        var princesa = mundo.princesa();
        var proyectil = mundo.proyectilPrincesa();
        while (iterador.tieneOtro()) {
            var isla = iterador.proximo();
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
        var jefe = mundo.jefe();
        if (jefe == null) {
            return;
        }

        var princesa = mundo.princesa();
        if (Rectangulos.enColision(jefe.rectangulo(), princesa.rectangulo())) {
            princesa.recibirMensaje("morir");
        }
        jefe.mover();
        jefe.dibujar(entorno, mundo);
    }

    private void colisionesProyectilPrincesa() {
        var proyectil = mundo.proyectilPrincesa();
        if (proyectil == null) {
            return;
        }
        var jefe = mundo.jefe();
        if (jefe != null && Rectangulos.enColision(jefe.rectangulo(), proyectil.rectangulo())) {
            mundo.establecerProyectilPrincesa(null);
            jefe.recibirMensaje("una vida menos");
            // El dragon emite sonido cuando un
            //proyectil lo colisiona
            try {
                Herramientas.play("sonido/dragon.wav");
            }
            catch (Exception error){
            System.out.println ("No se puede reproducir sonido de dragon");
            }
        }
        proyectil = mundo.proyectilPrincesa();
        if(proyectil == null){
            return;
        }
        var enemigosEnColision = mundo.enemigosEnColision(proyectil.rectangulo());
        if(enemigosEnColision.length > 0){
            mundo.establecerProyectilPrincesa(null);
        }
        for (var i = 0; i < enemigosEnColision.length; i++) {
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
        var princesa = mundo.princesa();
        if (princesa.vidas() <= 0) {
            derrota = true;
        }
        if (princesa.y() > mundo.limitesMundo().alto()*2.5) {
            //se agrega sonido cuando la princesa cae al vacio y pierde una vida
            try{
                Herramientas.play ("sonido/dragon2.wav");
            }catch (Exception error){
                System.out.println("Princesa cae al vacio no funciona el sonido");
            }
            princesaCaeAlVacio();
        }
        var enemigosEnColision = mundo.enemigosEnColision(princesa.rectangulo());
        for (int i = 0; i < enemigosEnColision.length; i++) {
            enemigosEnColision[i].recibirMensaje("morir");
            //la princesa emite sonido cuando le sacan vidas
            try{
                Herramientas.play ("sonido/PrincesaPierdeVidas.wav" );
            } catch (Exception error){
                System.out.println ("Sonido de pierde vidas con enemigo");
            }
            princesa.recibirMensaje("una vida menos");
        }
        princesa.mover(entorno);
        princesa.dibujar(entorno);
    }

    private void princesaCaeAlVacio(){
        var princesa = mundo.princesa();
        princesa.recibirMensaje("una vida menos");
        if(princesa.vidas() <= 0){
            derrota = true;
            return;
        }
        var iteradorIslas = mundo.iteradorIslas();
        var islaMasCercana = iteradorIslas.proximo();
        var deltaX = Math.abs(islaMasCercana.x() - princesa.x());
        while(iteradorIslas.tieneOtro()){
            var isla = iteradorIslas.proximo();
            var nuevoDeltaX = Math.abs(isla.x() - princesa.x());
            if(nuevoDeltaX <= deltaX){
                deltaX = nuevoDeltaX;
                islaMasCercana = isla;
            }
        }
        princesa.trasladar(islaMasCercana.x(), islaMasCercana.y() - Isla.altoIsla / 2.0 - princesa.alto() / 2.0);
    }

    private void colisionesCastillo() {
        var princesa = mundo.princesa();
        var castillo = mundo.castillo();
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

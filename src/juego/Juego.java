package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.awt.*;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private final Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private final GeneradorId generadorId;
    private final Mundo mundo;
    private final FondoJuego fondo;

    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...

        generadorId = new GeneradorId();
        fondo = new FondoJuego (400,300);
        var princesa = new Princesa(generadorId, entorno);
        var jefe = new Jefe(generadorId, entorno, 600, 700);
        mundo = new Mundo(entorno, princesa, jefe);

        var cantidadIslasBajas = Islas.proporcionIslasBajas * mundo.limitesMundo().area();
        var cantidadIslasAltas = Islas.proporcionIslasAltas * mundo.limitesMundo().area();
        var contador = 0;
        while (contador < cantidadIslasBajas) {
            var isla = Islas.nuevaNivelBajo(generadorId, mundo);
            mundo.agregarIsla(isla);
            contador++;
        }
        contador = 0;
        while (contador < cantidadIslasAltas) {
            var isla = Islas.nuevaNivelAlto(generadorId, mundo);
            mundo.agregarIsla(isla);
            contador++;
        }

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
        fondo.dibujar(entorno);
        if (mundo.faltanEnemigos()) {
            var opcion = Aleatorio.enteroRandom(0,2);
            Enemigo enemigo;
            switch (opcion){
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
            mundo.princesa().disparar(mundo, entorno, generadorId);
        }

        // ver colisiones de los enemigos
        colisionesEnemigos();

        // ver colisiones de las islas
        colisionesIslas();

        // ver colisiones del jefe
        colisionesJefe();

        // ver colisiones de proyectil
        colisionesProyectilPrincesa();

        // ver colisiones de la princesa
        colisionesPrincesa();
    }

    private void colisionesEnemigos() {
        var iterador = mundo.iteradorEnemigos();
        var princesa = mundo.princesa();
        while (iterador.tieneOtro()) {
            var enemigo = iterador.proximo();
            if (Rectangulos.enColision(enemigo.rectangulo(), princesa.rectangulo())) {
                princesa.recibirMensaje("una vida menos");
            }
            enemigo.mover(entorno);
            enemigo.dibujar(entorno, mundo);
        }
    }

    private void colisionesIslas() {
        var iterador = mundo.iteradorIslas();
        var princesa = mundo.princesa();
        var jefe = mundo.jefe();
        var proyectil = mundo.proyectilPrincesa();
        while (iterador.tieneOtro()) {
            var isla = iterador.proximo();
            if (Rectangulos.enColision(isla.rectangulo(), princesa.rectangulo())) {
                isla.actuarSobrePrincesa(princesa);
            }
            if (jefe != null && Rectangulos.enColision(isla.rectangulo(), jefe.rectangulo())) {
                isla.actuarSobreJefe(jefe);
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
            princesa.recibirMensaje("una vida menos");
        }
        jefe.mover(entorno);
        jefe.dibujar(entorno);
    }

    private void colisionesProyectilPrincesa() {
        var proyectil = mundo.proyectilPrincesa();
        if (proyectil == null) {
            return;
        }
        var jefe = mundo.jefe();
        if (jefe != null && Rectangulos.enColision(jefe.rectangulo(), proyectil.rectangulo())) {
            jefe.recibirMensaje("una vida menos");
        }
        var enemigosEnColision = mundo.enemigosEnColision(proyectil.rectangulo());
        for (var i = 0; i < enemigosEnColision.length; i++) {
            enemigosEnColision[i].recibirMensaje("morir");
        }
        proyectil.mover(entorno);
        proyectil.dibujar(entorno, mundo);
    }

    private void colisionesPrincesa() {
        var princesa = mundo.princesa();
        var enemigosEnColision = mundo.enemigosEnColision(princesa.rectangulo());
        for (int i = 0; i < enemigosEnColision.length; i++) {
            enemigosEnColision[i].recibirMensaje("morir");
        }
        princesa.mover(entorno);
        princesa.dibujar(entorno);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

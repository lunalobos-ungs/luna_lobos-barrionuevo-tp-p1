package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.util.Iterator;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private final Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private final GeneradorId generadorId;
    private final Mundo mundo;
    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...
        generadorId = new GeneradorId();
        var princesa = new Princesa(generadorId, entorno);
        var jefe = new Jefe(generadorId, entorno, 600, 700);
        mundo = new Mundo(entorno, princesa, jefe);

        var cantidadIslasBajas = 3;
        var cantidadIslasAltas = 2;
        var contador = 0;
        while(contador < cantidadIslasBajas){
            var isla = Islas.nuevaNivelBajo(generadorId, mundo);
            mundo.agregarIsla(isla);
            contador++;
        }
        contador = 0;
        while(contador < cantidadIslasAltas){
            var isla = Islas.nuevaNivelAlto(generadorId, mundo);
            mundo.agregarIsla(isla);
            contador++;
        }

        /*
        FabricaEnemigos fabricaEne = new FabricaEnemigos ();
        Enemigo enemigo1 = fabricaEne.enemigo1 (generadorId, entorno, isla1, isla2,  isla3, isla4 );
        Enemigo enemigo2 = fabricaEne.enemigo2 (generadorId, entorno, isla1,  isla2, isla3, isla4 );
        Enemigo enemigo3 = fabricaEne.enemigo3 (generadorId, entorno, isla1,  isla2, isla3, isla4 , enemigo1);
        Enemigo enemigo4 = fabricaEne.enemigo4 (generadorId, entorno, isla1, isla2, isla3, isla4, enemigo2);
        
        FabricaEnemigos fabricaExtra = new FabricaEnemigos ();
        EnemigoExtra enemigoExtra1 = fabricaExtra.enemigoExtra (generadorId, entorno ) ;

        contexto.agregar(enemigo1);
        contexto.agregar(enemigo2);
        contexto.agregar(enemigo3);
        contexto.agregar(enemigo4);
        
        contexto.agregar (enemigoExtra1);
        */
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
        mundo.purgar(); // eliminamos a aquellos que se salen del mundo

        if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && mundo.proyectilPrincesa() == null){
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

    private void colisionesEnemigos(){
        var iterador = mundo.iteradorEnemigos();
        var princesa = mundo.princesa();
        while(iterador.tieneOtro()){
            var enemigo = iterador.proximo();
            if(Rectangulos.enColision(enemigo.rectangulo(), princesa.rectangulo())){
                princesa.recibirMensaje("una vida menos");
            }
            enemigo.mover(entorno);
            enemigo.dibujar(entorno);
        }
    }

    private void colisionesIslas(){
        var iterador = mundo.iteradorIslas();
        var princesa = mundo.princesa();
        var jefe = mundo.jefe();
        var proyectil = mundo.proyectilPrincesa();
        while(iterador.tieneOtro()){
            var isla = iterador.proximo();
            if(Rectangulos.enColision(isla.rectangulo(), princesa.rectangulo())){
                System.out.println("colision con isla");
                isla.actuarSobrePrincesa(princesa);
            }
            if(jefe != null && Rectangulos.enColision(isla.rectangulo(), jefe.rectangulo())){
                isla.actuarSobreJefe(jefe);
            }
            if(proyectil != null && Rectangulos.enColision(isla.rectangulo(), proyectil.rectangulo())){
                isla.actuarSobreProyectilPrincesa(proyectil);
            }
            isla.dibujar(entorno);
        }

    }

    private void colisionesJefe(){
        var jefe = mundo.jefe();
        if(jefe == null){
            return;
        }
        var princesa = mundo.princesa();
        if(Rectangulos.enColision(jefe.rectangulo(), princesa.rectangulo())){
            princesa.recibirMensaje("una vida menos");
        }
        jefe.mover(entorno);
        jefe.dibujar(entorno);
    }

    private void colisionesProyectilPrincesa(){
        var proyectil = mundo.proyectilPrincesa();
        if(proyectil == null){
            return;
        }
        var jefe = mundo.jefe();
        if(jefe != null && Rectangulos.enColision(jefe.rectangulo(), proyectil.rectangulo())){
            jefe.recibirMensaje("una vida menos");
        }
        var enemigosEnColision = mundo.enemigosEnColision(proyectil.rectangulo());
        for(var i = 0; i < enemigosEnColision.length; i++){
            enemigosEnColision[i].recibirMensaje("morir");
        }
        proyectil.mover(entorno);
        proyectil.dibujar(entorno);
    }

    private void colisionesPrincesa(){
        var princesa = mundo.princesa();
        var enemigosEnColision = mundo.enemigosEnColision(princesa.rectangulo());
        for (int i = 0; i < enemigosEnColision.length; i++){
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

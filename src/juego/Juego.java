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
    private Princesa princesa;
    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...
        generadorId = new GeneradorId();
        princesa = new Princesa(generadorId, entorno);
        //Elemento tierraFirme = new TierraFirme(generadorId, entorno);
        mundo = new Mundo(entorno, princesa);

        Islas fabrica = new Islas();
        var cantidadIslasBajas = 4;
        var cantidadIslasAltas = 2;
        var contador = 0;
        while(contador < cantidadIslasBajas){
            var isla = fabrica.nuevaNivelBajo(generadorId, mundo);
            mundo.agregar(isla);
            contador++;
        }
        contador = 0;
        while(contador < cantidadIslasAltas){
            var isla = fabrica.nuevaNivelAlto(generadorId, mundo);
            mundo.agregar(isla);
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

        if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)){
            princesa.disparar(mundo, entorno, generadorId);
        }

        Iterator<Elemento> iterador = mundo.iterador();
        while(iterador.hasNext()){
            Elemento elemento = iterador.next();
            Elemento[] enColisionCon = mundo.enColisionCon(elemento);
            for (int i = 0; i < enColisionCon.length; i++){
                elemento.actuar(enColisionCon[i]);
            }
            elemento.mover(entorno);
            elemento.dibujar(entorno);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

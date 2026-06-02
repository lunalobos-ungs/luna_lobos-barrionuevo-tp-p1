package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.awt.*;
import java.util.Iterator;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private final Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private final GeneradorId generadorId;
    private final Contexto contexto;
    private Princesa princesa;
    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 800, 600);

        // Inicializar lo que haga falta para el juego
        // ...
        generadorId = new GeneradorId();
        princesa = new Princesa(generadorId, entorno);
        Elemento tierraFirme = new TierraFirme(generadorId, entorno);
        contexto = new Mundo(entorno, princesa, tierraFirme);
        
        /*
         * Dejo fijo valor : alto .
         * X, Y , ancho , de la isla, debe ser random  
         */
        FabricaIsla fabrica = new FabricaIsla ();
        Isla isla1 = fabrica.isla1 (generadorId, entorno);
        Isla isla2 = fabrica.isla2 (generadorId, entorno,  isla1);
        Isla isla3 = fabrica.isla3 (generadorId, entorno,  isla1 ) ;
        Isla isla4 = fabrica.isla4 ( generadorId, entorno, princesa) ;
        contexto.agregar(isla1);
        contexto.agregar(isla2);
        contexto.agregar(isla3);
        contexto.agregar(isla4);
        
        /*
         * 
         */
        FabricaEnemigos fabricaEne = new FabricaEnemigos ();
        Enemigo enemigo1 = fabricaEne.enemigo1 (generadorId, entorno, isla1, isla2,  isla3, isla4 );
        Enemigo enemigo2 = fabricaEne.enemigo2 (generadorId, entorno, isla1,  isla2, isla3, isla4 );
        Enemigo enemigo3 = fabricaEne.enemigo3 (generadorId, entorno, isla1,  isla2, isla3, isla4 , enemigo1);
        Enemigo enemigo4 = fabricaEne.enemigo4 (generadorId, entorno, isla1, isla2, isla3, isla4, enemigo2);
        
        contexto.agregar(enemigo1);
        contexto.agregar(enemigo2);
        contexto.agregar(enemigo3);
        contexto.agregar(enemigo4);
        

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
        entorno.dibujarRectangulo(entorno.ancho()/2.0, entorno.alto()/2.0, entorno.ancho(), entorno.alto(), 0, Color.WHITE);
        contexto.purgar();
        if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)){
            princesa.disparar(contexto, entorno, generadorId);
        }
        Iterator<Elemento> iterador = contexto.iterador();
        while(iterador.hasNext()){
            Elemento elemento = iterador.next();
            Elemento[] enColisionCon = contexto.enColisionCon(elemento);
            elemento.dibujar(entorno);
            if(enColisionCon.length > 0){
                for(int i = 0; i < enColisionCon.length; i++){
                    elemento.actuar(enColisionCon[i]);
                }
            }
            elemento.mover(entorno);
        }

    }


    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

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
         * Dejo fijo valores : alto .
         * X, Y , ancho de la isla, debe ser random  
         */
        FabricaIsla fabrica = new FabricaIsla ();
        Elemento isla1 = fabrica.isla1 (generadorId, entorno);
        Elemento isla2 = fabrica.isla2 (generadorId, entorno, (Isla) isla1);
        Elemento isla3 = fabrica.isla3 (generadorId, entorno,  (Isla) isla1 ) ;
        Elemento isla4 = fabrica.isla4 (generadorId, entorno, princesa) ;
        // islas creadas como modo de prueba 
        //Elemento isla1 = new Isla (generadorId, entorno, 600 , 150, 200) ;
        //Elemento isla2= new Isla (generadorId, entorno,  500 , 350, 200);
        //Elemento isla3= new Isla (generadorId, entorno, 100 , 350, 200 ) ; 
        
        contexto.agregar(isla1);
        contexto.agregar(isla2);
        contexto.agregar(isla3);
        contexto.agregar(isla4);
        

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

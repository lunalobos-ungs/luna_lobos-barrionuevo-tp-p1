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
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 1366, 768);

        // Inicializar lo que haga falta para el juego
        // ...
        generadorId = new GeneradorId();
        princesa = new Princesa(generadorId, entorno);
        Elemento tierraFirme = new TierraFirme(generadorId, entorno);
        contexto = new Mundo(entorno, princesa, tierraFirme);

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

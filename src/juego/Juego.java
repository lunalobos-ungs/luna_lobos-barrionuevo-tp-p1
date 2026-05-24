package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

import java.awt.*;
import java.util.Iterator;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...
    private GeneradorId generadorId;
    private Contexto contexto;
    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Super Elizabeth Sis", 1366, 768);

        // Inicializar lo que haga falta para el juego
        // ...
        generadorId = new GeneradorId();
        Elemento princesa = new Princesa(generadorId, entorno);
        Elemento tierraFirme = new TierraFirme(generadorId, entorno);
        contexto = new Mundo(princesa, tierraFirme);

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
        Iterator<Elemento> iterador = contexto.iterador();
        while(iterador.hasNext()){
            Elemento elemento = iterador.next();
            Elemento enColisiconCon = contexto.enColisionCon(elemento);
            if(enColisiconCon != null){
                elemento.actuar(enColisiconCon);
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

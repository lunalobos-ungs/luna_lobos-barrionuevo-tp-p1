package juego;

import java.util.Iterator;

/**
 * La idea de esta interfaz es que su implementación condense la
 * información de todos los elementos renderizados en pantalla
 * con el fin de poder ser consultada por cada elemento. Se considera
 * que todos los elementos son rectángulos.
 *
 * @author Miguel Angel Luna Lobos
 */
public interface Contexto {

    /**
     * Este método detecta los elementos con los cuales colisiona el
     * elemento argumento, puede devolver un array vacío si no hay
     * colisiones.
     *
     * @param elemento el elemento del cual queremos saber si está en colisión.
     * @return los elementos con los cuales colisiona el argumento, o un array
     * vacío si no hay colisiones.
     */
    Elemento[] enColisionCon(Elemento elemento);

    /**
     * Agrega un elemento al contexto, el mismo debe renderizarse en
     * pantalla de manera independiente a las implementaciones de esta interfaz.
     *
     * @param elemento el elemento a agregar
     */
    void agregar(Elemento elemento);

    /**
     * Elimina un elemento al contexto, el mismo debe dejar de renderizarse
     * en pantalla de manera independiente a las implementaciones de esta interfaz.
     *
     * @param elemento el elemento a quitar
     */
    void quitar(Elemento elemento);

    /**
     * Devuelve un iterador sobre todos los elementos actualmente en el contexto.
     *
     * @return un iterador de {@link Elemento}
     */
    Iterator<Elemento> iterador();

    /**
     * Elimina del contexto todos los elementos que se encuentran fuera de los
     * límites del mundo.
     */
    void purgar();

    /**
     * Devuelve los límites del mundo.
     * @return un {@link Rectangulo} que representa los límites del mundo.
     */
    Rectangulo limitesMundo();
}

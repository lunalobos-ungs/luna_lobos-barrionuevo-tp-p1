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
     * Este método detecta el elemento con el cual colisiona el elemento argumento, puede devolver null.
     *
     * @param elemento el elemento del cual queremos saber si está en colisión.
     * @return el elemento con el cual colisiona el argumento o null si no está en colisión.
     */
    Elemento enColisionCon(Elemento elemento);

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

    default Iterator<Elemento> iterador() {
        throw new UnsupportedOperationException("no implementado");
    }
}

package juego;


import entorno.Entorno;

import java.util.Iterator;
import java.util.Objects;

/**
 * Implementación de {@link Contexto} que gestiona todos los elementos activos del juego.
 * Mantiene un arreglo ordenado por ID y provee detección de colisiones rectangulares.
 *
 * @author Miguel Angel Luna Lobos
 */
public class Mundo implements Contexto {

    private static boolean enColision(Rectangulo r1, Rectangulo r2) {
        final double x1 = r1.x();
        final double x2 = r2.x();
        final double y1 = r1.y();
        final double y2 = r2.y();
        final double bordeIzquierdo1 = r1.bordeIzquierdo();
        final double bordeDerecho1 = r1.bordeDerecho();
        final double bordeSuperior1 = r1.bordeSuperior();
        final double bordeInferior1 = r1.bordeInferior();
        final double bordeIzquierdo2 = r2.bordeIzquierdo();
        final double bordeDerecho2 = r2.bordeDerecho();
        final double bordeSuperior2 = r2.bordeSuperior();
        final double bordeInferior2 = r2.bordeInferior();
        return ((bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2) || (bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2))
                && ((bordeSuperior1 <= bordeInferior2 && y1 >= y2) || (bordeInferior1 >= bordeSuperior2 && y1 <= y2));
    }

    /**
     * Determina la dirección desde la que {@code r1} llega a colisionar con {@code r2}.
     *
     * @param r1 el rectángulo cuya dirección de llegada se determina
     * @param r2 el rectángulo impactado
     * @return {@code "desde arriba"}, {@code "desde abajo"}, {@code "desde la izquierda"}
     *         o {@code "desde la derecha"}
     * @throws UnsupportedOperationException si los rectángulos no están en colisión
     */
    public static String tipoDeColision(Rectangulo r1, Rectangulo r2) {
        final double x1 = r1.x();
        final double x2 = r2.x();
        final double y1 = r1.y();
        final double y2 = r2.y();
        final double bordeIzquierdo1 = r1.bordeIzquierdo();
        final double bordeDerecho1 = r1.bordeDerecho();
        final double bordeSuperior1 = r1.bordeSuperior();
        final double bordeInferior1 = r1.bordeInferior();
        final double bordeIzquierdo2 = r2.bordeIzquierdo();
        final double bordeDerecho2 = r2.bordeDerecho();
        final double bordeSuperior2 = r2.bordeSuperior();
        final double bordeInferior2 = r2.bordeInferior();
        if (bordeInferior1 >= bordeSuperior2 && y1 <= y2) {
            return "desde arriba";
        }
        if (bordeSuperior1 <= bordeInferior2 && y1 >= y2) {
            return "desde abajo";
        }
        if (bordeDerecho1 >= bordeIzquierdo2 && x1 <= x2) {
            return "desde la izquierda";
        }
        if (bordeIzquierdo1 <= bordeDerecho2 && x1 >= x2) {
            return "desde la derecha";
        }
        throw new UnsupportedOperationException("no hay colisión");
    }

    /**
     * Puede haber nulos solo después del índice largo - 1.
     */
    private Elemento[] elementos;
    private int largo;
    private final Rectangulo limitesMundo;

    /**
     * Crea un mundo vacío con los límites del entorno.
     *
     * @param entorno el entorno del juego
     */
    public Mundo(Entorno entorno) {
        double ancho = entorno.ancho();
        double alto = entorno.alto();
        elementos = new Elemento[10];
        largo = 0;
        limitesMundo = new RectanguloSimple(ancho / 2.0, alto / 2.0, ancho, alto);
    }

    /**
     * Crea un mundo con los elementos iniciales provistos.
     *
     * @param entorno   el entorno del juego
     * @param elementos los elementos iniciales; ninguno puede ser nulo
     * @throws NullPointerException si alguno de los elementos es nulo
     */
    public Mundo(Entorno entorno, Elemento... elementos) {
        double ancho = entorno.ancho();
        double alto = entorno.alto();
        limitesMundo = new RectanguloSimple(ancho / 2.0, alto / 2.0, ancho, alto);
        this.elementos = new Elemento[Math.max(elementos.length, 10)];
        for (int i = 0; i < elementos.length; i++) {
            Objects.requireNonNull(elementos[i], "Los elementos provistos en este constructor no pueden ser nulos");
            this.elementos[i] = elementos[i];
        }
        largo = elementos.length;
    }


    @Override
    public Elemento[] enColisionCon(Elemento elemento) {
        final Elemento[] almacenador = new Elemento[largo];
        int contador = 0;
        for (int i = 0; i < largo; i++) {
            Elemento elemento_ = elementos[i];
            if (elemento.id() != elemento_.id() && enColision(elemento, elemento_)) {
                almacenador[contador++] = elemento_;
            }
        }
        final Elemento[] salida = new Elemento[contador];
        System.arraycopy(almacenador, 0, salida, 0, contador);
        return salida;
    }

    @Override
    public void agregar(Elemento elemento) {
        if (largo == elementos.length) {
            Elemento[] nuevoArray = new Elemento[elementos.length * 2];
            System.arraycopy(elementos, 0, nuevoArray, 0, elementos.length);
            elementos = nuevoArray;
        }
        elementos[largo++] = elemento;
    }

    @Override
    public void quitar(Elemento elemento) {
        int indice = indiceDe(elemento);
        if (indice >= 0) {
            for (int i = indice; i < largo - 1; i++) {
                elementos[i] = elementos[i + 1];
            }
            elementos[largo - 1] = null;
            largo--;
        }
    }

    /**
     * Busca el elemento en el array elementos por su id usando
     * el algoritmo de búsqueda binaria. Devuelve el índice del
     * elemento o un número negativo si no está presente en el
     * array elementos.
     *
     * @param elemento el elemento a buscar
     * @return el índice del elemento buscado o un número
     * negativo si no está presente
     */
    private int indiceDe(Elemento elemento) {
        int indiceMinimo = 0;
        int indiceMaximo = largo - 1;
        while (indiceMinimo <= indiceMaximo) {
            int indiceIntermedio = (indiceMinimo + indiceMaximo) / 2;
            Elemento elementoIntermedio = elementos[indiceIntermedio];
            int comparacion = compararElementos(elementoIntermedio, elemento);

            if (comparacion < 0) {
                indiceMinimo = indiceIntermedio + 1;
            } else if (comparacion > 0) {
                indiceMaximo = indiceIntermedio - 1;
            } else {
                return indiceIntermedio; // Encontrado (comparacion == 0)
            }
        }
        return -(indiceMinimo + 1);
    }

    /**
     * Compara dos elementos según su id. Devuelve -1 si el id
     * de elemento1 es menor que el id de elemento2.
     *
     * @param elemento1 el primer elemento
     * @param elemento2 el segundo elemento
     * @return un entero que puede -1, 0 o 1 según el resultado
     * de la comparación
     */
    private int compararElementos(Elemento elemento1, Elemento elemento2) {
        if (elemento1.id() < elemento2.id()) {
            return -1;
        } else if (elemento1.id() == elemento2.id()) {
            return 0; // esta condición nunca debería ocurrir porque los ids son únicos
        } else {
            return 1;
        }
    }

    @Override
    public Iterator<Elemento> iterador() {
        return new IteradorElementos(elementos, largo);
    }

    @Override
    
    /*
     * 1° elimina elementos que salgan de la pantalla
     * 2° eliminar enemigos que tienen como estado "muerto"
     * 3° eliminar enemigo cuando toda la princesa 
     */
    public void purgar() {
        int i = 0;
        while (i < largo) {
            Elemento elemento = elementos[i];
            if (!enColision(elemento, limitesMundo)|| elemento.debeEliminarse()) {
                quitar(elemento);
             }
            else {
                i++;
            }
        }
    }
}


/**
 * Iterador de instancia fija sobre un arreglo de elementos.
 * Toma una copia del arreglo al momento de la creación para evitar interferencias
 * con modificaciones realizadas durante la iteración.
 */
class IteradorElementos implements Iterator<Elemento> {
    int indice;
    int largo;
    Elemento[] elementos;

    /**
     * Crea un iterador sobre una copia del arreglo dado.
     *
     * @param elementos el arreglo de elementos a iterar
     * @param largo     la cantidad de elementos válidos en el arreglo
     */
    public IteradorElementos(Elemento[] elementos, int largo) {
        indice = 0;
        this.largo = largo;
        this.elementos = new Elemento[largo];
        System.arraycopy(elementos, 0, this.elementos, 0, largo);
    }

    @Override
    public boolean hasNext() {
        return indice < largo;
    }

    @Override
    public Elemento next() {
        return elementos[indice++];
    }
}
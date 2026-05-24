package juego;


import java.util.Iterator;

public class Mundo implements Contexto {

    /**
     * Puede haber nulos solo después del índice largo - 1.
     */
    private Elemento[] elementos;
    private int largo;

    public Mundo() {
        elementos = new Elemento[10];
        largo = 0;
    }

    public Mundo(Elemento... elementos) {
        this.elementos = new Elemento[Math.max(elementos.length, 10)];
        for (int i = 0; i < elementos.length; i++) {
            if (elementos[i] == null) {
                throw new NullPointerException("Los elementos provistos en este constructor no pueden ser nulos");
            }
            this.elementos[i] = elementos[i];
        }
        largo = elementos.length;
    }


    @Override
    public Elemento enColisionCon(Elemento elemento) {
        final double x = elemento.x();
        final double y = elemento.y();
        final double ancho = elemento.ancho();
        final double alto = elemento.alto();
        final double bordeIzquierdo = x - ancho / 2.0;
        final double bordeDerecho = x + ancho / 2.0;
        final double bordeArriba = y - alto / 2.0;
        final double bordeAbajo = y + alto / 2.0;
        for (int i = 0; i < largo; i++) {
            Elemento elemento_ = elementos[i];
            if(elemento.id() == elemento_.id()){
                continue;
            }
            final double x_ = elemento_.x();
            final double y_ = elemento_.y();
            final double ancho_ = elemento_.ancho();
            final double alto_ = elemento_.alto();
            final double bordeIzquierdo_ = x_ - ancho_ / 2.0;
            final double bordeDerecho_ = x_ + ancho_ / 2.0;
            final double bordeArriba_ = y_ - alto_ / 2.0;
            final double bordeAbajo_ = y_ + alto_ / 2.0;

            if (((bordeDerecho >= bordeIzquierdo_ && x <= x_) || (bordeIzquierdo <= bordeDerecho_ && x >= x_) )
                    && ((bordeArriba <= bordeAbajo_ && y >= y_) || (bordeAbajo >= bordeArriba_ && y <= y_))) {
                return elemento_;
            }
        }
        return null;
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
            for(int i = indice; i < largo - 1; i++){
                elementos[i] = elementos[i + 1];
            }
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
            if (comparacion < 0) indiceMinimo = indiceIntermedio + 1;
            else if (comparacion == 0) indiceMaximo = indiceIntermedio - 1;
            else return indiceIntermedio; // encontrado
        }
        return -(indiceMinimo + 1);  // no encontrado
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
}


class IteradorElementos implements Iterator<Elemento> {
    int indice;
    int largo;
    Elemento[] elementos;

    public IteradorElementos(Elemento[] elementos, int largo){
        indice = 0;
        this.largo = largo;
        this.elementos = elementos;
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
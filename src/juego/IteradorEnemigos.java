package juego;

/**
 * Clase creada para iterar sobre un array de enemigos.
 *
 * @author Miguel Angel Luna Lobos
 */
public class IteradorEnemigos {
    private Enemigo[] enemigos;

    private int largo;
    private int indice;

    /**
     * Crea un iterador de enemigos.
     * @param enemigos el array a recorrer
     * @param largo la cantidad de elementos del array a recorrer
     */
    public IteradorEnemigos(Enemigo[] enemigos, int largo) {
        this.enemigos = new Enemigo[largo];
        System.arraycopy(enemigos, 0, this.enemigos, 0, largo);
        this.largo = largo;
    }

    /**
     * Indica si aún tiene otro enemigo presente para devolver.
     * @return true si hay otro enemigo para devolver
     */
    public boolean tieneOtro() {
        return indice < largo;
    }

    /**
     * Devuelve el proximo enemigo.
     * @return el proximo enemigo
     */
    public Enemigo proximo(){
        return enemigos[indice++];
    }
}
package juego;

/**
 * Clase creada para iterar sobre un array de islas.
 */
public class IteradorIslas {
    private final Isla[] islas;
    private final int largo;
    private int indice;

    /**
     * Crea un iterador de islas.
     * @param islas el array de islas a recorrer
     * @param largo la cantidad de islas dentro del array a recorrer
     */
    public IteradorIslas(Isla[] islas, int largo) {
        this.islas = new Isla[largo];
        System.arraycopy(islas, 0, this.islas, 0, largo);
        this.largo = largo;
        indice = 0;
    }

    public boolean tieneOtro() {
        return indice < largo;
    }

    public Isla proximo(){
        return islas[indice++];
    }
}

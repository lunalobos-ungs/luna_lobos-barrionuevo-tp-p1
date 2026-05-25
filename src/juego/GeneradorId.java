package juego;

/**
 * Generador de identificadores únicos e incrementales para los elementos del juego.
 * Cada llamada a {@link #nuevoId()} devuelve un valor mayor al anterior, comenzando en cero.
 * Esto permite no romper a clase Mundo que require que los elementos estén ordenados por id
 * de menor a mayor.
 *
 * @author Miguel Angel Luna Lobos
 */
public class GeneradorId {
    private int actual;

    /**
     * Crea un nuevo generador cuyo primer identificador será cero.
     */
    public GeneradorId(){
        actual = 0;
    }

    /**
     * Genera y retorna el próximo identificador disponible.
     *
     * @return un entero único no negativo
     */
    public int nuevoId(){
        return actual++;
    }
}

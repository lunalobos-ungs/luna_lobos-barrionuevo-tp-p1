package juego;

public class GeneradorId {
    private int actual;

    public GeneradorId(){
        actual = 0;
    }

    public int nuevoId(){
        return actual++;
    }
}

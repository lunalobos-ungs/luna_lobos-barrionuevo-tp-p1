package juego;

public class IteradorIslas {
    private final Isla[] islas;
    private final int largo;
    private int indice;

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

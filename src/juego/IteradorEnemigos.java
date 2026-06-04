package juego;

public class IteradorEnemigos {
    private final Enemigo[] enemigos;

    private final int largo;
    private int indice;

    public IteradorEnemigos(Enemigo[] enemigos, int largo) {
        this.enemigos = new Enemigo[largo];
        System.arraycopy(enemigos, 0, this.enemigos, 0, largo);
        this.largo = largo;
    }

    public boolean tieneOtro() {
        return indice < largo;
    }

    public Enemigo proximo(){
        return enemigos[indice++];
    }
}
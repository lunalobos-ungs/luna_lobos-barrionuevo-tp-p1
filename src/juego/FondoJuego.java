package juego;

import  entorno.Entorno;
import java.awt.*;
//import java.time.Instant;

public class FondoJuego {
    private int x;
    private int y ;
    private final Image fondoOriginal;
    private Image fondo;

    FondoJuego (int x , int y){
        this.x = x;
        this.y = y;
        fondoOriginal=Imagenes.cargarImagen("fondo.png");
        fondo= fondoOriginal;

    }

    public void dibujar(Entorno entorno) {

        entorno.dibujarImagen(fondo, x, y, 0);
    }
}

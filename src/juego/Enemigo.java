package juego;

import java.awt.Image;
import java.time.Instant;

import entorno.Entorno;

/**
 * Representa los enemigos del juego;
 * Aparecen los enemigos por derecha e izquiera de forma aleatoria 
 * avanzan al lado opuesto al que aparecen
 * pueden superponerce entre si 
 * no pueden atravesar las islas
 * cuando llegan al borde de la pantalla se vuelven null
 * siempre debe tener una cantidad minima de enemigos vivos en pantalla
 * colision con princesa 
 * colision con proyectiles
 *
 */
public class Enemigo {
	private double x;
	private double y;
	private double ancho;
	private double alto;
    private final Image enemigoOriginal;
    private Image enemigo;
    private final int id;
    private final double velocidad;
    // los vos a implementar cuando el enemigo muera quiero que caiga con caida libre al suelo y luego desaparezca
    
    private double velocidadCaidaLibre;
    private double aceleracionGravitatoria;
    private double angulo;
    private Instant marcaTemporalDeCaida;
    private boolean vivo = true ;
    
    Enemigo (GeneradorId generadorId, Entorno entorno, double x , double y, double angulo  ){
        this.x = x;
        this.y = y ;
        ancho = 40.0;
        alto = 40.0;
        enemigoOriginal = Imagenes.cargarImagen("enemigo.png");
        enemigo = Imagenes.escalar(enemigoOriginal, ancho, alto);
        id = generadorId.nuevoId();
        velocidad = 1.0;
        velocidadCaidaLibre= 4.0;
        // luego el angulo se modifica entonces tendremos que hacer this.angulo
        this.angulo = angulo ;
        aceleracionGravitatoria = 10.0;
        //cayendo();
        }

    public int id() {
       return id;
    }


    public String tipo() {
        return "enemigo";
    }


    public double angulo() {
      return 0;
      // luego modificar y hacer return angulo;
    }


    public double x() {
       return x;
    }


    public double y() {
        return y;
    }


    public double ancho() {
        return ancho;
    }


    public double alto() {
        return alto;
    }

    public void establecerAncho(double ancho) {
    	this.ancho = ancho;
        enemigo = enemigoOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    public void establecerAlto(double alto) {
    	 this.alto = alto;
         enemigo = enemigoOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }
  
    // 0 represennta en angulo que luego tendra que ser cambiado para considerar colision y muerte en caida libre
    
    public void dibujar(Entorno entorno) {
    	 entorno.dibujarImagen(enemigo, x, y, 0);
    }

    public void mover(Entorno entorno) {
    	if (angulo == 0 ) {
    		x = x+ velocidad ;
    	}
    	else {
    		x= x - velocidad ;
    	}
    }


    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    public void establecerX(double x) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }


    public void establecerY(double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }


    public void recibirMensaje(String mensaje) {
        if (mensaje.equals("morir")) {
        	 vivo = false;
        }
    }
    
    public boolean debeEliminarse () {
    	return ! vivo ;
    }
    public Rectangulo rectangulo() {
    	return new Rectangulo (x,y,ancho,alto) ;
    }
}

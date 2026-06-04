package juego;

import java.awt.Image;

import entorno.Entorno;

public class Jefe{
	private double x;
	private double y;
	private double ancho;
	private double alto;
    private final Image enemigoExtraOriginal;
    private Image enemigoExtra;
    private final int id;
    private double angulo ;
    private final double velocidad;
    private boolean vivo=true;


	/*
	 *  Probando codigo de enemigo extra  
	 *  Podria aparecer por izquierda y derecha siempre para que la princesa tenga que saltar
	 *  entre isla e isla no se quede siempre sobre tierra firme  
	 */
    Jefe(GeneradorId generadorId, Entorno entorno, double x , double y  ){
        this.x = x;
        this.y = y ;
        ancho = 100.0;
        alto = 40.0;
        enemigoExtraOriginal = Imagenes.cargarImagen("jefeMalvado.png");
        enemigoExtra = Imagenes.escalar(enemigoExtraOriginal, ancho, alto);
        id = generadorId.nuevoId();
        velocidad = 1.0;

        
    }

    public int id() {
       return id;
    }


    public String tipo() {
        return "enemigoExtra";
    }


    public double angulo() {
      return 0;

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
        enemigoExtra = enemigoExtraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }


    public void establecerAlto(double alto) {
    	 this.alto = alto;
         enemigoExtra = enemigoExtraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }
  
    // Modificar el angulo para que la bola vaya y vuelva 
    
    public void dibujar(Entorno entorno) {
    	 entorno.dibujarImagen(enemigoExtra, x, y, 0);
    }


    public void mover(Entorno entorno) {
    	if (angulo==0) {
    		x= x+ velocidad;	
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

package juego;

import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class Jefe implements Elemento {
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
    private Rectangulo2 rectangulo ;

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
        enemigoExtraOriginal = Imagenes.cargarImagen("jefe.png");
        enemigoExtra = Imagenes.escalar(enemigoExtraOriginal, ancho, alto);
        id = generadorId.nuevoId();
        velocidad = 1.0;
        rectangulo= new Rectangulo2  (x,y, ancho, alto);
        
    }
    @Override
    public int id() {
       return id;
    }

    @Override
    public String tipo() {
        return "enemigoExtra";
    }

    @Override
    public double angulo() {
      return 0;

    }

    @Override
    public double x() {
       return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double ancho() {
        return ancho;
    }

    @Override
    public double alto() {
        return alto;
    }

    @Override
    public void establecerAncho(double ancho) {
    	this.ancho = ancho;
        enemigoExtra = enemigoExtraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }

    @Override
    public void establecerAlto(double alto) {
    	 this.alto = alto;
         enemigoExtra = enemigoExtraOriginal.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }
  
    // Modificar el angulo para que la bola vaya y vuelva 
    
    public void dibujar(Entorno entorno) {
    	 entorno.dibujarImagen(enemigoExtra, x, y, 0);
    }

    @Override
    public void mover(Entorno entorno) {
    	if (angulo==0) {
    		x= x+ velocidad;	
        }
    	else {
    		x= x - velocidad ;
    	}
    }

    @Override
    public void actuar(Elemento elemento) {
    	if (elemento.tipo().equals("proyectil")) {
    		this.recibirMensaje("morir");
    	}

    	return ;
        
    }

    @Override
    public void establecerAngulo(double angulo) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void establecerX(double x) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void establecerY(double y) {
        throw new UnsupportedOperationException("método aún sin implementar");
    }

    @Override
    public void recibirMensaje(String mensaje) {
        if (mensaje.equals("morir")) {
        	 vivo = false;
        }
    }
    
    public boolean debeEliminarse () {
    	return ! vivo ;
		
	}
    public Rectangulo2 rectangulo() {
    	return rectangulo ;
    }

}

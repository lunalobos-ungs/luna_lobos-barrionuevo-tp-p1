package juego;

import entorno.Entorno ;

public class FabricaIsla  {

	
	/*
	 * Retorna en ancho random para la isla 
	 * Estableci un ancho maximo  y minimo de lo que puede valor el random de la isla 
	 * dato: random() general valores aleatorios entre 0.0 y 0.9 nunca 1 
	 *  171 <= ancho <= 304
	 */
		public int generarAncho () {
			int minimoAncho = 60 ;
			int maximoAncho= 250 ;
			int rango= maximoAncho - minimoAncho ;
			return (int)((Math.random()*rango)+ minimoAncho);
		}
		
		/*Isla1 
		 * Determino un max para la posicion de x para que que la isla1 siempre este de la mitad de la 
		 * pantalla hacia la izquierda 
		 * posicion en Y es fija 
		 */
		
		public Isla isla1 (GeneradorId generadorId, Entorno entorno) {
			int ancho1= generarAncho();
			// genera que la 1° isla no se forme mas alla de la mitad de la pantalla 
			// para dejar espacio para la 2° isla 
			int maximoX = 400;
			int x1 = maximoX - ancho1;
		// Posicion en y de la isla1 debe estar fija 
			int y1= 400;
			return new Isla ( generadorId, entorno, x1, y1,ancho1 );
     }
		/*
		 * Isla2 tendra una posicion random luego de la isla1 y el espacio que considero entre 
		 * ambas islas
		 */

		public Isla isla2 (GeneradorId generadorId, Entorno entorno, Isla isla1 ) {
			int ancho2 = generarAncho();
			int espacioEntreIslas= 200;
			int x2= (int) isla1.x() + (int) isla1.ancho() + espacioEntreIslas ;
			int y2= 400 ;
			return new Isla (generadorId, entorno, x2, y2, ancho2);
			}
		
		/* 
		 * En isla3 no queremos que quede arriba de isla 1 para que la princesa no tenga
		 * que retroceder nunca , siempre se mueva hacia la derecha 
		 * tomamos como referencia donde termina isla1 lo logramos con un random extra
		 */
		public Isla isla3 (GeneradorId generadorId, Entorno entorno, Isla isla1 ) {
			int ancho3 = generarAncho();
			int distanciaExtra = (int) (Math.random()*70)+50 ;
			int x3= (int) isla1.x() +(int) isla1.ancho() + distanciaExtra ;
			int y3= 250 ;
			return new Isla (generadorId, entorno, x3, y3, ancho3);
			
		}
		
		/*
		 * Isla4 extra , la idea es mas visual no pretendemos que la princesa la pise
		 */
		
		public Isla isla4 (GeneradorId generadorId, Entorno entorno, Princesa princesa) {
			int ancho4 = generarAncho ();
			// definimos zona prohibida al ancho de la princesa para que la isla4 no se cree en la 
			// posicion donde cae la princesa
			int y4= 100;
			// un margen para que la isla no cree pegana a la princesa
			
			int inicioZonaProhibida =(int) princesa.x () ;
			int finZonaProhibida = (int) princesa.x() + (int) princesa.ancho() ;
			
			
			// solucionar  porq me da posiciones fuera de la pantalla y donde cae la princesa
			
			int maximoX= 800 - ancho4 ;
			int x4 =(int) (Math.random()*maximoX);
			if (x4 == 0 ) {
			   x4= ancho4 ;
			}
			if(x4 >=inicioZonaProhibida && x4 <= finZonaProhibida) {
				x4= finZonaProhibida + 50 ;	
			}
			
			return new Isla (generadorId, entorno, x4, y4, ancho4);
			
		}
		
	}



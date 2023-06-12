package laberinto.vector;

import java.util.Objects;

public class Posición {

	private int x;
	private int y;
	
	/**
	 * Crea una posición a partir de sus coordenadas en los ejes X e Y.
	 * @param x Coordenada X del punto.
	 * @param y Coordenada Y del punto.
	 */
	public Posición(int x,
            int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * Permite obtener la coordenada en el eje X de una posición.
	 * @return la coordenada en el eje X
	 */
	public int coordenadaX() {
		return this.x;
	}
	/**
	 * Permite obtener la coordenada en el eje Y de una posición.
	 * @return la coordenada en el eje Y
	 */
	public int coordenadaY() {
		return this.y;
	}
	
	/**
	 * Indica si otro objeto es igual a este. Para ser igual, tendrá que ser una Posición
	 * y tener las mismas coordenadas en el eje X y en el eje Y
	 * @param o El objeto a comparar con este.
	 * @return true si o representa la misma posicion que esta y false en caso contrario
	 */
	public boolean equals(java.lang.Object o) {
		boolean b=false;
		Posición nueva;
		if(o instanceof Posición) {
			nueva = (Posición) o;
			if (nueva.coordenadaX()==x && nueva.coordenadaY()==y) {
				b = true;
			}
		}
		return b;
	}
	/**
	 * Obtiene un nuevo punto en el plano cartesiano que es el resultado de mover este punto de acuerdo con el vector de dirección d.
	 * @param d Vector que indica el movimiento a aplicar
	 * @return el vector resultante de sumar a este, el vector d.
	 */
	public Posición mover(Dirección d) {
		Posición p;
		p = new  Posición (x+d.componenteX(),y+d.componenteY());
		return p;		
	}
	
	/**
	 * @return 
	 * 
	 */
	@Override 
	public int hashCode() {
		
		return Objects.hash(x,y);
		
	}
}

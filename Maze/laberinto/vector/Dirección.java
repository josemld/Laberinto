package laberinto.vector;
import java.util.Objects;
import java.util.Random;
public class Dirección {

	private int x;
	private int y;
	
	private Dirección (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * vector unitario (0,1)
	 */
	public static final Dirección ARRIBA = new Dirección (0,1);
	/**
	 * vector unitario (0,-1)
	 */
	public static final Dirección ABAJO = new Dirección (0,-1);
	/**
	 * vector unitario (1,0)
	 */
	public static final Dirección DERECHA = new Dirección (1,0);
	/**
	 * vector unitario (-1,0)
	 */
	public static final Dirección IZQUIERDA = new Dirección (-1,0);
	
	/**
	 * Genera una dirección aleatoria: DERECHA, IZQUIERDA, ARRIBA, o ABAJO
	 * @return Un vector unitario que indica una dirección
	 */
	public static Dirección aleatoria() {
		Random r = new Random();
		Dirección[] d = {ARRIBA,ABAJO,DERECHA,IZQUIERDA};
		return d[r.nextInt(4)];
	}
	
	/**
	 * Permite obtener la coponente X de un vector unitario
	 * @return la componente X.
	 */
	public int componenteX() {
		return this.x;
	}
	
	/**
	 * Permite obtener la coponente Y de un vector unitario
	 * @return la componente Y.
	 */
	public int componenteY() {
		return this.y;
	}
	
	/**
	 * A partir de la dirección actual, se obtiene la dirección correspondiente a girar 180 grados. 
	 * Por ejemplo, si la dirección actual es DERECHA, la dirección obtenida será IZQUIERDA.
	 * @return el vector unitario con la nueva dirección
	 */
	public Dirección darLaVuelta() {
		Dirección d= new Dirección (-x,-y);
		return d;
	}
	
	/**
	 * * A partir de la dirección actual, se obtiene la dirección correspondiente a girar 90 grados en sentido horario. 
	 * Por ejemplo, si la dirección actual es IZQUIERDA, la dirección obtenida será ARRIBA.
	 * @return  el vector unitario con la nueva dirección
	 */
	public Dirección girarDerecha() {
		Dirección d= new Dirección (y,-x);
		return d;
	}
	
	/**
	 * * A partir de la dirección actual, se obtiene la dirección correspondiente a girar 90 grados en sentido antihorario. 
	 * Por ejemplo, si la dirección actual es DERECHA, la dirección obtenida será ARRIBA.
	 * @return el vector unitario con la nueva dirección
	 */
	public Dirección girarIzquierda() {
		Dirección d= new Dirección (-y,x);
		return d;
	}
	
	/**
	 * Indica si otro objeto es igual a este. Para ser igual, tendrá que ser una Posición
	 * y tener las mismas coordenadas en el eje X y en el eje Y
	 * @param o El objeto a comparar con este.
	 * @return true si o representa la misma posicion que esta y false en caso contrario
	 */
	public boolean equals(java.lang.Object o) {
		boolean b=false;
		Dirección nueva;
		if(o instanceof Dirección) {
			nueva = (Dirección) o;
			if (nueva.componenteX()==x && nueva.componenteY()==y) {
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x,y);
	}
}

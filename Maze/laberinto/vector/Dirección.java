package laberinto.vector;
import java.util.Objects;
import java.util.Random;
public class Direcci�n {

	private int x;
	private int y;
	
	private Direcci�n (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * vector unitario (0,1)
	 */
	public static final Direcci�n ARRIBA = new Direcci�n (0,1);
	/**
	 * vector unitario (0,-1)
	 */
	public static final Direcci�n ABAJO = new Direcci�n (0,-1);
	/**
	 * vector unitario (1,0)
	 */
	public static final Direcci�n DERECHA = new Direcci�n (1,0);
	/**
	 * vector unitario (-1,0)
	 */
	public static final Direcci�n IZQUIERDA = new Direcci�n (-1,0);
	
	/**
	 * Genera una direcci�n aleatoria: DERECHA, IZQUIERDA, ARRIBA, o ABAJO
	 * @return Un vector unitario que indica una direcci�n
	 */
	public static Direcci�n aleatoria() {
		Random r = new Random();
		Direcci�n[] d = {ARRIBA,ABAJO,DERECHA,IZQUIERDA};
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
	 * A partir de la direcci�n actual, se obtiene la direcci�n correspondiente a girar 180 grados. 
	 * Por ejemplo, si la direcci�n actual es DERECHA, la direcci�n obtenida ser� IZQUIERDA.
	 * @return el vector unitario con la nueva direcci�n
	 */
	public Direcci�n darLaVuelta() {
		Direcci�n d= new Direcci�n (-x,-y);
		return d;
	}
	
	/**
	 * * A partir de la direcci�n actual, se obtiene la direcci�n correspondiente a girar 90 grados en sentido horario. 
	 * Por ejemplo, si la direcci�n actual es IZQUIERDA, la direcci�n obtenida ser� ARRIBA.
	 * @return  el vector unitario con la nueva direcci�n
	 */
	public Direcci�n girarDerecha() {
		Direcci�n d= new Direcci�n (y,-x);
		return d;
	}
	
	/**
	 * * A partir de la direcci�n actual, se obtiene la direcci�n correspondiente a girar 90 grados en sentido antihorario. 
	 * Por ejemplo, si la direcci�n actual es DERECHA, la direcci�n obtenida ser� ARRIBA.
	 * @return el vector unitario con la nueva direcci�n
	 */
	public Direcci�n girarIzquierda() {
		Direcci�n d= new Direcci�n (-y,x);
		return d;
	}
	
	/**
	 * Indica si otro objeto es igual a este. Para ser igual, tendr� que ser una Posici�n
	 * y tener las mismas coordenadas en el eje X y en el eje Y
	 * @param o El objeto a comparar con este.
	 * @return true si o representa la misma posicion que esta y false en caso contrario
	 */
	public boolean equals(java.lang.Object o) {
		boolean b=false;
		Direcci�n nueva;
		if(o instanceof Direcci�n) {
			nueva = (Direcci�n) o;
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

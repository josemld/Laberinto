/**
 * 
 */
package laberinto;

/**
 * @author agf_9
 *
 */
public interface IRobot {
	

	/**
	 * Realiza un movimiento en el laberinto. Los robots que implementen esta interfaz usarán una cierta estrategia para intentar llegar a la salida.
	 */
	void avanzar();
	
	/**
	 * Permite obtener el nombre del robot, para diferenciarlo de los demás
	 * @return el nombre del robot
	 */
	String nombre();

	
	/**
	 * Permite saber si el robot ha llegado a la salida
	 * @return true si el robot estña en la casilla de salida y false en caso contrario.
	 */
	boolean salidaEncontrada();

}

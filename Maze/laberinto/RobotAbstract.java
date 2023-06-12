/**
 * 
 */
package laberinto;

import laberinto.vector.Dirección;
import laberinto.vector.Posición;



public abstract class RobotAbstract implements IRobot {
	
	protected Posición pAct;//posicion actual
	protected Posición pAnt;//posicion anterior
	//Posición pIni;//posicion inicial
	protected String name;//nombre del robot
	protected Dirección d; //dirección del robot
	protected Laberinto lab;
	

	
	/**
	 * Genera un robot que debe salir del laberinto indicado.
	 * El <code>Robot</code> se sitúa en una <code>Posición</code>
	 * aleatoria del laberinto, orientado también en una
	 * <code>Dirección</code> aleatoria.
	 * @param nombre El nombre con el que se identificará el robot
	 * @param l El laberinto del que debe salir 
	 * @throws java.lang.IllegalArgumentException Si alguno de los parametros es null
	 */
	public RobotAbstract (String nombre, final Laberinto l ) throws java.lang.IllegalArgumentException
	{
		if(nombre==null||l==null) {
			throw new IllegalArgumentException("nombre=null o l=null");
		}
		name=nombre;
		lab = l;
		pAct = lab.posiciónAleatoria();
		d = Dirección.aleatoria();
		
		
	}

	/**
	 * Realiza un movimiento en el laberinto. Usando la estrategia de la mano derecha, realiza un moviemiento para intentar llegar a la salida.
	 * 
	 *
	 */
	public abstract void avanzar();


	/**
	 * Muestra en el laberinto la nueva posición del robot, mediante un
	 * círculo de color verde. Según se va moviendo el robot, va dejando
	 * una estela de color azul.
	 * @param anterior La posición que ocupaba anteriormente el robot.
	 * @param actual La posición que ocupa el robot en este momento.
	 */
	public abstract void draw ( Posición anterior, Posición actual);
	
	/**
	 * Permite obtener el nombre del robot, para diferenciarlo de los demás
	 * @return el nombre del robot
	 */
	public String nombre() {
		
		return name;
	}
	
	
	/**
	 * Permite saber si el robot ha llegado a la salida
	 * @return true si el robot estña en la casilla de salida y false en caso contrario.
	 */
	public boolean salidaEncontrada() {
		boolean encontrada;
		if(lab.posiciónSalida(pAct)) {
			encontrada=true;
			
		}else {
			encontrada=false;
		}
		return encontrada;
		
	}
	

}

/**
 * 
 */
package laberinto;

import laberinto.vector.Direcci�n;
import laberinto.vector.Posici�n;



public abstract class RobotAbstract implements IRobot {
	
	protected Posici�n pAct;//posicion actual
	protected Posici�n pAnt;//posicion anterior
	//Posici�n pIni;//posicion inicial
	protected String name;//nombre del robot
	protected Direcci�n d; //direcci�n del robot
	protected Laberinto lab;
	

	
	/**
	 * Genera un robot que debe salir del laberinto indicado.
	 * El <code>Robot</code> se sit�a en una <code>Posici�n</code>
	 * aleatoria del laberinto, orientado tambi�n en una
	 * <code>Direcci�n</code> aleatoria.
	 * @param nombre El nombre con el que se identificar� el robot
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
		pAct = lab.posici�nAleatoria();
		d = Direcci�n.aleatoria();
		
		
	}

	/**
	 * Realiza un movimiento en el laberinto. Usando la estrategia de la mano derecha, realiza un moviemiento para intentar llegar a la salida.
	 * 
	 *
	 */
	public abstract void avanzar();


	/**
	 * Muestra en el laberinto la nueva posici�n del robot, mediante un
	 * c�rculo de color verde. Seg�n se va moviendo el robot, va dejando
	 * una estela de color azul.
	 * @param anterior La posici�n que ocupaba anteriormente el robot.
	 * @param actual La posici�n que ocupa el robot en este momento.
	 */
	public abstract void draw ( Posici�n anterior, Posici�n actual);
	
	/**
	 * Permite obtener el nombre del robot, para diferenciarlo de los dem�s
	 * @return el nombre del robot
	 */
	public String nombre() {
		
		return name;
	}
	
	
	/**
	 * Permite saber si el robot ha llegado a la salida
	 * @return true si el robot est�a en la casilla de salida y false en caso contrario.
	 */
	public boolean salidaEncontrada() {
		boolean encontrada;
		if(lab.posici�nSalida(pAct)) {
			encontrada=true;
			
		}else {
			encontrada=false;
		}
		return encontrada;
		
	}
	

}

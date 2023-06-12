package laberinto;
import laberinto.vector.*;

import java.awt.Color;

import laberinto.utilidades.*;
/**
 * Las instancias de esta clase representan a robots que tienen la
 * capacidad de ver qu� tienen delante y qu� tienen a los lados, y
 * pueden moverse en la direcci�n en la que est�n orientados a raz�n
 * de una casilla cada vez. Con estas capacidades, deben ser capaces
 * de salir de un laberinto en el que desde cualquier casilla es
 * posible alcanzar cualquier otra.
 */
public class RobotManoDerecha extends RobotAbstract
{
	public static final Color CESTELA = Color.MAGENTA;
	public static final Color CROBOT = Color.GREEN;
	public static int velocidad=0;

	//public static int pasos;
	
	/**
	 * Genera un robot que debe salir del laberinto indicado.
	 * El <code>Robot</code> se sit�a en una <code>Posici�n</code>
	 * aleatoria del laberinto, orientado tambi�n en una
	 * <code>Direcci�n</code> aleatoria.
	 * @param nombre El nombre que identificar� el robot
	 * @param l El laberinto del que debe salir.
	 * @throws java.lang.IllegalArgumentException Si alguno de los par�metros es null.
	 */
	public RobotManoDerecha(String nombre, final Laberinto l ) throws java.lang.IllegalArgumentException
	{
		super(nombre,l);
	}

	/**
	 * Realiza un movimiento en el laberinto. Usando la estrategia de la mano derecha, realiza un moviemiento para intentar llegar a la salida
	 */
	@Override
	public void avanzar() {
		if (lab.hayMuroDerecha(pAct, d)) {
			if(lab.hayMuroEnfrente(pAct, d)) {
				if(lab.hayMuroIzquierda(pAct, d)) {
					d=d.darLaVuelta();
					pAnt = pAct;
					pAct = pAct.mover(d);
				}else {
					d=d.girarIzquierda();
					pAnt = pAct;
					pAct = pAct.mover(d);
				}
				
			}else {
				pAnt = pAct;
				pAct = pAct.mover(d);
			}
			
		}else {
			d = d.girarDerecha();
			pAnt = pAct;
			pAct = pAct.mover(d);
		}
		draw(pAct,pAnt);
		
	}

	/**
	 * Muestra en el laberinto la nueva posici�n del robot, mediante un c�rculo de color rojo. Seg�n se va moviendo el robot, va dejando una estela de color verde.
	 * @param anterior - La posici�n que ocupaba el robot antes de moverse.
	 * @param actual - La posici�n que ocupa el robot despu�s de moverse.
	 */
	@Override
	public void draw(Posici�n anterior, Posici�n actual) {

		StdDraw.setPenColor ( CESTELA );
		StdDraw.filledCircle ( anterior.coordenadaX()+0.5+1, anterior.coordenadaY()+0.5+1, 0.2 );
		StdDraw.pause(velocidad);
		
		StdDraw.setPenColor ( CROBOT );
		StdDraw.filledCircle ( actual.coordenadaX()+0.5+1, actual.coordenadaY()+0.5+1, 0.2 );
		StdDraw.pause(velocidad);
	}
	
}

package laberinto;
import laberinto.vector.*;

import java.awt.Color;

import laberinto.utilidades.*;
/**
 * Las instancias de esta clase representan a robots que tienen la
 * capacidad de ver qué tienen delante y qué tienen a los lados, y
 * pueden moverse en la dirección en la que están orientados a razón
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
	 * El <code>Robot</code> se sitúa en una <code>Posición</code>
	 * aleatoria del laberinto, orientado también en una
	 * <code>Dirección</code> aleatoria.
	 * @param nombre El nombre que identificará el robot
	 * @param l El laberinto del que debe salir.
	 * @throws java.lang.IllegalArgumentException Si alguno de los parámetros es null.
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
	 * Muestra en el laberinto la nueva posición del robot, mediante un círculo de color rojo. Según se va moviendo el robot, va dejando una estela de color verde.
	 * @param anterior - La posición que ocupaba el robot antes de moverse.
	 * @param actual - La posición que ocupa el robot después de moverse.
	 */
	@Override
	public void draw(Posición anterior, Posición actual) {

		StdDraw.setPenColor ( CESTELA );
		StdDraw.filledCircle ( anterior.coordenadaX()+0.5+1, anterior.coordenadaY()+0.5+1, 0.2 );
		StdDraw.pause(velocidad);
		
		StdDraw.setPenColor ( CROBOT );
		StdDraw.filledCircle ( actual.coordenadaX()+0.5+1, actual.coordenadaY()+0.5+1, 0.2 );
		StdDraw.pause(velocidad);
	}
	
}

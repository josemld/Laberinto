package laberinto;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import laberinto.vector.*;
import visual.VentanaOpciones;
import laberinto.utilidades.StdDraw;

/**
 * Las instancias de esta clase representan laberintos sin bucles
 * donde los movimientos �nicamente son posibles en direcci�n
 * horizantal o vertical y todas las casillas del laberinto son
 * accesibles desde cualquier otra casilla. La salida del laberinto
 * est� en una posici�n elegida de manera aleatoria.
 */
public class Laberinto
{

	private Random generador=null;
	public int abscisas=0;
	private int ordenadas=0;
	private Posici�n salida=null;
	private Map<Posici�n,Map<Direcci�n,Boolean>> hayMuro=null;

	public Laberinto ( final int abscisas, final int ordenadas ){
		try {
		if(((abscisas<4)||(abscisas>40))||((abscisas<4)||(abscisas>40))) {
			throw new IllegalArgumentException ("Tama�o no permitido");
		}else {
			generador = new Random();
			this.abscisas = abscisas;
			this.ordenadas = ordenadas;
			salida = posici�nAleatoria();
			hayMuro = new HashMap<Posici�n,Map<Direcci�n,Boolean>>();
			colocarMuros();
		}
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			Thread thread = new Thread(() -> {
                VentanaOpciones frame = new VentanaOpciones();
                frame.setVisible(true);
            });
            thread.start();
		}
	}

	/**
	 * Pone un muro en la direcci�n <code>d</code> desde la posici�n
	 * <code>p</code>.
	 * @param p La posici�n del laberinto.
	 * @param d La direcci�n respecto a la posici�n <code>p</code>
	 * donde hay que poner el muro.
	 */
	private void ponerMuro ( Posici�n p, Direcci�n d )
	{
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();

		if(hayMuro.containsKey(p)) {
			if(hayMuro(p,d)) {
				throw new IllegalStateException("Ya hay un muro en esta posici�n y direcci�n");
			}
			else {
				aux = hayMuro.get(p);
				aux.put(d, true);
				hayMuro.put(p,aux);
			}
		} else {
			aux.put(d, true);
			hayMuro.put(p,aux);
		}
	}

	/**
	 * Elimina el muro que hay en la direcci�n <code>d</code> desde
	 * la posici�n <code>p</code>.
	 * @param p La posici�n del laberinto.
	 * @param d La direcci�n respecto a la posici�n <code>p</code>
	 * de donde hay que quitar el muro.
	 */
	private void quitarMuro ( Posici�n p, Direcci�n d )
	{
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();
		
		if(hayMuro.containsKey(p)) {
			if(!hayMuro(p,d)) {
				throw new IllegalStateException("No hay un muro en esta posici�n y direcci�n");
			}
			else {
				aux = hayMuro.get(p);
				aux.put(d, false);
				hayMuro.put(p,aux);
			}
		} else {
			aux = hayMuro.get(p);
			aux.put(d, false);
			hayMuro.put(p,aux);
		}
	}

	/**
	 * Dice si en la direcci�n <code>d</code> desde la posici�n
	 * <code>p</code> hay un muro o no.
	 * @param p La posici�n del laberinto.
	 * @param d La direcci�n respecto a la posici�n <code>p</code>
	 * en la que se quiere saber si hay un muro.
	 */
	private boolean hayMuro ( Posici�n p, Direcci�n d )
	{
		boolean hay = false;
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(d)==null) 
		{hay = false;} 
		else {hay=aux.get(d);}
		
		
		return hay;
	
	}

	/**
	 * Coloca los muros para conformar el laberinto.
	 */
	private void colocarMuros ()
	{
		final Map<Posici�n,Boolean> visitado = new HashMap<Posici�n,Boolean>();
		marcarTodosNoVisitados ( visitado );
		colocarTodosLosMurosPosibles ();
		eliminarMuros ( visitado );
	}

	/**
	 * Marca todas las casillas del tablero como no visitadas por el
	 * algoritmo que configura el laberinto.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 */
	private void marcarTodosNoVisitados ( Map<Posici�n,Boolean> visitado )
	{
		for ( int x = 0; x < abscisas; x++ )
			for ( int y = 0; y < ordenadas; y++ )
				visitado.put ( new Posici�n(x,y), false );
	}

	/**
	 * Coloca muros entre todas las casillas del laberinto y en sus
	 * bordes.
	 */
	private void colocarTodosLosMurosPosibles ()
	{
		for ( int x = 0; x < abscisas; x++ )
			for ( int y = 0; y < ordenadas; y++ )
			{
				final Posici�n p = new Posici�n(x,y);
				ponerMuro ( p, Direcci�n.ARRIBA );
				ponerMuro ( p, Direcci�n.ABAJO );
				ponerMuro ( p, Direcci�n.DERECHA );
				ponerMuro ( p, Direcci�n.IZQUIERDA );
			}
	}

	/**
	 * Elimina los muros necesarios para configurar un laberinto con
	 * la propiedad de que cualquier casilla del laberinto es accesible
	 * desde cualquier otra casilla y sin tener bucles.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 */
	private void eliminarMuros ( Map<Posici�n,Boolean> visitado )
	{
		eliminarMuros ( visitado, new Posici�n ( 1, 1 ) );
	}

	/**
	 * Algoritmo recursivo que elimina los muros necesarios a partir
	 * de una posici�n dada.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posici�n a ser explorada por el algoritmo.
	 */
	private void eliminarMuros ( Map<Posici�n,Boolean> visitado, Posici�n p )
	{
		Direcci�n d;

		visitado.put ( p, true );
		d = vecinoNoVisitado ( visitado, p );
		while ( d != null )
		{
			quitarMuro ( p, d );
			quitarMuro ( p.mover(d), d.darLaVuelta() );
			eliminarMuros ( visitado, p.mover(d) );
			d = vecinoNoVisitado ( visitado, p );
		}
	}

	/**
	 * Elige una casilla vecina que no haya sido visitada, para ser
	 * la siguiente casilla a visitar.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posici�n que est� siendo explorada por el algoritmo.
	 * @return La direcci�n en la que se puede visitar una casilla que
	 * a�n no hab�a sido visitada. En caso de que todas las casillas
	 * vecinas de la actual ya hayan sido visitadas, devuelve <code>null</code>.
	 */
	private Direcci�n vecinoNoVisitado ( Map<Posici�n,Boolean> visitado, Posici�n p )
	{
		Direcci�n d;

		if ( hayVecinoNoVisitado ( visitado, p ) )
			d = unVecinoNoVisitado ( visitado, p );
		else
			d = null;
		return d;
	}

	/**
	 * Elige aleatoriamente una direcci�n en la que hay una casilla que
	 * a�n no ha sido visitada (suponiendo que la hay).
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posici�n que est� siendo explorada por el algoritmo.
	 * @return La direcci�n en la que se puede visitar una casilla que
	 * a�n no hab�a sido visitada.
	 */
	private Direcci�n unVecinoNoVisitado ( Map<Posici�n,Boolean> visitado, Posici�n p )
	{
		Direcci�n d;

		do
		{	d = Direcci�n.aleatoria();
		} while ( !visitado.containsKey(p.mover(d)) || visitado.get(p.mover(d)) );
		return d;
	}

	/**
	 * Comprueba si la casilla actual tiene alguna vecina no visitada a�n.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posici�n que est� siendo explorada por el algoritmo.
	 * @return <code>true</code> si alguna vecina de la casilla <code>p</code>
	 * a�n no ha sido visitada y <code>false</code> en caso contrario.
	 */
	private boolean hayVecinoNoVisitado ( Map<Posici�n,Boolean> visitado, Posici�n p )
	{
		final Direcci�n d = Direcci�n.aleatoria();
		final Posici�n v1 = p.mover(d);
		final Posici�n v2 = p.mover(d.girarDerecha());
		final Posici�n v3 = p.mover(d.girarIzquierda());
		final Posici�n v4 = p.mover(d.darLaVuelta());
		final boolean v1OK = visitado.containsKey(v1) && !visitado.get(v1);
		final boolean v2OK = visitado.containsKey(v2) && !visitado.get(v2);
		final boolean v3OK = visitado.containsKey(v3) && !visitado.get(v3);
		final boolean v4OK = visitado.containsKey(v4) && !visitado.get(v4);

		return v1OK || v2OK || v3OK || v4OK;
	}

	/**
	 * Genera una posici�n aleatoria dentro del laberinto.
	 * @return Una posici�n v�lida dentro del laberinto.
	 */
	public Posici�n posici�nAleatoria ()
	{
		final int randomX = generador.nextInt ( abscisas );
		final int randomY = generador.nextInt ( ordenadas );
		return new Posici�n ( randomX, randomY );
	}

	/**
	 * Permite conocer si una posici�n es aquella en la que
	 * est� situada la salida del laberinto.
	 * @param p Una posici�n.
	 * @return <code>true</code> si la salida del laberinto est�
	 * en la posici�n <code>p</code> y <code>false</code> en
	 * caso contrario.
	 */
	public boolean posici�nSalida ( Posici�n p )
	{
		return salida.equals ( p );
	}

	/**
	 * Dibuja el laberinto y marca la posici�n donde est� la salida
	 * con un punto de color rojo.
	 */
	public void draw()
	{
		StdDraw.setXscale ( 0, abscisas+2 );
		StdDraw.setYscale ( 0, ordenadas+2 );
		StdDraw.setPenColor ( StdDraw.RED );
		StdDraw.filledCircle ( salida.coordenadaX()+0.5+1, salida.coordenadaY()+0.5+1, 0.375 );
		StdDraw.setPenColor ( StdDraw.BLACK );
		for ( int x = 0; x < abscisas; x++ )
			for ( int y = 0; y < ordenadas; y++ )
			{
				final Posici�n p = new Posici�n(x,y);
				if ( hayMuro ( p, Direcci�n.ABAJO ) )
					StdDraw.line( x+1, y+1, x+1+1, y+1 );
				if ( hayMuro ( p, Direcci�n.ARRIBA ) )
					StdDraw.line( x+1, y+1+1, x+1+1, y+1+1 );
				if ( hayMuro ( p, Direcci�n.IZQUIERDA ) )
					StdDraw.line ( x+1, y+1, x+1, y+1+1 );
				if ( hayMuro ( p, Direcci�n.DERECHA ) )
					StdDraw.line ( x+1+1, y+1, x+1+1, y+1+1 );
			}
		StdDraw.show();
		StdDraw.pause(1000);
	}
	
	/****
	 * Metodos para ocultar/cerrar el laberinto
	 */
	public void clear () {
		StdDraw.clear();
	}
	public void hide () {
		StdDraw.hide();
	}
	
	public void close() {
		StdDraw.close();
	}

	/**
	 * Indica si estando en la posici�n <code>p</code> del laberinto y
	 * mirando en la direcci�n <code>d</code>, hay un muro a la derecha.
	 * @param p La posici�n.
	 * @param d La direcci�n en la que se mira.
	 * @return <code>true</code> si a la derecha hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroDerecha ( Posici�n p, Direcci�n d )
	{
		Direcci�n nueva = d.girarDerecha();
		boolean hay = false;
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(nueva)==null) 
		{hay = false;} 
		else {hay=aux.get(nueva);}
		
		
		return hay;
	}

	/**
	 * Indica si estando en la posici�n <code>p</code> del laberinto y
	 * mirando en la direcci�n <code>d</code>, hay un muro a la izquierda.
	 * @param p La posici�n.
	 * @param d La direcci�n en la que se mira.
	 * @return <code>true</code> si a la izquierda hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroIzquierda ( Posici�n p, Direcci�n d )
	{
		Direcci�n nueva = d.girarIzquierda();
		boolean hay = false;
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(nueva)==null) 
		{hay = false;} 
		else {hay=aux.get(nueva);}
		
		
		return hay;
	}

	/**
	 * Indica si estando en la posici�n <code>p</code> del laberinto y
	 * mirando en la direcci�n <code>d</code>, hay un muro enfrente.
	 * @param p La posici�n.
	 * @param d La direcci�n en la que se mira.
	 * @return <code>true</code> si enfrente hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroEnfrente ( Posici�n p, Direcci�n d )
	{
		boolean hay = false;
		Map<Direcci�n,Boolean> aux = new HashMap<Direcci�n,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(d)==null) 
		{hay = false;} 
		else {hay=aux.get(d);}
		
		
		return hay;
	}
}

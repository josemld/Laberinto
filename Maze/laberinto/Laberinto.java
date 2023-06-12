package laberinto;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import laberinto.vector.*;
import visual.VentanaOpciones;
import laberinto.utilidades.StdDraw;

/**
 * Las instancias de esta clase representan laberintos sin bucles
 * donde los movimientos únicamente son posibles en dirección
 * horizantal o vertical y todas las casillas del laberinto son
 * accesibles desde cualquier otra casilla. La salida del laberinto
 * está en una posición elegida de manera aleatoria.
 */
public class Laberinto
{

	private Random generador=null;
	public int abscisas=0;
	private int ordenadas=0;
	private Posición salida=null;
	private Map<Posición,Map<Dirección,Boolean>> hayMuro=null;

	public Laberinto ( final int abscisas, final int ordenadas ){
		try {
		if(((abscisas<4)||(abscisas>40))||((abscisas<4)||(abscisas>40))) {
			throw new IllegalArgumentException ("Tamaño no permitido");
		}else {
			generador = new Random();
			this.abscisas = abscisas;
			this.ordenadas = ordenadas;
			salida = posiciónAleatoria();
			hayMuro = new HashMap<Posición,Map<Dirección,Boolean>>();
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
	 * Pone un muro en la dirección <code>d</code> desde la posición
	 * <code>p</code>.
	 * @param p La posición del laberinto.
	 * @param d La dirección respecto a la posición <code>p</code>
	 * donde hay que poner el muro.
	 */
	private void ponerMuro ( Posición p, Dirección d )
	{
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();

		if(hayMuro.containsKey(p)) {
			if(hayMuro(p,d)) {
				throw new IllegalStateException("Ya hay un muro en esta posición y dirección");
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
	 * Elimina el muro que hay en la dirección <code>d</code> desde
	 * la posición <code>p</code>.
	 * @param p La posición del laberinto.
	 * @param d La dirección respecto a la posición <code>p</code>
	 * de donde hay que quitar el muro.
	 */
	private void quitarMuro ( Posición p, Dirección d )
	{
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();
		
		if(hayMuro.containsKey(p)) {
			if(!hayMuro(p,d)) {
				throw new IllegalStateException("No hay un muro en esta posición y dirección");
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
	 * Dice si en la dirección <code>d</code> desde la posición
	 * <code>p</code> hay un muro o no.
	 * @param p La posición del laberinto.
	 * @param d La dirección respecto a la posición <code>p</code>
	 * en la que se quiere saber si hay un muro.
	 */
	private boolean hayMuro ( Posición p, Dirección d )
	{
		boolean hay = false;
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();
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
		final Map<Posición,Boolean> visitado = new HashMap<Posición,Boolean>();
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
	private void marcarTodosNoVisitados ( Map<Posición,Boolean> visitado )
	{
		for ( int x = 0; x < abscisas; x++ )
			for ( int y = 0; y < ordenadas; y++ )
				visitado.put ( new Posición(x,y), false );
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
				final Posición p = new Posición(x,y);
				ponerMuro ( p, Dirección.ARRIBA );
				ponerMuro ( p, Dirección.ABAJO );
				ponerMuro ( p, Dirección.DERECHA );
				ponerMuro ( p, Dirección.IZQUIERDA );
			}
	}

	/**
	 * Elimina los muros necesarios para configurar un laberinto con
	 * la propiedad de que cualquier casilla del laberinto es accesible
	 * desde cualquier otra casilla y sin tener bucles.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 */
	private void eliminarMuros ( Map<Posición,Boolean> visitado )
	{
		eliminarMuros ( visitado, new Posición ( 1, 1 ) );
	}

	/**
	 * Algoritmo recursivo que elimina los muros necesarios a partir
	 * de una posición dada.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posición a ser explorada por el algoritmo.
	 */
	private void eliminarMuros ( Map<Posición,Boolean> visitado, Posición p )
	{
		Dirección d;

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
	 * @param p La posición que está siendo explorada por el algoritmo.
	 * @return La dirección en la que se puede visitar una casilla que
	 * aún no había sido visitada. En caso de que todas las casillas
	 * vecinas de la actual ya hayan sido visitadas, devuelve <code>null</code>.
	 */
	private Dirección vecinoNoVisitado ( Map<Posición,Boolean> visitado, Posición p )
	{
		Dirección d;

		if ( hayVecinoNoVisitado ( visitado, p ) )
			d = unVecinoNoVisitado ( visitado, p );
		else
			d = null;
		return d;
	}

	/**
	 * Elige aleatoriamente una dirección en la que hay una casilla que
	 * aún no ha sido visitada (suponiendo que la hay).
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posición que está siendo explorada por el algoritmo.
	 * @return La dirección en la que se puede visitar una casilla que
	 * aún no había sido visitada.
	 */
	private Dirección unVecinoNoVisitado ( Map<Posición,Boolean> visitado, Posición p )
	{
		Dirección d;

		do
		{	d = Dirección.aleatoria();
		} while ( !visitado.containsKey(p.mover(d)) || visitado.get(p.mover(d)) );
		return d;
	}

	/**
	 * Comprueba si la casilla actual tiene alguna vecina no visitada aún.
	 * @param visitado Para cada casilla del laberinto, indica si ha
	 * sido visitada ya por el algoritmo.
	 * @param p La posición que está siendo explorada por el algoritmo.
	 * @return <code>true</code> si alguna vecina de la casilla <code>p</code>
	 * aún no ha sido visitada y <code>false</code> en caso contrario.
	 */
	private boolean hayVecinoNoVisitado ( Map<Posición,Boolean> visitado, Posición p )
	{
		final Dirección d = Dirección.aleatoria();
		final Posición v1 = p.mover(d);
		final Posición v2 = p.mover(d.girarDerecha());
		final Posición v3 = p.mover(d.girarIzquierda());
		final Posición v4 = p.mover(d.darLaVuelta());
		final boolean v1OK = visitado.containsKey(v1) && !visitado.get(v1);
		final boolean v2OK = visitado.containsKey(v2) && !visitado.get(v2);
		final boolean v3OK = visitado.containsKey(v3) && !visitado.get(v3);
		final boolean v4OK = visitado.containsKey(v4) && !visitado.get(v4);

		return v1OK || v2OK || v3OK || v4OK;
	}

	/**
	 * Genera una posición aleatoria dentro del laberinto.
	 * @return Una posición válida dentro del laberinto.
	 */
	public Posición posiciónAleatoria ()
	{
		final int randomX = generador.nextInt ( abscisas );
		final int randomY = generador.nextInt ( ordenadas );
		return new Posición ( randomX, randomY );
	}

	/**
	 * Permite conocer si una posición es aquella en la que
	 * está situada la salida del laberinto.
	 * @param p Una posición.
	 * @return <code>true</code> si la salida del laberinto está
	 * en la posición <code>p</code> y <code>false</code> en
	 * caso contrario.
	 */
	public boolean posiciónSalida ( Posición p )
	{
		return salida.equals ( p );
	}

	/**
	 * Dibuja el laberinto y marca la posición donde está la salida
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
				final Posición p = new Posición(x,y);
				if ( hayMuro ( p, Dirección.ABAJO ) )
					StdDraw.line( x+1, y+1, x+1+1, y+1 );
				if ( hayMuro ( p, Dirección.ARRIBA ) )
					StdDraw.line( x+1, y+1+1, x+1+1, y+1+1 );
				if ( hayMuro ( p, Dirección.IZQUIERDA ) )
					StdDraw.line ( x+1, y+1, x+1, y+1+1 );
				if ( hayMuro ( p, Dirección.DERECHA ) )
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
	 * Indica si estando en la posición <code>p</code> del laberinto y
	 * mirando en la dirección <code>d</code>, hay un muro a la derecha.
	 * @param p La posición.
	 * @param d La dirección en la que se mira.
	 * @return <code>true</code> si a la derecha hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroDerecha ( Posición p, Dirección d )
	{
		Dirección nueva = d.girarDerecha();
		boolean hay = false;
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(nueva)==null) 
		{hay = false;} 
		else {hay=aux.get(nueva);}
		
		
		return hay;
	}

	/**
	 * Indica si estando en la posición <code>p</code> del laberinto y
	 * mirando en la dirección <code>d</code>, hay un muro a la izquierda.
	 * @param p La posición.
	 * @param d La dirección en la que se mira.
	 * @return <code>true</code> si a la izquierda hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroIzquierda ( Posición p, Dirección d )
	{
		Dirección nueva = d.girarIzquierda();
		boolean hay = false;
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(nueva)==null) 
		{hay = false;} 
		else {hay=aux.get(nueva);}
		
		
		return hay;
	}

	/**
	 * Indica si estando en la posición <code>p</code> del laberinto y
	 * mirando en la dirección <code>d</code>, hay un muro enfrente.
	 * @param p La posición.
	 * @param d La dirección en la que se mira.
	 * @return <code>true</code> si enfrente hay un muro y
	 * <code>false</code> en caso contrario.
	 */
	public boolean hayMuroEnfrente ( Posición p, Dirección d )
	{
		boolean hay = false;
		Map<Dirección,Boolean> aux = new HashMap<Dirección,Boolean>();
		aux = hayMuro.get(p);
		if(aux.get(d)==null) 
		{hay = false;} 
		else {hay=aux.get(d);}
		
		
		return hay;
	}
}

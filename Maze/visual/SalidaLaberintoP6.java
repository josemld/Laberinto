package visual;

import java.awt.Color;
import java.util.*;

import laberinto.IRobot;
import laberinto.Laberinto;
import laberinto.RobotManoDerecha;
import laberinto.RobotManoIzquierda;

public class SalidaLaberintoP6{
	int dimension, rD, rI, mov=0;

    int pasosMD = 0;
    int pasosMI = 0;
    
    int robMD=0;
	int robMI=0;
	
	Color color;
	
	String ganador = "";
	
	// Metodo que inicia la aplicacion
	
	public  void iniciar(int velocidad) {

		/***
		 * Inicia el programa creando el laberinto y añadiendo los robots, 
		 * luego los inicia y empiezan a reccorrer el laberinto hasta que terminan;
		 */
		
		try{
			Laberinto l = new Laberinto (dimension, dimension);
			
			if(l.abscisas >= 4 && l.abscisas <= 40) {
				
			RobotManoDerecha.velocidad=velocidad;
			RobotManoIzquierda.velocidad=velocidad;

			l.clear();
			l.draw();
			
			IRobot r = null;
			List <IRobot> robots = new ArrayList <IRobot>();
			ListIterator <IRobot> it;
			
			//Agrega los robots a la lista, la cual 
			//se recorrera a la hora de iniciar el juego
			
			for(int i=1;i<=rD;i++) {
				r=new RobotManoDerecha("RobotMD-"+ i, l);
				robots.add(r);
			}
			
			for(int i=1;i<=rI;i++) {
				r=new RobotManoIzquierda("RobotMI-"+ i, l);
				robots.add(r);	
			}	
			
			//Fin creacion lista
			
			//Bucle del juego del laberinto
			
			do{
				it = robots.listIterator();
				while(it.hasNext()) {
					r=it.next();
					if(r.salidaEncontrada()) {
						System.out.println("el robot de nombre "+ r.nombre() + " de clase: "+ r.getClass().getSimpleName() +" ha encontrado la salida en: " + mov + " movimientos");
						if (r.nombre().contains("MI")) {
							
							//Agrego los pasos y voy contando los robots que salen para ver el ganador
							
							pasosMI += mov;
							robMI++;
							if (robMI == rI && ganador.equals("")) {
								ganador = "Han ganado los Robots de Mano Izquierda (AZUL)";
								color = Color.blue;
							}
						}else {
						
							//Agrego los pasos y voy contando los robots que salen para ver el ganador
							
							pasosMD += mov;
							robMD++;
							if (robMD == rD && ganador.equals("")) {
								ganador = "Han ganado los Robots de Mano Derecha (VERDE)";
								color = Color.GREEN;
							}
						}
						
						//Voy eliminando los robots
						
						it.remove();
					}else r.avanzar();
				}
				
				mov++;
			
			}while(robots.size()>0);
			
			//Fin bucle
			
			//Creo el tercer hilo 
			
			Thread thread3 = new Thread(() -> {
	            /*SalidaLaberintoP6 ej = new SalidaLaberintoP6();
	            ej.iniciar();*/
	        
			//Instancio y uso el ultimo frame donde muestro el resultado
			
				Resultado menuFin = new Resultado();
				menuFin.ganador.setText(ganador);
				
				//If donde compruebo cual ha sido mas eficaz
				
				if (pasosMI < pasosMD) {
					menuFin.efectivo.setText("Han sido mas efectivos los RMI (AZUL)");
				}else if(pasosMI > pasosMD){
					menuFin.efectivo.setText("Han sido mas efectivos los RMD (VERDE)");
				}else {
					menuFin.efectivo.setText("Han sido igual de efectivos ambos equipos");
				}
				
				//Doy valor a las label
				
				menuFin.pasosRD.setText("Los Robots de mano Derecha (VERDE) han recorrido "+pasosMD+" pasos");
				menuFin.pasosRI.setText("Los Robots de mano Izquierda (AZUL) han recorrido "+pasosMI+" pasos");
				
				//Hago visible el menu
				
				menuFin.color=color;
				
				menuFin.setVisible(true);
			});
	        thread3.start();	
        
	        //Borro el laberinto
			
			l.hide();
		}else {
			System.out.println("Tamaño feo");
		}
			
		}catch (IllegalArgumentException exc){
			System.out.println("ERROR. Argumento no válido " + exc.getMessage());
		}catch (IllegalStateException ex) {
			System.out.println("ERROR:" + ex.getMessage());
		}
	}
}

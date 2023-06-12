package visual;

import java.awt.Dimension;
import java.util.*;
import javax.swing.JFrame;
import laberinto.IRobot;
import laberinto.Laberinto;
import laberinto.RobotManoDerecha;
import laberinto.RobotManoIzquierda;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;

public class VentanaOpciones extends JFrame {

	private JPanel contentPane;
	private JTextField dimension;
	private JTextField rmi;
	private JTextField rmd;
	JButton play;
	Thread thread2;
	private JTextField velocidad;
	
	public static void main(String[] args) {
        
		// Crear y ejecutar el primer hilo
		
        Thread thread1 = new Thread(() -> {
            VentanaOpciones frame = new VentanaOpciones();
            frame.setVisible(true);
        });
        thread1.start();
    }

	/**
	 * Crea el primer menú de opciones
	 */
	
	public VentanaOpciones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 461);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dimension = new JTextField();
		dimension.setFont(new Font("Tahoma", Font.BOLD, 14));
		dimension.setBackground(new Color(192, 192, 192));
		dimension.setHorizontalAlignment(SwingConstants.CENTER);
		dimension.setBounds(533, 257, 86, 20);
		contentPane.add(dimension);
		dimension.setColumns(10);
		
		rmi = new JTextField();
		rmi.setFont(new Font("Tahoma", Font.BOLD, 14));
		rmi.setBackground(new Color(192, 192, 192));
		rmi.setHorizontalAlignment(SwingConstants.CENTER);
		rmi.setColumns(10);
		rmi.setBounds(533, 288, 86, 20);
		contentPane.add(rmi);
		
		rmd = new JTextField();
		rmd.setFont(new Font("Tahoma", Font.BOLD, 14));
		rmd.setBackground(new Color(192, 192, 192));
		rmd.setHorizontalAlignment(SwingConstants.CENTER);
		rmd.setColumns(10);
		rmd.setBounds(533, 319, 86, 20);
		contentPane.add(rmd);
		
		JLabel lblNewLabel = new JLabel("Introduce las dimensiones del laberinto, entre 4 y 40");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 257, 513, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Introduce la cantidad de Robots Mano Izquierda a participar");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 288, 513, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Introduce la cantidad de Robots Mano Derecha a participar");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 319, 513, 20);
		contentPane.add(lblNewLabel_2);
		
		//Creo el boton con el que inicia el juego 
		
		play = new JButton("Iniciar");
		play.setFont(new Font("Tahoma", Font.BOLD, 18));
		play.setBackground(new Color(128, 255, 255));
		
		play.setBounds(289, 377, 122, 34);

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Crear y ejecutar el segundo hilo
				
		         	thread2 = new Thread(() -> {
		            SalidaLaberintoP6 ej = new SalidaLaberintoP6();
		            ej.dimension=Integer.parseInt(dimension.getText());
		            ej.rD=Integer.parseInt(rmd.getText());
		            ej.rI=Integer.parseInt(rmi.getText());
		            
		            //Cojo la velocidad introducida y la establezco en el juego
		            int vel = Integer.parseInt(velocidad.getText()); 
		            
		            setVisible(false);
		            ej.iniciar(vel);
		        });
		        thread2.start();
			}
		});
		
		setLocationRelativeTo(null);

		contentPane.add(play);
		
		JLabel lblNewLabel_3 = new JLabel("(OPCIONAL) Introduce la velocidad de reproduccion, entre 1 y 50");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_3.setBounds(10, 349, 513, 23);
		contentPane.add(lblNewLabel_3);
		
		velocidad = new JTextField();
		velocidad.setFont(new Font("Tahoma", Font.BOLD, 14));
		velocidad.setBackground(new Color(192, 192, 192));
		velocidad.setHorizontalAlignment(SwingConstants.CENTER);
		velocidad.setText("2");
		velocidad.setBounds(533, 350, 86, 20);
		contentPane.add(velocidad);
		velocidad.setColumns(10);
		
		JLabel NumEnteros = new JLabel("<html><u>TODOS LOS DATOS<br> A INTRODUCIR DE<br> CARACTER NUMERICO<br>DEBEN SER ENTEROS</u></html>");
		NumEnteros.setForeground(new Color(255, 0, 0));
		NumEnteros.setHorizontalAlignment(SwingConstants.CENTER);
		NumEnteros.setFont(new Font("Tahoma", Font.BOLD, 18));
		NumEnteros.setBounds(410, 13, 250, 122);
		contentPane.add(NumEnteros);
		
		JLabel Default = new JLabel("(Default)");
		Default.setHorizontalAlignment(SwingConstants.CENTER);
		Default.setBounds(623, 350, 86, 21);
		contentPane.add(Default);
		
		JButton btnNewButton = new JButton("Explicacion regla de la mano derecha");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/Regla_de_la_mano_derecha"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(10, 69, 332, 31);
		contentPane.add(btnNewButton);
		
		JButton btnExplicacionReglaDe = new JButton("Explicacion regla de la mano izquierda");
		btnExplicacionReglaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/Regla_de_la_mano_izquierda"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		btnExplicacionReglaDe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnExplicacionReglaDe.setBackground(new Color(192, 192, 192));
		btnExplicacionReglaDe.setBounds(10, 27, 332, 31);
		contentPane.add(btnExplicacionReglaDe);
		
		JButton trol = new JButton("No me presiones");
		trol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String[] FRASES = {
			    		"Déjameeee",
			            "¡Para de pulsarme!",
			            "¡Te dije que no me pulsaras!",
			            "¡Que pares!",
			            "No me pulses más",
			            "¡Detente!",
			            "¡Basta de pulsaciones!",
			            "No me pulses",
			            "Détente",
			            "Para de pulsarme",
			            "Te dije que no me pulsaras",
			            "Deja de pulsarme",
			            "No sigas pulsando",
			            "Basta de pulsaciones",
			            "No quiero que me pulses",
			            "Detén tus dedos",
			            "Evita tocarme",
			            "Por favor, no pulses",
			            "Respeta mi espacio",
			            "No insistas en pulsar",
			            "Olvida pulsarme",
			            "No quiero tus pulsaciones",
			            "Desiste de pulsarme",
			            "No lo hagas más",
			            "Párale ya",
			            "Cesa en tus pulsaciones",
			            "Mantén tus manos lejos",
			            "Guarda tus dedos para ti",
			            "No te acerques tanto",
			            "Sigue tus pulsaciones en otro lugar",
			            "No me gusta que pulses",
			            "Me incomoda que me toques",
			            "No necesito tus pulsaciones",
			            "Prefiero que no pulses",
			            "Evita tocar",
			            "No pulses más, por favor",
			            "Te pido que no me pulses",
			            "Detén esa acción",
			            "No quiero que pulses mi cuerpo",
			            "Me molesta que me pulses",
			            "No sigas presionando",
			            "Aléjate de mí",
			            "Deja de pulsar botones",
			            "Por favor, respétame",
			            "No me presiones",
			            "No pulses contra mi voluntad",
			            "Mantén las manos quietas",
			            "No te acerques tanto",
			            "No deseo tus pulsaciones",
			            "No acepto tus toques",
			            "Deja de ejercer presión",
			            "No quiero tus manos sobre mí",
			            "Por favor, no pulses más",
			            "No pulses sin permiso",
			            "No quiero ser pulsado",
			            "No sigas tocando",
			            "Evita tocarme",
			            "No quiero sentir tus pulsaciones",
			            "Me disgusta que me pulses",
			            "No pulses en exceso",
			            "Te lo ruego, no me pulses",
			            "No me gusta que toques",
			            "No pulses sin consentimiento",
			            "No deseo tu tacto",
			            "Por favor, evita pulsar",
			            "No insistas en tocarme",
			            "No me des presión",
			            "No pulses sin permiso previo",
			            "No quiero que me toques",
			            "Evita pulsar sin autorización",
			            "No me pulses innecesariamente",
			            "No sigas presionando",
			            "No me toques sin mi consentimiento",
			            "Por favor, no sigas pulsando",
			            "No quiero tus pulsaciones cerca",
			            "No acepto que me toques",
			            "Deja de pulsar, por favor",
			            "No pulses sin preguntar",
			            "No quiero tu contacto físico",
			            "No me gustan tus pulsaciones",
			            "No pulses si no te lo pido",
			            "No me toques más",
			            "Por favor, respeta mi espacio personal",
			            "No pulses sin autorización",
			            "No deseo tus manos sobre mí",
			            "Deja de presionar",
			            "No insistas en tocarme",
			            "¡TE DIJE QUE NO ME PRESIONARAS!"
			    };
			    
			    Random random = new Random();
		        int index = random.nextInt(FRASES.length);
		        String fraseAleatoria = FRASES[index];
		        
				trol.setText(fraseAleatoria);
			}
		});
		trol.setBackground(new Color(192, 192, 192));
		trol.setFont(new Font("Tahoma", Font.BOLD, 10));
		trol.setBounds(410, 128, 250, 22);
		contentPane.add(trol);
		//trol.setVisible(false);

		JLabel txt1 = new JLabel();
		txt1.setFont(new Font("Tahoma", Font.ITALIC, 18));
		txt1.setBounds(10, 111, 609, 131);
		txt1.setPreferredSize(new Dimension(200, 100)); // Establece un tamaño de 200x100 píxeles
		txt1.setText("<html>Este juego consiste en:<br><br>1- Dar las dimensiones al laberinto<br>2- Introducir los Robots Mano Izquierda, que son de color azul y amarillo<br>3- Introducir los Robots Mano Derecha, de color verde y magenta<br>4- (OPCIONAL) Modificar la velocidad de reproduccion</html>");
		contentPane.add(txt1);
		
	}
}

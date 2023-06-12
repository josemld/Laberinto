package visual;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Resultado extends JFrame {

	//Creo las variables
	private JPanel contentPane;
	JLabel ganador;
	JLabel pasosRD;
	JLabel pasosRI;
	JLabel efectivo;
	Color color;
	private JButton btnNewButton;


	/**
	 * Crea el frame
	 */
	public Resultado() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 897, 592);
		contentPane = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                int radio = Math.min(getWidth()/4, getHeight()) / 8;
                int centroX = getWidth() / 2;
                int centroY = getHeight() / 10;
                int x = centroX - radio;
                int y = centroY - radio;
                g.fillOval(x, y, radio * 2, radio * 2);
                g.fillOval(x, y*10+65, radio * 2, radio * 2);
                g.fillOval(x, y*10+190, radio * 2, radio * 2);
                g.drawLine(x-500, y+100, x+500, y+100);

            }
			
		};
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 128));
		
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ganador = new JLabel("Han ganado los Robots de Mano Izquierda");
		ganador.setForeground(new Color(0, 0, 0));
		ganador.setHorizontalAlignment(SwingConstants.CENTER);
		ganador.setFont(new Font("Papyrus", Font.BOLD, 30));
		ganador.setBounds(0, 62, 871, 84);
		contentPane.add(ganador);
		
		pasosRD = new JLabel("Los Robots de mano Derecha han recorrido 10000 pasos");
		pasosRD.setHorizontalAlignment(SwingConstants.CENTER);
		pasosRD.setForeground(new Color(0, 0, 0));
		pasosRD.setFont(new Font("Papyrus", Font.BOLD | Font.ITALIC, 20));
		pasosRD.setBounds(88, 186, 717, 49);
		contentPane.add(pasosRD);
		
		pasosRI = new JLabel("Los Robots de mano Izquierda han recorrido 10000 pasos");
		pasosRI.setHorizontalAlignment(SwingConstants.CENTER);
		pasosRI.setForeground(new Color(0, 0, 0));
		pasosRI.setFont(new Font("Papyrus", Font.BOLD | Font.ITALIC, 20));
		pasosRI.setBounds(88, 246, 717, 49);
		contentPane.add(pasosRI);
		
		efectivo = new JLabel("El equipo mas efectivo han sido los Mano Izquierda");
		efectivo.setForeground(new Color(0, 0, 0));
		efectivo.setFont(new Font("Papyrus", Font.BOLD, 20));
		efectivo.setHorizontalAlignment(SwingConstants.CENTER);
		efectivo.setBounds(10, 306, 861, 49);
		contentPane.add(efectivo);
		
		//BOTON DE REINICIO DEL JUEGO 
		
		btnNewButton = new JButton("Reiniciar");
		btnNewButton.setBackground(new Color(128, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            //VentanaOpciones.main(null);
	            Thread thread = new Thread(() -> {
	                VentanaOpciones frame = new VentanaOpciones();
	                dispose();
	                frame.setVisible(true);
	            });
	            thread.start();
	            
			}
		});
		btnNewButton.setFont(new Font("Papyrus", Font.BOLD, 30));
		btnNewButton.setBounds(340, 406, 199, 49);
		contentPane.add(btnNewButton);
	}
}

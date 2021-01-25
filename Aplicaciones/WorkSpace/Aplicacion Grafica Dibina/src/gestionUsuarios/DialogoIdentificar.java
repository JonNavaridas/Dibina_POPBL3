package gestionUsuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import elementos.User;
import gestionElementosVisuales.FontFactory;
import gestionPantallas.RoundedBorder;

public class DialogoIdentificar extends JDialog {

	private static final long serialVersionUID = 1L;

	public final static int DEFAULT_WIDTH = 350;
	public final static int DEFAULT_HEIGHT = 140;
	
	JTextField id;
	String[] words;
	boolean escaneado;

	public DialogoIdentificar(JFrame ventana, String[] words, boolean modo) {
		super(ventana, words[0], modo);
		this.words = words;
		escaneado = false;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		panel.add(crearPanelInfo(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private Container crearPanelInfo() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		id = new JTextField();
		id.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
														BorderFactory.createTitledBorder(new RoundedBorder(5), words[1])));
		id.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		id.setPreferredSize(new Dimension(250, 60));
		id.setHorizontalAlignment(JLabel.CENTER);
		panel.add(id);
		
		return panel;
	}
	
	private Container crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[2]);
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 12));
		boton.addActionListener((l)->{
			escaneado = true;
			this.dispose();
		});
		
		JPanel pBoton = new JPanel(new FlowLayout(10));
		pBoton.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[3]);
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 12));
		boton.addActionListener((l)->this.dispose());
		
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}

	public User getUser() {
		this.setVisible(true);
		
		if (escaneado) {
			Identificador identificador = new Identificador();
			return identificador.getUser(id.getText());
		}
		else return null;
	}
}

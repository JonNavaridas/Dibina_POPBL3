package gestionUsuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import elementos.User;
import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import gestionPantallas.RoundedBorder;
import usuarios.UserException;

public class DialogoContraseña extends JDialog {

	private static final long serialVersionUID = 1L;

	public final static int DEFAULT_WIDTH = 400;
	public final static int DEFAULT_HEIGHT = 250;
	
	JPasswordField oldPassword, newPassword;
	boolean cambiarContraseña;
	String[] words;
	User user;
	
	public DialogoContraseña(JFrame ventana, String titulo, boolean modo, User user, String[] words) {
		super(ventana,titulo,modo);
		this.words = words;
		this.user = user;
		
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
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.white);
		
		panel.add(crearPanelContraseñas(), BorderLayout.CENTER);
		panel.add(crearPanelBoton(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private Component crearPanelContraseñas() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 0, 20));		
		Font font = FontFactory.createFont(FontFactory.BASE_FONT, 12);
		panel.setBackground(Color.white);
		
		JPanel pOld = new JPanel(new BorderLayout(10, 0));
		oldPassword = crearJPasswordField(font, words[0]);

		pOld.setBackground(Color.white);
		pOld.add(oldPassword, BorderLayout.CENTER);
		pOld.add(crearJCheckBox(oldPassword), BorderLayout.EAST);

		JPanel pNew = new JPanel(new BorderLayout(10, 0));
		newPassword = crearJPasswordField(font, words[1]);

		pNew.setBackground(Color.white);
		pNew.add(newPassword, BorderLayout.CENTER);
		pNew.add(crearJCheckBox(newPassword), BorderLayout.EAST);
		
		panel.add(pOld);
		panel.add(pNew);
		
		return panel;
	}
	
	private JPasswordField crearJPasswordField(Font font, String titulo) {
		JPasswordField field = new JPasswordField(15);
		
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), 
				  		BorderFactory.createTitledBorder(new RoundedBorder(5), titulo)));
		field.setFont(font);
		field.setEchoChar('*');
		
		return field;
	}
	
	private JCheckBox crearJCheckBox(JPasswordField field) {
		JCheckBox mostrar = new JCheckBox();

		mostrar.setOpaque(true);
		mostrar.setBackground(Color.white);
		mostrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mostrar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_OCULTAR_CONTRASEÑA));
		mostrar.setSelectedIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_VER_CONTRASEÑA));
		mostrar.addActionListener((e)->{
			if (mostrar.isSelected()) field.setEchoChar((char)0);
			else field.setEchoChar('*');
		});
		
		return mostrar;
	}

	private Component crearPanelBoton() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[2]);
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.setPreferredSize(new Dimension(170, 30));
		boton.addActionListener((e)->{
			cambiarContraseña = true;
			this.dispose();
		});
		
		JPanel pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[4]);
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.setPreferredSize(new Dimension(170, 30));
		boton.addActionListener((e)->{
			cambiarContraseña = false;
			this.dispose();
		});
		
		pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}

	public int getNewPassword() throws UserException {
		this.setVisible(true);
		
		if (cambiarContraseña) {
			String password = "";
			for (int i = 0; i < oldPassword.getPassword().length; i++) password += oldPassword.getPassword()[i];
			
			if (user.getContraseña() == password.hashCode()) {				
				password = "";
				for (int i = 0; i < newPassword.getPassword().length; i++)
					password += newPassword.getPassword()[i];
				return password.hashCode();
			}
			else throw new UserException(words[3]);
		}
		else return -1;
	}
}
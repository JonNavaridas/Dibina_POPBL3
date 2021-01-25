package gestionUsuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.InputMismatchException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import elementos.Permisos;
import elementos.User;
import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import gestionPantallas.RoundedBorder;

public class DialogoCrearUsuario extends JDialog {

	private static final long serialVersionUID = 1L;

	public final static int DEFAULT_WIDTH = 400;
	public final static int DEFAULT_HEIGHT = 320;
	
	JRadioButton basico, avanzado, total;
	ButtonGroup permisos;
	String[] words;
	
	boolean crearUsuario;
	JPasswordField contraseña;
	JTextField nombre, apellido, id;
	
	public DialogoCrearUsuario(JFrame ventana, String titulo, boolean modo, String[] words) {
		super(ventana,titulo,modo);
		this.words = words;
		
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
		
		panel.add(crearPanelUsuario(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component crearPanelUsuario() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
		Font font = FontFactory.createFont(FontFactory.BASE_FONT, 14);
		panel.setBackground(Color.white);
		
		id = new JTextField();
		id.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
					 BorderFactory.createTitledBorder(new RoundedBorder(5), words[1]))); // ID user
		id.setFont(font);
		panel.add(id);
		
		nombre = new JTextField();
		nombre.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
					 	 BorderFactory.createTitledBorder(new RoundedBorder(5), words[2]))); // Nombre
		nombre.setFont(font);
		
		apellido = new JTextField();
		apellido.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
					 	 BorderFactory.createTitledBorder(new RoundedBorder(5), words[3]))); // Apellido
		apellido.setFont(font);
		
		JPanel pNombre = new JPanel(new GridLayout(1, 2, 10, 0));
		pNombre.setBackground(Color.white);
		pNombre.add(nombre);
		pNombre.add(apellido);
		panel.add(pNombre);
		
		contraseña = new JPasswordField();
		contraseña.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
				 			 BorderFactory.createTitledBorder(new RoundedBorder(5), words[4])));
		contraseña.setEchoChar('*');
		contraseña.setFont(font);
		
		JPanel pContraseña = new JPanel(new BorderLayout());
		JCheckBox mostrar = new JCheckBox();
		pContraseña.setBackground(Color.white);

		mostrar.setOpaque(true);
		mostrar.setBackground(Color.white);
		mostrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mostrar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_OCULTAR_CONTRASEÑA));
		mostrar.setSelectedIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_VER_CONTRASEÑA));
		mostrar.addActionListener((e)->{
			if (mostrar.isSelected()) contraseña.setEchoChar((char)0);
			else contraseña.setEchoChar('*');
		});
		
		pContraseña.add(contraseña, BorderLayout.CENTER);
		pContraseña.add(mostrar, BorderLayout.EAST);
		panel.add(pContraseña);
		
		JPanel pPermisos = new JPanel();
		pPermisos.setBackground(Color.white);
		pPermisos.add(crearPanelPermisos());
		panel.add(pPermisos);
		
		return panel;
	}

	private Component crearPanelPermisos() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBackground(Color.white);
		
		Font font = FontFactory.createFont(FontFactory.BASE_FONT, 16);
		
		basico = new JRadioButton(words[6]);
		basico.setBackground(Color.white);
		basico.setSelected(true);
		basico.setFont(font);
		
		avanzado = new JRadioButton(words[7]);
		avanzado.setBackground(Color.white);
		avanzado.setSelected(false);
		avanzado.setFont(font);
		
		total = new JRadioButton(words[8]);
		total.setBackground(Color.white);
		total.setSelected(false);
		total.setFont(font);

		permisos = new ButtonGroup();
		permisos.add(basico);
		permisos.add(avanzado);
		permisos.add(total);
		
		panel.add(basico);
		panel.add(avanzado);
		panel.add(total);
		
		return panel;
	}

	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1, 20));
		
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[5]); // Crear
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.setPreferredSize(new Dimension(170, 30));
		boton.addActionListener((e)->{
			crearUsuario = true;
			this.dispose();
		});
		
		JPanel pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[0]); // Cancelar
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.setPreferredSize(new Dimension(170, 30));
		boton.addActionListener((e)->{
			crearUsuario = false;
			this.dispose();
		});
		
		pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}

	public User getNewUsuario() throws InputMismatchException {
		this.setVisible(true);
		
		String id = this.id.getText();
		String nombre;
						 
		if (this.nombre.getText().length() > 1 && this.apellido.getText().length() > 1)
			nombre = this.nombre.getText().substring(0, 1).toLowerCase() + this.apellido.getText().substring(0, 1).toUpperCase() + this.apellido.getText().substring(1);
		else if (this.nombre.getText().length() == 1)
			nombre = this.nombre.getText().toLowerCase() + this.apellido.getText().substring(0, 1).toUpperCase() + this.apellido.getText().substring(1);
		else if (this.apellido.getText().length() == 1)
			nombre = this.nombre.getText().substring(0, 1).toLowerCase() + this.apellido.getText().toUpperCase();
		else if (this.nombre.getText().length() == 1 && this.nombre.getText().length() == 1)
			nombre = this.nombre.getText().toLowerCase() + this.apellido.getText().toUpperCase();
		else
			nombre = "";
		
		String nombreCompleto = this.nombre.getText() + " " + this.apellido.getText();
		String contraseña = "";
		String permisos;
		
		for (int i = 0; i < this.contraseña.getPassword().length; i++) contraseña += this.contraseña.getPassword()[i];
		
		if (this.basico.isSelected()) permisos = "basico";
		else if (this.avanzado.isSelected()) permisos = "avanzado";
		else permisos = "total";
		
		if (id.equals("") || nombre.equals("") || contraseña.equals("") || apellido.getText().equals("")) return null;
		return new User(id, nombre, nombreCompleto, contraseña.hashCode(), Permisos.getPermiso(permisos));
	}
}
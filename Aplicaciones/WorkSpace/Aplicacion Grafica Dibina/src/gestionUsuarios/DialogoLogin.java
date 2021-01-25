package gestionUsuarios;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import elementos.User;
import gestionElementosVisuales.ImageFactory;
import gestionFicheros.LectorElementos;

public class DialogoLogin extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public static Color COLORFONDO = new Color(255,119,0);
	
	public final static int DEFAULT_WIDTH = 500;
	public final static int DEFAULT_HEIGHT = 700;

	User user;
	String[] words;
	Identificador iden;

	JFrame ventana;
	JTextField usuario; 
	JPasswordField password;
	
	public DialogoLogin(JFrame ventana, String titulo, boolean modo) {
		super(ventana,titulo,modo);
		words = LectorElementos.leerLogin(0);
		iden = new Identificador();
		
		this.ventana = ventana;
		this.setContentPane(crearPanel());
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(new Dimension(DEFAULT_WIDTH, 700));
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private Container crearPanel() {
		JPanel panel  = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(crearImagenUser());
		panel.add(crearPanelRellenoFormulario());
		panel.add(crearBotonConfirmar());
		
		return panel;
	}

	private Container crearBotonConfirmar() {
		JPanel panel  = new JPanel(new GridLayout(6, 1, 0, 0));
		panel.setBackground(COLORFONDO);
		
		JButton boton = new JButton(words[3]);
		boton.addActionListener((l)->{
			user = iden.getUser(usuario.getText(), new String(password.getPassword()).hashCode());
			if(user != null) {
				this.dispose();
			}
			else {
				JOptionPane.showConfirmDialog(this, words[5], words[6], JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
				password.setText("");
			}});
		boton.setPreferredSize(new Dimension(100, 20));
		
		JPanel pBoton = new JPanel();
		pBoton.setBackground(COLORFONDO);
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[4]);
		boton.addActionListener((l)->{
			DialogoIdentificar dlg = new DialogoIdentificar(ventana, Arrays.copyOfRange(words, 7, 11), true);
			user = dlg.getUser();
			
			if (user != null) {
				this.dispose();
			}
		});
		if (words[0].equals("EUS")) boton.setBorder(null);
		boton.setPreferredSize(new Dimension(100, 20));
		
		pBoton = new JPanel();
		pBoton.setBackground(COLORFONDO);
		pBoton.add(boton);
		panel.add(pBoton);
		
		JPanel pIdiomas = new JPanel();
		pIdiomas.setBackground(COLORFONDO);
		ButtonGroup group = new ButtonGroup();
	    JRadioButton radioButton = new JRadioButton("ESP");
	    radioButton.setMnemonic(KeyEvent.VK_B);
	    radioButton.setActionCommand("ESP");
	    if(words[0].equals("ESP"))radioButton.setSelected(true);
	    pIdiomas.add(radioButton);
	    group.add(radioButton);
	    radioButton.addActionListener(this);
	    radioButton.setBackground(COLORFONDO);

	    radioButton = new JRadioButton("EUS");
	    radioButton.setMnemonic(KeyEvent.VK_C);
	    radioButton.setActionCommand("EUS");
	    if(words[0].equals("EUS"))radioButton.setSelected(true);
	    pIdiomas.add(radioButton);
	    group.add(radioButton);
	    radioButton.addActionListener(this);
	    radioButton.setBackground(COLORFONDO);
	    
	    radioButton = new JRadioButton("ENG");
	    radioButton.setMnemonic(KeyEvent.VK_D);
	    radioButton.setActionCommand("ENG");
	    if(words[0].equals("ENG"))radioButton.setSelected(true);
	    pIdiomas.add(radioButton);
	    group.add(radioButton);
	    radioButton.addActionListener(this);
	    radioButton.setBackground(COLORFONDO);
	    panel.add(pIdiomas);
		
		return panel;
	}

	private Container crearPanelRellenoFormulario() {
		JPanel panel  = new JPanel();
		usuario = new JTextField(20);
		password = new JPasswordField(15);
		password.setEchoChar('*');
		JPanel panelGrid = new JPanel(new GridLayout(2,1,0,0));
		panelGrid.setBorder(new LineBorder(Color.white, 15, true));
		panelGrid.setBackground(COLORFONDO);
		panelGrid.add(crearTextField(usuario, words[1]));
		panelGrid.add(crearTextField(password, words[2]));
		panel.add(panelGrid);
		panel.setBackground(COLORFONDO);
		return panel;
	}

	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		if(text instanceof JPasswordField) {
			JPanel panel2 = new JPanel();
			panel2.setBackground(Color.white);
			
			JCheckBox mostrar = new JCheckBox();
			mostrar.setIcon(new ImageIcon(escalarFotoFondoBlancoPng(ImageFactory.ICONO_HIDE,200,200,25,25)));
			mostrar.setSelectedIcon(new ImageIcon(escalarFotoFondoBlancoPng(ImageFactory.ICONO_SHOW,200,200,25,25)));
			mostrar.setBackground(Color.white);
			mostrar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(0, 3, 0, 3)));
			mostrar.addActionListener((l)->{
				if(mostrar.isSelected())password.setEchoChar((char)0);
				else password.setEchoChar('*');
			});
			text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
			panel2.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(10, 5, 5, 5),
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), titulo), 
								BorderFactory.createEmptyBorder(5, 5, 10, 5))));
			panel2.add(text);
			panel2.add(mostrar);
			panel.add(panel2);
		}
		else {
			text.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(5, 5, 10, 5),
							BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), titulo)), 
					BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(15, 10, 15, 10), 
							BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(8, 8, 8, 8)))));
			panel.add(text);
		}
		return panel;
	}

	
	private BufferedImage escalarFotoFondoBlancoPng(String path, int width, int height, int newWidth, int newHeight) {
		File imgFile = new File(path);
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try { 
		    buffImg = ImageIO.read(imgFile); 
		} 
		catch (IOException e) { }
		BufferedImage copy = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = copy.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
		g2d.drawImage(buffImg, 0, 0, null);
		g2d.dispose(); 
		
		return resizeImage(copy, newWidth,newHeight);		
	}

	private Container crearImagenUser() {
		JPanel panel = new JPanel();		
		JLabel lbimagen  = new JLabel(new ImageIcon(makeRoundedCorner(escalarFotoFondoBlancoPng(ImageFactory.IMAGEN_USER, 750, 750, 200, 200), 220)));
		lbimagen.setPreferredSize(new Dimension(200,200));
		panel.add(lbimagen);
		lbimagen.setBackground(COLORFONDO);
		panel.setBackground(COLORFONDO);
		return panel;
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}
	
	public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();

	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);
	    
	    g2.dispose();
	    
	    return output;
	}
	
	public User getUserLoged() {
		return user;
	}
	
	public String getLanguage() {
		return words[0];
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		switch(command) {
		case "EUS": words = LectorElementos.leerLogin(1); break;
		case "ENG": words = LectorElementos.leerLogin(2); break;
		case "ESP":
		default: words = LectorElementos.leerLogin(0); break;
		}
		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.removeAll();
		contentPane.add(crearPanel());
		contentPane.revalidate(); 
		contentPane.repaint();
	}
}
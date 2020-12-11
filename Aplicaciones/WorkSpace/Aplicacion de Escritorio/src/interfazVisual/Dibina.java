package interfazVisual;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Dibina extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 600;

	public Dibina() {
		super("Ventana Dividida");
		
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		
		this.setIconImage(new ImageIcon("Icons/icon.png").getImage());
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JSplitPane pVentana  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, crearPanelVentanaL(), crearPanelVentanaR());
		pVentana.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pVentana.setDividerLocation(150);

		this.setJMenuBar(crearBarraMenu());
		this.setContentPane(pVentana);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JMenuBar crearBarraMenu() {
		JMenuBar barra = new JMenuBar();
		
		barra.add(crearMenuPaquetes());
		barra.add(crearMenuStock());
		barra.add(Box.createHorizontalGlue());
		barra.add(crearMenuSalir());
		
		return barra;
	}
	
	private JMenu crearMenuStock() {
		JMenu menuPaquetes = new JMenu("Paquetes");
		JMenuItem ver, estado;
		
		ver = menuPaquetes.add("Ver stock disponible");
		ver.setIcon(new ImageIcon("Icons/añadir.png"));
		ver.addActionListener((e)->System.out.println("Recibir"));
		
		estado = menuPaquetes.add("Estado del almacén");
		estado.setIcon(new ImageIcon("Icons/eliminar.png"));
		estado.addActionListener((e)->System.out.println("Enviar"));
		
		return menuPaquetes;
	}

	private JMenu crearMenuPaquetes() {
		JMenu menuPaquetes = new JMenu("Paquetes");
		JMenuItem recibir, enviar, administrar;
		
		recibir = menuPaquetes.add("Recibir un paquete");
		recibir.setIcon(new ImageIcon("Icons/añadir.png"));
		recibir.addActionListener((e)->System.out.println("Recibir"));
		
		enviar = menuPaquetes.add("Enviar un paquete");
		enviar.setIcon(new ImageIcon("Icons/eliminar.png"));
		enviar.addActionListener((e)->System.out.println("Enviar"));
		
		administrar = menuPaquetes.add("Administrar paquetes");
		administrar.setIcon(new ImageIcon("Icons/eliminar.png"));
		administrar.addActionListener((e)->System.out.println("Administrar"));
		
		return menuPaquetes;
	}

	private JMenu crearMenuSalir() {
		JMenu menuSalir = new JMenu("Salir");
		JMenuItem salir;
		
		salir = menuSalir.add("Salir");
		salir.setIcon(new ImageIcon("Icons/salir.png"));
		salir.addActionListener((e)->Dibina.this.dispose());
		
		return menuSalir;
	}
	
	private Container crearPanelVentanaL() {
		JPanel panel = new JPanel();
		return panel;
	}

	private Container crearPanelVentanaR() {
		JPanel panel = new JPanel();
		return panel;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Dibina app = new Dibina();
		app.setVisible(true);
	}
}
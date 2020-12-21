package interfazVisual;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import diseñoTabla.ModeloTabla;
import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import gestionElementosVisuales.ImageFactory;
import gestionPaquetes.ControladorPedidos;
import paneles.PanelBuscar;
import paneles.PanelHP;
import paneles.PanelHistorial;
import paneles.PanelPrincipal;
import paneles.PanelStockDisponible;
import usuarios.DialogoLogin;
import usuarios.User;

public class Dibina extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 600;
	
	private List<Procedencia> listaProcedencias;
	private List<Producto> listaProductos;
	private List<Pedido> listaPedidos;
	private List<Tipo> listaTipos;
	private JScrollPane pDisplay;
	
	ControladorPedidos controlador;
	ModeloTabla modelo;
	User user;

	public Dibina() {
		super("Dibina Stock Manager");
		
		pDisplay = new JScrollPane();
		listaTipos = new ArrayList<>();
		listaPedidos = new ArrayList<>();
		listaProductos = new ArrayList<>();
		listaProcedencias = new ArrayList<>();

		PantallaCarga carga = new PantallaCarga(this, null, true);
		listaTipos = carga.getTipos();
		listaPedidos = carga.getPedidos();
		listaProductos = carga.getProductos();
		listaProcedencias = carga.getProcedencias();
		
		controlador = new ControladorPedidos(listaProcedencias, listaTipos);
		
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();

		this.setIconImage(ImageFactory.createImage(ImageFactory.ICONO));
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JSplitPane pVentana  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, crearPanelElementos(), pDisplay);
		pVentana.setDividerLocation(width/15);
		pVentana.setBorder(null);
		pDisplay.setBorder(null);
		
		this.setContentPane(pVentana);
		this.setJMenuBar(crearBarraMenu());
		pDisplay.setViewportView(new PanelPrincipal()); 
		
		
		this.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		DialogoLogin login = new DialogoLogin(this, "Dibina Login", true);
		user = login.getUserLoged();
		if(user == null)this.dispose();
	}
	
	private JMenuBar crearBarraMenu() {
		JMenuBar barra = new JMenuBar();
		
		barra.add(crearMenuStock());
		barra.add(crearMenuGestion());
		barra.add(Box.createHorizontalGlue());
		barra.add(crearMenuSalir());
		
		return barra;
	}
	
	private JMenu crearMenuGestion() {
		JMenu menuPaquetes = new JMenu("Gestion");
		JMenuItem ver, estado;
		
		ver = menuPaquetes.add("Ver lista pedidos");
		ver.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_PEDIDOS));
		ver.addActionListener((e)->System.out.println("Ver lista"));
		
		estado = menuPaquetes.add("Historial del almacén");
		estado.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_HISTORIAL));
		estado.addActionListener((e)->System.out.println("Historial"));
			pDisplay.setViewportView(new PanelHistorial(modelo));
			this.repaint();
		return menuPaquetes;
	}

	private JMenu crearMenuStock() {
		JMenu menuPaquetes = new JMenu("Stock");
		JMenuItem ver, pedido, buscar;
		
		ver = menuPaquetes.add("Ver stock");
		ver.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_STOCK));
		ver.addActionListener((e)->{
			pDisplay.setViewportView(new PanelStockDisponible(controlador, listaProductos));
			this.repaint();
		});
		ver.setToolTipText("");
		ver.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		
		pedido = menuPaquetes.add("Hacer pedido");
		pedido.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_PEDIDO));
		pedido.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHP(listaPedidos, controlador));
			this.repaint();
		});
		
		buscar = menuPaquetes.add("Buscar elementos");
		buscar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_BUSQUEDA));
		buscar.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modelo));
			this.repaint();
		});
		
		return menuPaquetes;
	}

	private JMenu crearMenuSalir() {
		JMenu menuPersonal = new JMenu("Area Personal");
		JMenuItem usuario, salir;
		
		usuario = menuPersonal.add("Mi usuario");
		usuario.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_USUARIO));
		usuario.addActionListener((e)->System.out.println("Usuario"));
		
		menuPersonal.add(crearSubmenuIdioma());
		
		salir = menuPersonal.add("Salir");
		salir.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_SALIR));
		salir.addActionListener((e)->Dibina.this.dispose());
		
		return menuPersonal;
	}
	
	private JMenuItem crearSubmenuIdioma() {
		JMenu submenu = new JMenu("Cambiar Idioma");
		JMenuItem item;
		
		submenu.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_IDIOMA));
		
		item = new JMenuItem("Castellano");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_CASTELLANO));
		item.addActionListener((e)->System.out.println("Idioma 1"));
		submenu.add(item);

		item = new JMenuItem("Euskera");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_EUSKERA));
		item.addActionListener((e)->System.out.println("Idioma 2"));
		submenu.add(item);

		item = new JMenuItem("Ingles");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_INGLES));
		item.addActionListener((e)->System.out.println("Idioma 3"));
		submenu.add(item);
		
		return submenu;
	}

	private Container crearPanelElementos() {
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBackground(Color.white);
		JButton boton;
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HOME));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelPrincipal());
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_STOCK));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelStockDisponible(controlador, listaProductos));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_PEDIDO));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHP(listaPedidos, controlador));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_BUSQUEDA));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modelo));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_PEDIDOS));
		boton.addActionListener((e)->{
			
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HISTORIAL));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHistorial(modelo));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);
		
		return panel;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
			
		@SuppressWarnings("unused")
		Dibina app = new Dibina();
	}
}
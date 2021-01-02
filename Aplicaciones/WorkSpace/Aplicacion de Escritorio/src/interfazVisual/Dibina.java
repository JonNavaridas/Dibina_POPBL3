package interfazVisual;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import gestionElementosVisuales.ImageFactory;
import gestionPaquetes.ControladorPedidos;
import paneles.PanelBuscar;
import paneles.PanelHacerPedido;
import paneles.PanelHistorial;
import paneles.PanelListaPedidos;
import paneles.PanelPrincipal;
import paneles.PanelStockDisponible;
import renderizadoTablaPedidos.ModeloTablaPedidos;
import renderizadoTablaTipos.ModeloTablaTipos;
import usuarios.DialogoLogin;
import usuarios.PanelUsuario;
import usuarios.User;

public class Dibina extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String[] IDIOMAS = { "Casellano", "Euskera", "Ingles" };
	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 600;
	
	private List<Procedencia> listaProcedencias; // Empresas proveedoras.
	private List<Producto> listaProductos; // Productos en almac�n.
	private List<Pedido> listaPedidos; // Pedidos por gestionar.
	private List<Tipo> listaTipos; // Tipos de productos posibles.
	private String[] destinos; // Lugares donde los productos pueden ser entregados.
	
	private JScrollPane pDisplay; // Panel cambiante.
	
	ControladorPedidos controlador;
	ModeloTablaPedidos modeloPedidos;
	ModeloTablaTipos modeloTipos;
	User user; // Usuario que ha iniciado sesi�n.

	public Dibina() {
		super("Dibina Stock Manager");
		pDisplay = new JScrollPane();
		
		// Pantalla que se mostrara mientras los elementos cargan.
		PantallaCarga carga = new PantallaCarga(this, null, true);
		listaTipos = carga.getTipos();
		destinos = carga.getDestinos();
		listaPedidos = carga.getPedidos();
		listaProductos = carga.getProductos();
		listaProcedencias = carga.getProcedencias();
		
		controlador = new ControladorPedidos(listaProcedencias, listaTipos, listaProductos);
		modeloPedidos = new ModeloTablaPedidos(listaPedidos);
		modeloTipos = new ModeloTablaTipos(listaProductos);
		
		pDisplay.setBorder(null);
		pDisplay.setViewportView(new PanelPrincipal());
		
		// Determinar el tama�o de la pantalla.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();

		// Colocar pantalla en el centro
		this.setIconImage(ImageFactory.createImage(ImageFactory.ICONO));
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		// Crear panel principal de la ventana
		JSplitPane pVentana  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, crearPanelElementos(), pDisplay);
		pVentana.setDividerLocation(width/15); // Colocar la divisi�n teniendo en cuenta el tama�o de la pantalla.
		pVentana.setBorder(null);

		this.setContentPane(pVentana);
		this.setJMenuBar(crearBarraMenu());
		
		this.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // La pantalla ocupa toda la ventana
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Determinar el usuario
		DialogoLogin login = new DialogoLogin(this, "Dibina Login", true);
		user = login.getUserLoged();
		
		if (user == null)
			this.dispose();
		else
			this.setVisible(true);
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
		ver.addActionListener((e)->{
			pDisplay.setViewportView(new PanelListaPedidos(modeloPedidos, listaPedidos));
			this.repaint();
		});
		ver.setToolTipText("Realizar un pedido a los almacenes."); // Aplicar una descripci�n
		ver.setAccelerator(KeyStroke.getKeyStroke("control 4")); // Poner una hotkey
		
		estado = menuPaquetes.add("Historial del almac�n");
		estado.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_HISTORIAL));
		estado.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHistorial(modeloTipos));
			this.repaint();
		});
		estado.setToolTipText("Realizar un pedido a los almacenes."); // Aplicar una descripci�n
		estado.setAccelerator(KeyStroke.getKeyStroke("control 5")); // Poner una hotkey
		
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
		ver.setToolTipText("Ver elementos disponibles en el almacen."); // Aplicar una descripci�n
		ver.setAccelerator(KeyStroke.getKeyStroke("control 1")); // Poner una hotkey
		
		pedido = menuPaquetes.add("Hacer pedido");
		pedido.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_PEDIDO));
		pedido.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHacerPedido(listaPedidos, destinos, controlador, user));
			this.repaint();
		});
		pedido.setToolTipText("Realizar un pedido a los almacenes."); // Aplicar una descripci�n
		pedido.setAccelerator(KeyStroke.getKeyStroke("control 2")); // Poner una hotkey
		
		buscar = menuPaquetes.add("Buscar elementos");
		buscar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_BUSQUEDA));
		buscar.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modeloTipos, listaProductos));
			this.repaint();
		});
		buscar.setToolTipText("Buscar diversos elementos en el almac�n, con la posibilidad de aplicar filtros."); // Aplicar una descripci�n
		buscar.setAccelerator(KeyStroke.getKeyStroke("control 3")); // Poner una hotkey
		
		return menuPaquetes;
	}

	private JMenu crearMenuSalir() {
		JMenu menuPersonal = new JMenu("Area Personal");
		JMenuItem item;
		
		item = menuPersonal.add("Mi usuario");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_USUARIO));
		item.addActionListener((e)->{
			pDisplay.setViewportView(new PanelUsuario(modeloPedidos, user));
			this.repaint();
		});
		item.setToolTipText("Ver distintos parametros sobre mi usuario."); // Aplicar una descripci�n
		item.setAccelerator(KeyStroke.getKeyStroke("control U")); // Poner una hotkey
		
		menuPersonal.add(crearSubmenuIdioma());
		
		item = menuPersonal.add("Crear usuario");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_USUARIO));
		item.addActionListener((e)->{
			System.out.println("Nuevo usuario.");
		});
		item.setToolTipText("Crear un nuevo usuario."); // Aplicar una descripci�n
		item.setAccelerator(KeyStroke.getKeyStroke("control N")); // Poner una hotkey
		
		item = menuPersonal.add("Salir");
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_SALIR));
		item.addActionListener((e)->Dibina.this.dispose());
		item.setAccelerator(KeyStroke.getKeyStroke("control S")); // Poner una hotkey
		
		return menuPersonal;
	}
	
	private JMenuItem crearSubmenuIdioma() {
		JMenu submenu = new JMenu("Cambiar Idioma");
		JMenuItem item;
		
		submenu.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_IDIOMA));
		
		item = new JMenuItem(Dibina.IDIOMAS[0]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_CASTELLANO));
		item.addActionListener((e)->{
			System.out.println("Idioma 1");
		});
		item.setToolTipText("Castellano."); // Aplicar una descripci�n
		item.setAccelerator(KeyStroke.getKeyStroke("control C")); // Poner una hotkey
		submenu.add(item);

		item = new JMenuItem(Dibina.IDIOMAS[1]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_EUSKERA));
		item.addActionListener((e)->{
			System.out.println("Idioma 2");
		});
		item.setToolTipText("Euskara."); // Aplicar una descripci�n
		item.setAccelerator(KeyStroke.getKeyStroke("control E")); // Poner una hotkey
		submenu.add(item);

		item = new JMenuItem(Dibina.IDIOMAS[2]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_INGLES));
		item.addActionListener((e)->{
			System.out.println("Idioma 3");
		});
		item.setToolTipText("English."); // Aplicar una descripci�n
		item.setAccelerator(KeyStroke.getKeyStroke("control I")); // Poner una hotkey
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
			pDisplay.setViewportView(new PanelHacerPedido(listaPedidos, destinos, controlador, user));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_BUSQUEDA));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modeloTipos, listaProductos));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_PEDIDOS));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelListaPedidos(modeloPedidos, listaPedidos));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HISTORIAL));
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHistorial(modeloTipos));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);
		
		return panel;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Dibina app = new Dibina();
	}
}
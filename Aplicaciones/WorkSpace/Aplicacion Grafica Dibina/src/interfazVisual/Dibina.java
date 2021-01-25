package interfazVisual;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Arrays;
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

import comunicacionSockets.Cliente;
import elementos.Pedido;
import elementos.Permisos;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import elementos.User;
import gestionElementosVisuales.ImageFactory;
import gestionFicheros.LectorElementos;
import gestionPedidos.ControladorPedidos;
import gestionUsuarios.DialogoCrearUsuario;
import gestionUsuarios.DialogoLogin;
import paneles.PanelBuscar;
import paneles.PanelHacerPedido;
import paneles.PanelHistorial;
import paneles.PanelListaPedidos;
import paneles.PanelPrincipal;
import paneles.PanelStockDisponible;
import renderizadoTablaPedidos.ModeloTablaPedidos;
import renderizadoTablaTipos.ModeloTablaTipos;
import usuarios.PanelUsuario;

public class Dibina extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String[] IDIOMAS = { "Casellano", "Euskera", "Ingles" };
	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 600;
	
	private List<Procedencia> listaProcedencias; // Empresas proveedoras.
	private List<Producto> listaProductos; // Productos en almacén.
	private List<Pedido> listaPedidos; // Pedidos por gestionar.
	private List<Tipo> listaTipos; // Tipos de productos posibles.
	private String[] destinos; // Lugares donde los productos pueden ser entregados.
	
	private String[] menus;
	private String[] paneles;
	private String[] passwordUser;
	private JScrollPane pDisplay; // Panel cambiante.
	
	ControladorPedidos controlador;
	ModeloTablaPedidos modeloPedidos;
	ModeloTablaTipos modeloTipos;
	User user; // Usuario que ha iniciado sesión.

	public Dibina() {
		super("Dibina Stock Manager");
		pDisplay = new JScrollPane();
		
		// Pantalla que se mostrara mientras los elementos cargan.
		actualizarDatosAplicacion();
		pDisplay.setBorder(null);
		
		// Determinar el tamaño de la pantalla.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();

		// Colocar pantalla en el centro
		this.setIconImage(ImageFactory.createImage(ImageFactory.ICONO));
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		// Determinar el usuario
		DialogoLogin login = new DialogoLogin(this, "Dibina Login", true);
		user = login.getUserLoged();
		
		switch(login.getLanguage()) {
		case "EUS": actualizarIdioma(1);break;
		case "ENG": actualizarIdioma(2);break;
		case "ESP":
		default: actualizarIdioma(0);break;
		}
		
		if (user == null)
			this.dispose();
		else {
			// Crear panel principal de la ventana
			JSplitPane pVentana  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, crearPanelElementos(), pDisplay);
			pVentana.setDividerLocation(width/15); // Colocar la división teniendo en cuenta el tamaño de la pantalla.
			pVentana.setBorder(null);
	
			listaPedidos.stream().forEach((p)->{
				if (p.getUser().equals(user)) {
					user.addPedido(p);
				}
			});
			
			this.setContentPane(pVentana);
			this.setJMenuBar(crearBarraMenu());
			
			this.setVisible(true);
			this.setBackground(Color.white);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH); // La pantalla ocupa toda la ventana
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public void actualizarPanelIdioma() {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		JSplitPane pVentana  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, crearPanelElementos(), pDisplay);
		pVentana.setDividerLocation(width/15); // Colocar la división teniendo en cuenta el tamaño de la pantalla.
		pVentana.setBorder(null);

		JSplitPane contentPane = (JSplitPane) this.getContentPane();
		contentPane.removeAll();
		contentPane.add(pVentana);
		contentPane.revalidate(); 
		contentPane.repaint();
		JMenuBar barra = this.getJMenuBar();
		barra.removeAll();
		barra.add(crearMenuStock());
		if (!user.getPermisos().equals(Permisos.BASICO)) barra.add(crearMenuGestion());
		barra.add(crearMenuActualizar());
		barra.add(Box.createHorizontalGlue());
		barra.add(crearMenuUsuarios());
		barra.revalidate();
        barra.repaint();
	}
	
	private JMenuBar crearBarraMenu() {
		JMenuBar barra = new JMenuBar();
		
		barra.add(crearMenuStock());
		if (!user.getPermisos().equals(Permisos.BASICO)) barra.add(crearMenuGestion());
		barra.add(crearMenuActualizar());
		barra.add(Box.createHorizontalGlue());
		barra.add(crearMenuUsuarios());
		
		return barra;
	}
	
	private JMenu crearMenuGestion() {
		JMenu menuPaquetes = new JMenu(menus[7]);
		JMenuItem ver, estado;
		
		ver = menuPaquetes.add(menus[8]);
		ver.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_PEDIDOS));
		ver.addActionListener((e)->{
			pDisplay.setViewportView(new PanelListaPedidos(modeloPedidos, listaPedidos, Arrays.copyOfRange(paneles, 14, 23), Arrays.copyOfRange(paneles, 36, 43)));
			this.repaint();
		});
		ver.setToolTipText(menus[9]); // Aplicar una descripción
		ver.setAccelerator(KeyStroke.getKeyStroke("control 4")); // Poner una hotkey
		
		estado = menuPaquetes.add(menus[10]);
		estado.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_HISTORIAL));
		estado.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHistorial(listaPedidos, Arrays.copyOfRange(paneles, 22, 25), Arrays.copyOfRange(paneles, 36, 43)));
			this.repaint();
		});
		estado.setToolTipText(menus[11]); // Aplicar una descripción
		estado.setAccelerator(KeyStroke.getKeyStroke("control 5")); // Poner una hotkey
		
		return menuPaquetes;
	}

	private JMenu crearMenuStock() {
		JMenu menuPaquetes = new JMenu(menus[0]);
		JMenuItem ver, pedido, buscar;
		
		ver = menuPaquetes.add(menus[1]);
		ver.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_STOCK));
		ver.addActionListener((e)->{
			pDisplay.setViewportView(new PanelStockDisponible(controlador, listaProductos, Arrays.copyOfRange(paneles, 0, 5)));
			this.repaint();
		});
		ver.setToolTipText(menus[2]); // Aplicar una descripción
		ver.setAccelerator(KeyStroke.getKeyStroke("control 1")); // Poner una hotkey
		
		pedido = menuPaquetes.add(menus[3]);
		pedido.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_PEDIDO));
		pedido.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHacerPedido(listaPedidos, listaProductos, destinos, controlador, user, Arrays.copyOfRange(paneles, 5, 14)));
			this.repaint();
		});
		pedido.setToolTipText(menus[4]); // Aplicar una descripción
		pedido.setAccelerator(KeyStroke.getKeyStroke("control 2")); // Poner una hotkey
		
		buscar = menuPaquetes.add(menus[5]);
		buscar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_BUSQUEDA));
		buscar.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modeloTipos, listaProductos, Arrays.copyOfRange(paneles, 14, 16)));
			this.repaint();
		});
		buscar.setToolTipText(menus[6]); // Aplicar una descripción
		buscar.setAccelerator(KeyStroke.getKeyStroke("control 3")); // Poner una hotkey
		
		return menuPaquetes;
	}
	
	private JMenu crearMenuActualizar() {
		JMenu menuPaquetes = new JMenu(menus[12]);
		JMenuItem actualizar;
		
		actualizar = menuPaquetes.add(menus[12]);
		actualizar.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_REFRESCAR));
		actualizar.addActionListener((e)->{
			actualizarDatosAplicacion();
		});
		actualizar.setToolTipText(menus[13]); // Aplicar una descripción
		actualizar.setAccelerator(KeyStroke.getKeyStroke("control R")); // Poner una hotkey
		
		return menuPaquetes;
	}
	
	public void actualizarDatosAplicacion() {
		PantallaCarga carga = new PantallaCarga(this, null, true);
		listaTipos = carga.getTipos();
		destinos = carga.getDestinos();
		listaPedidos = carga.getPedidos();
		listaProductos = carga.getProductos();
		listaProcedencias = carga.getProcedencias();
		
		if (user != null) {
			listaPedidos.stream().forEach((p)->{
				if (p.getUser().equals(user)) {
					user.addPedido(p);
				}
			});
		}
		
		controlador = new ControladorPedidos(listaProcedencias, listaTipos, listaProductos);
		modeloPedidos = new ModeloTablaPedidos(listaPedidos);
		modeloTipos = new ModeloTablaTipos(listaProductos);

		pDisplay.setViewportView(new PanelPrincipal());
	}

	private JMenu crearMenuUsuarios() {
		JMenu menuPersonal = new JMenu(menus[14]);
		JMenuItem item;
		
		item = menuPersonal.add(menus[15]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_USUARIO));
		item.addActionListener((e)->{
			modeloPedidos.setLista(user.getListaPedidos());
			pDisplay.setViewportView(new PanelUsuario(this, modeloPedidos, user, Arrays.copyOfRange(paneles, 22, 36), Arrays.copyOfRange(paneles, 36, 43), Arrays.copyOfRange(passwordUser,0,5)));
			this.repaint();
		});
		item.setToolTipText(menus[16]); // Aplicar una descripción
		item.setAccelerator(KeyStroke.getKeyStroke("control U")); // Poner una hotkey
		
		menuPersonal.add(crearSubmenuIdioma());
		
		if (user.getPermisos().equals(Permisos.TOTAL)) {
			item = menuPersonal.add(menus[18]);
			item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_USUARIO));
			item.addActionListener((l)->{
				DialogoCrearUsuario dlg = new DialogoCrearUsuario(this, menus[18], true, Arrays.copyOfRange(passwordUser,4,13));
				User user = dlg.getNewUsuario();
				
				if (user != null) {
					Cliente cliente = new Cliente(user.transformToString(), "Crear usuario", this);
					cliente.start();
				}
			});
			item.setToolTipText(menus[19]); // Aplicar una descripción
			item.setAccelerator(KeyStroke.getKeyStroke("control N")); // Poner una hotkey
		}
		
		item = menuPersonal.add(menus[20]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_SALIR));
		item.addActionListener((e)->Dibina.this.dispose());
		item.setAccelerator(KeyStroke.getKeyStroke("control S")); // Poner una hotkey
		
		return menuPersonal;
	}
	
	private JMenuItem crearSubmenuIdioma() {
		JMenu submenu = new JMenu(menus[17]);
		JMenuItem item;
		
		submenu.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_IDIOMA));
		
		item = new JMenuItem(Dibina.IDIOMAS[0]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_CASTELLANO));
		item.addActionListener((e)->{
			actualizarIdioma(0);
			actualizarPanelIdioma();
			actualizarDatosAplicacion();
		});
		item.setAccelerator(KeyStroke.getKeyStroke("alt C")); // Poner una hotkey
		submenu.add(item);

		item = new JMenuItem(Dibina.IDIOMAS[1]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_EUSKERA));
		item.addActionListener((e)->{
			actualizarIdioma(1);
			actualizarPanelIdioma();
			actualizarDatosAplicacion();
		});
		item.setAccelerator(KeyStroke.getKeyStroke("alt E")); // Poner una hotkey
		submenu.add(item);

		item = new JMenuItem(Dibina.IDIOMAS[2]);
		item.setIcon(ImageFactory.createImageIcon(ImageFactory.ICONO_INGLES));
		item.addActionListener((e)->{
			actualizarIdioma(2);
			actualizarPanelIdioma();
			actualizarDatosAplicacion();
		});
		item.setAccelerator(KeyStroke.getKeyStroke("alt I")); // Poner una hotkey
		submenu.add(item);
		
		return submenu;
	}
	
	public void actualizarIdioma(int language) {
		menus = LectorElementos.leerDibinaMenus(language);
		paneles = LectorElementos.leerPaneles(language);
		passwordUser = LectorElementos.leerPasswordUser(language);		
	}

	private Container crearPanelElementos() {
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBackground(Color.white);
		JButton boton;
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HOME));
		boton.setToolTipText(menus[21]);
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelPrincipal());
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_STOCK));
		boton.setToolTipText(menus[22]); 
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelStockDisponible(controlador, listaProductos, Arrays.copyOfRange(paneles, 0, 5)));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_PEDIDO));
		boton.setToolTipText(menus[23]); 
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHacerPedido(listaPedidos, listaProductos, destinos, controlador, user, Arrays.copyOfRange(paneles, 5, 14)));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_BUSQUEDA));
		boton.setToolTipText(menus[24]); 
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelBuscar(modeloTipos, listaProductos, Arrays.copyOfRange(paneles, 14, 16)));
			this.repaint();
		});
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_PEDIDOS));
		boton.setToolTipText(menus[25]); 
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelListaPedidos(modeloPedidos, listaPedidos, Arrays.copyOfRange(paneles, 14, 23), Arrays.copyOfRange(paneles, 36, 43)));
			this.repaint();
		});
		if (user.getPermisos().equals(Permisos.BASICO)) boton.setEnabled(false);
		boton.setBackground(Color.white);
		boton.setBorder(null);
		panel.add(boton);

		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HISTORIAL));
		boton.setToolTipText(menus[26]); 
		boton.addActionListener((e)->{
			pDisplay.setViewportView(new PanelHistorial(listaPedidos, Arrays.copyOfRange(paneles, 22, 25), Arrays.copyOfRange(paneles, 36, 43)));
			this.repaint();
		});
		if (user.getPermisos().equals(Permisos.BASICO)) boton.setEnabled(false);
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
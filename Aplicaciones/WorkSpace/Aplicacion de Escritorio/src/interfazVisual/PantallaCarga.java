package interfazVisual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import gestionElementosVisuales.ImageFactory;
import gestionFicheros.LectorElementos;

public class PantallaCarga extends JDialog {

	private static final long serialVersionUID = 1L;

	JProgressBar barraCarga;
	ImageIcon imagenCarga;
	
	String[] destinos;
	List<Tipo> listaTipos;
	List<Pedido> listaPedidos;
	List<Producto> listaProductos;
	List<Procedencia> listaProcedencias;
	
	public PantallaCarga(JFrame ventana, String titulo, boolean modo) {
		super(ventana, titulo, modo);
		imagenCarga = ImageFactory.createImageIcon(ImageFactory.IMAGEN_CARGA);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		
		this.setIconImage(ImageFactory.createImage(ImageFactory.ICONO));
		this.setLocation(width/2 - imagenCarga.getIconWidth()/2, height/2 - imagenCarga.getIconHeight()/2);
		
		this.setUndecorated(true);
		this.setContentPane(crearPanelVentana());
		this.pack();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.cargar(); // Inicializar elementos
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(new JLabel(imagenCarga));
		panel.add(crearPanelCarga(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component crearPanelCarga() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		
		barraCarga = new JProgressBar(0, 100);
		barraCarga.setValue(0);
		barraCarga.setSize(imagenCarga.getIconWidth(), 50);
		barraCarga.setStringPainted(true);
		barraCarga.setForeground(Color.black);
		barraCarga.setBorder(null);
		panel.add(barraCarga);
		
		return panel;
	}
	
	private void cargar() {
		LectorElementos gestor = new LectorElementos();
		
		listaTipos = gestor.leerTipos();
		destinos = gestor.leerDestinos();
		listaPedidos = gestor.leerPedidos();
		listaProductos = gestor.leerProductos();
		listaProcedencias = gestor.leerProcedencias();
		
		PantallaCarga.this.dispose();
	}
	
	public List<Tipo> getTipos() {
		return listaTipos;
	}

	public List<Pedido> getPedidos() {
		return listaPedidos;
	}
	
	public List<Procedencia> getProcedencias() {
		return listaProcedencias;
	}

	public List<Producto> getProductos() {
		return listaProductos;
	}

	public String[] getDestinos() {
		return destinos;
	}
}
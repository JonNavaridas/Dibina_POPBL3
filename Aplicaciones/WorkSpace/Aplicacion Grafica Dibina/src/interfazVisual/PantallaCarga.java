package interfazVisual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
		
		Carga carga = new Carga();
		carga.start(); // Inicializar elementos
		this.setVisible(true);
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(new JLabel(imagenCarga));
		panel.add(crearPanelCarga(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component crearPanelCarga() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.setBackground(new Color(255, 119, 0));
		
		barraCarga = new JProgressBar(0, 100);
		barraCarga.setBorder(BorderFactory.createLineBorder(Color.black));
		barraCarga.setSize(imagenCarga.getIconWidth(), 50);
		barraCarga.setForeground(new Color(255, 119, 0));
		barraCarga.setStringPainted(true);
		barraCarga.setValue(0);
		
		panel.add(barraCarga);
		
		return panel;
	}
	
	private class Carga extends Thread {

		@Override
		public void run() { // Caragr los distintos elementos del programa.
			try {
				listaTipos = LectorElementos.leerTipos();
				Thread.sleep(50);
				barraCarga.setValue(20);
				if (listaTipos == null) listaTipos = new ArrayList<>();
				
				destinos = LectorElementos.leerDestinos();
				Thread.sleep(50);
				barraCarga.setValue(40);
				
				listaPedidos = LectorElementos.leerPedidos();
				Thread.sleep(50);
				barraCarga.setValue(60);
				if (listaPedidos == null) listaPedidos = new ArrayList<>();
				
				listaProductos = LectorElementos.leerProductos();
				Thread.sleep(50);
				barraCarga.setValue(80);
				if (listaProductos == null) listaProductos = new ArrayList<>();
				
				listaProcedencias = LectorElementos.leerProcedencias();
				Thread.sleep(50);
				barraCarga.setValue(100);
				if (listaProcedencias == null) listaProcedencias = new ArrayList<>();
				
				PantallaCarga.this.dispose();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
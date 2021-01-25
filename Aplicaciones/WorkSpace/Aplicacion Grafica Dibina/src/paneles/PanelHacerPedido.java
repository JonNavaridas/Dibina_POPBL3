package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import comunicacionSockets.Cliente;
import elementos.Estado;
import elementos.Pedido;
import elementos.Producto;
import elementos.User;
import gestionElementosVisuales.FontFactory;
import gestionPantallas.RoundedBorder;
import gestionPedidos.ControladorPedidos;
import gestionPedidos.PedidoException;
import renderizadoLista.ListTipoRenderer;

public class PanelHacerPedido extends JScrollPane {

	private static final long serialVersionUID = 1L;

	List<Pedido> listaPedidos;
	List<Producto> listaProductos;
	ControladorPedidos controlador;
	Map<String, Integer> displayPedido;
	
	User user;
	Pedido pedido;
	String[] words;
	JList<String> elementosPedido;
	DefaultListModel<String> modeloPedido;
	
	DefaultListModel<String> modeloTipos;
	JComboBox<String> destino;
	JList<String> tipos;
	JTextField cantidad;

	public PanelHacerPedido(List<Pedido> listaPedidos, List<Producto> listaProductos, String[] destinos, ControladorPedidos controlador, User user, String[] words) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.displayPedido = new TreeMap<>();
		this.listaProductos = listaProductos;
		this.listaPedidos = listaPedidos;
		this.controlador = controlador;
		this.pedido = new Pedido();
		this.words = words;
		this.user = user;

		destino = new JComboBox<>(destinos);
		destino.setSelectedIndex(0);
		
		this.setViewportView(crearPanelVentana());
	}

	private Component crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		panel.add(panelGestionPedido(), BorderLayout.CENTER);
		panel.add(panelInformacion(), BorderLayout.WEST);
		
		return panel;
	}

	private Component panelGestionPedido() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(panelDatos(), BorderLayout.NORTH);
		panel.add(panelEspecificaciones(), BorderLayout.CENTER);
		panel.add(panelBotones(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component panelDatos() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panel.setBackground(Color.white);
		
		JPanel pCantidad = new JPanel();
		pCantidad.setBackground(Color.white);
		
		JPanel pDestino = new JPanel();
		pDestino.setBackground(Color.white);
		
		cantidad = new JTextField();
		pCantidad.add(cantidad);
		pDestino.add(destino);

		cantidad.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(new RoundedBorder(5), words[1]),
															  BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		destino.setBorder(new RoundedBorder(5));
		destino.setBackground(Color.white);
		
		cantidad.setPreferredSize(new Dimension(300, 50));
		destino.setPreferredSize(new Dimension(300, 50));

		cantidad.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		destino.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		
		panel.add(pCantidad);
		panel.add(pDestino);
		
		return panel;
	}

	private Component panelInformacion() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.white);
		
		panel.add(panelTipos());
		
		return panel;
	}

	private Component panelTipos() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(new RoundedBorder(5), " " + words[0] + " ", TitledBorder.LEFT,
														 TitledBorder.CENTER, FontFactory.createFont(FontFactory.BASE_FONT, 14)),
						BorderFactory.createEmptyBorder(5, 5, 5, 5))
				);
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(200, 500));

		modeloTipos = new DefaultListModel<>();
		tipos = new JList<>();
		
		tipos.setModel(modeloTipos);
		controlador.getListaProductos().stream().forEach((p)->modeloTipos.addElement(p.toString()));
		
		tipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tipos.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		tipos.setCellRenderer(new ListTipoRenderer());
		
		panel.setViewportView(tipos);
		
		return panel;
	}

	private Component panelEspecificaciones() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50), new RoundedBorder(10)));
		panel.setBackground(Color.white);
		
		elementosPedido = new JList<>();
		modeloPedido = new DefaultListModel<>();
		
		elementosPedido.setModel(modeloPedido);
		elementosPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		elementosPedido.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		
		panel.setViewportView(elementosPedido);
		
		return panel;
	}

	private Component panelBotones() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		panel.setBackground(Color.white);
		
		JPanel pBoton = new JPanel();
		JButton boton = new JButton(words[2]);
		pBoton.setBackground(Color.white);

		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			try {
				if (Integer.parseInt(cantidad.getText()) <= 0) throw new IllegalArgumentException();
				
				int unidades = Integer.parseInt(cantidad.getText());
				Producto productoSeleccionado = null;				
				int cantidadEnAlmacen = 0;
				
				for (Producto p : listaProductos) {
					if (p.toString().toLowerCase().equals(tipos.getSelectedValue().toLowerCase())) {
						cantidadEnAlmacen = p.getCantidad();
						if (unidades > cantidadEnAlmacen || unidades <= 0)
							throw new PedidoException(words[5]);
						else {
							productoSeleccionado = p.getCopy();
							break;
						}
					}
				}
				
				if (productoSeleccionado != null) {
					productoSeleccionado.setCantidad(unidades);
					pedido.addProducto(productoSeleccionado);
				}
				
				if (displayPedido.get(tipos.getSelectedValue()) != null)
					displayPedido.put(tipos.getSelectedValue(), displayPedido.get(tipos.getSelectedValue()) + Integer.parseInt(cantidad.getText()));
				else 
					displayPedido.put(tipos.getSelectedValue(), Integer.parseInt(cantidad.getText()));

				modeloPedido.removeAllElements();
				displayPedido.entrySet().stream().forEach((e)->modeloPedido.addElement(words[6] + ": " + e.getKey() + ". " + words[1] + ": " + e.getValue()));
			} 
			catch (PedidoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), words[7], JOptionPane.ERROR_MESSAGE);
			}
			catch (IllegalArgumentException | NullPointerException e) {
				JOptionPane.showMessageDialog(this, words[8], words[7], JOptionPane.ERROR_MESSAGE);
			}
			finally {
				cantidad.setText("");
			}
		});
		
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[3]);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			if (pedido.getListaProductos().size() > 0) {
				pedido.setUser(user);
				pedido.setId(controlador.generarID());
				pedido.setFecha(Calendar.getInstance().getTime());
				pedido.setDestino((String)destino.getSelectedItem());
				pedido.setEstado(Estado.PROCESANDO);
				
				Cliente cliente = new Cliente(pedido.transformToString(), "Añadir pedido", this);
				cliente.start();
				
				listaPedidos.add(pedido);
				user.addPedido(pedido);
				
				modeloPedido.removeAllElements();
				displayPedido = new TreeMap<>();
				pedido = new Pedido();
				cantidad.setText("");
			}
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		boton = new JButton(words[4]);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			if (!elementosPedido.isSelectionEmpty()) {
				try {
					int[] indices = elementosPedido.getSelectedIndices();
					
					for (int i = 0; i < indices.length; i++) {
						displayPedido.remove(pedido.getListaProductos().get(indices[i]).toString(),
								pedido.getListaProductos().get(indices[i]).getCantidad());
					}
					pedido.removeProducto(indices);
					
					for (int i = 0; i < indices.length; i++) {
						modeloPedido.remove(indices[i]);
					}
				} catch (PedidoException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), words[7], JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}
}
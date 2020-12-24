package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import diseñoLista.ListTipoRenderer;
import elementos.Pedido;
import elementos.Producto;
import gestionElementosVisuales.FontFactory;
import gestionPantallas.RoundedBorder;
import gestionPaquetes.ControladorPedidos;
import gestionPaquetes.PedidoException;

public class PanelHacerPedido extends JScrollPane {

	private static final long serialVersionUID = 1L;

	List<Pedido> listaPedidos;
	ControladorPedidos controlador;
	Map<String, Integer> displayPedido;
	
	Pedido pedido;
	JList<String> elementosPedido;
	DefaultListModel<String> modeloPedido;
	
	DefaultListModel<String> modeloTipos;
	JComboBox<String> destino;
	JList<String> tipos;
	JTextField cantidad;

	public PanelHacerPedido(List<Pedido> listaPedidos, String[] destinos, ControladorPedidos controlador) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.displayPedido = new HashMap<>();
		this.listaPedidos = listaPedidos;
		this.controlador = controlador;
		this.pedido = new Pedido();

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

		cantidad.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(new RoundedBorder(5), "Cantidad"),
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
						BorderFactory.createTitledBorder(new RoundedBorder(5), " Lista de Elementos ", TitledBorder.LEFT,
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
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		panel.setBackground(Color.white);
		
		JPanel pBoton = new JPanel();
		JButton boton = new JButton("Insertar");
		pBoton.setBackground(Color.white);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			try { // WORK IN PROGRESS
				if (Integer.parseInt(cantidad.getText()) <= 0) throw new IllegalArgumentException();
				
				String[] valores = tipos.getSelectedValue().split("[ ]");
				int unidades = Integer.parseInt(cantidad.getText());
				
				pedido.addProducto(new Producto(controlador.getTipo(valores[0]), null,
								  (unidades <= 1000 && unidades > 0) ? unidades : null, controlador.getProcedencia(valores[1])));
				
				if (displayPedido.get(tipos.getSelectedValue()) != null) {
					modeloPedido.removeAllElements();
					displayPedido.put(
							tipos.getSelectedValue(), displayPedido.get(tipos.getSelectedValue()) + Integer.parseInt(cantidad.getText())
					);
					displayPedido.entrySet().stream().forEach((e)->{
						modeloPedido.addElement("Elemento: " + e.getKey() + ". Cantidad: " + e.getValue());
					});
				}
				else {
					displayPedido.put(tipos.getSelectedValue(), Integer.parseInt(cantidad.getText()));
					modeloPedido.addElement("Elemento: " + tipos.getSelectedValue() + ". Cantidad: " + cantidad.getText());
				}
					
			} 
			catch (IllegalArgumentException | NullPointerException e) {
				JOptionPane.showMessageDialog(this, "Los datos proporcionados no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		pBoton = new JPanel();
		boton = new JButton("Hacer pedido");
		pBoton.setBackground(Color.white);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			if (pedido.getListaProductos().size() > 0) {
				pedido.setId(controlador.generarID());
				pedido.setFecha(Calendar.getInstance().getTime());
				pedido.setDestino((String)destino.getSelectedItem());
				listaPedidos.add(pedido);
				modeloPedido.removeAllElements();
			}
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		pBoton = new JPanel();
		boton = new JButton("Eliminar");
		pBoton.setBackground(Color.white);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		boton.addActionListener((l)->{
			if (!elementosPedido.isSelectionEmpty()) {
				try {
					String[] valores = elementosPedido.getSelectedValue().split("[.]");
					String[] parametros = valores[0].split("[ ]");

					modeloPedido.remove(elementosPedido.getSelectedIndex());
					pedido.removeProducto(controlador.getTipo(parametros[1]), controlador.getProcedencia(parametros[2]),
							Integer.parseInt(valores[1].split(": ")[1]));
				} catch (PedidoException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}
}
package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import diseñoLista.ListTipoRenderer;
import elementos.Pedido;
import gestionPantallas.RoundedBorder;
import gestionPaquetes.ControladorPedidos;

public class PanelHP extends JScrollPane {

	private static final long serialVersionUID = 1L;

	List<Pedido> listaPedidos;
	ControladorPedidos controlador;
	
	DefaultListModel<String> modelo;
	JComboBox<String> destino;
	JList<String> tipos;
	JTextField cantidad;

	public PanelHP(List<Pedido> listaPedidos, ControladorPedidos controlador) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.listaPedidos = listaPedidos;
		this.controlador = controlador;

		destino = new JComboBox<>(controlador.getTipoArray());
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

		cantidad.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		destino.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		
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
														 TitledBorder.CENTER, new Font("Times new roman", Font.TRUETYPE_FONT, 14)),
						BorderFactory.createEmptyBorder(5, 5, 5, 5))
				);
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(200, 500));

		modelo = new DefaultListModel<>();
		tipos = new JList<>();
		
		tipos.setModel(modelo);
		controlador.getListaTipos().stream().forEach((p)->modelo.addElement(p.getNombre()));
		
		tipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tipos.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 14));
		tipos.setCellRenderer(new ListTipoRenderer());
		
		panel.setViewportView(tipos);
		
		return panel;
	}

	private Component panelEspecificaciones() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50), new RoundedBorder(5)));
		panel.setBackground(Color.white);
		
		
		
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
		boton.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 14));
		boton.addActionListener((l)->{
			
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		pBoton = new JPanel();
		boton = new JButton("Hacer pedido");
		pBoton.setBackground(Color.white);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 14));
		boton.addActionListener((l)->{
			
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		pBoton = new JPanel();
		boton = new JButton("Eliminar");
		pBoton.setBackground(Color.white);
		
		boton.setPreferredSize(new Dimension(200, 40));
		boton.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 14));
		boton.addActionListener((l)->{
			
		});
		pBoton.add(boton);
		panel.add(pBoton);
		
		return panel;
	}
}
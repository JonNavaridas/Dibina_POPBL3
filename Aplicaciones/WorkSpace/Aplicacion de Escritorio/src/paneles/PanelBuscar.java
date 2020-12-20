package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import diseñoTabla.HeaderRenderer;
import diseñoTabla.ModeloColumnas;
import diseñoTabla.ModeloTabla;
import diseñoTabla.RendererTabla;
import elementos.Producto;

public class PanelBuscar extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static final String[] tipos = { "Todo", "Mascarillas", "Vacunas", "Guantes", "Hidrogel", "Epi" };
	private static final String[] cantidades = { "Todo", "0-100", "100-200", "200-300", "300-400", "400-500" };
	
	JComboBox<String> tipo, procedencia, cantidad;
	String[] listaTipo, listaProcedencia, listaCantidad;
	
	List<Producto> listaProductos;
	List<Producto> listaDisplay;
	ModeloTabla modeloTabla;
	JTable tabla;

	public PanelBuscar(ModeloTabla controlador) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.modeloTabla = controlador;
		
		listaProcedencia = new String[1];
		listaProcedencia[0] = "Todo";
		
		tipo = new JComboBox<>(tipos);
		procedencia = new JComboBox<>(listaProcedencia);
		cantidad = new JComboBox<>(cantidades);
		
		tipo.setSelectedIndex(0);
		procedencia.setSelectedIndex(0);
		cantidad.setSelectedIndex(0);
		
		this.setViewportView(crearPanelVentana());
		this.setBackground(Color.white);
	}

	private Component crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		JButton boton = new JButton("Filtrar");
		boton.addActionListener((e)->{
			listaDisplay = listaProductos;/*
			if (!((String)tipo.getSelectedItem()).equals("Todo")) {
				listaDisplay = listaDisplay.stream().filter((p)->p.getTipo().equals(
									((Tipo)tipo.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)procedencia.getSelectedItem()).equals("Todo")) {
				listaDisplay = listaDisplay.stream().filter((p)->p.getProcedenciaDestino().equals(
						((String)tipo.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)cantidad.getSelectedItem()).equals("Todo")) {
				String[] valores = ((String)cantidad.getSelectedItem()).split("[-]");
				
				listaDisplay = listaDisplay.stream().filter((p)->{
					return p.getCantidad() >= Integer.parseInt(valores[0]) && p.getCantidad() <= Integer.parseInt(valores[1]);
				}).collect(Collectors.toList());
			}
			controlador.setListaPaquetes(listaDisplay);*/
			this.repaint();
		});
		boton.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		boton.setPreferredSize(new Dimension(150, 30));
		
		JPanel pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		
		panel.add(crearPanelFiltros(), BorderLayout.NORTH);
		panel.add(crearPanelTabla(), BorderLayout.CENTER);
		panel.add(pBoton, BorderLayout.SOUTH);
		
		return panel;
	}

	private Component crearPanelFiltros() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		
		tipo.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		procedencia.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		cantidad.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		
		panel.add(tipo);
		panel.add(procedencia);
		panel.add(cantidad);
		
		return panel;
	}

	private Component crearPanelTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla()));
		tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));
		
		panel.setViewportView(tabla);
		
		return panel;
	}
}
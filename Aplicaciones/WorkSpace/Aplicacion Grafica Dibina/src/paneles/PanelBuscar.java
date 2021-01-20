package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import elementos.Producto;
import gestionElementosVisuales.FontFactory;
import renderizadoTablaTipos.HeaderRenderer;
import renderizadoTablaTipos.ModeloColumnas;
import renderizadoTablaTipos.ModeloTablaTipos;
import renderizadoTablaTipos.RendererTabla;

public class PanelBuscar extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	List<Integer> listaCantidades;
	JComboBox<String> tipo, procedencia, cantidad;
	String[] listaTipo, listaProcedencia, listaCantidad, words;
	
	List<Producto> listaProductos;
	List<Producto> listaDisplay;
	ModeloTablaTipos modeloTabla;
	JTable tabla;

	public PanelBuscar(ModeloTablaTipos modelo, List<Producto> listaProductos, String[] words) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.words = words;
		this.modeloTabla = modelo;
		this.listaProductos = listaProductos;
		this.listaDisplay = listaProductos;
		this.modeloTabla.setLista(listaDisplay);
		
		// Pasar los datos a arrays para poder añadirlos a los combo boxes.
		listaTipo = this.transformToArray(listaProductos.stream().map(Producto::getTipo).distinct().collect(Collectors.toList()));
		listaProcedencia = this.transformToArray(listaProductos.stream().map(Producto::getProcedencia).distinct().collect(Collectors.toList()));
		listaCantidades = listaProductos.stream().map(Producto::getCantidad).distinct().collect(Collectors.toList());
		
		if (listaCantidades.size() > 0) setCantidades();
		else {
			listaCantidad = new String[1];
			listaCantidad[0] = words[0];
		}
		
		tipo = new JComboBox<>(listaTipo);
		procedencia = new JComboBox<>(listaProcedencia);
		cantidad = new JComboBox<>(listaCantidad);
		
		tipo.setSelectedIndex(0);
		procedencia.setSelectedIndex(0);
		cantidad.setSelectedIndex(0);
		
		this.setViewportView(crearPanelVentana());
		this.setBackground(Color.white);
	}

	private Component crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[1]);
		boton.addActionListener((e)->{
			listaDisplay = listaProductos;
			if (!((String)tipo.getSelectedItem()).equals(words[0])) { // Aplicar filtro tipos
				listaDisplay = listaDisplay.stream().filter((p)->p.getTipo().toString().equals(
									((String)tipo.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)procedencia.getSelectedItem()).equals(words[0])) { // Aplicar filtro procedencia
				listaDisplay = listaDisplay.stream().filter((p)->p.getProcedencia().toString().equals(
						((String)procedencia.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)cantidad.getSelectedItem()).equals(words[0])) { // Aplicar filtro cantidad
				listaDisplay = listaDisplay.stream().filter((p)->{
					return p.getCantidad() >= Integer.parseInt(((String)cantidad.getSelectedItem()).substring(1));
				}).collect(Collectors.toList());
			}
			modeloTabla.setLista(listaDisplay); // Mostrar la nueva lista con los filtros aplicados
			this.repaint();
		});
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
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
		
		tipo.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		procedencia.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		cantidad.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		
		panel.add(tipo);
		panel.add(procedencia);
		panel.add(cantidad);
		
		return panel;
	}

	private Component crearPanelTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		int language;
		
		switch(words[0]) {
		case "Guztia":language = 1; break;
		case "All": language = 2; break;
		case "Todo":
			default:language = 0; break;
		}
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla(), language));
		tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));
		
		tabla.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) { // Detectar si el usuario a clickado en el titulo de la lista
				switch(tabla.columnAtPoint(e.getPoint())) { // Comprobar en que columna a clickado
				case 0:  // Ordenar alfabeticamente teniendo en cuenta el tipo
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Producto::getNombreTipo)).collect(Collectors.toList());
					break;
				case 1: // Ordenar por valor (de menor a mayor)
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Producto::getCantidad)).collect(Collectors.toList());
					break;
				case 2: // Ordenar alfabeticamente teniendo en cuenta la procedencia
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Producto::getNombreProcedencia)).collect(Collectors.toList());
					break;
				case 3: // Ordenar por fecha
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Producto::getFecha).reversed()).collect(Collectors.toList());
					break;
				}
				modeloTabla.setLista(listaDisplay);
				PanelBuscar.this.repaint();
			}
		});
		panel.getViewport().setBackground(Color.white);
		panel.setViewportView(tabla);
		
		return panel;
	}
	
	// Transformar cualquier lista en un array de String teniendo en cuenta su toString()
	private <T> String[] transformToArray(List<T> elementos) {
		String[] array = new String[elementos.size() + 1];
		
		array[0] = "";
		for (int i = 0; i < elementos.size(); i++) {
			array[i + 1] = elementos.get(i).toString();
		}
		Arrays.sort(array);
		array[0] = words[0];
		
		return array;
	}

	//  Determinar el filtro de cantidad dependiendo de la cantidad más alta
	private void setCantidades() {
		Integer max = listaCantidades.stream().reduce((a, b)->(a > b) ? a : b).get();
		Integer divisor = (max >= 900) ? 100 : (max >= 500) ? 50 : (max >= 200) ? 20 : (max >= 20) ? 10 : 1;
		Integer length = (max/divisor) + 1;
		
		listaCantidad = new String[length + 1];
		listaCantidad[0] = words[0];
		
		for (int i = 0; i < length; i++)
			listaCantidad[i + 1] = "+" + String.valueOf((i) * divisor);	
	}
}